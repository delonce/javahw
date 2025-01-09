package org.delonce;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        Predicate<Object> condition= Objects::isNull;
        Function<Object,Integer>ifTrue=obj->0;
        Function<CharSequence,Integer> ifFalse=CharSequence::length;

        Function<String,Integer> safeStringLength = ternaryOperator(condition, ifTrue, ifFalse);

        System.out.println(safeStringLength.apply("hello"));
        System.out.println(safeStringLength.apply(null));
    }

    private static <T, R> Function<T, R> ternaryOperator(Predicate<Object> condition, Function<? super T, R> ifTrue, Function<? super T, R> ifFalse) {
        return s -> condition.test(s) ? ifTrue.apply(s) : ifFalse.apply(s);
    }
}