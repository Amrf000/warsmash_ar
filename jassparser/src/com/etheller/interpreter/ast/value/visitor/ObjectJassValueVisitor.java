package com.etheller.interpreter.ast.value.visitor;

import com.etheller.interpreter.ast.value.*;

public class ObjectJassValueVisitor<T> implements JassValueVisitor<T> {
    private static final ObjectJassValueVisitor<?> INSTANCE = new ObjectJassValueVisitor();

    public static <V> ObjectJassValueVisitor<V> getInstance() {
        return (ObjectJassValueVisitor<V>) INSTANCE;
    }

    @Override
    public T accept(final IntegerJassValue value) {
        return null;
    }

    @Override
    public T accept(final RealJassValue value) {
        return null;
    }

    @Override
    public T accept(final BooleanJassValue value) {
        return null;
    }

    @Override
    public T accept(final StringJassValue value) {
        return null;
    }

    @Override
    public T accept(final CodeJassValue value) {
        return null;
    }

    @Override
    public T accept(final ArrayJassValue value) {
        return null;
    }

    @Override
    public T accept(final HandleJassValue value) {
        return (T) value.getJavaValue();
    }

}
