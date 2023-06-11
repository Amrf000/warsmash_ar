package com.etheller.warsmash.viewer5.handlers.w3x.simulation.abilities.hero;

public enum CPrimaryAttribute {
    STRENGTH,
    INTELLIGENCE,
    AGILITY;

    public static CPrimaryAttribute parsePrimaryAttribute(final String targetTypeString) {
        if (targetTypeString == null) {
            return STRENGTH;
        }
        switch (targetTypeString.toUpperCase()) {
            case "INT":
                return INTELLIGENCE;
            case "AGI":
                return AGILITY;
            case "STR":
            default:
                return STRENGTH;
        }
    }
}
