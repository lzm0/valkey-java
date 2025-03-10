package io.valkey.async.commands;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import io.valkey.args.SortedSetOption;
import io.valkey.params.ScanParams;
import io.valkey.params.ZAddParams;
import io.valkey.params.ZIncrByParams;
import io.valkey.params.ZParams;
import io.valkey.params.ZRangeParams;
import io.valkey.resps.ScanResult;
import io.valkey.resps.Tuple;
import io.valkey.util.KeyValue;

/**
 * This interface provides async commands for Redis sorted set operations.
 */
public interface AsyncSortedSetCommands {

  /**
   * Add the specified member having the specified score to the sorted set stored at key.
   * @param key the key
   * @param score the score
   * @param member the member
   * @return CompletableFuture that completes with 1 if member is added, 0 if score was updated
   */
  CompletableFuture<Long> zadd(String key, double score, String member);

  /**
   * Add the specified member having the specified score to the sorted set stored at key with params.
   * @param key the key
   * @param score the score
   * @param member the member
   * @param params the ZAddParams
   * @return CompletableFuture that completes with 1 if member is added, 0 if score was updated
   */
  CompletableFuture<Long> zadd(String key, double score, String member, ZAddParams params);

  /**
   * Add multiple members with scores to the sorted set stored at key.
   * @param key the key
   * @param scoreMembers mapping of members to scores
   * @return CompletableFuture that completes with number of elements added
   */
  CompletableFuture<Long> zadd(String key, Map<String, Double> scoreMembers);

  /**
   * Add multiple members with scores to the sorted set stored at key with params.
   * @param key the key
   * @param scoreMembers mapping of members to scores
   * @param params the ZAddParams
   * @return CompletableFuture that completes with number of elements added
   */
  CompletableFuture<Long> zadd(String key, Map<String, Double> scoreMembers, ZAddParams params);

  /**
   * Increment the score of member in sorted set by increment.
   * @param key the key
   * @param score the increment
   * @param member the member
   * @param params the ZAddParams
   * @return CompletableFuture that completes with the new score
   */
  CompletableFuture<Double> zaddIncr(String key, double score, String member, ZAddParams params);

  /**
   * Remove members from the sorted set stored at key.
   * @param key the key
   * @param members the members to remove
   * @return CompletableFuture that completes with number of members removed
   */
  CompletableFuture<Long> zrem(String key, String... members);

  /**
   * Increment the score of member in sorted set by increment.
   * @param key the key
   * @param increment the increment
   * @param member the member
   * @return CompletableFuture that completes with the new score
   */
  CompletableFuture<Double> zincrby(String key, double increment, String member);

  /**
   * Increment the score of member in sorted set by increment with params.
   * @param key the key
   * @param increment the increment
   * @param member the member
   * @param params the ZIncrByParams
   * @return CompletableFuture that completes with the new score
   */
  CompletableFuture<Double> zincrby(String key, double increment, String member, ZIncrByParams params);

  /**
   * Get the rank of member in sorted set, with scores ordered from low to high.
   * @param key the key
   * @param member the member
   * @return CompletableFuture that completes with the rank or null if member doesn't exist
   */
  CompletableFuture<Long> zrank(String key, String member);

  /**
   * Get the rank of member in sorted set, with scores ordered from high to low.
   * @param key the key
   * @param member the member
   * @return CompletableFuture that completes with the rank or null if member doesn't exist
   */
  CompletableFuture<Long> zrevrank(String key, String member);

  /**
   * Get the rank and score of member in sorted set, with scores ordered from low to high.
   * @param key the key
   * @param member the member
   * @return CompletableFuture that completes with KeyValue containing rank and score
   */
  CompletableFuture<KeyValue<Long, Double>> zrankWithScore(String key, String member);

  /**
   * Get the rank and score of member in sorted set, with scores ordered from high to low.
   * @param key the key
   * @param member the member
   * @return CompletableFuture that completes with KeyValue containing rank and score
   */
  CompletableFuture<KeyValue<Long, Double>> zrevrankWithScore(String key, String member);

  /**
   * Return elements between start and stop from sorted set.
   * @param key the key
   * @param start the start index
   * @param stop the stop index
   * @return CompletableFuture that completes with list of elements
   */
  CompletableFuture<List<String>> zrange(String key, long start, long stop);

  /**
   * Return elements between start and stop from sorted set in reverse order.
   * @param key the key
   * @param start the start index
   * @param stop the stop index
   * @return CompletableFuture that completes with list of elements
   */
  CompletableFuture<List<String>> zrevrange(String key, long start, long stop);

