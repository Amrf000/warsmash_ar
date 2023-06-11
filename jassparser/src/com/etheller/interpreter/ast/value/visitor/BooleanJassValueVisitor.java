package com.etheller.interpreter.ast.value.visitor;

import com.etheller.interpreter.ast.value.*;

public class BooleanJassValueVisitor implements JassValueVisitor<Boolean> {
    private static final BooleanJassValueVisitor INSTANCE = new BooleanJassValueVisitor();

    public static BooleanJassValueVisitor getInstance() {
        return INSTANCE;
    }

    @Override
    public Boolean accept(final IntegerJassValue value) {
        throw new IllegalStateException("Unable to convert " + value + " to boolean");
    }

    @Override
    public Boolean accept(final RealJassValue value) {
        throw new IllegalStateException("Unable to convert " + value + " to boolean");
    }

    @Override
    public Boolean accept(final BooleanJassValue value) {
        return value.getValue();
    }

    @Override
    public Boolean accept(final StringJassValue value) {
        throw new IllegalStateException("Unable to convert " + value + " to boolean");
    }

    @Override
    public Boolean accept(final CodeJassValue value) {
        throw new IllegalStateException("Unable to convert " + value + " to boolean");
    }

    @Override
    public Boolean accept(final ArrayJassValue value) {
        throw new IllegalStateException("Unable to convert " + value + " to boolean");
    }

    @Override
    public Boolean accept(final HandleJassValue value) {
        throw new IllegalStateException("Unable to convert " + value + " to boolean");
    }

}
