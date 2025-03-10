package io.valkey.async;

import io.valkey.resps.ScanResult;
import io.valkey.util.SafeEncoder;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

public class AsyncHashBinaryCommandsTest extends AsyncJedisBasicTest {

    private static final byte[] KEY = "myhash".getBytes();
    private static final byte[] FIELD = "field".getBytes();
    private static final byte[] VALUE = "value".getBytes();

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        jedis.del(KEY).get();
    }

    @Test
    public void testHset() throws ExecutionException, InterruptedException {
        Long status = jedis.hset(KEY, FIELD, VALUE).get();
        assertEquals(Long.valueOf(1), status);

        byte[] value = jedis.hget(KEY, FIELD).get();
        assertArrayEquals(VALUE, value);
    }

    @Test
    public void testHsetWithMap() throws ExecutionException, InterruptedException {
        Map<byte[], byte[]> hash = new HashMap<>();
        hash.put("field1".getBytes(), "value1".getBytes());
        hash.put("field2".getBytes(), "value2".getBytes());

        String status = jedis.hmset(KEY, hash).get();
        assertEquals("OK", status);

        List<byte[]> values = jedis.hmget(KEY, "field1".getBytes(), "field2".getBytes()).get();
        assertEquals(2, values.size());
        assertArrayEquals("value1".getBytes(), values.get(0));
        assertArrayEquals("value2".getBytes(), values.get(1));
    }

    @Test
    public void testHsetnx() throws ExecutionException, InterruptedException {
        Long status = jedis.hsetnx(KEY, FIELD, VALUE).get();
        assertEquals(Long.valueOf(1), status);

        status = jedis.hsetnx(KEY, FIELD, "newValue".getBytes()).get();
        assertEquals(Long.valueOf(0), status);

        byte[] value = jedis.hget(KEY, FIELD).get();
        assertArrayEquals(VALUE, value);
    }

    @Test
    public void testHdel() throws ExecutionException, InterruptedException {
        jedis.hset(KEY, FIELD, VALUE).get();

        Long status = jedis.hdel(KEY, FIELD).get();
        assertEquals(Long.valueOf(1), status);

        byte[] value = jedis.hget(KEY, FIELD).get();
        assertNull(value);
    }

    @Test
    public void testHlen() throws ExecutionException, InterruptedException {
        Map<byte[], byte[]> hash = new HashMap<>();
        hash.put("field1".getBytes(), "value1".getBytes());
        hash.put("field2".getBytes(), "value2".getBytes());

        jedis.hmset(KEY, hash).get();

        Long length = jedis.hlen(KEY).get();
        assertEquals(Long.valueOf(2), length);
    }

    @Test
    public void testHstrlen() throws ExecutionException, InterruptedException {
        jedis.hset(KEY, FIELD, VALUE).get();

        Long length = jedis.hstrlen(KEY, FIELD).get();
        assertEquals(Long.valueOf(VALUE.length), length);
    }

    @Test
    public void testHexists() throws ExecutionException, InterruptedException {
        jedis.hset(KEY, FIELD, VALUE).get();

        Boolean exists = jedis.hexists(KEY, FIELD).get();
        assertTrue(exists);

        exists = jedis.hexists(KEY, "nonexistent".getBytes()).get();
        assertFalse(exists);
    }

    @Test
    public void testHkeys() throws ExecutionException, InterruptedException {
        Map<byte[], byte[]> hash = new HashMap<>();
        hash.put("field1".getBytes(), "value1".getBytes());
        hash.put("field2".getBytes(), "value2".getBytes());

        jedis.hmset(KEY, hash).get();

        Set<byte[]> keySet = jedis.hkeys(KEY).get();
        List<byte[]> keys = new ArrayList<>(keySet);
        assertEquals(2, keys.size());
        assertTrue(containsByteArrayKey(keys, "field1".getBytes()));
        assertTrue(containsByteArrayKey(keys, "field2".getBytes()));
    }

    @Test
    public void testHvals() throws ExecutionException, InterruptedException {
        Map<byte[], byte[]> hash = new HashMap<>();
        hash.put("field1".getBytes(), "value1".getBytes());
        hash.put("field2".getBytes(), "value2".getBytes());

        jedis.hmset(KEY, hash).get();

        List<byte[]> values = jedis.hvals(KEY).get();
        assertEquals(2, values.size());
        assertTrue(containsByteArrayKey(values, "value1".getBytes()));
        assertTrue(containsByteArrayKey(values, "value2".getBytes()));
    }

    @Test
    public void testHgetAll() throws ExecutionException, InterruptedException {
        Map<byte[], byte[]> hash = new HashMap<>();
        hash.put("field1".getBytes(), "value1".getBytes());
        hash.put("field2".getBytes(), "value2".getBytes());

        jedis.hmset(KEY, hash).get();

        Map<byte[], byte[]> result = jedis.hgetAll(KEY).get();
        assertEquals(hash.size(), result.size());
        for (Entry<byte[], byte[]> entry : hash.entrySet()) {
            assertArrayEquals(entry.getValue(), result.get(entry.getKey()));
        }
    }

    @Test
    public void testHincrBy() throws ExecutionException, InterruptedException {
        jedis.hset(KEY, FIELD, "1".getBytes()).get();

        Long value = jedis.hincrBy(KEY, FIELD, 2).get();
        assertEquals(Long.valueOf(3), value);

        byte[] byteValue = jedis.hget(KEY, FIELD).get();
        assertEquals("3", SafeEncoder.encode(byteValue));
    }

    @Test
    public void testHincrByFloat() throws ExecutionException, InterruptedException {
        jedis.hset(KEY, FIELD, "1.5".getBytes()).get();

        Double value = jedis.hincrByFloat(KEY, FIELD, 2.3).get();
        assertEquals(3.8, value, 0.0001);

        byte[] byteValue = jedis.hget(KEY, FIELD).get();
        double storedValue = Double.parseDouble(SafeEncoder.encode(byteValue));
        assertEquals(3.8, storedValue, 0.0001);
    }

    @Test
    public void testHscan() throws ExecutionException, InterruptedException {
        Map<byte[], byte[]> hash = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            hash.put(("field" + i).getBytes(), ("value" + i).getBytes());
        }

        jedis.hmset(KEY, hash).get();

        byte[] cursor = "0".getBytes();
        int count = 0;
        do {
            ScanResult<Entry<byte[], byte[]>> scanResult = jedis.hscan(KEY, cursor).get();
            cursor = scanResult.getCursorAsBytes();
            count += scanResult.getResult().size();
        } while (!java.util.Arrays.equals(cursor, "0".getBytes()));

        assertEquals(10, count);
    }

    private boolean containsByteArrayKey(List<byte[]> list, byte[] key) {
        for (byte[] item : list) {
            if (java.util.Arrays.equals(item, key)) {
                return true;
            }
        }
        return false;
    }
} 