package io.valkey.async.commands;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * This interface provides async commands for Redis management operations with binary data.
 */
public interface AsyncControlBinaryCommands extends AsyncAccessControlLogBinaryCommands, AsyncClientBinaryCommands {

  /**
   * Async version of ROLE command.
   * Provide information on the role of a Redis instance in the context of replication,
   * by returning if the instance is currently a master, slave, or sentinel. The command
   * also returns additional information about the state of the replication
   * (if the role is master or slave) or the list of monitored master names (if the role is sentinel).
   *
   * @return CompletableFuture that completes with the information on the role of a Redis instance
   */
  CompletableFuture<List<Object>> roleBinary();

  /**
   * Async version of OBJECT REFCOUNT command.
   * Returns the reference count of the stored at {@code key}.
   *
   * @param key The key in Redis server
   * @return CompletableFuture that completes with the reference count of the stored at {@code key}
   */
  CompletableFuture<Long> objectRefcount(byte[] key);

  /**
   * Async version of OBJECT ENCODING command.
   * Returns the internal encoding for the Redis object stored at {@code key}.
   * <p>
   * See for details: <a href="https://redis.io/commands/object-encoding">OBJECT ENCODING key</a>
   *
   * @param key The key in Redis server
   * @return CompletableFuture that completes with the number of references
   */
  CompletableFuture<byte[]> objectEncoding(byte[] key);

  /**
   * Async version of OBJECT IDLETIME command.
   * Returns the time in seconds since the last access to the value stored at {@code key}.
   * The command is only available when the maxmemory-policy configuration directive
   * is not set to one of the LFU policies.
   *
   * @param key The key in Redis server
   * @return CompletableFuture that completes with the idle time in seconds
   */
  CompletableFuture<Long> objectIdletime(byte[] key);

  /**
   * Async version of OBJECT HELP command.
   * Returns the object subcommands and usages.
   *
   * @return CompletableFuture that completes with object subcommands and usages
   */
  CompletableFuture<List<byte[]>> objectHelpBinary();

  /**
   * Async version of OBJECT FREQ command.
   * Returns the logarithmic access frequency counter of a Redis object stored at {@code key}.
   * <p>
   * The command is only available when the maxmemory-policy configuration directive is
   * set to one of the LFU policies.
   *
   * @param key The key in Redis server
   * @return CompletableFuture that completes with the counter's value
   */
  CompletableFuture<Long> objectFreq(byte[] key);

  /**
   * Async version of MEMORY DOCTOR command.
   * Reports about different memory-related issues that the Redis server experiences,
   * and advises about possible remedies.
   *
   * @return CompletableFuture that completes with the memory doctor report
   */
  CompletableFuture<byte[]> memoryDoctorBinary();

  /**
   * Async version of MEMORY USAGE command.
   * Reports the number of bytes that a key and its value require to be stored in RAM.
   * The reported usage is the total of memory allocations for data and administrative
   * overheads that a key its value require.
   * <p>
   * See for details: <a href="https://redis.io/commands/memory-usage">MEMORY USAGE key</a>
   *
   * @param key The key in Redis server
   * @return CompletableFuture that completes with the memory usage in bytes, or null when the key does not exist
   */
  CompletableFuture<Long> memoryUsage(byte[] key);

  /**
   * Async version of MEMORY USAGE command.
   * Reports the number of bytes that a key and its value require to be stored in RAM.
   * The reported usage is the total of memory allocations for data and administrative
   * overheads that a key its value require.
   * <p>
   * See for details: <a href="https://redis.io/commands/memory-usage">MEMORY USAGE key SAMPLES count</a>
   *
   * @param key The key in Redis server
   * @param samples The number of samples to take
   * @return CompletableFuture that completes with the memory usage in bytes, or null when the key does not exist
   */
  CompletableFuture<Long> memoryUsage(byte[] key, int samples);
} 