package io.valkey.async.commands;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import io.valkey.StreamEntryID;
import io.valkey.params.XAddParams;
import io.valkey.params.XAutoClaimParams;
import io.valkey.params.XClaimParams;
import io.valkey.params.XPendingParams;
import io.valkey.params.XReadGroupParams;
import io.valkey.params.XReadParams;
import io.valkey.params.XTrimParams;
import io.valkey.resps.StreamConsumerInfo;
import io.valkey.resps.StreamConsumersInfo;
import io.valkey.resps.StreamEntry;
import io.valkey.resps.StreamFullInfo;
import io.valkey.resps.StreamGroupInfo;
import io.valkey.resps.StreamInfo;
import io.valkey.resps.StreamPendingEntry;
import io.valkey.resps.StreamPendingSummary;

/**
 * This interface provides async commands for Redis Stream operations.
 */
public interface AsyncStreamCommands {

  /**
   * Async version of <b><a href="http://redis.io/commands/xadd">XADD Command</a></b>
   * Add an entry to a stream.
   * @param key the key
   * @param id the entry ID or null for auto-generation
   * @param hash the entry fields and values
   * @return CompletableFuture that completes with the ID of the added entry
   */
  CompletableFuture<StreamEntryID> xadd(String key, StreamEntryID id, Map<String, String> hash);

  /**
   * Async version of <b><a href="http://redis.io/commands/xadd">XADD Command</a></b>
   * Add an entry to a stream with additional parameters.
   * @param key the key
   * @param params the parameters
   * @param hash the entry fields and values
   * @return CompletableFuture that completes with the ID of the added entry
   */
  CompletableFuture<StreamEntryID> xadd(String key, XAddParams params, Map<String, String> hash);

  /**
   * Async version of <b><a href="http://redis.io/commands/xlen">XLEN Command</a></b>
   * Get the length of a stream.
   * @param key the key
   * @return CompletableFuture that completes with the length of the stream
   */
  CompletableFuture<Long> xlen(String key);

  /**
   * Async version of <b><a href="http://redis.io/commands/xrange">XRANGE Command</a></b>
   * Get entries from a stream within a range of IDs.
   * @param key the key
   * @param start minimum ID (or null for earliest)
   * @param end maximum ID (or null for latest)
   * @return CompletableFuture that completes with list of entries
   */
  CompletableFuture<List<StreamEntry>> xrange(String key, StreamEntryID start, StreamEntryID end);

  /**
   * Async version of <b><a href="http://redis.io/commands/xrange">XRANGE Command</a></b>
   * Get entries from a stream within a range of IDs with count limit.
   * @param key the key
   * @param start minimum ID (or null for earliest)
   * @param end maximum ID (or null for latest)
   * @param count maximum number of entries to return
   * @return CompletableFuture that completes with list of entries
   */
  CompletableFuture<List<StreamEntry>> xrange(String key, StreamEntryID start, StreamEntryID end, int count);

  /**
   * Async version of <b><a href="http://redis.io/commands/xrevrange">XREVRANGE Command</a></b>
   * Get entries from a stream within a range of IDs in reverse order.
   * @param key the key
   * @param end maximum ID (or null for latest)
   * @param start minimum ID (or null for earliest)
   * @return CompletableFuture that completes with list of entries
   */
  CompletableFuture<List<StreamEntry>> xrevrange(String key, StreamEntryID end, StreamEntryID start);

  /**
   * Async version of <b><a href="http://redis.io/commands/xrevrange">XREVRANGE Command</a></b>
   * Get entries from a stream within a range of IDs in reverse order with count limit.
   * @param key the key
   * @param end maximum ID (or null for latest)
   * @param start minimum ID (or null for earliest)
   * @param count maximum number of entries to return
   * @return CompletableFuture that completes with list of entries
   */
  CompletableFuture<List<StreamEntry>> xrevrange(String key, StreamEntryID end, StreamEntryID start, int count);

  /**
   * Async version of XRANGE command with string IDs.
   * @param key the key
   * @param start minimum ID
   * @param end maximum ID
   * @return CompletableFuture that completes with list of entries
   */
  CompletableFuture<List<StreamEntry>> xrange(String key, String start, String end);

