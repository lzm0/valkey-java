package io.valkey.async.commands;

import io.valkey.args.FlushMode;
import io.valkey.util.KeyValue;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * This interface provides async commands for Redis sample binary keyed operations.
 */
public interface AsyncSampleBinaryKeyedCommands {

  /**
   * Async version of WAIT command.
   * @param sampleKey sample key
   * @param replicas number of replicas
   * @param timeout timeout in milliseconds
   * @return CompletableFuture that completes with number of replicas that acknowledged the write
   */
  CompletableFuture<Long> waitReplicas(byte[] sampleKey, int replicas, long timeout);

  /**
   * Async version of WAITAOF command.
   * @param sampleKey sample key
   * @param numLocal number of local replicas
   * @param numReplicas number of replicas
   * @param timeout timeout in milliseconds
   * @return CompletableFuture that completes with KeyValue containing number of local and replica writes acknowledged
   */
  CompletableFuture<KeyValue<Long, Long>> waitAOF(byte[] sampleKey, long numLocal, long numReplicas, long timeout);

  /**
   * Async version of EVAL command.
   * @param script Lua script
   * @param sampleKey sample key
   * @return CompletableFuture that completes with the result of script evaluation
   */
  CompletableFuture<Object> eval(byte[] script, byte[] sampleKey);

  /**
   * Async version of EVALSHA command.
   * @param sha1 SHA1 digest of the script
   * @param sampleKey sample key
   * @return CompletableFuture that completes with the result of script evaluation
   */
  CompletableFuture<Object> evalsha(byte[] sha1, byte[] sampleKey);

  /**
   * Async version of SCRIPT EXISTS command.
   * @param sha1 SHA1 digest of the script
   * @param sampleKey sample key
   * @return CompletableFuture that completes with true if script exists
   */
  CompletableFuture<Boolean> scriptExists(byte[] sha1, byte[] sampleKey);

  /**
   * Async version of SCRIPT EXISTS command.
   * @param sampleKey sample key
   * @param sha1s SHA1 digests of the scripts
   * @return CompletableFuture that completes with list of booleans indicating script existence
   */
  CompletableFuture<List<Boolean>> scriptExists(byte[] sampleKey, byte[]... sha1s);

  /**
   * Async version of SCRIPT LOAD command.
   * @param script Lua script
   * @param sampleKey sample key
   * @return CompletableFuture that completes with SHA1 digest of the script
   */
  CompletableFuture<byte[]> scriptLoad(byte[] script, byte[] sampleKey);

  /**
   * Async version of SCRIPT FLUSH command.
   * @param sampleKey sample key
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> scriptFlush(byte[] sampleKey);

  /**
   * Async version of SCRIPT FLUSH command.
   * @param sampleKey sample key
   * @param flushMode flush mode
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> scriptFlush(byte[] sampleKey, FlushMode flushMode);

  /**
   * Async version of SCRIPT KILL command.
   * @param sampleKey sample key
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> scriptKill(byte[] sampleKey);
} 