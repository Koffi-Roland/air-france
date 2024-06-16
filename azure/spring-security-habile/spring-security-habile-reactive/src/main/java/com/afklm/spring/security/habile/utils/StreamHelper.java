package com.afklm.spring.security.habile.utils;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Utility class providing tools to apply streams operations to a {@link Collection} in a clearer and less verbose way
 *
 * @author TECC
 */
public final class StreamHelper {
    private StreamHelper() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * This method allows to apply arbitrary {@link Stream} operations to the provided list.
     * It doesn't affect the original list and produces a new one.
     * <p>
     * Example :
     * <pre>
     *     {@code
     *     StreamHelper.applyTo(list, elements -> elements
     *                     .map(element -> "some prefix" + element)
     *                     .peek(element -> LOGGER.info("an element of the list is now {}", element))
     *                     .distinct()
     *                     .limit(5));
     *     }
     * </pre>
     *
     * @param list       the list the operations will apply to
     * @param operations the operations to be applied to the list, designed to be provided as a lambda
     * @param <T>        input type
     * @param <U>        output type, can be the same as <T> but not necessarily
     * @return a new list as a result of the chain of operations applied to the input list
     */
    public static <T, U> List<U> applyTo(Collection<T> list, Function<Stream<T>, Stream<U>> operations) {
        return operations.apply(list.stream()).collect(Collectors.toList());
    }

    /**
     * This method is a shorthand of {@link StreamHelper#applyTo(Collection, Function)} designed
     * to easily apply a transformation to the provided list.
     * It doesn't affect the original list and produces a new one.
     * <p>
     * Example :
     * <pre>
     *     {@code
     *     StreamHelper.transform(l, SimpleGrantedAuthority::new);
     *     }
     * </pre>
     *
     * @param list      the list the operation will apply to
     * @param operation the operation to be applied to the list, designed to be provided as a lambda
     * @param <T>       input type
     * @param <U>       output type, can be the same as <T> but not necessarily
     * @return a new list as a result of the transformation applied to the input list
     */
    public static <T, U> List<U> transform(Collection<T> list, Function<T, U> operation) {
        return applyTo(list, stream -> stream.map(operation));
    }

    /**
     * This method is a shorthand of {@link StreamHelper#transform(Collection, Function)} designed to easily
     * cast the elements of a list.
     * It doesn't affect the original list and produces a new one.
     * <p>
     * Example :
     * <pre>
     *     {@code
     *     StreamHelper.cast(list, SomeClass.class);
     *     }
     * </pre>
     *
     * @param list        the list the operation will apply to
     * @param targetClass target class to cast the elements of the list to
     * @param <T>         input type
     * @param <U>         output type, can be the same as <T> but not necessarily
     * @return a new list as a result of the cast applied to the elements of the list
     */
    public static <T, U> List<U> cast(Collection<T> list, Class<U> targetClass) {
        return transform(list, targetClass::cast);
    }
}
