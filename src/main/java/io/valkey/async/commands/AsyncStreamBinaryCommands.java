package io.valkey.async.commands;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import io.valkey.params.XAddParams;
import io.valkey.params.XAutoClaimParams;
import io.valkey.params.XClaimParams;
import io.valkey.params.XPendingParams;
import io.valkey.params.XReadGroupParams;
import io.valkey.params.XReadParams;
import io.valkey.params.XTrimParams;

/**
 * This interface provides async commands for Redis Stream operations with binary data.
 */
public interface AsyncStreamBinaryCommands {

  /**
   * Binary version of XADD command.
   * @param key the key
   * @param params the parameters
   * @param hash the entry fields and values
   * @return CompletableFuture that completes with the ID of the added entry
   */
  CompletableFuture<byte[]> xadd(byte[] key, XAddParams params, Map<byte[], byte[]> hash);

  /**
   * Binary version of XLEN command.
   * @param key the key
   * @return CompletableFuture that completes with the length of the stream
   */
  CompletableFuture<Long> xlen(byte[] key);

  /**
   * Binary version of XRANGE command.
   * @param key the key
   * @param start minimum ID
   * @param end maximum ID
   * @return CompletableFuture that completes with list of entries
   */
  CompletableFuture<List<Object>> xrange(byte[] key, byte[] start, byte[] end);

  /**
   * Binary version of XRANGE command with count limit.
   * @param key the key
   * @param start minimum ID
   * @param end maximum ID
   * @param count maximum number of entries to return
   * @return CompletableFuture that completes with list of entries
   */
  CompletableFuture<List<Object>> xrange(byte[] key, byte[] start, byte[] end, int count);

  /**
   * Binary version of XREVRANGE command.
   * @param key the key
   * @param end maximum ID
   * @param start minimum ID
   * @return CompletableFuture that completes with list of entries
   */
  CompletableFuture<List<Object>> xrevrange(byte[] key, byte[] end, byte[] start);

  /**
   * Binary version of XREVRANGE command with count limit.
   * @param key the key
   * @param end maximum ID
   * @param start minimum ID
   * @param count maximum number of entries to return
   * @return CompletableFuture that completes with list of entries
   */
  CompletableFuture<List<Object>> xrevrange(byte[] key, byte[] end, byte[] start, int count);

  /**
   * Binary version of XACK command.
   * @param key the key
   * @param group the consumer group
   * @param ids the message IDs to acknowledge
   * @return CompletableFuture that completes with number of messages acknowledged
   */
  CompletableFuture<Long> xack(byte[] key, byte[] group, byte[]... ids);

  /**
   * Binary version of XGROUP CREATE command.
   * @param key the key
   * @param groupName the group name
   * @param id the ID to start at
   * @param makeStream whether to create the stream if it doesn't exist
   * @return CompletableFuture that completes with "OK"
   */
  CompletableFuture<String> xgroupCreate(byte[] key, byte[] groupName, byte[] id, boolean makeStream);

  /**
   * Binary version of XGROUP SETID command.
   * @param key the key
   * @param groupName the group name
   * @param id the ID to set
   * @return CompletableFuture that completes with "OK"
   */
  CompletableFuture<String> xgroupSetID(byte[] key, byte[] groupName, byte[] id);

  /**
   * Binary version of XGROUP DESTROY command.
   * @param key the key
   * @param groupName the group name
   * @return CompletableFuture that completes with 1 if destroyed, 0 if not found
   */
  CompletableFuture<Long> xgroupDestroy(byte[] key, byte[] groupName);

  /**
   * Binary version of XGROUP CREATECONSUMER command.
   * @param key the key
   * @param groupName the group name
   * @param consumerName the consumer name
   * @return CompletableFuture that completes with true if created, false if already exists
   */
  CompletableFuture<Boolean> xgroupCreateConsumer(byte[] key, byte[] groupName, byte[] consumerName);

  /**
   * Binary version of XGROUP DELCONSUMER command.
   * @param key the key
   * @param groupName the group name
   * @param consumerName the consumer name
   * @return CompletableFuture that completes with number of pending messages owned by consumer
   */
  CompletableFuture<Long> xgroupDelConsumer(byte[] key, byte[] groupName, byte[] consumerName);

  /**
   * Binary version of XDEL command.
   * @param key the key
   * @param ids the entry IDs to delete
   * @return CompletableFuture that completes with number of entries deleted
   */
  CompletableFuture<Long> xdel(byte[] key, byte[]... ids);

  /**
   * Binary version of XTRIM command.
   * @param key the key
   * @param maxLen the maximum length
   * @param approximateLength whether to trim approximately to maxLen
   * @return CompletableFuture that completes with number of entries deleted
   */
  CompletableFuture<Long> xtrim(byte[] key, long maxLen, boolean approximateLength);

