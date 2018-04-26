package ru.spbau.mit.task4;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.util.Objects;

public class PredicateTest {
    private Predicate<Object> isNull = Objects::isNull;
    private Predicate<Object> isNotNull = Objects::nonNull;

    @Test
    public void testAlwaysTrue() {
        assertTrue(isNull.ALWAYS_TRUE.apply(null));
        assertTrue(isNull.ALWAYS_TRUE.apply(123));

        assertTrue(isNotNull.ALWAYS_TRUE.apply(12));
        assertTrue(isNotNull.ALWAYS_TRUE.apply(null));
    }

    @Test
    public void testAlwaysFalse() {
        assertFalse(isNull.ALWAYS_FALSE.apply(1));
        assertFalse(isNull.ALWAYS_FALSE.apply(123L));
        assertFalse(isNull.ALWAYS_FALSE.apply("123"));
        assertFalse(isNull.ALWAYS_FALSE.apply(true));

        assertFalse(isNotNull.ALWAYS_FALSE.apply(null));
    }

    @Test
    public void testOr() {
        assertTrue(isNull.or(isNotNull).apply(null));
        assertTrue(isNull.or(isNull.ALWAYS_TRUE).apply("123"));
        assertTrue(isNull.or(isNull.ALWAYS_FALSE).apply(null));
        assertTrue(isNull.or(isNull).apply(null));
        assertFalse(isNull.or(isNull).apply(7));

        assertTrue(isNotNull.or(isNull).apply(null));
        assertTrue(isNotNull.or(isNotNull.ALWAYS_TRUE).apply(null));
        assertFalse(isNotNull.or(isNotNull.ALWAYS_FALSE).apply(null));
        assertTrue(isNotNull.or(isNotNull).apply("12"));
        assertFalse(isNotNull.or(isNotNull).apply(null));
    }

    @Test
    public void testAnd() {
        assertFalse(isNull.and(isNotNull).apply(null));
        assertFalse(isNull.and(isNull.ALWAYS_TRUE).apply(123));
        assertFalse(isNull.and(isNull.ALWAYS_FALSE).apply(null));
        assertTrue(isNull.and(isNull).apply(null));
        assertFalse(isNull.and(isNull).apply(142));

        assertFalse(isNotNull.and(isNull).apply(null));
        assertFalse(isNotNull.and(isNotNull.ALWAYS_TRUE).apply(null));
        assertTrue(isNotNull.and(isNotNull.ALWAYS_TRUE).apply(102));
        assertFalse(isNotNull.and(isNotNull.ALWAYS_FALSE).apply(null));
        assertTrue(isNotNull.and(isNotNull).apply("123"));
        assertFalse(isNotNull.and(isNotNull).apply(null));
    }

    @Test
    public void testNot() {
        assertFalse(isNull.not().apply(null));
        assertTrue(isNull.not().apply(""));
        assertFalse(isNotNull.not().apply(12));
        assertTrue(isNotNull.not().apply(null));
    }

    @Test
    public void testLazy() {

        Predicate<Object> undefined = x -> {throw new NullPointerException();};

        assertTrue(isNull.or(undefined).apply(null));
        assertFalse(isNull.and(undefined).apply("123"));

        assertThrows(NullPointerException.class, () -> isNull.or(undefined).apply("123"));
        assertThrows(NullPointerException.class, () -> undefined.and(undefined.ALWAYS_TRUE).apply(null));

    }
}
