package ru.spbau.mit.task2;

import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DictionaryPair<Key, Value> {

    private final Key key;
    private final Value value;

    DictionaryPair(@NotNull Key k, @Nullable Value v) {
        this.key = k;
        this.value = v;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DictionaryPair<?, ?> that = (DictionaryPair<?, ?>) o;
        return Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    public Key getKey() {
        return key;
    }

    public Value getValue() {
        return value;
    }
}
