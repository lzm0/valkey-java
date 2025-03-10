package io.valkey.async.commands;

import java.util.concurrent.CompletableFuture;

import io.valkey.args.ClientAttributeOption;
import io.valkey.args.ClientPauseMode;
import io.valkey.args.ClientType;
import io.valkey.args.UnblockType;
import io.valkey.params.ClientKillParams;
import io.valkey.resps.TrackingInfo;

/**
 * This interface provides async commands for client operations in Redis.
 * The params is String encoded in utf-8
 */
public interface AsyncClientCommands {

  /**
   * Async version of <b><a href="http://redis.io/commands/client-kill">CLIENT KILL Command</a></b>
   * Close a given client connection.
   *
   * @param ipPort The ip:port should match a line returned by the CLIENT LIST command (addr field).
   * @return CompletableFuture that completes with OK on success
   */
  CompletableFuture<String> clientKill(String ipPort);

  /**
   * Async version of <b><a href="http://redis.io/commands/client-kill">CLIENT KILL Command</a></b>
   * Close a given client connection.
   *
   * @param ip   The ip should match a line returned by the CLIENT LIST command (addr field).
   * @param port The port should match a line returned by the CLIENT LIST command (addr field).
   * @return CompletableFuture that completes with OK on success
   */
  CompletableFuture<String> clientKill(String ip, int port);

  /**
   * Async version of <b><a href="http://redis.io/commands/client-kill">CLIENT KILL Command</a></b>
   * Close client connections based on certain selection parameters.
   *
   * @param params Parameters defining what client connections to close.
   * @return CompletableFuture that completes with the number of client connections that were closed
   */
  CompletableFuture<Long> clientKill(ClientKillParams params);

  /**
   * Async version of <b><a href="http://redis.io/commands/client-getname">CLIENT GETNAME Command</a></b>
   * Returns the name of the current connection as set by CLIENT SETNAME
   *
   * @return CompletableFuture that completes with current connect name
   */
  CompletableFuture<String> clientGetname();

  /**
   * Async version of <b><a href="http://redis.io/commands/client-list">CLIENT LIST Command</a></b>
   * Returns information and statistics about the client connections server
   * in a mostly human-readable format.
   *
   * @return CompletableFuture that completes with all clients info connected to redis-server
   */
  CompletableFuture<String> clientList();

  /**
   * Async version of <b><a href="http://redis.io/commands/client-list">CLIENT LIST Command</a></b>
   * Returns information and statistics about the client connections server
   * in a mostly human-readable format filter by client type.
   *
   * @return CompletableFuture that completes with all clients info connected to redis-server
   */
  CompletableFuture<String> clientList(ClientType type);

  /**
   * Async version of <b><a href="http://redis.io/commands/client-list">CLIENT LIST Command</a></b>
   * Returns information and statistics about the client connections server
   * in a mostly human-readable format filter by client ids.
   *
   * @param clientIds Unique 64-bit client IDs
   * @return CompletableFuture that completes with all clients info connected to redis-server
   */
  CompletableFuture<String> clientList(long... clientIds);

  /**
   * Async version of <b><a href="http://redis.io/commands/client-info">CLIENT INFO Command</a></b>
   * Returns information and statistics about the current client connection
   * in a mostly human-readable format.
   *
   * @return CompletableFuture that completes with information and statistics about the current client connection
   */
  CompletableFuture<String> clientInfo();

  /**
   * Async version of <b><a href="http://redis.io/commands/client-setinfo">CLIENT SETINFO Command</a></b>
   * client set info command
   * Since redis 7.2
   * @param attr the attr option
   * @param value the value
   * @return CompletableFuture that completes with OK on success or error
   */
  CompletableFuture<String> clientSetInfo(ClientAttributeOption attr, String value);

  /**
   * Async version of <b><a href="http://redis.io/commands/client-setname">CLIENT SETNAME Command</a></b>
   * Assigns a name to the current connection.
   *
   * @param name current connection name
   * @return CompletableFuture that completes with OK if the connection name was successfully set
   */
  CompletableFuture<String> clientSetname(String name);

