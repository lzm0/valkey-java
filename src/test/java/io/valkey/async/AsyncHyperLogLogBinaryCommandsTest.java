package io.valkey.async;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

public class AsyncHyperLogLogBinaryCommandsTest extends AsyncJedisBasicTest {

    private static final byte[] KEY1 = "hll1".getBytes();
    private static final byte[] KEY2 = "hll2".getBytes();
    private static final byte[] KEY3 = "hll3".getBytes();

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        jedis.del(KEY1, KEY2, KEY3).get();
    }

    @Test
    public void testPfadd() throws ExecutionException, InterruptedException {
        Long status = jedis.pfadd(KEY1, "a".getBytes(), "b".getBytes(), "c".getBytes(), "d".getBytes()).get();
        assertEquals(Long.valueOf(1), status);

        status = jedis.pfadd(KEY1, "a".getBytes(), "b".getBytes(), "c".getBytes(), "d".getBytes()).get();
        assertEquals(Long.valueOf(0), status);

        status = jedis.pfadd(KEY1, "e".getBytes(), "f".getBytes(), "g".getBytes()).get();
        assertEquals(Long.valueOf(1), status);
    }

    @Test
    public void testPfcount() throws ExecutionException, InterruptedException {
        jedis.pfadd(KEY1, "a".getBytes(), "b".getBytes(), "c".getBytes(), "d".getBytes()).get();
        jedis.pfadd(KEY2, "c".getBytes(), "d".getBytes(), "e".getBytes(), "f".getBytes()).get();

        Long count = jedis.pfcount(KEY1).get();
        assertEquals(Long.valueOf(4), count);

        count = jedis.pfcount(KEY2).get();
        assertEquals(Long.valueOf(4), count);

        count = jedis.pfcount(KEY1, KEY2).get();
        assertEquals(Long.valueOf(6), count);
    }

    @Test
    public void testPfmerge() throws ExecutionException, InterruptedException {
        jedis.pfadd(KEY1, "a".getBytes(), "b".getBytes(), "c".getBytes(), "d".getBytes()).get();
        jedis.pfadd(KEY2, "c".getBytes(), "d".getBytes(), "e".getBytes(), "f".getBytes()).get();

        String status = jedis.pfmerge(KEY3, KEY1, KEY2).get();
        assertEquals("OK", status);

        Long count = jedis.pfcount(KEY3).get();
        assertEquals(Long.valueOf(6), count);
    }
} 