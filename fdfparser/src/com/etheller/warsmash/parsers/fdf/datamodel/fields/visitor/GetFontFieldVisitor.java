package com.etheller.warsmash.parsers.fdf.datamodel.fields.visitor;

import com.etheller.warsmash.parsers.fdf.datamodel.FontDefinition;
import com.etheller.warsmash.parsers.fdf.datamodel.fields.*;

public class GetFontFieldVisitor implements FrameDefinitionFieldVisitor<FontDefinition> {
    public static final GetFontFieldVisitor INSTANCE = new GetFontFieldVisitor();

    @Override
    public FontDefinition accept(final RepeatingFrameDefinitionField field) {
        return null;
    }

    @Override
    public FontDefinition accept(final StringFrameDefinitionField field) {
        return null;
    }

    @Override
    public FontDefinition accept(final StringPairFrameDefinitionField field) {
        return null;
    }

    @Override
    public FontDefinition accept(final FloatFrameDefinitionField field) {
        return null;
    }

    @Override
    public FontDefinition accept(final Vector3FrameDefinitionField field) {
        return null;
    }

    @Override
    public FontDefinition accept(final Vector4FrameDefinitionField field) {
        return null;
    }

    @Override
    public FontDefinition accept(final Vector2FrameDefinitionField field) {
        return null;
    }

    @Override
    public FontDefinition accept(final FontFrameDefinitionField field) {
        return field.getValue();
    }

    @Override
    public FontDefinition accept(final TextJustifyFrameDefinitionField field) {
        return null;
    }

    @Override
    public FontDefinition accept(final MenuItemFrameDefinitionField field) {
        return null;
    }

}
