package io.valkey.async;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

public class AsyncSampleKeyedCommandsTest extends AsyncJedisBasicTest {

    private static final String KEY = "myset";
    private static final String VALUE = "value";

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        jedis.del(KEY).get();
    }

    @Test
    public void testRandomKey() throws ExecutionException, InterruptedException {
        jedis.set("key1", "value1").get();
        jedis.set("key2", "value2").get();
        jedis.set("key3", "value3").get();

        String randomKey = jedis.randomKey().get();
        assertNotNull(randomKey);
        assertTrue(randomKey.startsWith("key"));
        String value = jedis.get(randomKey).get();
        assertNotNull(value);
        assertTrue(value.startsWith("value"));
    }

    @Test
    public void testSrandmember() throws ExecutionException, InterruptedException {
        jedis.sadd(KEY, "a", "b", "c", "d", "e").get();

        String member = jedis.srandmember(KEY).get();
        assertNotNull(member);
        assertTrue(jedis.sismember(KEY, member).get());
    }

    @Test
    public void testSrandmemberMulti() throws ExecutionException, InterruptedException {
        jedis.sadd(KEY, "a", "b", "c", "d", "e").get();

        // Test positive count (unique elements)
        List<String> members = jedis.srandmember(KEY, 3).get();
        assertEquals(3, members.size());
        for (String member : members) {
            assertTrue(jedis.sismember(KEY, member).get());
        }

        // Test negative count (may have duplicates)
        members = jedis.srandmember(KEY, -3).get();
        assertEquals(3, members.size());
        for (String member : members) {
            assertTrue(jedis.sismember(KEY, member).get());
        }
    }

    @Test
    public void testSpop() throws ExecutionException, InterruptedException {
        jedis.sadd(KEY, "a", "b", "c", "d", "e").get();

        String member = jedis.spop(KEY).get();
        assertNotNull(member);
        assertFalse(jedis.sismember(KEY, member).get());
        assertEquals(Long.valueOf(4), jedis.scard(KEY).get());
    }

    @Test
    public void testSpopMulti() throws ExecutionException, InterruptedException {
        jedis.sadd(KEY, "a", "b", "c", "d", "e").get();

        Set<String> members = jedis.spop(KEY, 3).get();
        assertEquals(3, members.size());
        for (String member : members) {
            assertFalse(jedis.sismember(KEY, member).get());
        }
        assertEquals(Long.valueOf(2), jedis.scard(KEY).get());
    }
} 