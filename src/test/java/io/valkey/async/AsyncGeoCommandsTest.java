package io.valkey.async;

import io.valkey.args.GeoUnit;
import io.valkey.params.GeoSearchParam;
import io.valkey.GeoCoordinate;
import io.valkey.resps.GeoRadiusResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

public class AsyncGeoCommandsTest extends AsyncJedisBasicTest {

    private static final String KEY = "Sicily";

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        jedis.del(KEY).get();
    }

    @Test
    public void testGeoAdd() throws ExecutionException, InterruptedException {
        Map<String, GeoCoordinate> memberCoordinateMap = new HashMap<>();
        memberCoordinateMap.put("Palermo", new GeoCoordinate(13.361389, 38.115556));
        memberCoordinateMap.put("Catania", new GeoCoordinate(15.087269, 37.502669));

        Long result = jedis.geoadd(KEY, memberCoordinateMap).get();
        assertEquals(Long.valueOf(2), result);

        // Test single add
        Long singleResult = jedis.geoadd(KEY, 13.583333, 37.316667, "Agrigento").get();
        assertEquals(Long.valueOf(1), singleResult);
    }

    @Test
    public void testGeoDist() throws ExecutionException, InterruptedException {
        setupSicilyPoints();

        Double dist = jedis.geodist(KEY, "Palermo", "Catania").get();
        assertNotNull(dist);
        assertTrue(dist > 0);

        // Test with unit
        Double distKm = jedis.geodist(KEY, "Palermo", "Catania", GeoUnit.KM).get();
        assertNotNull(distKm);
        assertTrue(distKm > 0);
    }

    @Test
    public void testGeoHash() throws ExecutionException, InterruptedException {
        setupSicilyPoints();

        List<String> hashes = jedis.geohash(KEY, "Palermo", "Catania").get();
        assertEquals(2, hashes.size());
        assertNotNull(hashes.get(0));
        assertNotNull(hashes.get(1));
    }

    @Test
    public void testGeoPos() throws ExecutionException, InterruptedException {
        setupSicilyPoints();

        List<GeoCoordinate> positions = jedis.geopos(KEY, "Palermo", "Catania").get();
        assertEquals(2, positions.size());
        
        GeoCoordinate palermo = positions.get(0);
        assertNotNull(palermo);
        assertEquals(13.361389, palermo.getLongitude(), 0.1);
        assertEquals(38.115556, palermo.getLatitude(), 0.1);

        GeoCoordinate catania = positions.get(1);
        assertNotNull(catania);
        assertEquals(15.087269, catania.getLongitude(), 0.1);
        assertEquals(37.502669, catania.getLatitude(), 0.1);
    }

    @Test
    public void testGeoRadius() throws ExecutionException, InterruptedException {
        setupSicilyPoints();

        // Search within 100km of Palermo
        List<GeoRadiusResponse> responses = jedis.georadius(KEY, 13.361389, 38.115556, 100, GeoUnit.KM).get();
        assertNotNull(responses);
        assertTrue(responses.size() >= 1);

        // Test with full parameters
        GeoSearchParam param = GeoSearchParam.geoSearchParam()
            .fromLonLat(13.361389, 38.115556)
            .byRadius(100, GeoUnit.KM)
            .count(2)
            .asc()
            .withCoord()
            .withDist();

        List<GeoRadiusResponse> searchResponses = jedis.geosearch(KEY, param).get();
        assertNotNull(searchResponses);
        assertTrue(searchResponses.size() >= 1);
        assertNotNull(searchResponses.get(0).getCoordinate());
        assertNotNull(searchResponses.get(0).getDistance());
    }

    @Test
    public void testGeoRadiusByMember() throws ExecutionException, InterruptedException {
        setupSicilyPoints();

        // Search within 100km of Palermo
        List<GeoRadiusResponse> responses = jedis.georadiusByMember(KEY, "Palermo", 100, GeoUnit.KM).get();
        assertNotNull(responses);
        assertTrue(responses.size() >= 1);

        // Test with full parameters
        GeoSearchParam param = GeoSearchParam.geoSearchParam()
            .fromMember("Palermo")
            .byRadius(100, GeoUnit.KM)
            .count(2)
            .asc()
            .withCoord()
            .withDist();

        List<GeoRadiusResponse> searchResponses = jedis.geosearch(KEY, param).get();
        assertNotNull(searchResponses);
        assertTrue(searchResponses.size() >= 1);
        assertNotNull(searchResponses.get(0).getCoordinate());
        assertNotNull(searchResponses.get(0).getDistance());
    }

    @Test
    public void testGeoSearch() throws ExecutionException, InterruptedException {
        setupSicilyPoints();

        // Search within 100km radius of Palermo
        GeoSearchParam param = GeoSearchParam.geoSearchParam()
            .fromLonLat(13.361389, 38.115556)
            .byRadius(100, GeoUnit.KM)
            .count(2)
            .asc();

        List<GeoRadiusResponse> results = jedis.geosearch(KEY, param).get();
        assertNotNull(results);
        assertTrue(results.size() >= 1);

        // Search with member
        param = GeoSearchParam.geoSearchParam()
            .fromMember("Palermo")
            .byRadius(100, GeoUnit.KM)
            .count(2)
            .asc();

        results = jedis.geosearch(KEY, param).get();
        assertNotNull(results);
        assertTrue(results.size() >= 1);
    }

    @Test
    public void testGeoSearchStore() throws ExecutionException, InterruptedException {
        setupSicilyPoints();

        String destKey = "Sicily_nearby";
        GeoSearchParam param = GeoSearchParam.geoSearchParam()
            .fromLonLat(13.361389, 38.115556)
            .byRadius(100, GeoUnit.KM)
            .count(2)
            .asc();

        Long result = jedis.geosearchStore(destKey, KEY, param).get();
        assertTrue(result > 0);

        // Clean up
        jedis.del(destKey).get();
    }

    private void setupSicilyPoints() throws ExecutionException, InterruptedException {
        Map<String, GeoCoordinate> memberCoordinateMap = new HashMap<>();
        memberCoordinateMap.put("Palermo", new GeoCoordinate(13.361389, 38.115556));
        memberCoordinateMap.put("Catania", new GeoCoordinate(15.087269, 37.502669));
        memberCoordinateMap.put("Agrigento", new GeoCoordinate(13.583333, 37.316667));
        jedis.geoadd(KEY, memberCoordinateMap).get();
    }
} 