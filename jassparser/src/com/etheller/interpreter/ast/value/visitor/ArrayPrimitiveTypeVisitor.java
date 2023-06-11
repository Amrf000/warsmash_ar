package com.etheller.interpreter.ast.value.visitor;

import com.etheller.interpreter.ast.value.*;

public class ArrayPrimitiveTypeVisitor implements JassTypeVisitor<JassType> {
    private static final ArrayPrimitiveTypeVisitor INSTANCE = new ArrayPrimitiveTypeVisitor();

    public static ArrayPrimitiveTypeVisitor getInstance() {
        return INSTANCE;
    }

    @Override
    public JassType accept(final PrimitiveJassType primitiveType) {
        return null;
    }

    @Override
    public JassType accept(final ArrayJassType arrayType) {
        return arrayType.getPrimitiveType();
    }

    @Override
    public JassType accept(final HandleJassType type) {
        return null;
    }

}
