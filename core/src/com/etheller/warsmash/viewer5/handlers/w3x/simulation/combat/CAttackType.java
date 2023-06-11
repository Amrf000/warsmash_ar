package com.etheller.warsmash.viewer5.handlers.w3x.simulation.combat;

import com.etheller.interpreter.ast.util.CHandle;

public enum CAttackType implements CodeKeyType, CHandle {
    UNKNOWN,
    NORMAL,
    PIERCE,
    SIEGE,
    SPELLS,
    CHAOS,
    MAGIC,
    HERO;

    public static final CAttackType[] VALUES = values();

    private final String codeKey;
    private final String damageKey;

    CAttackType() {
        final String name = name();
        final String computedCodeKey = name.charAt(0) + name.substring(1).toLowerCase();
        if (computedCodeKey.equals("Spells")) {
            this.codeKey = "Magic";
        } else {
            this.codeKey = computedCodeKey;
        }
        this.damageKey = this.codeKey;
    }

    public static CAttackType parseAttackType(final String attackTypeString) {
        final String upperCaseAttackType = attackTypeString.toUpperCase();
        if ("SEIGE".equals(upperCaseAttackType)) {
            return SIEGE;
        }
        return valueOf(upperCaseAttackType);
    }

    @Override
    public String getCodeKey() {
        return this.codeKey;
    }

    public String getDamageKey() {
        return this.damageKey;
    }

    @Override
    public int getHandleId() {
        return ordinal();
    }
}
