package io.valkey.async.commands;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import io.valkey.args.ClusterFailoverOption;
import io.valkey.args.ClusterResetType;
import io.valkey.resps.ClusterShardInfo;

/**
 * This interface provides async commands for Redis cluster operations.
 */
public interface AsyncClusterCommands {

  /**
   * Async version of <b><a href="http://redis.io/commands/asking">ASKING Command</a></b>
   * @return CompletableFuture that completes with OK on success
   */
  CompletableFuture<String> asking();

  /**
   * Async version of <b><a href="http://redis.io/commands/readonly">READONLY Command</a></b>
   * @return CompletableFuture that completes with OK on success
   */
  CompletableFuture<String> readonly();

  /**
   * Async version of <b><a href="http://redis.io/commands/readwrite">READWRITE Command</a></b>
   * @return CompletableFuture that completes with OK on success
   */
  CompletableFuture<String> readwrite();

  /**
   * Async version of <b><a href="http://redis.io/commands/cluster-nodes">CLUSTER NODES Command</a></b>
   * @return CompletableFuture that completes with cluster nodes information
   */
  CompletableFuture<String> clusterNodes();

  /**
   * Async version of <b><a href="http://redis.io/commands/cluster-meet">CLUSTER MEET Command</a></b>
   * @param ip The IP address of the node
   * @param port The port number of the node
   * @return CompletableFuture that completes with OK on success
   */
  CompletableFuture<String> clusterMeet(String ip, int port);

  /**
   * Async version of <b><a href="http://redis.io/commands/cluster-addslots">CLUSTER ADDSLOTS Command</a></b>
   * @param slots The slots to add
   * @return CompletableFuture that completes with OK on success
   */
  CompletableFuture<String> clusterAddSlots(int... slots);

  /**
   * Async version of <b><a href="http://redis.io/commands/cluster-delslots">CLUSTER DELSLOTS Command</a></b>
   * @param slots The slots to delete
   * @return CompletableFuture that completes with OK on success
   */
  CompletableFuture<String> clusterDelSlots(int... slots);

  /**
   * Async version of <b><a href="http://redis.io/commands/cluster-info">CLUSTER INFO Command</a></b>
   * @return CompletableFuture that completes with cluster information
   */
  CompletableFuture<String> clusterInfo();

  /**
   * Async version of <b><a href="http://redis.io/commands/cluster-getkeysinslot">CLUSTER GETKEYSINSLOT Command</a></b>
   * @param slot The slot number
   * @param count The maximum number of keys to return
   * @return CompletableFuture that completes with list of keys in the specified slot
   */
  CompletableFuture<List<String>> clusterGetKeysInSlot(int slot, int count);

  /**
   * Async version of <b><a href="http://redis.io/commands/cluster-getkeysinslot">CLUSTER GETKEYSINSLOT Command</a></b>
   * @param slot The slot number
   * @param count The maximum number of keys to return
   * @return CompletableFuture that completes with list of keys in binary format
   */
  CompletableFuture<List<byte[]>> clusterGetKeysInSlotBinary(int slot, int count);

  /**
   * Async version of <b><a href="http://redis.io/commands/cluster-setslot">CLUSTER SETSLOT NODE Command</a></b>
   * @param slot The slot number
   * @param nodeId The ID of the node
   * @return CompletableFuture that completes with OK on success
   */
  CompletableFuture<String> clusterSetSlotNode(int slot, String nodeId);

  /**
   * Async version of <b><a href="http://redis.io/commands/cluster-setslot">CLUSTER SETSLOT MIGRATING Command</a></b>
   * @param slot The slot number
   * @param nodeId The ID of the destination node
   * @return CompletableFuture that completes with OK on success
   */
  CompletableFuture<String> clusterSetSlotMigrating(int slot, String nodeId);

  /**
   * Async version of <b><a href="http://redis.io/commands/cluster-setslot">CLUSTER SETSLOT IMPORTING Command</a></b>
   * @param slot The slot number
   * @param nodeId The ID of the source node
   * @return CompletableFuture that completes with OK on success
   */
  CompletableFuture<String> clusterSetSlotImporting(int slot, String nodeId);

  /**
   * Async version of <b><a href="http://redis.io/commands/cluster-setslot">CLUSTER SETSLOT STABLE Command</a></b>
   * @param slot The slot number
   * @return CompletableFuture that completes with OK on success
   */
  CompletableFuture<String> clusterSetSlotStable(int slot);

  /**
   * Async version of <b><a href="http://redis.io/commands/cluster-forget">CLUSTER FORGET Command</a></b>
   * @param nodeId The ID of the node to forget
   * @return CompletableFuture that completes with OK on success
   */
  CompletableFuture<String> clusterForget(String nodeId);

  /**
   * Async version of <b><a href="http://redis.io/commands/cluster-flushslots">CLUSTER FLUSHSLOTS Command</a></b>
   * @return CompletableFuture that completes with OK on success
   */
  CompletableFuture<String> clusterFlushSlots();

  /**
   * Async version of <b><a href="http://redis.io/commands/cluster-keyslot">CLUSTER KEYSLOT Command</a></b>
   * @param key The key to get the slot for
   * @return CompletableFuture that completes with the slot number
   */
  CompletableFuture<Long> clusterKeySlot(String key);

  /**
   * Async version of <b><a href="http://redis.io/commands/cluster-count-failure-reports">CLUSTER COUNT-FAILURE-REPORTS Command</a></b>
   * @param nodeId The ID of the node
   * @return CompletableFuture that completes with the number of failure reports
   */
  CompletableFuture<Long> clusterCountFailureReports(String nodeId);

