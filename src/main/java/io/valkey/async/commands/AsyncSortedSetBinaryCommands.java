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
 * This interface provides async commands for Redis sorted set operations with binary data.
 */
public interface AsyncSortedSetBinaryCommands {

  /**
   * Binary version of ZADD command.
   * @param key the key
   * @param score the score
   * @param member the member
   * @return CompletableFuture that completes with 1 if member is added, 0 if score was updated
   */
  CompletableFuture<Long> zadd(byte[] key, double score, byte[] member);

  /**
   * Binary version of ZADD command with params.
   * @param key the key
   * @param score the score
   * @param member the member
   * @param params the ZAddParams
   * @return CompletableFuture that completes with 1 if member is added, 0 if score was updated
   */
  CompletableFuture<Long> zadd(byte[] key, double score, byte[] member, ZAddParams params);

  /**
   * Binary version of ZADD command for multiple members.
   * @param key the key
   * @param scoreMembers mapping of members to scores
   * @return CompletableFuture that completes with number of elements added
   */
  CompletableFuture<Long> zadd(byte[] key, Map<byte[], Double> scoreMembers);

  /**
   * Binary version of ZADD command for multiple members with params.
   * @param key the key
   * @param scoreMembers mapping of members to scores
   * @param params the ZAddParams
   * @return CompletableFuture that completes with number of elements added
   */
  CompletableFuture<Long> zadd(byte[] key, Map<byte[], Double> scoreMembers, ZAddParams params);

  /**
   * Binary version of ZADD INCR command.
   * @param key the key
   * @param score the increment
   * @param member the member
   * @param params the ZAddParams
   * @return CompletableFuture that completes with the new score
   */
  CompletableFuture<Double> zaddIncr(byte[] key, double score, byte[] member, ZAddParams params);

  /**
   * Binary version of ZREM command.
   * @param key the key
   * @param members the members to remove
   * @return CompletableFuture that completes with number of members removed
   */
  CompletableFuture<Long> zrem(byte[] key, byte[]... members);

  /**
   * Binary version of ZINCRBY command.
   * @param key the key
   * @param increment the increment
   * @param member the member
   * @return CompletableFuture that completes with the new score
   */
  CompletableFuture<Double> zincrby(byte[] key, double increment, byte[] member);

  /**
   * Binary version of ZINCRBY command with params.
   * @param key the key
   * @param increment the increment
   * @param member the member
   * @param params the ZIncrByParams
   * @return CompletableFuture that completes with the new score
   */
  CompletableFuture<Double> zincrby(byte[] key, double increment, byte[] member, ZIncrByParams params);

  /**
   * Binary version of ZRANK command.
   * @param key the key
   * @param member the member
   * @return CompletableFuture that completes with the rank or null if member doesn't exist
   */
  CompletableFuture<Long> zrank(byte[] key, byte[] member);

  /**
   * Binary version of ZREVRANK command.
   * @param key the key
   * @param member the member
   * @return CompletableFuture that completes with the rank or null if member doesn't exist
   */
  CompletableFuture<Long> zrevrank(byte[] key, byte[] member);

  /**
   * Binary version of ZRANK command with score.
   * @param key the key
   * @param member the member
   * @return CompletableFuture that completes with KeyValue containing rank and score
   */
  CompletableFuture<KeyValue<Long, Double>> zrankWithScore(byte[] key, byte[] member);

  /**
   * Binary version of ZREVRANK command with score.
   * @param key the key
   * @param member the member
   * @return CompletableFuture that completes with KeyValue containing rank and score
   */
  CompletableFuture<KeyValue<Long, Double>> zrevrankWithScore(byte[] key, byte[] member);

  /**
   * Binary version of ZRANGE command.
   * @param key the key
   * @param start the start index
   * @param stop the stop index
   * @return CompletableFuture that completes with list of elements
   */
  CompletableFuture<List<byte[]>> zrange(byte[] key, long start, long stop);

