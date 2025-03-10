package io.valkey.async;

import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ExecutionException;

import io.valkey.resps.ScanResult;
import io.valkey.resps.Tuple;
import io.valkey.params.ZAddParams;
import io.valkey.params.ZRangeParams;

import static org.junit.Assert.*;

public class AsyncSortedSetBinaryCommandsTest extends AsyncJedisBasicTest {

    private static final byte[] KEY = "myzset".getBytes();
    private static final byte[] DEST_KEY = "otherzset".getBytes();
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
    public void testZadd() throws ExecutionException, InterruptedException {
        Long result = jedis.zadd(KEY, 1.0, MEMBER1).get();
        assertEquals(1L, result.longValue());

        result = jedis.zadd(KEY, 1.0, MEMBER1).get();
        assertEquals(0L, result.longValue());

        Map<byte[], Double> scoreMembers = new HashMap<>();
        scoreMembers.put(MEMBER2, 2.0);
        scoreMembers.put(MEMBER3, 3.0);
        result = jedis.zadd(KEY, scoreMembers).get();
        assertEquals(2L, result.longValue());
    }

    @Test
    public void testZaddWithParams() throws ExecutionException, InterruptedException {
        jedis.zadd(KEY, 1.0, MEMBER1).get();
        
        ZAddParams params = new ZAddParams().nx();
        Long result = jedis.zadd(KEY, 2.0, MEMBER1, params).get();
        assertEquals(0L, result.longValue());

        params = new ZAddParams().xx().ch();
        result = jedis.zadd(KEY, 2.0, MEMBER1, params).get();
        assertEquals(1L, result.longValue());
    }

    @Test
    public void testZcard() throws ExecutionException, InterruptedException {
        Long count = jedis.zcard(KEY).get();
        assertEquals(0L, count.longValue());

        jedis.zadd(KEY, 1.0, MEMBER1).get();
        jedis.zadd(KEY, 2.0, MEMBER2).get();
        count = jedis.zcard(KEY).get();
        assertEquals(2L, count.longValue());
    }

    @Test
    public void testZcount() throws ExecutionException, InterruptedException {
        jedis.zadd(KEY, 1.0, MEMBER1).get();
        jedis.zadd(KEY, 2.0, MEMBER2).get();
        jedis.zadd(KEY, 3.0, MEMBER3).get();

        Long count = jedis.zcount(KEY, 1.0, 2.0).get();
        assertEquals(2L, count.longValue());
    }

    @Test
    public void testZincrby() throws ExecutionException, InterruptedException {
        jedis.zadd(KEY, 1.0, MEMBER1).get();
        Double score = jedis.zincrby(KEY, 2.0, MEMBER1).get();
        assertEquals(3.0, score, 0.0001);
    }

    @Test
    public void testZrange() throws ExecutionException, InterruptedException {
        jedis.zadd(KEY, 1.0, MEMBER1).get();
        jedis.zadd(KEY, 2.0, MEMBER2).get();
        jedis.zadd(KEY, 3.0, MEMBER3).get();

        List<byte[]> range = jedis.zrange(KEY, 0, -1).get();
        assertEquals(3, range.size());
        assertArrayEquals(MEMBER1, range.get(0));
        assertArrayEquals(MEMBER2, range.get(1));
        assertArrayEquals(MEMBER3, range.get(2));
    }

    @Test
    public void testZrangeWithScores() throws ExecutionException, InterruptedException {
        jedis.zadd(KEY, 1.0, MEMBER1).get();
        jedis.zadd(KEY, 2.0, MEMBER2).get();
        jedis.zadd(KEY, 3.0, MEMBER3).get();

        List<Tuple> range = jedis.zrangeWithScores(KEY, 0, -1).get();
        assertEquals(3, range.size());
        assertArrayEquals(MEMBER1, range.get(0).getBinaryElement());
        assertEquals(1.0, range.get(0).getScore(), 0.0001);
    }

    @Test
    public void testZrangeByScore() throws ExecutionException, InterruptedException {
        jedis.zadd(KEY, 1.0, MEMBER1).get();
        jedis.zadd(KEY, 2.0, MEMBER2).get();
        jedis.zadd(KEY, 3.0, MEMBER3).get();

        List<byte[]> range = jedis.zrangeByScore(KEY, 1.0, 2.0).get();
        assertEquals(2, range.size());
        assertArrayEquals(MEMBER1, range.get(0));
        assertArrayEquals(MEMBER2, range.get(1));
    }

    @Test
    public void testZrangeByScoreWithScores() throws ExecutionException, InterruptedException {
        jedis.zadd(KEY, 1.0, MEMBER1).get();
        jedis.zadd(KEY, 2.0, MEMBER2).get();
        jedis.zadd(KEY, 3.0, MEMBER3).get();

        List<Tuple> range = jedis.zrangeByScoreWithScores(KEY, 1.0, 2.0).get();
        assertEquals(2, range.size());
        assertArrayEquals(MEMBER1, range.get(0).getBinaryElement());
        assertEquals(1.0, range.get(0).getScore(), 0.0001);
    }