  /**
   * Async version of <b><a href="http://redis.io/commands/cluster-countkeysinslot">CLUSTER COUNTKEYSINSLOT Command</a></b>
   * @param slot The slot number
   * @return CompletableFuture that completes with the number of keys in the slot
   */
  CompletableFuture<Long> clusterCountKeysInSlot(int slot);

  /**
   * Async version of <b><a href="http://redis.io/commands/cluster-saveconfig">CLUSTER SAVECONFIG Command</a></b>
   * @return CompletableFuture that completes with OK on success
   */
  CompletableFuture<String> clusterSaveConfig();

  /**
   * Async version of <b><a href="http://redis.io/commands/cluster-set-config-epoch">CLUSTER SET-CONFIG-EPOCH Command</a></b>
   * Set a specific config epoch in a fresh node. It only works when the nodes' table
   * of the node is empty or when the node current config epoch is zero.
   * @param configEpoch The config epoch to set
   * @return CompletableFuture that completes with OK on success
   */
  CompletableFuture<String> clusterSetConfigEpoch(long configEpoch);

  /**
   * Async version of <b><a href="http://redis.io/commands/cluster-bumpepoch">CLUSTER BUMPEPOCH Command</a></b>
   * Advance the cluster config epoch.
   * @return CompletableFuture that completes with BUMPED if the epoch was incremented, or STILL if the node already has the
   * greatest config epoch in the cluster
   */
  CompletableFuture<String> clusterBumpEpoch();

  /**
   * Async version of <b><a href="http://redis.io/commands/cluster-replicate">CLUSTER REPLICATE Command</a></b>
   * @param nodeId The ID of the master node to replicate
   * @return CompletableFuture that completes with OK on success
   */
  CompletableFuture<String> clusterReplicate(String nodeId);

  /**
   * Async version of CLUSTER SLAVES Command (deprecated since Redis 5)
   * @deprecated Use {@link AsyncClusterCommands#clusterReplicas(String)}
   */
  @Deprecated
  CompletableFuture<List<String>> clusterSlaves(String nodeId);

  /**
   * Async version of <b><a href="http://redis.io/commands/cluster-replicas">CLUSTER REPLICAS Command</a></b>
   * @param nodeId The ID of the master node
   * @return CompletableFuture that completes with list of replica nodes
   */
  CompletableFuture<List<String>> clusterReplicas(String nodeId);

  /**
   * Async version of <b><a href="http://redis.io/commands/cluster-failover">CLUSTER FAILOVER Command</a></b>
   * @return CompletableFuture that completes with OK on success
   */
  CompletableFuture<String> clusterFailover();

  /**
   * Async version of <b><a href="http://redis.io/commands/cluster-failover">CLUSTER FAILOVER Command</a></b>
   * @param failoverOption The failover option
   * @return CompletableFuture that completes with OK on success
   */
  CompletableFuture<String> clusterFailover(ClusterFailoverOption failoverOption);

  /**
   * Async version of CLUSTER SLOTS Command (deprecated since Redis 7)
   * @deprecated Use {@link AsyncClusterCommands#clusterShards()}
   */
  @Deprecated
  CompletableFuture<List<Object>> clusterSlots();

  /**
   * Async version of <b><a href="http://redis.io/commands/cluster-shards">CLUSTER SHARDS Command</a></b>
   * Returns details about the shards of the cluster.
   * This command replaces the {@code CLUSTER SLOTS} command from Redis 7,
   * by providing a more efficient and extensible representation of the cluster.
   *
   * @return CompletableFuture that completes with a list of shards, with each shard containing two objects, 'slots' and 'nodes'
   */
  CompletableFuture<List<ClusterShardInfo>> clusterShards();

  /**
   * Async version of <b><a href="http://redis.io/commands/cluster-reset">CLUSTER RESET Command</a></b>
   * @return CompletableFuture that completes with OK on success
   */
  CompletableFuture<String> clusterReset();

  /**
   * Async version of <b><a href="http://redis.io/commands/cluster-reset">CLUSTER RESET Command</a></b>
   * @param resetType The type of reset (can be null for default behavior)
   * @return CompletableFuture that completes with OK on success
   */
  CompletableFuture<String> clusterReset(ClusterResetType resetType);

  /**
   * Async version of <b><a href="http://redis.io/commands/cluster-myid">CLUSTER MYID Command</a></b>
   * @return CompletableFuture that completes with the ID of the current node
   */
  CompletableFuture<String> clusterMyId();

  /**
   * Async version of <b><a href="http://redis.io/commands/cluster-myshardid">CLUSTER MYSHARDID Command</a></b>
   * @return CompletableFuture that completes with the shard ID of the current node
   */
  CompletableFuture<String> clusterMyShardId();

  /**
   * Async version of <b><a href="http://redis.io/commands/cluster-links">CLUSTER LINKS Command</a></b>
   * Returns the information of all peer links as an array, where each array element is a map that contains
   * attributes and their values for an individual link.
   *
   * @return CompletableFuture that completes with the information of all peer links as an array
   */
  CompletableFuture<List<Map<String, Object>>> clusterLinks();

  /**
   * Async version of <b><a href="http://redis.io/commands/cluster-addslotsrange">CLUSTER ADDSLOTSRANGE Command</a></b>
   * Takes a list of slot ranges (specified by start and end slots) to assign to the node
   *
   * @param ranges slots range
   * @return CompletableFuture that completes with OK if the command was successful
   */
  CompletableFuture<String> clusterAddSlotsRange(int... ranges);

  /**
   * Async version of <b><a href="http://redis.io/commands/cluster-delslotsrange">CLUSTER DELSLOTSRANGE Command</a></b>
   * Takes a list of slot ranges (specified by start and end slots) to remove from the node
   *
   * @param ranges slots range
   * @return CompletableFuture that completes with OK if the command was successful
   */
  CompletableFuture<String> clusterDelSlotsRange(int... ranges);
} 