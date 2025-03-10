package io.valkey.async.commands;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * This interface provides async commands for Redis sentinel operations.
 */
public interface AsyncSentinelCommands {

  /**
   * Async version of SENTINEL MYID command.
   * @return CompletableFuture that completes with sentinel ID
   */
  CompletableFuture<String> sentinelMyId();

  /**
   * Async version of SENTINEL MASTERS command.
   * @return CompletableFuture that completes with list of monitored masters
   */
  CompletableFuture<List<Map<String, String>>> sentinelMasters();

  /**
   * Async version of SENTINEL MASTER command.
   * @param masterName master name
   * @return CompletableFuture that completes with master info
   */
  CompletableFuture<Map<String, String>> sentinelMaster(String masterName);

  /**
   * Async version of SENTINEL SENTINELS command.
   * @param masterName master name
   * @return CompletableFuture that completes with list of sentinel info
   */
  CompletableFuture<List<Map<String, String>>> sentinelSentinels(String masterName);

  /**
   * Async version of SENTINEL GET-MASTER-ADDR-BY-NAME command.
   * @param masterName master name
   * @return CompletableFuture that completes with master address
   */
  CompletableFuture<List<String>> sentinelGetMasterAddrByName(String masterName);

  /**
   * Async version of SENTINEL RESET command.
   * @param pattern pattern to match master names
   * @return CompletableFuture that completes with number of masters reset
   */
  CompletableFuture<Long> sentinelReset(String pattern);

  /**
   * Async version of SENTINEL REPLICAS command.
   * @param masterName master name
   * @return CompletableFuture that completes with list of replica info
   */
  CompletableFuture<List<Map<String, String>>> sentinelReplicas(String masterName);

  /**
   * Async version of SENTINEL FAILOVER command.
   * @param masterName master name
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> sentinelFailover(String masterName);

  /**
   * Async version of SENTINEL MONITOR command.
   * @param masterName master name
   * @param ip master host
   * @param port master port
   * @param quorum quorum count
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> sentinelMonitor(String masterName, String ip, int port, int quorum);

  /**
   * Async version of SENTINEL REMOVE command.
   * @param masterName master name
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> sentinelRemove(String masterName);

  /**
   * Async version of SENTINEL SET command.
   * @param masterName master name
   * @param parameterMap parameters to set
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> sentinelSet(String masterName, Map<String, String> parameterMap);
} 