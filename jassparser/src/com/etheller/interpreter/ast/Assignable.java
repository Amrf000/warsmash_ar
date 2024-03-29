package com.etheller.interpreter.ast;

import com.etheller.interpreter.ast.value.JassType;
import com.etheller.interpreter.ast.value.JassValue;
import com.etheller.interpreter.ast.value.visitor.JassTypeGettingValueVisitor;

public class Assignable {
    private final JassType type;
    private JassValue value;

    public Assignable(final JassType type) {
        this.type = type;
    }

    public JassValue getValue() {
        return this.value;
    }

    public void setValue(final JassValue value) {
        if (value == null) {
            if (!this.type.isNullable()) {
                throw new RuntimeException("Type " + this.type.getName() + " cannot be assigned to null!");
            }
            this.value = this.type.getNullValue();
        } else {
            final JassType valueType = value.visit(JassTypeGettingValueVisitor.getInstance());
            if (!this.type.isAssignableFrom(valueType)) {
                throw new RuntimeException("Incompatible types " + valueType.getName() + " != " + this.type.getName());
            }
            this.value = value;
        }
    }

    public JassType getType() {
        return this.type;
    }
}
