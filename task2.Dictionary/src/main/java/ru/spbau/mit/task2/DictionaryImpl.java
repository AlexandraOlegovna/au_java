package ru.spbau.mit.task2;

import java.util.ArrayList;
import java.util.Iterator;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DictionaryImpl<Key, Value> implements Dictionary<Key, Value> {

    private final int MIN_SIZE_OF_TABLE = 16;
    private double lowCapacityLevel;
    private double highCapacityLevel;
    private ArrayList<DictionaryList<Key, Value>> hashTable;
    private int size = 0;

    public DictionaryImpl() {
        this(0.2, 0.8);
    }

    public DictionaryImpl(double low_level, double high_level) {
        lowCapacityLevel = low_level;
        highCapacityLevel = high_level;
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
    public Value get(@Nullable Key key) {
        assertNullKey(key);
        int ind = getHash(key);
        return hashTable.get(ind).find(key);
    }

    @Override
    public Value put(@Nullable Key key, @Nullable Value value) {
        assertNullKey(key);
        assertNullValue(value);
        int ind = getHash(key);
        Value result = hashTable.get(ind).put(key, value);
        size += (result == null) ? 1 : 0;
        rehash();
        return result;
    }

    @Override
    public Value remove(@Nullable Key key) {
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

    private int getHash(@NotNull Key key) {
        int h = key.hashCode() % hashTable.size();
        int g = 1 + ((key.hashCode() / hashTable.size()) % (hashTable.size() - 1));
        return (h + g) % hashTable.size();
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
