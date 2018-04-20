package ru.spbau.mit.task2;

import java.util.LinkedList;
import java.util.ListIterator;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DictionaryList<Key, Value> {

    private final LinkedList<DictionaryPair<Key, Value>> list = new LinkedList<>();

    public Value put(@NotNull Key key, @NotNull Value value) {
        DictionaryPair<Key, Value> pair = new DictionaryPair<>(key, value);

        int ind = findIndex(key);
        if (ind == -1) {
            list.add(pair);
            return null;
        }
        else {
            DictionaryPair<Key, Value> oldPair = list.get(ind);
            list.set(ind, pair);
            return oldPair.getValue();
        }
    }

    public Value remove(@NotNull Key key) {
        int ind = findIndex(key);
        if (ind != -1) {
            DictionaryPair<Key, Value> oldPair = list.get(ind);
            list.remove(ind);
            return oldPair.getValue();
        }
        return null;
    }

    public Value find(@NotNull Key key) {
        int ind = findIndex(key);
        if (ind == -1) {
            return null;
        } else {
            return list.get(ind).getValue();
        }
    }

    private int findIndex(@NotNull Key key) {
        return list.indexOf(new DictionaryPair<Key, Value>(key, null));
    }

    public ListIterator<DictionaryPair<Key, Value>> listIterator() {
        return list.listIterator();
    }
}
