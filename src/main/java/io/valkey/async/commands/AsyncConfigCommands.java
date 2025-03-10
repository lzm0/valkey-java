package io.valkey.async.commands;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * This interface provides async commands for Redis configuration operations.
 */
public interface AsyncConfigCommands {

  /**
   * Async version of CONFIG GET command.
   * @param pattern configuration parameter pattern
   * @return CompletableFuture that completes with map of parameter names to values
   */
  CompletableFuture<Map<String, String>> configGet(String pattern);

  /**
   * Async version of CONFIG GET command.
   * @param patterns configuration parameter patterns
   * @return CompletableFuture that completes with map of parameter names to values
   */
  CompletableFuture<Map<String, String>> configGet(String... patterns);

  /**
   * Async version of CONFIG GET command for binary data.
   * @param pattern configuration parameter pattern
   * @return CompletableFuture that completes with map of parameter names to values
   */
  CompletableFuture<Map<byte[], byte[]>> configGet(byte[] pattern);

  /**
   * Async version of CONFIG GET command for binary data.
   * @param patterns configuration parameter patterns
   * @return CompletableFuture that completes with map of parameter names to values
   */
  CompletableFuture<Map<byte[], byte[]>> configGet(byte[]... patterns);

  /**
   * Async version of CONFIG SET command.
   * @param parameter configuration parameter name
   * @param value configuration parameter value
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> configSet(String parameter, String value);

  /**
   * Async version of CONFIG SET command.
   * @param parameterValues array of parameter name/value pairs
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> configSet(String... parameterValues);

  /**
   * Async version of CONFIG SET command.
   * @param parameterValues map of parameter names to values
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> configSet(Map<String, String> parameterValues);

  /**
   * Async version of CONFIG SET command for binary data.
   * @param parameter configuration parameter name
   * @param value configuration parameter value
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> configSet(byte[] parameter, byte[] value);

  /**
   * Async version of CONFIG SET command for binary data.
   * @param parameterValues array of parameter name/value pairs
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> configSet(byte[]... parameterValues);

  /**
   * Async version of CONFIG SET command for binary data.
   * @param parameterValues map of parameter names to values
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> configSetBinary(Map<byte[], byte[]> parameterValues);

  /**
   * Async version of CONFIG RESETSTAT command.
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> configResetStat();

  /**
   * Async version of CONFIG REWRITE command.
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> configRewrite();
} 