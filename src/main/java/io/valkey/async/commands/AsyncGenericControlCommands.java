package io.valkey.async.commands;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import io.valkey.Module;
import io.valkey.args.FlushMode;
import io.valkey.params.FailoverParams;
import io.valkey.resps.Slowlog;

/**
 * This interface provides async commands for Redis generic control operations.
 */
public interface AsyncGenericControlCommands extends AsyncConfigCommands, AsyncScriptingControlCommands, AsyncSlowlogCommands {

  /**
   * Async version of <b><a href="http://redis.io/commands/failover">FAILOVER Command</a></b>
   * Force a replica to perform a manual failover of its master instance.
   * @return CompletableFuture that completes with OK on success
   */
  CompletableFuture<String> failover();

  /**
   * Async version of <b><a href="http://redis.io/commands/failover">FAILOVER Command</a></b>
   * Force a replica to perform a manual failover of its master instance with specified parameters.
   * @param failoverParams parameters for the failover operation
   * @return CompletableFuture that completes with OK on success
   */
  CompletableFuture<String> failover(FailoverParams failoverParams);

  /**
   * Async version of <b><a href="http://redis.io/commands/failover">FAILOVER ABORT Command</a></b>
   * Abort an ongoing failover.
   * @return CompletableFuture that completes with OK on success
   */
  CompletableFuture<String> failoverAbort();

  /**
   * Async version of <b><a href="http://redis.io/commands/module-list">MODULE LIST Command</a></b>
   * List all modules loaded by the server.
   * @return CompletableFuture that completes with list of {@link Module}
   */
  CompletableFuture<List<Module>> moduleList();
} 