  /**
   * Binary version of ZREVRANGE command.
   * @param key the key
   * @param start the start index
   * @param stop the stop index
   * @return CompletableFuture that completes with list of elements
   */
  CompletableFuture<List<byte[]>> zrevrange(byte[] key, long start, long stop);

  /**
   * Binary version of ZRANGE command with scores.
   * @param key the key
   * @param start the start index
   * @param stop the stop index
   * @return CompletableFuture that completes with list of elements and their scores
   */
  CompletableFuture<List<Tuple>> zrangeWithScores(byte[] key, long start, long stop);

  /**
   * Binary version of ZREVRANGE command with scores.
   * @param key the key
   * @param start the start index
   * @param stop the stop index
   * @return CompletableFuture that completes with list of elements and their scores
   */
  CompletableFuture<List<Tuple>> zrevrangeWithScores(byte[] key, long start, long stop);

  /**
   * Binary version of ZRANGE command with params.
   * @param key the key
   * @param zRangeParams the range parameters
   * @return CompletableFuture that completes with list of elements
   */
  CompletableFuture<List<byte[]>> zrange(byte[] key, ZRangeParams zRangeParams);

  /**
   * Binary version of ZRANGE command with scores and params.
   * @param key the key
   * @param zRangeParams the range parameters
   * @return CompletableFuture that completes with list of elements and their scores
   */
  CompletableFuture<List<Tuple>> zrangeWithScores(byte[] key, ZRangeParams zRangeParams);

  /**
   * Binary version of ZRANGESTORE command.
   * @param dest the destination key
   * @param src the source key
   * @param zRangeParams the range parameters
   * @return CompletableFuture that completes with number of elements stored
   */
  CompletableFuture<Long> zrangestore(byte[] dest, byte[] src, ZRangeParams zRangeParams);

  /**
   * Binary version of ZRANDMEMBER command.
   * @param key the key
   * @return CompletableFuture that completes with random element
   */
  CompletableFuture<byte[]> zrandmember(byte[] key);

  /**
   * Binary version of ZRANDMEMBER command with count.
   * @param key the key
   * @param count number of elements to return
   * @return CompletableFuture that completes with list of random elements
   */
  CompletableFuture<List<byte[]>> zrandmember(byte[] key, long count);

  /**
   * Binary version of ZRANDMEMBER command with scores.
   * @param key the key
   * @param count number of elements to return
   * @return CompletableFuture that completes with list of random elements and their scores
   */
  CompletableFuture<List<Tuple>> zrandmemberWithScores(byte[] key, long count);

  /**
   * Binary version of ZCARD command.
   * @param key the key
   * @return CompletableFuture that completes with cardinality of set
   */
  CompletableFuture<Long> zcard(byte[] key);

  /**
   * Binary version of ZSCORE command.
   * @param key the key
   * @param member the member
   * @return CompletableFuture that completes with score or null if member doesn't exist
   */
  CompletableFuture<Double> zscore(byte[] key, byte[] member);

  /**
   * Binary version of ZMSCORE command.
   * @param key the key
   * @param members the members
   * @return CompletableFuture that completes with list of scores
   */
  CompletableFuture<List<Double>> zmscore(byte[] key, byte[]... members);

  /**
   * Binary version of ZPOPMAX command.
   * @param key the key
   * @return CompletableFuture that completes with popped member and score
   */
  CompletableFuture<Tuple> zpopmax(byte[] key);

  /**
   * Binary version of ZPOPMAX command with count.
   * @param key the key
   * @param count number of members to pop
   * @return CompletableFuture that completes with list of popped members and scores
   */
  CompletableFuture<List<Tuple>> zpopmax(byte[] key, int count);

  /**
   * Binary version of ZPOPMIN command.
   * @param key the key
   * @return CompletableFuture that completes with popped member and score
   */
  CompletableFuture<Tuple> zpopmin(byte[] key);

