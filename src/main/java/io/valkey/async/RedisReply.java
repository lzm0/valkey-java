package io.valkey.async;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class RedisReply {
    private String value;
    private Throwable error;

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setError(Throwable error) {
        this.error = error;
    }

    public Throwable getError() {
        return error;
    }

    public void parse(ByteBuffer bytes) {
        if (bytes != null) {
            value = StandardCharsets.US_ASCII.decode(bytes).toString();
        }
    }
}
