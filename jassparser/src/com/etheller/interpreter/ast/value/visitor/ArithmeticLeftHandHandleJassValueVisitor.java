package com.etheller.interpreter.ast.value.visitor;

import com.etheller.interpreter.ast.expression.ArithmeticSign;
import com.etheller.interpreter.ast.value.*;

public class ArithmeticLeftHandHandleJassValueVisitor implements JassValueVisitor<JassValue> {
    public static final ArithmeticLeftHandHandleJassValueVisitor INSTANCE = new ArithmeticLeftHandHandleJassValueVisitor();
    private HandleJassValue leftHand;
    private ArithmeticSign sign;

    public ArithmeticLeftHandHandleJassValueVisitor reset(final HandleJassValue leftHand, final ArithmeticSign sign) {
        this.leftHand = leftHand;
        this.sign = sign;
        return this;
    }

    @Override
    public JassValue accept(final BooleanJassValue value) {
        throw new UnsupportedOperationException("Cannot perform handle comparison on boolean");
    }

    @Override
    public JassValue accept(final IntegerJassValue value) {
        throw new UnsupportedOperationException("Cannot perform handle comparison on integer");
    }

    @Override
    public JassValue accept(final RealJassValue value) {
        throw new UnsupportedOperationException("Cannot perform handle comparison on real");
    }

    @Override
    public JassValue accept(final StringJassValue value) {
        throw new UnsupportedOperationException("Cannot perform handle comparison on string");
    }

    @Override
    public JassValue accept(final CodeJassValue value) {
        throw new UnsupportedOperationException("Cannot perform handle comparison on code");
    }

    @Override
    public JassValue accept(final ArrayJassValue value) {
        throw new UnsupportedOperationException("Cannot perform handle comparison on array");
    }

    @Override
    public JassValue accept(final HandleJassValue value) {
        return this.sign.apply(this.leftHand, value);
    }

}