  /**
   * Binary version of ZPOPMIN command with count.
   * @param key the key
   * @param count number of members to pop
   * @return CompletableFuture that completes with list of popped members and scores
   */
  CompletableFuture<List<Tuple>> zpopmin(byte[] key, int count);

  /**
   * Binary version of ZCOUNT command.
   * @param key the key
   * @param min minimum score
   * @param max maximum score
   * @return CompletableFuture that completes with count of elements
   */
  CompletableFuture<Long> zcount(byte[] key, double min, double max);

  /**
   * Binary version of ZCOUNT command with string scores.
   * @param key the key
   * @param min minimum score
   * @param max maximum score
   * @return CompletableFuture that completes with count of elements
   */
  CompletableFuture<Long> zcount(byte[] key, byte[] min, byte[] max);

  /**
   * Binary version of ZRANGEBYSCORE command.
   * @param key the key
   * @param min minimum score
   * @param max maximum score
   * @return CompletableFuture that completes with list of elements
   */
  CompletableFuture<List<byte[]>> zrangeByScore(byte[] key, double min, double max);

  /**
   * Binary version of ZRANGEBYSCORE command with string scores.
   * @param key the key
   * @param min minimum score
   * @param max maximum score
   * @return CompletableFuture that completes with list of elements
   */
  CompletableFuture<List<byte[]>> zrangeByScore(byte[] key, byte[] min, byte[] max);

  /**
   * Binary version of ZRANGEBYSCORE command with offset and count.
   * @param key the key
   * @param min minimum score
   * @param max maximum score
   * @param offset starting offset
   * @param count max number of elements
   * @return CompletableFuture that completes with list of elements
   */
  CompletableFuture<List<byte[]>> zrangeByScore(byte[] key, double min, double max, int offset, int count);

  /**
   * Binary version of ZRANGEBYSCORE command with string scores, offset and count.
   * @param key the key
   * @param min minimum score
   * @param max maximum score
   * @param offset starting offset
   * @param count max number of elements
   * @return CompletableFuture that completes with list of elements
   */
  CompletableFuture<List<byte[]>> zrangeByScore(byte[] key, byte[] min, byte[] max, int offset, int count);

  /**
   * Binary version of ZRANGEBYSCORE command with scores.
   * @param key the key
   * @param min minimum score
   * @param max maximum score
   * @return CompletableFuture that completes with list of elements and their scores
   */
  CompletableFuture<List<Tuple>> zrangeByScoreWithScores(byte[] key, double min, double max);

  /**
   * Binary version of ZRANGEBYSCORE command with string scores and scores.
   * @param key the key
   * @param min minimum score
   * @param max maximum score
   * @return CompletableFuture that completes with list of elements and their scores
   */
  CompletableFuture<List<Tuple>> zrangeByScoreWithScores(byte[] key, byte[] min, byte[] max);

  /**
   * Binary version of ZRANGEBYSCORE command with scores, offset and count.
   * @param key the key
   * @param min minimum score
   * @param max maximum score
   * @param offset starting offset
   * @param count max number of elements
   * @return CompletableFuture that completes with list of elements and their scores
   */
  CompletableFuture<List<Tuple>> zrangeByScoreWithScores(byte[] key, double min, double max, int offset, int count);

  /**
   * Binary version of ZRANGEBYSCORE command with string scores, scores, offset and count.
   * @param key the key
   * @param min minimum score
   * @param max maximum score
   * @param offset starting offset
   * @param count max number of elements
   * @return CompletableFuture that completes with list of elements and their scores
   */
  CompletableFuture<List<Tuple>> zrangeByScoreWithScores(byte[] key, byte[] min, byte[] max, int offset, int count);

  /**
   * Binary version of ZREVRANGEBYSCORE command.
   * @param key the key
   * @param max maximum score
   * @param min minimum score
   * @return CompletableFuture that completes with list of elements
   */
  CompletableFuture<List<byte[]>> zrevrangeByScore(byte[] key, double max, double min);

