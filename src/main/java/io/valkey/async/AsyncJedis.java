package io.valkey.async;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import io.valkey.CommandObject;
import io.valkey.CommandObjects;
import io.valkey.GeoCoordinate;
import io.valkey.StreamEntryID;
import io.valkey.args.*;
import io.valkey.async.commands.*;
import io.valkey.executors.AsyncCommandExecutor;
import io.valkey.params.*;
import io.valkey.providers.ConnectionProvider;
import io.valkey.resps.*;
import io.valkey.util.KeyValue;

import io.valkey.async.commands.AsyncJedisCommands;

public class AsyncJedis implements AsyncJedisCommands, AsyncJedisBinaryCommands, AsyncServerCommands,
    AsyncSampleKeyedCommands, AsyncSampleBinaryKeyedCommands, AutoCloseable {
    protected final ConnectionProvider provider;
    protected final AsyncCommandExecutor executor;
    protected final CommandObjects commandObjects;

    public AsyncJedis(ConnectionProvider provider) {
        this.provider = provider;
        this.executor = new AsyncCommandExecutor(provider);
        this.commandObjects = new CommandObjects();
    }

    public AsyncJedis(AsyncCommandExecutor executor, ConnectionProvider provider) {
        this.provider = provider;
        this.executor = executor;
        this.commandObjects = new CommandObjects();
    }

    @Override
    public void close() {
        executor.close();
    }

    protected <T> CompletableFuture<T> executeCommand(CommandObject<T> commandObject) {
        return executor.executeCommandAsync(commandObject);
    }

    // String commands
    @Override
    public CompletableFuture<String> set(String key, String value) {
        return executeCommand(commandObjects.set(key, value));
    }

    @Override
    public CompletableFuture<String> set(String key, String value, SetParams params) {
        return executeCommand(commandObjects.set(key, value, params));
    }

    @Override
    public CompletableFuture<String> get(String key) {
        return executeCommand(commandObjects.get(key));
    }

    @Override
    public CompletableFuture<String> setGet(String key, String value) {
        return executeCommand(commandObjects.setGet(key, value));
    }

    @Override
    public CompletableFuture<String> setGet(String key, String value, SetParams params) {
        return executeCommand(commandObjects.setGet(key, value, params));
    }

    @Override
    public CompletableFuture<String> getDel(String key) {
        return executeCommand(commandObjects.getDel(key));
    }

    @Override
    public CompletableFuture<String> getEx(String key, GetExParams params) {
        return executeCommand(commandObjects.getEx(key, params));
    }

    @Override
    public CompletableFuture<String> set(byte[] key, byte[] value) {
        return executeCommand(commandObjects.set(key, value));
    }

    @Override
    public CompletableFuture<String> set(byte[] key, byte[] value, SetParams params) {
        return executeCommand(commandObjects.set(key, value, params));
    }

    @Override
    public CompletableFuture<byte[]> get(byte[] key) {
        return executeCommand(commandObjects.get(key));
    }

    @Override
    public CompletableFuture<byte[]> setGet(byte[] key, byte[] value) {
        return executeCommand(commandObjects.setGet(key, value));
    }

    @Override
    public CompletableFuture<byte[]> setGet(byte[] key, byte[] value, SetParams params) {
        return executeCommand(commandObjects.setGet(key, value, params));
    }

    @Override
    public CompletableFuture<byte[]> getDel(byte[] key) {
        return executeCommand(commandObjects.getDel(key));
    }

    @Override
    public CompletableFuture<byte[]> getEx(byte[] key, GetExParams params) {
        return executeCommand(commandObjects.getEx(key, params));
    }

    // Bit commands
    @Override
    public CompletableFuture<Boolean> setbit(String key, long offset, boolean value) {
        return executeCommand(commandObjects.setbit(key, offset, value));
    }

    @Override
    public CompletableFuture<Boolean> getbit(String key, long offset) {
        return executeCommand(commandObjects.getbit(key, offset));
    }

    @Override
    public CompletableFuture<Long> bitcount(String key) {
        return executeCommand(commandObjects.bitcount(key));
    }

    @Override
    public CompletableFuture<Long> bitcount(String key, long start, long end) {
        return executeCommand(commandObjects.bitcount(key, start, end));
    }

    @Override
    public CompletableFuture<Long> bitcount(String key, long start, long end, BitCountOption option) {
        return executeCommand(commandObjects.bitcount(key, start, end, option));
    }

    @Override
    public CompletableFuture<Long> bitpos(String key, boolean value) {
        return executeCommand(commandObjects.bitpos(key, value));
    }

    @Override
    public CompletableFuture<Long> bitpos(String key, boolean value, BitPosParams params) {
        return executeCommand(commandObjects.bitpos(key, value, params));
    }

    @Override
    public CompletableFuture<List<Long>> bitfield(String key, String... arguments) {
        return executeCommand(commandObjects.bitfield(key, arguments));
    }

    @Override
    public CompletableFuture<List<Long>> bitfieldReadonly(String key, String... arguments) {
        return executeCommand(commandObjects.bitfieldReadonly(key, arguments));
    }

    @Override
    public CompletableFuture<Long> bitop(BitOP op, String destKey, String... srcKeys) {
        return executeCommand(commandObjects.bitop(op, destKey, srcKeys));
    }

    // Key commands
    @Override
    public CompletableFuture<Boolean> exists(String key) {
        return executeCommand(commandObjects.exists(key));
    }

    @Override
    public CompletableFuture<Long> exists(String... keys) {
        return executeCommand(commandObjects.exists(keys));
    }

    @Override
    public CompletableFuture<Long> persist(String key) {
        return executeCommand(commandObjects.persist(key));
    }

    @Override
    public CompletableFuture<String> type(String key) {
        return executeCommand(commandObjects.type(key));
    }

    @Override
    public CompletableFuture<byte[]> dump(String key) {
        return executeCommand(commandObjects.dump(key));
    }

    @Override
    public CompletableFuture<String> restore(String key, long ttl, byte[] serializedValue) {
        return executeCommand(commandObjects.restore(key, ttl, serializedValue));
    }

    @Override
    public CompletableFuture<String> restore(String key, long ttl, byte[] serializedValue, RestoreParams params) {
        return executeCommand(commandObjects.restore(key, ttl, serializedValue, params));
    }

    @Override
    public CompletableFuture<Long> expire(String key, long seconds) {
        return executeCommand(commandObjects.expire(key, seconds));
    }

    @Override
    public CompletableFuture<Long> expire(String key, long seconds, ExpiryOption expiryOption) {
        return executeCommand(commandObjects.expire(key, seconds, expiryOption));
    }

    @Override
    public CompletableFuture<Long> pexpire(String key, long milliseconds) {
        return executeCommand(commandObjects.pexpire(key, milliseconds));
    }

    @Override
    public CompletableFuture<Long> pexpire(String key, long milliseconds, ExpiryOption expiryOption) {
        return executeCommand(commandObjects.pexpire(key, milliseconds, expiryOption));
    }

    @Override
    public CompletableFuture<Long> expireTime(String key) {
        return executeCommand(commandObjects.expireTime(key));
    }

    @Override
    public CompletableFuture<Long> pexpireTime(String key) {
        return executeCommand(commandObjects.pexpireTime(key));
    }

    @Override
    public CompletableFuture<Long> expireAt(String key, long unixTime) {
        return executeCommand(commandObjects.expireAt(key, unixTime));
    }

    @Override
    public CompletableFuture<Long> expireAt(String key, long unixTime, ExpiryOption expiryOption) {
        return executeCommand(commandObjects.expireAt(key, unixTime, expiryOption));
    }

    @Override
    public CompletableFuture<Long> pexpireAt(String key, long millisecondsTimestamp) {
        return executeCommand(commandObjects.pexpireAt(key, millisecondsTimestamp));
    }

    @Override
    public CompletableFuture<Long> ttl(String key) {
        return executeCommand(commandObjects.ttl(key));
    }

    @Override
    public CompletableFuture<Long> pttl(String key) {
        return executeCommand(commandObjects.pttl(key));
    }

    @Override
    public CompletableFuture<Long> touch(String key) {
        return executeCommand(commandObjects.touch(key));
    }

    @Override
    public CompletableFuture<Long> touch(String... keys) {
        return executeCommand(commandObjects.touch(keys));
    }

    @Override
    public CompletableFuture<List<String>> sort(String key) {
        return executeCommand(commandObjects.sort(key));
    }

    @Override
    public CompletableFuture<List<String>> sort(String key, SortingParams sortingParams) {
        return executeCommand(commandObjects.sort(key, sortingParams));
    }

    @Override
    public CompletableFuture<Long> del(String key) {
        return executeCommand(commandObjects.del(key));
    }

    @Override
    public CompletableFuture<Long> del(String... keys) {
        return executeCommand(commandObjects.del(keys));
    }

    @Override
    public CompletableFuture<Long> unlink(String key) {
        return executeCommand(commandObjects.unlink(key));
    }

    @Override
    public CompletableFuture<Long> unlink(String... keys) {
        return executeCommand(commandObjects.unlink(keys));
    }

    // Hash commands
    @Override
    public CompletableFuture<Long> hset(String key, String field, String value) {
        return executeCommand(commandObjects.hset(key, field, value));
    }

    @Override
    public CompletableFuture<Long> hset(String key, Map<String, String> hash) {
        return executeCommand(commandObjects.hset(key, hash));
    }

    @Override
    public CompletableFuture<String> hget(String key, String field) {
        return executeCommand(commandObjects.hget(key, field));
    }

    @Override
    public CompletableFuture<Long> hsetnx(String key, String field, String value) {
        return executeCommand(commandObjects.hsetnx(key, field, value));
    }

    @Override
    public CompletableFuture<String> hmset(String key, Map<String, String> hash) {
        return executeCommand(commandObjects.hmset(key, hash));
    }

    @Override
    public CompletableFuture<List<String>> hmget(String key, String... fields) {
        return executeCommand(commandObjects.hmget(key, fields));
    }

    @Override
    public CompletableFuture<Long> hincrBy(String key, String field, long value) {
        return executeCommand(commandObjects.hincrBy(key, field, value));
    }

    @Override
    public CompletableFuture<Double> hincrByFloat(String key, String field, double value) {
        return executeCommand(commandObjects.hincrByFloat(key, field, value));
    }

    @Override
    public CompletableFuture<Boolean> hexists(String key, String field) {
        return executeCommand(commandObjects.hexists(key, field));
    }

    @Override
    public CompletableFuture<Long> hdel(String key, String... field) {
        return executeCommand(commandObjects.hdel(key, field));
    }

    @Override
    public CompletableFuture<Long> hlen(String key) {
        return executeCommand(commandObjects.hlen(key));
    }

    @Override
    public CompletableFuture<Set<String>> hkeys(String key) {
        return executeCommand(commandObjects.hkeys(key));
    }

    @Override
    public CompletableFuture<List<String>> hvals(String key) {
        return executeCommand(commandObjects.hvals(key));
    }

    @Override
    public CompletableFuture<Map<String, String>> hgetAll(String key) {
        return executeCommand(commandObjects.hgetAll(key));
    }

    @Override
    public CompletableFuture<String> hrandfield(String key) {
        return executeCommand(commandObjects.hrandfield(key));
    }

    @Override
    public CompletableFuture<List<String>> hrandfield(String key, long count) {
        return executeCommand(commandObjects.hrandfield(key, count));
    }

    // Set commands
    @Override
    public CompletableFuture<Long> sadd(String key, String... members) {
        return executeCommand(commandObjects.sadd(key, members));
    }

    @Override
    public CompletableFuture<Set<String>> smembers(String key) {
        return executeCommand(commandObjects.smembers(key));
    }

    @Override
    public CompletableFuture<Long> srem(String key, String... members) {
        return executeCommand(commandObjects.srem(key, members));
    }

    @Override
    public CompletableFuture<String> spop(String key) {
        return executeCommand(commandObjects.spop(key));
    }

    @Override
    public CompletableFuture<Set<String>> spop(String key, long count) {
        return executeCommand(commandObjects.spop(key, count));
    }

    @Override
    public CompletableFuture<Long> scard(String key) {
        return executeCommand(commandObjects.scard(key));
    }

    @Override
    public CompletableFuture<Boolean> sismember(String key, String member) {
        return executeCommand(commandObjects.sismember(key, member));
    }

    @Override
    public CompletableFuture<List<Boolean>> smismember(String key, String... members) {
        return executeCommand(commandObjects.smismember(key, members));
    }

    // Scripting commands
    @Override
    public CompletableFuture<Object> eval(String script) {
        return executeCommand(commandObjects.eval(script));
    }

    @Override
    public CompletableFuture<Object> eval(String script, int keyCount, String... params) {
        return executeCommand(commandObjects.eval(script, keyCount, params));
    }

    @Override
    public CompletableFuture<Object> eval(String script, List<String> keys, List<String> args) {
        return executeCommand(commandObjects.eval(script, keys, args));
    }

    @Override
    public CompletableFuture<Object> evalReadonly(String script, List<String> keys, List<String> args) {
        return executeCommand(commandObjects.evalReadonly(script, keys, args));
    }

    @Override
    public CompletableFuture<Object> evalsha(String sha1) {
        return executeCommand(commandObjects.evalsha(sha1));
    }

    @Override
    public CompletableFuture<Object> evalsha(String sha1, int keyCount, String... params) {
        return executeCommand(commandObjects.evalsha(sha1, keyCount, params));
    }

    @Override
    public CompletableFuture<Object> evalsha(String sha1, List<String> keys, List<String> args) {
        return executeCommand(commandObjects.evalsha(sha1, keys, args));
    }

    @Override
    public CompletableFuture<Object> evalshaReadonly(String sha1, List<String> keys, List<String> args) {
        return executeCommand(commandObjects.evalshaReadonly(sha1, keys, args));
    }

    @Override
    public CompletableFuture<String> scriptLoad(String script, String sampleKey) {
        return executeCommand(commandObjects.scriptLoad(script, sampleKey));
    }

    @Override
    public CompletableFuture<String> scriptFlush(String sampleKey) {
        return executeCommand(commandObjects.scriptFlush(sampleKey));
    }

    @Override
    public CompletableFuture<String> scriptFlush(String sampleKey, FlushMode flushMode) {
        return executeCommand(commandObjects.scriptFlush(sampleKey, flushMode));
    }

    @Override
    public CompletableFuture<String> scriptKill(String sampleKey) {
        return executeCommand(commandObjects.scriptKill(sampleKey));
    }

    @Override
    public CompletableFuture<List<Boolean>> scriptExists(String sampleKey, String... sha1s) {
        return executeCommand(commandObjects.scriptExists(sampleKey, sha1s));
    }

    // Function commands
    @Override
    public CompletableFuture<Object> fcall(String name, List<String> keys, List<String> args) {
        return executeCommand(commandObjects.fcall(name, keys, args));
    }

    @Override
    public CompletableFuture<Object> fcallReadonly(String name, List<String> keys, List<String> args) {
        return executeCommand(commandObjects.fcallReadonly(name, keys, args));
    }

    @Override
    public CompletableFuture<String> functionDelete(String libraryName) {
        return executeCommand(commandObjects.functionDelete(libraryName));
    }

    @Override
    public CompletableFuture<String> functionFlush() {
        return executeCommand(commandObjects.functionFlush());
    }

    @Override
    public CompletableFuture<String> functionKill() {
        return executeCommand(commandObjects.functionKill());
    }

    @Override
    public CompletableFuture<List<LibraryInfo>> functionList() {
        return executeCommand(commandObjects.functionList());
    }

    @Override
    public CompletableFuture<List<LibraryInfo>> functionList(String libraryNamePattern) {
        return executeCommand(commandObjects.functionList(libraryNamePattern));
    }

    @Override
    public CompletableFuture<String> functionLoad(String functionCode) {
        return executeCommand(commandObjects.functionLoad(functionCode));
    }

    // Sample commands
    @Override
    public CompletableFuture<Long> waitReplicas(String sampleKey, int replicas, long timeout) {
        return executeCommand(commandObjects.waitReplicas(sampleKey, replicas, timeout));
    }

    @Override
    public CompletableFuture<Long> waitReplicas(byte[] sampleKey, int replicas, long timeout) {
        return executeCommand(commandObjects.waitReplicas(sampleKey, replicas, timeout));
    }

    @Override
    public CompletableFuture<byte[]> scriptLoad(byte[] script, byte[] sampleKey) {
        return executeCommand(commandObjects.scriptLoad(script, sampleKey));
    }

    @Override
    public CompletableFuture<Long> zunionstore(String destKey, String... keys) {
        return executeCommand(commandObjects.zunionstore(destKey, keys));
    }

    @Override
    public CompletableFuture<Long> zunionstore(String destKey, ZParams params, String... keys) {
        return executeCommand(commandObjects.zunionstore(destKey, params, keys));
    }

    @Override
    public CompletableFuture<Long> zunionstore(byte[] destKey, byte[]... keys) {
        return executeCommand(commandObjects.zunionstore(destKey, keys));
    }

    @Override
    public CompletableFuture<Long> zunionstore(byte[] destKey, ZParams params, byte[]... keys) {
        return executeCommand(commandObjects.zunionstore(destKey, params, keys));
    }

    @Override
    public CompletableFuture<ScanResult<String>> sscan(String key, String cursor, ScanParams params) {
        return executeCommand(commandObjects.sscan(key, cursor, params));
    }

    @Override
    public CompletableFuture<ScanResult<byte[]>> sscan(byte[] key, byte[] cursor, ScanParams params) {
        return executeCommand(commandObjects.sscan(key, cursor, params));
    }

    @Override
    public CompletableFuture<Long> pexpire(byte[] key, long milliseconds) {
        return executeCommand(commandObjects.pexpire(key, milliseconds));
    }

    @Override
    public CompletableFuture<Long> pexpire(byte[] key, long milliseconds, ExpiryOption expiryOption) {
        return executeCommand(commandObjects.pexpire(key, milliseconds, expiryOption));
    }

    @Override
    public CompletableFuture<Object> eval(String script, String sampleKey) {
        return executeCommand(commandObjects.eval(script, sampleKey));
    }

    @Override
    public CompletableFuture<Object> eval(byte[] script, byte[] sampleKey) {
        return executeCommand(commandObjects.eval(script, sampleKey));
    }

    @Override
    public CompletableFuture<Object> fcallReadonly(byte[] name, List<byte[]> keys, List<byte[]> args) {
        return executeCommand(commandObjects.fcallReadonly(name, keys, args));
    }

    @Override
    public CompletableFuture<Object> fcall(byte[] name, List<byte[]> keys, List<byte[]> args) {
        return executeCommand(commandObjects.fcall(name, keys, args));
    }

    @Override
    public CompletableFuture<List<StreamEntryID>> xclaimJustId(String key, String group, String consumer,
        long minIdleTime, XClaimParams params, StreamEntryID... ids) {
        return executeCommand(commandObjects.xclaimJustId(key, group, consumer, minIdleTime, params, ids));
    }

    @Override
    public CompletableFuture<Entry<StreamEntryID, List<StreamEntry>>> xautoclaim(String key, String group,
        String consumerName, long minIdleTime, StreamEntryID start, XAutoClaimParams params) {
        return executeCommand(commandObjects.xautoclaim(key, group, consumerName, minIdleTime, start, params));
    }

    @Override
    public CompletableFuture<Entry<StreamEntryID, List<StreamEntryID>>> xautoclaimJustId(String key, String group,
        String consumerName, long minIdleTime, StreamEntryID start, XAutoClaimParams params) {
        return executeCommand(commandObjects.xautoclaimJustId(key, group, consumerName, minIdleTime, start, params));
    }

    @Override
    public CompletableFuture<StreamInfo> xinfoStream(String key) {
        return executeCommand(commandObjects.xinfoStream(key));
    }

    @Override
    public CompletableFuture<StreamFullInfo> xinfoStreamFull(String key) {
        return executeCommand(commandObjects.xinfoStreamFull(key));
    }

    @Override
    public CompletableFuture<StreamFullInfo> xinfoStreamFull(String key, int count) {
        return executeCommand(commandObjects.xinfoStreamFull(key, count));
    }

    @Override
    public CompletableFuture<List<StreamGroupInfo>> xinfoGroups(String key) {
        return executeCommand(commandObjects.xinfoGroups(key));
    }

    @Override
    public CompletableFuture<List<StreamConsumersInfo>> xinfoConsumers(String key, String group) {
        return executeCommand(commandObjects.xinfoConsumers(key, group));
    }

    @Override
    public CompletableFuture<List<StreamConsumerInfo>> xinfoConsumers2(String key, String group) {
        return executeCommand(commandObjects.xinfoConsumers2(key, group));
    }

    @Override
    public CompletableFuture<List<byte[]>> xclaimJustId(byte[] key, byte[] group, byte[] consumer,
        long minIdleTime, XClaimParams params, byte[]... ids) {
        return executeCommand(commandObjects.xclaimJustId(key, group, consumer, minIdleTime, params, ids));
    }

    @Override
    public CompletableFuture<byte[]> hrandfield(byte[] key) {
        return executeCommand(commandObjects.hrandfield(key));
    }

    @Override
    public CompletableFuture<List<byte[]>> hrandfield(byte[] key, long count) {
        return executeCommand(commandObjects.hrandfield(key, count));
    }

    @Override
    public CompletableFuture<String> type(byte[] key) {
        return executeCommand(commandObjects.type(key));
    }

    @Override
    public CompletableFuture<Object> eval(byte[] script) {
        return executeCommand(commandObjects.eval(script));
    }

    @Override
    public CompletableFuture<List<byte[]>> zunion(ZParams params, byte[]... keys) {
        return executeCommand(commandObjects.zunion(params, keys));
    }

    @Override
    public CompletableFuture<List<Tuple>> zunionWithScores(ZParams params, byte[]... keys) {
        return executeCommand(commandObjects.zunionWithScores(params, keys));
    }

    @Override
    public CompletableFuture<KeyValue<byte[], List<Tuple>>> bzmpop(double timeout, SortedSetOption option,
        byte[]... keys) {
        return executeCommand(commandObjects.bzmpop(timeout, option, keys));
    }

    @Override
    public CompletableFuture<KeyValue<byte[], List<Tuple>>> bzmpop(double timeout, SortedSetOption option, int count,
        byte[]... keys) {
        return executeCommand(commandObjects.bzmpop(timeout, option, count, keys));
    }

    @Override
    public CompletableFuture<Long> zadd(String key, double score, String member) {
        return executeCommand(commandObjects.zadd(key, score, member));
    }

    @Override
    public CompletableFuture<Long> zadd(String key, double score, String member, ZAddParams params) {
        return executeCommand(commandObjects.zadd(key, score, member, params));
    }

    @Override
    public CompletableFuture<Long> zadd(String key, Map<String, Double> scoreMembers) {
        return executeCommand(commandObjects.zadd(key, scoreMembers));
    }

    @Override
    public CompletableFuture<Long> zadd(String key, Map<String, Double> scoreMembers, ZAddParams params) {
        return executeCommand(commandObjects.zadd(key, scoreMembers, params));
    }

    @Override
    public CompletableFuture<Double> zaddIncr(String key, double score, String member, ZAddParams params) {
        return executeCommand(commandObjects.zaddIncr(key, score, member, params));
    }

    @Override
    public CompletableFuture<Long> zrem(String key, String... members) {
        return executeCommand(commandObjects.zrem(key, members));
    }

    @Override
    public CompletableFuture<Double> zincrby(String key, double increment, String member) {
        return executeCommand(commandObjects.zincrby(key, increment, member));
    }

    @Override
    public CompletableFuture<Double> zincrby(String key, double increment, String member, ZIncrByParams params) {
        return executeCommand(commandObjects.zincrby(key, increment, member, params));
    }

    @Override
    public CompletableFuture<Long> zrank(String key, String member) {
        return executeCommand(commandObjects.zrank(key, member));
    }

    @Override
    public CompletableFuture<Long> zrevrank(String key, String member) {
        return executeCommand(commandObjects.zrevrank(key, member));
    }

    @Override
    public CompletableFuture<KeyValue<Long, Double>> zrankWithScore(String key, String member) {
        return executeCommand(commandObjects.zrankWithScore(key, member));
    }

    @Override
    public CompletableFuture<KeyValue<Long, Double>> zrevrankWithScore(String key, String member) {
        return executeCommand(commandObjects.zrevrankWithScore(key, member));
    }

    @Override
    public CompletableFuture<List<String>> zrange(String key, long start, long stop) {
        return executeCommand(commandObjects.zrange(key, start, stop));
    }

    @Override
    public CompletableFuture<List<String>> zrevrange(String key, long start, long stop) {
        return executeCommand(commandObjects.zrevrange(key, start, stop));
    }

    @Override
    public CompletableFuture<List<String>> zrange(String key, ZRangeParams zRangeParams) {
        return executeCommand(commandObjects.zrange(key, zRangeParams));
    }

    @Override
    public CompletableFuture<Long> zrangestore(String dest, String src, ZRangeParams zRangeParams) {
        return executeCommand(commandObjects.zrangestore(dest, src, zRangeParams));
    }

    @Override
    public CompletableFuture<String> zrandmember(String key) {
        return executeCommand(commandObjects.zrandmember(key));
    }

    @Override
    public CompletableFuture<List<String>> zrandmember(String key, long count) {
        return executeCommand(commandObjects.zrandmember(key, count));
    }

    @Override
    public CompletableFuture<List<Tuple>> zrandmemberWithScores(String key, long count) {
        return executeCommand(commandObjects.zrandmemberWithScores(key, count));
    }

    @Override
    public CompletableFuture<Long> zcard(String key) {
        return executeCommand(commandObjects.zcard(key));
    }

    @Override
    public CompletableFuture<Double> zscore(String key, String member) {
        return executeCommand(commandObjects.zscore(key, member));
    }

    @Override
    public CompletableFuture<List<Double>> zmscore(String key, String... members) {
        return executeCommand(commandObjects.zmscore(key, members));
    }

    @Override
    public CompletableFuture<Tuple> zpopmax(String key) {
        return executeCommand(commandObjects.zpopmax(key));
    }

    @Override
    public CompletableFuture<List<Tuple>> zpopmax(String key, int count) {
        return executeCommand(commandObjects.zpopmax(key, count));
    }

    @Override
    public CompletableFuture<Tuple> zpopmin(String key) {
        return executeCommand(commandObjects.zpopmin(key));
    }

    @Override
    public CompletableFuture<List<Tuple>> zpopmin(String key, int count) {
        return executeCommand(commandObjects.zpopmin(key, count));
    }

    @Override
    public CompletableFuture<Long> zcount(String key, double min, double max) {
        return executeCommand(commandObjects.zcount(key, min, max));
    }

    @Override
    public CompletableFuture<Long> zcount(String key, String min, String max) {
        return executeCommand(commandObjects.zcount(key, min, max));
    }

    @Override
    public CompletableFuture<List<String>> zrangeByScore(String key, double min, double max) {
        return executeCommand(commandObjects.zrangeByScore(key, min, max));
    }

    @Override
    public CompletableFuture<List<String>> zrangeByScore(String key, String min, String max) {
        return executeCommand(commandObjects.zrangeByScore(key, min, max));
    }

    @Override
    public CompletableFuture<List<String>> zrangeByScore(String key, double min, double max, int offset, int count) {
        return executeCommand(commandObjects.zrangeByScore(key, min, max, offset, count));
    }

    @Override
    public CompletableFuture<List<String>> zrangeByScore(String key, String min, String max, int offset, int count) {
        return executeCommand(commandObjects.zrangeByScore(key, min, max, offset, count));
    }

    @Override
    public CompletableFuture<List<Tuple>> zrangeByScoreWithScores(String key, double min, double max) {
        return executeCommand(commandObjects.zrangeByScoreWithScores(key, min, max));
    }

    @Override
    public CompletableFuture<List<Tuple>> zrangeByScoreWithScores(String key, String min, String max) {
        return executeCommand(commandObjects.zrangeByScoreWithScores(key, min, max));
    }

    @Override
    public CompletableFuture<List<Tuple>> zrangeByScoreWithScores(String key, double min, double max, int offset,
        int count) {
        return executeCommand(commandObjects.zrangeByScoreWithScores(key, min, max, offset, count));
    }

    @Override
    public CompletableFuture<List<Tuple>> zrangeByScoreWithScores(String key, String min, String max, int offset,
        int count) {
        return executeCommand(commandObjects.zrangeByScoreWithScores(key, min, max, offset, count));
    }

    @Override
    public CompletableFuture<List<String>> zrevrangeByScore(String key, double max, double min) {
        return executeCommand(commandObjects.zrevrangeByScore(key, max, min));
    }

    @Override
    public CompletableFuture<List<String>> zrevrangeByScore(String key, String max, String min) {
        return executeCommand(commandObjects.zrevrangeByScore(key, max, min));
    }

    @Override
    public CompletableFuture<List<String>> zrevrangeByScore(String key, double max, double min, int offset, int count) {
        return executeCommand(commandObjects.zrevrangeByScore(key, max, min, offset, count));
    }

    @Override
    public CompletableFuture<List<String>> zrevrangeByScore(String key, String max, String min, int offset, int count) {
        return executeCommand(commandObjects.zrevrangeByScore(key, max, min, offset, count));
    }

    @Override
    public CompletableFuture<List<Tuple>> zrevrangeByScoreWithScores(String key, double max, double min) {
        return executeCommand(commandObjects.zrevrangeByScoreWithScores(key, max, min));
    }

    @Override
    public CompletableFuture<List<Tuple>> zrevrangeByScoreWithScores(String key, String max, String min) {
        return executeCommand(commandObjects.zrevrangeByScoreWithScores(key, max, min));
    }

    @Override
    public CompletableFuture<List<Tuple>> zrevrangeByScoreWithScores(String key, double max, double min, int offset,
        int count) {
        return executeCommand(commandObjects.zrevrangeByScoreWithScores(key, max, min, offset, count));
    }

    @Override
    public CompletableFuture<List<Tuple>> zrevrangeByScoreWithScores(String key, String max, String min, int offset,
        int count) {
        return executeCommand(commandObjects.zrevrangeByScoreWithScores(key, max, min, offset, count));
    }

    @Override
    public CompletableFuture<Long> zremrangeByRank(String key, long start, long stop) {
        return executeCommand(commandObjects.zremrangeByRank(key, start, stop));
    }

    @Override
    public CompletableFuture<Long> zlexcount(String key, String min, String max) {
        return executeCommand(commandObjects.zlexcount(key, min, max));
    }

    @Override
    public CompletableFuture<List<String>> zrangeByLex(String key, String min, String max) {
        return executeCommand(commandObjects.zrangeByLex(key, min, max));
    }

    @Override
    public CompletableFuture<List<String>> zrangeByLex(String key, String min, String max, int offset, int count) {
        return executeCommand(commandObjects.zrangeByLex(key, min, max, offset, count));
    }

    @Override
    public CompletableFuture<List<String>> zrevrangeByLex(String key, String max, String min) {
        return executeCommand(commandObjects.zrevrangeByLex(key, max, min));
    }

    @Override
    public CompletableFuture<List<String>> zrevrangeByLex(String key, String max, String min, int offset, int count) {
        return executeCommand(commandObjects.zrevrangeByLex(key, max, min, offset, count));
    }

    @Override
    public CompletableFuture<ScanResult<Tuple>> zscan(String key, String cursor) {
        return AsyncJedisCommands.super.zscan(key, cursor);
    }

    @Override
    public CompletableFuture<ScanResult<Tuple>> zscan(String key, String cursor, ScanParams params) {
        return executeCommand(commandObjects.zscan(key, cursor, params));
    }

    @Override
    public CompletableFuture<KeyValue<String, Tuple>> bzpopmax(double timeout, String... keys) {
        return executeCommand(commandObjects.bzpopmax(timeout, keys));
    }

    @Override
    public CompletableFuture<KeyValue<String, Tuple>> bzpopmin(double timeout, String... keys) {
        return executeCommand(commandObjects.bzpopmin(timeout, keys));
    }

    @Override
    public CompletableFuture<List<String>> zdiff(String... keys) {
        return executeCommand(commandObjects.zdiff(keys));
    }

    @Override
    public CompletableFuture<List<Tuple>> zdiffWithScores(String... keys) {
        return executeCommand(commandObjects.zdiffWithScores(keys));
    }

    @Override
    public CompletableFuture<Long> zdiffstore(String dstkey, String... keys) {
        return executeCommand(commandObjects.zdiffstore(dstkey, keys));
    }

    @Override
    public CompletableFuture<List<String>> zinter(ZParams params, String... keys) {
        return executeCommand(commandObjects.zinter(params, keys));
    }

    @Override
    public CompletableFuture<List<Tuple>> zinterWithScores(ZParams params, String... keys) {
        return executeCommand(commandObjects.zinterWithScores(params, keys));
    }

    @Override
    public CompletableFuture<Long> zintercard(String... keys) {
        return executeCommand(commandObjects.zintercard(keys));
    }

    @Override
    public CompletableFuture<Long> zintercard(long limit, String... keys) {
        return executeCommand(commandObjects.zintercard(limit, keys));
    }

    @Override
    public CompletableFuture<List<String>> zunion(ZParams params, String... keys) {
        return executeCommand(commandObjects.zunion(params, keys));
    }

    @Override
    public CompletableFuture<List<Tuple>> zunionWithScores(ZParams params, String... keys) {
        return executeCommand(commandObjects.zunionWithScores(params, keys));
    }

    @Override
    public CompletableFuture<KeyValue<String, List<Tuple>>> bzmpop(double timeout, SortedSetOption option, int count,
        String... keys) {
        return executeCommand(commandObjects.bzmpop(timeout, option, count, keys));
    }

    @Override
    public CompletableFuture<Boolean> setbit(byte[] key, long offset, boolean value) {
        return executeCommand(commandObjects.setbit(key, offset, value));
    }

    @Override
    public CompletableFuture<Boolean> getbit(byte[] key, long offset) {
        return executeCommand(commandObjects.getbit(key, offset));
    }

    @Override
    public CompletableFuture<Long> bitcount(byte[] key) {
        return executeCommand(commandObjects.bitcount(key));
    }

    @Override
    public CompletableFuture<Long> bitcount(byte[] key, long start, long end) {
        return executeCommand(commandObjects.bitcount(key, start, end));
    }

    @Override
    public CompletableFuture<Long> bitcount(byte[] key, long start, long end, BitCountOption option) {
        return executeCommand(commandObjects.bitcount(key, start, end, option));
    }

    @Override
    public CompletableFuture<Long> bitpos(byte[] key, boolean value) {
        return executeCommand(commandObjects.bitpos(key, value));
    }

    @Override
    public CompletableFuture<Long> bitpos(byte[] key, boolean value, BitPosParams params) {
        return executeCommand(commandObjects.bitpos(key, value, params));
    }

    @Override
    public CompletableFuture<List<Long>> bitfield(byte[] key, byte[]... arguments) {
        return executeCommand(commandObjects.bitfield(key, arguments));
    }

    @Override
    public CompletableFuture<List<Long>> bitfieldReadonly(byte[] key, byte[]... arguments) {
        return executeCommand(commandObjects.bitfieldReadonly(key, arguments));
    }

    @Override
    public CompletableFuture<Long> bitop(BitOP op, byte[] destKey, byte[]... srcKeys) {
        return executeCommand(commandObjects.bitop(op, destKey, srcKeys));
    }

    @Override
    public CompletableFuture<String> functionDelete(byte[] libraryName) {
        return executeCommand(commandObjects.functionDelete(libraryName));
    }

    @Override
    public CompletableFuture<List<Object>> functionList(byte[] libraryNamePattern) {
        return executeCommand(commandObjects.functionList(libraryNamePattern));
    }

    @Override
    public CompletableFuture<List<Object>> functionListWithCode(byte[] libraryNamePattern) {
        return executeCommand(commandObjects.functionListWithCode(libraryNamePattern));
    }

    @Override
    public CompletableFuture<String> functionLoad(byte[] functionCode) {
        return executeCommand(commandObjects.functionLoad(functionCode));
    }

    @Override
    public CompletableFuture<String> functionLoadReplace(byte[] functionCode) {
        return executeCommand(commandObjects.functionLoadReplace(functionCode));
    }

    @Override
    public CompletableFuture<Object> functionStatsBinary() {
        return executeCommand(commandObjects.functionStatsBinary());
    }

    @Override
    public CompletableFuture<byte[]> functionDump() {
        return executeCommand(commandObjects.functionDump());
    }

    @Override
    public CompletableFuture<String> functionFlush(FlushMode mode) {
        return executeCommand(commandObjects.functionFlush(mode));
    }

    @Override
    public CompletableFuture<List<LibraryInfo>> functionListWithCode() {
        return executeCommand(commandObjects.functionListWithCode());
    }

    @Override
    public CompletableFuture<List<LibraryInfo>> functionListWithCode(String libraryNamePattern) {
        return executeCommand(commandObjects.functionListWithCode(libraryNamePattern));
    }

    @Override
    public CompletableFuture<String> functionLoadReplace(String functionCode) {
        return executeCommand(commandObjects.functionLoadReplace(functionCode));
    }

    @Override
    public CompletableFuture<String> functionRestore(byte[] serializedValue) {
        return executeCommand(commandObjects.functionRestore(serializedValue));
    }

    @Override
    public CompletableFuture<String> functionRestore(byte[] serializedValue, FunctionRestorePolicy policy) {
        return executeCommand(commandObjects.functionRestore(serializedValue, policy));
    }

    @Override
    public CompletableFuture<FunctionStats> functionStats() {
        return executeCommand(commandObjects.functionStats());
    }

    @Override
    public CompletableFuture<Long> geoadd(byte[] key, double longitude, double latitude, byte[] member) {
        return executeCommand(commandObjects.geoadd(key, longitude, latitude, member));
    }

    @Override
    public CompletableFuture<Long> geoadd(byte[] key, Map<byte[], GeoCoordinate> memberCoordinateMap) {
        return executeCommand(commandObjects.geoadd(key, memberCoordinateMap));
    }

    @Override
    public CompletableFuture<Long> geoadd(byte[] key, GeoAddParams params,
        Map<byte[], GeoCoordinate> memberCoordinateMap) {
        return executeCommand(commandObjects.geoadd(key, params, memberCoordinateMap));
    }

    @Override
    public CompletableFuture<Double> geodist(byte[] key, byte[] member1, byte[] member2) {
        return executeCommand(commandObjects.geodist(key, member1, member2));
    }

    @Override
    public CompletableFuture<Double> geodist(byte[] key, byte[] member1, byte[] member2, GeoUnit unit) {
        return executeCommand(commandObjects.geodist(key, member1, member2, unit));
    }

    @Override
    public CompletableFuture<List<byte[]>> geohash(byte[] key, byte[]... members) {
        return executeCommand(commandObjects.geohash(key, members));
    }

    @Override
    public CompletableFuture<List<GeoCoordinate>> geopos(byte[] key, byte[]... members) {
        return executeCommand(commandObjects.geopos(key, members));
    }

    @Override
    public CompletableFuture<List<GeoRadiusResponse>> georadius(byte[] key, double longitude, double latitude,
        double radius, GeoUnit unit) {
        return executeCommand(commandObjects.georadius(key, longitude, latitude, radius, unit));
    }

    @Override
    public CompletableFuture<List<GeoRadiusResponse>> georadiusReadonly(byte[] key, double longitude, double latitude,
        double radius, GeoUnit unit) {
        return executeCommand(commandObjects.georadiusReadonly(key, longitude, latitude, radius, unit));
    }

    @Override
    public CompletableFuture<List<GeoRadiusResponse>> georadius(byte[] key, double longitude, double latitude,
        double radius, GeoUnit unit, GeoRadiusParam param) {
        return executeCommand(commandObjects.georadius(key, longitude, latitude, radius, unit, param));
    }

    @Override
    public CompletableFuture<List<GeoRadiusResponse>> georadiusReadonly(byte[] key, double longitude, double latitude,
        double radius, GeoUnit unit, GeoRadiusParam param) {
        return executeCommand(commandObjects.georadiusReadonly(key, longitude, latitude, radius, unit, param));
    }

    @Override
    public CompletableFuture<List<GeoRadiusResponse>> georadiusByMemberReadonly(byte[] key, byte[] member,
        double radius, GeoUnit unit) {
        return executeCommand(commandObjects.georadiusByMemberReadonly(key, member, radius, unit));
    }

    @Override
    public CompletableFuture<List<GeoRadiusResponse>> georadiusByMemberReadonly(byte[] key, byte[] member,
        double radius, GeoUnit unit, GeoRadiusParam param) {
        return executeCommand(commandObjects.georadiusByMemberReadonly(key, member, radius, unit, param));
    }

    @Override
    public CompletableFuture<Long> georadiusStore(byte[] key, double longitude, double latitude, double radius,
        GeoUnit unit, GeoRadiusParam param, GeoRadiusStoreParam storeParam) {
        return executeCommand(commandObjects.georadiusStore(key, longitude, latitude, radius, unit, param, storeParam));
    }

    @Override
    public CompletableFuture<Long> georadiusByMemberStore(byte[] key, byte[] member, double radius, GeoUnit unit,
        GeoRadiusParam param, GeoRadiusStoreParam storeParam) {
        return executeCommand(commandObjects.georadiusByMemberStore(key, member, radius, unit, param, storeParam));
    }

    @Override
    public CompletableFuture<List<GeoRadiusResponse>> geosearch(byte[] key, byte[] member, double radius,
        GeoUnit unit) {
        return executeCommand(commandObjects.geosearch(key, member, radius, unit));
    }

    @Override
    public CompletableFuture<List<GeoRadiusResponse>> geosearch(byte[] key, GeoCoordinate coord, double radius,
        GeoUnit unit) {
        return executeCommand(commandObjects.geosearch(key, coord, radius, unit));
    }

    @Override
    public CompletableFuture<List<GeoRadiusResponse>> geosearch(byte[] key, byte[] member, double width, double height,
        GeoUnit unit) {
        return executeCommand(commandObjects.geosearch(key, member, width, height, unit));
    }

    @Override
    public CompletableFuture<List<GeoRadiusResponse>> geosearch(byte[] key, GeoCoordinate coord, double width,
        double height, GeoUnit unit) {
        return executeCommand(commandObjects.geosearch(key, coord, width, height, unit));
    }

    @Override
    public CompletableFuture<List<GeoRadiusResponse>> geosearch(byte[] key, GeoSearchParam params) {
        return executeCommand(commandObjects.geosearch(key, params));
    }

    @Override
    public CompletableFuture<Long> pfcount(byte[] key) {
        return executeCommand(commandObjects.pfcount(key));
    }

    @Override
    public CompletableFuture<Long> pfcount(byte[]... keys) {
        return executeCommand(commandObjects.pfcount(keys));
    }

    @Override
    public CompletableFuture<Map<String, List<StreamEntry>>> xreadAsMap(XReadParams params,
        Map<String, StreamEntryID> streams) {
        return executeCommand(commandObjects.xreadAsMap(params, streams));
    }

    @Override
    public CompletableFuture<String> scriptFlush(byte[] sampleKey) {
        return executeCommand(commandObjects.scriptFlush(sampleKey));
    }

    @Override
    public CompletableFuture<String> scriptFlush(byte[] sampleKey, FlushMode flushMode) {
        return executeCommand(commandObjects.scriptFlush(sampleKey, flushMode));
    }

    @Override
    public CompletableFuture<List<byte[]>> hmget(byte[] key, byte[]... fields) {
        return executeCommand(commandObjects.hmget(key, fields));
    }

    @Override
    public CompletableFuture<LCSMatchResult> lcs(byte[] keyA, byte[] keyB, LCSParams params) {
        return executeCommand(commandObjects.lcs(keyA, keyB, params));
    }

    @Override
    public CompletableFuture<LCSMatchResult> lcs(String keyA, String keyB, LCSParams params) {
        return executeCommand(commandObjects.lcs(keyA, keyB, params));
    }

    @Override
    public CompletableFuture<Object> eval(byte[] script, List<byte[]> keys, List<byte[]> args) {
        return executeCommand(commandObjects.eval(script, keys, args));
    }

    @Override
    public CompletableFuture<Object> eval(byte[] script, int keyCount, byte[]... params) {
        return executeCommand(commandObjects.eval(script, keyCount, params));
    }

    @Override
    public CompletableFuture<KeyValue<String, List<Tuple>>> zmpop(SortedSetOption option, String... keys) {
        return executeCommand(commandObjects.zmpop(option, keys));
    }

    @Override
    public CompletableFuture<KeyValue<String, List<Tuple>>> zmpop(SortedSetOption option, int count, String... keys) {
        return executeCommand(commandObjects.zmpop(option, count, keys));
    }

    @Override
    public CompletableFuture<List<Map.Entry<String, List<StreamEntry>>>> xreadGroup(String groupName, String consumer,
        XReadGroupParams params, Map<String, StreamEntryID> streams) {
        return executeCommand(commandObjects.xreadGroup(groupName, consumer, params, streams));
    }

    @Override
    public CompletableFuture<String> restore(byte[] key, long ttl, byte[] serializedValue) {
        return executeCommand(commandObjects.restore(key, ttl, serializedValue));
    }

    @Override
    public CompletableFuture<String> restore(byte[] key, long ttl, byte[] serializedValue, RestoreParams params) {
        return executeCommand(commandObjects.restore(key, ttl, serializedValue, params));
    }

    @Override
    public CompletableFuture<List<GeoRadiusResponse>> georadiusByMember(byte[] key, byte[] member, double radius,
        GeoUnit unit) {
        return executeCommand(commandObjects.georadiusByMember(key, member, radius, unit));
    }

    @Override
    public CompletableFuture<List<GeoRadiusResponse>> georadiusByMember(byte[] key, byte[] member, double radius,
        GeoUnit unit, GeoRadiusParam param) {
        return executeCommand(commandObjects.georadiusByMember(key, member, radius, unit, param));
    }

    @Override
    public CompletableFuture<Long> pfcount(String key) {
        return executeCommand(commandObjects.pfcount(key));
    }

    @Override
    public CompletableFuture<String> rename(byte[] oldkey, byte[] newkey) {
        return executeCommand(commandObjects.rename(oldkey, newkey));
    }

    @Override
    public CompletableFuture<String> rename(String oldkey, String newkey) {
        return executeCommand(commandObjects.rename(oldkey, newkey));
    }

    @Override
    public CompletableFuture<List<Object>> functionListWithCodeBinary() {
        return executeCommand(commandObjects.functionListWithCodeBinary());
    }

    @Override
    public CompletableFuture<Long> rpushx(String key, String... values) {
        return executeCommand(commandObjects.rpushx(key, values));
    }

    @Override
    public CompletableFuture<Long> ttl(byte[] key) {
        return executeCommand(commandObjects.ttl(key));
    }

    @Override
    public CompletableFuture<Long> hsetnx(byte[] key, byte[] field, byte[] value) {
        return executeCommand(commandObjects.hsetnx(key, field, value));
    }

    @Override
    public CompletableFuture<List<GeoRadiusResponse>> georadiusByMember(String key, String member, double radius,
        GeoUnit unit) {
        return executeCommand(commandObjects.georadiusByMember(key, member, radius, unit));
    }

    @Override
    public CompletableFuture<Long> zremrangeByScore(byte[] key, byte[] min, byte[] max) {
        return executeCommand(commandObjects.zremrangeByScore(key, min, max));
    }

    @Override
    public CompletableFuture<List<GeoRadiusResponse>> georadiusByMember(String key, String member, double radius,
        GeoUnit unit, GeoRadiusParam param) {
        return executeCommand(commandObjects.georadiusByMember(key, member, radius, unit, param));
    }

    @Override
    public CompletableFuture<List<Object>> functionListBinary() {
        return executeCommand(commandObjects.functionListBinary());
    }

    @Override
    public CompletableFuture<Long> incr(byte[] key) {
        return executeCommand(commandObjects.incr(key));
    }

    @Override
    public CompletableFuture<Long> incr(String key) {
        return executeCommand(commandObjects.incr(key));
    }

    @Override
    public CompletableFuture<List<Object>> xread(XReadParams xReadParams, Entry<byte[], byte[]>... streams) {
        return executeCommand(commandObjects.xread(xReadParams, streams));
    }

    @Override
    public CompletableFuture<List<Entry<String, List<StreamEntry>>>> xread(XReadParams xReadParams,
        Map<String, StreamEntryID> streams) {
        return executeCommand(commandObjects.xread(xReadParams, streams));
    }

    @Override
    public CompletableFuture<Long> zremrangeByLex(String key, String min, String max) {
        return executeCommand(commandObjects.zremrangeByLex(key, min, max));
    }

    @Override
    public CompletableFuture<byte[]> getSet(byte[] key, byte[] value) {
        return executeCommand(commandObjects.getSet(key, value));
    }

    @Override
    public CompletableFuture<String> getSet(String key, String value) {
        return executeCommand(commandObjects.getSet(key, value));
    }

    @Override
    public CompletableFuture<List<Tuple>> zrangeWithScores(byte[] key, long start, long stop) {
        return executeCommand(commandObjects.zrangeWithScores(key, start, stop));
    }

    @Override
    public CompletableFuture<List<Tuple>> zrangeWithScores(byte[] key, ZRangeParams params) {
        return executeCommand(commandObjects.zrangeWithScores(key, params));
    }

    @Override
    public CompletableFuture<Long> decrBy(byte[] key, long decrement) {
        return executeCommand(commandObjects.decrBy(key, decrement));
    }

    @Override
    public CompletableFuture<List<StreamEntry>> xclaim(String key, String group, String consumer, long minIdleTime,
        XClaimParams params, StreamEntryID... ids) {
        return executeCommand(commandObjects.xclaim(key, group, consumer, minIdleTime, params, ids));
    }

    @Override
    public CompletableFuture<List<Boolean>> scriptExists(byte[] sampleKey, byte[]... sha1s) {
        return executeCommand(commandObjects.scriptExists(sampleKey, sha1s));
    }

    @Override
    public CompletableFuture<Long> srem(byte[] key, byte[]... members) {
        return executeCommand(commandObjects.srem(key, members));
    }

    @Override
    public CompletableFuture<Long> rpushx(byte[] key, byte[]... values) {
        return executeCommand(commandObjects.rpushx(key, values));
    }

    @Override
    public CompletableFuture<Long> geosearchStore(String dest, String src, String member, double radius, GeoUnit unit) {
        return executeCommand(commandObjects.geosearchStore(dest, src, member, radius, unit));
    }

    @Override
    public CompletableFuture<Long> geosearchStore(String dest, String src, String member, double width, double height,
        GeoUnit unit) {
        return executeCommand(commandObjects.geosearchStore(dest, src, member, width, height, unit));
    }

    @Override
    public CompletableFuture<Long> geosearchStore(String dest, String src, GeoCoordinate coord, double radius,
        GeoUnit unit) {
        return executeCommand(commandObjects.geosearchStore(dest, src, coord, radius, unit));
    }

    @Override
    public CompletableFuture<Long> zremrangeByScore(String key, double min, double max) {
        return executeCommand(commandObjects.zremrangeByScore(key, min, max));
    }

    @Override
    public CompletableFuture<List<byte[]>> zrevrangeByScore(byte[] key, byte[] max, byte[] min, int offset, int count) {
        return executeCommand(commandObjects.zrevrangeByScore(key, max, min, offset, count));
    }

    @Override
    public CompletableFuture<Long> zremrangeByScore(String key, String min, String max) {
        return executeCommand(commandObjects.zremrangeByScore(key, min, max));
    }

    @Override
    public CompletableFuture<Long> pfcount(String... keys) {
        return executeCommand(commandObjects.pfcount(keys));
    }

    @Override
    public CompletableFuture<Long> zremrangeByScore(byte[] key, double min, double max) {
        return executeCommand(commandObjects.zremrangeByScore(key, min, max));
    }

    @Override
    public CompletableFuture<Long> hset(byte[] key, byte[] field, byte[] value) {
        return executeCommand(commandObjects.hset(key, field, value));
    }

    @Override
    public CompletableFuture<Long> hset(byte[] key, Map<byte[], byte[]> hash) {
        return executeCommand(commandObjects.hset(key, hash));
    }

    @Override
    public CompletableFuture<byte[]> hget(byte[] key, byte[] field) {
        return executeCommand(commandObjects.hget(key, field));
    }

    @Override
    public CompletableFuture<String> hmset(byte[] key, Map<byte[], byte[]> hash) {
        return executeCommand(commandObjects.hmset(key, hash));
    }

    @Override
    public CompletableFuture<Long> hincrBy(byte[] key, byte[] field, long value) {
        return executeCommand(commandObjects.hincrBy(key, field, value));
    }

    @Override
    public CompletableFuture<Double> hincrByFloat(byte[] key, byte[] field, double value) {
        return executeCommand(commandObjects.hincrByFloat(key, field, value));
    }

    @Override
    public CompletableFuture<Boolean> hexists(byte[] key, byte[] field) {
        return executeCommand(commandObjects.hexists(key, field));
    }

    @Override
    public CompletableFuture<Long> hdel(byte[] key, byte[]... fields) {
        return executeCommand(commandObjects.hdel(key, fields));
    }

    @Override
    public CompletableFuture<Long> hlen(byte[] key) {
        return executeCommand(commandObjects.hlen(key));
    }

    @Override
    public CompletableFuture<Set<byte[]>> hkeys(byte[] key) {
        return executeCommand(commandObjects.hkeys(key));
    }

    @Override
    public CompletableFuture<List<byte[]>> hvals(byte[] key) {
        return executeCommand(commandObjects.hvals(key));
    }

    @Override
    public CompletableFuture<Map<byte[], byte[]>> hgetAll(byte[] key) {
        return executeCommand(commandObjects.hgetAll(key));
    }

    @Override
    public CompletableFuture<List<Entry<byte[], byte[]>>> hrandfieldWithValues(byte[] key, long count) {
        return executeCommand(commandObjects.hrandfieldWithValues(key, count));
    }

    @Override
    public CompletableFuture<ScanResult<Entry<byte[], byte[]>>> hscan(byte[] key, byte[] cursor) {
        return AsyncJedisBinaryCommands.super.hscan(key, cursor);
    }

    @Override
    public CompletableFuture<ScanResult<Entry<byte[], byte[]>>> hscan(byte[] key, byte[] cursor, ScanParams params) {
        return executeCommand(commandObjects.hscan(key, cursor, params));
    }

    @Override
    public CompletableFuture<ScanResult<byte[]>> hscanNoValues(byte[] key, byte[] cursor) {
        return AsyncJedisBinaryCommands.super.hscanNoValues(key, cursor);
    }

    @Override
    public CompletableFuture<ScanResult<byte[]>> hscanNoValues(byte[] key, byte[] cursor, ScanParams params) {
        return executeCommand(commandObjects.hscanNoValues(key, cursor, params));
    }

    @Override
    public CompletableFuture<Long> hstrlen(byte[] key, byte[] field) {
        return executeCommand(commandObjects.hstrlen(key, field));
    }

    @Override
    public CompletableFuture<List<Entry<String, String>>> hrandfieldWithValues(String key, long count) {
        return executeCommand(commandObjects.hrandfieldWithValues(key, count));
    }

    @Override
    public CompletableFuture<ScanResult<Entry<String, String>>> hscan(String key, String cursor) {
        return AsyncJedisCommands.super.hscan(key, cursor);
    }

    @Override
    public CompletableFuture<ScanResult<Entry<String, String>>> hscan(String key, String cursor, ScanParams params) {
        return executeCommand(commandObjects.hscan(key, cursor, params));
    }

    @Override
    public CompletableFuture<ScanResult<String>> hscanNoValues(String key, String cursor) {
        return AsyncJedisCommands.super.hscanNoValues(key, cursor);
    }

    @Override
    public CompletableFuture<ScanResult<String>> hscanNoValues(String key, String cursor, ScanParams params) {
        return executeCommand(commandObjects.hscanNoValues(key, cursor, params));
    }

    @Override
    public CompletableFuture<Long> hstrlen(String key, String field) {
        return executeCommand(commandObjects.hstrlen(key, field));
    }

    @Override
    public CompletableFuture<Long> sadd(byte[] key, byte[]... members) {
        return executeCommand(commandObjects.sadd(key, members));
    }

    @Override
    public CompletableFuture<Set<byte[]>> smembers(byte[] key) {
        return executeCommand(commandObjects.smembers(key));
    }

    @Override
    public CompletableFuture<byte[]> spop(byte[] key) {
        return executeCommand(commandObjects.spop(key));
    }

    @Override
    public CompletableFuture<Set<byte[]>> spop(byte[] key, long count) {
        return executeCommand(commandObjects.spop(key, count));
    }

    @Override
    public CompletableFuture<Long> scard(byte[] key) {
        return executeCommand(commandObjects.scard(key));
    }

    @Override
    public CompletableFuture<Boolean> sismember(byte[] key, byte[] member) {
        return executeCommand(commandObjects.sismember(key, member));
    }

    @Override
    public CompletableFuture<List<Boolean>> smismember(byte[] key, byte[]... members) {
        return executeCommand(commandObjects.smismember(key, members));
    }

    @Override
    public CompletableFuture<byte[]> srandmember(byte[] key) {
        return executeCommand(commandObjects.srandmember(key));
    }

    @Override
    public CompletableFuture<List<byte[]>> srandmember(byte[] key, int count) {
        return executeCommand(commandObjects.srandmember(key, count));
    }

    @Override
    public CompletableFuture<Set<byte[]>> sdiff(byte[]... keys) {
        return executeCommand(commandObjects.sdiff(keys));
    }

    @Override
    public CompletableFuture<Long> sdiffstore(byte[] dstkey, byte[]... keys) {
        return executeCommand(commandObjects.sdiffstore(dstkey, keys));
    }

    @Override
    public CompletableFuture<Set<byte[]>> sinter(byte[]... keys) {
        return executeCommand(commandObjects.sinter(keys));
    }

    @Override
    public CompletableFuture<Long> sinterstore(byte[] dstkey, byte[]... keys) {
        return executeCommand(commandObjects.sinterstore(dstkey, keys));
    }

    @Override
    public CompletableFuture<Long> sintercard(byte[]... keys) {
        return executeCommand(commandObjects.sintercard(keys));
    }

    @Override
    public CompletableFuture<Long> sintercard(int limit, byte[]... keys) {
        return executeCommand(commandObjects.sintercard(limit, keys));
    }

    @Override
    public CompletableFuture<Set<byte[]>> sunion(byte[]... keys) {
        return executeCommand(commandObjects.sunion(keys));
    }

    @Override
    public CompletableFuture<Long> sunionstore(byte[] dstkey, byte[]... keys) {
        return executeCommand(commandObjects.sunionstore(dstkey, keys));
    }

    @Override
    public CompletableFuture<Long> smove(byte[] srckey, byte[] dstkey, byte[] member) {
        return executeCommand(commandObjects.smove(srckey, dstkey, member));
    }

    @Override
    public CompletableFuture<String> srandmember(String key) {
        return executeCommand(commandObjects.srandmember(key));
    }

    @Override
    public CompletableFuture<List<String>> srandmember(String key, int count) {
        return executeCommand(commandObjects.srandmember(key, count));
    }

    @Override
    public CompletableFuture<Set<String>> sdiff(String... keys) {
        return executeCommand(commandObjects.sdiff(keys));
    }

    @Override
    public CompletableFuture<Long> sdiffstore(String dstkey, String... keys) {
        return executeCommand(commandObjects.sdiffstore(dstkey, keys));
    }

    @Override
    public CompletableFuture<Set<String>> sinter(String... keys) {
        return executeCommand(commandObjects.sinter(keys));
    }

    @Override
    public CompletableFuture<Long> sinterstore(String dstkey, String... keys) {
        return executeCommand(commandObjects.sinterstore(dstkey, keys));
    }

    @Override
    public CompletableFuture<Long> sintercard(String... keys) {
        return executeCommand(commandObjects.sintercard(keys));
    }

    @Override
    public CompletableFuture<Long> sintercard(int limit, String... keys) {
        return executeCommand(commandObjects.sintercard(limit, keys));
    }

    @Override
    public CompletableFuture<Set<String>> sunion(String... keys) {
        return executeCommand(commandObjects.sunion(keys));
    }

    @Override
    public CompletableFuture<Long> sunionstore(String dstkey, String... keys) {
        return executeCommand(commandObjects.sunionstore(dstkey, keys));
    }

    @Override
    public CompletableFuture<Long> smove(String srckey, String dstkey, String member) {
        return executeCommand(commandObjects.smove(srckey, dstkey, member));
    }

    @Override
    public CompletableFuture<Long> zadd(byte[] key, double score, byte[] member) {
        return executeCommand(commandObjects.zadd(key, score, member));
    }

    @Override
    public CompletableFuture<Long> zadd(byte[] key, double score, byte[] member, ZAddParams params) {
        return executeCommand(commandObjects.zadd(key, score, member, params));
    }

    @Override
    public CompletableFuture<Long> zadd(byte[] key, Map<byte[], Double> scoreMembers) {
        return executeCommand(commandObjects.zadd(key, scoreMembers));
    }

    @Override
    public CompletableFuture<Long> zadd(byte[] key, Map<byte[], Double> scoreMembers, ZAddParams params) {
        return executeCommand(commandObjects.zadd(key, scoreMembers, params));
    }

    @Override
    public CompletableFuture<Double> zaddIncr(byte[] key, double score, byte[] member, ZAddParams params) {
        return executeCommand(commandObjects.zaddIncr(key, score, member, params));
    }

    @Override
    public CompletableFuture<Long> zrem(byte[] key, byte[]... members) {
        return executeCommand(commandObjects.zrem(key, members));
    }

    @Override
    public CompletableFuture<Double> zincrby(byte[] key, double increment, byte[] member) {
        return executeCommand(commandObjects.zincrby(key, increment, member));
    }

    @Override
    public CompletableFuture<Double> zincrby(byte[] key, double increment, byte[] member, ZIncrByParams params) {
        return executeCommand(commandObjects.zincrby(key, increment, member, params));
    }

    @Override
    public CompletableFuture<Long> zrank(byte[] key, byte[] member) {
        return executeCommand(commandObjects.zrank(key, member));
    }

    @Override
    public CompletableFuture<Long> zrevrank(byte[] key, byte[] member) {
        return executeCommand(commandObjects.zrevrank(key, member));
    }

    @Override
    public CompletableFuture<KeyValue<Long, Double>> zrankWithScore(byte[] key, byte[] member) {
        return executeCommand(commandObjects.zrankWithScore(key, member));
    }

    @Override
    public CompletableFuture<KeyValue<Long, Double>> zrevrankWithScore(byte[] key, byte[] member) {
        return executeCommand(commandObjects.zrevrankWithScore(key, member));
    }

    @Override
    public CompletableFuture<List<byte[]>> zrange(byte[] key, long start, long stop) {
        return executeCommand(commandObjects.zrange(key, start, stop));
    }

    @Override
    public CompletableFuture<List<byte[]>> zrevrange(byte[] key, long start, long stop) {
        return executeCommand(commandObjects.zrevrange(key, start, stop));
    }

    @Override
    public CompletableFuture<List<Tuple>> zrevrangeWithScores(byte[] key, long start, long stop) {
        return executeCommand(commandObjects.zrevrangeWithScores(key, start, stop));
    }

    @Override
    public CompletableFuture<List<byte[]>> zrange(byte[] key, ZRangeParams zRangeParams) {
        return executeCommand(commandObjects.zrange(key, zRangeParams));
    }

    @Override
    public CompletableFuture<Long> zrangestore(byte[] dest, byte[] src, ZRangeParams zRangeParams) {
        return executeCommand(commandObjects.zrangestore(dest, src, zRangeParams));
    }

    @Override
    public CompletableFuture<byte[]> zrandmember(byte[] key) {
        return executeCommand(commandObjects.zrandmember(key));
    }

    @Override
    public CompletableFuture<List<byte[]>> zrandmember(byte[] key, long count) {
        return executeCommand(commandObjects.zrandmember(key, count));
    }

    @Override
    public CompletableFuture<List<Tuple>> zrandmemberWithScores(byte[] key, long count) {
        return executeCommand(commandObjects.zrandmemberWithScores(key, count));
    }

    @Override
    public CompletableFuture<Long> zcard(byte[] key) {
        return executeCommand(commandObjects.zcard(key));
    }

    @Override
    public CompletableFuture<Double> zscore(byte[] key, byte[] member) {
        return executeCommand(commandObjects.zscore(key, member));
    }

    @Override
    public CompletableFuture<List<Double>> zmscore(byte[] key, byte[]... members) {
        return executeCommand(commandObjects.zmscore(key, members));
    }

    @Override
    public CompletableFuture<Tuple> zpopmax(byte[] key) {
        return executeCommand(commandObjects.zpopmax(key));
    }

    @Override
    public CompletableFuture<List<Tuple>> zpopmax(byte[] key, int count) {
        return executeCommand(commandObjects.zpopmax(key, count));
    }

    @Override
    public CompletableFuture<Tuple> zpopmin(byte[] key) {
        return executeCommand(commandObjects.zpopmin(key));
    }

    @Override
    public CompletableFuture<List<Tuple>> zpopmin(byte[] key, int count) {
        return executeCommand(commandObjects.zpopmin(key, count));
    }

    @Override
    public CompletableFuture<Long> zcount(byte[] key, double min, double max) {
        return executeCommand(commandObjects.zcount(key, min, max));
    }

    @Override
    public CompletableFuture<Long> zcount(byte[] key, byte[] min, byte[] max) {
        return executeCommand(commandObjects.zcount(key, min, max));
    }

    @Override
    public CompletableFuture<List<byte[]>> zrangeByScore(byte[] key, double min, double max) {
        return executeCommand(commandObjects.zrangeByScore(key, min, max));
    }

    @Override
    public CompletableFuture<List<byte[]>> zrangeByScore(byte[] key, byte[] min, byte[] max) {
        return executeCommand(commandObjects.zrangeByScore(key, min, max));
    }

    @Override
    public CompletableFuture<List<byte[]>> zrangeByScore(byte[] key, double min, double max, int offset, int count) {
        return executeCommand(commandObjects.zrangeByScore(key, min, max, offset, count));
    }

    @Override
    public CompletableFuture<List<byte[]>> zrangeByScore(byte[] key, byte[] min, byte[] max, int offset, int count) {
        return executeCommand(commandObjects.zrangeByScore(key, min, max, offset, count));
    }

    @Override
    public CompletableFuture<List<Tuple>> zrangeByScoreWithScores(byte[] key, double min, double max) {
        return executeCommand(commandObjects.zrangeByScoreWithScores(key, min, max));
    }

    @Override
    public CompletableFuture<List<Tuple>> zrangeByScoreWithScores(byte[] key, byte[] min, byte[] max) {
        return executeCommand(commandObjects.zrangeByScoreWithScores(key, min, max));
    }

    @Override
    public CompletableFuture<List<Tuple>> zrangeByScoreWithScores(byte[] key, double min, double max, int offset,
        int count) {
        return executeCommand(commandObjects.zrangeByScoreWithScores(key, min, max, offset, count));
    }

    @Override
    public CompletableFuture<List<Tuple>> zrangeByScoreWithScores(byte[] key, byte[] min, byte[] max, int offset,
        int count) {
        return executeCommand(commandObjects.zrangeByScoreWithScores(key, min, max, offset, count));
    }

    @Override
    public CompletableFuture<List<byte[]>> zrevrangeByScore(byte[] key, double max, double min) {
        return executeCommand(commandObjects.zrevrangeByScore(key, max, min));
    }

    @Override
    public CompletableFuture<List<byte[]>> zrevrangeByScore(byte[] key, byte[] max, byte[] min) {
        return executeCommand(commandObjects.zrevrangeByScore(key, max, min));
    }

    @Override
    public CompletableFuture<List<byte[]>> zrevrangeByScore(byte[] key, double max, double min, int offset, int count) {
        return executeCommand(commandObjects.zrevrangeByScore(key, max, min, offset, count));
    }

    @Override
    public CompletableFuture<List<Tuple>> zrevrangeByScoreWithScores(byte[] key, double max, double min) {
        return executeCommand(commandObjects.zrevrangeByScoreWithScores(key, max, min));
    }

    @Override
    public CompletableFuture<List<Tuple>> zrevrangeByScoreWithScores(byte[] key, byte[] max, byte[] min) {
        return executeCommand(commandObjects.zrevrangeByScoreWithScores(key, max, min));
    }

    @Override
    public CompletableFuture<List<Tuple>> zrevrangeByScoreWithScores(byte[] key, double max, double min, int offset,
        int count) {
        return executeCommand(commandObjects.zrevrangeByScoreWithScores(key, max, min, offset, count));
    }

    @Override
    public CompletableFuture<List<Tuple>> zrevrangeByScoreWithScores(byte[] key, byte[] max, byte[] min, int offset,
        int count) {
        return executeCommand(commandObjects.zrevrangeByScoreWithScores(key, max, min, offset, count));
    }

    @Override
    public CompletableFuture<Long> zremrangeByRank(byte[] key, long start, long stop) {
        return executeCommand(commandObjects.zremrangeByRank(key, start, stop));
    }

    @Override
    public CompletableFuture<Long> zlexcount(byte[] key, byte[] min, byte[] max) {
        return executeCommand(commandObjects.zlexcount(key, min, max));
    }

    @Override
    public CompletableFuture<List<byte[]>> zrangeByLex(byte[] key, byte[] min, byte[] max) {
        return executeCommand(commandObjects.zrangeByLex(key, min, max));
    }

    @Override
    public CompletableFuture<List<byte[]>> zrangeByLex(byte[] key, byte[] min, byte[] max, int offset, int count) {
        return executeCommand(commandObjects.zrangeByLex(key, min, max, offset, count));
    }

    @Override
    public CompletableFuture<List<byte[]>> zrevrangeByLex(byte[] key, byte[] max, byte[] min) {
        return executeCommand(commandObjects.zrevrangeByLex(key, max, min));
    }

    @Override
    public CompletableFuture<List<byte[]>> zrevrangeByLex(byte[] key, byte[] max, byte[] min, int offset, int count) {
        return executeCommand(commandObjects.zrevrangeByLex(key, max, min, offset, count));
    }

    @Override
    public CompletableFuture<Long> zremrangeByLex(byte[] key, byte[] min, byte[] max) {
        return executeCommand(commandObjects.zremrangeByLex(key, min, max));
    }

    @Override
    public CompletableFuture<ScanResult<Tuple>> zscan(byte[] key, byte[] cursor) {
        return AsyncJedisBinaryCommands.super.zscan(key, cursor);
    }

    @Override
    public CompletableFuture<ScanResult<Tuple>> zscan(byte[] key, byte[] cursor, ScanParams params) {
        return executeCommand(commandObjects.zscan(key, cursor, params));
    }

    @Override
    public CompletableFuture<KeyValue<byte[], Tuple>> bzpopmax(double timeout, byte[]... keys) {
        return executeCommand(commandObjects.bzpopmax(timeout, keys));
    }

    @Override
    public CompletableFuture<KeyValue<byte[], Tuple>> bzpopmin(double timeout, byte[]... keys) {
        return executeCommand(commandObjects.bzpopmin(timeout, keys));
    }

    @Override
    public CompletableFuture<List<byte[]>> zdiff(byte[]... keys) {
        return executeCommand(commandObjects.zdiff(keys));
    }

    @Override
    public CompletableFuture<List<Tuple>> zdiffWithScores(byte[]... keys) {
        return executeCommand(commandObjects.zdiffWithScores(keys));
    }

    @Override
    public CompletableFuture<Long> zdiffstore(byte[] dstkey, byte[]... keys) {
        return executeCommand(commandObjects.zdiffstore(dstkey, keys));
    }

    @Override
    public CompletableFuture<List<byte[]>> zinter(ZParams params, byte[]... keys) {
        return executeCommand(commandObjects.zinter(params, keys));
    }

    @Override
    public CompletableFuture<List<Tuple>> zinterWithScores(ZParams params, byte[]... keys) {
        return executeCommand(commandObjects.zinterWithScores(params, keys));
    }

    @Override
    public CompletableFuture<Long> zinterstore(byte[] dstkey, byte[]... sets) {
        return executeCommand(commandObjects.zinterstore(dstkey, sets));
    }

    @Override
    public CompletableFuture<Long> zinterstore(byte[] dstkey, ZParams params, byte[]... sets) {
        return executeCommand(commandObjects.zinterstore(dstkey, params, sets));
    }

    @Override
    public CompletableFuture<Long> zintercard(byte[]... keys) {
        return executeCommand(commandObjects.zintercard(keys));
    }

    @Override
    public CompletableFuture<Long> zintercard(long limit, byte[]... keys) {
        return executeCommand(commandObjects.zintercard(limit, keys));
    }

    @Override
    public CompletableFuture<Long> geosearchStore(byte[] dest, byte[] src, byte[] member, double radius, GeoUnit unit) {
        return executeCommand(commandObjects.geosearchStore(dest, src, member, radius, unit));
    }

    @Override
    public CompletableFuture<Long> geosearchStore(byte[] dest, byte[] src, GeoCoordinate coord, double radius,
        GeoUnit unit) {
        return executeCommand(commandObjects.geosearchStore(dest, src, coord, radius, unit));
    }

    @Override
    public CompletableFuture<Long> geosearchStore(byte[] dest, byte[] src, byte[] member, double width, double height,
        GeoUnit unit) {
        return executeCommand(commandObjects.geosearchStore(dest, src, member, width, height, unit));
    }

    @Override
    public CompletableFuture<Long> geosearchStore(byte[] dest, byte[] src, GeoCoordinate coord, double width,
        double height, GeoUnit unit) {
        return executeCommand(commandObjects.geosearchStore(dest, src, coord, width, height, unit));
    }

    @Override
    public CompletableFuture<Long> geosearchStore(byte[] dest, byte[] src, GeoSearchParam params) {
        return executeCommand(commandObjects.geosearchStore(dest, src, params));
    }

    @Override
    public CompletableFuture<Long> geosearchStoreStoreDist(byte[] dest, byte[] src, GeoSearchParam params) {
        return executeCommand(commandObjects.geosearchStoreStoreDist(dest, src, params));
    }

    @Override
    public CompletableFuture<Long> geoadd(String key, double longitude, double latitude, String member) {
        return executeCommand(commandObjects.geoadd(key, longitude, latitude, member));
    }

    @Override
    public CompletableFuture<Long> geoadd(String key, Map<String, GeoCoordinate> memberCoordinateMap) {
        return executeCommand(commandObjects.geoadd(key, memberCoordinateMap));
    }

    @Override
    public CompletableFuture<Long> geoadd(String key, GeoAddParams params,
        Map<String, GeoCoordinate> memberCoordinateMap) {
        return executeCommand(commandObjects.geoadd(key, params, memberCoordinateMap));
    }

    @Override
    public CompletableFuture<Double> geodist(String key, String member1, String member2) {
        return executeCommand(commandObjects.geodist(key, member1, member2));
    }

    @Override
    public CompletableFuture<Double> geodist(String key, String member1, String member2, GeoUnit unit) {
        return executeCommand(commandObjects.geodist(key, member1, member2, unit));
    }

    @Override
    public CompletableFuture<List<String>> geohash(String key, String... members) {
        return executeCommand(commandObjects.geohash(key, members));
    }

    @Override
    public CompletableFuture<List<GeoCoordinate>> geopos(String key, String... members) {
        return executeCommand(commandObjects.geopos(key, members));
    }

    @Override
    public CompletableFuture<List<GeoRadiusResponse>> georadius(String key, double longitude, double latitude,
        double radius, GeoUnit unit) {
        return executeCommand(commandObjects.georadius(key, longitude, latitude, radius, unit));
    }

    @Override
    public CompletableFuture<List<GeoRadiusResponse>> georadiusReadonly(String key, double longitude, double latitude,
        double radius, GeoUnit unit) {
        return executeCommand(commandObjects.georadiusReadonly(key, longitude, latitude, radius, unit));
    }

    @Override
    public CompletableFuture<List<GeoRadiusResponse>> georadius(String key, double longitude, double latitude,
        double radius, GeoUnit unit, GeoRadiusParam param) {
        return executeCommand(commandObjects.georadius(key, longitude, latitude, radius, unit, param));
    }

    @Override
    public CompletableFuture<List<GeoRadiusResponse>> georadiusReadonly(String key, double longitude, double latitude,
        double radius, GeoUnit unit, GeoRadiusParam param) {
        return executeCommand(commandObjects.georadiusReadonly(key, longitude, latitude, radius, unit, param));
    }

    @Override
    public CompletableFuture<List<GeoRadiusResponse>> georadiusByMemberReadonly(String key, String member,
        double radius, GeoUnit unit) {
        return executeCommand(commandObjects.georadiusByMemberReadonly(key, member, radius, unit));
    }

    @Override
    public CompletableFuture<List<GeoRadiusResponse>> georadiusByMemberReadonly(String key, String member,
        double radius, GeoUnit unit, GeoRadiusParam param) {
        return executeCommand(commandObjects.georadiusByMemberReadonly(key, member, radius, unit, param));
    }

    @Override
    public CompletableFuture<Long> georadiusStore(String key, double longitude, double latitude, double radius,
        GeoUnit unit, GeoRadiusParam param, GeoRadiusStoreParam storeParam) {
        return executeCommand(commandObjects.georadiusStore(key, longitude, latitude, radius, unit, param, storeParam));
    }

    @Override
    public CompletableFuture<Long> georadiusByMemberStore(String key, String member, double radius, GeoUnit unit,
        GeoRadiusParam param, GeoRadiusStoreParam storeParam) {
        return executeCommand(commandObjects.georadiusByMemberStore(key, member, radius, unit, param, storeParam));
    }

    @Override
    public CompletableFuture<List<GeoRadiusResponse>> geosearch(String key, String member, double radius,
        GeoUnit unit) {
        return executeCommand(commandObjects.geosearch(key, member, radius, unit));
    }

    @Override
    public CompletableFuture<List<GeoRadiusResponse>> geosearch(String key, GeoCoordinate coord, double radius,
        GeoUnit unit) {
        return executeCommand(commandObjects.geosearch(key, coord, radius, unit));
    }

    @Override
    public CompletableFuture<List<GeoRadiusResponse>> geosearch(String key, String member, double width, double height,
        GeoUnit unit) {
        return executeCommand(commandObjects.geosearch(key, member, width, height, unit));
    }

    @Override
    public CompletableFuture<List<GeoRadiusResponse>> geosearch(String key, GeoCoordinate coord, double width,
        double height, GeoUnit unit) {
        return executeCommand(commandObjects.geosearch(key, coord, width, height, unit));
    }

    @Override
    public CompletableFuture<List<GeoRadiusResponse>> geosearch(String key, GeoSearchParam params) {
        return executeCommand(commandObjects.geosearch(key, params));
    }

    @Override
    public CompletableFuture<Long> geosearchStore(String dest, String src, GeoCoordinate coord, double width,
        double height, GeoUnit unit) {
        return executeCommand(commandObjects.geosearchStore(dest, src, coord, width, height, unit));
    }

    @Override
    public CompletableFuture<Long> geosearchStore(String dest, String src, GeoSearchParam params) {
        return executeCommand(commandObjects.geosearchStore(dest, src, params));
    }

    @Override
    public CompletableFuture<Long> geosearchStoreStoreDist(String dest, String src, GeoSearchParam params) {
        return executeCommand(commandObjects.geosearchStoreStoreDist(dest, src, params));
    }

    @Override
    public CompletableFuture<Long> pfadd(byte[] key, byte[]... elements) {
        return executeCommand(commandObjects.pfadd(key, elements));
    }

    @Override
    public CompletableFuture<String> pfmerge(byte[] destkey, byte[]... sourcekeys) {
        return executeCommand(commandObjects.pfmerge(destkey, sourcekeys));
    }

    @Override
    public CompletableFuture<Long> pfadd(String key, String... elements) {
        return executeCommand(commandObjects.pfadd(key, elements));
    }

    @Override
    public CompletableFuture<String> pfmerge(String destkey, String... sourcekeys) {
        return executeCommand(commandObjects.pfmerge(destkey, sourcekeys));
    }

    @Override
    public CompletableFuture<Boolean> exists(byte[] key) {
        return executeCommand(commandObjects.exists(key));
    }

    @Override
    public CompletableFuture<Long> exists(byte[]... keys) {
        return executeCommand(commandObjects.exists(keys));
    }

    @Override
    public CompletableFuture<Long> persist(byte[] key) {
        return executeCommand(commandObjects.persist(key));
    }

    @Override
    public CompletableFuture<byte[]> dump(byte[] key) {
        return executeCommand(commandObjects.dump(key));
    }

    @Override
    public CompletableFuture<Long> expire(byte[] key, long seconds) {
        return executeCommand(commandObjects.expire(key, seconds));
    }

    @Override
    public CompletableFuture<Long> expire(byte[] key, long seconds, ExpiryOption expiryOption) {
        return executeCommand(commandObjects.expire(key, seconds, expiryOption));
    }

    @Override
    public CompletableFuture<Long> expireTime(byte[] key) {
        return executeCommand(commandObjects.expireTime(key));
    }

    @Override
    public CompletableFuture<Long> pexpireTime(byte[] key) {
        return executeCommand(commandObjects.pexpireTime(key));
    }

    @Override
    public CompletableFuture<Long> expireAt(byte[] key, long unixTime) {
        return executeCommand(commandObjects.expireAt(key, unixTime));
    }

    @Override
    public CompletableFuture<Long> expireAt(byte[] key, long unixTime, ExpiryOption expiryOption) {
        return executeCommand(commandObjects.expireAt(key, unixTime, expiryOption));
    }

    @Override
    public CompletableFuture<Long> pexpireAt(byte[] key, long millisecondsTimestamp) {
        return executeCommand(commandObjects.pexpireAt(key, millisecondsTimestamp));
    }

    @Override
    public CompletableFuture<Long> pexpireAt(byte[] key, long millisecondsTimestamp, ExpiryOption expiryOption) {
        return executeCommand(commandObjects.pexpireAt(key, millisecondsTimestamp, expiryOption));
    }

    @Override
    public CompletableFuture<Long> pttl(byte[] key) {
        return executeCommand(commandObjects.pttl(key));
    }

    @Override
    public CompletableFuture<Long> touch(byte[] key) {
        return executeCommand(commandObjects.touch(key));
    }

    @Override
    public CompletableFuture<Long> touch(byte[]... keys) {
        return executeCommand(commandObjects.touch(keys));
    }

    @Override
    public CompletableFuture<List<byte[]>> sort(byte[] key) {
        return executeCommand(commandObjects.sort(key));
    }

    @Override
    public CompletableFuture<List<byte[]>> sort(byte[] key, SortingParams sortingParams) {
        return executeCommand(commandObjects.sort(key, sortingParams));
    }

    @Override
    public CompletableFuture<List<byte[]>> sortReadonly(byte[] key, SortingParams sortingParams) {
        return executeCommand(commandObjects.sortReadonly(key, sortingParams));
    }

    @Override
    public CompletableFuture<Long> del(byte[] key) {
        return executeCommand(commandObjects.del(key));
    }

    @Override
    public CompletableFuture<Long> del(byte[]... keys) {
        return executeCommand(commandObjects.del(keys));
    }

    @Override
    public CompletableFuture<Long> unlink(byte[] key) {
        return executeCommand(commandObjects.unlink(key));
    }

    @Override
    public CompletableFuture<Long> unlink(byte[]... keys) {
        return executeCommand(commandObjects.unlink(keys));
    }

    @Override
    public CompletableFuture<Boolean> copy(byte[] srcKey, byte[] dstKey, boolean replace) {
        return executeCommand(commandObjects.copy(srcKey, dstKey, replace));
    }

    @Override
    public CompletableFuture<Long> renamenx(byte[] oldkey, byte[] newkey) {
        return executeCommand(commandObjects.renamenx(oldkey, newkey));
    }

    @Override
    public CompletableFuture<Long> sort(byte[] key, SortingParams sortingParams, byte[] dstkey) {
        return executeCommand(commandObjects.sort(key, sortingParams, dstkey));
    }

    @Override
    public CompletableFuture<Long> sort(byte[] key, byte[] dstkey) {
        return executeCommand(commandObjects.sort(key, dstkey));
    }

    @Override
    public CompletableFuture<Long> memoryUsage(byte[] key) {
        return executeCommand(commandObjects.memoryUsage(key));
    }

    @Override
    public CompletableFuture<Long> memoryUsage(byte[] key, int samples) {
        return executeCommand(commandObjects.memoryUsage(key, samples));
    }

    @Override
    public CompletableFuture<Long> objectRefcount(byte[] key) {
        return executeCommand(commandObjects.objectRefcount(key));
    }

    @Override
    public CompletableFuture<Long> pexpireAt(String key, long millisecondsTimestamp, ExpiryOption expiryOption) {
        return executeCommand(commandObjects.pexpireAt(key, millisecondsTimestamp, expiryOption));
    }

    @Override
    public CompletableFuture<Long> sort(String key, String dstkey) {
        return executeCommand(commandObjects.sort(key, dstkey));
    }

    @Override
    public CompletableFuture<Long> sort(String key, SortingParams sortingParams, String dstkey) {
        return executeCommand(commandObjects.sort(key, sortingParams, dstkey));
    }

    @Override
    public CompletableFuture<List<String>> sortReadonly(String key, SortingParams sortingParams) {
        return executeCommand(commandObjects.sortReadonly(key, sortingParams));
    }

    @Override
    public CompletableFuture<Boolean> copy(String srcKey, String dstKey, boolean replace) {
        return executeCommand(commandObjects.copy(srcKey, dstKey, replace));
    }

    @Override
    public CompletableFuture<Long> renamenx(String oldkey, String newkey) {
        return executeCommand(commandObjects.renamenx(oldkey, newkey));
    }

    @Override
    public CompletableFuture<Long> memoryUsage(String key) {
        return executeCommand(commandObjects.memoryUsage(key));
    }

    @Override
    public CompletableFuture<Long> memoryUsage(String key, int samples) {
        return executeCommand(commandObjects.memoryUsage(key, samples));
    }

    @Override
    public CompletableFuture<Long> objectRefcount(String key) {
        return executeCommand(commandObjects.objectRefcount(key));
    }

    @Override
    public CompletableFuture<Long> rpush(byte[] key, byte[]... args) {
        return executeCommand(commandObjects.rpush(key, args));
    }

    @Override
    public CompletableFuture<Long> lpush(byte[] key, byte[]... args) {
        return executeCommand(commandObjects.lpush(key, args));
    }

    @Override
    public CompletableFuture<Long> llen(byte[] key) {
        return executeCommand(commandObjects.llen(key));
    }

    @Override
    public CompletableFuture<List<byte[]>> lrange(byte[] key, long start, long stop) {
        return executeCommand(commandObjects.lrange(key, start, stop));
    }

    @Override
    public CompletableFuture<String> ltrim(byte[] key, long start, long stop) {
        return executeCommand(commandObjects.ltrim(key, start, stop));
    }

    @Override
    public CompletableFuture<byte[]> lindex(byte[] key, long index) {
        return executeCommand(commandObjects.lindex(key, index));
    }

    @Override
    public CompletableFuture<String> lset(byte[] key, long index, byte[] value) {
        return executeCommand(commandObjects.lset(key, index, value));
    }

    @Override
    public CompletableFuture<Long> lrem(byte[] key, long count, byte[] value) {
        return executeCommand(commandObjects.lrem(key, count, value));
    }

    @Override
    public CompletableFuture<byte[]> lpop(byte[] key) {
        return executeCommand(commandObjects.lpop(key));
    }

    @Override
    public CompletableFuture<List<byte[]>> lpop(byte[] key, int count) {
        return executeCommand(commandObjects.lpop(key, count));
    }

    @Override
    public CompletableFuture<Long> lpos(byte[] key, byte[] element) {
        return executeCommand(commandObjects.lpos(key, element));
    }

    @Override
    public CompletableFuture<Long> lpos(byte[] key, byte[] element, LPosParams params) {
        return executeCommand(commandObjects.lpos(key, element, params));
    }

    @Override
    public CompletableFuture<List<Long>> lpos(byte[] key, byte[] element, LPosParams params, long count) {
        return executeCommand(commandObjects.lpos(key, element, params, count));
    }

    @Override
    public CompletableFuture<byte[]> rpop(byte[] key) {
        return executeCommand(commandObjects.rpop(key));
    }

    @Override
    public CompletableFuture<List<byte[]>> rpop(byte[] key, int count) {
        return executeCommand(commandObjects.rpop(key, count));
    }

    @Override
    public CompletableFuture<Long> linsert(byte[] key, ListPosition where, byte[] pivot, byte[] value) {
        return executeCommand(commandObjects.linsert(key, where, pivot, value));
    }

    @Override
    public CompletableFuture<Long> lpushx(byte[] key, byte[]... args) {
        return executeCommand(commandObjects.lpushx(key, args));
    }

    @Override
    public CompletableFuture<List<byte[]>> blpop(int timeout, byte[]... keys) {
        return executeCommand(commandObjects.blpop(timeout, keys));
    }

    @Override
    public CompletableFuture<KeyValue<byte[], byte[]>> blpop(double timeout, byte[]... keys) {
        return executeCommand(commandObjects.blpop(timeout, keys));
    }

    @Override
    public CompletableFuture<List<byte[]>> brpop(int timeout, byte[]... keys) {
        return executeCommand(commandObjects.brpop(timeout, keys));
    }

    @Override
    public CompletableFuture<KeyValue<byte[], byte[]>> brpop(double timeout, byte[]... keys) {
        return executeCommand(commandObjects.brpop(timeout, keys));
    }

    @Override
    public CompletableFuture<byte[]> rpoplpush(byte[] srckey, byte[] dstkey) {
        return executeCommand(commandObjects.rpoplpush(srckey, dstkey));
    }

    @Override
    public CompletableFuture<byte[]> brpoplpush(byte[] source, byte[] destination, int timeout) {
        return executeCommand(commandObjects.brpoplpush(source, destination, timeout));
    }

    @Override
    public CompletableFuture<byte[]> lmove(byte[] srcKey, byte[] dstKey, ListDirection from, ListDirection to) {
        return executeCommand(commandObjects.lmove(srcKey, dstKey, from, to));
    }

    @Override
    public CompletableFuture<byte[]> blmove(byte[] srcKey, byte[] dstKey, ListDirection from, ListDirection to,
        double timeout) {
        return executeCommand(commandObjects.blmove(srcKey, dstKey, from, to, timeout));
    }

    @Override
    public CompletableFuture<KeyValue<byte[], List<byte[]>>> lmpop(ListDirection direction, byte[]... keys) {
        return executeCommand(commandObjects.lmpop(direction, keys));
    }

    @Override
    public CompletableFuture<KeyValue<byte[], List<byte[]>>> lmpop(ListDirection direction, int count, byte[]... keys) {
        return executeCommand(commandObjects.lmpop(direction, count, keys));
    }

    @Override
    public CompletableFuture<KeyValue<byte[], List<byte[]>>> blmpop(double timeout, ListDirection direction,
        byte[]... keys) {
        return executeCommand(commandObjects.blmpop(timeout, direction, keys));
    }

    @Override
    public CompletableFuture<KeyValue<byte[], List<byte[]>>> blmpop(double timeout, ListDirection direction, int count,
        byte[]... keys) {
        return executeCommand(commandObjects.blmpop(timeout, direction, count, keys));
    }

    @Override
    public CompletableFuture<Long> rpush(String key, String... strings) {
        return executeCommand(commandObjects.rpush(key, strings));
    }

    @Override
    public CompletableFuture<Long> lpush(String key, String... strings) {
        return executeCommand(commandObjects.lpush(key, strings));
    }

    @Override
    public CompletableFuture<Long> llen(String key) {
        return executeCommand(commandObjects.llen(key));
    }

    @Override
    public CompletableFuture<List<String>> lrange(String key, long start, long stop) {
        return executeCommand(commandObjects.lrange(key, start, stop));
    }

    @Override
    public CompletableFuture<String> ltrim(String key, long start, long stop) {
        return executeCommand(commandObjects.ltrim(key, start, stop));
    }

    @Override
    public CompletableFuture<String> lindex(String key, long index) {
        return executeCommand(commandObjects.lindex(key, index));
    }

    @Override
    public CompletableFuture<String> lset(String key, long index, String value) {
        return executeCommand(commandObjects.lset(key, index, value));
    }

    @Override
    public CompletableFuture<Long> lrem(String key, long count, String value) {
        return executeCommand(commandObjects.lrem(key, count, value));
    }

    @Override
    public CompletableFuture<String> lpop(String key) {
        return executeCommand(commandObjects.lpop(key));
    }

    @Override
    public CompletableFuture<List<String>> lpop(String key, int count) {
        return executeCommand(commandObjects.lpop(key, count));
    }

    @Override
    public CompletableFuture<Long> lpos(String key, String element) {
        return executeCommand(commandObjects.lpos(key, element));
    }

    @Override
    public CompletableFuture<Long> lpos(String key, String element, LPosParams params) {
        return executeCommand(commandObjects.lpos(key, element, params));
    }

    @Override
    public CompletableFuture<List<Long>> lpos(String key, String element, LPosParams params, long count) {
        return executeCommand(commandObjects.lpos(key, element, params, count));
    }

    @Override
    public CompletableFuture<String> rpop(String key) {
        return executeCommand(commandObjects.rpop(key));
    }

    @Override
    public CompletableFuture<List<String>> rpop(String key, int count) {
        return executeCommand(commandObjects.rpop(key, count));
    }

    @Override
    public CompletableFuture<Long> linsert(String key, ListPosition where, String pivot, String value) {
        return executeCommand(commandObjects.linsert(key, where, pivot, value));
    }

    @Override
    public CompletableFuture<Long> lpushx(String key, String... strings) {
        return executeCommand(commandObjects.lpushx(key, strings));
    }

    @Override
    public CompletableFuture<List<String>> blpop(int timeout, String... keys) {
        return executeCommand(commandObjects.blpop(timeout, keys));
    }

    @Override
    public CompletableFuture<KeyValue<String, String>> blpop(double timeout, String... keys) {
        return executeCommand(commandObjects.blpop(timeout, keys));
    }

    @Override
    public CompletableFuture<List<String>> brpop(int timeout, String... keys) {
        return executeCommand(commandObjects.brpop(timeout, keys));
    }

    @Override
    public CompletableFuture<KeyValue<String, String>> brpop(double timeout, String... keys) {
        return executeCommand(commandObjects.brpop(timeout, keys));
    }

    @Override
    public CompletableFuture<String> rpoplpush(String srckey, String dstkey) {
        return executeCommand(commandObjects.rpoplpush(srckey, dstkey));
    }

    @Override
    public CompletableFuture<String> brpoplpush(String source, String destination, int timeout) {
        return executeCommand(commandObjects.brpoplpush(source, destination, timeout));
    }

    @Override
    public CompletableFuture<String> lmove(String srcKey, String dstKey, ListDirection from, ListDirection to) {
        return executeCommand(commandObjects.lmove(srcKey, dstKey, from, to));
    }

    @Override
    public CompletableFuture<String> blmove(String srcKey, String dstKey, ListDirection from, ListDirection to,
        double timeout) {
        return executeCommand(commandObjects.blmove(srcKey, dstKey, from, to, timeout));
    }

    @Override
    public CompletableFuture<KeyValue<String, List<String>>> lmpop(ListDirection direction, String... keys) {
        return executeCommand(commandObjects.lmpop(direction, keys));
    }

    @Override
    public CompletableFuture<KeyValue<String, List<String>>> lmpop(ListDirection direction, int count, String... keys) {
        return executeCommand(commandObjects.lmpop(direction, count, keys));
    }

    @Override
    public CompletableFuture<KeyValue<String, List<String>>> blmpop(double timeout, ListDirection direction,
        String... keys) {
        return executeCommand(commandObjects.blmpop(timeout, direction, keys));
    }

    @Override
    public CompletableFuture<KeyValue<String, List<String>>> blmpop(double timeout, ListDirection direction, int count,
        String... keys) {
        return executeCommand(commandObjects.blmpop(timeout, direction, count, keys));
    }

    @Override
    public CompletableFuture<KeyValue<Long, Long>> waitAOF(byte[] sampleKey, long numLocal, long numReplicas,
        long timeout) {
        return executeCommand(commandObjects.waitAOF(sampleKey, numLocal, numReplicas, timeout));
    }

    @Override
    public CompletableFuture<Object> evalsha(byte[] sha1, byte[] sampleKey) {
        return executeCommand(commandObjects.evalsha(sha1, sampleKey));
    }

    @Override
    public CompletableFuture<String> scriptKill(byte[] sampleKey) {
        return executeCommand(commandObjects.scriptKill(sampleKey));
    }

    @Override
    public CompletableFuture<KeyValue<Long, Long>> waitAOF(String sampleKey, long numLocal, long numReplicas,
        long timeout) {
        return executeCommand(commandObjects.waitAOF(sampleKey, numLocal, numReplicas, timeout));
    }

    @Override
    public CompletableFuture<Object> evalsha(String sha1, String sampleKey) {
        return executeCommand(commandObjects.evalsha(sha1, sampleKey));
    }

    @Override
    public CompletableFuture<Object> evalReadonly(byte[] script, List<byte[]> keys, List<byte[]> args) {
        return executeCommand(commandObjects.evalReadonly(script, keys, args));
    }

    @Override
    public CompletableFuture<Object> evalsha(byte[] sha1) {
        return executeCommand(commandObjects.evalsha(sha1));
    }

    @Override
    public CompletableFuture<Object> evalsha(byte[] sha1, int keyCount, byte[]... params) {
        return executeCommand(commandObjects.evalsha(sha1, keyCount, params));
    }

    @Override
    public CompletableFuture<Object> evalsha(byte[] sha1, List<byte[]> keys, List<byte[]> args) {
        return executeCommand(commandObjects.evalsha(sha1, keys, args));
    }

    @Override
    public CompletableFuture<Object> evalshaReadonly(byte[] sha1, List<byte[]> keys, List<byte[]> args) {
        return executeCommand(commandObjects.evalshaReadonly(sha1, keys, args));
    }

    @Override
    public CompletableFuture<List<Tuple>> zrangeWithScores(String key, long start, long stop) {
        return executeCommand(commandObjects.zrangeWithScores(key, start, stop));
    }

    @Override
    public CompletableFuture<List<Tuple>> zrevrangeWithScores(String key, long start, long stop) {
        return executeCommand(commandObjects.zrevrangeWithScores(key, start, stop));
    }

    @Override
    public CompletableFuture<List<Tuple>> zrangeWithScores(String key, ZRangeParams zRangeParams) {
        return executeCommand(commandObjects.zrangeWithScores(key, zRangeParams));
    }

    @Override
    public CompletableFuture<Long> zinterstore(String dstkey, String... sets) {
        return executeCommand(commandObjects.zinterstore(dstkey, sets));
    }

    @Override
    public CompletableFuture<Long> zinterstore(String dstkey, ZParams params, String... sets) {
        return executeCommand(commandObjects.zinterstore(dstkey, params, sets));
    }

    @Override
    public CompletableFuture<KeyValue<String, List<Tuple>>> bzmpop(double timeout, SortedSetOption option,
        String... keys) {
        return executeCommand(commandObjects.bzmpop(timeout, option, keys));
    }

    @Override
    public CompletableFuture<byte[]> xadd(byte[] key, XAddParams params, Map<byte[], byte[]> hash) {
        return executeCommand(commandObjects.xadd(key, params, hash));
    }

    @Override
    public CompletableFuture<Long> xlen(byte[] key) {
        return executeCommand(commandObjects.xlen(key));
    }

    @Override
    public CompletableFuture<List<Object>> xrange(byte[] key, byte[] start, byte[] end) {
        return executeCommand(commandObjects.xrange(key, start, end));
    }

    @Override
    public CompletableFuture<List<Object>> xrange(byte[] key, byte[] start, byte[] end, int count) {
        return executeCommand(commandObjects.xrange(key, start, end, count));
    }

    @Override
    public CompletableFuture<List<Object>> xrevrange(byte[] key, byte[] end, byte[] start) {
        return executeCommand(commandObjects.xrevrange(key, end, start));
    }

    @Override
    public CompletableFuture<List<Object>> xrevrange(byte[] key, byte[] end, byte[] start, int count) {
        return executeCommand(commandObjects.xrevrange(key, end, start, count));
    }

    @Override
    public CompletableFuture<Long> xack(byte[] key, byte[] group, byte[]... ids) {
        return executeCommand(commandObjects.xack(key, group, ids));
    }

    @Override
    public CompletableFuture<String> xgroupCreate(byte[] key, byte[] groupName, byte[] id, boolean makeStream) {
        return executeCommand(commandObjects.xgroupCreate(key, groupName, id, makeStream));
    }

    @Override
    public CompletableFuture<String> xgroupSetID(byte[] key, byte[] groupName, byte[] id) {
        return executeCommand(commandObjects.xgroupSetID(key, groupName, id));
    }

    @Override
    public CompletableFuture<Long> xgroupDestroy(byte[] key, byte[] groupName) {
        return executeCommand(commandObjects.xgroupDestroy(key, groupName));
    }

    @Override
    public CompletableFuture<Boolean> xgroupCreateConsumer(byte[] key, byte[] groupName, byte[] consumerName) {
        return executeCommand(commandObjects.xgroupCreateConsumer(key, groupName, consumerName));
    }

    @Override
    public CompletableFuture<Long> xgroupDelConsumer(byte[] key, byte[] groupName, byte[] consumerName) {
        return executeCommand(commandObjects.xgroupDelConsumer(key, groupName, consumerName));
    }

    @Override
    public CompletableFuture<Long> xdel(byte[] key, byte[]... ids) {
        return executeCommand(commandObjects.xdel(key, ids));
    }

    @Override
    public CompletableFuture<Long> xtrim(byte[] key, long maxLen, boolean approximateLength) {
        return executeCommand(commandObjects.xtrim(key, maxLen, approximateLength));
    }

    @Override
    public CompletableFuture<Long> xtrim(byte[] key, XTrimParams params) {
        return executeCommand(commandObjects.xtrim(key, params));
    }

    @Override
    public CompletableFuture<Object> xpending(byte[] key, byte[] groupName) {
        return executeCommand(commandObjects.xpending(key, groupName));
    }

    @Override
    public CompletableFuture<List<Object>> xpending(byte[] key, byte[] groupName, XPendingParams params) {
        return executeCommand(commandObjects.xpending(key, groupName, params));
    }

    @Override
    public CompletableFuture<List<byte[]>> xclaim(byte[] key, byte[] group, byte[] consumerName, long minIdleTime,
        XClaimParams params, byte[]... ids) {
        return executeCommand(commandObjects.xclaim(key, group, consumerName, minIdleTime, params, ids));
    }

    @Override
    public CompletableFuture<List<Object>> xautoclaim(byte[] key, byte[] groupName, byte[] consumerName,
        long minIdleTime, byte[] start, XAutoClaimParams params) {
        return executeCommand(commandObjects.xautoclaim(key, groupName, consumerName, minIdleTime, start, params));
    }

    @Override
    public CompletableFuture<List<Object>> xautoclaimJustId(byte[] key, byte[] groupName, byte[] consumerName,
        long minIdleTime, byte[] start, XAutoClaimParams params) {
        return executeCommand(
            commandObjects.xautoclaimJustId(key, groupName, consumerName, minIdleTime, start, params));
    }

    @Override
    public CompletableFuture<Object> xinfoStream(byte[] key) {
        return executeCommand(commandObjects.xinfoStream(key));
    }

    @Override
    public CompletableFuture<Object> xinfoStreamFull(byte[] key) {
        return executeCommand(commandObjects.xinfoStreamFull(key));
    }

    @Override
    public CompletableFuture<Object> xinfoStreamFull(byte[] key, int count) {
        return executeCommand(commandObjects.xinfoStreamFull(key, count));
    }

    @Override
    public CompletableFuture<List<Object>> xinfoGroups(byte[] key) {
        return executeCommand(commandObjects.xinfoGroups(key));
    }

    @Override
    public CompletableFuture<List<Object>> xinfoConsumers(byte[] key, byte[] group) {
        return executeCommand(commandObjects.xinfoConsumers(key, group));
    }

    @Override
    public CompletableFuture<StreamEntryID> xadd(String key, StreamEntryID id, Map<String, String> hash) {
        return executeCommand(commandObjects.xadd(key, id, hash));
    }

    @Override
    public CompletableFuture<StreamEntryID> xadd(String key, XAddParams params, Map<String, String> hash) {
        return executeCommand(commandObjects.xadd(key, params, hash));
    }

    @Override
    public CompletableFuture<Long> xlen(String key) {
        return executeCommand(commandObjects.xlen(key));
    }

    @Override
    public CompletableFuture<List<StreamEntry>> xrange(String key, StreamEntryID start, StreamEntryID end) {
        return executeCommand(commandObjects.xrange(key, start, end));
    }

    @Override
    public CompletableFuture<List<StreamEntry>> xrange(String key, StreamEntryID start, StreamEntryID end, int count) {
        return executeCommand(commandObjects.xrange(key, start, end, count));
    }

    @Override
    public CompletableFuture<List<StreamEntry>> xrevrange(String key, StreamEntryID end, StreamEntryID start) {
        return executeCommand(commandObjects.xrevrange(key, end, start));
    }

    @Override
    public CompletableFuture<List<StreamEntry>> xrevrange(String key, StreamEntryID end, StreamEntryID start,
        int count) {
        return executeCommand(commandObjects.xrevrange(key, end, start, count));
    }

    @Override
    public CompletableFuture<List<StreamEntry>> xrange(String key, String start, String end) {
        return executeCommand(commandObjects.xrange(key, start, end));
    }

    @Override
    public CompletableFuture<List<StreamEntry>> xrange(String key, String start, String end, int count) {
        return executeCommand(commandObjects.xrange(key, start, end, count));
    }

    @Override
    public CompletableFuture<List<StreamEntry>> xrevrange(String key, String end, String start) {
        return executeCommand(commandObjects.xrevrange(key, end, start));
    }

    @Override
    public CompletableFuture<List<StreamEntry>> xrevrange(String key, String end, String start, int count) {
        return executeCommand(commandObjects.xrevrange(key, end, start, count));
    }

    @Override
    public CompletableFuture<Long> xack(String key, String group, StreamEntryID... ids) {
        return executeCommand(commandObjects.xack(key, group, ids));
    }

    @Override
    public CompletableFuture<String> xgroupCreate(String key, String groupName, StreamEntryID id, boolean makeStream) {
        return executeCommand(commandObjects.xgroupCreate(key, groupName, id, makeStream));
    }

    @Override
    public CompletableFuture<String> xgroupSetID(String key, String groupName, StreamEntryID id) {
        return executeCommand(commandObjects.xgroupSetID(key, groupName, id));
    }

    @Override
    public CompletableFuture<Long> xgroupDestroy(String key, String groupName) {
        return executeCommand(commandObjects.xgroupDestroy(key, groupName));
    }

    @Override
    public CompletableFuture<Boolean> xgroupCreateConsumer(String key, String groupName, String consumerName) {
        return executeCommand(commandObjects.xgroupCreateConsumer(key, groupName, consumerName));
    }

    @Override
    public CompletableFuture<Long> xgroupDelConsumer(String key, String groupName, String consumerName) {
        return executeCommand(commandObjects.xgroupDelConsumer(key, groupName, consumerName));
    }

    @Override
    public CompletableFuture<Long> xdel(String key, StreamEntryID... ids) {
        return executeCommand(commandObjects.xdel(key, ids));
    }

    @Override
    public CompletableFuture<Long> xtrim(String key, long maxLen, boolean approximate) {
        return executeCommand(commandObjects.xtrim(key, maxLen, approximate));
    }

    @Override
    public CompletableFuture<Long> xtrim(String key, XTrimParams params) {
        return executeCommand(commandObjects.xtrim(key, params));
    }

    @Override
    public CompletableFuture<StreamPendingSummary> xpending(String key, String groupName) {
        return executeCommand(commandObjects.xpending(key, groupName));
    }

    @Override
    public CompletableFuture<List<StreamPendingEntry>> xpending(String key, String groupName, XPendingParams params) {
        return executeCommand(commandObjects.xpending(key, groupName, params));
    }

    @Override
    public CompletableFuture<Map<String, List<StreamEntry>>> xreadGroupAsMap(String groupName, String consumer,
        XReadGroupParams xReadGroupParams, Map<String, StreamEntryID> streams) {
        return executeCommand(commandObjects.xreadGroupAsMap(groupName, consumer, xReadGroupParams, streams));
    }

    @Override
    public CompletableFuture<Long> setrange(byte[] key, long offset, byte[] value) {
        return executeCommand(commandObjects.setrange(key, offset, value));
    }

    @Override
    public CompletableFuture<byte[]> getrange(byte[] key, long startOffset, long endOffset) {
        return executeCommand(commandObjects.getrange(key, startOffset, endOffset));
    }

    @Override
    public CompletableFuture<Long> setnx(byte[] key, byte[] value) {
        return executeCommand(commandObjects.setnx(key, value));
    }

    @Override
    public CompletableFuture<String> setex(byte[] key, long seconds, byte[] value) {
        return executeCommand(commandObjects.setex(key, seconds, value));
    }

    @Override
    public CompletableFuture<String> psetex(byte[] key, long milliseconds, byte[] value) {
        return executeCommand(commandObjects.psetex(key, milliseconds, value));
    }

    @Override
    public CompletableFuture<List<byte[]>> mget(byte[]... keys) {
        return executeCommand(commandObjects.mget(keys));
    }

    @Override
    public CompletableFuture<String> mset(byte[]... keysvalues) {
        return executeCommand(commandObjects.mset(keysvalues));
    }

    @Override
    public CompletableFuture<Long> msetnx(byte[]... keysvalues) {
        return executeCommand(commandObjects.msetnx(keysvalues));
    }

    @Override
    public CompletableFuture<Long> incrBy(byte[] key, long increment) {
        return executeCommand(commandObjects.incrBy(key, increment));
    }

    @Override
    public CompletableFuture<Double> incrByFloat(byte[] key, double increment) {
        return executeCommand(commandObjects.incrByFloat(key, increment));
    }

    @Override
    public CompletableFuture<Long> decr(byte[] key) {
        return executeCommand(commandObjects.decr(key));
    }

    @Override
    public CompletableFuture<Long> append(byte[] key, byte[] value) {
        return executeCommand(commandObjects.append(key, value));
    }

    @Override
    public CompletableFuture<byte[]> substr(byte[] key, int start, int end) {
        return executeCommand(commandObjects.substr(key, start, end));
    }

    @Override
    public CompletableFuture<Long> strlen(byte[] key) {
        return executeCommand(commandObjects.strlen(key));
    }

    @Override
    public CompletableFuture<Long> setrange(String key, long offset, String value) {
        return executeCommand(commandObjects.setrange(key, offset, value));
    }

    @Override
    public CompletableFuture<String> getrange(String key, long startOffset, long endOffset) {
        return executeCommand(commandObjects.getrange(key, startOffset, endOffset));
    }

    @Override
    public CompletableFuture<Long> setnx(String key, String value) {
        return executeCommand(commandObjects.setnx(key, value));
    }

    @Override
    public CompletableFuture<String> setex(String key, long seconds, String value) {
        return executeCommand(commandObjects.setex(key, seconds, value));
    }

    @Override
    public CompletableFuture<String> psetex(String key, long milliseconds, String value) {
        return executeCommand(commandObjects.psetex(key, milliseconds, value));
    }

    @Override
    public CompletableFuture<List<String>> mget(String... keys) {
        return executeCommand(commandObjects.mget(keys));
    }

    @Override
    public CompletableFuture<String> mset(String... keysvalues) {
        return executeCommand(commandObjects.mset(keysvalues));
    }

    @Override
    public CompletableFuture<Long> msetnx(String... keysvalues) {
        return executeCommand(commandObjects.msetnx(keysvalues));
    }

    @Override
    public CompletableFuture<Long> incrBy(String key, long increment) {
        return executeCommand(commandObjects.incrBy(key, increment));
    }

    @Override
    public CompletableFuture<Double> incrByFloat(String key, double increment) {
        return executeCommand(commandObjects.incrByFloat(key, increment));
    }

    @Override
    public CompletableFuture<Long> decr(String key) {
        return executeCommand(commandObjects.decr(key));
    }

    @Override
    public CompletableFuture<Long> decrBy(String key, long decrement) {
        return executeCommand(commandObjects.decrBy(key, decrement));
    }

    @Override
    public CompletableFuture<Long> append(String key, String value) {
        return executeCommand(commandObjects.append(key, value));
    }

    @Override
    public CompletableFuture<String> substr(String key, int start, int end) {
        return executeCommand(commandObjects.substr(key, start, end));
    }

    @Override
    public CompletableFuture<Long> strlen(String key) {
        return executeCommand(commandObjects.strlen(key));
    }

    @Override
    public CompletableFuture<Boolean> scriptExists(byte[] sha1, byte[] sampleKey) {
        return executeCommand(commandObjects.scriptExists(sampleKey, new byte[][]{sha1}))
            .thenApply(list -> list.get(0));
    }

    @Override
    public CompletableFuture<Boolean> scriptExists(String sha1, String sampleKey) {
        return executeCommand(commandObjects.scriptExists(sampleKey, new String[]{sha1}))
            .thenApply(list -> list.get(0));
    }

    @Override
    public CompletableFuture<KeyValue<byte[], List<Tuple>>> zmpop(SortedSetOption option, byte[]... keys) {
        return executeCommand(commandObjects.zmpop(option, keys));
    }

    @Override
    public CompletableFuture<KeyValue<byte[], List<Tuple>>> zmpop(SortedSetOption option, int count, byte[]... keys) {
        return executeCommand(commandObjects.zmpop(option, count, keys));
    }

    @Override
    public CompletableFuture<List<Object>> xreadGroup(byte[] groupName, byte[] consumer,
        XReadGroupParams xReadGroupParams, Entry<byte[], byte[]>... streams) {
        return executeCommand(commandObjects.xreadGroup(groupName, consumer, xReadGroupParams, streams));
    }

    @Override
    public CompletableFuture<ScanResult<byte[]>> sscan(byte[] key, byte[] cursor) {
        return executeCommand(commandObjects.sscan(key, cursor, new ScanParams()));
    }

    @Override
    public CompletableFuture<ScanResult<String>> sscan(String key, String cursor) {
        return executeCommand(commandObjects.sscan(key, cursor, new ScanParams()));
    }

    @Override
    public CompletableFuture<String> ping() {
        return executeCommand(commandObjects.ping());
    }

    @Override
    public CompletableFuture<String> ping(String message) {
        return executeCommand(commandObjects.ping(message));
    }

    @Override
    public CompletableFuture<String> echo(String string) {
        return executeCommand(commandObjects.echo(string));
    }

    @Override
    public CompletableFuture<byte[]> echo(byte[] arg) {
        return executeCommand(commandObjects.echo(arg));
    }

    @Override
    public CompletableFuture<String> flushDB() {
        return executeCommand(commandObjects.flushDB());
    }

    @Override
    public CompletableFuture<String> flushDB(FlushMode flushMode) {
        return executeCommand(commandObjects.flushDB(flushMode));
    }

    @Override
    public CompletableFuture<String> flushAll() {
        return executeCommand(commandObjects.flushAll());
    }

    @Override
    public CompletableFuture<String> flushAll(FlushMode flushMode) {
        return executeCommand(commandObjects.flushAll(flushMode));
    }

    @Override
    public CompletableFuture<String> auth(String password) {
        return executeCommand(commandObjects.auth(password));
    }

    @Override
    public CompletableFuture<String> auth(String user, String password) {
        return executeCommand(commandObjects.auth(user, password));
    }

    @Override
    public CompletableFuture<String> save() {
        return executeCommand(commandObjects.save());
    }

    @Override
    public CompletableFuture<String> bgsave() {
        return executeCommand(commandObjects.bgsave());
    }

    @Override
    public CompletableFuture<String> bgsave(SaveMode scheduleFlag) {
        return executeCommand(commandObjects.bgsave(scheduleFlag));
    }

    @Override
    public CompletableFuture<Long> lastsave() {
        return executeCommand(commandObjects.lastsave());
    }

    @Override
    public CompletableFuture<String> shutdown() {
        return executeCommand(commandObjects.shutdown());
    }

    @Override
    public CompletableFuture<String> shutdown(ShutdownParams params) {
        return executeCommand(commandObjects.shutdown(params));
    }

    @Override
    public CompletableFuture<String> info() {
        return executeCommand(commandObjects.info());
    }

    @Override
    public CompletableFuture<String> info(String section) {
        return executeCommand(commandObjects.info(section));
    }

    @Override
    public CompletableFuture<String> slaveof(String host, int port) {
        return executeCommand(commandObjects.slaveof(host, port));
    }

    @Override
    public CompletableFuture<String> replicaof(String host, int port) {
        return executeCommand(commandObjects.replicaof(host, port));
    }

    @Override
    public CompletableFuture<List<Object>> role() {
        return executeCommand(commandObjects.role());
    }

    @Override
    public CompletableFuture<String> debugObject(String key) {
        return executeCommand(commandObjects.debugObject(key));
    }

    @Override
    public CompletableFuture<String> debugSegfault() {
        return executeCommand(commandObjects.debugSegfault());
    }

    @Override
    public CompletableFuture<String> memoryDoctor() {
        return executeCommand(commandObjects.memoryDoctor());
    }

    @Override
    public CompletableFuture<String> memoryMallocStats() {
        return executeCommand(commandObjects.memoryMallocStats());
    }

    @Override
    public CompletableFuture<String> memoryPurge() {
        return executeCommand(commandObjects.memoryPurge());
    }

    @Override
    public CompletableFuture<Map<String, Object>> memoryStats() {
        return executeCommand(commandObjects.memoryStats());
    }

    @Override
    public CompletableFuture<Map<String, LatencyLatestInfo>> latencyLatest() {
        return executeCommand(commandObjects.latencyLatest());
    }

    @Override
    public CompletableFuture<List<LatencyHistoryInfo>> latencyHistory(LatencyEvent event) {
        return executeCommand(commandObjects.latencyHistory(event));
    }

    @Override
    public CompletableFuture<Long> latencyReset(LatencyEvent... events) {
        return executeCommand(commandObjects.latencyReset(events));
    }

    @Override
    public CompletableFuture<String> latencyGraph(LatencyEvent event) {
        return executeCommand(commandObjects.latencyGraph(event));
    }

    @Override
    public CompletableFuture<String> latencyDoctor() {
        return executeCommand(commandObjects.latencyDoctor());
    }

    @Override
    public CompletableFuture<String> lolwut() {
        throw new UnsupportedOperationException();
    }

    @Override
    public CompletableFuture<String> lolwut(LolwutParams params) {
        throw new UnsupportedOperationException();
    }

    @Override
    public CompletableFuture<Set<String>> keys(String pattern) {
        return executeCommand(commandObjects.keys(pattern));
    }

    @Override
    public CompletableFuture<ScanResult<String>> scan(String cursor) {
        return executeCommand(commandObjects.scan(cursor));
    }

    @Override
    public CompletableFuture<ScanResult<String>> scan(String cursor, ScanParams params) {
        return executeCommand(commandObjects.scan(cursor, params));
    }

    @Override
    public CompletableFuture<ScanResult<String>> scan(String cursor, ScanParams params, String type) {
        return executeCommand(commandObjects.scan(cursor, params, type));
    }

    @Override
    public CompletableFuture<String> randomKey() {
        return executeCommand(commandObjects.randomKey());
    }
}
