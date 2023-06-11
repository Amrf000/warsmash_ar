package com.etheller.interpreter.ast.scope;

import com.etheller.interpreter.ast.Assignable;
import com.etheller.interpreter.ast.scope.variableevent.VariableEvent;
import com.etheller.interpreter.ast.value.JassType;
import com.etheller.interpreter.ast.value.JassValue;
import com.etheller.interpreter.ast.value.visitor.RealJassValueVisitor;

import java.util.ArrayList;
import java.util.List;

public class GlobalScopeAssignable extends Assignable {
    private final List<VariableEvent> variableEvents = new ArrayList<>();
    private final GlobalScope globalScope;

    public GlobalScopeAssignable(final JassType type, final GlobalScope globalScope) {
        super(type);
        this.globalScope = globalScope;
    }

    @Override
    public void setValue(final JassValue value) {
        final JassValue prevValue = getValue();
        super.setValue(value);
        if (!this.variableEvents.isEmpty()) {
            final Double prevReal = prevValue.visit(RealJassValueVisitor.getInstance());
            final Double newReal = value.visit(RealJassValueVisitor.getInstance());
            for (final VariableEvent variableEvent : this.variableEvents) {
                if (!variableEvent.isMatching(prevReal)
                        && variableEvent.isMatching(newReal)) {
                    variableEvent.fire(this.globalScope);
                }
            }
        }
    }

    public void add(final VariableEvent variableEvent) {
        this.variableEvents.add(variableEvent);
    }

    public void remove(final VariableEvent variableEvent) {
        this.variableEvents.remove(variableEvent);
    }

}
