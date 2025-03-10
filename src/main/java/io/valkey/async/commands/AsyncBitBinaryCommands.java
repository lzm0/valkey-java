package io.valkey.async.commands;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import io.valkey.args.BitCountOption;
import io.valkey.args.BitOP;
import io.valkey.params.BitPosParams;

/**
 * This interface provides async commands for Redis bit operations with binary data.
 */
public interface AsyncBitBinaryCommands {

  /**
   * Binary version of SETBIT command.
   * @param key the key
   * @param offset bit offset
   * @param value bit value (true = 1, false = 0)
   * @return CompletableFuture that completes with the original bit value
   */
  CompletableFuture<Boolean> setbit(byte[] key, long offset, boolean value);

  /**
   * Binary version of GETBIT command.
   * @param key the key
   * @param offset bit offset
   * @return CompletableFuture that completes with the bit value
   */
  CompletableFuture<Boolean> getbit(byte[] key, long offset);

  /**
   * Binary version of BITCOUNT command.
   * @param key the key
   * @return CompletableFuture that completes with number of bits set to 1
   */
  CompletableFuture<Long> bitcount(byte[] key);

  /**
   * Binary version of BITCOUNT command.
   * @param key the key
   * @param start start byte index
   * @param end end byte index
   * @return CompletableFuture that completes with number of bits set to 1
   */
  CompletableFuture<Long> bitcount(byte[] key, long start, long end);

  /**
   * Binary version of BITCOUNT command.
   * @param key the key
   * @param start start index
   * @param end end index
   * @param option BYTE or BIT counting mode
   * @return CompletableFuture that completes with number of bits set to 1
   */
  CompletableFuture<Long> bitcount(byte[] key, long start, long end, BitCountOption option);

  /**
   * Binary version of BITPOS command.
   * @param key the key
   * @param value bit value to look for
   * @return CompletableFuture that completes with position of the first matching bit
   */
  CompletableFuture<Long> bitpos(byte[] key, boolean value);

  /**
   * Binary version of BITPOS command.
   * @param key the key
   * @param value bit value to look for
   * @param params additional parameters
   * @return CompletableFuture that completes with position of the first matching bit
   */
  CompletableFuture<Long> bitpos(byte[] key, boolean value, BitPosParams params);

  /**
   * Binary version of BITFIELD command.
   * @param key the key
   * @param arguments command arguments
   * @return CompletableFuture that completes with list of results
   */
  CompletableFuture<List<Long>> bitfield(byte[] key, byte[]... arguments);

  /**
   * Binary version of BITFIELD readonly command.
   * @param key the key
   * @param arguments command arguments
   * @return CompletableFuture that completes with list of results
   */
  CompletableFuture<List<Long>> bitfieldReadonly(byte[] key, byte[]... arguments);

  /**
   * Binary version of BITOP command.
   * @param op bitwise operation (AND, OR, XOR, NOT)
   * @param destKey destination key
   * @param srcKeys source keys
   * @return CompletableFuture that completes with size of string stored in destKey
   */
  CompletableFuture<Long> bitop(BitOP op, byte[] destKey, byte[]... srcKeys);
} 