    @Test
    public void testZrank() throws ExecutionException, InterruptedException {
        jedis.zadd(KEY, 1.0, MEMBER1).get();
        jedis.zadd(KEY, 2.0, MEMBER2).get();
        jedis.zadd(KEY, 3.0, MEMBER3).get();

        Long rank = jedis.zrank(KEY, MEMBER2).get();
        assertEquals(1L, rank.longValue());
    }

    @Test
    public void testZrem() throws ExecutionException, InterruptedException {
        jedis.zadd(KEY, 1.0, MEMBER1).get();
        jedis.zadd(KEY, 2.0, MEMBER2).get();

        Long result = jedis.zrem(KEY, MEMBER1).get();
        assertEquals(1L, result.longValue());

        result = jedis.zrem(KEY, MEMBER1).get();
        assertEquals(0L, result.longValue());
    }

    @Test
    public void testZremrangeByRank() throws ExecutionException, InterruptedException {
        jedis.zadd(KEY, 1.0, MEMBER1).get();
        jedis.zadd(KEY, 2.0, MEMBER2).get();
        jedis.zadd(KEY, 3.0, MEMBER3).get();

        Long result = jedis.zremrangeByRank(KEY, 0, 1).get();
        assertEquals(2L, result.longValue());
    }

    @Test
    public void testZremrangeByScore() throws ExecutionException, InterruptedException {
        jedis.zadd(KEY, 1.0, MEMBER1).get();
        jedis.zadd(KEY, 2.0, MEMBER2).get();
        jedis.zadd(KEY, 3.0, MEMBER3).get();

        Long result = jedis.zremrangeByScore(KEY, 1.0, 2.0).get();
        assertEquals(2L, result.longValue());
    }

    @Test
    public void testZrevrange() throws ExecutionException, InterruptedException {
        jedis.zadd(KEY, 1.0, MEMBER1).get();
        jedis.zadd(KEY, 2.0, MEMBER2).get();
        jedis.zadd(KEY, 3.0, MEMBER3).get();

        List<byte[]> range = jedis.zrevrange(KEY, 0, -1).get();
        assertEquals(3, range.size());
        assertArrayEquals(MEMBER3, range.get(0));
        assertArrayEquals(MEMBER2, range.get(1));
        assertArrayEquals(MEMBER1, range.get(2));
    }

    @Test
    public void testZrevrangeWithScores() throws ExecutionException, InterruptedException {
        jedis.zadd(KEY, 1.0, MEMBER1).get();
        jedis.zadd(KEY, 2.0, MEMBER2).get();
        jedis.zadd(KEY, 3.0, MEMBER3).get();

        List<Tuple> range = jedis.zrevrangeWithScores(KEY, 0, -1).get();
        assertEquals(3, range.size());
        assertArrayEquals(MEMBER3, range.get(0).getBinaryElement());
        assertEquals(3.0, range.get(0).getScore(), 0.0001);
    }

    @Test
    public void testZrevrangeByScore() throws ExecutionException, InterruptedException {
        jedis.zadd(KEY, 1.0, MEMBER1).get();
        jedis.zadd(KEY, 2.0, MEMBER2).get();
        jedis.zadd(KEY, 3.0, MEMBER3).get();

        List<byte[]> range = jedis.zrevrangeByScore(KEY, 3.0, 1.0).get();
        assertEquals(3, range.size());
        assertArrayEquals(MEMBER3, range.get(0));
        assertArrayEquals(MEMBER2, range.get(1));
        assertArrayEquals(MEMBER1, range.get(2));
    }

    @Test
    public void testZrevrank() throws ExecutionException, InterruptedException {
        jedis.zadd(KEY, 1.0, MEMBER1).get();
        jedis.zadd(KEY, 2.0, MEMBER2).get();
        jedis.zadd(KEY, 3.0, MEMBER3).get();

        Long rank = jedis.zrevrank(KEY, MEMBER1).get();
        assertEquals(2L, rank.longValue());
    }

    @Test
    public void testZscore() throws ExecutionException, InterruptedException {
        jedis.zadd(KEY, 1.5, MEMBER1).get();
        Double score = jedis.zscore(KEY, MEMBER1).get();
        assertEquals(1.5, score, 0.0001);
    }

    @Test
    public void testZmscore() throws ExecutionException, InterruptedException {
        jedis.zadd(KEY, 1.0, MEMBER1).get();
        jedis.zadd(KEY, 2.0, MEMBER2).get();

        List<Double> scores = jedis.zmscore(KEY, MEMBER1, MEMBER2, MEMBER3).get();
        assertEquals(3, scores.size());
        assertEquals(1.0, scores.get(0), 0.0001);
        assertEquals(2.0, scores.get(1), 0.0001);
        assertNull(scores.get(2));
    }

