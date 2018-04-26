package ru.spbau.mit.task4;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;


class Function2Test {

    private Function2<Integer, Integer, Integer> plus = (x, y) -> x + y;
    private Function1<Integer, Integer> inc = x -> x + 1;
    private Function2<Integer, String, String> concat = (x, y) -> x.toString() + " " + y;
    private Function2<Object, Object, Boolean> nulAndNotNull = (x, y) -> (x == null && y != null);


    @Test
    public void testApply() {

        assertEquals((Integer) 3, plus.apply(1, 2));
        assertEquals((Integer) (-12), plus.apply(-10, -2));

        assertEquals("42 is a number", concat.apply(42, "is a number"));
        assertEquals("-1 ", concat.apply(-1, ""));

        assertTrue(nulAndNotNull.apply(null, ""));
        assertFalse(nulAndNotNull.apply("", null));
        assertFalse(nulAndNotNull.apply(123456, "1234"));
    }


    @Test
    public void testCompose() {
        assertEquals((Integer) 42, plus.compose(inc).apply(40, 1));
        assertEquals((Integer) (-8), plus.compose(inc).apply(-5, -4));
        assertEquals((Integer) 4, concat.compose(String::length).apply(12, "2"));
        assertEquals("246", plus.compose(Object::toString).apply(123, 123));
    }


    @Test
    public void testBind1() {
        Function1<Integer, Integer> plusOne = plus.bind1(1);
        Function1<Object, Boolean> isNotNull = nulAndNotNull.bind1(null);

        assertEquals((Integer) 13, plusOne.apply(12));
        assertTrue(isNotNull.apply(12));
    }


    @Test
    public void testBind2() {

        Function1<Integer, Integer> plusOne = plus.bind2(1);
        Function1<Object, Boolean> isNull = nulAndNotNull.bind2(1);

        assertEquals((Integer) 13, plusOne.apply(12));
        assertTrue(isNull.apply(null));
    }


    @Test
    public void testCurry() {

        Function1<Integer, Function1<Integer, Integer>> curryPlus = plus.curry();
        assertEquals((Integer) 3, curryPlus.apply(1).apply(2));
        assertEquals((Integer) 0, curryPlus.apply(-5).apply(5));

        Function1<Object, Function1<Object, Boolean>> curryNulAndNotNull = nulAndNotNull.curry();
        assertTrue(curryNulAndNotNull.apply(null).apply(2));
        assertFalse(curryNulAndNotNull.apply(null).apply(null));
    }


}