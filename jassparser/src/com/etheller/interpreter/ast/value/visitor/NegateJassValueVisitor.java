package com.etheller.interpreter.ast.value.visitor;

import com.etheller.interpreter.ast.value.*;

public class NegateJassValueVisitor implements JassValueVisitor<JassValue> {
    private static final NegateJassValueVisitor INSTANCE = new NegateJassValueVisitor();

    public static NegateJassValueVisitor getInstance() {
        return INSTANCE;
    }

    @Override
    public JassValue accept(final IntegerJassValue value) {
        return new IntegerJassValue(-value.getValue());
    }

    @Override
    public JassValue accept(final RealJassValue value) {
        return new RealJassValue(-value.getValue());
    }

    @Override
    public JassValue accept(final BooleanJassValue value) {
        throw new IllegalStateException("Unable to apply numeric unary negative sign to boolean");
    }

    @Override
    public JassValue accept(final StringJassValue value) {
        throw new IllegalStateException("Unable to apply numeric unary negative sign to string");
    }

    @Override
    public JassValue accept(final CodeJassValue value) {
        throw new IllegalStateException("Unable to apply numeric unary negative sign to code");
    }

    @Override
    public JassValue accept(final ArrayJassValue value) {
        throw new IllegalStateException("Unable to apply numeric unary negative sign to array");
    }

    @Override
    public JassValue accept(final HandleJassValue value) {
        throw new IllegalStateException("Unable to apply numeric unary negative sign to handle");
    }

}
