package com.etheller.interpreter.ast.value.visitor;

import com.etheller.interpreter.ast.expression.ArithmeticSign;
import com.etheller.interpreter.ast.value.*;

public class ArithmeticLeftHandNullJassValueVisitor implements JassValueVisitor<JassValue> {
    public static final ArithmeticLeftHandNullJassValueVisitor INSTANCE = new ArithmeticLeftHandNullJassValueVisitor();
    private ArithmeticSign sign;

    public ArithmeticLeftHandNullJassValueVisitor reset(final ArithmeticSign sign) {
        this.sign = sign;
        return this;
    }

    @Override
    public JassValue accept(final BooleanJassValue value) {
        throw new UnsupportedOperationException("Invalid binary operation: null and boolean");
    }

    @Override
    public JassValue accept(final IntegerJassValue value) {
        throw new UnsupportedOperationException("Invalid binary operation: null and integer");
    }

    @Override
    public JassValue accept(final RealJassValue value) {
        throw new UnsupportedOperationException("Invalid binary operation: null and real");
    }

    @Override
    public JassValue accept(final StringJassValue value) {
        return this.sign.apply(null, value.getValue());
    }

    @Override
    public JassValue accept(final CodeJassValue value) {
        throw new UnsupportedOperationException("Invalid binary operation: null and code");
    }

    @Override
    public JassValue accept(final ArrayJassValue value) {
        throw new UnsupportedOperationException("Invalid binary operation: null and array");
    }

    @Override
    public JassValue accept(final HandleJassValue value) {
        final HandleJassType rightHandType = value.getType();
        if (!rightHandType.isNullable()) {
            throw new UnsupportedOperationException("Cannot operate on null for type: " + rightHandType.getName());
        }
        // TODO would be nice not to have to call getNullValue here...
        return this.sign.apply(rightHandType.getNullValue(), value);
    }

}
