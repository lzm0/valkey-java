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
 * This interface provides async commands for Redis Geo operations.
 */
public interface AsyncGeoCommands {

  /**
   * Async version of <b><a href="http://redis.io/commands/geoadd">GEOADD Command</a></b>
   * Adds the specified geospatial item (longitude, latitude, member) to the specified key.
   * @param key the key
   * @param longitude longitude
   * @param latitude latitude
   * @param member member name
   * @return CompletableFuture that completes with the number of elements added
   */
  CompletableFuture<Long> geoadd(String key, double longitude, double latitude, String member);

  /**
   * Async version of <b><a href="http://redis.io/commands/geoadd">GEOADD Command</a></b>
   * Adds the specified geospatial items to the specified key.
   * @param key the key
   * @param memberCoordinateMap Members names with their geo coordinates
   * @return CompletableFuture that completes with the number of elements added
   */
  CompletableFuture<Long> geoadd(String key, Map<String, GeoCoordinate> memberCoordinateMap);

  /**
   * Async version of <b><a href="http://redis.io/commands/geoadd">GEOADD Command</a></b>
   * Adds the specified geospatial items with parameters.
   * @param key the key
   * @param params Additional options
   * @param memberCoordinateMap Members names with their geo coordinates
   * @return CompletableFuture that completes with the number of elements added
   */
  CompletableFuture<Long> geoadd(String key, GeoAddParams params, Map<String, GeoCoordinate> memberCoordinateMap);

  /**
   * Async version of <b><a href="http://redis.io/commands/geodist">GEODIST Command</a></b>
   * Returns the distance between two members.
   * @param key the key
   * @param member1 first member
   * @param member2 second member
   * @return CompletableFuture that completes with the distance as a double
   */
  CompletableFuture<Double> geodist(String key, String member1, String member2);

  /**
   * Async version of <b><a href="http://redis.io/commands/geodist">GEODIST Command</a></b>
   * Returns the distance between two members in specified unit.
   * @param key the key
   * @param member1 first member
   * @param member2 second member
   * @param unit distance unit
   * @return CompletableFuture that completes with the distance as a double
   */
  CompletableFuture<Double> geodist(String key, String member1, String member2, GeoUnit unit);

  /**
   * Async version of <b><a href="http://redis.io/commands/geohash">GEOHASH Command</a></b>
   * Returns Geohash strings for members.
   * @param key the key
   * @param members member names
   * @return CompletableFuture that completes with list of Geohash strings
   */
  CompletableFuture<List<String>> geohash(String key, String... members);

  /**
   * Async version of <b><a href="http://redis.io/commands/geopos">GEOPOS Command</a></b>
   * Returns the positions of members.
   * @param key the key
   * @param members member names
   * @return CompletableFuture that completes with list of coordinates
   */
  CompletableFuture<List<GeoCoordinate>> geopos(String key, String... members);

  /**
   * Async version of <b><a href="http://redis.io/commands/georadius">GEORADIUS Command</a></b>
   * Query members within radius.
   * @param key the key
   * @param longitude center longitude
   * @param latitude center latitude
   * @param radius radius
   * @param unit distance unit
   * @return CompletableFuture that completes with list of matching members
   */
  CompletableFuture<List<GeoRadiusResponse>> georadius(String key, double longitude, double latitude,
      double radius, GeoUnit unit);

  /**
   * Async version of GEORADIUS readonly command.
   * @param key the key
   * @param longitude center longitude
   * @param latitude center latitude
   * @param radius radius
   * @param unit distance unit
   * @return CompletableFuture that completes with list of matching members
   */
  CompletableFuture<List<GeoRadiusResponse>> georadiusReadonly(String key, double longitude,
      double latitude, double radius, GeoUnit unit);

  /**
   * Async version of <b><a href="http://redis.io/commands/georadius">GEORADIUS Command</a></b>
   * Query members within radius with parameters.
   * @param key the key
   * @param longitude center longitude
   * @param latitude center latitude
   * @param radius radius
   * @param unit distance unit
   * @param param additional parameters
   * @return CompletableFuture that completes with list of matching members
   */
  CompletableFuture<List<GeoRadiusResponse>> georadius(String key, double longitude, double latitude,
      double radius, GeoUnit unit, GeoRadiusParam param);

