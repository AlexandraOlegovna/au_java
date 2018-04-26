package ru.spbau.mit.task4;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Collections {

    private Collections() { }

    @NotNull
    public static <T, R> Iterable<R> map(@NotNull Function1<? super T, R> f,
                                         @NotNull Iterable<? extends T> list) {
        List<R> newList = new ArrayList<>();
        for (T i : list) {
            newList.add(f.apply(i));
        }
        return newList;
    }

    @NotNull
    public static <T> Iterable<T> filter(@NotNull Predicate<? super T> f,
                                         @NotNull Iterable<? extends T> list) {
        List<T> newList = new ArrayList<>();
        for (T i : list) {
            if (f.apply(i)) {
                newList.add(i);
            }
        }
        return newList;
    }

    @NotNull
    public static <T> Iterable<T> takeWhile(@NotNull Predicate<? super T> f,
                                            @NotNull Iterable<? extends T> list) {
        List<T> newList = new ArrayList<>();
        for (T i : list) {
            if (f.apply(i)) {
                newList.add(i);
            } else {
                break;
            }
        }
        return newList;
    }

    @NotNull
    public static <T> Iterable<T> takeUnless(@NotNull Predicate<? super T> f,
                                             @NotNull Iterable<? extends T> list) {
        return takeWhile(f.not(), list);
    }

    @NotNull
    public static <T, S> S foldl(@NotNull Function2<? super S, ? super T, ? extends S> f,
                                 S acc,
                                 @NotNull Iterable<? extends T> list) {
        for (T i : list) {
            acc = f.apply(acc, i);
        }
        return acc;
    }

    @NotNull
    public static <T, S> S foldr(@NotNull Function2<? super T, ? super S, ? extends S> f,
                                 S acc,
                                 @NotNull Iterable<? extends T> list) {
        return foldrIterator(f, acc, list.iterator());
    }

    @NotNull
    private static <T, S> S foldrIterator(@NotNull Function2<? super T, ? super S, ? extends S> f,
                                          S acc,
                                          @NotNull Iterator<? extends T> listIter) {
        if (listIter.hasNext()) {
            T elem = listIter.next();
            acc = foldrIterator(f, acc, listIter);
            return f.apply(elem, acc);
        } else {
            return acc;
        }
    }
}
