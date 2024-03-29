package com.etheller.interpreter.ast.value.visitor;

import com.etheller.interpreter.ast.function.JassFunction;
import com.etheller.interpreter.ast.value.*;

public class JassFunctionJassValueVisitor implements JassValueVisitor<JassFunction> {
    private static final JassFunctionJassValueVisitor INSTANCE = new JassFunctionJassValueVisitor();

    public static JassFunctionJassValueVisitor getInstance() {
        return INSTANCE;
    }

    @Override
    public JassFunction accept(final IntegerJassValue value) {
        return null;
    }

    @Override
    public JassFunction accept(final RealJassValue value) {
        return null;
    }

    @Override
    public JassFunction accept(final BooleanJassValue value) {
        return null;
    }

    @Override
    public JassFunction accept(final StringJassValue value) {
        return null;
    }

    @Override
    public JassFunction accept(final CodeJassValue value) {
        return value.getValue();
    }

    @Override
    public JassFunction accept(final ArrayJassValue value) {
        return null;
    }

    @Override
    public JassFunction accept(final HandleJassValue value) {
        return null;
    }

}