  /**
   * Async version of XRANGE command with string IDs and count limit.
   * @param key the key
   * @param start minimum ID
   * @param end maximum ID
   * @param count maximum number of entries to return
   * @return CompletableFuture that completes with list of entries
   */
  CompletableFuture<List<StreamEntry>> xrange(String key, String start, String end, int count);

  /**
   * Async version of XREVRANGE command with string IDs.
   * @param key the key
   * @param end maximum ID
   * @param start minimum ID
   * @return CompletableFuture that completes with list of entries
   */
  CompletableFuture<List<StreamEntry>> xrevrange(String key, String end, String start);

  /**
   * Async version of XREVRANGE command with string IDs and count limit.
   * @param key the key
   * @param end maximum ID
   * @param start minimum ID
   * @param count maximum number of entries to return
   * @return CompletableFuture that completes with list of entries
   */
  CompletableFuture<List<StreamEntry>> xrevrange(String key, String end, String start, int count);

  /**
   * Async version of <b><a href="http://redis.io/commands/xack">XACK Command</a></b>
   * Acknowledge one or more pending messages.
   * @param key the key
   * @param group the consumer group
   * @param ids the message IDs to acknowledge
   * @return CompletableFuture that completes with number of messages acknowledged
   */
  CompletableFuture<Long> xack(String key, String group, StreamEntryID... ids);

  /**
   * Async version of <b><a href="http://redis.io/commands/xgroup-create">XGROUP CREATE Command</a></b>
   * Create a consumer group.
   * @param key the key
   * @param groupName the group name
   * @param id the ID to start at
   * @param makeStream whether to create the stream if it doesn't exist
   * @return CompletableFuture that completes with "OK"
   */
  CompletableFuture<String> xgroupCreate(String key, String groupName, StreamEntryID id, boolean makeStream);

  /**
   * Async version of <b><a href="http://redis.io/commands/xgroup-setid">XGROUP SETID Command</a></b>
   * Set a consumer group's last delivered ID.
   * @param key the key
   * @param groupName the group name
   * @param id the ID to set
   * @return CompletableFuture that completes with "OK"
   */
  CompletableFuture<String> xgroupSetID(String key, String groupName, StreamEntryID id);

  /**
   * Async version of <b><a href="http://redis.io/commands/xgroup-destroy">XGROUP DESTROY Command</a></b>
   * Destroy a consumer group.
   * @param key the key
   * @param groupName the group name
   * @return CompletableFuture that completes with 1 if destroyed, 0 if not found
   */
  CompletableFuture<Long> xgroupDestroy(String key, String groupName);

  /**
   * Async version of <b><a href="http://redis.io/commands/xgroup-createconsumer">XGROUP CREATECONSUMER Command</a></b>
   * Create a consumer in a consumer group.
   * @param key the key
   * @param groupName the group name
   * @param consumerName the consumer name
   * @return CompletableFuture that completes with true if created, false if already exists
   */
  CompletableFuture<Boolean> xgroupCreateConsumer(String key, String groupName, String consumerName);

  /**
   * Async version of <b><a href="http://redis.io/commands/xgroup-delconsumer">XGROUP DELCONSUMER Command</a></b>
   * Delete a consumer from a consumer group.
   * @param key the key
   * @param groupName the group name
   * @param consumerName the consumer name
   * @return CompletableFuture that completes with number of pending messages owned by consumer
   */
  CompletableFuture<Long> xgroupDelConsumer(String key, String groupName, String consumerName);

  /**
   * Async version of <b><a href="http://redis.io/commands/xdel">XDEL Command</a></b>
   * Delete entries from a stream.
   * @param key the key
   * @param ids the entry IDs to delete
   * @return CompletableFuture that completes with number of entries deleted
   */
  CompletableFuture<Long> xdel(String key, StreamEntryID... ids);

