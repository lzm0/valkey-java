package io.valkey.async.commands;

/**
 * This interface aggregates all Redis binary async commands.
 */
public interface AsyncJedisBinaryCommands extends AsyncKeyBinaryCommands, AsyncStringBinaryCommands,
    AsyncListBinaryCommands, AsyncHashBinaryCommands, AsyncSetBinaryCommands, AsyncSortedSetBinaryCommands,
    AsyncGeoBinaryCommands, AsyncHyperLogLogBinaryCommands, AsyncStreamBinaryCommands, AsyncScriptingKeyBinaryCommands,
    AsyncFunctionBinaryCommands {
} 