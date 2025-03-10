package io.valkey.async.commands;

import java.util.concurrent.CompletableFuture;

import io.valkey.args.FlushMode;
import io.valkey.params.MigrateParams;

/**
 * This interface provides async commands for Redis database operations.
 */
public interface AsyncDatabaseCommands {

  /**
   * Async version of SELECT command.
   * Select the DB with having the specified zero-based numeric index.
   * @param index the index
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> select(int index);

  /**
   * Async version of DBSIZE command.
   * Return the number of keys in the currently-selected database.
   * @return CompletableFuture that completes with the number of keys
   */
  CompletableFuture<Long> dbSize();

  /**
   * Async version of FLUSHDB command.
   * Delete all the keys of the currently selected DB. This command never fails. The time-complexity
   * for this operation is O(N), N being the number of keys in the database.
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> flushDB();

  /**
   * Async version of FLUSHDB command.
   * Delete all the keys of the currently selected DB. This command never fails. The time-complexity
   * for this operation is O(N), N being the number of keys in the database.
   * @param flushMode can be SYNC or ASYNC
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> flushDB(FlushMode flushMode);

  /**
   * Async version of SWAPDB command.
   * This command swaps two Redis databases, so that immediately all the clients connected to a
   * given database will see the data of the other database, and the other way around.
   * @param index1
   * @param index2
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> swapDB(int index1, int index2);

  /**
   * Async version of MOVE command.
   * Move the specified key from the currently selected DB to the specified destination DB. Note
   * that this command returns 1 only if the key was successfully moved, and 0 if the target key was
   * already there or if the source key was not found at all, so it is possible to use MOVE as a
   * locking primitive.
   * @param key  The specified key
   * @param dbIndex Specified destination database
   * @return CompletableFuture that completes with 1 if the key was moved, 0 if the key was not moved because already present on the target
   * DB or was not found in the current DB
   */
  CompletableFuture<Long> move(String key, int dbIndex);

  /**
   * Binary version of {@link AsyncDatabaseCommands#move(String, int) MOVE}.
   * @see AsyncDatabaseCommands#move(String, int)
   */
  CompletableFuture<Long> move(byte[] key, int dbIndex);

  /**
   * Async version of COPY command.
   * Copy the value stored at the source key to the destination key.
   * @param srcKey the source key.
   * @param dstKey the destination key.
   * @param db allows specifying an alternative logical database index for the destination key.
   * @param replace removes the destination key before copying the value to it, in order to avoid error.
   * @return CompletableFuture that completes with true if the key was copied, false otherwise
   */
  CompletableFuture<Boolean> copy(String srcKey, String dstKey, int db, boolean replace);

  /**
   * Binary version of {@link AsyncDatabaseCommands#copy(String, String, int, boolean) COPY}.
   * @see AsyncDatabaseCommands#copy(String, String, int, boolean)
   */
  CompletableFuture<Boolean> copy(byte[] srcKey, byte[] dstKey, int db, boolean replace);

  /**
   * Async version of MIGRATE command.
   * Atomically transfer a key from a source Redis instance to a destination Redis instance.
   * On success the key is deleted from the original instance and is guaranteed to exist in
   * the target instance.
   *
   * @param host          target host
   * @param port          target port
   * @param key           migrate key
   * @param destinationDB target db
   * @param timeout       the maximum idle time in any moment of the communication with the
   *                      destination instance in milliseconds.
   * @return CompletableFuture that completes with OK on success, or NOKEY if no keys were found in the source instance
   */
  CompletableFuture<String> migrate(String host, int port, String key, int destinationDB, int timeout);

  /**
   * Binary version of {@link AsyncDatabaseCommands#migrate(String, int, String, int, int) MIGRATE}.
   * @see AsyncDatabaseCommands#migrate(String, int, String, int, int)
   */
  CompletableFuture<String> migrate(String host, int port, byte[] key, int destinationDB, int timeout);

  /**
   * Async version of MIGRATE command.
   * Atomically transfer a key from a source Redis instance to a destination Redis instance.
   * On success the key is deleted from the original instance and is guaranteed to exist in
   * the target instance.
   * @param host          target host
   * @param port          target port
   * @param destinationDB target db
   * @param timeout the maximum idle time in any moment of the communication with the
   *               destination instance in milliseconds.
   * @param params {@link MigrateParams}
   * @param keys to migrate
   * @return CompletableFuture that completes with OK on success, or NOKEY if no keys were found in the source instance.
   */
  CompletableFuture<String> migrate(String host, int port, int destinationDB, int timeout, MigrateParams params,
      String... keys);

  /**
   * Binary version of {@link AsyncDatabaseCommands#migrate(String, int, int, int, MigrateParams, String...) MIGRATE}.
   * @see AsyncDatabaseCommands#migrate(String, int, int, int, MigrateParams, String...)
   */
  CompletableFuture<String> migrate(String host, int port, int destinationDB, int timeout, MigrateParams params,
      byte[]... keys);
} 