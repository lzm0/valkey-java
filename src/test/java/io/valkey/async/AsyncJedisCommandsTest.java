package io.valkey.async;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

public class AsyncJedisCommandsTest extends AsyncJedisBasicTest {

    private static final String KEY = "mykey";
    private static final String VALUE = "myvalue";
    private static final String FIELD = "myfield";

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

        String value = jedis.get(KEY).get();
        assertEquals(VALUE, value);
    }

    @Test
    public void testSetWithExpire() throws ExecutionException, InterruptedException {
        String status = jedis.setex(KEY, 2, VALUE).get();
        assertEquals("OK", status);

        String value = jedis.get(KEY).get();
        assertEquals(VALUE, value);

        Thread.sleep(2100); // Wait for expiration
        value = jedis.get(KEY).get();
        assertNull(value);
    }

    @Test
    public void testIncr() throws ExecutionException, InterruptedException {
        jedis.set(KEY, "1").get();
        Long value = jedis.incr(KEY).get();
        assertEquals(Long.valueOf(2), value);

        value = jedis.incrBy(KEY, 5).get();
        assertEquals(Long.valueOf(7), value);
    }

    @Test
    public void testDecr() throws ExecutionException, InterruptedException {
        jedis.set(KEY, "10").get();
        Long value = jedis.decr(KEY).get();
        assertEquals(Long.valueOf(9), value);

        value = jedis.decrBy(KEY, 5).get();
        assertEquals(Long.valueOf(4), value);
    }

    @Test
    public void testAppend() throws ExecutionException, InterruptedException {
        jedis.set(KEY, "Hello").get();
        Long length = jedis.append(KEY, " World").get();
        assertEquals(Long.valueOf(11), length);

        String value = jedis.get(KEY).get();
        assertEquals("Hello World", value);
    }

    @Test
    public void testStrlen() throws ExecutionException, InterruptedException {
        jedis.set(KEY, "Hello World").get();
        Long length = jedis.strlen(KEY).get();
        assertEquals(Long.valueOf(11), length);
    }

    @Test
    public void testMset() throws ExecutionException, InterruptedException {
        String status = jedis.mset("key1", "value1", "key2", "value2").get();
        assertEquals("OK", status);

        List<String> values = jedis.mget("key1", "key2").get();
        assertEquals(2, values.size());
        assertEquals("value1", values.get(0));
        assertEquals("value2", values.get(1));
    }

    @Test
    public void testDel() throws ExecutionException, InterruptedException {
        jedis.set(KEY, VALUE).get();
        Long status = jedis.del(KEY).get();
        assertEquals(Long.valueOf(1), status);

        String value = jedis.get(KEY).get();
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
        String value = jedis.get(KEY).get();
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
        jedis.lpush(KEY, "value").get();
        type = jedis.type(KEY).get();
        assertEquals("list", type);
    }

    @Test
    public void testSetnx() throws ExecutionException, InterruptedException {
        Long status = jedis.setnx(KEY, VALUE).get();
        assertEquals(Long.valueOf(1), status);

        status = jedis.setnx(KEY, "newvalue").get();
        assertEquals(Long.valueOf(0), status);

        String value = jedis.get(KEY).get();
        assertEquals(VALUE, value);
    }

    @Test
    public void testGetSet() throws ExecutionException, InterruptedException {
        jedis.set(KEY, VALUE).get();
        String oldValue = jedis.getSet(KEY, "newvalue").get();
        assertEquals(VALUE, oldValue);

        String newValue = jedis.get(KEY).get();
        assertEquals("newvalue", newValue);
    }

    @Test
    public void testGetRange() throws ExecutionException, InterruptedException {
        jedis.set(KEY, "Hello World").get();
        String value = jedis.getrange(KEY, 6, 10).get();
        assertEquals("World", value);

        value = jedis.getrange(KEY, 0, -1).get();
        assertEquals("Hello World", value);
    }

    @Test
    public void testSetRange() throws ExecutionException, InterruptedException {
        jedis.set(KEY, "Hello World").get();
        Long length = jedis.setrange(KEY, 6, "Redis").get();
        assertEquals(Long.valueOf(11), length);

        String value = jedis.get(KEY).get();
        assertEquals("Hello Redis", value);
    }

    @Test
    public void testMsetnx() throws ExecutionException, InterruptedException {
        Long status = jedis.msetnx("key1", "value1", "key2", "value2").get();
        assertEquals(Long.valueOf(1), status);

        status = jedis.msetnx("key2", "value2", "key3", "value3").get();
        assertEquals(Long.valueOf(0), status);

        List<String> values = jedis.mget("key1", "key2", "key3").get();
        assertEquals(3, values.size());
        assertEquals("value1", values.get(0));
        assertEquals("value2", values.get(1));
        assertNull(values.get(2));
    }

    @Test
    public void testIncrByFloat() throws ExecutionException, InterruptedException {
        jedis.set(KEY, "1.5").get();
        Double value = jedis.incrByFloat(KEY, 2.3).get();
        assertEquals(3.8, value, 0.0001);

        String strValue = jedis.get(KEY).get();
        double storedValue = Double.parseDouble(strValue);
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