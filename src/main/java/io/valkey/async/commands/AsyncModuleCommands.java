package io.valkey.async.commands;

import io.valkey.Module;
import io.valkey.params.ModuleLoadExParams;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * This interface provides async commands for Redis module operations.
 */
public interface AsyncModuleCommands {

  /**
   * Async version of MODULE LOAD command.
   * Load and initialize the Redis module from the dynamic library specified by the path argument.
   *
   * @param path should be the absolute path of the library, including the full filename
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> moduleLoad(String path);

  /**
   * Async version of MODULE LOAD command.
   * Load and initialize the Redis module from the dynamic library specified by the path argument.
   *
   * @param path should be the absolute path of the library, including the full filename
   * @param args additional arguments are passed unmodified to the module
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> moduleLoad(String path, String... args);

  /**
   * Async version of MODULE LOADEX command.
   * Loads a module from a dynamic library at runtime with configuration directives.
   * <p>
   * This is an extended version of the MODULE LOAD command.
   * <p>
   * It loads and initializes the Redis module from the dynamic library specified by the path
   * argument. The path should be the absolute path of the library, including the full filename.
   * <p>
   * You can use the optional CONFIG argument to provide the module with configuration directives.
   * Any additional arguments that follow the ARGS keyword are passed unmodified to the module.
   *
   * @param path should be the absolute path of the library, including the full filename
   * @param params as in description
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> moduleLoadEx(String path, ModuleLoadExParams params);

  /**
   * Async version of MODULE UNLOAD command.
   * Unload the module specified by name. Note that the module's name is reported by the
   * {@link AsyncModuleCommands#moduleList() MODULE LIST} command, and may differ from the dynamic
   * library's filename.
   *
   * @param name the name of the module to unload
   * @return CompletableFuture that completes with OK if successful
   */
  CompletableFuture<String> moduleUnload(String name);

  /**
   * Async version of MODULE LIST command.
   * Return information about the modules loaded to the server.
   *
   * @return CompletableFuture that completes with list of {@link Module}
   */
  CompletableFuture<List<Module>> moduleList();
} 