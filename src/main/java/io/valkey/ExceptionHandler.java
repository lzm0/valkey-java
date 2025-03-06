package io.valkey;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

public class ExceptionHandler {
    private Map<Predicate<String>, ErrorCallback> patternCallbacks;

    public interface ErrorCallback {
        void onError(String errorMessage);
    }

    public ExceptionHandler() {
        this.patternCallbacks = new HashMap<>();
    }

    public void register(Predicate<String> pattern, ErrorCallback callback) {
        patternCallbacks.put(pattern, callback);
    }

    // This method allows the registration of a new error pattern and its corresponding callback.
    // It takes a Predicate<String> that defines the error pattern to match,
    // and an ErrorCallback that will be executed when the pattern is matched.
    // This enables dynamic handling of different types of errors based on their messages.
    public void handleException(Exception e) {
        Optional.ofNullable(e.getMessage()).ifPresent(errorMessage -> {
            patternCallbacks.forEach((pattern, callback) -> {
                if (pattern.test(errorMessage)) {
                    callback.onError(errorMessage);
                }
            });
        });
    }
}