  /**
   * Return elements with scores between start and stop from sorted set.
   * @param key the key
   * @param start the start index
   * @param stop the stop index
   * @return CompletableFuture that completes with list of elements and their scores
   */
  CompletableFuture<List<Tuple>> zrangeWithScores(String key, long start, long stop);

  /**
   * Return elements with scores between start and stop from sorted set in reverse order.
   * @param key the key
   * @param start the start index
   * @param stop the stop index
   * @return CompletableFuture that completes with list of elements and their scores
   */
  CompletableFuture<List<Tuple>> zrevrangeWithScores(String key, long start, long stop);

  /**
   * Return elements from sorted set by range with params.
   * @param key the key
   * @param zRangeParams the range parameters
   * @return CompletableFuture that completes with list of elements
   */
  CompletableFuture<List<String>> zrange(String key, ZRangeParams zRangeParams);

  /**
   * Return elements with scores from sorted set by range with params.
   * @param key the key
   * @param zRangeParams the range parameters
   * @return CompletableFuture that completes with list of elements and their scores
   */
  CompletableFuture<List<Tuple>> zrangeWithScores(String key, ZRangeParams zRangeParams);

  /**
   * Store result of range operation in destination key.
   * @param dest the destination key
   * @param src the source key
   * @param zRangeParams the range parameters
   * @return CompletableFuture that completes with number of elements stored
   */
  CompletableFuture<Long> zrangestore(String dest, String src, ZRangeParams zRangeParams);

  /**
   * Get a random element from sorted set.
   * @param key the key
   * @return CompletableFuture that completes with random element
   */
  CompletableFuture<String> zrandmember(String key);

  /**
   * Get multiple random elements from sorted set.
   * @param key the key
   * @param count number of elements to return
   * @return CompletableFuture that completes with list of random elements
   */
  CompletableFuture<List<String>> zrandmember(String key, long count);

  /**
   * Get multiple random elements with scores from sorted set.
   * @param key the key
   * @param count number of elements to return
   * @return CompletableFuture that completes with list of random elements and their scores
   */
  CompletableFuture<List<Tuple>> zrandmemberWithScores(String key, long count);

  /**
   * Get the number of elements in sorted set.
   * @param key the key
   * @return CompletableFuture that completes with cardinality of set
   */
  CompletableFuture<Long> zcard(String key);

  /**
   * Get score of member in sorted set.
   * @param key the key
   * @param member the member
   * @return CompletableFuture that completes with score or null if member doesn't exist
   */
  CompletableFuture<Double> zscore(String key, String member);

  /**
   * Get scores for multiple members in sorted set.
   * @param key the key
   * @param members the members
   * @return CompletableFuture that completes with list of scores
   */
  CompletableFuture<List<Double>> zmscore(String key, String... members);

  /**
   * Remove and return member with highest score from sorted set.
   * @param key the key
   * @return CompletableFuture that completes with popped member and score
   */
  CompletableFuture<Tuple> zpopmax(String key);

  /**
   * Remove and return multiple members with highest scores from sorted set.
   * @param key the key
   * @param count number of members to pop
   * @return CompletableFuture that completes with list of popped members and scores
   */
  CompletableFuture<List<Tuple>> zpopmax(String key, int count);

  /**
   * Remove and return member with lowest score from sorted set.
   * @param key the key
   * @return CompletableFuture that completes with popped member and score
   */
  CompletableFuture<Tuple> zpopmin(String key);

  /**
   * Remove and return multiple members with lowest scores from sorted set.
   * @param key the key
   * @param count number of members to pop
   * @return CompletableFuture that completes with list of popped members and scores
   */
  CompletableFuture<List<Tuple>> zpopmin(String key, int count);

  /**
   * Count elements in sorted set with scores between min and max.
   * @param key the key
   * @param min minimum score
   * @param max maximum score
   * @return CompletableFuture that completes with count of elements
   */
  CompletableFuture<Long> zcount(String key, double min, double max);

  /**
   * Count elements in sorted set with scores between min and max.
   * @param key the key
   * @param min minimum score
   * @param max maximum score
   * @return CompletableFuture that completes with count of elements
   */
  CompletableFuture<Long> zcount(String key, String min, String max);

  /**
   * Return elements with scores between min and max.
   * @param key the key
   * @param min minimum score
   * @param max maximum score
   * @return CompletableFuture that completes with list of elements
   */
  CompletableFuture<List<String>> zrangeByScore(String key, double min, double max);

