package com.etheller.interpreter.ast.value.visitor;

import com.etheller.interpreter.ast.value.*;

public class RealJassValueVisitor implements JassValueVisitor<Double> {
    private static final RealJassValueVisitor INSTANCE = new RealJassValueVisitor();

    public static RealJassValueVisitor getInstance() {
        return INSTANCE;
    }

    @Override
    public Double accept(final IntegerJassValue value) {
        return (double) value.getValue();
    }

    @Override
    public Double accept(final RealJassValue value) {
        return value.getValue();
    }

    @Override
    public Double accept(final BooleanJassValue value) {
        return 0.0;
    }

    @Override
    public Double accept(final StringJassValue value) {
        return 0.0;
    }

    @Override
    public Double accept(final CodeJassValue value) {
        return 0.0;
    }

    @Override
    public Double accept(final ArrayJassValue value) {
        return 0.0;
    }

    @Override
    public Double accept(final HandleJassValue value) {
        return 0.0;
    }

}
