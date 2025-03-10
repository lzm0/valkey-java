package io.valkey.async.commands;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * This interface provides async commands for Redis scripting operations.
 */
public interface AsyncScriptingKeyCommands {

  /**
   * Async version of EVAL command.
   * @param script Lua script
   * @return CompletableFuture that completes with the result of script evaluation
   */
  CompletableFuture<Object> eval(String script);

  /**
   * Async version of EVAL command.
   * @param script Lua script
   * @param keyCount number of keys
   * @param params parameters
   * @return CompletableFuture that completes with the result of script evaluation
   */
  CompletableFuture<Object> eval(String script, int keyCount, String... params);

  /**
   * Async version of EVAL command.
   * @param script Lua script
   * @param keys keys
   * @param args arguments
   * @return CompletableFuture that completes with the result of script evaluation
   */
  CompletableFuture<Object> eval(String script, List<String> keys, List<String> args);

  /**
   * Async version of EVAL_RO command.
   * @param script Lua script
   * @param keys keys
   * @param args arguments
   * @return CompletableFuture that completes with the result of script evaluation
   */
  CompletableFuture<Object> evalReadonly(String script, List<String> keys, List<String> args);

  /**
   * Async version of EVALSHA command.
   * @param sha1 SHA1 digest of the script
   * @return CompletableFuture that completes with the result of script evaluation
   */
  CompletableFuture<Object> evalsha(String sha1);

  /**
   * Async version of EVALSHA command.
   * @param sha1 SHA1 digest of the script
   * @param keyCount number of keys
   * @param params parameters
   * @return CompletableFuture that completes with the result of script evaluation
   */
  CompletableFuture<Object> evalsha(String sha1, int keyCount, String... params);

  /**
   * Async version of EVALSHA command.
   * @param sha1 SHA1 digest of the script
   * @param keys keys
   * @param args arguments
   * @return CompletableFuture that completes with the result of script evaluation
   */
  CompletableFuture<Object> evalsha(String sha1, List<String> keys, List<String> args);

  /**
   * Async version of EVALSHA_RO command.
   * @param sha1 SHA1 digest of the script
   * @param keys keys
   * @param args arguments
   * @return CompletableFuture that completes with the result of script evaluation
   */
  CompletableFuture<Object> evalshaReadonly(String sha1, List<String> keys, List<String> args);
} 