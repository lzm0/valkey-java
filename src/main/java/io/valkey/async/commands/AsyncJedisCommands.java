package io.valkey.async.commands;

/**
 * This interface aggregates all Redis async commands.
 */
public interface AsyncJedisCommands extends AsyncKeyCommands, AsyncStringCommands, AsyncListCommands, AsyncHashCommands,
    AsyncSetCommands, AsyncSortedSetCommands, AsyncGeoCommands, AsyncHyperLogLogCommands, AsyncStreamCommands,
    AsyncScriptingKeyCommands, AsyncFunctionCommands {
} 