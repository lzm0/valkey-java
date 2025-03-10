package io.valkey.async.commands;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import io.valkey.params.ScanParams;
import io.valkey.resps.ScanResult;

/**
 * This interface provides async commands for Redis hash operations with binary data.
 */
public interface AsyncHashBinaryCommands {

  CompletableFuture<Long> hset(byte[] key, byte[] field, byte[] value);

  CompletableFuture<Long> hset(byte[] key, Map<byte[], byte[]> hash);

  CompletableFuture<byte[]> hget(byte[] key, byte[] field);

  CompletableFuture<Long> hsetnx(byte[] key, byte[] field, byte[] value);

  CompletableFuture<String> hmset(byte[] key, Map<byte[], byte[]> hash);

  CompletableFuture<List<byte[]>> hmget(byte[] key, byte[]... fields);

  CompletableFuture<Long> hincrBy(byte[] key, byte[] field, long value);

  CompletableFuture<Double> hincrByFloat(byte[] key, byte[] field, double value);

  CompletableFuture<Boolean> hexists(byte[] key, byte[] field);

  CompletableFuture<Long> hdel(byte[] key, byte[]... fields);

  CompletableFuture<Long> hlen(byte[] key);

  CompletableFuture<Set<byte[]>> hkeys(byte[] key);

  CompletableFuture<List<byte[]>> hvals(byte[] key);

  CompletableFuture<Map<byte[], byte[]>> hgetAll(byte[] key);

  CompletableFuture<byte[]> hrandfield(byte[] key);

  CompletableFuture<List<byte[]>> hrandfield(byte[] key, long count);

  CompletableFuture<List<Map.Entry<byte[], byte[]>>> hrandfieldWithValues(byte[] key, long count);

  default CompletableFuture<ScanResult<Entry<byte[], byte[]>>> hscan(byte[] key, byte[] cursor) {
    return hscan(key, cursor, new ScanParams());
  }

  CompletableFuture<ScanResult<Map.Entry<byte[], byte[]>>> hscan(byte[] key, byte[] cursor, ScanParams params);

  default CompletableFuture<ScanResult<byte[]>> hscanNoValues(byte[] key, byte[] cursor) {
    return hscanNoValues(key, cursor, new ScanParams());
  }

  CompletableFuture<ScanResult<byte[]>> hscanNoValues(byte[] key, byte[] cursor, ScanParams params);

  CompletableFuture<Long> hstrlen(byte[] key, byte[] field);
} 