  /**
   * Binary version of ZREVRANGEBYSCORE command with string scores.
   * @param key the key
   * @param max maximum score
   * @param min minimum score
   * @return CompletableFuture that completes with list of elements
   */
  CompletableFuture<List<byte[]>> zrevrangeByScore(byte[] key, byte[] max, byte[] min);

  /**
   * Binary version of ZREVRANGEBYSCORE command with offset and count.
   * @param key the key
   * @param max maximum score
   * @param min minimum score
   * @param offset starting offset
   * @param count max number of elements
   * @return CompletableFuture that completes with list of elements
   */
  CompletableFuture<List<byte[]>> zrevrangeByScore(byte[] key, double max, double min, int offset, int count);

  /**
   * Binary version of ZREVRANGEBYSCORE command with string scores, offset and count.
   * @param key the key
   * @param max maximum score
   * @param min minimum score
   * @param offset starting offset
   * @param count max number of elements
   * @return CompletableFuture that completes with list of elements
   */
  CompletableFuture<List<byte[]>> zrevrangeByScore(byte[] key, byte[] max, byte[] min, int offset, int count);

  /**
   * Binary version of ZREVRANGEBYSCORE command with scores.
   * @param key the key
   * @param max maximum score
   * @param min minimum score
   * @return CompletableFuture that completes with list of elements and their scores
   */
  CompletableFuture<List<Tuple>> zrevrangeByScoreWithScores(byte[] key, double max, double min);

  /**
   * Binary version of ZREVRANGEBYSCORE command with string scores and scores.
   * @param key the key
   * @param max maximum score
   * @param min minimum score
   * @return CompletableFuture that completes with list of elements and their scores
   */
  CompletableFuture<List<Tuple>> zrevrangeByScoreWithScores(byte[] key, byte[] max, byte[] min);

  /**
   * Binary version of ZREVRANGEBYSCORE command with scores, offset and count.
   * @param key the key
   * @param max maximum score
   * @param min minimum score
   * @param offset starting offset
   * @param count max number of elements
   * @return CompletableFuture that completes with list of elements and their scores
   */
  CompletableFuture<List<Tuple>> zrevrangeByScoreWithScores(byte[] key, double max, double min, int offset, int count);

  /**
   * Binary version of ZREVRANGEBYSCORE command with string scores, scores, offset and count.
   * @param key the key
   * @param max maximum score
   * @param min minimum score
   * @param offset starting offset
   * @param count max number of elements
   * @return CompletableFuture that completes with list of elements and their scores
   */
  CompletableFuture<List<Tuple>> zrevrangeByScoreWithScores(byte[] key, byte[] max, byte[] min, int offset, int count);

  /**
   * Binary version of ZREMRANGEBYRANK command.
   * @param key the key
   * @param start start rank
   * @param stop stop rank
   * @return CompletableFuture that completes with number of elements removed
   */
  CompletableFuture<Long> zremrangeByRank(byte[] key, long start, long stop);

  /**
   * Binary version of ZREMRANGEBYSCORE command.
   * @param key the key
   * @param min minimum score
   * @param max maximum score
   * @return CompletableFuture that completes with number of elements removed
   */
  CompletableFuture<Long> zremrangeByScore(byte[] key, double min, double max);

  /**
   * Binary version of ZREMRANGEBYSCORE command with string scores.
   * @param key the key
   * @param min minimum score
   * @param max maximum score
   * @return CompletableFuture that completes with number of elements removed
   */
  CompletableFuture<Long> zremrangeByScore(byte[] key, byte[] min, byte[] max);

  /**
   * Binary version of ZLEXCOUNT command.
   * @param key the key
   * @param min minimum value
   * @param max maximum value
   * @return CompletableFuture that completes with count of elements
   */
  CompletableFuture<Long> zlexcount(byte[] key, byte[] min, byte[] max);

