package io.valkey.async;

import io.valkey.util.SafeEncoder;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

public class AsyncJedisBinaryCommandsTest extends AsyncJedisBasicTest {

    private static final byte[] KEY = "mykey".getBytes();
    private static final byte[] VALUE = "myvalue".getBytes();
    private static final byte[] FIELD = "myfield".getBytes();

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        jedis.del(KEY).get();
    }

    @Test
    public void testSet() throws ExecutionException, InterruptedException {
        String status = jedis.set(KEY, VALUE).get();
        assertEquals("OK", status);

        byte[] value = jedis.get(KEY).get();
        assertArrayEquals(VALUE, value);
    }

    @Test
    public void testSetWithExpire() throws ExecutionException, InterruptedException {
        String status = jedis.setex(KEY, 2, VALUE).get();
        assertEquals("OK", status);

        byte[] value = jedis.get(KEY).get();
        assertArrayEquals(VALUE, value);

        Thread.sleep(2100); // Wait for expiration
        value = jedis.get(KEY).get();
        assertNull(value);
    }

    @Test
    public void testIncr() throws ExecutionException, InterruptedException {
        jedis.set(KEY, "1".getBytes()).get();
        Long value = jedis.incr(KEY).get();
        assertEquals(Long.valueOf(2), value);

        value = jedis.incrBy(KEY, 5).get();
        assertEquals(Long.valueOf(7), value);
    }

    @Test
    public void testDecr() throws ExecutionException, InterruptedException {
        jedis.set(KEY, "10".getBytes()).get();
        Long value = jedis.decr(KEY).get();
        assertEquals(Long.valueOf(9), value);

        value = jedis.decrBy(KEY, 5).get();
        assertEquals(Long.valueOf(4), value);
    }

    @Test
    public void testAppend() throws ExecutionException, InterruptedException {
        jedis.set(KEY, "Hello".getBytes()).get();
        Long length = jedis.append(KEY, " World".getBytes()).get();
        assertEquals(Long.valueOf(11), length);

        byte[] value = jedis.get(KEY).get();
        assertEquals("Hello World", SafeEncoder.encode(value));
    }

    @Test
    public void testStrlen() throws ExecutionException, InterruptedException {
        jedis.set(KEY, "Hello World".getBytes()).get();
        Long length = jedis.strlen(KEY).get();
        assertEquals(Long.valueOf(11), length);
    }

    @Test
    public void testMset() throws ExecutionException, InterruptedException {
        byte[] key1 = "key1".getBytes();
        byte[] key2 = "key2".getBytes();
        byte[] value1 = "value1".getBytes();
        byte[] value2 = "value2".getBytes();

        String status = jedis.mset(key1, value1, key2, value2).get();
        assertEquals("OK", status);

        List<byte[]> values = jedis.mget(key1, key2).get();
        assertEquals(2, values.size());
        assertArrayEquals(value1, values.get(0));
        assertArrayEquals(value2, values.get(1));
    }

    @Test
    public void testDel() throws ExecutionException, InterruptedException {
        jedis.set(KEY, VALUE).get();
        Long status = jedis.del(KEY).get();
        assertEquals(Long.valueOf(1), status);

        byte[] value = jedis.get(KEY).get();
        assertNull(value);
    }

    @Test
    public void testExists() throws ExecutionException, InterruptedException {
        jedis.set(KEY, VALUE).get();
        Boolean exists = jedis.exists(KEY).get();
        assertTrue(exists);

        jedis.del(KEY).get();
        exists = jedis.exists(KEY).get();
        assertFalse(exists);
    }

    @Test
    public void testExpire() throws ExecutionException, InterruptedException {
        jedis.set(KEY, VALUE).get();
        Long status = jedis.expire(KEY, 2).get();
        assertEquals(Long.valueOf(1), status);

        Thread.sleep(2100); // Wait for expiration
        byte[] value = jedis.get(KEY).get();
        assertNull(value);
    }

    @Test
    public void testTtl() throws ExecutionException, InterruptedException {
        jedis.set(KEY, VALUE).get();
        jedis.expire(KEY, 2).get();

        Long ttl = jedis.ttl(KEY).get();
        assertTrue(ttl > 0 && ttl <= 2);
    }

    @Test
    public void testPersist() throws ExecutionException, InterruptedException {
        jedis.setex(KEY, 2, VALUE).get();
        Long status = jedis.persist(KEY).get();
        assertEquals(Long.valueOf(1), status);

        Long ttl = jedis.ttl(KEY).get();
        assertEquals(Long.valueOf(-1), ttl);
    }

    @Test
    public void testType() throws ExecutionException, InterruptedException {
        jedis.set(KEY, VALUE).get();
        String type = jedis.type(KEY).get();
        assertEquals("string", type);

        jedis.del(KEY).get();
        jedis.lpush(KEY, "value".getBytes()).get();
        type = jedis.type(KEY).get();
        assertEquals("list", type);
    }

    @Test
    public void testSetnx() throws ExecutionException, InterruptedException {
        Long status = jedis.setnx(KEY, VALUE).get();
        assertEquals(Long.valueOf(1), status);

        status = jedis.setnx(KEY, "newvalue".getBytes()).get();
        assertEquals(Long.valueOf(0), status);

        byte[] value = jedis.get(KEY).get();
        assertArrayEquals(VALUE, value);
    }

    @Test
    public void testGetSet() throws ExecutionException, InterruptedException {
        jedis.set(KEY, VALUE).get();
        byte[] oldValue = jedis.getSet(KEY, "newvalue".getBytes()).get();
        assertArrayEquals(VALUE, oldValue);

        byte[] newValue = jedis.get(KEY).get();
        assertEquals("newvalue", SafeEncoder.encode(newValue));
    }

    @Test
    public void testGetRange() throws ExecutionException, InterruptedException {
        jedis.set(KEY, "Hello World".getBytes()).get();
        byte[] value = jedis.getrange(KEY, 6, 10).get();
        assertEquals("World", SafeEncoder.encode(value));

        value = jedis.getrange(KEY, 0, -1).get();
        assertEquals("Hello World", SafeEncoder.encode(value));
    }

    @Test
    public void testSetRange() throws ExecutionException, InterruptedException {
        jedis.set(KEY, "Hello World".getBytes()).get();
        Long length = jedis.setrange(KEY, 6, "Redis".getBytes()).get();
        assertEquals(Long.valueOf(11), length);

        byte[] value = jedis.get(KEY).get();
        assertEquals("Hello Redis", SafeEncoder.encode(value));
    }

    @Test
    public void testMsetnx() throws ExecutionException, InterruptedException {
        byte[] key1 = "key1".getBytes();
        byte[] key2 = "key2".getBytes();
        byte[] key3 = "key3".getBytes();
        byte[] value1 = "value1".getBytes();
        byte[] value2 = "value2".getBytes();
        byte[] value3 = "value3".getBytes();

        Long status = jedis.msetnx(key1, value1, key2, value2).get();
        assertEquals(Long.valueOf(1), status);

        status = jedis.msetnx(key2, value2, key3, value3).get();
        assertEquals(Long.valueOf(0), status);

        List<byte[]> values = jedis.mget(key1, key2, key3).get();
        assertEquals(3, values.size());
        assertArrayEquals(value1, values.get(0));
        assertArrayEquals(value2, values.get(1));
        assertNull(values.get(2));
    }

    @Test
    public void testIncrByFloat() throws ExecutionException, InterruptedException {
        jedis.set(KEY, "1.5".getBytes()).get();
        Double value = jedis.incrByFloat(KEY, 2.3).get();
        assertEquals(3.8, value, 0.0001);

        byte[] byteValue = jedis.get(KEY).get();
        double storedValue = Double.parseDouble(SafeEncoder.encode(byteValue));
        assertEquals(3.8, storedValue, 0.0001);
    }

    @Test
    public void testSetbit() throws ExecutionException, InterruptedException {
        Boolean status = jedis.setbit(KEY, 7, true).get();
        assertFalse(status);

        Boolean value = jedis.getbit(KEY, 7).get();
        assertTrue(value);
    }

    @Test
    public void testBitcount() throws ExecutionException, InterruptedException {
        jedis.setbit(KEY, 7, true).get();
        jedis.setbit(KEY, 15, true).get();
        Long count = jedis.bitcount(KEY).get();
        assertEquals(Long.valueOf(2), count);

        count = jedis.bitcount(KEY, 0, 0).get();
        assertEquals(Long.valueOf(1), count);
    }
} 