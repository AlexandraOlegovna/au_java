import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

class TrieImplTest {

    private TrieImpl trie;

    @BeforeEach
    void initTrie() {
        trie = new TrieImpl();
    }

    @Test
    void typicalCaseTest() {
        assertEquals(0, trie.size());
        assertFalse(trie.contains("test"));
        assertFalse(trie.remove("test"));
        assertTrue(trie.add("test"));
        assertTrue(trie.contains("test"));
        assertEquals(1, trie.size());
        assertEquals(1, trie.howManyStartsWithPrefix("t"));
        assertEquals(1, trie.howManyStartsWithPrefix("tes"));
        assertEquals(0, trie.howManyStartsWithPrefix("tos"));
        assertTrue(trie.remove("test"));
        assertFalse(trie.contains("test"));
        assertEquals(0, trie.size());
    }

    @Test
    void specificCaseTest() {
        assertTrue(trie.add("prefix"));
        assertTrue(trie.add("profix"));
        assertEquals(2, trie.howManyStartsWithPrefix("pr"));
        assertEquals(1, trie.howManyStartsWithPrefix("pref"));
        assertEquals(1, trie.howManyStartsWithPrefix("prof"));
        assertEquals(0, trie.howManyStartsWithPrefix("fix"));

        assertTrue(trie.remove("profix"));
        assertEquals(1, trie.howManyStartsWithPrefix("pr"));
        assertEquals(1, trie.howManyStartsWithPrefix("pref"));
        assertEquals(0, trie.howManyStartsWithPrefix("prof"));
        assertEquals(0, trie.howManyStartsWithPrefix("fix"));

        assertFalse(trie.add("prefix"));
        assertEquals(1, trie.howManyStartsWithPrefix("pr"));
        assertEquals(1, trie.howManyStartsWithPrefix("pref"));
        assertEquals(0, trie.howManyStartsWithPrefix("fix"));

        assertTrue(trie.remove("prefix"));
        assertFalse(trie.remove("prefix"));
        assertEquals(0, trie.howManyStartsWithPrefix("pr"));
        assertEquals(0, trie.howManyStartsWithPrefix("pref"));
        assertEquals(0, trie.howManyStartsWithPrefix("fix"));
    }

    @Test
    void addTest() {
        assertTrue(trie.add("abc"));
        assertEquals(1, trie.size());
        assertTrue(trie.add("a"));
        assertEquals(2, trie.size());
        assertTrue(trie.add("ab"));
        assertEquals(3, trie.size());
        assertFalse(trie.add("abc"));
        assertEquals(3, trie.size());
        assertTrue(trie.add("cab"));
        assertEquals(4, trie.size());
        assertFalse(trie.add(""));
        assertFalse(trie.add(null));
    }

    @Test
    void containsTest() {
        assertFalse(trie.contains("abc"));
        trie.add("abc");
        assertTrue(trie.contains("abc"));
        trie.add("abc");
        assertTrue(trie.contains("abc"));

        assertFalse(trie.contains("a"));
        trie.add("a");
        assertTrue(trie.contains("a"));
        assertFalse(trie.contains("ab"));
        trie.add("ab");
        assertTrue(trie.contains("ab"));
        assertFalse(trie.contains(""));
        assertFalse(trie.contains(null));
    }

    @Test
    void removeTest() {
        assertFalse(trie.remove("abc"));
        trie.add("abc");
        assertTrue(trie.remove("abc"));
        trie.add("abc");
        assertTrue(trie.remove("abc"));

        assertFalse(trie.remove("bc"));
        assertFalse(trie.remove("a"));
        trie.add("a");
        assertTrue(trie.remove("a"));
        assertFalse(trie.contains("ab"));
        assertFalse(trie.contains(""));
        assertFalse(trie.contains(null));
    }

    @Test
    void sizeTest() {
        assertEquals(0, trie.size());
        trie.add("abc");
        assertEquals(1, trie.size());
        trie.add("abc");
        assertEquals(1, trie.size());
        trie.remove("abc");
        assertEquals(0, trie.size());
        trie.contains("abc");
        assertEquals(0, trie.size());

        trie.add("a");
        assertEquals(1, trie.size());
        trie.add("ab");
        assertEquals(2, trie.size());
        trie.add("abc");
        assertEquals(3, trie.size());
        trie.add("cab");
        assertEquals(4, trie.size());
        trie.remove("ab");
        assertEquals(3, trie.size());
    }

    @Test
    void howManyStartsWithPrefixTest() {
        assertEquals(0, trie.howManyStartsWithPrefix("abc"));
        trie.add("abc");
        assertEquals(1, trie.howManyStartsWithPrefix("a"));
        assertEquals(1, trie.howManyStartsWithPrefix("ab"));
        assertEquals(1, trie.howManyStartsWithPrefix("abc"));
        trie.add("abc");
        trie.add("ab");
        assertEquals(2, trie.howManyStartsWithPrefix("a"));
        assertEquals(2, trie.howManyStartsWithPrefix("ab"));
        assertEquals(1, trie.howManyStartsWithPrefix("abc"));
        trie.add("ba");
        assertEquals(1, trie.howManyStartsWithPrefix("b"));
        assertEquals(1, trie.howManyStartsWithPrefix("ba"));
        assertEquals(2, trie.howManyStartsWithPrefix("a"));
        trie.remove("ab");
        assertEquals(1, trie.howManyStartsWithPrefix("a"));
        assertEquals(1, trie.howManyStartsWithPrefix("ab"));
        assertEquals(1, trie.howManyStartsWithPrefix("abc"));
        assertEquals(0, trie.howManyStartsWithPrefix(""));
        assertEquals(0, trie.howManyStartsWithPrefix(null));
    }

    @Test
    void lettersCaseTest() {
        trie.add("abc");
        trie.add("AbC");
        assertTrue(trie.contains("abc"));
        assertTrue(trie.contains("AbC"));
        assertFalse(trie.contains("aBc"));
        assertFalse(trie.contains("ABC"));
    }
}