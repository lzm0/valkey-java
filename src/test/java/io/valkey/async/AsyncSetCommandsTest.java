package io.valkey.async;

import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ExecutionException;

import io.valkey.resps.ScanResult;

import static org.junit.Assert.*;

public class AsyncSetCommandsTest extends AsyncJedisBasicTest {

    private static final String KEY = "myset";
    private static final String DEST_KEY = "otherset";
    private static final String MEMBER1 = "member1";
    private static final String MEMBER2 = "member2";
    private static final String MEMBER3 = "member3";

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        jedis.del(KEY).get();
        jedis.del(DEST_KEY).get();
    }

    @Test
    public void testSadd() throws ExecutionException, InterruptedException {
        Long result = jedis.sadd(KEY, MEMBER1).get();
        assertEquals(1L, result.longValue());

        result = jedis.sadd(KEY, MEMBER1).get();
        assertEquals(0L, result.longValue());

        result = jedis.sadd(KEY, MEMBER2, MEMBER3).get();
        assertEquals(2L, result.longValue());
    }

    @Test
    public void testSmembers() throws ExecutionException, InterruptedException {
        jedis.sadd(KEY, MEMBER1, MEMBER2, MEMBER3).get();
        Set<String> members = jedis.smembers(KEY).get();
        
        assertEquals(3, members.size());
        assertTrue(members.contains(MEMBER1));
        assertTrue(members.contains(MEMBER2));
        assertTrue(members.contains(MEMBER3));
    }

    @Test
    public void testSrem() throws ExecutionException, InterruptedException {
        jedis.sadd(KEY, MEMBER1, MEMBER2, MEMBER3).get();
        
        Long result = jedis.srem(KEY, MEMBER1).get();
        assertEquals(1L, result.longValue());

        result = jedis.srem(KEY, MEMBER1).get();
        assertEquals(0L, result.longValue());

        result = jedis.srem(KEY, MEMBER2, MEMBER3).get();
        assertEquals(2L, result.longValue());
    }

    @Test
    public void testSpop() throws ExecutionException, InterruptedException {
        jedis.sadd(KEY, MEMBER1).get();
        String member = jedis.spop(KEY).get();
        assertEquals(MEMBER1, member);
        
        Set<String> members = jedis.smembers(KEY).get();
        assertTrue(members.isEmpty());
    }

    @Test
    public void testSpopMultiple() throws ExecutionException, InterruptedException {
        jedis.sadd(KEY, MEMBER1, MEMBER2, MEMBER3).get();
        Set<String> popped = jedis.spop(KEY, 2).get();
        
        assertEquals(2, popped.size());
        Set<String> remaining = jedis.smembers(KEY).get();
        assertEquals(1, remaining.size());
    }

    @Test
    public void testScard() throws ExecutionException, InterruptedException {
        Long count = jedis.scard(KEY).get();
        assertEquals(0L, count.longValue());

        jedis.sadd(KEY, MEMBER1, MEMBER2, MEMBER3).get();
        count = jedis.scard(KEY).get();
        assertEquals(3L, count.longValue());
    }

    @Test
    public void testSismember() throws ExecutionException, InterruptedException {
        jedis.sadd(KEY, MEMBER1).get();
        
        Boolean exists = jedis.sismember(KEY, MEMBER1).get();
        assertTrue(exists);

        exists = jedis.sismember(KEY, MEMBER2).get();
        assertFalse(exists);
    }

    @Test
    public void testSmismember() throws ExecutionException, InterruptedException {
        jedis.sadd(KEY, MEMBER1, MEMBER2).get();
        
        List<Boolean> exists = jedis.smismember(KEY, MEMBER1, MEMBER2, MEMBER3).get();
        assertEquals(3, exists.size());
        assertTrue(exists.get(0));
        assertTrue(exists.get(1));
        assertFalse(exists.get(2));
    }

    @Test
    public void testSrandmember() throws ExecutionException, InterruptedException {
        jedis.sadd(KEY, MEMBER1).get();
        String member = jedis.srandmember(KEY).get();
        assertEquals(MEMBER1, member);
    }

    @Test
    public void testSrandmemberMultiple() throws ExecutionException, InterruptedException {
        jedis.sadd(KEY, MEMBER1, MEMBER2, MEMBER3).get();
        List<String> members = jedis.srandmember(KEY, 2).get();
        
        assertEquals(2, members.size());
        Set<String> original = jedis.smembers(KEY).get();
        assertEquals(3, original.size());
    }

    @Test
    public void testSdiff() throws ExecutionException, InterruptedException {
        jedis.sadd(KEY, MEMBER1, MEMBER2, MEMBER3).get();
        jedis.sadd(DEST_KEY, MEMBER2).get();
        
        Set<String> diff = jedis.sdiff(KEY, DEST_KEY).get();
        assertEquals(2, diff.size());
        assertTrue(diff.contains(MEMBER1));
        assertTrue(diff.contains(MEMBER3));
    }

    @Test
    public void testSdiffstore() throws ExecutionException, InterruptedException {
        jedis.sadd(KEY, MEMBER1, MEMBER2, MEMBER3).get();
        jedis.sadd(DEST_KEY, MEMBER2).get();
        
        String newKey = "newset";
        Long count = jedis.sdiffstore(newKey, KEY, DEST_KEY).get();
        assertEquals(2L, count.longValue());
        
        Set<String> members = jedis.smembers(newKey).get();
        assertEquals(2, members.size());
        assertTrue(members.contains(MEMBER1));
        assertTrue(members.contains(MEMBER3));
    }

    @Test
    public void testSinter() throws ExecutionException, InterruptedException {
        jedis.sadd(KEY, MEMBER1, MEMBER2).get();
        jedis.sadd(DEST_KEY, MEMBER2, MEMBER3).get();
        
        Set<String> inter = jedis.sinter(KEY, DEST_KEY).get();
        assertEquals(1, inter.size());
        assertTrue(inter.contains(MEMBER2));
    }

    @Test
    public void testSinterstore() throws ExecutionException, InterruptedException {
        jedis.sadd(KEY, MEMBER1, MEMBER2).get();
        jedis.sadd(DEST_KEY, MEMBER2, MEMBER3).get();
        
        String newKey = "newset";
        Long count = jedis.sinterstore(newKey, KEY, DEST_KEY).get();
        assertEquals(1L, count.longValue());
        
        Set<String> members = jedis.smembers(newKey).get();
        assertEquals(1, members.size());
        assertTrue(members.contains(MEMBER2));
    }

    @Test
    public void testSintercard() throws ExecutionException, InterruptedException {
        jedis.sadd(KEY, MEMBER1, MEMBER2).get();
        jedis.sadd(DEST_KEY, MEMBER2, MEMBER3).get();
        
        Long count = jedis.sintercard(KEY, DEST_KEY).get();
        assertEquals(1L, count.longValue());
    }

    @Test
    public void testSintercardWithLimit() throws ExecutionException, InterruptedException {
        jedis.sadd(KEY, MEMBER1, MEMBER2).get();
        jedis.sadd(DEST_KEY, MEMBER2, MEMBER3).get();
        
        Long count = jedis.sintercard(1, KEY, DEST_KEY).get();
        assertEquals(1L, count.longValue());
    }

    @Test
    public void testSunion() throws ExecutionException, InterruptedException {
        jedis.sadd(KEY, MEMBER1, MEMBER2).get();
        jedis.sadd(DEST_KEY, MEMBER2, MEMBER3).get();
        
        Set<String> union = jedis.sunion(KEY, DEST_KEY).get();
        assertEquals(3, union.size());
        assertTrue(union.contains(MEMBER1));
        assertTrue(union.contains(MEMBER2));
        assertTrue(union.contains(MEMBER3));
    }

    @Test
    public void testSunionstore() throws ExecutionException, InterruptedException {
        jedis.sadd(KEY, MEMBER1, MEMBER2).get();
        jedis.sadd(DEST_KEY, MEMBER2, MEMBER3).get();
        
        String newKey = "newset";
        Long count = jedis.sunionstore(newKey, KEY, DEST_KEY).get();
        assertEquals(3L, count.longValue());
        
        Set<String> members = jedis.smembers(newKey).get();
        assertEquals(3, members.size());
        assertTrue(members.contains(MEMBER1));
        assertTrue(members.contains(MEMBER2));
        assertTrue(members.contains(MEMBER3));
    }

    @Test
    public void testSmove() throws ExecutionException, InterruptedException {
        jedis.sadd(KEY, MEMBER1, MEMBER2).get();
        jedis.sadd(DEST_KEY, MEMBER3).get();
        
        Long result = jedis.smove(KEY, DEST_KEY, MEMBER1).get();
        assertEquals(1L, result.longValue());
        
        Set<String> sourceMembers = jedis.smembers(KEY).get();
        assertEquals(1, sourceMembers.size());
        assertTrue(sourceMembers.contains(MEMBER2));
        
        Set<String> destMembers = jedis.smembers(DEST_KEY).get();
        assertEquals(2, destMembers.size());
        assertTrue(destMembers.contains(MEMBER1));
        assertTrue(destMembers.contains(MEMBER3));
    }

    @Test
    public void testSscan() throws ExecutionException, InterruptedException {
        for (int i = 0; i < 10; i++) {
            jedis.sadd(KEY, "member" + i).get();
        }
        
        String cursor = "0";
        int count = 0;
        do {
            ScanResult<String> scanResult = jedis.sscan(KEY, cursor).get();
            cursor = scanResult.getCursor();
            count += scanResult.getResult().size();
        } while (!cursor.equals("0"));
        
        assertEquals(10, count);
    }
} 