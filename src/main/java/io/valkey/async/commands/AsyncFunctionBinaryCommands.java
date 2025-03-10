package io.valkey.async.commands;

import io.valkey.args.FlushMode;
import io.valkey.args.FunctionRestorePolicy;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * This interface provides async commands for Redis function operations with binary data.
 */
public interface AsyncFunctionBinaryCommands {

  /**
   * Async version of FCALL command.
   * Invoke a function.
   * @param name function name
   * @param keys function keys
   * @param args function arguments
   * @return CompletableFuture that completes with the function result
   */
  CompletableFuture<Object> fcall(byte[] name, List<byte[]> keys, List<byte[]> args);

  /**
   * Async version of FCALL_RO command.
   * This is a read-only variant of the FCALL command that cannot execute commands that modify data.
   * @param name function name
   * @param keys function keys
   * @param args function arguments
   * @return CompletableFuture that completes with the function result
   */
  CompletableFuture<Object> fcallReadonly(byte[] name, List<byte[]> keys, List<byte[]> args);

  /**
   * Async version of FUNCTION DELETE command.
   * This command deletes the library called library-name and all functions in it.
   * If the library doesn't exist, the server returns an error.
   * @param libraryName library name to delete
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> functionDelete(byte[] libraryName);

  /**
   * Async version of FUNCTION DUMP command.
   * Return the serialized payload of loaded libraries. You can restore the
   * serialized payload later with the FUNCTION RESTORE command.
   * @return CompletableFuture that completes with the serialized payload
   */
  CompletableFuture<byte[]> functionDump();

  /**
   * Async version of FUNCTION FLUSH command.
   * Deletes all the libraries, unless called with the optional mode argument, the
   * 'lazyfree-lazy-user-flush' configuration directive sets the effective behavior.
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> functionFlush();

  /**
   * Async version of FUNCTION FLUSH command.
   * Deletes all the libraries, unless called with the optional mode argument, the
   * 'lazyfree-lazy-user-flush' configuration directive sets the effective behavior.
   * @param mode ASYNC: Asynchronously flush the libraries, SYNC: Synchronously flush the libraries.
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> functionFlush(FlushMode mode);

  /**
   * Async version of FUNCTION KILL command.
   * Kill a function that is currently executing. The command can be used only on functions
   * that did not modify the dataset during their execution.
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> functionKill();

  /**
   * Async version of FUNCTION LIST command.
   * Return information about the functions and libraries.
   * @return CompletableFuture that completes with list of function information
   */
  CompletableFuture<List<Object>> functionListBinary();

  /**
   * Async version of FUNCTION LIST command.
   * Return information about the functions and libraries.
   * @param libraryNamePattern a pattern for matching library names
   * @return CompletableFuture that completes with list of function information
   */
  CompletableFuture<List<Object>> functionList(byte[] libraryNamePattern);

  /**
   * Async version of FUNCTION LIST WITHCODE command.
   * Return information about the functions and libraries including their code.
   * @return CompletableFuture that completes with list of function information including code
   */
  CompletableFuture<List<Object>> functionListWithCodeBinary();

  /**
   * Async version of FUNCTION LIST WITHCODE command.
   * Return information about the functions and libraries including their code.
   * @param libraryNamePattern a pattern for matching library names
   * @return CompletableFuture that completes with list of function information including code
   */
  CompletableFuture<List<Object>> functionListWithCode(byte[] libraryNamePattern);

  /**
   * Async version of FUNCTION LOAD command.
   * Load a library to Redis.
   * @param functionCode the library code
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> functionLoad(byte[] functionCode);

  /**
   * Async version of FUNCTION LOAD REPLACE command.
   * Load a library to Redis, replacing any existing one with the same name.
   * @param functionCode the library code
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> functionLoadReplace(byte[] functionCode);

  /**
   * Async version of FUNCTION RESTORE command.
   * Restore libraries from the serialized payload.
   * @param serializedValue the serialized payload
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> functionRestore(byte[] serializedValue);

  /**
   * Async version of FUNCTION RESTORE command.
   * Restore libraries from the serialized payload.
   * @param serializedValue the serialized payload
   * @param policy the restore policy
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> functionRestore(byte[] serializedValue, FunctionRestorePolicy policy);

  /**
   * Async version of FUNCTION STATS command.
   * Return information about the function running in the server.
   * @return CompletableFuture that completes with function statistics
   */
  CompletableFuture<Object> functionStatsBinary();
} 