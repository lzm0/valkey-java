package io.valkey.async.commands;

import io.valkey.args.FlushMode;
import io.valkey.args.LatencyEvent;
import io.valkey.args.SaveMode;
import io.valkey.params.LolwutParams;
import io.valkey.params.ShutdownParams;
import io.valkey.resps.LatencyHistoryInfo;
import io.valkey.resps.LatencyLatestInfo;
import io.valkey.util.KeyValue;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * This interface provides async commands for Redis server operations.
 */
public interface AsyncServerCommands {

  /**
   * Async version of PING command.
   * This command is often used to test if a connection is still alive, or to measure latency.
   * @return CompletableFuture that completes with PONG
   */
  CompletableFuture<String> ping();

  /**
   * Async version of PING command.
   * @param message message to send
   * @return CompletableFuture that completes with message
   */
  CompletableFuture<String> ping(String message);

  /**
   * Async version of ECHO command.
   * @param string message to echo
   * @return CompletableFuture that completes with message
   */
  CompletableFuture<String> echo(String string);

  /**
   * Async version of ECHO command.
   * @param arg message to echo
   * @return CompletableFuture that completes with message
   */
  CompletableFuture<byte[]> echo(byte[] arg);

  /**
   * Async version of FLUSHDB command.
   * Delete all the keys of the currently selected DB. This command never fails.
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> flushDB();

  /**
   * Async version of FLUSHDB command.
   * Delete all the keys of the currently selected DB. This command never fails.
   * @param flushMode can be SYNC or ASYNC
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> flushDB(FlushMode flushMode);

  /**
   * Async version of FLUSHALL command.
   * Delete all the keys of all the existing databases, not just the currently selected one.
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> flushAll();

  /**
   * Async version of FLUSHALL command.
   * Delete all the keys of all the existing databases, not just the currently selected one.
   * @param flushMode SYNC or ASYNC
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> flushAll(FlushMode flushMode);

  /**
   * Async version of AUTH command.
   * Request for authentication in a password-protected Redis server.
   * @param password password to authenticate with
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> auth(String password);

  /**
   * Async version of AUTH command.
   * Request for authentication in a password-protected Redis server.
   * @param user username to authenticate with
   * @param password password to authenticate with
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> auth(String user, String password);

  /**
   * Async version of SAVE command.
   * Synchronously save the dataset to disk.
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> save();

  /**
   * Async version of BGSAVE command.
   * Asynchronously save the dataset to disk.
   * @return CompletableFuture that completes with status message
   */
  CompletableFuture<String> bgsave();

  /**
   * Async version of BGSAVE command.
   * Asynchronously save the dataset to disk.
   * @param scheduleFlag schedule flag
   * @return CompletableFuture that completes with status message
   */
  CompletableFuture<String> bgsave(SaveMode scheduleFlag);

  /**
   * Async version of LASTSAVE command.
   * Get the UNIX time stamp of the last successful save to disk.
   * @return CompletableFuture that completes with timestamp
   */
  CompletableFuture<Long> lastsave();

  /**
   * Async version of SHUTDOWN command.
   * Stop all the clients, save the DB, then quit the server.
   * @return CompletableFuture that completes with status message
   */
  CompletableFuture<String> shutdown();

  /**
   * Async version of SHUTDOWN command.
   * Stop all the clients, save the DB, then quit the server.
   * @param params shutdown parameters
   * @return CompletableFuture that completes with status message
   */
  CompletableFuture<String> shutdown(ShutdownParams params);

  /**
   * Async version of INFO command.
   * Get information and statistics about the server.
   * @return CompletableFuture that completes with server info
   */
  CompletableFuture<String> info();

  /**
   * Async version of INFO command.
   * Get information and statistics about the server.
   * @param section section to get info for
   * @return CompletableFuture that completes with server info
   */
  CompletableFuture<String> info(String section);