  /**
   * Return elements with scores between min and max.
   * @param key the key
   * @param min minimum score
   * @param max maximum score
   * @return CompletableFuture that completes with list of elements
   */
  CompletableFuture<List<String>> zrangeByScore(String key, String min, String max);

  /**
   * Return elements with scores between min and max with offset and count.
   * @param key the key
   * @param min minimum score
   * @param max maximum score
   * @param offset starting offset
   * @param count max number of elements
   * @return CompletableFuture that completes with list of elements
   */
  CompletableFuture<List<String>> zrangeByScore(String key, double min, double max, int offset, int count);

  /**
   * Return elements with scores between min and max with offset and count.
   * @param key the key
   * @param min minimum score
   * @param max maximum score
   * @param offset starting offset
   * @param count max number of elements
   * @return CompletableFuture that completes with list of elements
   */
  CompletableFuture<List<String>> zrangeByScore(String key, String min, String max, int offset, int count);

  /**
   * Return elements with scores between min and max.
   * @param key the key
   * @param min minimum score
   * @param max maximum score
   * @return CompletableFuture that completes with list of elements and their scores
   */
  CompletableFuture<List<Tuple>> zrangeByScoreWithScores(String key, double min, double max);

  /**
   * Return elements with scores between min and max.
   * @param key the key
   * @param min minimum score
   * @param max maximum score
   * @return CompletableFuture that completes with list of elements and their scores
   */
  CompletableFuture<List<Tuple>> zrangeByScoreWithScores(String key, String min, String max);

  /**
   * Return elements with scores between min and max with offset and count.
   * @param key the key
   * @param min minimum score
   * @param max maximum score
   * @param offset starting offset
   * @param count max number of elements
   * @return CompletableFuture that completes with list of elements and their scores
   */
  CompletableFuture<List<Tuple>> zrangeByScoreWithScores(String key, double min, double max, int offset, int count);

  /**
   * Return elements with scores between min and max with offset and count.
   * @param key the key
   * @param min minimum score
   * @param max maximum score
   * @param offset starting offset
   * @param count max number of elements
   * @return CompletableFuture that completes with list of elements and their scores
   */
  CompletableFuture<List<Tuple>> zrangeByScoreWithScores(String key, String min, String max, int offset, int count);

  /**
   * Return elements with scores between max and min in reverse order.
   * @param key the key
   * @param max maximum score
   * @param min minimum score
   * @return CompletableFuture that completes with list of elements
   */
  CompletableFuture<List<String>> zrevrangeByScore(String key, double max, double min);

  /**
   * Return elements with scores between max and min in reverse order.
   * @param key the key
   * @param max maximum score
   * @param min minimum score
   * @return CompletableFuture that completes with list of elements
   */
  CompletableFuture<List<String>> zrevrangeByScore(String key, String max, String min);

  /**
   * Return elements with scores between max and min in reverse order with offset and count.
   * @param key the key
   * @param max maximum score
   * @param min minimum score
   * @param offset starting offset
   * @param count max number of elements
   * @return CompletableFuture that completes with list of elements
   */
  CompletableFuture<List<String>> zrevrangeByScore(String key, double max, double min, int offset, int count);

  /**
   * Return elements with scores between max and min in reverse order with offset and count.
   * @param key the key
   * @param max maximum score
   * @param min minimum score
   * @param offset starting offset
   * @param count max number of elements
   * @return CompletableFuture that completes with list of elements
   */
  CompletableFuture<List<String>> zrevrangeByScore(String key, String max, String min, int offset, int count);

  /**
   * Return elements with scores between max and min in reverse order.
   * @param key the key
   * @param max maximum score
   * @param min minimum score
   * @return CompletableFuture that completes with list of elements and their scores
   */
  CompletableFuture<List<Tuple>> zrevrangeByScoreWithScores(String key, double max, double min);

  /**
   * Return elements with scores between max and min in reverse order.
   * @param key the key
   * @param max maximum score
   * @param min minimum score
   * @return CompletableFuture that completes with list of elements and their scores
   */
  CompletableFuture<List<Tuple>> zrevrangeByScoreWithScores(String key, String max, String min);

