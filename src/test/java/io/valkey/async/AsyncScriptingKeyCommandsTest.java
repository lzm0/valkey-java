package io.valkey.async;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

public class AsyncScriptingKeyCommandsTest extends AsyncJedisBasicTest {

    private static final String KEY = "mykey";
    private static final String VALUE = "myvalue";

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        jedis.del(KEY).get();
    }

    @Test
    public void testEval() throws ExecutionException, InterruptedException {
        String script = "return {KEYS[1],KEYS[2],ARGV[1],ARGV[2]}";
        List<String> keys = new ArrayList<>();
        keys.add("key1");
        keys.add("key2");
        List<String> args = new ArrayList<>();
        args.add("first");
        args.add("second");

        @SuppressWarnings("unchecked")
        List<String> result = (List<String>) jedis.eval(script, keys, args).get();
        assertEquals(4, result.size());
        assertEquals("key1", result.get(0));
        assertEquals("key2", result.get(1));
        assertEquals("first", result.get(2));
        assertEquals("second", result.get(3));
    }

    @Test
    public void testEvalWithoutKeys() throws ExecutionException, InterruptedException {
        String script = "return 'hello'";
        Object result = jedis.eval(script, Collections.emptyList(), Collections.emptyList()).get();
        assertEquals("hello", result);
    }

    @Test
    public void testEvalsha() throws ExecutionException, InterruptedException {
        String script = "return {KEYS[1],ARGV[1]}";
        String sha = jedis.scriptLoad(script, KEY).get();
        
        List<String> keys = Collections.singletonList("key1");
        List<String> args = Collections.singletonList("first");

        @SuppressWarnings("unchecked")
        List<String> result = (List<String>) jedis.evalsha(sha, keys, args).get();
        assertEquals(2, result.size());
        assertEquals("key1", result.get(0));
        assertEquals("first", result.get(1));
    }

    @Test
    public void testScriptExists() throws ExecutionException, InterruptedException {
        String script = "return 'hello'";
        String sha = jedis.scriptLoad(script, KEY).get();
        
        Boolean exists = jedis.scriptExists(sha, KEY).get();
        assertTrue(exists);

        exists = jedis.scriptExists("nonexistentsha", KEY).get();
        assertFalse(exists);
    }

    @Test
    public void testScriptFlush() throws ExecutionException, InterruptedException {
        String script = "return 'hello'";
        String sha = jedis.scriptLoad(script, KEY).get();
        
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
        String script = "return 10";
        Object result = jedis.eval(script, Collections.emptyList(), Collections.emptyList()).get();
        assertEquals(Long.valueOf(10), result);
    }

    @Test
    public void testEvalReturnError() throws ExecutionException, InterruptedException {
        String script = "return redis.error_reply('error message')";
        try {
            jedis.eval(script, Collections.emptyList(), Collections.emptyList()).get();
            fail("Should throw exception");
        } catch (ExecutionException e) {
            assertTrue(e.getCause().getMessage().contains("error message"));
        }
    }

    @Test
    public void testEvalModifyKeyValue() throws ExecutionException, InterruptedException {
        String script = "redis.call('set', KEYS[1], ARGV[1]); return redis.call('get', KEYS[1])";
        List<String> keys = Collections.singletonList(KEY);
        List<String> args = Collections.singletonList(VALUE);

        Object result = jedis.eval(script, keys, args).get();
        assertEquals(VALUE, result);
        assertEquals(VALUE, jedis.get(KEY).get());
    }

    @Test
    public void testEvalCalculation() throws ExecutionException, InterruptedException {
        String script = "local a = tonumber(ARGV[1]); local b = tonumber(ARGV[2]); return a + b";
        List<String> args = new ArrayList<>();
        args.add("5");
        args.add("3");

        Object result = jedis.eval(script, Collections.emptyList(), args).get();
        assertEquals(Long.valueOf(8), result);
    }
} 