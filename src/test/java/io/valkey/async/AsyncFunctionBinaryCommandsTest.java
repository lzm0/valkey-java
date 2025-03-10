package io.valkey.async;

import io.valkey.args.FlushMode;
import io.valkey.args.FunctionRestorePolicy;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

public class AsyncFunctionBinaryCommandsTest extends AsyncJedisBasicTest {

    private static final byte[] LUA_SCRIPT = ("#!lua name=mylib\n" +
            "redis.register_function({\n" +
            "    function_name = 'sum',\n" +
            "    callback = function(keys, args)\n" +
            "        local sum = 0\n" +
            "        for _, v in ipairs(args) do\n" +
            "            sum = sum + tonumber(v)\n" +
            "        end\n" +
            "        return sum\n" +
            "    end\n" +
            "})\n" +
            "redis.register_function({\n" +
            "    function_name = 'readonly_sum',\n" +
            "    callback = function(keys, args)\n" +
            "        local sum = 0\n" +
            "        for _, v in ipairs(args) do\n" +
            "            sum = sum + tonumber(v)\n" +
            "        end\n" +
            "        return sum\n" +
            "    end,\n" +
            "    flags = { 'no-writes' }\n" +
            "})").getBytes();

    private static final byte[] LIBRARY_NAME = "mylib".getBytes();
    private static final byte[] FUNCTION_NAME = "sum".getBytes();
    private static final byte[] READONLY_FUNCTION_NAME = "readonly_sum".getBytes();

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        // 清理所有函数
        jedis.functionFlush().get();
    }

    @Test
    public void testFunctionLoadAndCall() throws ExecutionException, InterruptedException {
        // Load function
        String result = jedis.functionLoad(LUA_SCRIPT).get();
        assertEquals("mylib", result);

        // Call function
        List<byte[]> keys = Collections.emptyList();
        List<byte[]> args = Arrays.asList("1".getBytes(), "2".getBytes(), "3".getBytes());
        Object sum = jedis.fcall(FUNCTION_NAME, keys, args).get();
        assertEquals(6L, sum);

        // Call readonly function
        sum = jedis.fcallReadonly(READONLY_FUNCTION_NAME, keys, args).get();
        assertEquals(6L, sum);
    }

    @Test
    public void testFunctionList() throws ExecutionException, InterruptedException {
        // Load function first
        jedis.functionLoad(LUA_SCRIPT).get();

        // Test functionListBinary
        List<Object> libraries = jedis.functionListBinary().get();
        assertEquals(1, libraries.size());

        // Test functionList with pattern
        libraries = jedis.functionList(LIBRARY_NAME).get();
        assertEquals(1, libraries.size());

        // Test functionListWithCodeBinary
        libraries = jedis.functionListWithCodeBinary().get();
        assertEquals(1, libraries.size());

        // Test functionListWithCode with pattern
        libraries = jedis.functionListWithCode(LIBRARY_NAME).get();
        assertEquals(1, libraries.size());
    }

    @Test
    public void testFunctionDump() throws ExecutionException, InterruptedException {
        // Load function
        jedis.functionLoad(LUA_SCRIPT).get();

        // Dump functions
        byte[] dump = jedis.functionDump().get();
        assertNotNull(dump);
        assertTrue(dump.length > 0);

        // Flush all functions
        assertEquals("OK", jedis.functionFlush().get());

        // Verify functions are gone
        assertTrue(jedis.functionListBinary().get().isEmpty());

        // Restore functions
        assertEquals("OK", jedis.functionRestore(dump).get());

        // Verify functions are restored
        List<Object> libraries = jedis.functionListBinary().get();
        assertEquals(1, libraries.size());
    }

    @Test
    public void testFunctionDelete() throws ExecutionException, InterruptedException {
        // Load function
        jedis.functionLoad(LUA_SCRIPT).get();

        // Delete function
        assertEquals("OK", jedis.functionDelete(LIBRARY_NAME).get());

        // Verify function is gone
        assertTrue(jedis.functionListBinary().get().isEmpty());
    }

    @Test
    public void testFunctionFlushModes() throws ExecutionException, InterruptedException {
        // Load function
        jedis.functionLoad(LUA_SCRIPT).get();

        // Test SYNC mode
        assertEquals("OK", jedis.functionFlush(FlushMode.SYNC).get());
        assertTrue(jedis.functionListBinary().get().isEmpty());

        // Reload function
        jedis.functionLoad(LUA_SCRIPT).get();

        // Test ASYNC mode
        assertEquals("OK", jedis.functionFlush(FlushMode.ASYNC).get());
        assertTrue(jedis.functionListBinary().get().isEmpty());
    }

    @Test
    public void testFunctionStats() throws ExecutionException, InterruptedException {
        // Load function
        jedis.functionLoad(LUA_SCRIPT).get();

        // Get stats
        Object stats = jedis.functionStatsBinary().get();
        assertNotNull(stats);
    }

    @Test
    public void testFunctionRestore() throws ExecutionException, InterruptedException {
        // Load initial function
        jedis.functionLoad(LUA_SCRIPT).get();

        // Dump functions
        byte[] dump = jedis.functionDump().get();

        // Test different restore policies
        jedis.functionFlush().get();
        assertEquals("OK", jedis.functionRestore(dump, FunctionRestorePolicy.APPEND).get());

        jedis.functionFlush().get();
        assertEquals("OK", jedis.functionRestore(dump, FunctionRestorePolicy.REPLACE).get());

        jedis.functionFlush().get();
        assertEquals("OK", jedis.functionRestore(dump, FunctionRestorePolicy.FLUSH).get());
    }

    @Test
    public void testFunctionLoadReplace() throws ExecutionException, InterruptedException {
        // Load initial function
        String result = jedis.functionLoad(LUA_SCRIPT).get();
        assertEquals("mylib", result);

        // Replace with same function
        result = jedis.functionLoadReplace(LUA_SCRIPT).get();
        assertEquals("mylib", result);

        // Verify function still works
        List<byte[]> keys = Collections.emptyList();
        List<byte[]> args = Arrays.asList("1".getBytes(), "2".getBytes(), "3".getBytes());
        Object sum = jedis.fcall(FUNCTION_NAME, keys, args).get();
        assertEquals(6L, sum);
    }
} 