  /**
   * Async version of <b><a href="http://redis.io/commands/xtrim">XTRIM Command</a></b>
   * Trim a stream to a maximum length.
   * @param key the key
   * @param maxLen the maximum length
   * @param approximate whether to trim approximately to maxLen
   * @return CompletableFuture that completes with number of entries deleted
   */
  CompletableFuture<Long> xtrim(String key, long maxLen, boolean approximate);

  /**
   * Async version of <b><a href="http://redis.io/commands/xtrim">XTRIM Command</a></b>
   * Trim a stream with parameters.
   * @param key the key
   * @param params the trim parameters
   * @return CompletableFuture that completes with number of entries deleted
   */
  CompletableFuture<Long> xtrim(String key, XTrimParams params);

  /**
   * Async version of <b><a href="http://redis.io/commands/xpending">XPENDING Command</a></b>
   * Get information about pending entries in a consumer group.
   * @param key the key
   * @param groupName the group name
   * @return CompletableFuture that completes with pending summary
   */
  CompletableFuture<StreamPendingSummary> xpending(String key, String groupName);

  /**
   * Async version of <b><a href="http://redis.io/commands/xpending">XPENDING Command</a></b>
   * Get information about pending entries in a consumer group with parameters.
   * @param key the key
   * @param groupName the group name
   * @param params the parameters
   * @return CompletableFuture that completes with list of pending entries
   */
  CompletableFuture<List<StreamPendingEntry>> xpending(String key, String groupName, XPendingParams params);

  /**
   * Async version of <b><a href="http://redis.io/commands/xclaim">XCLAIM Command</a></b>
   * Claim pending messages in a consumer group.
   * @param key the key
   * @param group the group name
   * @param consumerName the consumer name
   * @param minIdleTime minimum idle time in milliseconds
   * @param params claim parameters
   * @param ids message IDs to claim
   * @return CompletableFuture that completes with claimed entries
   */
  CompletableFuture<List<StreamEntry>> xclaim(String key, String group, String consumerName, long minIdleTime,
      XClaimParams params, StreamEntryID... ids);

  /**
   * Async version of <b><a href="http://redis.io/commands/xclaim">XCLAIM Command</a></b>
   * Claim pending messages in a consumer group, returning just the IDs.
   * @param key the key
   * @param group the group name
   * @param consumerName the consumer name
   * @param minIdleTime minimum idle time in milliseconds
   * @param params claim parameters
   * @param ids message IDs to claim
   * @return CompletableFuture that completes with claimed message IDs
   */
  CompletableFuture<List<StreamEntryID>> xclaimJustId(String key, String group, String consumerName, long minIdleTime,
      XClaimParams params, StreamEntryID... ids);

  /**
   * Async version of <b><a href="http://redis.io/commands/xautoclaim">XAUTOCLAIM Command</a></b>
   * Automatically claim pending messages in a consumer group.
   * @param key the key
   * @param group the group name
   * @param consumerName the consumer name
   * @param minIdleTime minimum idle time in milliseconds
   * @param start start ID for scanning
   * @param params claim parameters
   * @return CompletableFuture that completes with claimed entries and next start ID
   */
  CompletableFuture<Map.Entry<StreamEntryID, List<StreamEntry>>> xautoclaim(String key, String group,
      String consumerName, long minIdleTime, StreamEntryID start, XAutoClaimParams params);

  /**
   * Async version of <b><a href="http://redis.io/commands/xautoclaim">XAUTOCLAIM Command</a></b>
   * Automatically claim pending messages in a consumer group, returning just the IDs.
   * @param key the key
   * @param group the group name
   * @param consumerName the consumer name
   * @param minIdleTime minimum idle time in milliseconds
   * @param start start ID for scanning
   * @param params claim parameters
   * @return CompletableFuture that completes with claimed message IDs and next start ID
   */
  CompletableFuture<Map.Entry<StreamEntryID, List<StreamEntryID>>> xautoclaimJustId(String key, String group,
      String consumerName, long minIdleTime, StreamEntryID start, XAutoClaimParams params);

  /**
   * Async version of <b><a href="http://redis.io/commands/xinfo-stream">XINFO STREAM Command</a></b>
   * Get information about a stream.
   * @param key the key
   * @return CompletableFuture that completes with stream information
   */
  CompletableFuture<StreamInfo> xinfoStream(String key);

