package com.etheller.interpreter.ast.value.visitor;

import com.etheller.interpreter.ast.value.*;

public class NotJassValueVisitor implements JassValueVisitor<JassValue> {
    private static final NotJassValueVisitor INSTANCE = new NotJassValueVisitor();

    public static NotJassValueVisitor getInstance() {
        return INSTANCE;
    }

    @Override
    public JassValue accept(final IntegerJassValue value) {
        throw new IllegalStateException("Unable to apply not keyword to a variable of type integer");
    }

    @Override
    public JassValue accept(final RealJassValue value) {
        throw new IllegalStateException("Unable to apply not keyword to a variable of type real");
    }

    @Override
    public JassValue accept(final BooleanJassValue value) {
        return BooleanJassValue.inverse(value);
    }

    @Override
    public JassValue accept(final StringJassValue value) {
        throw new IllegalStateException("Unable to apply not keyword to a variable of type string");
    }

    @Override
    public JassValue accept(final CodeJassValue value) {
        throw new IllegalStateException("Unable to apply not keyword to a variable of type code");
    }

    @Override
    public JassValue accept(final ArrayJassValue value) {
        throw new IllegalStateException("Unable to apply not keyword to a variable of an array type");
    }

    @Override
    public JassValue accept(final HandleJassValue value) {
        throw new IllegalStateException(
                "Unable to apply not keyword to a variable of type " + value.getType().getName());
    }

}