  /**
   * Binary version of ZRANGEBYLEX command.
   * @param key the key
   * @param min minimum value
   * @param max maximum value
   * @return CompletableFuture that completes with list of elements
   */
  CompletableFuture<List<byte[]>> zrangeByLex(byte[] key, byte[] min, byte[] max);

  /**
   * Binary version of ZRANGEBYLEX command with offset and count.
   * @param key the key
   * @param min minimum value
   * @param max maximum value
   * @param offset starting offset
   * @param count max number of elements
   * @return CompletableFuture that completes with list of elements
   */
  CompletableFuture<List<byte[]>> zrangeByLex(byte[] key, byte[] min, byte[] max, int offset, int count);

  /**
   * Binary version of ZREVRANGEBYLEX command.
   * @param key the key
   * @param max maximum value
   * @param min minimum value
   * @return CompletableFuture that completes with list of elements
   */
  CompletableFuture<List<byte[]>> zrevrangeByLex(byte[] key, byte[] max, byte[] min);

  /**
   * Binary version of ZREVRANGEBYLEX command with offset and count.
   * @param key the key
   * @param max maximum value
   * @param min minimum value
   * @param offset starting offset
   * @param count max number of elements
   * @return CompletableFuture that completes with list of elements
   */
  CompletableFuture<List<byte[]>> zrevrangeByLex(byte[] key, byte[] max, byte[] min, int offset, int count);

  /**
   * Binary version of ZREMRANGEBYLEX command.
   * @param key the key
   * @param min minimum value
   * @param max maximum value
   * @return CompletableFuture that completes with number of elements removed
   */
  CompletableFuture<Long> zremrangeByLex(byte[] key, byte[] min, byte[] max);

  /**
   * Binary version of ZSCAN command.
   * @param key the key
   * @param cursor the cursor
   * @return CompletableFuture that completes with scan result
   */
  default CompletableFuture<ScanResult<Tuple>> zscan(byte[] key, byte[] cursor) {
    return zscan(key, cursor, new ScanParams());
  }

  /**
   * Binary version of ZSCAN command with params.
   * @param key the key
   * @param cursor the cursor
   * @param params scan parameters
   * @return CompletableFuture that completes with scan result
   */
  CompletableFuture<ScanResult<Tuple>> zscan(byte[] key, byte[] cursor, ScanParams params);

  /**
   * Binary version of BZPOPMAX command.
   * @param timeout max seconds to block
   * @param keys the keys
   * @return CompletableFuture that completes with key and popped member with score
   */
  CompletableFuture<KeyValue<byte[], Tuple>> bzpopmax(double timeout, byte[]... keys);

  /**
   * Binary version of BZPOPMIN command.
   * @param timeout max seconds to block
   * @param keys the keys
   * @return CompletableFuture that completes with key and popped member with score
   */
  CompletableFuture<KeyValue<byte[], Tuple>> bzpopmin(double timeout, byte[]... keys);

  /**
   * Binary version of ZDIFF command.
   * @param keys the keys
   * @return CompletableFuture that completes with resulting set
   */
  CompletableFuture<List<byte[]>> zdiff(byte[]... keys);

  /**
   * Binary version of ZDIFF command with scores.
   * @param keys the keys
   * @return CompletableFuture that completes with resulting set and scores
   */
  CompletableFuture<List<Tuple>> zdiffWithScores(byte[]... keys);

  /**
   * Binary version of ZDIFFSTORE command.
   * @param dstkey destination key
   * @param keys source keys
   * @return CompletableFuture that completes with number of elements in resulting set
   */
  CompletableFuture<Long> zdiffstore(byte[] dstkey, byte[]... keys);

  /**
   * Binary version of ZINTER command.
   * @param params intersection parameters
   * @param keys the keys
   * @return CompletableFuture that completes with resulting set
   */
  CompletableFuture<List<byte[]>> zinter(ZParams params, byte[]... keys);

