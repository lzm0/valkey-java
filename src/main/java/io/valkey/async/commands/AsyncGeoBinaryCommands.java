package io.valkey.async.commands;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import io.valkey.GeoCoordinate;
import io.valkey.args.GeoUnit;
import io.valkey.params.GeoAddParams;
import io.valkey.params.GeoRadiusParam;
import io.valkey.params.GeoRadiusStoreParam;
import io.valkey.params.GeoSearchParam;
import io.valkey.resps.GeoRadiusResponse;

/**
 * This interface provides async commands for Redis Geo operations with binary data.
 */
public interface AsyncGeoBinaryCommands {

  /**
   * Binary version of GEOADD command.
   * @param key the key
   * @param longitude longitude
   * @param latitude latitude
   * @param member member name
   * @return CompletableFuture that completes with number of elements added
   */
  CompletableFuture<Long> geoadd(byte[] key, double longitude, double latitude, byte[] member);

  /**
   * Binary version of GEOADD command.
   * @param key the key
   * @param memberCoordinateMap Members names with their geo coordinates
   * @return CompletableFuture that completes with number of elements added
   */
  CompletableFuture<Long> geoadd(byte[] key, Map<byte[], GeoCoordinate> memberCoordinateMap);

  /**
   * Binary version of GEOADD command with parameters.
   * @param key the key
   * @param params Additional options
   * @param memberCoordinateMap Members names with their geo coordinates
   * @return CompletableFuture that completes with number of elements added
   */
  CompletableFuture<Long> geoadd(byte[] key, GeoAddParams params, Map<byte[], GeoCoordinate> memberCoordinateMap);

  /**
   * Binary version of GEODIST command.
   * @param key the key
   * @param member1 first member
   * @param member2 second member
   * @return CompletableFuture that completes with distance as double
   */
  CompletableFuture<Double> geodist(byte[] key, byte[] member1, byte[] member2);

  /**
   * Binary version of GEODIST command with unit.
   * @param key the key
   * @param member1 first member
   * @param member2 second member
   * @param unit distance unit
   * @return CompletableFuture that completes with distance as double
   */
  CompletableFuture<Double> geodist(byte[] key, byte[] member1, byte[] member2, GeoUnit unit);

  /**
   * Binary version of GEOHASH command.
   * @param key the key
   * @param members member names
   * @return CompletableFuture that completes with list of geohash strings
   */
  CompletableFuture<List<byte[]>> geohash(byte[] key, byte[]... members);

  /**
   * Binary version of GEOPOS command.
   * @param key the key
   * @param members member names
   * @return CompletableFuture that completes with list of coordinates
   */
  CompletableFuture<List<GeoCoordinate>> geopos(byte[] key, byte[]... members);

  /**
   * Binary version of GEORADIUS command.
   * @param key the key
   * @param longitude center longitude
   * @param latitude center latitude
   * @param radius radius
   * @param unit distance unit
   * @return CompletableFuture that completes with list of matching members
   */
  CompletableFuture<List<GeoRadiusResponse>> georadius(byte[] key, double longitude, double latitude,
      double radius, GeoUnit unit);

  /**
   * Binary version of GEORADIUS readonly command.
   * @param key the key
   * @param longitude center longitude
   * @param latitude center latitude
   * @param radius radius
   * @param unit distance unit
   * @return CompletableFuture that completes with list of matching members
   */
  CompletableFuture<List<GeoRadiusResponse>> georadiusReadonly(byte[] key, double longitude,
      double latitude, double radius, GeoUnit unit);

  /**
   * Binary version of GEORADIUS command with parameters.
   * @param key the key
   * @param longitude center longitude
   * @param latitude center latitude
   * @param radius radius
   * @param unit distance unit
   * @param param additional parameters
   * @return CompletableFuture that completes with list of matching members
   */
  CompletableFuture<List<GeoRadiusResponse>> georadius(byte[] key, double longitude, double latitude,
      double radius, GeoUnit unit, GeoRadiusParam param);

  /**
   * Binary version of GEORADIUS readonly command with parameters.
   * @param key the key
   * @param longitude center longitude
   * @param latitude center latitude
   * @param radius radius
   * @param unit distance unit
   * @param param additional parameters
   * @return CompletableFuture that completes with list of matching members
   */
  CompletableFuture<List<GeoRadiusResponse>> georadiusReadonly(byte[] key, double longitude,
      double latitude, double radius, GeoUnit unit, GeoRadiusParam param);

  /**
   * Binary version of GEORADIUSBYMEMBER command.
   * @param key the key
   * @param member center member
   * @param radius radius
   * @param unit distance unit
   * @return CompletableFuture that completes with list of matching members
   */
  CompletableFuture<List<GeoRadiusResponse>> georadiusByMember(byte[] key, byte[] member,
      double radius, GeoUnit unit);

  /**
   * Binary version of GEORADIUSBYMEMBER readonly command.
   * @param key the key
   * @param member center member
   * @param radius radius
   * @param unit distance unit
   * @return CompletableFuture that completes with list of matching members
   */
  CompletableFuture<List<GeoRadiusResponse>> georadiusByMemberReadonly(byte[] key, byte[] member,
      double radius, GeoUnit unit);

  /**
   * Binary version of GEORADIUSBYMEMBER command with parameters.
   * @param key the key
   * @param member center member
   * @param radius radius
   * @param unit distance unit
   * @param param additional parameters
   * @return CompletableFuture that completes with list of matching members
   */
  CompletableFuture<List<GeoRadiusResponse>> georadiusByMember(byte[] key, byte[] member,
      double radius, GeoUnit unit, GeoRadiusParam param);

