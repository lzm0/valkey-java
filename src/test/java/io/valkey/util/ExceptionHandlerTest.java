package io.valkey.util;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;

import io.valkey.DefaultJedisClientConfig;
import io.valkey.ExceptionHandler;
import io.valkey.HostAndPort;
import io.valkey.HostAndPorts;
import io.valkey.Jedis;
import io.valkey.UnifiedJedis;
import io.valkey.providers.PooledConnectionProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ExceptionHandlerTest {
    private final HostAndPort hostAndPort = HostAndPorts.getRedisServers().get(0);
    private Jedis jedis;

    @Before
    public void setUp() {
        jedis = new Jedis(hostAndPort);
        jedis.auth("foobared");
    }

    @Test
    public void retryWithExceptionHandler() {
        int maxAttempts = 10;
        Duration maxTotalRetriesDuration = Duration.ofSeconds(10);
        PooledConnectionProvider provider = new PooledConnectionProvider(hostAndPort,
            DefaultJedisClientConfig.builder().password("foobared").build());
        ExceptionHandler handler = new ExceptionHandler();
        handler.register(
            message -> message.contains("WRONGTYPE Operation against a key holding the wrong kind of value"),
            errorMessage -> {
                try {
                    System.out.println("Retrying...");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        );

        String key = UUID.randomUUID().toString();
        Assert.assertEquals("OK", jedis.set(key, key));
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // after 5s, del this key.
                    Thread.sleep(5000);
                    Assert.assertEquals(1, jedis.del(key));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        UnifiedJedis unifiedJedis = new UnifiedJedis(provider, maxAttempts, maxTotalRetriesDuration, handler);
        Map<String, String> map = unifiedJedis.hgetAll(key);
        Assert.assertTrue(map.isEmpty());
    }

    @Test
    public void retryWithExponentialBackoffCallback() {
        int maxAttempts = 4;
        Duration maxTotalRetriesDuration = Duration.ofSeconds(20);
        PooledConnectionProvider provider = new PooledConnectionProvider(hostAndPort,
            DefaultJedisClientConfig.builder().password("foobared").build());
        String key = UUID.randomUUID().toString();
        Assert.assertEquals("OK", jedis.set(key, key));
        ExceptionHandler handler = new ExceptionHandler();
        class ExponentialBackoffCallback implements ExceptionHandler.ErrorCallback {
            private int attempt = 0; // 计数器

            @Override
            public void onError(String errorMessage) {
                // 计算 sleep 时间（2^attempt 秒）
                int sleepTime = (int) Math.pow(2, attempt);
                try {
                    System.out.println("Sleeping for " + sleepTime + " seconds before handling: " + errorMessage);
                    Thread.sleep(sleepTime * 1000); // 转换为毫秒
                    if (attempt == maxAttempts - 2) {
                        Assert.assertEquals(1, jedis.del(key));
                    }
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt(); // 恢复中断状态
                }
                attempt++; // 增加计数
            }
        }

        handler.register(
            message -> message.contains("WRONGTYPE Operation against a key holding the wrong kind of value"),
            new ExponentialBackoffCallback()
        );

        UnifiedJedis unifiedJedis = new UnifiedJedis(provider, maxAttempts, maxTotalRetriesDuration, handler);
        long start = System.currentTimeMillis();
        Map<String, String> map = unifiedJedis.hgetAll(key);
        long end = System.currentTimeMillis();
        Assert.assertTrue(map.isEmpty());
        Assert.assertTrue(end - start >= 7000); // 2^0 + 2^1 + 2^2 = 7 seconds
    }
}
