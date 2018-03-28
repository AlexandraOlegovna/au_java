import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import ru.spbau.mit.task2.DictionaryImpl;

class DictionaryImplTest {
    private DictionaryImpl<String, String> dict;
    private String[] abc =
            {"a", "b", "c", "d", "e", "f", "g", "h", "i",
                    "j", "k", "l", "m", "n", "o", "p", "q", "r",
                    "s", "t", "u", "v", "w", "x", "y", "z", "A",
                    "B", "C", "D", "E", "F", "G", "H", "I", "J",
                    "K", "L", "M", "N", "O", "P", "Q", "R", "S",
                    "T", "U", "V", "W", "X", "Y", "Z"};


    @BeforeEach
    void initDictionary() {
        dict = new DictionaryImpl<>();
    }

    @Test
    void typicalTest1() {

        for (int i = 0; i < abc.length - 1; ++i) {
            assertNull(dict.remove(abc[i]));
            assertNull(dict.get(abc[i] + ""));
            assertFalse(dict.contains(abc[i]));
        }

        assertEquals(0, dict.size());

        for (int i = 0; i < abc.length - 1; ++i) {
            assertNull(dict.put(abc[i], abc[i + 1]));
        }

        assertEquals(abc.length - 1, dict.size());

        for (int i = 0; i < abc.length - 1; ++i) {
            assertEquals(abc[i + 1], dict.put(abc[i], abc[i + 1]));
        }

        assertEquals(abc.length - 1, dict.size());

        for (int i = abc.length - 2; i >= 0; --i) {
            assertEquals(abc[i + 1], dict.get(abc[i]));
            assertTrue(dict.contains(abc[i]));
        }

        assertEquals(abc.length - 1, dict.size());

        for (int i = 0; i < abc.length - 1; ++i) {
            assertEquals(abc[i + 1], dict.remove(abc[i]));
        }

        assertEquals(0, dict.size());


        for (int i = 0; i < abc.length - 1; ++i) {
            assertNull(dict.put(abc[i], abc[i + 1]));
        }

        assertEquals(abc.length - 1, dict.size());

    }

    @Test
    void putTest() {
        assertEquals(0, dict.size());
        assertNull(dict.put("a", "b"));
        assertEquals(1, dict.size());
        assertNull(dict.put("b", "c"));
        assertEquals(2, dict.size());
        assertNull(dict.put("c", "d"));
        assertEquals(3, dict.size());
        assertEquals("b", dict.put("a", "c"));
        assertEquals(3, dict.size());
        assertEquals("d", dict.put("c", "c"));
        assertEquals(3, dict.size());
        assertThrows(IllegalArgumentException.class, () -> dict.put(null, "a"));
        assertThrows(IllegalArgumentException.class, () -> dict.put("a", null));
        assertThrows(IllegalArgumentException.class, () -> dict.put(null, null));
    }

    @Test
    void getTest() {
        assertNull(dict.get("a"));
        assertNull(dict.put("a", "b"));
        assertEquals("b", dict.get("a"));
        assertNull(dict.get("b"));
        assertEquals("b", dict.put("a", "B"));
        assertNull(dict.get("b"));
        assertEquals("B", dict.get("a"));
        assertEquals("B", dict.put("a", "c"));
        assertEquals("c", dict.get("a"));
        assertThrows(IllegalArgumentException.class, () -> dict.get(null));
    }

    @Test
    void containsTest() {
        assertFalse(dict.contains("a"));
        assertNull(dict.put("a", "b"));
        assertTrue(dict.contains("a"));
        assertEquals("b", dict.put("a", "c"));
        assertTrue(dict.contains("a"));
        assertFalse(dict.contains("b"));
        assertFalse(dict.contains("c"));
    }

    @Test
    void removeTest() {
        assertNull(dict.remove("a"));
        assertNull(dict.put("a", "b"));
        assertEquals("b", dict.remove("a"));
        assertNull(dict.get("a"));
        assertNull(dict.remove("a"));

        assertNull(dict.put("b", "c"));
        assertEquals("c", dict.put("b", "d"));
        assertEquals("d", dict.remove("b"));
        assertNull(dict.get("b"));
        assertNull(dict.remove("b"));
        assertThrows(IllegalArgumentException.class, () -> dict.remove(null));
    }


    @Test
    void clearTest() {
        assertNull(dict.put("a", "b"));
        assertNull(dict.put("b", "b"));
        assertNull(dict.put("c", "b"));
        assertNull(dict.put("d", "b"));
        assertEquals(4, dict.size());

        dict.clear();
        assertEquals(0, dict.size());

    }

    @Test
    void sizeTest() {
        assertNull(dict.remove("a"));
        assertEquals(0, dict.size());
        assertNull(dict.put("a", "b"));
        assertEquals(1, dict.size());
        assertNull(dict.put("b", "b"));
        assertEquals(2, dict.size());
        assertEquals("b", dict.get("a"));
        assertEquals(2, dict.size());
        assertEquals("b", dict.put("a", "b"));
        assertEquals(2, dict.size());
        assertNull(dict.put("c", "b"));
        assertEquals(3, dict.size());
        assertEquals("b", dict.remove("a"));
        assertEquals(2, dict.size());
        assertNull(dict.put("d", "b"));
        assertEquals(3, dict.size());
        dict.clear();
        assertEquals(0, dict.size());
        assertNull(dict.put("a", "a"));
        assertEquals(1, dict.size());
    }

}
