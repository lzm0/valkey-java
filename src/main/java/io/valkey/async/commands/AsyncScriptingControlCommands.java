package io.valkey.async.commands;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import io.valkey.args.FlushMode;

/**
 * This interface provides async commands for Redis scripting control operations.
 */
public interface AsyncScriptingControlCommands {

  /**
   * Async version of <b><a href="http://redis.io/commands/script-exists">SCRIPT EXISTS Command</a></b>
   * Returns information about the existence of the scripts in the script cache.
   * @param sha1 the SHA1 digest of the script
   * @return CompletableFuture that completes with true if the script exists, false otherwise
   */
  CompletableFuture<Boolean> scriptExists(String sha1);

  /**
   * Async version of <b><a href="http://redis.io/commands/script-exists">SCRIPT EXISTS Command</a></b>
   * Returns information about the existence of the scripts in the script cache.
   * @param sha1 array of SHA1 digests of scripts
   * @return CompletableFuture that completes with list of booleans indicating existence of each script
   */
  CompletableFuture<List<Boolean>> scriptExists(String... sha1);

  /**
   * Async version of <b><a href="http://redis.io/commands/script-exists">SCRIPT EXISTS Command</a></b>
   * Binary version of {@link AsyncScriptingControlCommands#scriptExists(String)}
   * @param sha1 the SHA1 digest of the script
   * @return CompletableFuture that completes with true if the script exists, false otherwise
   */
  CompletableFuture<Boolean> scriptExists(byte[] sha1);

  /**
   * Async version of <b><a href="http://redis.io/commands/script-exists">SCRIPT EXISTS Command</a></b>
   * Binary version of {@link AsyncScriptingControlCommands#scriptExists(String...)}
   * @param sha1 array of SHA1 digests of scripts
   * @return CompletableFuture that completes with list of booleans indicating existence of each script
   */
  CompletableFuture<List<Boolean>> scriptExists(byte[]... sha1);

  /**
   * Async version of <b><a href="http://redis.io/commands/script-load">SCRIPT LOAD Command</a></b>
   * Load a script into the scripts cache without executing it.
   * @param script the script to load
   * @return CompletableFuture that completes with the SHA1 digest of the script
   */
  CompletableFuture<String> scriptLoad(String script);

  /**
   * Async version of <b><a href="http://redis.io/commands/script-load">SCRIPT LOAD Command</a></b>
   * Binary version of {@link AsyncScriptingControlCommands#scriptLoad(String)}
   * @param script the script to load
   * @return CompletableFuture that completes with the SHA1 digest of the script
   */
  CompletableFuture<byte[]> scriptLoad(byte[] script);

  /**
   * Async version of <b><a href="http://redis.io/commands/script-flush">SCRIPT FLUSH Command</a></b>
   * Remove all scripts from the script cache.
   * @return CompletableFuture that completes with OK on success
   */
  CompletableFuture<String> scriptFlush();

  /**
   * Async version of <b><a href="http://redis.io/commands/script-flush">SCRIPT FLUSH Command</a></b>
   * Remove all scripts from the script cache with specified flush mode.
   * @param flushMode SYNC or ASYNC flush mode
   * @return CompletableFuture that completes with OK on success
   */
  CompletableFuture<String> scriptFlush(FlushMode flushMode);

  /**
   * Async version of <b><a href="http://redis.io/commands/script-kill">SCRIPT KILL Command</a></b>
   * Kill the currently executing Lua script.
   * @return CompletableFuture that completes with OK on success
   */
  CompletableFuture<String> scriptKill();
} 