  /**
   * Async version of <b><a href="http://redis.io/commands/client-id">CLIENT ID Command</a></b>
   * Returns the ID of the current connection.
   *
   * @return CompletableFuture that completes with the id of the client
   */
  CompletableFuture<Long> clientId();

  /**
   * Async version of <b><a href="http://redis.io/commands/client-unblock">CLIENT UNBLOCK Command</a></b>
   * Unblock from a different connection, a client blocked in a
   * blocking operation, such as for instance BRPOP or XREAD or WAIT.
   *
   * @param clientId The id of the client
   * @return CompletableFuture that completes with:
   * 1 if the client was unblocked successfully.
   * 0 if the client wasn't unblocked.
   */
  CompletableFuture<Long> clientUnblock(long clientId);

  /**
   * Async version of <b><a href="http://redis.io/commands/client-unblock">CLIENT UNBLOCK Command</a></b>
   * Unblock from a different connection, a client blocked in a
   * blocking operation, such as for instance BRPOP or XREAD or WAIT.
   *
   * @param clientId    The id of the client
   * @param unblockType TIMEOUT|ERROR
   * @return CompletableFuture that completes with:
   * 1 if the client was unblocked successfully.
   * 0 if the client wasn't unblocked.
   */
  CompletableFuture<Long> clientUnblock(long clientId, UnblockType unblockType);

  /**
   * Async version of <b><a href="http://redis.io/commands/client-pause">CLIENT PAUSE Command</a></b>
   * A connections control command able to suspend all the
   * Redis clients for the specified amount of time (in milliseconds)
   *
   * @param timeout WRITE|ALL
   * @return CompletableFuture that completes with OK or an error if the timeout is invalid
   */
  CompletableFuture<String> clientPause(long timeout);

  /**
   * Async version of <b><a href="http://redis.io/commands/client-pause">CLIENT PAUSE Command</a></b>
   * A connections control command able to suspend all the
   * Redis clients for the specified amount of time (in milliseconds)
   *
   * @param timeout Command timeout
   * @param mode    WRITE|ALL
   * @return CompletableFuture that completes with OK or an error if the timeout is invalid
   */
  CompletableFuture<String> clientPause(long timeout, ClientPauseMode mode);

  /**
   * Async version of <b><a href="http://redis.io/commands/client-unpause">CLIENT UNPAUSE Command</a></b>
   * CLIENT UNPAUSE is used to resume command processing for all clients that were paused by CLIENT PAUSE.
   * @return CompletableFuture that completes with OK on success
   */
  CompletableFuture<String> clientUnpause();

  /**
   * Async version of <b><a href="http://redis.io/commands/client-no-evict">CLIENT NO-EVICT Command</a></b>
   * Turn on the client eviction mode for the current connection.
   *
   * @return CompletableFuture that completes with OK on success
   */
  CompletableFuture<String> clientNoEvictOn();

  /**
   * Async version of <b><a href="http://redis.io/commands/client-no-evict">CLIENT NO-EVICT Command</a></b>
   * Turn off the client eviction mode for the current connection.
   *
   * @return CompletableFuture that completes with OK on success
   */
  CompletableFuture<String> clientNoEvictOff();

  /**
   * Async version of <b><a href="http://redis.io/commands/client-no-touch">CLIENT NO-TOUCH Command</a></b>
   * Turn on CLIENT NO-TOUCH mode
   *
   * @return CompletableFuture that completes with OK on success
   */
  CompletableFuture<String> clientNoTouchOn();

  /**
   * Async version of <b><a href="http://redis.io/commands/client-no-touch">CLIENT NO-TOUCH Command</a></b>
   * Turn off CLIENT NO-TOUCH mode
   *
   * @return CompletableFuture that completes with OK on success
   */
  CompletableFuture<String> clientNoTouchOff();

  /**
   * Async version of <b><a href="http://redis.io/commands/client-tracking-info">CLIENT TRACKING INFO Command</a></b>
   * Get tracking information about the current connection
   *
   * @return CompletableFuture that completes with tracking information
   */
  CompletableFuture<TrackingInfo> clientTrackingInfo();
} 