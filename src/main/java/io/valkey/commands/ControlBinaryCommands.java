package io.valkey.commands;

import java.util.List;

/**
 * The interface about Redis management command
 */
public interface ControlBinaryCommands extends AccessControlLogBinaryCommands, ClientBinaryCommands {

  /**
   * Provide information on the role of a Redis instance in the context of replication,
   * by returning if the instance is currently a master, slave, or sentinel. The command
   * also returns additional information about the state of the replication
   * (if the role is master or slave) or the list of monitored master names (if the role is sentinel).
   *
   * @return The information on the role of a Redis instance
   */
  List<Object> roleBinary();

  /**
   * Returns the reference count of the stored at {@code key}.
   *
   * @param key The key in Redis server
   * @return The reference count of the stored at {@code key}
   */
  Long objectRefcount(byte[] key);

  /**
   * Returns the internal encoding for the Redis object stored at {@code key}.
   * <p>
   * See for details: <a href="https://valkey.io/commands/object-encoding">OBJECT ENCODING key</a>
   *
   * @param key The key in Redis server
   * @return The number of references
   */
  byte[] objectEncoding(byte[] key);

  /**
   * Returns the time in seconds since the last access to the value stored at {@code key}.
   * The command is only available when the maxmemory-policy configuration directive
   * is not set to one of the LFU policies.
   *
   * @param key The key in Redis server
   * @return The idle time in seconds
   */
  Long objectIdletime(byte[] key);

  /**
   * Returns the object subcommands and usages.
   *
   * @return object subcommands and usages
   */
  List<byte[]> objectHelpBinary();

  /**
   * Returns the logarithmic access frequency counter of a Redis object stored at {@code key}.
   * <p>
   * The command is only available when the maxmemory-policy configuration directive is
   * set to one of the LFU policies.
   *
   * @param key The key in Redis server
   * @return The counter's value
   */
  Long objectFreq(byte[] key);

  /**
   * Reports about different memory-related issues that the Redis server experiences,
   * and advises about possible remedies.
   */
  byte[] memoryDoctorBinary();

  /**
   * Reports the number of bytes that a key and its value require to be stored in RAM.
   * The reported usage is the total of memory allocations for data and administrative
   * overheads that a key its value require.
   * <p>
   * See for details: <a href="https://valkey.io/commands/memory-usage">MEMORY USAGE key</a>
   *
   * @param key The key in Redis server
   * @return The memory usage in bytes, or nil(Java's null) when the key does not exist
   */
  Long memoryUsage(byte[] key);

  /**
   * Reports the number of bytes that a key and its value require to be stored in RAM.
   * The reported usage is the total of memory allocations for data and administrative
   * overheads that a key its value require.
   * <p>
   * See for details: <a href="https://valkey.io/commands/memory-usage">MEMORY USAGE key SAMPLES count</a>
   *
   * @param key The key in Redis server
   * @return The memory usage in bytes, or nil(Java's null) when the key does not exist
   */
  Long memoryUsage(byte[] key, int samples);

}