  /**
   * Return elements with scores between max and min in reverse order with offset and count.
   * @param key the key
   * @param max maximum score
   * @param min minimum score
   * @param offset starting offset
   * @param count max number of elements
   * @return CompletableFuture that completes with list of elements and their scores
   */
  CompletableFuture<List<Tuple>> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count);

  /**
   * Return elements with scores between max and min in reverse order with offset and count.
   * @param key the key
   * @param max maximum score
   * @param min minimum score
   * @param offset starting offset
   * @param count max number of elements
   * @return CompletableFuture that completes with list of elements and their scores
   */
  CompletableFuture<List<Tuple>> zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count);

  /**
   * Remove elements between start and stop ranks.
   * @param key the key
   * @param start start rank
   * @param stop stop rank
   * @return CompletableFuture that completes with number of elements removed
   */
  CompletableFuture<Long> zremrangeByRank(String key, long start, long stop);

  /**
   * Remove elements with scores between min and max.
   * @param key the key
   * @param min minimum score
   * @param max maximum score
   * @return CompletableFuture that completes with number of elements removed
   */
  CompletableFuture<Long> zremrangeByScore(String key, double min, double max);

  /**
   * Remove elements with scores between min and max.
   * @param key the key
   * @param min minimum score
   * @param max maximum score
   * @return CompletableFuture that completes with number of elements removed
   */
  CompletableFuture<Long> zremrangeByScore(String key, String min, String max);

  /**
   * Count elements with lexicographical values between min and max.
   * @param key the key
   * @param min minimum value
   * @param max maximum value
   * @return CompletableFuture that completes with count of elements
   */
  CompletableFuture<Long> zlexcount(String key, String min, String max);

  /**
   * Return elements with lexicographical values between min and max.
   * @param key the key
   * @param min minimum value
   * @param max maximum value
   * @return CompletableFuture that completes with list of elements
   */
  CompletableFuture<List<String>> zrangeByLex(String key, String min, String max);

  /**
   * Return elements with lexicographical values between min and max with offset and count.
   * @param key the key
   * @param min minimum value
   * @param max maximum value
   * @param offset starting offset
   * @param count max number of elements
   * @return CompletableFuture that completes with list of elements
   */
  CompletableFuture<List<String>> zrangeByLex(String key, String min, String max, int offset, int count);

  /**
   * Return elements with lexicographical values between max and min in reverse order.
   * @param key the key
   * @param max maximum value
   * @param min minimum value
   * @return CompletableFuture that completes with list of elements
   */
  CompletableFuture<List<String>> zrevrangeByLex(String key, String max, String min);

  /**
   * Return elements with lexicographical values between max and min in reverse order with offset and count.
   * @param key the key
   * @param max maximum value
   * @param min minimum value
   * @param offset starting offset
   * @param count max number of elements
   * @return CompletableFuture that completes with list of elements
   */
  CompletableFuture<List<String>> zrevrangeByLex(String key, String max, String min, int offset, int count);

  /**
   * Remove elements with lexicographical values between min and max.
   * @param key the key
   * @param min minimum value
   * @param max maximum value
   * @return CompletableFuture that completes with number of elements removed
   */
  CompletableFuture<Long> zremrangeByLex(String key, String min, String max);

  /**
   * Incrementally iterate sorted set elements and scores.
   * @param key the key
   * @param cursor the cursor
   * @return CompletableFuture that completes with scan result
   */
  default CompletableFuture<ScanResult<Tuple>> zscan(String key, String cursor) {
    return zscan(key, cursor, new ScanParams());
  }

  /**
   * Incrementally iterate sorted set elements and scores.
   * @param key the key
   * @param cursor the cursor
   * @param params scan parameters
   * @return CompletableFuture that completes with scan result
   */
  CompletableFuture<ScanResult<Tuple>> zscan(String key, String cursor, ScanParams params);

  /**
   * Blocking version of ZPOPMAX.
   * @param timeout max seconds to block
   * @param keys the keys
   * @return CompletableFuture that completes with key and popped member with score
   */
  CompletableFuture<KeyValue<String, Tuple>> bzpopmax(double timeout, String... keys);

  /**
   * Blocking version of ZPOPMIN.
   * @param timeout max seconds to block
   * @param keys the keys
   * @return CompletableFuture that completes with key and popped member with score
   */
  CompletableFuture<KeyValue<String, Tuple>> bzpopmin(double timeout, String... keys);

  /**
   * Get the difference between multiple sorted sets.
   * @param keys the keys
   * @return CompletableFuture that completes with resulting set
   */
  CompletableFuture<List<String>> zdiff(String... keys);

  /**
   * Get the difference between multiple sorted sets with scores.
   * @param keys the keys
   * @return CompletableFuture that completes with resulting set and scores
   */
  CompletableFuture<List<Tuple>> zdiffWithScores(String... keys);

  /**
   * Store the difference of multiple sorted sets in a key.
   * @param dstkey destination key
   * @param keys source keys
   * @return CompletableFuture that completes with number of elements in resulting set
   */
  CompletableFuture<Long> zdiffstore(String dstkey, String... keys);

  /**
   * Get the intersection between multiple sorted sets.
   * @param params intersection parameters
   * @param keys the keys
   * @return CompletableFuture that completes with resulting set
   */
  CompletableFuture<List<String>> zinter(ZParams params, String... keys);

  /**
   * Get the intersection between multiple sorted sets with scores.
   * @param params intersection parameters
   * @param keys the keys
   * @return CompletableFuture that completes with resulting set and scores
   */
  CompletableFuture<List<Tuple>> zinterWithScores(ZParams params, String... keys);

  /**
   * Store the intersection of multiple sorted sets in a key.
   * @param dstkey destination key
   * @param sets source keys
   * @return CompletableFuture that completes with number of elements in resulting set
   */
  CompletableFuture<Long> zinterstore(String dstkey, String... sets);

  /**
   * Store the intersection of multiple sorted sets in a key.
   * @param dstkey destination key
   * @param params intersection parameters
   * @param sets source keys
   * @return CompletableFuture that completes with number of elements in resulting set
   */
  CompletableFuture<Long> zinterstore(String dstkey, ZParams params, String... sets);

  /**
   * Get the cardinality of intersection between multiple sorted sets.
   * @param keys the keys
   * @return CompletableFuture that completes with intersection cardinality
   */
  CompletableFuture<Long> zintercard(String... keys);

  /**
   * Get the cardinality of intersection between multiple sorted sets with limit.
   * @param limit maximum cardinality
   * @param keys the keys
   * @return CompletableFuture that completes with intersection cardinality
   */
  CompletableFuture<Long> zintercard(long limit, String... keys);

  /**
   * Get the union between multiple sorted sets.
   * @param params union parameters
   * @param keys the keys
   * @return CompletableFuture that completes with resulting set
   */
  CompletableFuture<List<String>> zunion(ZParams params, String... keys);

  /**
   * Get the union between multiple sorted sets with scores.
   * @param params union parameters
   * @param keys the keys
   * @return CompletableFuture that completes with resulting set and scores
   */
  CompletableFuture<List<Tuple>> zunionWithScores(ZParams params, String... keys);

  /**
   * Store the union of multiple sorted sets in a key.
   * @param dstkey destination key
   * @param sets source keys
   * @return CompletableFuture that completes with number of elements in resulting set
   */
  CompletableFuture<Long> zunionstore(String dstkey, String... sets);

  /**
   * Store the union of multiple sorted sets in a key.
   * @param dstkey destination key
   * @param params union parameters
   * @param sets source keys
   * @return CompletableFuture that completes with number of elements in resulting set
   */
  CompletableFuture<Long> zunionstore(String dstkey, ZParams params, String... sets);

  /**
   * Pop elements from sorted sets by min or max score.
   * @param option min or max
   * @param keys the keys
   * @return CompletableFuture that completes with key and popped elements
   */
  CompletableFuture<KeyValue<String, List<Tuple>>> zmpop(SortedSetOption option, String... keys);

  /**
   * Pop multiple elements from sorted sets by min or max score.
   * @param option min or max
   * @param count number of elements to pop
   * @param keys the keys
   * @return CompletableFuture that completes with key and popped elements
   */
  CompletableFuture<KeyValue<String, List<Tuple>>> zmpop(SortedSetOption option, int count, String... keys);

  /**
   * Blocking version of ZMPOP.
   * @param timeout max seconds to block
   * @param option min or max
   * @param keys the keys
   * @return CompletableFuture that completes with key and popped elements
   */
  CompletableFuture<KeyValue<String, List<Tuple>>> bzmpop(double timeout, SortedSetOption option, String... keys);

  /**
   * Blocking version of ZMPOP with count.
   * @param timeout max seconds to block
   * @param option min or max
   * @param count number of elements to pop
   * @param keys the keys
   * @return CompletableFuture that completes with key and popped elements
   */
  CompletableFuture<KeyValue<String, List<Tuple>>> bzmpop(double timeout, SortedSetOption option, int count, String... keys);
} 