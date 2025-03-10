package io.valkey.async.commands;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import io.valkey.CommandArguments;
import io.valkey.resps.AccessControlLogEntry;
import io.valkey.resps.AccessControlUser;

/**
 * This class provides the async interfaces necessary to interact with
 * Access Control Lists (ACLs) within redis. These are the interfaces
 * for string-based (i.e. decoded) interactions.
 *
 * @see <a href="https://redis.io/topics/acl">Redis ACL Guide</a>
 */
public interface AsyncAccessControlLogCommands {

  /**
   * Async version of <b><a href="http://redis.io/commands/acl-whoami">ACL WHOAMI Command</a></b>
   * Returns the username used to authenticate the current connection.
   *
   * @return CompletableFuture that completes with the username used for the current connection
   */
  CompletableFuture<String> aclWhoAmI();

  /**
   * Async version of <b><a href="http://redis.io/commands/acl-genpass">ACL GENPASS Command</a></b>
   * Generate a random password
   *
   * @return CompletableFuture that completes with a random password
   */
  CompletableFuture<String> aclGenPass();

  /**
   * Async version of <b><a href="http://redis.io/commands/acl-genpass">ACL GENPASS Command</a></b>
   * Generate a random password
   *
   * @param bits the number of output bits
   * @return CompletableFuture that completes with a random password
   */
  CompletableFuture<String> aclGenPass(int bits);

  /**
   * Async version of <b><a href="http://redis.io/commands/acl-list">ACL LIST Command</a></b>
   * Returns the currently active ACL rules on the Redis Server
   *
   * @return CompletableFuture that completes with an array of ACL rules
   */
  CompletableFuture<List<String>> aclList();

  /**
   * Async version of <b><a href="http://redis.io/commands/acl-users">ACL USERS Command</a></b>
   * Shows a list of all usernames currently configured with access control lists (ACL).
   *
   * @return CompletableFuture that completes with list of users
   */
  CompletableFuture<List<String>> aclUsers();

  /**
   * Async version of ACL GETUSER Command
   * The command returns all the rules defined for an existing ACL user.
   * @param name username
   * @return CompletableFuture that completes with a list of ACL rule definitions for the user
   */
  CompletableFuture<AccessControlUser> aclGetUser(String name);

  /**
   * Async version of <b><a href="http://redis.io/commands/acl-setuser">ACL SETUSER Command</a></b>
   * Create an ACL for the specified user with the default rules.
   *
   * @param name user who receives an acl
   * @return CompletableFuture that completes with OK on success
   */
  CompletableFuture<String> aclSetUser(String name);

  /**
   * Async version of <b><a href="http://redis.io/commands/acl-setuser">ACL SETUSER Command</a></b>
   * Create an ACL for the specified user, while specifying the rules.
   *
   * @param name user who receives an acl
   * @param rules the acl rules for the specified user
   * @return CompletableFuture that completes with OK on success
   */
  CompletableFuture<String> aclSetUser(String name, String... rules);

  /**
   * Async version of <b><a href="http://redis.io/commands/acl-deluser">ACL DELUSER Command</a></b>
   * Delete the specified user, from the ACL.
   *
   * @param names The usernames to delete
   * @return CompletableFuture that completes with the number of users deleted
   */
  CompletableFuture<Long> aclDelUser(String... names);

  /**
   * Async version of <b><a href="http://redis.io/commands/acl-cat">ACL CAT Command</a></b>
   * Show the available ACL categories.
   *
   * @return CompletableFuture that completes with the available ACL categories
   */
  CompletableFuture<List<String>> aclCat();

  /**
   * Async version of <b><a href="http://redis.io/commands/acl-cat">ACL CAT Command</a></b>
   * Show the available ACLs for a given category.
   *
   * @param category The category for which to list available ACLs
   * @return CompletableFuture that completes with the available ACL categories
   */
  CompletableFuture<List<String>> aclCat(String category);

  /**
   * Async version of <b><a href="http://redis.io/commands/acl-log">ACL LOG Command</a></b>
   * Shows the recent ACL security events.
   *
   * @return CompletableFuture that completes with the list of recent security events
   */
  CompletableFuture<List<AccessControlLogEntry>> aclLog();

  /**
   * Async version of <b><a href="http://redis.io/commands/acl-log">ACL LOG Command</a></b>
   * Shows the recent limit ACL security events.
   *
   * @param limit The number of results to return
   * @return CompletableFuture that completes with the list of recent security events
   */
  CompletableFuture<List<AccessControlLogEntry>> aclLog(int limit);

  /**
   * Async version of <b><a href="http://redis.io/commands/acl-log">ACL LOG RESET Command</a></b>
   * Reset the script event log
   *
   * @return CompletableFuture that completes with OK on success
   */
  CompletableFuture<String> aclLogReset();

  /**
   * Async version of <b><a href="http://redis.io/commands/acl-load">ACL LOAD Command</a></b>
   * This function tells Redis to reload its external ACL rules,
   * when Redis is configured with an external ACL file
   *
   * @return CompletableFuture that completes with OK or error text
   */
  CompletableFuture<String> aclLoad();

  /**
   * Async version of <b><a href="http://redis.io/commands/acl-save">ACL SAVE Command</a></b>
   * Save the currently defined in-memory ACL to disk.
   *
   * @return CompletableFuture that completes with OK on success
   */
  CompletableFuture<String> aclSave();

  /**
   * Async version of ACL DRYRUN Command
   * Simulate the execution of a command by a user
   *
   * @param username The user to simulate
   * @param command The command to simulate
   * @param args The command arguments
   * @return CompletableFuture that completes with the simulation result
   */
  CompletableFuture<String> aclDryRun(String username, String command, String... args);

  /**
   * Async version of ACL DRYRUN Command
   * Simulate the execution of a command by a user
   *
   * @param username The user to simulate
   * @param commandArgs The command and its arguments
   * @return CompletableFuture that completes with the simulation result
   */
  CompletableFuture<String> aclDryRun(String username, CommandArguments commandArgs);
} 