package io.valkey.async;

import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ExecutionException;

import io.valkey.resps.*;
import io.valkey.params.*;
import io.valkey.StreamEntryID;

import static org.junit.Assert.*;

public class AsyncStreamCommandsTest extends AsyncJedisBasicTest {

    private static final String KEY = "mystream";
    private static final String GROUP = "mygroup";
    private static final String CONSUMER = "myconsumer";
    private static final String FIELD = "field";
    private static final String VALUE = "value";

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        jedis.del(KEY).get();
    }

    @Test
    public void testXadd() throws ExecutionException, InterruptedException {
        Map<String, String> hash = new HashMap<>();
        hash.put(FIELD, VALUE);
        StreamEntryID id = jedis.xadd(KEY, StreamEntryID.NEW_ENTRY, hash).get();
        assertNotNull(id);
    }

    @Test
    public void testXaddWithId() throws ExecutionException, InterruptedException {
        Map<String, String> hash = new HashMap<>();
        hash.put(FIELD, VALUE);
        StreamEntryID entryId = new StreamEntryID(1234567891234L, 0);
        StreamEntryID id = jedis.xadd(KEY, entryId, hash).get();
        assertEquals(entryId, id);
    }

    @Test
    public void testXlen() throws ExecutionException, InterruptedException {
        Map<String, String> hash = new HashMap<>();
        hash.put(FIELD, VALUE);
        jedis.xadd(KEY, StreamEntryID.NEW_ENTRY, hash).get();
        Long length = jedis.xlen(KEY).get();
        assertEquals(1L, length.longValue());
    }

    @Test
    public void testXrange() throws ExecutionException, InterruptedException {
        Map<String, String> hash = new HashMap<>();
        hash.put(FIELD, VALUE);
        StreamEntryID id1 = jedis.xadd(KEY, StreamEntryID.NEW_ENTRY, hash).get();
        hash.put(FIELD, "value2");
        StreamEntryID id2 = jedis.xadd(KEY, StreamEntryID.NEW_ENTRY, hash).get();

        List<StreamEntry> range = jedis.xrange(KEY, "-", "+").get();
        assertEquals(2, range.size());
        assertEquals(id1, range.get(0).getID());
        assertEquals(VALUE, range.get(0).getFields().get(FIELD));
        assertEquals(id2, range.get(1).getID());
        assertEquals("value2", range.get(1).getFields().get(FIELD));
    }

    @Test
    public void testXrevrange() throws ExecutionException, InterruptedException {
        Map<String, String> hash = new HashMap<>();
        hash.put(FIELD, VALUE);
        StreamEntryID id1 = jedis.xadd(KEY, StreamEntryID.NEW_ENTRY, hash).get();
        hash.put(FIELD, "value2");
        StreamEntryID id2 = jedis.xadd(KEY, StreamEntryID.NEW_ENTRY, hash).get();

        List<StreamEntry> range = jedis.xrevrange(KEY, "+", "-").get();
        assertEquals(2, range.size());
        assertEquals(id2, range.get(0).getID());
        assertEquals("value2", range.get(0).getFields().get(FIELD));
        assertEquals(id1, range.get(1).getID());
        assertEquals(VALUE, range.get(1).getFields().get(FIELD));
    }

    @Test
    public void testXgroupCreate() throws ExecutionException, InterruptedException {
        Map<String, String> hash = new HashMap<>();
        hash.put(FIELD, VALUE);
        jedis.xadd(KEY, StreamEntryID.NEW_ENTRY, hash).get();

        String status = jedis.xgroupCreate(KEY, GROUP, StreamEntryID.LAST_ENTRY, false).get();
        assertEquals("OK", status);
    }

    @Test
    public void testXgroupDestroy() throws ExecutionException, InterruptedException {
        Map<String, String> hash = new HashMap<>();
        hash.put(FIELD, VALUE);
        jedis.xadd(KEY, StreamEntryID.NEW_ENTRY, hash).get();
        jedis.xgroupCreate(KEY, GROUP, StreamEntryID.LAST_ENTRY, false).get();

        Long result = jedis.xgroupDestroy(KEY, GROUP).get();
        assertEquals(1L, result.longValue());
    }

    @Test
    public void testXgroupCreateConsumer() throws ExecutionException, InterruptedException {
        Map<String, String> hash = new HashMap<>();
        hash.put(FIELD, VALUE);
        jedis.xadd(KEY, StreamEntryID.NEW_ENTRY, hash).get();
        jedis.xgroupCreate(KEY, GROUP, StreamEntryID.LAST_ENTRY, false).get();

        Boolean result = jedis.xgroupCreateConsumer(KEY, GROUP, CONSUMER).get();
        assertTrue(result);
    }

    @Test
    public void testXgroupDelConsumer() throws ExecutionException, InterruptedException {
        Map<String, String> hash = new HashMap<>();
        hash.put(FIELD, VALUE);
        jedis.xadd(KEY, StreamEntryID.NEW_ENTRY, hash).get();
        jedis.xgroupCreate(KEY, GROUP, StreamEntryID.LAST_ENTRY, false).get();
        jedis.xgroupCreateConsumer(KEY, GROUP, CONSUMER).get();

        Long result = jedis.xgroupDelConsumer(KEY, GROUP, CONSUMER).get();
        assertEquals(0L, result.longValue());
    }

    @Test
    public void testXreadGroup() throws ExecutionException, InterruptedException {
        Map<String, String> hash = new HashMap<>();
        hash.put(FIELD, VALUE);
        StreamEntryID id = jedis.xadd(KEY, StreamEntryID.NEW_ENTRY, hash).get();
        jedis.xgroupCreate(KEY, GROUP, StreamEntryID.LAST_ENTRY, false).get();

        hash.put(FIELD, "value2");
        StreamEntryID id2 = jedis.xadd(KEY, StreamEntryID.NEW_ENTRY, hash).get();

        Map<String, StreamEntryID> streams = new HashMap<>();
        streams.put(KEY, StreamEntryID.UNRECEIVED_ENTRY);

        XReadGroupParams params = new XReadGroupParams();
        List<Map.Entry<String, List<StreamEntry>>> entries = jedis.xreadGroup(GROUP, CONSUMER, params, streams).get();
        assertEquals(1, entries.size());
        assertEquals(1, entries.get(0).getValue().size());
        assertEquals(id2, entries.get(0).getValue().get(0).getID());
    }

    @Test
    public void testXack() throws ExecutionException, InterruptedException {
        Map<String, String> hash = new HashMap<>();
        hash.put(FIELD, VALUE);
        StreamEntryID id = jedis.xadd(KEY, StreamEntryID.NEW_ENTRY, hash).get();
        jedis.xgroupCreate(KEY, GROUP, StreamEntryID.LAST_ENTRY, false).get();

        hash.put(FIELD, "value2");
        StreamEntryID id2 = jedis.xadd(KEY, StreamEntryID.NEW_ENTRY, hash).get();

        Map<String, StreamEntryID> streams = new HashMap<>();
        streams.put(KEY, StreamEntryID.UNRECEIVED_ENTRY);

        XReadGroupParams params = new XReadGroupParams();
        List<Map.Entry<String, List<StreamEntry>>> entries = jedis.xreadGroup(GROUP, CONSUMER, params, streams).get();
        StreamEntryID entryId = entries.get(0).getValue().get(0).getID();

        Long result = jedis.xack(KEY, GROUP, entryId).get();
        assertEquals(1L, result.longValue());
    }

    @Test
    public void testXpending() throws ExecutionException, InterruptedException {
        Map<String, String> hash = new HashMap<>();
        hash.put(FIELD, VALUE);
        StreamEntryID id = jedis.xadd(KEY, StreamEntryID.NEW_ENTRY, hash).get();
        jedis.xgroupCreate(KEY, GROUP, StreamEntryID.LAST_ENTRY, false).get();

        hash.put(FIELD, "value2");
        StreamEntryID id2 = jedis.xadd(KEY, StreamEntryID.NEW_ENTRY, hash).get();

        Map<String, StreamEntryID> streams = new HashMap<>();
        streams.put(KEY, StreamEntryID.UNRECEIVED_ENTRY);
        XReadGroupParams params = new XReadGroupParams();
        jedis.xreadGroup(GROUP, CONSUMER, params, streams).get();

        StreamPendingSummary summary = jedis.xpending(KEY, GROUP).get();
        assertEquals(1, summary.getTotal());
        assertEquals(1L, summary.getConsumerMessageCount().get(CONSUMER).longValue());
    }

    @Test
    public void testXclaim() throws ExecutionException, InterruptedException {
        Map<String, String> hash = new HashMap<>();
        hash.put(FIELD, VALUE);
        StreamEntryID id = jedis.xadd(KEY, StreamEntryID.NEW_ENTRY, hash).get();
        jedis.xgroupCreate(KEY, GROUP, StreamEntryID.LAST_ENTRY, false).get();

        hash.put(FIELD, "value2");
        StreamEntryID id2 = jedis.xadd(KEY, StreamEntryID.NEW_ENTRY, hash).get();

        Map<String, StreamEntryID> streams = new HashMap<>();
        streams.put(KEY, StreamEntryID.UNRECEIVED_ENTRY);
        XReadGroupParams params = new XReadGroupParams();
        List<Map.Entry<String, List<StreamEntry>>> entries = jedis.xreadGroup(GROUP, CONSUMER, params, streams).get();
        StreamEntryID entryId = entries.get(0).getValue().get(0).getID();

        Thread.sleep(1000); // Wait for message to be pending

        XClaimParams claimParams = new XClaimParams();
        List<StreamEntry> claimed = jedis.xclaim(KEY, GROUP, "newconsumer", 0L, claimParams, entryId).get();
        assertEquals(1, claimed.size());
        assertEquals(entryId, claimed.get(0).getID());
    }

    @Test
    public void testXdel() throws ExecutionException, InterruptedException {
        Map<String, String> hash = new HashMap<>();
        hash.put(FIELD, VALUE);
        StreamEntryID id = jedis.xadd(KEY, StreamEntryID.NEW_ENTRY, hash).get();

        Long result = jedis.xdel(KEY, id).get();
        assertEquals(1L, result.longValue());
    }

    @Test
    public void testXtrim() throws ExecutionException, InterruptedException {
        Map<String, String> hash = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            hash.put(FIELD, "value" + i);
            jedis.xadd(KEY, StreamEntryID.NEW_ENTRY, hash).get();
        }

        Long result = jedis.xtrim(KEY, 10, false).get();
        assertTrue(result > 0);
        assertEquals(10L, jedis.xlen(KEY).get().longValue());
    }
} 