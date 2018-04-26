package ru.spbau.mit.task4;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.Objects;


public class CollectionsTest {


    private Predicate<Integer> gteZero = x -> x >= 0;
    private Predicate<Integer> isEven = x -> x % 2 == 0;
    private Predicate<Integer> gteZeroORisEven = gteZero.or(isEven);
    private Predicate<Integer> gteZeroANDisEven = gteZero.and(isEven);

    @Test
    public void testMap() {
        assertEquals(java.util.Collections.emptyList(),
            Collections.map(
                x -> x,
                java.util.Collections.emptyList()));

        assertEquals(
            Arrays.asList(0, 1, 2, 3),
            Collections.map(x -> x, Arrays.asList(0, 1, 2, 3)));
        assertEquals(
            Arrays.asList(1, 2, 3, 4),
            Collections.map(x -> x + 1, Arrays.asList(0, 1, 2, 3)));
        assertEquals(
            Arrays.asList(0, 1, 4, 9),
            Collections.map(x -> x * x, Arrays.asList(0, 1, 2, 3)));
        assertEquals(
            Arrays.asList(0, 1, 2),
            Collections.map(String::length, Arrays.asList("", "1", "12")));
        assertEquals(
            Arrays.asList(false, true, true),
            Collections.map(Objects::isNull, Arrays.asList("12", null, null)));
    }

    @Test
    public void testFilter() {
        assertEquals(java.util.Collections.emptyList(),
            Collections.filter(
                x -> true,
                java.util.Collections.emptyList()));

        assertEquals(
            Arrays.asList(0, 1, 2, 3),
            Collections.filter(x -> true, Arrays.asList(0, 1, 2, 3)));
        assertEquals(
            Arrays.asList(1, 2, 3),
            Collections.filter(x -> x > 0, Arrays.asList(0, 1, 2, 3)));
        assertEquals(
            Arrays.asList(0, 2),
            Collections.filter(x -> x % 2 == 0, Arrays.asList(0, 1, 2, 3)));
        assertEquals(
            java.util.Collections.emptyList(),
            Collections.filter(x -> x.contains("3"), Arrays.asList("", "1", "12")));
        assertEquals(
            Arrays.asList(null, null),
            Collections.filter(Objects::isNull, Arrays.asList("12", null, null)));
    }

    @Test
    public void testTakeWhile() {
        assertEquals(java.util.Collections.emptyList(),
            Collections.takeWhile(
                x -> true,
                java.util.Collections.emptyList()));

        assertEquals(
            Arrays.asList(0, 1, 2),
            Collections.takeWhile(gteZero, Arrays.asList(0, 1, 2, -2, -3)));
        assertEquals(
            java.util.Collections.singletonList(0),
            Collections.takeWhile(isEven, Arrays.asList(0, 1, 2, -2, -3)));
        assertEquals(
            Arrays.asList(0, 1, 2, -2),
            Collections.takeWhile(gteZeroORisEven, Arrays.asList(0, 1, 2, -2, -3)));
        assertEquals(
            java.util.Collections.singletonList(0),
            Collections.takeWhile(gteZeroANDisEven, Arrays.asList(0, 1, 2, -2, -3)));
        assertEquals(
            Arrays.asList("3", "13"),
            Collections.takeWhile(x -> x.contains("3"), Arrays.asList("3", "13", "12")));
        assertEquals(
            java.util.Collections.singletonList(null),
            Collections.takeWhile(Objects::isNull, Arrays.asList(null, "12", null, null)));
    }

    @Test
    public void testTakeUnless() {
        assertEquals(java.util.Collections.emptyList(),
            Collections.takeUnless(
                x -> true,
                java.util.Collections.emptyList()));

        assertEquals(
            Arrays.asList(-3, -2),
            Collections.takeUnless(gteZero, Arrays.asList(-3, -2, 1, 0, 2)));
        assertEquals(
            java.util.Collections.singletonList(-3),
            Collections.takeUnless(isEven, Arrays.asList(-3, -2, 1, 0, 2)));
        assertEquals(
            Arrays.asList(-3, -1),
            Collections.takeUnless(gteZeroORisEven, Arrays.asList(-3, -1, 0, 1, 2)));
        assertEquals(
            Arrays.asList(-3, 1, -2),
            Collections.takeUnless(gteZeroANDisEven, Arrays.asList(-3, 1, -2, 0, 2)));
        assertEquals(
            java.util.Collections.emptyList(),
            Collections.takeUnless(x -> x.contains("3"), Arrays.asList("13", "",  "12")));
        assertEquals(
            java.util.Collections.singletonList(""),
            Collections.takeUnless(Objects::isNull, Arrays.asList("", null, "12", null, null)));
    }

    @Test
    public void testFoldl() {
        assertEquals("",
            Collections.foldl(
                (x, y) -> x, "",
                java.util.Collections.emptyList()));

        assertEquals(
            (Integer) 4,
            Collections.foldl(
                (x, y) -> y,
                0,
                Arrays.asList(0, 1, 2, 3, 4)));

        assertEquals(
            (Integer) 10,
            Collections.foldl(
                (x, y) -> x + y,
                0,
                Arrays.asList(0, 1, 2, 3, 4)));

        assertEquals(
            (Integer) 120,
            Collections.foldl(
                (x, y) -> x * y,
                1,
                Arrays.asList(1, 2, 3, 4, 5)));

        assertEquals(
            (Integer) 3,
            Collections.foldl(
                (x, y) -> x + y.length(),
                0,
                Arrays.asList("", "1", "12")));

        assertEquals(
            "1234",
            Collections.foldl(
                (x, y) -> x + y.toString(),
                "",
                Arrays.asList(1, 2, 3, 4)));

        assertEquals(
            true,
            Collections.foldl(
                (x, y) -> (y == null),
                null,
                Arrays.asList("12", null, null)));
    }

    @Test
    public void testFoldr() {
        assertEquals("",
            Collections.foldr(
                (x, y) -> y, "",
                java.util.Collections.emptyList()));

        assertEquals(
            (Integer) 0,
            Collections.foldr(
                (x, y) -> y,
                0,
                Arrays.asList(0, 1, 2, 3, 4)));

        assertEquals(
            (Integer) 10,
            Collections.foldr(
                (x, y) -> x + y,
                0,
                Arrays.asList(0, 1, 2, 3, 4)));

        assertEquals(
            (Integer) 120,
            Collections.foldr(
                (x, y) -> x * y,
                1,
                Arrays.asList(1, 2, 3, 4, 5)));

        assertEquals(
            (Integer) 3,
            Collections.foldr(
                (x, y) -> y + x.length(),
                0,
                Arrays.asList("", "1", "12")));

        assertEquals(
            "4321",
            Collections.foldr(
                (x, y) -> y + x.toString(),
                "",
                Arrays.asList(1, 2, 3, 4)));

        assertEquals(
            false,
            Collections.foldr(
                (x, y) -> (x == null),
                null,
                Arrays.asList("12", null, null)));
    }


}
