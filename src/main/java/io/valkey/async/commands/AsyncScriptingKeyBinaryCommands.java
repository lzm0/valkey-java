package io.valkey.async.commands;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * This interface provides async commands for Redis scripting operations with binary data.
 */
public interface AsyncScriptingKeyBinaryCommands {

  /**
   * Async version of EVAL command.
   * @param script Lua script
   * @return CompletableFuture that completes with the result of script evaluation
   */
  CompletableFuture<Object> eval(byte[] script);

  /**
   * Async version of EVAL command.
   * @param script Lua script
   * @param keyCount number of keys
   * @param params parameters
   * @return CompletableFuture that completes with the result of script evaluation
   */
  CompletableFuture<Object> eval(byte[] script, int keyCount, byte[]... params);

  /**
   * Async version of EVAL command.
   * @param script Lua script
   * @param keys keys
   * @param args arguments
   * @return CompletableFuture that completes with the result of script evaluation
   */
  CompletableFuture<Object> eval(byte[] script, List<byte[]> keys, List<byte[]> args);

  /**
   * Async version of EVAL_RO command.
   * @param script Lua script
   * @param keys keys
   * @param args arguments
   * @return CompletableFuture that completes with the result of script evaluation
   */
  CompletableFuture<Object> evalReadonly(byte[] script, List<byte[]> keys, List<byte[]> args);

  /**
   * Async version of EVALSHA command.
   * @param sha1 SHA1 digest of the script
   * @return CompletableFuture that completes with the result of script evaluation
   */
  CompletableFuture<Object> evalsha(byte[] sha1);

  /**
   * Async version of EVALSHA command.
   * @param sha1 SHA1 digest of the script
   * @param keyCount number of keys
   * @param params parameters
   * @return CompletableFuture that completes with the result of script evaluation
   */
  CompletableFuture<Object> evalsha(byte[] sha1, int keyCount, byte[]... params);

  /**
   * Async version of EVALSHA command.
   * @param sha1 SHA1 digest of the script
   * @param keys keys
   * @param args arguments
   * @return CompletableFuture that completes with the result of script evaluation
   */
  CompletableFuture<Object> evalsha(byte[] sha1, List<byte[]> keys, List<byte[]> args);

  /**
   * Async version of EVALSHA_RO command.
   * @param sha1 SHA1 digest of the script
   * @param keys keys
   * @param args arguments
   * @return CompletableFuture that completes with the result of script evaluation
   */
  CompletableFuture<Object> evalshaReadonly(byte[] sha1, List<byte[]> keys, List<byte[]> args);
} 