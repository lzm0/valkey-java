package io.valkey.async;

import io.valkey.args.ListPosition;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

public class AsyncListCommandsTest extends AsyncJedisBasicTest {

    private static final String KEY = "mylist";
    private static final String VALUE = "value";

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        jedis.del(KEY).get();
    }

    @Test
    public void testLpush() throws ExecutionException, InterruptedException {
        Long size = jedis.lpush(KEY, "c", "b", "a").get();
        assertEquals(Long.valueOf(3), size);

        List<String> list = jedis.lrange(KEY, 0, -1).get();
        assertEquals(3, list.size());
        assertEquals("a", list.get(0));
        assertEquals("b", list.get(1));
        assertEquals("c", list.get(2));
    }

    @Test
    public void testRpush() throws ExecutionException, InterruptedException {
        Long size = jedis.rpush(KEY, "a", "b", "c").get();
        assertEquals(Long.valueOf(3), size);

        List<String> list = jedis.lrange(KEY, 0, -1).get();
        assertEquals(3, list.size());
        assertEquals("a", list.get(0));
        assertEquals("b", list.get(1));
        assertEquals("c", list.get(2));
    }

    @Test
    public void testLpushx() throws ExecutionException, InterruptedException {
        Long size = jedis.lpushx(KEY, VALUE).get();
        assertEquals(Long.valueOf(0), size);

        jedis.lpush(KEY, "first").get();
        size = jedis.lpushx(KEY, VALUE).get();
        assertEquals(Long.valueOf(2), size);

        List<String> list = jedis.lrange(KEY, 0, -1).get();
        assertEquals(2, list.size());
        assertEquals(VALUE, list.get(0));
        assertEquals("first", list.get(1));
    }

    @Test
    public void testRpushx() throws ExecutionException, InterruptedException {
        Long size = jedis.rpushx(KEY, VALUE).get();
        assertEquals(Long.valueOf(0), size);

        jedis.rpush(KEY, "first").get();
        size = jedis.rpushx(KEY, VALUE).get();
        assertEquals(Long.valueOf(2), size);

        List<String> list = jedis.lrange(KEY, 0, -1).get();
        assertEquals(2, list.size());
        assertEquals("first", list.get(0));
        assertEquals(VALUE, list.get(1));
    }

    @Test
    public void testLlen() throws ExecutionException, InterruptedException {
        jedis.rpush(KEY, "a", "b", "c").get();
        Long size = jedis.llen(KEY).get();
        assertEquals(Long.valueOf(3), size);
    }

    @Test
    public void testLindex() throws ExecutionException, InterruptedException {
        jedis.rpush(KEY, "a", "b", "c").get();
        String element = jedis.lindex(KEY, 1).get();
        assertEquals("b", element);

        element = jedis.lindex(KEY, -1).get();
        assertEquals("c", element);

        element = jedis.lindex(KEY, 100).get();
        assertNull(element);
    }

    @Test
    public void testLset() throws ExecutionException, InterruptedException {
        jedis.rpush(KEY, "a", "b", "c").get();
        String status = jedis.lset(KEY, 1, "newvalue").get();
        assertEquals("OK", status);

        List<String> list = jedis.lrange(KEY, 0, -1).get();
        assertEquals(3, list.size());
        assertEquals("a", list.get(0));
        assertEquals("newvalue", list.get(1));
        assertEquals("c", list.get(2));
    }

    @Test
    public void testLpop() throws ExecutionException, InterruptedException {
        jedis.rpush(KEY, "a", "b", "c").get();
        String element = jedis.lpop(KEY).get();
        assertEquals("a", element);

        List<String> list = jedis.lrange(KEY, 0, -1).get();
        assertEquals(2, list.size());
        assertEquals("b", list.get(0));
        assertEquals("c", list.get(1));
    }

    @Test
    public void testRpop() throws ExecutionException, InterruptedException {
        jedis.rpush(KEY, "a", "b", "c").get();
        String element = jedis.rpop(KEY).get();
        assertEquals("c", element);

        List<String> list = jedis.lrange(KEY, 0, -1).get();
        assertEquals(2, list.size());
        assertEquals("a", list.get(0));
        assertEquals("b", list.get(1));
    }

    @Test
    public void testLtrim() throws ExecutionException, InterruptedException {
        jedis.rpush(KEY, "a", "b", "c", "d", "e").get();
        String status = jedis.ltrim(KEY, 1, 3).get();
        assertEquals("OK", status);

        List<String> list = jedis.lrange(KEY, 0, -1).get();
        assertEquals(3, list.size());
        assertEquals("b", list.get(0));
        assertEquals("c", list.get(1));
        assertEquals("d", list.get(2));
    }

    @Test
    public void testLrem() throws ExecutionException, InterruptedException {
        jedis.rpush(KEY, "a", "b", "a", "c", "a", "d").get();
        Long count = jedis.lrem(KEY, 2, "a").get();
        assertEquals(Long.valueOf(2), count);

        List<String> list = jedis.lrange(KEY, 0, -1).get();
        assertEquals(4, list.size());
        assertEquals("b", list.get(0));
        assertEquals("c", list.get(1));
        assertEquals("a", list.get(2));
        assertEquals("d", list.get(3));
    }

    @Test
    public void testLinsert() throws ExecutionException, InterruptedException {
        jedis.rpush(KEY, "a", "b", "c").get();
        Long size = jedis.linsert(KEY, ListPosition.BEFORE, "b", "newvalue").get();
        assertEquals(Long.valueOf(4), size);

        List<String> list = jedis.lrange(KEY, 0, -1).get();
        assertEquals(4, list.size());
        assertEquals("a", list.get(0));
        assertEquals("newvalue", list.get(1));
        assertEquals("b", list.get(2));
        assertEquals("c", list.get(3));
    }
} 