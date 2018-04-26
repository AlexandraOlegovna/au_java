package ru.spbau.mit.task4;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;


class Function1Test {

    private Function1<Integer, Integer> inc = x -> x + 1;
    private Function1<Integer, String> str = Object::toString;
    private Function1<String, Integer> len = String::length;
    private Function1<Object, Long> objToLong = x -> (x == null) ? Long.MIN_VALUE : Long.MAX_VALUE;



    @Test
    public void testApply() {

        assertEquals(
            (Integer) 42,
            inc.apply(41));
        assertEquals(
            (Integer) 0,
            inc.apply(-1));

        assertEquals(
            "42",
            str.apply(42));
        assertEquals(
            "-1",
            str.apply(-1));

        assertEquals(
            (Integer) 6,
            len.apply("length"));
        assertEquals(
            (Integer) 0,
            len.apply(""));

        assertEquals(
            (Long) Long.MIN_VALUE,
            objToLong.apply(null));
        assertEquals(
            (Long) Long.MAX_VALUE,
            objToLong.apply(""));
        assertEquals(
            (Long) Long.MAX_VALUE,
            objToLong.apply(123456));
    }


    @Test
    public void testCompose() {
        assertEquals(
            (Integer) 42,
            inc.compose(inc).apply(40));
        assertEquals(
            (Integer) (-3),
            inc.compose(inc).apply(-5));

        assertEquals(
            "123457",
            inc.compose(str).apply(123456));

        assertEquals(
            (Integer) 6,
            str.compose(len).apply(123456));
        assertEquals(
            "6",
            len.compose(str).apply("123456"));

    }
}