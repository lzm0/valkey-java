package io.valkey.async.commands;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import io.valkey.params.ScanParams;
import io.valkey.resps.ScanResult;

/**
 * The interface for Redis Set commands using binary data.
 */
public interface AsyncSetBinaryCommands {

    /**
     * Add one or more members to a set.
     *
     * @param key Key of the set
     * @param members Members to add to the set
     * @return The number of elements that were added to the set
     */
    CompletableFuture<Long> sadd(byte[] key, byte[]... members);

    /**
     * Get all members in a set.
     *
     * @param key Key of the set
     * @return All elements of the set
     */
    CompletableFuture<Set<byte[]>> smembers(byte[] key);

    /**
     * Remove one or more members from a set.
     *
     * @param key Key of the set
     * @param members Members to remove from the set
     * @return The number of elements that were removed from the set
     */
    CompletableFuture<Long> srem(byte[] key, byte[]... members);

    /**
     * Remove and return a random member from a set.
     *
     * @param key Key of the set
     * @return The removed element, or null when key does not exist
     */
    CompletableFuture<byte[]> spop(byte[] key);

    /**
     * Remove and return one or more random members from a set.
     *
     * @param key Key of the set
     * @param count Number of elements to pop
     * @return The removed elements
     */
    CompletableFuture<Set<byte[]>> spop(byte[] key, long count);

    /**
     * Get the number of members in a set.
     *
     * @param key Key of the set
     * @return The cardinality (number of elements) of the set
     */
    CompletableFuture<Long> scard(byte[] key);

    /**
     * Determine if a given value is a member of a set.
     *
     * @param key Key of the set
     * @param member Member to check
     * @return True if the element is a member of the set, false otherwise
     */
    CompletableFuture<Boolean> sismember(byte[] key, byte[] member);

    /**
     * Determine if multiple values are members of a set.
     *
     * @param key Key of the set
     * @param members Members to check
     * @return List of booleans indicating membership of each element
     */
    CompletableFuture<List<Boolean>> smismember(byte[] key, byte[]... members);

    /**
     * Get a random member from a set.
     *
     * @param key Key of the set
     * @return Random element from the set
     */
    CompletableFuture<byte[]> srandmember(byte[] key);

    /**
     * Get multiple random members from a set.
     *
     * @param key Key of the set
     * @param count Number of elements to return
     * @return Random elements from the set
     */
    CompletableFuture<List<byte[]>> srandmember(byte[] key, int count);

    /**
     * Subtract multiple sets.
     *
     * @param keys Keys of the sets
     * @return The members of the resulting set
     */
    CompletableFuture<Set<byte[]>> sdiff(byte[]... keys);

    /**
     * Subtract multiple sets and store the resulting set in a key.
     *
     * @param dstkey Destination key
     * @param keys Source keys
     * @return The number of elements in the resulting set
     */
    CompletableFuture<Long> sdiffstore(byte[] dstkey, byte[]... keys);

    /**
     * Intersect multiple sets.
     *
     * @param keys Keys of the sets
     * @return The members of the resulting set
     */
    CompletableFuture<Set<byte[]>> sinter(byte[]... keys);

    /**
     * Intersect multiple sets and store the resulting set in a key.
     *
     * @param dstkey Destination key
     * @param keys Source keys
     * @return The number of elements in the resulting set
     */
    CompletableFuture<Long> sinterstore(byte[] dstkey, byte[]... keys);

    /**
     * Returns the cardinality of the set which would result from the intersection of all given sets.
     *
     * @param keys Keys of the sets
     * @return The cardinality of the resulting set
     */
    CompletableFuture<Long> sintercard(byte[]... keys);

    /**
     * Returns the cardinality of the set which would result from the intersection of all given sets,
     * up to the given limit.
     *
     * @param limit Maximum number of elements to count
     * @param keys Keys of the sets
     * @return The cardinality of the resulting set, limited by the given count
     */
    CompletableFuture<Long> sintercard(int limit, byte[]... keys);

    /**
     * Add multiple sets.
     *
     * @param keys Keys of the sets
     * @return The members of the resulting set
     */
    CompletableFuture<Set<byte[]>> sunion(byte[]... keys);

    /**
     * Add multiple sets and store the resulting set in a key.
     *
     * @param dstkey Destination key
     * @param keys Source keys
     * @return The number of elements in the resulting set
     */
    CompletableFuture<Long> sunionstore(byte[] dstkey, byte[]... keys);

    /**
     * Move a member from one set to another.
     *
     * @param srckey Source key
     * @param dstkey Destination key
     * @param member Member to move
     * @return 1 if the element was moved, 0 otherwise
     */
    CompletableFuture<Long> smove(byte[] srckey, byte[] dstkey, byte[] member);

    /**
     * Binary version of SSCAN command.
     * @param key the key
     * @param cursor the cursor
     * @return CompletableFuture that completes with scan result
     */
    default CompletableFuture<ScanResult<byte[]>> sscan(byte[] key, byte[] cursor) {
        return sscan(key, cursor, new ScanParams());
    }

    /**
     * Binary version of SSCAN command.
     * @param key the key
     * @param cursor the cursor
     * @param params the scan parameters
     * @return CompletableFuture that completes with scan result
     */
    CompletableFuture<ScanResult<byte[]>> sscan(byte[] key, byte[] cursor, ScanParams params);
} 