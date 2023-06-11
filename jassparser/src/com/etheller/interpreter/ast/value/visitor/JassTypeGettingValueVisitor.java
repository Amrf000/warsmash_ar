package com.etheller.interpreter.ast.value.visitor;

import com.etheller.interpreter.ast.value.*;

public class JassTypeGettingValueVisitor implements JassValueVisitor<JassType> {
    public static final JassTypeGettingValueVisitor INSTANCE = new JassTypeGettingValueVisitor();

    public static JassTypeGettingValueVisitor getInstance() {
        return INSTANCE;
    }

    @Override
    public JassType accept(final IntegerJassValue value) {
        return JassType.INTEGER;
    }

    @Override
    public JassType accept(final RealJassValue value) {
        return JassType.REAL;
    }

    @Override
    public JassType accept(final BooleanJassValue value) {
        return JassType.BOOLEAN;
    }

    @Override
    public JassType accept(final StringJassValue value) {
        return JassType.STRING;
    }

    @Override
    public JassType accept(final CodeJassValue value) {
        return JassType.CODE;
    }

    @Override
    public JassType accept(final ArrayJassValue value) {
        return value.getType();
    }

    @Override
    public JassType accept(final HandleJassValue value) {
        return value.getType();
    }

}
