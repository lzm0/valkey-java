package io.valkey.async.commands;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import io.valkey.params.ScanParams;
import io.valkey.resps.ScanResult;

/**
 * The interface for Redis Set commands using string data.
 */
public interface AsyncSetCommands {

    /**
     * Add one or more members to a set.
     *
     * @param key Key of the set
     * @param members Members to add to the set
     * @return The number of elements that were added to the set
     */
    CompletableFuture<Long> sadd(String key, String... members);

    /**
     * Get all members in a set.
     *
     * @param key Key of the set
     * @return All elements of the set
     */
    CompletableFuture<Set<String>> smembers(String key);

    /**
     * Remove one or more members from a set.
     *
     * @param key Key of the set
     * @param members Members to remove from the set
     * @return The number of elements that were removed from the set
     */
    CompletableFuture<Long> srem(String key, String... members);

    /**
     * Remove and return a random member from a set.
     *
     * @param key Key of the set
     * @return The removed element, or null when key does not exist
     */
    CompletableFuture<String> spop(String key);

    /**
     * Remove and return one or more random members from a set.
     *
     * @param key Key of the set
     * @param count Number of elements to pop
     * @return The removed elements
     */
    CompletableFuture<Set<String>> spop(String key, long count);

    /**
     * Get the number of members in a set.
     *
     * @param key Key of the set
     * @return The cardinality (number of elements) of the set
     */
    CompletableFuture<Long> scard(String key);

    /**
     * Determine if a given value is a member of a set.
     *
     * @param key Key of the set
     * @param member Member to check
     * @return True if the element is a member of the set, false otherwise
     */
    CompletableFuture<Boolean> sismember(String key, String member);

    /**
     * Determine if multiple values are members of a set.
     *
     * @param key Key of the set
     * @param members Members to check
     * @return List of booleans indicating membership of each element
     */
    CompletableFuture<List<Boolean>> smismember(String key, String... members);

    /**
     * Get a random member from a set.
     *
     * @param key Key of the set
     * @return Random element from the set
     */
    CompletableFuture<String> srandmember(String key);

    /**
     * Get multiple random members from a set.
     *
     * @param key Key of the set
     * @param count Number of elements to return
     * @return Random elements from the set
     */
    CompletableFuture<List<String>> srandmember(String key, int count);

    /**
     * Subtract multiple sets.
     *
     * @param keys Keys of the sets
     * @return The members of the resulting set
     */
    CompletableFuture<Set<String>> sdiff(String... keys);

    /**
     * Subtract multiple sets and store the resulting set in a key.
     *
     * @param dstkey Destination key
     * @param keys Source keys
     * @return The number of elements in the resulting set
     */
    CompletableFuture<Long> sdiffstore(String dstkey, String... keys);

    /**
     * Intersect multiple sets.
     *
     * @param keys Keys of the sets
     * @return The members of the resulting set
     */
    CompletableFuture<Set<String>> sinter(String... keys);

    /**
     * Intersect multiple sets and store the resulting set in a key.
     *
     * @param dstkey Destination key
     * @param keys Source keys
     * @return The number of elements in the resulting set
     */
    CompletableFuture<Long> sinterstore(String dstkey, String... keys);

    /**
     * Returns the cardinality of the set which would result from the intersection of all given sets.
     *
     * @param keys Keys of the sets
     * @return The cardinality of the resulting set
     */
    CompletableFuture<Long> sintercard(String... keys);

    /**
     * Returns the cardinality of the set which would result from the intersection of all given sets,
     * up to the given limit.
     *
     * @param limit Maximum number of elements to count
     * @param keys Keys of the sets
     * @return The cardinality of the resulting set, limited by the given count
     */
    CompletableFuture<Long> sintercard(int limit, String... keys);

    /**
     * Add multiple sets.
     *
     * @param keys Keys of the sets
     * @return The members of the resulting set
     */
    CompletableFuture<Set<String>> sunion(String... keys);

    /**
     * Add multiple sets and store the resulting set in a key.
     *
     * @param dstkey Destination key
     * @param keys Source keys
     * @return The number of elements in the resulting set
     */
    CompletableFuture<Long> sunionstore(String dstkey, String... keys);

    /**
     * Move a member from one set to another.
     *
     * @param srckey Source key
     * @param dstkey Destination key
     * @param member Member to move
     * @return 1 if the element was moved, 0 otherwise
     */
    CompletableFuture<Long> smove(String srckey, String dstkey, String member);

    /**
     * Incrementally iterate over a set.
     *
     * @param key Key of the set
     * @param cursor Cursor
     * @return Scan result with cursor and elements
     */
    default CompletableFuture<ScanResult<String>> sscan(String key, String cursor) {
        return sscan(key, cursor, new ScanParams());
    }

    /**
     * Incrementally iterate over a set.
     *
     * @param key Key of the set
     * @param cursor Cursor
     * @param params Scan parameters
     * @return Scan result with cursor and elements
     */
    CompletableFuture<ScanResult<String>> sscan(String key, String cursor, ScanParams params);
} 