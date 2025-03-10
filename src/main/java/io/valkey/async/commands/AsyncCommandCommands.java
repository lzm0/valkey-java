package io.valkey.async.commands;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import io.valkey.params.CommandListFilterByParams;
import io.valkey.resps.CommandDocument;
import io.valkey.resps.CommandInfo;
import io.valkey.util.KeyValue;

/**
 * This interface provides async commands for Redis command operations.
 */
public interface AsyncCommandCommands {

  /**
   * Async version of COMMAND COUNT command.
   * @return CompletableFuture that completes with number of total commands
   */
  CompletableFuture<Long> commandCount();

  /**
   * Async version of COMMAND DOCS command.
   * @param commands command names to get documentation for
   * @return CompletableFuture that completes with map of command names to documentation
   */
  CompletableFuture<Map<String, CommandDocument>> commandDocs(String... commands);

  /**
   * Async version of COMMAND GETKEYS command.
   * @param command command to analyze
   * @return CompletableFuture that completes with list of keys used by command
   */
  CompletableFuture<List<String>> commandGetKeys(String... command);

  /**
   * Async version of COMMAND GETKEYSANDFLAGS command.
   * @param command command to analyze
   * @return CompletableFuture that completes with list of keys and their flags
   */
  CompletableFuture<List<KeyValue<String, List<String>>>> commandGetKeysAndFlags(String... command);

  /**
   * Async version of COMMAND INFO command.
   * @param commands command names to get info for
   * @return CompletableFuture that completes with map of command names to info
   */
  CompletableFuture<Map<String, CommandInfo>> commandInfo(String... commands);

  /**
   * Async version of COMMAND LIST command.
   * @return CompletableFuture that completes with list of command names
   */
  CompletableFuture<List<String>> commandList();

  /**
   * Async version of COMMAND LIST FILTERBY command.
   * @param filterByParams filter parameters
   * @return CompletableFuture that completes with filtered list of command names
   */
  CompletableFuture<List<String>> commandListFilterBy(CommandListFilterByParams filterByParams);
} 