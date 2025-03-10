package io.valkey.async;

import io.valkey.args.ListPosition;
import io.valkey.util.SafeEncoder;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

public class AsyncListBinaryCommandsTest extends AsyncJedisBasicTest {

    private static final byte[] KEY = "mylist".getBytes();
    private static final byte[] VALUE = "value".getBytes();

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        jedis.del(KEY).get();
    }

    @Test
    public void testLpush() throws ExecutionException, InterruptedException {
        byte[] a = "a".getBytes();
        byte[] b = "b".getBytes();
        byte[] c = "c".getBytes();

        Long size = jedis.lpush(KEY, c, b, a).get();
        assertEquals(Long.valueOf(3), size);

        List<byte[]> list = jedis.lrange(KEY, 0, -1).get();
        assertEquals(3, list.size());
        assertArrayEquals(a, list.get(0));
        assertArrayEquals(b, list.get(1));
        assertArrayEquals(c, list.get(2));
    }

    @Test
    public void testRpush() throws ExecutionException, InterruptedException {
        byte[] a = "a".getBytes();
        byte[] b = "b".getBytes();
        byte[] c = "c".getBytes();

        Long size = jedis.rpush(KEY, a, b, c).get();
        assertEquals(Long.valueOf(3), size);

        List<byte[]> list = jedis.lrange(KEY, 0, -1).get();
        assertEquals(3, list.size());
        assertArrayEquals(a, list.get(0));
        assertArrayEquals(b, list.get(1));
        assertArrayEquals(c, list.get(2));
    }

    @Test
    public void testLpushx() throws ExecutionException, InterruptedException {
        Long size = jedis.lpushx(KEY, VALUE).get();
        assertEquals(Long.valueOf(0), size);

        byte[] first = "first".getBytes();
        jedis.lpush(KEY, first).get();
        size = jedis.lpushx(KEY, VALUE).get();
        assertEquals(Long.valueOf(2), size);

        List<byte[]> list = jedis.lrange(KEY, 0, -1).get();
        assertEquals(2, list.size());
        assertArrayEquals(VALUE, list.get(0));
        assertArrayEquals(first, list.get(1));
    }

    @Test
    public void testRpushx() throws ExecutionException, InterruptedException {
        Long size = jedis.rpushx(KEY, VALUE).get();
        assertEquals(Long.valueOf(0), size);

        byte[] first = "first".getBytes();
        jedis.rpush(KEY, first).get();
        size = jedis.rpushx(KEY, VALUE).get();
        assertEquals(Long.valueOf(2), size);

        List<byte[]> list = jedis.lrange(KEY, 0, -1).get();
        assertEquals(2, list.size());
        assertArrayEquals(first, list.get(0));
        assertArrayEquals(VALUE, list.get(1));
    }

    @Test
    public void testLlen() throws ExecutionException, InterruptedException {
        jedis.rpush(KEY, "a".getBytes(), "b".getBytes(), "c".getBytes()).get();
        Long size = jedis.llen(KEY).get();
        assertEquals(Long.valueOf(3), size);
    }

    @Test
    public void testLindex() throws ExecutionException, InterruptedException {
        byte[] a = "a".getBytes();
        byte[] b = "b".getBytes();
        byte[] c = "c".getBytes();

        jedis.rpush(KEY, a, b, c).get();
        byte[] element = jedis.lindex(KEY, 1).get();
        assertArrayEquals(b, element);

        element = jedis.lindex(KEY, -1).get();
        assertArrayEquals(c, element);

        element = jedis.lindex(KEY, 100).get();
        assertNull(element);
    }

    @Test
    public void testLset() throws ExecutionException, InterruptedException {
        byte[] a = "a".getBytes();
        byte[] b = "b".getBytes();
        byte[] c = "c".getBytes();
        byte[] newvalue = "newvalue".getBytes();

        jedis.rpush(KEY, a, b, c).get();
        String status = jedis.lset(KEY, 1, newvalue).get();
        assertEquals("OK", status);

        List<byte[]> list = jedis.lrange(KEY, 0, -1).get();
        assertEquals(3, list.size());
        assertArrayEquals(a, list.get(0));
        assertArrayEquals(newvalue, list.get(1));
        assertArrayEquals(c, list.get(2));
    }

    @Test
    public void testLpop() throws ExecutionException, InterruptedException {
        byte[] a = "a".getBytes();
        byte[] b = "b".getBytes();
        byte[] c = "c".getBytes();

        jedis.rpush(KEY, a, b, c).get();
        byte[] element = jedis.lpop(KEY).get();
        assertArrayEquals(a, element);

        List<byte[]> list = jedis.lrange(KEY, 0, -1).get();
        assertEquals(2, list.size());
        assertArrayEquals(b, list.get(0));
        assertArrayEquals(c, list.get(1));
    }

    @Test
    public void testRpop() throws ExecutionException, InterruptedException {
        byte[] a = "a".getBytes();
        byte[] b = "b".getBytes();
        byte[] c = "c".getBytes();

        jedis.rpush(KEY, a, b, c).get();
        byte[] element = jedis.rpop(KEY).get();
        assertArrayEquals(c, element);

        List<byte[]> list = jedis.lrange(KEY, 0, -1).get();
        assertEquals(2, list.size());
        assertArrayEquals(a, list.get(0));
        assertArrayEquals(b, list.get(1));
    }

    @Test
    public void testLtrim() throws ExecutionException, InterruptedException {
        byte[] a = "a".getBytes();
        byte[] b = "b".getBytes();
        byte[] c = "c".getBytes();
        byte[] d = "d".getBytes();
        byte[] e = "e".getBytes();

        jedis.rpush(KEY, a, b, c, d, e).get();
        String status = jedis.ltrim(KEY, 1, 3).get();
        assertEquals("OK", status);

        List<byte[]> list = jedis.lrange(KEY, 0, -1).get();
        assertEquals(3, list.size());
        assertArrayEquals(b, list.get(0));
        assertArrayEquals(c, list.get(1));
        assertArrayEquals(d, list.get(2));
    }

    @Test
    public void testLrem() throws ExecutionException, InterruptedException {
        byte[] a = "a".getBytes();
        byte[] b = "b".getBytes();
        byte[] c = "c".getBytes();
        byte[] d = "d".getBytes();

        jedis.rpush(KEY, a, b, a, c, a, d).get();
        Long count = jedis.lrem(KEY, 2, a).get();
        assertEquals(Long.valueOf(2), count);

        List<byte[]> list = jedis.lrange(KEY, 0, -1).get();
        assertEquals(4, list.size());
        assertArrayEquals(b, list.get(0));
        assertArrayEquals(c, list.get(1));
        assertArrayEquals(a, list.get(2));
        assertArrayEquals(d, list.get(3));
    }

    @Test
    public void testLinsert() throws ExecutionException, InterruptedException {
        byte[] a = "a".getBytes();
        byte[] b = "b".getBytes();
        byte[] c = "c".getBytes();
        byte[] newvalue = "newvalue".getBytes();

        jedis.rpush(KEY, a, b, c).get();
        Long size = jedis.linsert(KEY, ListPosition.BEFORE, b, newvalue).get();
        assertEquals(Long.valueOf(4), size);

        List<byte[]> list = jedis.lrange(KEY, 0, -1).get();
        assertEquals(4, list.size());
        assertArrayEquals(a, list.get(0));
        assertArrayEquals(newvalue, list.get(1));
        assertArrayEquals(b, list.get(2));
        assertArrayEquals(c, list.get(3));
    }
} 