  /**
   * Binary version of GEORADIUSBYMEMBER readonly command with parameters.
   * @param key the key
   * @param member center member
   * @param radius radius
   * @param unit distance unit
   * @param param additional parameters
   * @return CompletableFuture that completes with list of matching members
   */
  CompletableFuture<List<GeoRadiusResponse>> georadiusByMemberReadonly(byte[] key, byte[] member,
      double radius, GeoUnit unit, GeoRadiusParam param);

  /**
   * Binary version of GEORADIUS store command.
   * @param key the key
   * @param longitude center longitude
   * @param latitude center latitude
   * @param radius radius
   * @param unit distance unit
   * @param param radius parameters
   * @param storeParam store parameters
   * @return CompletableFuture that completes with number of stored items
   */
  CompletableFuture<Long> georadiusStore(byte[] key, double longitude, double latitude,
      double radius, GeoUnit unit, GeoRadiusParam param, GeoRadiusStoreParam storeParam);

  /**
   * Binary version of GEORADIUSBYMEMBER store command.
   * @param key the key
   * @param member center member
   * @param radius radius
   * @param unit distance unit
   * @param param radius parameters
   * @param storeParam store parameters
   * @return CompletableFuture that completes with number of stored items
   */
  CompletableFuture<Long> georadiusByMemberStore(byte[] key, byte[] member, double radius,
      GeoUnit unit, GeoRadiusParam param, GeoRadiusStoreParam storeParam);

  /**
   * Binary version of GEOSEARCH command.
   * @param key the key
   * @param member center member
   * @param radius radius
   * @param unit distance unit
   * @return CompletableFuture that completes with list of matching members
   */
  CompletableFuture<List<GeoRadiusResponse>> geosearch(byte[] key, byte[] member, double radius,
      GeoUnit unit);

  /**
   * Binary version of GEOSEARCH command.
   * @param key the key
   * @param coord center coordinate
   * @param radius radius
   * @param unit distance unit
   * @return CompletableFuture that completes with list of matching members
   */
  CompletableFuture<List<GeoRadiusResponse>> geosearch(byte[] key, GeoCoordinate coord,
      double radius, GeoUnit unit);

  /**
   * Binary version of GEOSEARCH command.
   * @param key the key
   * @param member center member
   * @param width box width
   * @param height box height
   * @param unit distance unit
   * @return CompletableFuture that completes with list of matching members
   */
  CompletableFuture<List<GeoRadiusResponse>> geosearch(byte[] key, byte[] member, double width,
      double height, GeoUnit unit);

  /**
   * Binary version of GEOSEARCH command.
   * @param key the key
   * @param coord center coordinate
   * @param width box width
   * @param height box height
   * @param unit distance unit
   * @return CompletableFuture that completes with list of matching members
   */
  CompletableFuture<List<GeoRadiusResponse>> geosearch(byte[] key, GeoCoordinate coord,
      double width, double height, GeoUnit unit);

  /**
   * Binary version of GEOSEARCH command.
   * @param key the key
   * @param params search parameters
   * @return CompletableFuture that completes with list of matching members
   */
  CompletableFuture<List<GeoRadiusResponse>> geosearch(byte[] key, GeoSearchParam params);

  /**
   * Binary version of GEOSEARCHSTORE command.
   * @param dest destination key
   * @param src source key
   * @param member center member
   * @param radius radius
   * @param unit distance unit
   * @return CompletableFuture that completes with number of stored items
   */
  CompletableFuture<Long> geosearchStore(byte[] dest, byte[] src, byte[] member, double radius,
      GeoUnit unit);

  /**
   * Binary version of GEOSEARCHSTORE command.
   * @param dest destination key
   * @param src source key
   * @param coord center coordinate
   * @param radius radius
   * @param unit distance unit
   * @return CompletableFuture that completes with number of stored items
   */
  CompletableFuture<Long> geosearchStore(byte[] dest, byte[] src, GeoCoordinate coord,
      double radius, GeoUnit unit);

  /**
   * Binary version of GEOSEARCHSTORE command.
   * @param dest destination key
   * @param src source key
   * @param member center member
   * @param width box width
   * @param height box height
   * @param unit distance unit
   * @return CompletableFuture that completes with number of stored items
   */
  CompletableFuture<Long> geosearchStore(byte[] dest, byte[] src, byte[] member, double width,
      double height, GeoUnit unit);

  /**
   * Binary version of GEOSEARCHSTORE command.
   * @param dest destination key
   * @param src source key
   * @param coord center coordinate
   * @param width box width
   * @param height box height
   * @param unit distance unit
   * @return CompletableFuture that completes with number of stored items
   */
  CompletableFuture<Long> geosearchStore(byte[] dest, byte[] src, GeoCoordinate coord,
      double width, double height, GeoUnit unit);

  /**
   * Binary version of GEOSEARCHSTORE command.
   * @param dest destination key
   * @param src source key
   * @param params search parameters
   * @return CompletableFuture that completes with number of stored items
   */
  CompletableFuture<Long> geosearchStore(byte[] dest, byte[] src, GeoSearchParam params);

  /**
   * Binary version of GEOSEARCHSTORE command with distances.
   * @param dest destination key
   * @param src source key
   * @param params search parameters
   * @return CompletableFuture that completes with number of stored items
   */
  CompletableFuture<Long> geosearchStoreStoreDist(byte[] dest, byte[] src, GeoSearchParam params);
} 