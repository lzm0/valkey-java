package io.valkey.async;

import io.valkey.util.SafeEncoder;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

public class AsyncSampleKeyedBinaryCommandsTest extends AsyncJedisBasicTest {

    private static final byte[] KEY = "myset".getBytes();
    private static final byte[] VALUE = "value".getBytes();

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        jedis.del(KEY).get();
    }

    @Test
    public void testRandomKey() throws ExecutionException, InterruptedException {
        byte[] key1 = "key1".getBytes();
        byte[] key2 = "key2".getBytes();
        byte[] key3 = "key3".getBytes();
        byte[] value1 = "value1".getBytes();
        byte[] value2 = "value2".getBytes();
        byte[] value3 = "value3".getBytes();

        jedis.set(key1, value1).get();
        jedis.set(key2, value2).get();
        jedis.set(key3, value3).get();

        String randomKey = jedis.randomKey().get();
        assertNotNull(randomKey);
        assertTrue(randomKey.startsWith("key"));
        byte[] value = jedis.get(randomKey.getBytes()).get();
        assertNotNull(value);
        assertTrue(SafeEncoder.encode(value).startsWith("value"));
    }

    @Test
    public void testSrandmember() throws ExecutionException, InterruptedException {
        byte[] a = "a".getBytes();
        byte[] b = "b".getBytes();
        byte[] c = "c".getBytes();
        byte[] d = "d".getBytes();
        byte[] e = "e".getBytes();

        jedis.sadd(KEY, a, b, c, d, e).get();

        byte[] member = jedis.srandmember(KEY).get();
        assertNotNull(member);
        assertTrue(jedis.sismember(KEY, member).get());
    }

    @Test
    public void testSrandmemberMulti() throws ExecutionException, InterruptedException {
        byte[] a = "a".getBytes();
        byte[] b = "b".getBytes();
        byte[] c = "c".getBytes();
        byte[] d = "d".getBytes();
        byte[] e = "e".getBytes();

        jedis.sadd(KEY, a, b, c, d, e).get();

        // Test positive count (unique elements)
        List<byte[]> members = jedis.srandmember(KEY, 3).get();
        assertEquals(3, members.size());
        for (byte[] member : members) {
            assertTrue(jedis.sismember(KEY, member).get());
        }

        // Test negative count (may have duplicates)
        members = jedis.srandmember(KEY, -3).get();
        assertEquals(3, members.size());
        for (byte[] member : members) {
            assertTrue(jedis.sismember(KEY, member).get());
        }
    }

    @Test
    public void testSpop() throws ExecutionException, InterruptedException {
        byte[] a = "a".getBytes();
        byte[] b = "b".getBytes();
        byte[] c = "c".getBytes();
        byte[] d = "d".getBytes();
        byte[] e = "e".getBytes();

        jedis.sadd(KEY, a, b, c, d, e).get();

        byte[] member = jedis.spop(KEY).get();
        assertNotNull(member);
        assertFalse(jedis.sismember(KEY, member).get());
        assertEquals(Long.valueOf(4), jedis.scard(KEY).get());
    }

    @Test
    public void testSpopMulti() throws ExecutionException, InterruptedException {
        byte[] a = "a".getBytes();
        byte[] b = "b".getBytes();
        byte[] c = "c".getBytes();
        byte[] d = "d".getBytes();
        byte[] e = "e".getBytes();

        jedis.sadd(KEY, a, b, c, d, e).get();

        Set<byte[]> members = jedis.spop(KEY, 3).get();
        assertEquals(3, members.size());
        for (byte[] member : members) {
            assertFalse(jedis.sismember(KEY, member).get());
        }
        assertEquals(Long.valueOf(2), jedis.scard(KEY).get());
    }
} 