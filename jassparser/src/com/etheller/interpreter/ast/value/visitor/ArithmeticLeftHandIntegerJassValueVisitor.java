package com.etheller.interpreter.ast.value.visitor;

import com.etheller.interpreter.ast.expression.ArithmeticSign;
import com.etheller.interpreter.ast.value.*;

public class ArithmeticLeftHandIntegerJassValueVisitor implements JassValueVisitor<JassValue> {
    public static final ArithmeticLeftHandIntegerJassValueVisitor INSTANCE = new ArithmeticLeftHandIntegerJassValueVisitor();
    private IntegerJassValue leftHand;
    private ArithmeticSign sign;

    public ArithmeticLeftHandIntegerJassValueVisitor reset(final IntegerJassValue leftHand, final ArithmeticSign sign) {
        this.leftHand = leftHand;
        this.sign = sign;
        return this;
    }

    @Override
    public JassValue accept(final BooleanJassValue value) {
        throw new UnsupportedOperationException("Cannot perform integer arithmetic on boolean");
    }

    @Override
    public JassValue accept(final IntegerJassValue value) {
        return this.sign.apply(this.leftHand, value);
    }

    @Override
    public JassValue accept(final RealJassValue value) {
        return this.sign.apply(this.leftHand, value);
    }

    @Override
    public JassValue accept(final StringJassValue value) {
        throw new UnsupportedOperationException("Cannot perform arithmetic on string (Did you mean to use I2S?)");
        // uncomment the below if you decide you build a mod where I2S is no longer
        // necessary, probably:
//		return new StringJassValue(this.leftHand.toString() + value.getValue());
    }

    @Override
    public JassValue accept(final CodeJassValue value) {
        throw new UnsupportedOperationException("Cannot perform arithmetic on code");
    }

    @Override
    public JassValue accept(final ArrayJassValue value) {
        throw new UnsupportedOperationException("Cannot perform arithmetic on array");
    }

    @Override
    public JassValue accept(final HandleJassValue value) {
        throw new UnsupportedOperationException("Cannot perform arithmetic on handle");
    }

}
