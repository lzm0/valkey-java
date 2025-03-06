package io.valkey.async;

import io.valkey.HostAndPort;
import io.valkey.HostAndPorts;
import org.junit.Assert;
import org.junit.Test;

public class AsyncJedisBasicTest {
    private static final HostAndPort hnp = HostAndPorts.getRedisServers().get(0);

    @Test
    public void connectSuccess() {
        AsyncJedis jedis = new AsyncJedis(hnp);
        try {
            boolean connect = jedis.connect();
            Assert.assertTrue(connect);
            RedisReply reply = jedis.set("test", "test").get();
            Assert.assertEquals("OK", reply.getValue());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("connect failed");
        }
    }

    @Test
    public void setAndGetSuccess() {
        AsyncJedis jedis = new AsyncJedis(hnp);
        try {
            boolean connect = jedis.connect();
            Assert.assertTrue(connect);
            
            // Test SET
            RedisReply setReply = jedis.set("testKey", "testValue").get();
            Assert.assertEquals("OK", setReply.getValue());
            
            // Test GET
            RedisReply getReply = jedis.get("testKey").get();
            Assert.assertEquals("testValue", getReply.getValue());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("test failed: " + e.getMessage());
        }
    }
}