  /**
   * Binary version of ZINTER command with scores.
   * @param params intersection parameters
   * @param keys the keys
   * @return CompletableFuture that completes with resulting set and scores
   */
  CompletableFuture<List<Tuple>> zinterWithScores(ZParams params, byte[]... keys);

  /**
   * Binary version of ZINTERSTORE command.
   * @param dstkey destination key
   * @param sets source keys
   * @return CompletableFuture that completes with number of elements in resulting set
   */
  CompletableFuture<Long> zinterstore(byte[] dstkey, byte[]... sets);

  /**
   * Binary version of ZINTERSTORE command with params.
   * @param dstkey destination key
   * @param params intersection parameters
   * @param sets source keys
   * @return CompletableFuture that completes with number of elements in resulting set
   */
  CompletableFuture<Long> zinterstore(byte[] dstkey, ZParams params, byte[]... sets);

  /**
   * Binary version of ZINTERCARD command.
   * @param keys the keys
   * @return CompletableFuture that completes with intersection cardinality
   */
  CompletableFuture<Long> zintercard(byte[]... keys);

  /**
   * Binary version of ZINTERCARD command with limit.
   * @param limit maximum cardinality
   * @param keys the keys
   * @return CompletableFuture that completes with intersection cardinality
   */
  CompletableFuture<Long> zintercard(long limit, byte[]... keys);

  /**
   * Binary version of ZUNION command.
   * @param params union parameters
   * @param keys the keys
   * @return CompletableFuture that completes with resulting set
   */
  CompletableFuture<List<byte[]>> zunion(ZParams params, byte[]... keys);

  /**
   * Binary version of ZUNION command with scores.
   * @param params union parameters
   * @param keys the keys
   * @return CompletableFuture that completes with resulting set and scores
   */
  CompletableFuture<List<Tuple>> zunionWithScores(ZParams params, byte[]... keys);

  /**
   * Binary version of ZUNIONSTORE command.
   * @param dstkey destination key
   * @param sets source keys
   * @return CompletableFuture that completes with number of elements in resulting set
   */
  CompletableFuture<Long> zunionstore(byte[] dstkey, byte[]... sets);

  /**
   * Binary version of ZUNIONSTORE command with params.
   * @param dstkey destination key
   * @param params union parameters
   * @param sets source keys
   * @return CompletableFuture that completes with number of elements in resulting set
   */
  CompletableFuture<Long> zunionstore(byte[] dstkey, ZParams params, byte[]... sets);

  /**
   * Binary version of ZMPOP command.
   * @param option min or max
   * @param keys the keys
   * @return CompletableFuture that completes with key and popped elements
   */
  CompletableFuture<KeyValue<byte[], List<Tuple>>> zmpop(SortedSetOption option, byte[]... keys);

  /**
   * Binary version of ZMPOP command with count.
   * @param option min or max
   * @param count number of elements to pop
   * @param keys the keys
   * @return CompletableFuture that completes with key and popped elements
   */
  CompletableFuture<KeyValue<byte[], List<Tuple>>> zmpop(SortedSetOption option, int count, byte[]... keys);

  /**
   * Binary version of BZMPOP command.
   * @param timeout max seconds to block
   * @param option min or max
   * @param keys the keys
   * @return CompletableFuture that completes with key and popped elements
   */
  CompletableFuture<KeyValue<byte[], List<Tuple>>> bzmpop(double timeout, SortedSetOption option, byte[]... keys);

  /**
   * Binary version of BZMPOP command with count.
   * @param timeout max seconds to block
   * @param option min or max
   * @param count number of elements to pop
   * @param keys the keys
   * @return CompletableFuture that completes with key and popped elements
   */
  CompletableFuture<KeyValue<byte[], List<Tuple>>> bzmpop(double timeout, SortedSetOption option, int count, byte[]... keys);
} 