  /**
   * Async version of GEORADIUS readonly command with parameters.
   * @param key the key
   * @param longitude center longitude
   * @param latitude center latitude
   * @param radius radius
   * @param unit distance unit
   * @param param additional parameters
   * @return CompletableFuture that completes with list of matching members
   */
  CompletableFuture<List<GeoRadiusResponse>> georadiusReadonly(String key, double longitude,
      double latitude, double radius, GeoUnit unit, GeoRadiusParam param);

  /**
   * Async version of <b><a href="http://redis.io/commands/georadiusbymember">GEORADIUSBYMEMBER Command</a></b>
   * Query members within radius from member.
   * @param key the key
   * @param member center member
   * @param radius radius
   * @param unit distance unit
   * @return CompletableFuture that completes with list of matching members
   */
  CompletableFuture<List<GeoRadiusResponse>> georadiusByMember(String key, String member,
      double radius, GeoUnit unit);

  /**
   * Async version of GEORADIUSBYMEMBER readonly command.
   * @param key the key
   * @param member center member
   * @param radius radius
   * @param unit distance unit
   * @return CompletableFuture that completes with list of matching members
   */
  CompletableFuture<List<GeoRadiusResponse>> georadiusByMemberReadonly(String key, String member,
      double radius, GeoUnit unit);

  /**
   * Async version of <b><a href="http://redis.io/commands/georadiusbymember">GEORADIUSBYMEMBER Command</a></b>
   * Query members within radius from member with parameters.
   * @param key the key
   * @param member center member
   * @param radius radius
   * @param unit distance unit
   * @param param additional parameters
   * @return CompletableFuture that completes with list of matching members
   */
  CompletableFuture<List<GeoRadiusResponse>> georadiusByMember(String key, String member,
      double radius, GeoUnit unit, GeoRadiusParam param);

  /**
   * Async version of GEORADIUSBYMEMBER readonly command with parameters.
   * @param key the key
   * @param member center member
   * @param radius radius
   * @param unit distance unit
   * @param param additional parameters
   * @return CompletableFuture that completes with list of matching members
   */
  CompletableFuture<List<GeoRadiusResponse>> georadiusByMemberReadonly(String key, String member,
      double radius, GeoUnit unit, GeoRadiusParam param);

  /**
   * Async version of GEORADIUS store command.
   * @param key the key
   * @param longitude center longitude
   * @param latitude center latitude
   * @param radius radius
   * @param unit distance unit
   * @param param radius parameters
   * @param storeParam store parameters
   * @return CompletableFuture that completes with number of stored items
   */
  CompletableFuture<Long> georadiusStore(String key, double longitude, double latitude,
      double radius, GeoUnit unit, GeoRadiusParam param, GeoRadiusStoreParam storeParam);

  /**
   * Async version of GEORADIUSBYMEMBER store command.
   * @param key the key
   * @param member center member
   * @param radius radius
   * @param unit distance unit
   * @param param radius parameters
   * @param storeParam store parameters
   * @return CompletableFuture that completes with number of stored items
   */
  CompletableFuture<Long> georadiusByMemberStore(String key, String member, double radius,
      GeoUnit unit, GeoRadiusParam param, GeoRadiusStoreParam storeParam);

  /**
   * Async version of <b><a href="http://redis.io/commands/geosearch">GEOSEARCH Command</a></b>
   * Query members within radius from member.
   * @param key the key
   * @param member center member
   * @param radius radius
   * @param unit distance unit
   * @return CompletableFuture that completes with list of matching members
   */
  CompletableFuture<List<GeoRadiusResponse>> geosearch(String key, String member, double radius,
      GeoUnit unit);

  /**
   * Async version of <b><a href="http://redis.io/commands/geosearch">GEOSEARCH Command</a></b>
   * Query members within radius from coordinate.
   * @param key the key
   * @param coord center coordinate
   * @param radius radius
   * @param unit distance unit
   * @return CompletableFuture that completes with list of matching members
   */
  CompletableFuture<List<GeoRadiusResponse>> geosearch(String key, GeoCoordinate coord,
      double radius, GeoUnit unit);