  /**
   * Binary version of XTRIM command with parameters.
   * @param key the key
   * @param params the trim parameters
   * @return CompletableFuture that completes with number of entries deleted
   */
  CompletableFuture<Long> xtrim(byte[] key, XTrimParams params);

  /**
   * Binary version of XPENDING command.
   * @param key the key
   * @param groupName the group name
   * @return CompletableFuture that completes with pending information
   */
  CompletableFuture<Object> xpending(byte[] key, byte[] groupName);

  /**
   * Binary version of XPENDING command with parameters.
   * @param key the key
   * @param groupName the group name
   * @param params the parameters
   * @return CompletableFuture that completes with list of pending entries
   */
  CompletableFuture<List<Object>> xpending(byte[] key, byte[] groupName, XPendingParams params);

  /**
   * Binary version of XCLAIM command.
   * @param key the key
   * @param group the group name
   * @param consumerName the consumer name
   * @param minIdleTime minimum idle time in milliseconds
   * @param params claim parameters
   * @param ids message IDs to claim
   * @return CompletableFuture that completes with claimed entries
   */
  CompletableFuture<List<byte[]>> xclaim(byte[] key, byte[] group, byte[] consumerName,
      long minIdleTime, XClaimParams params, byte[]... ids);

  /**
   * Binary version of XCLAIM command with JUSTID option.
   * @param key the key
   * @param group the group name
   * @param consumerName the consumer name
   * @param minIdleTime minimum idle time in milliseconds
   * @param params claim parameters
   * @param ids message IDs to claim
   * @return CompletableFuture that completes with claimed message IDs
   */
  CompletableFuture<List<byte[]>> xclaimJustId(byte[] key, byte[] group, byte[] consumerName,
      long minIdleTime, XClaimParams params, byte[]... ids);

  /**
   * Binary version of XAUTOCLAIM command.
   * @param key the key
   * @param groupName the group name
   * @param consumerName the consumer name
   * @param minIdleTime minimum idle time in milliseconds
   * @param start start ID for scanning
   * @param params claim parameters
   * @return CompletableFuture that completes with claimed entries
   */
  CompletableFuture<List<Object>> xautoclaim(byte[] key, byte[] groupName, byte[] consumerName,
      long minIdleTime, byte[] start, XAutoClaimParams params);

  /**
   * Binary version of XAUTOCLAIM command with JUSTID option.
   * @param key the key
   * @param groupName the group name
   * @param consumerName the consumer name
   * @param minIdleTime minimum idle time in milliseconds
   * @param start start ID for scanning
   * @param params claim parameters
   * @return CompletableFuture that completes with claimed message IDs
   */
  CompletableFuture<List<Object>> xautoclaimJustId(byte[] key, byte[] groupName, byte[] consumerName,
      long minIdleTime, byte[] start, XAutoClaimParams params);

  /**
   * Binary version of XINFO STREAM command.
   * @param key the key
   * @return CompletableFuture that completes with stream information
   */
  CompletableFuture<Object> xinfoStream(byte[] key);

  /**
   * Binary version of XINFO STREAM FULL command.
   * @param key the key
   * @return CompletableFuture that completes with full stream information
   */
  CompletableFuture<Object> xinfoStreamFull(byte[] key);

  /**
   * Binary version of XINFO STREAM FULL command with count limit.
   * @param key the key
   * @param count maximum number of entries to return
   * @return CompletableFuture that completes with full stream information
   */
  CompletableFuture<Object> xinfoStreamFull(byte[] key, int count);

  /**
   * Binary version of XINFO GROUPS command.
   * @param key the key
   * @return CompletableFuture that completes with list of consumer group information
   */
  CompletableFuture<List<Object>> xinfoGroups(byte[] key);

  /**
   * Binary version of XINFO CONSUMERS command.
   * @param key the key
   * @param group the group name
   * @return CompletableFuture that completes with list of consumer information
   */
  CompletableFuture<List<Object>> xinfoConsumers(byte[] key, byte[] group);

  /**
   * Binary version of XREAD command.
   * @param xReadParams read parameters
   * @param streams map entries of stream keys to IDs
   * @return CompletableFuture that completes with entries from the streams
   */
  CompletableFuture<List<Object>> xread(XReadParams xReadParams, Map.Entry<byte[], byte[]>... streams);

  /**
   * Binary version of XREADGROUP command.
   * @param groupName the group name
   * @param consumer the consumer name
   * @param xReadGroupParams read parameters
   * @param streams map entries of stream keys to IDs
   * @return CompletableFuture that completes with entries from the streams
   */
  CompletableFuture<List<Object>> xreadGroup(byte[] groupName, byte[] consumer,
      XReadGroupParams xReadGroupParams, Map.Entry<byte[], byte[]>... streams);
} 