package ru.spbau.mit.task2;

import java.util.ArrayList;
import java.util.Iterator;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DictionaryImpl<Key, Value> implements Dictionary<Key, Value> {

    private final int MIN_SIZE_OF_TABLE = 16;
    private final static double INIT_LOW_LEVEL = 0.2;
    private final static double INIT_HIGH_LEVEL = 0.8;
    private double lowCapacityLevel;
    private double highCapacityLevel;
    private ArrayList<DictionaryList<Key, Value>> hashTable;
    private int size = 0;

    public DictionaryImpl() {
        this(INIT_LOW_LEVEL, INIT_HIGH_LEVEL);
    }

    public DictionaryImpl(double lowLevel, double highLevel) {
        lowCapacityLevel = lowLevel;
        highCapacityLevel = highLevel;
        initHashTable(MIN_SIZE_OF_TABLE);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(@NotNull Key key) {
        return get(key) != null;
    }

    @Override
    @Nullable
    public Value get(@NotNull Key key) {
        assertNullKey(key);
        int ind = getHash(key);
        return hashTable.get(ind).find(key);
    }

    @Override
    @Nullable
    public Value put(@NotNull Key key, @NotNull Value value) {
        assertNullKey(key);
        assertNullValue(value);
        int ind = getHash(key);
        Value result = hashTable.get(ind).put(key, value);
        size += (result == null) ? 1 : 0;
        rehash();
        return result;
    }

    @Override
    @Nullable
    public Value remove(@NotNull Key key) {
        assertNullKey(key);
        int ind = getHash(key);
        Value result = hashTable.get(ind).remove(key);
        size -= (result == null) ? 0 : 1;
        rehash();
        return result;
    }

    @Override
    public void clear() {
        initHashTable(MIN_SIZE_OF_TABLE);
        size = 0;
    }

    /**
     * Double hashing is one of the best methods available
     * for open addressing because the permutations produced have many of the characteristics
     * of randomly chosen permutations.
     *
     * Following the links below you can read about double hashing and see examples of function:
     * h = k % m
     * g = 1 + (k / m) % m', m' = m - 1
     * (h + g) % m
     *
     * https://courses.cs.washington.edu/courses/cse326/09wi/lectures/13-hashing.pdf
     * http://staff.ustc.edu.cn/~csli/graduate/algorithms/book6/chap12.htm
     */
    private int getHash(@NotNull Key key) {
        int h = key.hashCode() % hashTable.size();
        int g = 1 + ((key.hashCode() / hashTable.size()) % (hashTable.size() - 1));
        return Math.floorMod(h + g,  hashTable.size());
    }

    private void assertNullKey(@Nullable Key key) {
        if (key == null) {
            throw new IllegalArgumentException("key can not be null");
        }
    }

    private void assertNullValue(@Nullable Value value) {
        if (value == null) {
            throw new IllegalArgumentException("value can not be null");
        }
    }

    private void initHashTable(int initSize) {
        hashTable = new ArrayList<>();
        for (int i = 0; i < initSize; ++i) {
            hashTable.add(new DictionaryList<>());
        }
    }

    private void rehash() {
        double fullness = (double) size / hashTable.size();
        ArrayList<DictionaryList<Key, Value>> oldTable = hashTable;

        if (fullness > highCapacityLevel) {
            initHashTable(2 * oldTable.size());
        } else if (fullness < lowCapacityLevel && hashTable.size() != MIN_SIZE_OF_TABLE) {
            initHashTable(oldTable.size() / 2);
        } else {
            return;
        }

        for (DictionaryList<Key, Value> list : oldTable) {
            Iterator<DictionaryPair<Key, Value>> itr = list.listIterator();
            while (itr.hasNext()) {
                DictionaryPair<Key, Value> pair = itr.next();
                int ind = getHash(pair.getKey());
                hashTable.get(ind).put(pair.getKey(), pair.getValue());
            }
        }
    }
}
