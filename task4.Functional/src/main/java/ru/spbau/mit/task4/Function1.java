package ru.spbau.mit.task4;

import org.jetbrains.annotations.NotNull;

public interface Function1<T, R> {
    R apply(T x);

    @NotNull
    default <G> Function1<T, G> compose(@NotNull Function1<? super R, G> g) {
        return x -> g.apply(apply(x));
    }

}
