package ru.spbau.mit.task4;

import org.jetbrains.annotations.NotNull;

public interface Function2<T, V, R> {
    R apply(T x, V y);

    @NotNull
    default <G> Function2<T, V, G> compose(@NotNull Function1<? super R, G> g) {
        return (x, y) -> g.apply(apply(x, y));
    }

    @NotNull
    default Function1<V, R> bind1(T x) {
        return y -> apply(x, y);
    }

    @NotNull
    default Function1<T, R> bind2(V y) {
        return x -> apply(x, y);
    }

    @NotNull
    default Function1<T, Function1<V, R>> curry() {
        return Function2.this::bind1;
    }


}