  /**
   * Async version of SLAVEOF command.
   * Make the server a replica of another instance, or promote it as master.
   * @param host master host
   * @param port master port
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> slaveof(String host, int port);

  /**
   * Async version of REPLICAOF command.
   * Make the server a replica of another instance, or promote it as master.
   * @param host master host
   * @param port master port
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> replicaof(String host, int port);

  /**
   * Async version of ROLE command.
   * Return the role of the instance in the context of replication.
   * @return CompletableFuture that completes with role info
   */
  CompletableFuture<List<Object>> role();

  /**
   * Async version of DEBUG OBJECT command.
   * Get debugging information about a key.
   * @param key key to debug
   * @return CompletableFuture that completes with debug info
   */
  CompletableFuture<String> debugObject(String key);

  /**
   * Async version of DEBUG SEGFAULT command.
   * Make the server crash.
   * @return CompletableFuture that completes with status message
   */
  CompletableFuture<String> debugSegfault();

  /**
   * Async version of MEMORY DOCTOR command.
   * Outputs memory problems report.
   * @return CompletableFuture that completes with memory report
   */
  CompletableFuture<String> memoryDoctor();

  /**
   * Async version of MEMORY MALLOC-STATS command.
   * Show allocator internal stats.
   * @return CompletableFuture that completes with allocator stats
   */
  CompletableFuture<String> memoryMallocStats();

  /**
   * Async version of MEMORY PURGE command.
   * Ask the allocator to release memory.
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> memoryPurge();

  /**
   * Async version of MEMORY STATS command.
   * Show memory usage details.
   * @return CompletableFuture that completes with memory stats
   */
  CompletableFuture<Map<String, Object>> memoryStats();

  /**
   * Async version of MEMORY USAGE command.
   * Estimate the memory usage of a key.
   * @param key key to check
   * @return CompletableFuture that completes with memory usage in bytes
   */
  CompletableFuture<Long> memoryUsage(String key);

  /**
   * Async version of MEMORY USAGE command.
   * Estimate the memory usage of a key.
   * @param key key to check
   * @param samples number of samples
   * @return CompletableFuture that completes with memory usage in bytes
   */
  CompletableFuture<Long> memoryUsage(String key, int samples);

  /**
   * Async version of LATENCY LATEST command.
   * Return the latest latency samples for all events.
   * @return CompletableFuture that completes with latest latency samples
   */
  CompletableFuture<Map<String, LatencyLatestInfo>> latencyLatest();

  /**
   * Async version of LATENCY HISTORY command.
   * Return timestamp-latency samples for the specified event.
   * @param event event to get history for
   * @return CompletableFuture that completes with latency history
   */
  CompletableFuture<List<LatencyHistoryInfo>> latencyHistory(LatencyEvent event);

  /**
   * Async version of LATENCY RESET command.
   * Reset latency data for one or more events.
   * @param events events to reset
   * @return CompletableFuture that completes with number of events reset
   */
  CompletableFuture<Long> latencyReset(LatencyEvent... events);

  /**
   * Async version of LATENCY GRAPH command.
   * Return an ASCII-art representation of the latency for an event.
   * @param event event to graph
   * @return CompletableFuture that completes with latency graph
   */
  CompletableFuture<String> latencyGraph(LatencyEvent event);

  /**
   * Async version of LATENCY DOCTOR command.
   * Return a human-readable latency analysis report.
   * @return CompletableFuture that completes with latency report
   */
  CompletableFuture<String> latencyDoctor();

  /**
   * Async version of LOLWUT command.
   * Display some computer art and the Redis version.
   * @return CompletableFuture that completes with art and version
   */
  CompletableFuture<String> lolwut();

  /**
   * Async version of LOLWUT command.
   * Display some computer art and the Redis version.
   * @param params lolwut parameters
   * @return CompletableFuture that completes with art and version
   */
  CompletableFuture<String> lolwut(LolwutParams params);
} 