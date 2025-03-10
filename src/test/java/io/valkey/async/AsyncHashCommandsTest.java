package io.valkey.async;

import io.valkey.resps.ScanResult;
import io.valkey.util.SafeEncoder;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

public class AsyncHashCommandsTest extends AsyncJedisBasicTest {

    private static final String KEY = "myhash";
    private static final String FIELD = "field";
    private static final String VALUE = "value";

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

        String value = jedis.hget(KEY, FIELD).get();
        assertEquals(VALUE, value);
    }

    @Test
    public void testHsetWithMap() throws ExecutionException, InterruptedException {
        Map<String, String> hash = new HashMap<>();
        hash.put("field1", "value1");
        hash.put("field2", "value2");

        String status = jedis.hmset(KEY, hash).get();
        assertEquals("OK", status);

        List<String> values = jedis.hmget(KEY, "field1", "field2").get();
        assertEquals(2, values.size());
        assertEquals("value1", values.get(0));
        assertEquals("value2", values.get(1));
    }

    @Test
    public void testHsetnx() throws ExecutionException, InterruptedException {
        Long status = jedis.hsetnx(KEY, FIELD, VALUE).get();
        assertEquals(Long.valueOf(1), status);

        status = jedis.hsetnx(KEY, FIELD, "newValue").get();
        assertEquals(Long.valueOf(0), status);

        String value = jedis.hget(KEY, FIELD).get();
        assertEquals(VALUE, value);
    }

    @Test
    public void testHdel() throws ExecutionException, InterruptedException {
        jedis.hset(KEY, FIELD, VALUE).get();

        Long status = jedis.hdel(KEY, FIELD).get();
        assertEquals(Long.valueOf(1), status);

        String value = jedis.hget(KEY, FIELD).get();
        assertNull(value);
    }

    @Test
    public void testHlen() throws ExecutionException, InterruptedException {
        Map<String, String> hash = new HashMap<>();
        hash.put("field1", "value1");
        hash.put("field2", "value2");

        jedis.hmset(KEY, hash).get();

        Long length = jedis.hlen(KEY).get();
        assertEquals(Long.valueOf(2), length);
    }

    @Test
    public void testHstrlen() throws ExecutionException, InterruptedException {
        jedis.hset(KEY, FIELD, VALUE).get();

        Long length = jedis.hstrlen(KEY, FIELD).get();
        assertEquals(Long.valueOf(VALUE.length()), length);
    }

    @Test
    public void testHexists() throws ExecutionException, InterruptedException {
        jedis.hset(KEY, FIELD, VALUE).get();

        Boolean exists = jedis.hexists(KEY, FIELD).get();
        assertTrue(exists);

        exists = jedis.hexists(KEY, "nonexistent").get();
        assertFalse(exists);
    }

    @Test
    public void testHkeys() throws ExecutionException, InterruptedException {
        Map<String, String> hash = new HashMap<>();
        hash.put("field1", "value1");
        hash.put("field2", "value2");

        jedis.hmset(KEY, hash).get();

        Set<String> keySet = jedis.hkeys(KEY).get();
        List<String> keys = new ArrayList<>(keySet);
        assertEquals(2, keys.size());
        assertTrue(keys.contains("field1"));
        assertTrue(keys.contains("field2"));
    }

    @Test
    public void testHvals() throws ExecutionException, InterruptedException {
        Map<String, String> hash = new HashMap<>();
        hash.put("field1", "value1");
        hash.put("field2", "value2");

        jedis.hmset(KEY, hash).get();

        List<String> values = jedis.hvals(KEY).get();
        assertEquals(2, values.size());
        assertTrue(values.contains("value1"));
        assertTrue(values.contains("value2"));
    }

    @Test
    public void testHgetAll() throws ExecutionException, InterruptedException {
        Map<String, String> hash = new HashMap<>();
        hash.put("field1", "value1");
        hash.put("field2", "value2");

        jedis.hmset(KEY, hash).get();

        Map<String, String> result = jedis.hgetAll(KEY).get();
        assertEquals(hash.size(), result.size());
        assertEquals(hash, result);
    }

    @Test
    public void testHincrBy() throws ExecutionException, InterruptedException {
        jedis.hset(KEY, FIELD, "1").get();

        Long value = jedis.hincrBy(KEY, FIELD, 2).get();
        assertEquals(Long.valueOf(3), value);

        String strValue = jedis.hget(KEY, FIELD).get();
        assertEquals("3", strValue);
    }

    @Test
    public void testHincrByFloat() throws ExecutionException, InterruptedException {
        jedis.hset(KEY, FIELD, "1.5").get();

        Double value = jedis.hincrByFloat(KEY, FIELD, 2.3).get();
        assertEquals(3.8, value, 0.0001);

        String strValue = jedis.hget(KEY, FIELD).get();
        double storedValue = Double.parseDouble(strValue);
        assertEquals(3.8, storedValue, 0.0001);
    }

    @Test
    public void testHscan() throws ExecutionException, InterruptedException {
        Map<String, String> hash = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            hash.put("field" + i, "value" + i);
        }

        jedis.hmset(KEY, hash).get();

        String cursor = "0";
        int count = 0;
        do {
            ScanResult<Entry<String, String>> scanResult = jedis.hscan(KEY, cursor).get();
            cursor = scanResult.getCursor();
            count += scanResult.getResult().size();
        } while (!"0".equals(cursor));

        assertEquals(10, count);
    }
} 