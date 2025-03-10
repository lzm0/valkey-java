package io.valkey.async;

import io.valkey.DefaultJedisClientConfig;
import io.valkey.HostAndPort;
import io.valkey.HostAndPorts;
import io.valkey.providers.PooledConnectionProvider;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

public class AsyncJedisBasicTest {
    private static final HostAndPort hnp = HostAndPorts.getRedisServers().get(0);
    protected AsyncJedis jedis;

    @Before
    public void setUp() throws Exception {
        DefaultJedisClientConfig config = DefaultJedisClientConfig.builder()
            .password("foobared")
            .build();
        jedis = new AsyncJedis(new PooledConnectionProvider(hnp, config));
        Assert.assertEquals("OK", jedis.flushAll().get());
    }

    @After
    public void tearDown() {
        jedis.close();
    }
}
