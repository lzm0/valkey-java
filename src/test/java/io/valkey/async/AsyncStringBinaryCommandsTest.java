package io.valkey.async;

import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ExecutionException;

import io.valkey.resps.*;
import io.valkey.params.*;

import static org.junit.Assert.*;

public class AsyncStringBinaryCommandsTest extends AsyncJedisBasicTest {

    private static final byte[] KEY = "mykey".getBytes();
    private static final byte[] VALUE = "value".getBytes();
    private static final byte[] OTHER_KEY = "otherkey".getBytes();
    private static final byte[] OTHER_VALUE = "othervalue".getBytes();

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        jedis.del(KEY).get();
        jedis.del(OTHER_KEY).get();
    }

    @Test
    public void testSet() throws ExecutionException, InterruptedException {
        String status = jedis.set(KEY, VALUE).get();
        assertEquals("OK", status);
        assertArrayEquals(VALUE, jedis.get(KEY).get());
    }

    @Test
    public void testSetWithParams() throws ExecutionException, InterruptedException {
        SetParams params = SetParams.setParams().nx();
        String status = jedis.set(KEY, VALUE, params).get();
        assertEquals("OK", status);

        status = jedis.set(KEY, OTHER_VALUE, params).get();
        assertNull(status);
        assertArrayEquals(VALUE, jedis.get(KEY).get());
    }

    @Test
    public void testGet() throws ExecutionException, InterruptedException {
        assertNull(jedis.get(KEY).get());
        jedis.set(KEY, VALUE).get();
        assertArrayEquals(VALUE, jedis.get(KEY).get());
    }

    @Test
    public void testSetGet() throws ExecutionException, InterruptedException {
        assertNull(jedis.setGet(KEY, VALUE).get());
        assertArrayEquals(VALUE, jedis.get(KEY).get());

        assertArrayEquals(VALUE, jedis.setGet(KEY, OTHER_VALUE).get());
        assertArrayEquals(OTHER_VALUE, jedis.get(KEY).get());
    }

    @Test
    public void testSetGetWithParams() throws ExecutionException, InterruptedException {
        SetParams params = SetParams.setParams().nx();
        byte[] oldValue = jedis.setGet(KEY, VALUE, params).get();
        assertNull(oldValue);
        assertArrayEquals(VALUE, jedis.get(KEY).get());

        oldValue = jedis.setGet(KEY, OTHER_VALUE, params).get();
        assertArrayEquals(VALUE, oldValue);
        assertArrayEquals(VALUE, jedis.get(KEY).get());
    }

    @Test
    public void testGetDel() throws ExecutionException, InterruptedException {
        assertNull(jedis.getDel(KEY).get());
        jedis.set(KEY, VALUE).get();
        assertArrayEquals(VALUE, jedis.getDel(KEY).get());
        assertNull(jedis.get(KEY).get());
    }

    @Test
    public void testGetEx() throws ExecutionException, InterruptedException {
        assertNull(jedis.getEx(KEY, GetExParams.getExParams().ex(1)).get());
        jedis.set(KEY, VALUE).get();
        assertArrayEquals(VALUE, jedis.getEx(KEY, GetExParams.getExParams().ex(1)).get());
        Thread.sleep(2000);
        assertNull(jedis.get(KEY).get());
    }

    @Test
    public void testSetrange() throws ExecutionException, InterruptedException {
        assertEquals(VALUE.length, jedis.setrange(KEY, 0, VALUE).get().longValue());
        jedis.set(KEY, VALUE).get();
        assertEquals(OTHER_VALUE.length, jedis.setrange(KEY, 0, OTHER_VALUE).get().longValue());
        assertArrayEquals(OTHER_VALUE, jedis.get(KEY).get());
    }

    @Test
    public void testGetrange() throws ExecutionException, InterruptedException {
        assertArrayEquals("".getBytes(), jedis.getrange(KEY, 0, -1).get());
        jedis.set(KEY, VALUE).get();
        assertArrayEquals(VALUE, jedis.getrange(KEY, 0, -1).get());
        assertArrayEquals("val".getBytes(), jedis.getrange(KEY, 0, 2).get());
    }

    @Test
    public void testGetSet() throws ExecutionException, InterruptedException {
        assertNull(jedis.getSet(KEY, VALUE).get());
        assertArrayEquals(VALUE, jedis.get(KEY).get());

        assertArrayEquals(VALUE, jedis.getSet(KEY, OTHER_VALUE).get());
        assertArrayEquals(OTHER_VALUE, jedis.get(KEY).get());
    }

    @Test
    public void testSetnx() throws ExecutionException, InterruptedException {
        assertEquals(1L, jedis.setnx(KEY, VALUE).get().longValue());
        assertEquals(0L, jedis.setnx(KEY, OTHER_VALUE).get().longValue());
        assertArrayEquals(VALUE, jedis.get(KEY).get());
    }

    @Test
    public void testSetex() throws ExecutionException, InterruptedException {
        assertEquals("OK", jedis.setex(KEY, 1L, VALUE).get());
        assertArrayEquals(VALUE, jedis.get(KEY).get());
        Thread.sleep(2000);
        assertNull(jedis.get(KEY).get());
    }

    @Test
    public void testPsetex() throws ExecutionException, InterruptedException {
        assertEquals("OK", jedis.psetex(KEY, 1000L, VALUE).get());
        assertArrayEquals(VALUE, jedis.get(KEY).get());
        Thread.sleep(2000);
        assertNull(jedis.get(KEY).get());
    }

    @Test
    public void testMget() throws ExecutionException, InterruptedException {
        jedis.set(KEY, VALUE).get();
        jedis.set(OTHER_KEY, OTHER_VALUE).get();
        List<byte[]> values = jedis.mget(KEY, OTHER_KEY, "nonexistent".getBytes()).get();
        assertEquals(3, values.size());
        assertArrayEquals(VALUE, values.get(0));
        assertArrayEquals(OTHER_VALUE, values.get(1));
        assertNull(values.get(2));
    }

    @Test
    public void testMset() throws ExecutionException, InterruptedException {
        assertEquals("OK", jedis.mset(KEY, VALUE, OTHER_KEY, OTHER_VALUE).get());
        assertArrayEquals(VALUE, jedis.get(KEY).get());
        assertArrayEquals(OTHER_VALUE, jedis.get(OTHER_KEY).get());
    }

    @Test
    public void testMsetnx() throws ExecutionException, InterruptedException {
        assertEquals(1L, jedis.msetnx(KEY, VALUE, OTHER_KEY, OTHER_VALUE).get().longValue());
        assertEquals(0L, jedis.msetnx(KEY, OTHER_VALUE, "newkey".getBytes(), "newvalue".getBytes()).get().longValue());
        assertArrayEquals(VALUE, jedis.get(KEY).get());
        assertArrayEquals(OTHER_VALUE, jedis.get(OTHER_KEY).get());
        assertNull(jedis.get("newkey".getBytes()).get());
    }

    @Test
    public void testIncr() throws ExecutionException, InterruptedException {
        assertEquals(1L, jedis.incr(KEY).get().longValue());
        assertEquals(2L, jedis.incr(KEY).get().longValue());
    }

    @Test
    public void testIncrBy() throws ExecutionException, InterruptedException {
        assertEquals(5L, jedis.incrBy(KEY, 5).get().longValue());
        assertEquals(15L, jedis.incrBy(KEY, 10).get().longValue());
    }

    @Test
    public void testIncrByFloat() throws ExecutionException, InterruptedException {
        assertEquals(5.5, jedis.incrByFloat(KEY, 5.5).get(), 0.0001);
        assertEquals(10.7, jedis.incrByFloat(KEY, 5.2).get(), 0.0001);
    }

    @Test
    public void testDecr() throws ExecutionException, InterruptedException {
        assertEquals(-1L, jedis.decr(KEY).get().longValue());
        assertEquals(-2L, jedis.decr(KEY).get().longValue());
    }

    @Test
    public void testDecrBy() throws ExecutionException, InterruptedException {
        assertEquals(-5L, jedis.decrBy(KEY, 5).get().longValue());
        assertEquals(-15L, jedis.decrBy(KEY, 10).get().longValue());
    }

    @Test
    public void testAppend() throws ExecutionException, InterruptedException {
        assertEquals(VALUE.length, jedis.append(KEY, VALUE).get().longValue());
        assertEquals(VALUE.length + OTHER_VALUE.length, jedis.append(KEY, OTHER_VALUE).get().longValue());
        byte[] expected = new byte[VALUE.length + OTHER_VALUE.length];
        System.arraycopy(VALUE, 0, expected, 0, VALUE.length);
        System.arraycopy(OTHER_VALUE, 0, expected, VALUE.length, OTHER_VALUE.length);
        assertArrayEquals(expected, jedis.get(KEY).get());
    }

    @Test
    public void testSubstr() throws ExecutionException, InterruptedException {
        assertArrayEquals("".getBytes(), jedis.substr(KEY, 0, -1).get());
        jedis.set(KEY, VALUE).get();
        assertArrayEquals(VALUE, jedis.substr(KEY, 0, -1).get());
        assertArrayEquals("val".getBytes(), jedis.substr(KEY, 0, 2).get());
    }

    @Test
    public void testStrlen() throws ExecutionException, InterruptedException {
        assertEquals(0L, jedis.strlen(KEY).get().longValue());
        jedis.set(KEY, VALUE).get();
        assertEquals(VALUE.length, jedis.strlen(KEY).get().longValue());
    }

    @Test
    public void testLcs() throws ExecutionException, InterruptedException {
        jedis.set(KEY, "ansi".getBytes()).get();
        jedis.set(OTHER_KEY, "anti".getBytes()).get();

        LCSParams params = LCSParams.LCSParams().len();
        LCSMatchResult result = jedis.lcs(KEY, OTHER_KEY, params).get();
        assertEquals(3, result.getLen());
    }
} 