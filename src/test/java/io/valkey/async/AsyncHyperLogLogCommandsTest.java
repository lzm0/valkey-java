package io.valkey.async;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

public class AsyncHyperLogLogCommandsTest extends AsyncJedisBasicTest {

    private static final String KEY1 = "hll1";
    private static final String KEY2 = "hll2";
    private static final String KEY3 = "hll3";

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        jedis.del(KEY1, KEY2, KEY3).get();
    }

    @Test
    public void testPfadd() throws ExecutionException, InterruptedException {
        Long status = jedis.pfadd(KEY1, "a", "b", "c", "d").get();
        assertEquals(Long.valueOf(1), status);

        status = jedis.pfadd(KEY1, "a", "b", "c", "d").get();
        assertEquals(Long.valueOf(0), status);

        status = jedis.pfadd(KEY1, "e", "f", "g").get();
        assertEquals(Long.valueOf(1), status);
    }

    @Test
    public void testPfcount() throws ExecutionException, InterruptedException {
        jedis.pfadd(KEY1, "a", "b", "c", "d").get();
        jedis.pfadd(KEY2, "c", "d", "e", "f").get();

        Long count = jedis.pfcount(KEY1).get();
        assertEquals(Long.valueOf(4), count);

        count = jedis.pfcount(KEY2).get();
        assertEquals(Long.valueOf(4), count);

        count = jedis.pfcount(KEY1, KEY2).get();
        assertEquals(Long.valueOf(6), count);
    }

    @Test
    public void testPfmerge() throws ExecutionException, InterruptedException {
        jedis.pfadd(KEY1, "a", "b", "c", "d").get();
        jedis.pfadd(KEY2, "c", "d", "e", "f").get();

        String status = jedis.pfmerge(KEY3, KEY1, KEY2).get();
        assertEquals("OK", status);

        Long count = jedis.pfcount(KEY3).get();
        assertEquals(Long.valueOf(6), count);
    }
} 