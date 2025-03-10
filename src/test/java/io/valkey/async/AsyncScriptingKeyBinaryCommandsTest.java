package io.valkey.async;

import io.valkey.util.SafeEncoder;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

public class AsyncScriptingKeyBinaryCommandsTest extends AsyncJedisBasicTest {

    private static final byte[] KEY = "mykey".getBytes();
    private static final byte[] VALUE = "myvalue".getBytes();

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        jedis.del(KEY).get();
    }

    @Test
    public void testEval() throws ExecutionException, InterruptedException {
        byte[] script = "return {KEYS[1],KEYS[2],ARGV[1],ARGV[2]}".getBytes();
        List<byte[]> keys = new ArrayList<>();
        keys.add("key1".getBytes());
        keys.add("key2".getBytes());
        List<byte[]> args = new ArrayList<>();
        args.add("first".getBytes());
        args.add("second".getBytes());

        @SuppressWarnings("unchecked")
        List<byte[]> result = (List<byte[]>) jedis.eval(script, keys, args).get();
        assertEquals(4, result.size());
        assertArrayEquals("key1".getBytes(), result.get(0));
        assertArrayEquals("key2".getBytes(), result.get(1));
        assertArrayEquals("first".getBytes(), result.get(2));
        assertArrayEquals("second".getBytes(), result.get(3));
    }

    @Test
    public void testEvalWithoutKeys() throws ExecutionException, InterruptedException {
        byte[] script = "return 'hello'".getBytes();
        Object result = jedis.eval(script, Collections.emptyList(), Collections.emptyList()).get();
        assertArrayEquals("hello".getBytes(), (byte[]) result);
    }

    @Test
    public void testEvalsha() throws ExecutionException, InterruptedException {
        byte[] script = "return {KEYS[1],ARGV[1]}".getBytes();
        byte[] sha = jedis.scriptLoad(script, KEY).get();
        
        List<byte[]> keys = Collections.singletonList("key1".getBytes());
        List<byte[]> args = Collections.singletonList("first".getBytes());

        @SuppressWarnings("unchecked")
        List<byte[]> result = (List<byte[]>) jedis.evalsha(sha, keys, args).get();
        assertEquals(2, result.size());
        assertArrayEquals("key1".getBytes(), result.get(0));
        assertArrayEquals("first".getBytes(), result.get(1));
    }

    @Test
    public void testScriptExists() throws ExecutionException, InterruptedException {
        byte[] script = "return 'hello'".getBytes();
        byte[] sha = jedis.scriptLoad(script, KEY).get();
        
        Boolean exists = jedis.scriptExists(sha, KEY).get();
        assertTrue(exists);

        exists = jedis.scriptExists("nonexistentsha".getBytes(), KEY).get();
        assertFalse(exists);
    }

    @Test
    public void testScriptFlush() throws ExecutionException, InterruptedException {
        byte[] script = "return 'hello'".getBytes();
        byte[] sha = jedis.scriptLoad(script, KEY).get();
        
        String result = jedis.scriptFlush(KEY).get();
        assertEquals("OK", result);

        Boolean exists = jedis.scriptExists(sha, KEY).get();
        assertFalse(exists);
    }

    @Test
    public void testScriptKill() throws ExecutionException, InterruptedException {
        try {
            jedis.scriptKill(KEY).get();
            fail("Should throw exception if no script is running");
        } catch (ExecutionException e) {
            assertTrue(e.getCause().getMessage().contains("NOTBUSY"));
        }
    }

    @Test
    public void testEvalReturnInteger() throws ExecutionException, InterruptedException {
        byte[] script = "return 10".getBytes();
        Object result = jedis.eval(script, Collections.emptyList(), Collections.emptyList()).get();
        assertEquals(Long.valueOf(10), result);
    }

    @Test
    public void testEvalReturnError() throws ExecutionException, InterruptedException {
        byte[] script = "return redis.error_reply('error message')".getBytes();
        try {
            jedis.eval(script, Collections.emptyList(), Collections.emptyList()).get();
            fail("Should throw exception");
        } catch (ExecutionException e) {
            assertTrue(e.getCause().getMessage().contains("error message"));
        }
    }

    @Test
    public void testEvalModifyKeyValue() throws ExecutionException, InterruptedException {
        byte[] script = "redis.call('set', KEYS[1], ARGV[1]); return redis.call('get', KEYS[1])".getBytes();
        List<byte[]> keys = Collections.singletonList(KEY);
        List<byte[]> args = Collections.singletonList(VALUE);

        Object result = jedis.eval(script, keys, args).get();
        assertArrayEquals(VALUE, (byte[]) result);
        assertArrayEquals(VALUE, jedis.get(KEY).get());
    }

    @Test
    public void testEvalCalculation() throws ExecutionException, InterruptedException {
        byte[] script = "local a = tonumber(ARGV[1]); local b = tonumber(ARGV[2]); return a + b".getBytes();
        List<byte[]> args = new ArrayList<>();
        args.add("5".getBytes());
        args.add("3".getBytes());

        Object result = jedis.eval(script, Collections.emptyList(), args).get();
        assertEquals(Long.valueOf(8), result);
    }
} 