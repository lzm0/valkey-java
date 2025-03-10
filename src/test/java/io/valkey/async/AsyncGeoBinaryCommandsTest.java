package io.valkey.async;

import io.valkey.args.GeoUnit;
import io.valkey.params.GeoSearchParam;
import io.valkey.GeoCoordinate;
import io.valkey.resps.GeoRadiusResponse;
import io.valkey.util.SafeEncoder;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

public class AsyncGeoBinaryCommandsTest extends AsyncJedisBasicTest {

    private static final byte[] KEY = "Sicily".getBytes();
    private static final byte[] PALERMO = "Palermo".getBytes();
    private static final byte[] CATANIA = "Catania".getBytes();
    private static final byte[] AGRIGENTO = "Agrigento".getBytes();

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        jedis.del(KEY).get();
    }

    @Test
    public void testGeoAdd() throws ExecutionException, InterruptedException {
        Map<byte[], GeoCoordinate> memberCoordinateMap = new HashMap<>();
        memberCoordinateMap.put(PALERMO, new GeoCoordinate(13.361389, 38.115556));
        memberCoordinateMap.put(CATANIA, new GeoCoordinate(15.087269, 37.502669));

        Long result = jedis.geoadd(KEY, memberCoordinateMap).get();
        assertEquals(Long.valueOf(2), result);

        // Test single add
        Long singleResult = jedis.geoadd(KEY, 13.583333, 37.316667, AGRIGENTO).get();
        assertEquals(Long.valueOf(1), singleResult);
    }

    @Test
    public void testGeoDist() throws ExecutionException, InterruptedException {
        setupSicilyPoints();

        Double dist = jedis.geodist(KEY, PALERMO, CATANIA).get();
        assertNotNull(dist);
        assertTrue(dist > 0);

        // Test with unit
        Double distKm = jedis.geodist(KEY, PALERMO, CATANIA, GeoUnit.KM).get();
        assertNotNull(distKm);
        assertTrue(distKm > 0);
    }

    @Test
    public void testGeoHash() throws ExecutionException, InterruptedException {
        setupSicilyPoints();

        List<byte[]> hashes = jedis.geohash(KEY, PALERMO, CATANIA).get();
        assertEquals(2, hashes.size());
        assertNotNull(hashes.get(0));
        assertNotNull(hashes.get(1));
    }

    @Test
    public void testGeoPos() throws ExecutionException, InterruptedException {
        setupSicilyPoints();

        List<GeoCoordinate> positions = jedis.geopos(KEY, PALERMO, CATANIA).get();
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
        List<GeoRadiusResponse> responses = jedis.georadiusByMember(KEY, PALERMO, 100, GeoUnit.KM).get();
        assertNotNull(responses);
        assertTrue(responses.size() >= 1);

        // Test with full parameters
        GeoSearchParam param = GeoSearchParam.geoSearchParam()
            .fromMember(SafeEncoder.encode(PALERMO))
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
            .fromMember(SafeEncoder.encode(PALERMO))
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

        byte[] destKey = "Sicily_nearby".getBytes();
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
        Map<byte[], GeoCoordinate> memberCoordinateMap = new HashMap<>();
        memberCoordinateMap.put(PALERMO, new GeoCoordinate(13.361389, 38.115556));
        memberCoordinateMap.put(CATANIA, new GeoCoordinate(15.087269, 37.502669));
        memberCoordinateMap.put(AGRIGENTO, new GeoCoordinate(13.583333, 37.316667));
        jedis.geoadd(KEY, memberCoordinateMap).get();
    }
} 