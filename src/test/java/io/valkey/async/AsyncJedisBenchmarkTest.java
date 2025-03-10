package io.valkey.async;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

import io.valkey.DefaultJedisClientConfig;
import io.valkey.HostAndPort;
import io.valkey.HostAndPorts;
import io.valkey.providers.PooledConnectionProvider;

public class AsyncJedisBenchmarkTest extends AsyncJedisBasicTest {
    private static final int THREAD_COUNT = 100;
    private static final int OPERATIONS_PER_THREAD = 1000000; //
    private static final AtomicLong totalOperations = new AtomicLong(0);
    private static final AtomicLong totalTime = new AtomicLong(0);

    private static final HostAndPort hnp = HostAndPorts.getRedisServers().get(0);

    public static void main(String[] args) {
        DefaultJedisClientConfig config = DefaultJedisClientConfig.builder()
            .password("foobared")
            .build();
        AsyncJedis jedis = new AsyncJedis(new PooledConnectionProvider(hnp, config));
        try {
            jedis.flushAll();
            
            // 创建计数器，用于等待所有线程完成
            CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
            
            // 记录开始时间
            long startTime = System.currentTimeMillis();
            
            // 创建并启动线程
            for (int i = 0; i < THREAD_COUNT; i++) {
                final int threadId = i;
                new Thread(() -> {
                    try {
                        for (int j = 0; j < OPERATIONS_PER_THREAD; j++) {
                            String key = "test:key:" + threadId + ":" + j;
                            String value = "test:value:" + threadId + ":" + j;
                            jedis.set(key, value);
                            totalOperations.incrementAndGet();
                        }
                    } finally {
                        latch.countDown();
                    }
                }).start();
            }
            
            // 等待所有线程完成
            latch.await();
            
            // 计算总时间
            long endTime = System.currentTimeMillis();
            totalTime.set(endTime - startTime);
            
            // 计算并输出结果
            double qps = (totalOperations.get() * 1000.0) / totalTime.get();
            System.out.println("性能测试结果：");
            System.out.println("总线程数：" + THREAD_COUNT);
            System.out.println("每个线程操作次数：" + OPERATIONS_PER_THREAD);
            System.out.println("总操作次数：" + totalOperations.get());
            System.out.println("总执行时间：" + totalTime.get() + " ms");
            System.out.printf("QPS：%.2f%n", qps);
            
        } catch (InterruptedException e) {
            System.err.println("测试被中断：" + e.getMessage());
            e.printStackTrace();
        } finally {
            // 关闭Jedis连接
            jedis.close();
        }
    }
}
