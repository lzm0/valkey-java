package io.valkey.async;

import io.valkey.args.FlushMode;
import io.valkey.args.FunctionRestorePolicy;
import io.valkey.resps.FunctionStats;
import io.valkey.resps.LibraryInfo;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

public class AsyncFunctionCommandsTest extends AsyncJedisBasicTest {

    private static final String LUA_SCRIPT = "#!lua name=mylib\n" +
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
            "})";

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
        List<String> keys = Collections.emptyList();
        List<String> args = Arrays.asList("1", "2", "3");
        Object sum = jedis.fcall("sum", keys, args).get();
        assertEquals(6L, sum);

        // Call readonly function
        sum = jedis.fcallReadonly("readonly_sum", keys, args).get();
        assertEquals(6L, sum);
    }

    @Test
    public void testFunctionList() throws ExecutionException, InterruptedException {
        // Load function first
        jedis.functionLoad(LUA_SCRIPT).get();

        // Test functionList
        List<LibraryInfo> libraries = jedis.functionList().get();
        assertEquals(1, libraries.size());
        
        LibraryInfo lib = libraries.get(0);
        assertEquals("mylib", lib.getLibraryName());
        assertEquals(2, lib.getFunctions().size());
        assertEquals("sum", lib.getFunctions().get(0).get("name"));
        assertEquals("readonly_sum", lib.getFunctions().get(1).get("name"));

        // Test functionList with pattern
        libraries = jedis.functionList("my*").get();
        assertEquals(1, libraries.size());
        assertEquals("mylib", libraries.get(0).getLibraryName());

        // Test functionListWithCode
        libraries = jedis.functionListWithCode().get();
        assertEquals(1, libraries.size());
        assertNotNull(libraries.get(0).getLibraryCode());
        assertTrue(libraries.get(0).getLibraryCode().contains("function"));
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
        assertTrue(jedis.functionList().get().isEmpty());

        // Restore functions
        assertEquals("OK", jedis.functionRestore(dump).get());

        // Verify functions are restored
        List<LibraryInfo> libraries = jedis.functionList().get();
        assertEquals(1, libraries.size());
        assertEquals("mylib", libraries.get(0).getLibraryName());
    }

    @Test
    public void testFunctionDelete() throws ExecutionException, InterruptedException {
        // Load function
        jedis.functionLoad(LUA_SCRIPT).get();

        // Delete function
        assertEquals("OK", jedis.functionDelete("mylib").get());

        // Verify function is gone
        assertTrue(jedis.functionList().get().isEmpty());
    }

    @Test
    public void testFunctionFlushModes() throws ExecutionException, InterruptedException {
        // Load function
        jedis.functionLoad(LUA_SCRIPT).get();

        // Test SYNC mode
        assertEquals("OK", jedis.functionFlush(FlushMode.SYNC).get());
        assertTrue(jedis.functionList().get().isEmpty());

        // Reload function
        jedis.functionLoad(LUA_SCRIPT).get();

        // Test ASYNC mode
        assertEquals("OK", jedis.functionFlush(FlushMode.ASYNC).get());
        assertTrue(jedis.functionList().get().isEmpty());
    }

    @Test
    public void testFunctionStats() throws ExecutionException, InterruptedException {
        // Load function
        jedis.functionLoad(LUA_SCRIPT).get();

        // Get stats
        FunctionStats stats = jedis.functionStats().get();
        assertNotNull(stats);
        // Note: We can't make specific assertions about the stats values
        // as they depend on the Redis server state
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
        List<String> keys = Collections.emptyList();
        List<String> args = Arrays.asList("1", "2", "3");
        Object sum = jedis.fcall("sum", keys, args).get();
        assertEquals(6L, sum);
    }
} 