  /**
   * Async version of <b><a href="http://redis.io/commands/geosearch">GEOSEARCH Command</a></b>
   * Query members within box from member.
   * @param key the key
   * @param member center member
   * @param width box width
   * @param height box height
   * @param unit distance unit
   * @return CompletableFuture that completes with list of matching members
   */
  CompletableFuture<List<GeoRadiusResponse>> geosearch(String key, String member, double width,
      double height, GeoUnit unit);

  /**
   * Async version of <b><a href="http://redis.io/commands/geosearch">GEOSEARCH Command</a></b>
   * Query members within box from coordinate.
   * @param key the key
   * @param coord center coordinate
   * @param width box width
   * @param height box height
   * @param unit distance unit
   * @return CompletableFuture that completes with list of matching members
   */
  CompletableFuture<List<GeoRadiusResponse>> geosearch(String key, GeoCoordinate coord,
      double width, double height, GeoUnit unit);

  /**
   * Async version of <b><a href="http://redis.io/commands/geosearch">GEOSEARCH Command</a></b>
   * Query members with parameters.
   * @param key the key
   * @param params search parameters
   * @return CompletableFuture that completes with list of matching members
   */
  CompletableFuture<List<GeoRadiusResponse>> geosearch(String key, GeoSearchParam params);

  /**
   * Async version of <b><a href="http://redis.io/commands/geosearchstore">GEOSEARCHSTORE Command</a></b>
   * Store results of geosearch by member.
   * @param dest destination key
   * @param src source key
   * @param member center member
   * @param radius radius
   * @param unit distance unit
   * @return CompletableFuture that completes with number of stored items
   */
  CompletableFuture<Long> geosearchStore(String dest, String src, String member, double radius,
      GeoUnit unit);

  /**
   * Async version of <b><a href="http://redis.io/commands/geosearchstore">GEOSEARCHSTORE Command</a></b>
   * Store results of geosearch by coordinate.
   * @param dest destination key
   * @param src source key
   * @param coord center coordinate
   * @param radius radius
   * @param unit distance unit
   * @return CompletableFuture that completes with number of stored items
   */
  CompletableFuture<Long> geosearchStore(String dest, String src, GeoCoordinate coord,
      double radius, GeoUnit unit);

  /**
   * Async version of <b><a href="http://redis.io/commands/geosearchstore">GEOSEARCHSTORE Command</a></b>
   * Store results of geosearch by member box.
   * @param dest destination key
   * @param src source key
   * @param member center member
   * @param width box width
   * @param height box height
   * @param unit distance unit
   * @return CompletableFuture that completes with number of stored items
   */
  CompletableFuture<Long> geosearchStore(String dest, String src, String member, double width,
      double height, GeoUnit unit);

  /**
   * Async version of <b><a href="http://redis.io/commands/geosearchstore">GEOSEARCHSTORE Command</a></b>
   * Store results of geosearch by coordinate box.
   * @param dest destination key
   * @param src source key
   * @param coord center coordinate
   * @param width box width
   * @param height box height
   * @param unit distance unit
   * @return CompletableFuture that completes with number of stored items
   */
  CompletableFuture<Long> geosearchStore(String dest, String src, GeoCoordinate coord,
      double width, double height, GeoUnit unit);

  /**
   * Async version of <b><a href="http://redis.io/commands/geosearchstore">GEOSEARCHSTORE Command</a></b>
   * Store results of geosearch with parameters.
   * @param dest destination key
   * @param src source key
   * @param params search parameters
   * @return CompletableFuture that completes with number of stored items
   */
  CompletableFuture<Long> geosearchStore(String dest, String src, GeoSearchParam params);

  /**
   * Async version of <b><a href="http://redis.io/commands/geosearchstore">GEOSEARCHSTORE Command</a></b>
   * Store results of geosearch with distances.
   * @param dest destination key
   * @param src source key
   * @param params search parameters
   * @return CompletableFuture that completes with number of stored items
   */
  CompletableFuture<Long> geosearchStoreStoreDist(String dest, String src, GeoSearchParam params);
} 