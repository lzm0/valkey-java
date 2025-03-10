package io.valkey.async;

import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ExecutionException;

import io.valkey.resps.ScanResult;

import static org.junit.Assert.*;

public class AsyncSetBinaryCommandsTest extends AsyncJedisBasicTest {

    private static final byte[] KEY = "myset".getBytes();
    private static final byte[] DEST_KEY = "otherset".getBytes();
    private static final byte[] MEMBER1 = "member1".getBytes();
    private static final byte[] MEMBER2 = "member2".getBytes();
    private static final byte[] MEMBER3 = "member3".getBytes();

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
        Set<byte[]> members = jedis.smembers(KEY).get();
        
        assertEquals(3, members.size());
        assertTrue(containsByteArray(members, MEMBER1));
        assertTrue(containsByteArray(members, MEMBER2));
        assertTrue(containsByteArray(members, MEMBER3));
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
        byte[] member = jedis.spop(KEY).get();
        assertArrayEquals(MEMBER1, member);
        
        Set<byte[]> members = jedis.smembers(KEY).get();
        assertTrue(members.isEmpty());
    }

    @Test
    public void testSpopMultiple() throws ExecutionException, InterruptedException {
        jedis.sadd(KEY, MEMBER1, MEMBER2, MEMBER3).get();
        Set<byte[]> popped = jedis.spop(KEY, 2).get();
        
        assertEquals(2, popped.size());
        Set<byte[]> remaining = jedis.smembers(KEY).get();
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
        byte[] member = jedis.srandmember(KEY).get();
        assertArrayEquals(MEMBER1, member);
    }

    @Test
    public void testSrandmemberMultiple() throws ExecutionException, InterruptedException {
        jedis.sadd(KEY, MEMBER1, MEMBER2, MEMBER3).get();
        List<byte[]> members = jedis.srandmember(KEY, 2).get();
        
        assertEquals(2, members.size());
        Set<byte[]> original = jedis.smembers(KEY).get();
        assertEquals(3, original.size());
    }

    @Test
    public void testSdiff() throws ExecutionException, InterruptedException {
        jedis.sadd(KEY, MEMBER1, MEMBER2, MEMBER3).get();
        jedis.sadd(DEST_KEY, MEMBER2).get();
        
        Set<byte[]> diff = jedis.sdiff(KEY, DEST_KEY).get();
        assertEquals(2, diff.size());
        assertTrue(containsByteArray(diff, MEMBER1));
        assertTrue(containsByteArray(diff, MEMBER3));
    }

    @Test
    public void testSdiffstore() throws ExecutionException, InterruptedException {
        jedis.sadd(KEY, MEMBER1, MEMBER2, MEMBER3).get();
        jedis.sadd(DEST_KEY, MEMBER2).get();
        
        byte[] newKey = "newset".getBytes();
        Long count = jedis.sdiffstore(newKey, KEY, DEST_KEY).get();
        assertEquals(2L, count.longValue());
        
        Set<byte[]> members = jedis.smembers(newKey).get();
        assertEquals(2, members.size());
        assertTrue(containsByteArray(members, MEMBER1));
        assertTrue(containsByteArray(members, MEMBER3));
    }

    @Test
    public void testSinter() throws ExecutionException, InterruptedException {
        jedis.sadd(KEY, MEMBER1, MEMBER2).get();
        jedis.sadd(DEST_KEY, MEMBER2, MEMBER3).get();
        
        Set<byte[]> inter = jedis.sinter(KEY, DEST_KEY).get();
        assertEquals(1, inter.size());
        assertTrue(containsByteArray(inter, MEMBER2));
    }

    @Test
    public void testSinterstore() throws ExecutionException, InterruptedException {
        jedis.sadd(KEY, MEMBER1, MEMBER2).get();
        jedis.sadd(DEST_KEY, MEMBER2, MEMBER3).get();
        
        byte[] newKey = "newset".getBytes();
        Long count = jedis.sinterstore(newKey, KEY, DEST_KEY).get();
        assertEquals(1L, count.longValue());
        
        Set<byte[]> members = jedis.smembers(newKey).get();
        assertEquals(1, members.size());
        assertTrue(containsByteArray(members, MEMBER2));
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
        
        Set<byte[]> union = jedis.sunion(KEY, DEST_KEY).get();
        assertEquals(3, union.size());
        assertTrue(containsByteArray(union, MEMBER1));
        assertTrue(containsByteArray(union, MEMBER2));
        assertTrue(containsByteArray(union, MEMBER3));
    }

    @Test
    public void testSunionstore() throws ExecutionException, InterruptedException {
        jedis.sadd(KEY, MEMBER1, MEMBER2).get();
        jedis.sadd(DEST_KEY, MEMBER2, MEMBER3).get();
        
        byte[] newKey = "newset".getBytes();
        Long count = jedis.sunionstore(newKey, KEY, DEST_KEY).get();
        assertEquals(3L, count.longValue());
        
        Set<byte[]> members = jedis.smembers(newKey).get();
        assertEquals(3, members.size());
        assertTrue(containsByteArray(members, MEMBER1));
        assertTrue(containsByteArray(members, MEMBER2));
        assertTrue(containsByteArray(members, MEMBER3));
    }

    @Test
    public void testSmove() throws ExecutionException, InterruptedException {
        jedis.sadd(KEY, MEMBER1, MEMBER2).get();
        jedis.sadd(DEST_KEY, MEMBER3).get();
        
        Long result = jedis.smove(KEY, DEST_KEY, MEMBER1).get();
        assertEquals(1L, result.longValue());
        
        Set<byte[]> sourceMembers = jedis.smembers(KEY).get();
        assertEquals(1, sourceMembers.size());
        assertTrue(containsByteArray(sourceMembers, MEMBER2));
        
        Set<byte[]> destMembers = jedis.smembers(DEST_KEY).get();
        assertEquals(2, destMembers.size());
        assertTrue(containsByteArray(destMembers, MEMBER1));
        assertTrue(containsByteArray(destMembers, MEMBER3));
    }

    @Test
    public void testSscan() throws ExecutionException, InterruptedException {
        for (int i = 0; i < 10; i++) {
            jedis.sadd(KEY, ("member" + i).getBytes()).get();
        }
        
        byte[] cursor = "0".getBytes();
        int count = 0;
        do {
            ScanResult<byte[]> scanResult = jedis.sscan(KEY, cursor).get();
            cursor = scanResult.getCursor().getBytes();
            count += scanResult.getResult().size();
        } while (!Arrays.equals(cursor, "0".getBytes()));
        
        assertEquals(10, count);
    }

    private boolean containsByteArray(Set<byte[]> set, byte[] target) {
        for (byte[] item : set) {
            if (Arrays.equals(item, target)) {
                return true;
            }
        }
        return false;
    }
} 