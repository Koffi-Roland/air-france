package com.afklm.spring.security.habile.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * Utility class to wrap a calculation we know will always be the same but may be called many times.
 * Inspired from {@see <a href="https://dzone.com/articles/be-lazy-with-java-8">dzone.com/articles/be-lazy-with-java-8</a>}
 *
 * @param <T> the type of what you want to be wrapped
 */
public final class LazyCalculation<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(LazyCalculation.class);
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private volatile Optional<T> value = Optional.empty();

    /**
     * When you're not sure whether some branching will be executed or not, this method can be used on non changing
     * values needing an expensive computation to be retrieved, in order to save some time.
     * The computation is only done once on the first call, and the stored value is returned if it's called again.
     *
     * @param supplier what needs to be done to fix the value
     * @return the computed result
     */
    public T getOrCompute(Supplier<T> supplier) {
        final Optional<T> result = value; // Just one volatile read
        return result.orElse(maybeCompute(supplier));
    }

    private synchronized T maybeCompute(Supplier<T> supplier) {
        if (value.isEmpty()) {
            value = Optional.of(supplier.get());
            LOGGER.trace("Computed the result for supplier {}, should only be seen once", supplier.toString());
        }

        return value.get();
    }
}
