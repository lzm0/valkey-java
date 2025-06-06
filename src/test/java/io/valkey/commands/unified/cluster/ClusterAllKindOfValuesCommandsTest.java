package io.valkey.commands.unified.cluster;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import io.valkey.RedisProtocol;
import io.valkey.commands.unified.AllKindOfValuesCommandsTestBase;
import io.valkey.params.ScanParams;
import io.valkey.resps.ScanResult;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class ClusterAllKindOfValuesCommandsTest extends AllKindOfValuesCommandsTestBase {

  public ClusterAllKindOfValuesCommandsTest(RedisProtocol protocol) {
    super(protocol);
  }

  @Before
  public void setUp() {
    jedis = ClusterCommandsTestHelper.getCleanCluster(protocol);
  }

  @After
  public void tearDown() {
    jedis.close();
    ClusterCommandsTestHelper.clearClusterData();
  }

  @Test
  @Override
  public void existsMany() {
    String status = jedis.set("{foo}1", "bar1");
    assertEquals("OK", status);

    status = jedis.set("{foo}2", "bar2");
    assertEquals("OK", status);

    Assert.assertEquals(2L, jedis.exists("{foo}1", "{foo}2"));

    Assert.assertEquals(1L, jedis.del("{foo}1"));

    Assert.assertEquals(1L, jedis.exists("{foo}1", "{foo}2"));
  }

  @Test
  @Override
  public void del() {
    jedis.set("{foo}1", "bar1");
    jedis.set("{foo}2", "bar2");
    jedis.set("{foo}3", "bar3");

    Assert.assertEquals(3L, jedis.del("{foo}1", "{foo}2", "{foo}3"));

    assertFalse(jedis.exists("{foo}1"));
    assertFalse(jedis.exists("{foo}2"));
    assertFalse(jedis.exists("{foo}3"));

    jedis.set("{foo}1", "bar1");

    Assert.assertEquals(1L, jedis.del("{foo}1", "{foo}2"));

    Assert.assertEquals(0L, jedis.del("{foo}1", "{foo}2"));
  }

  @Test
  @Override
  public void unlink() {
    jedis.set("{foo}1", "bar1");
    jedis.set("{foo}2", "bar2");
    jedis.set("{foo}3", "bar3");

    Assert.assertEquals(3, jedis.unlink("{foo}1", "{foo}2", "{foo}3"));

    Assert.assertEquals(0, jedis.exists("{foo}1", "{foo}2", "{foo}3"));

    jedis.set("{foo}1", "bar1");

    Assert.assertEquals(1, jedis.unlink("{foo}1", "{foo}2"));

    Assert.assertEquals(0, jedis.unlink("{foo}1", "{foo}2"));

    jedis.set("{foo}", "bar");
    Assert.assertEquals(1, jedis.unlink("{foo}"));
    assertFalse(jedis.exists("{foo}"));
  }

  @Test
  @Override
  public void keys() {
    jedis.set("{foo}", "bar");
    jedis.set("{foo}bar", "bar");

    Set<String> keys = jedis.keys("{foo}*");
    Set<String> expected = new HashSet<>();
    expected.add("{foo}");
    expected.add("{foo}bar");
    assertEquals(expected, keys);

    expected.clear();
    keys = jedis.keys("{bar}*");

    assertEquals(expected, keys);
  }

  @Test
  @Override
  public void rename() {
    jedis.set("foo{#}", "bar");
    String status = jedis.rename("foo{#}", "bar{#}");
    assertEquals("OK", status);

    assertNull(jedis.get("foo{#}"));

    Assert.assertEquals("bar", jedis.get("bar{#}"));
  }

  @Test
  @Override
  public void renamenx() {
    jedis.set("foo{&}", "bar");
    Assert.assertEquals(1, jedis.renamenx("foo{&}", "bar{&}"));

    jedis.set("foo{&}", "bar");
    Assert.assertEquals(0, jedis.renamenx("foo{&}", "bar{&}"));
  }

  @Test(expected = UnsupportedOperationException.class)
  @Override
  public void dbSize() {
    super.dbSize();
  }

  @Test
  @Override
  public void touch() throws Exception {
    Assert.assertEquals(0, jedis.touch("{foo}1", "{foo}2", "{foo}3"));

    jedis.set("{foo}1", "bar1");

    Thread.sleep(1100); // little over 1 sec
    assertTrue(jedis.objectIdletime("{foo}1") > 0);

    Assert.assertEquals(1, jedis.touch("{foo}1"));
    Assert.assertEquals(0L, jedis.objectIdletime("{foo}1").longValue());

    Assert.assertEquals(1, jedis.touch("{foo}1", "{foo}2", "{foo}3"));

    jedis.set("{foo}2", "bar2");

    jedis.set("{foo}3", "bar3");

    Assert.assertEquals(3, jedis.touch("{foo}1", "{foo}2", "{foo}3"));
  }

  @Test
  @Override
  public void scan() {
    jedis.set("{%}b", "b");
    jedis.set("a{%}", "a");

    ScanResult<String> result = jedis.scan(ScanParams.SCAN_POINTER_START, new ScanParams().match("*{%}*"));

    Assert.assertEquals(ScanParams.SCAN_POINTER_START, result.getCursor());
    assertFalse(result.getResult().isEmpty());
  }

  @Test
  @Override
  public void scanMatch() {
    ScanParams params = new ScanParams();
    params.match("a{-}*");

    jedis.set("b{-}", "b");
    jedis.set("a{-}", "a");
    jedis.set("a{-}a", "aa");
    ScanResult<String> result = jedis.scan(ScanParams.SCAN_POINTER_START, params);

    Assert.assertEquals(ScanParams.SCAN_POINTER_START, result.getCursor());
    assertFalse(result.getResult().isEmpty());
  }

  @Test
  @Override
  public void scanCount() {
    ScanParams params = new ScanParams();
    params.match("{a}*");
    params.count(2);

    for (int i = 0; i < 10; i++) {
      jedis.set("{a}" + i, "a" + i);
    }

    ScanResult<String> result = jedis.scan(ScanParams.SCAN_POINTER_START, params);
    assertTrue(result.getResult().size() >= 2);
  }

  @Test
  @Override
  public void scanType() {
    ScanParams noCount = new ScanParams().match("*{+}*");
    ScanParams pagingParams = new ScanParams().match("*{+}*").count(4);

    jedis.set("{+}a", "a");
    jedis.hset("{+}b", "b", "b");
    jedis.set("c{+}", "c");
    jedis.sadd("d{+}", "d");
    jedis.set("e{+}", "e");
    jedis.zadd("{+}f", 0d, "f");
    jedis.set("{+}g", "g");

    // string
    ScanResult<String> scanResult;

    scanResult = jedis.scan(ScanParams.SCAN_POINTER_START, pagingParams, "string");
    assertTrue(scanResult.isCompleteIteration()); // https://github.com/valkey-io/valkey/issues/1490
    assertEquals(4, scanResult.getResult().size());


    scanResult = jedis.scan(ScanParams.SCAN_POINTER_START, noCount, "hash");
    assertEquals(Collections.singletonList("{+}b"), scanResult.getResult());
    scanResult = jedis.scan(ScanParams.SCAN_POINTER_START, noCount, "set");
    assertEquals(Collections.singletonList("d{+}"), scanResult.getResult());
    scanResult = jedis.scan(ScanParams.SCAN_POINTER_START, noCount, "zset");
    assertEquals(Collections.singletonList("{+}f"), scanResult.getResult());
  }

  @Test(expected = IllegalArgumentException.class)
  @Override
  public void scanIsCompleteIteration() {
    super.scanIsCompleteIteration();
  }

  @Test
  @Override
  public void copy() {
    assertFalse(jedis.copy("unkn{o}wn", "f{o}o", false));

    jedis.set("{foo}1", "bar");
    assertTrue(jedis.copy("{foo}1", "{foo}2", false));
    Assert.assertEquals("bar", jedis.get("{foo}2"));

    // replace
    jedis.set("{foo}1", "bar1");
    assertTrue(jedis.copy("{foo}1", "{foo}2", true));
    Assert.assertEquals("bar1", jedis.get("{foo}2"));
  }
}