    @Test
    public void testZrangeByLex() throws ExecutionException, InterruptedException {
        byte[] a = "a".getBytes();
        byte[] b = "b".getBytes();
        byte[] c = "c".getBytes();
        jedis.zadd(KEY, 0, a).get();
        jedis.zadd(KEY, 0, b).get();
        jedis.zadd(KEY, 0, c).get();

        List<byte[]> range = jedis.zrangeByLex(KEY, "[a".getBytes(), "(c".getBytes()).get();
        assertEquals(2, range.size());
        assertArrayEquals(a, range.get(0));
        assertArrayEquals(b, range.get(1));
    }

    @Test
    public void testZlexcount() throws ExecutionException, InterruptedException {
        byte[] a = "a".getBytes();
        byte[] b = "b".getBytes();
        byte[] c = "c".getBytes();
        jedis.zadd(KEY, 0, a).get();
        jedis.zadd(KEY, 0, b).get();
        jedis.zadd(KEY, 0, c).get();

        Long count = jedis.zlexcount(KEY, "[a".getBytes(), "(c".getBytes()).get();
        assertEquals(2L, count.longValue());
    }

    @Test
    public void testZremrangeByLex() throws ExecutionException, InterruptedException {
        byte[] a = "a".getBytes();
        byte[] b = "b".getBytes();
        byte[] c = "c".getBytes();
        jedis.zadd(KEY, 0, a).get();
        jedis.zadd(KEY, 0, b).get();
        jedis.zadd(KEY, 0, c).get();

        Long result = jedis.zremrangeByLex(KEY, "[a".getBytes(), "(c".getBytes()).get();
        assertEquals(2L, result.longValue());
    }

    @Test
    public void testZpopmax() throws ExecutionException, InterruptedException {
        jedis.zadd(KEY, 1.0, MEMBER1).get();
        jedis.zadd(KEY, 2.0, MEMBER2).get();

        Tuple popped = jedis.zpopmax(KEY).get();
        assertArrayEquals(MEMBER2, popped.getBinaryElement());
        assertEquals(2.0, popped.getScore(), 0.0001);
    }

    @Test
    public void testZpopmaxWithCount() throws ExecutionException, InterruptedException {
        jedis.zadd(KEY, 1.0, MEMBER1).get();
        jedis.zadd(KEY, 2.0, MEMBER2).get();
        jedis.zadd(KEY, 3.0, MEMBER3).get();

        List<Tuple> popped = jedis.zpopmax(KEY, 2).get();
        assertEquals(2, popped.size());
        assertArrayEquals(MEMBER3, popped.get(0).getBinaryElement());
        assertEquals(3.0, popped.get(0).getScore(), 0.0001);
        assertArrayEquals(MEMBER2, popped.get(1).getBinaryElement());
        assertEquals(2.0, popped.get(1).getScore(), 0.0001);
    }

    @Test
    public void testZpopmin() throws ExecutionException, InterruptedException {
        jedis.zadd(KEY, 1.0, MEMBER1).get();
        jedis.zadd(KEY, 2.0, MEMBER2).get();

        Tuple popped = jedis.zpopmin(KEY).get();
        assertArrayEquals(MEMBER1, popped.getBinaryElement());
        assertEquals(1.0, popped.getScore(), 0.0001);
    }

    @Test
    public void testZpopminWithCount() throws ExecutionException, InterruptedException {
        jedis.zadd(KEY, 1.0, MEMBER1).get();
        jedis.zadd(KEY, 2.0, MEMBER2).get();
        jedis.zadd(KEY, 3.0, MEMBER3).get();

        List<Tuple> popped = jedis.zpopmin(KEY, 2).get();
        assertEquals(2, popped.size());
        assertArrayEquals(MEMBER1, popped.get(0).getBinaryElement());
        assertEquals(1.0, popped.get(0).getScore(), 0.0001);
        assertArrayEquals(MEMBER2, popped.get(1).getBinaryElement());
        assertEquals(2.0, popped.get(1).getScore(), 0.0001);
    }

    @Test
    public void testZscan() throws ExecutionException, InterruptedException {
        Map<byte[], Double> scoreMembers = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            scoreMembers.put(("member" + i).getBytes(), (double) i);
        }
        jedis.zadd(KEY, scoreMembers).get();

        byte[] cursor = "0".getBytes();
        int count = 0;
        do {
            ScanResult<Tuple> scanResult = jedis.zscan(KEY, cursor).get();
            cursor = scanResult.getCursor().getBytes();
            count += scanResult.getResult().size();
        } while (!Arrays.equals(cursor, "0".getBytes()));

        assertEquals(10, count);
    }
} 