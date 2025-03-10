package io.valkey.async.commands;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import io.valkey.args.FlushMode;
import io.valkey.args.FunctionRestorePolicy;
import io.valkey.resps.FunctionStats;
import io.valkey.resps.LibraryInfo;

/**
 * This interface provides async commands for Redis function operations.
 */
public interface AsyncFunctionCommands {

  /**
   * Async version of <b><a href="http://redis.io/commands/fcall">FCALL Command</a></b>
   * Invoke a function.
   * @param name function name
   * @param keys keys that the function can access
   * @param args additional arguments
   * @return CompletableFuture that completes with the function's result
   */
  CompletableFuture<Object> fcall(String name, List<String> keys, List<String> args);

  /**
   * Async version of <b><a href="http://redis.io/commands/fcall-ro">FCALL_RO Command</a></b>
   * This is a read-only variant of the FCALL command that cannot execute commands that modify data.
   * @param name function name
   * @param keys keys that the function can access
   * @param args additional arguments
   * @return CompletableFuture that completes with the function's result
   */
  CompletableFuture<Object> fcallReadonly(String name, List<String> keys, List<String> args);

  /**
   * Async version of <b><a href="http://redis.io/commands/function-delete">FUNCTION DELETE Command</a></b>
   * This command deletes the library called library-name and all functions in it.
   * If the library doesn't exist, the server returns an error.
   * @param libraryName name of the library to delete
   * @return CompletableFuture that completes with OK on success
   */
  CompletableFuture<String> functionDelete(String libraryName);

  /**
   * Async version of <b><a href="http://redis.io/commands/function-dump">FUNCTION DUMP Command</a></b>
   * Return the serialized payload of loaded libraries. You can restore the
   * serialized payload later with the FUNCTION RESTORE command.
   * @return CompletableFuture that completes with the serialized payload
   */
  CompletableFuture<byte[]> functionDump();

  /**
   * Async version of <b><a href="http://redis.io/commands/function-flush">FUNCTION FLUSH Command</a></b>
   * Deletes all the libraries, unless called with the optional mode argument, the
   * 'lazyfree-lazy-user-flush' configuration directive sets the effective behavior.
   * @return CompletableFuture that completes with OK on success
   */
  CompletableFuture<String> functionFlush();

  /**
   * Async version of <b><a href="http://redis.io/commands/function-flush">FUNCTION FLUSH Command</a></b>
   * Deletes all the libraries, unless called with the optional mode argument, the
   * 'lazyfree-lazy-user-flush' configuration directive sets the effective behavior.
   * @param mode ASYNC: Asynchronously flush the libraries, SYNC: Synchronously flush the libraries.
   * @return CompletableFuture that completes with OK on success
   */
  CompletableFuture<String> functionFlush(FlushMode mode);

  /**
   * Async version of <b><a href="http://redis.io/commands/function-kill">FUNCTION KILL Command</a></b>
   * Kill a function that is currently executing. The command can be used only on functions
   * that did not modify the dataset during their execution.
   * @return CompletableFuture that completes with OK on success
   */
  CompletableFuture<String> functionKill();

  /**
   * Async version of <b><a href="http://redis.io/commands/function-list">FUNCTION LIST Command</a></b>
   * Return information about the functions and libraries.
   * @return CompletableFuture that completes with list of {@link LibraryInfo}
   */
  CompletableFuture<List<LibraryInfo>> functionList();

  /**
   * Async version of <b><a href="http://redis.io/commands/function-list">FUNCTION LIST Command</a></b>
   * Return information about the functions and libraries.
   * @param libraryNamePattern a pattern for matching library names
   * @return CompletableFuture that completes with list of {@link LibraryInfo}
   */
  CompletableFuture<List<LibraryInfo>> functionList(String libraryNamePattern);

  /**
   * Async version of <b><a href="http://redis.io/commands/function-list">FUNCTION LIST Command</a></b>
   * Similar to FUNCTION LIST but include the libraries source implementation in the reply.
   * @return CompletableFuture that completes with list of {@link LibraryInfo}
   */
  CompletableFuture<List<LibraryInfo>> functionListWithCode();

  /**
   * Async version of <b><a href="http://redis.io/commands/function-list">FUNCTION LIST Command</a></b>
   * Similar to FUNCTION LIST but include the libraries source implementation in the reply.
   * @param libraryNamePattern a pattern for matching library names
   * @return CompletableFuture that completes with list of {@link LibraryInfo}
   */
  CompletableFuture<List<LibraryInfo>> functionListWithCode(String libraryNamePattern);

  /**
   * Async version of <b><a href="http://redis.io/commands/function-load">FUNCTION LOAD Command</a></b>
   * Load a library to Redis.
   * <p>
   * The library payload must start with Shebang statement that provides a metadata about the
   * library (like the engine to use and the library name). Shebang format:
   * {@code #!<engine name> name=<library name>}. Currently engine name must be lua.
   *
   * @param functionCode the source code
   * @return CompletableFuture that completes with the library name that was loaded
   */
  CompletableFuture<String> functionLoad(String functionCode);

  /**
   * Async version of <b><a href="http://redis.io/commands/function-load">FUNCTION LOAD REPLACE Command</a></b>
   * Load a library to Redis. Will replace the current library if it already exists.
   * @param functionCode the source code
   * @return CompletableFuture that completes with the library name that was loaded
   */
  CompletableFuture<String> functionLoadReplace(String functionCode);

  /**
   * Async version of <b><a href="http://redis.io/commands/function-restore">FUNCTION RESTORE Command</a></b>
   * Restore libraries from the serialized payload. Default policy is APPEND.
   * @param serializedValue the serialized payload
   * @return CompletableFuture that completes with OK on success
   */
  CompletableFuture<String> functionRestore(byte[] serializedValue);

  /**
   * Async version of <b><a href="http://redis.io/commands/function-restore">FUNCTION RESTORE Command</a></b>
   * Restore libraries from the serialized payload.
   * @param serializedValue the serialized payload
   * @param policy can be {@link FunctionRestorePolicy FLUSH, APPEND or REPLACE}
   * @return CompletableFuture that completes with OK on success
   */
  CompletableFuture<String> functionRestore(byte[] serializedValue, FunctionRestorePolicy policy);

  /**
   * Async version of <b><a href="http://redis.io/commands/function-stats">FUNCTION STATS Command</a></b>
   * Return information about the function that's currently running and information
   * about the available execution engines.
   * @return CompletableFuture that completes with {@link FunctionStats}
   */
  CompletableFuture<FunctionStats> functionStats();
} 