  /**
   * Async version of <b><a href="http://redis.io/commands/xinfo-stream">XINFO STREAM FULL Command</a></b>
   * Get full information about a stream.
   * @param key the key
   * @return CompletableFuture that completes with full stream information
   */
  CompletableFuture<StreamFullInfo> xinfoStreamFull(String key);

  /**
   * Async version of <b><a href="http://redis.io/commands/xinfo-stream">XINFO STREAM FULL Command</a></b>
   * Get full information about a stream with count limit.
   * @param key the key
   * @param count maximum number of entries to return
   * @return CompletableFuture that completes with full stream information
   */
  CompletableFuture<StreamFullInfo> xinfoStreamFull(String key, int count);

  /**
   * Async version of <b><a href="http://redis.io/commands/xinfo-groups">XINFO GROUPS Command</a></b>
   * Get information about consumer groups of a stream.
   * @param key the key
   * @return CompletableFuture that completes with list of consumer group information
   */
  CompletableFuture<List<StreamGroupInfo>> xinfoGroups(String key);

  /**
   * Async version of <b><a href="http://redis.io/commands/xinfo-consumers">XINFO CONSUMERS Command</a></b>
   * Get information about consumers in a consumer group.
   * @param key the key
   * @param group the group name
   * @return CompletableFuture that completes with list of consumer information
   * @deprecated Use {@link #xinfoConsumers2(String, String)}
   */
  @Deprecated
  CompletableFuture<List<StreamConsumersInfo>> xinfoConsumers(String key, String group);

  /**
   * Async version of <b><a href="http://redis.io/commands/xinfo-consumers">XINFO CONSUMERS Command</a></b>
   * Get information about consumers in a consumer group.
   * @param key the key
   * @param group the group name
   * @return CompletableFuture that completes with list of consumer information
   */
  CompletableFuture<List<StreamConsumerInfo>> xinfoConsumers2(String key, String group);

  /**
   * Async version of <b><a href="http://redis.io/commands/xread">XREAD Command</a></b>
   * Read data from one or more streams.
   * @param xReadParams read parameters
   * @param streams map of stream keys to IDs
   * @return CompletableFuture that completes with entries from the streams
   */
  CompletableFuture<List<Map.Entry<String, List<StreamEntry>>>> xread(XReadParams xReadParams,
      Map<String, StreamEntryID> streams);

  /**
   * Async version of <b><a href="http://redis.io/commands/xread">XREAD Command</a></b>
   * Read data from one or more streams, returning as a map.
   * @param xReadParams read parameters
   * @param streams map of stream keys to IDs
   * @return CompletableFuture that completes with map of stream entries
   */
  CompletableFuture<Map<String, List<StreamEntry>>> xreadAsMap(XReadParams xReadParams,
      Map<String, StreamEntryID> streams);

  /**
   * Async version of <b><a href="http://redis.io/commands/xreadgroup">XREADGROUP Command</a></b>
   * Read data from one or more streams as part of a consumer group.
   * @param groupName the group name
   * @param consumer the consumer name
   * @param xReadGroupParams read parameters
   * @param streams map of stream keys to IDs
   * @return CompletableFuture that completes with entries from the streams
   */
  CompletableFuture<List<Map.Entry<String, List<StreamEntry>>>> xreadGroup(String groupName,
      String consumer, XReadGroupParams xReadGroupParams, Map<String, StreamEntryID> streams);

  /**
   * Async version of <b><a href="http://redis.io/commands/xreadgroup">XREADGROUP Command</a></b>
   * Read data from one or more streams as part of a consumer group, returning as a map.
   * @param groupName the group name
   * @param consumer the consumer name
   * @param xReadGroupParams read parameters
   * @param streams map of stream keys to IDs
   * @return CompletableFuture that completes with map of stream entries
   */
  CompletableFuture<Map<String, List<StreamEntry>>> xreadGroupAsMap(String groupName,
      String consumer, XReadGroupParams xReadGroupParams, Map<String, StreamEntryID> streams);
} 