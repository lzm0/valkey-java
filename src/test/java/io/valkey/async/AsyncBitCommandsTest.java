package io.valkey.async;

import io.valkey.args.BitCountOption;
import io.valkey.args.BitOP;
import io.valkey.params.BitPosParams;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

public class AsyncBitCommandsTest extends AsyncJedisBasicTest {
    
    @Test
    public void testSetAndGetBit() throws ExecutionException, InterruptedException {
        String key = "mykey";
        // Test setbit
        assertFalse(jedis.setbit(key, 7, true).get());  // 新key的位值一定是false
        assertTrue(jedis.getbit(key, 7).get());
        
        assertTrue(jedis.setbit(key, 7, false).get());  // 现在位值是true,所以返回true
        assertFalse(jedis.getbit(key, 7).get());

        // Test with binary
        byte[] bkey = "bkey".getBytes();
        assertFalse(jedis.setbit(bkey, 7, true).get());  // 新key的位值一定是false
        assertTrue(jedis.getbit(bkey, 7).get());
    }

    @Test
    public void testBitCount() throws ExecutionException, InterruptedException {
        String key = "mykey";
        jedis.setbit(key, 0, true).get();
        jedis.setbit(key, 3, true).get();
        jedis.setbit(key, 7, true).get();

        // Test bitcount without range
        assertEquals(3L, (long) jedis.bitcount(key).get());

        // Test bitcount with range
        assertEquals(3L, (long) jedis.bitcount(key, 0, 0).get());  // All bits are in first byte
        
        // Test bitcount with option
        assertEquals(3L, (long) jedis.bitcount(key, 0, 0, BitCountOption.BYTE).get());

        // Test with binary
        byte[] bkey = "bkey".getBytes();
        jedis.setbit(bkey, 0, true).get();
        jedis.setbit(bkey, 3, true).get();
        assertEquals(2L, (long) jedis.bitcount(bkey).get());
    }

    @Test
    public void testBitPos() throws ExecutionException, InterruptedException {
        String key = "mykey";
        jedis.setbit(key, 3, true).get();
        jedis.setbit(key, 7, true).get();
        jedis.setbit(key, 13, true).get();
        jedis.setbit(key, 39, true).get();

        // Test bitpos without params
        assertEquals(3L, (long) jedis.bitpos(key, true).get());
        assertEquals(0L, (long) jedis.bitpos(key, false).get());

        // Test bitpos with params
        BitPosParams params = new BitPosParams(0, 1); // Search in first two bytes
        assertEquals(3L, (long) jedis.bitpos(key, true, params).get());

        // Test with binary
        byte[] bkey = "bkey".getBytes();
        jedis.setbit(bkey, 3, true).get();
        jedis.setbit(bkey, 7, true).get();
        assertEquals(3L, (long) jedis.bitpos(bkey, true).get());
    }

    @Test
    public void testBitfield() throws ExecutionException, InterruptedException {
        String key = "mykey";
        
        // Test SET and GET operations
        List<Long> result = jedis.bitfield(key, "SET", "i8", "0", "100",
                                               "GET", "i8", "0").get();
        assertEquals(2, result.size());
        assertEquals(0L, (long) result.get(0));  // Previous value was 0
        assertEquals(100L, (long) result.get(1)); // New value is 100

        // Test bitfield readonly
        result = jedis.bitfieldReadonly(key, "GET", "i8", "0").get();
        assertEquals(1, result.size());
        assertEquals(100L, (long) result.get(0));

        // Test with binary
        byte[] bkey = "bkey".getBytes();
        byte[][] args = new byte[][] {
            "SET".getBytes(),
            "i8".getBytes(),
            "0".getBytes(),
            "100".getBytes()
        };
        result = jedis.bitfield(bkey, args).get();
        assertEquals(1, result.size());
        assertEquals(0L, (long) result.get(0));
    }

    @Test
    public void testBitOp() throws ExecutionException, InterruptedException {
        String key1 = "key1";
        String key2 = "key2";
        String destKey = "dest";

        // Set up test data
        jedis.setbit(key1, 0, true).get();
        jedis.setbit(key1, 2, true).get();
        
        jedis.setbit(key2, 1, true).get();
        jedis.setbit(key2, 2, true).get();

        // Test AND operation
        long resultLen = jedis.bitop(BitOP.AND, destKey, key1, key2).get();
        assertEquals(1L, resultLen);
        assertTrue(jedis.getbit(destKey, 2).get());  // Only bit 2 should be set
        assertFalse(jedis.getbit(destKey, 0).get());
        assertFalse(jedis.getbit(destKey, 1).get());

        // Test OR operation
        resultLen = jedis.bitop(BitOP.OR, destKey, key1, key2).get();
        assertEquals(1L, resultLen);
        assertTrue(jedis.getbit(destKey, 0).get());
        assertTrue(jedis.getbit(destKey, 1).get());
        assertTrue(jedis.getbit(destKey, 2).get());

        // Test with binary
        byte[] bkey1 = "bkey1".getBytes();
        byte[] bkey2 = "bkey2".getBytes();
        byte[] bdestKey = "bdest".getBytes();
        
        jedis.setbit(bkey1, 0, true).get();
        jedis.setbit(bkey2, 1, true).get();
        
        resultLen = jedis.bitop(BitOP.OR, bdestKey, bkey1, bkey2).get();
        assertEquals(1L, resultLen);
        assertTrue(jedis.getbit(bdestKey, 0).get());
        assertTrue(jedis.getbit(bdestKey, 1).get());
    }
}
