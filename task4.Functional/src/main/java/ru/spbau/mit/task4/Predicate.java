package ru.spbau.mit.task4;

import org.jetbrains.annotations.NotNull;

public interface Predicate<T> extends Function1<T, Boolean>{

    @NotNull
    default Predicate<T> or (@NotNull Predicate<? super T> predicate) {
        return x -> apply(x) || predicate.apply(x);
    }

    @NotNull
    default Predicate<T> and (@NotNull Predicate<? super T> predicate) {
        return x -> apply(x) && predicate.apply(x);
    }

    @NotNull
    default Predicate<T> not () {
        return x -> !apply(x);
    }

    Predicate<Object> ALWAYS_TRUE = x -> true;
    Predicate<Object> ALWAYS_FALSE = x -> false;
}
