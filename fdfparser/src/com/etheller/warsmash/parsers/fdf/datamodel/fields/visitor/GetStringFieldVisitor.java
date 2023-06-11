package com.etheller.warsmash.parsers.fdf.datamodel.fields.visitor;

import com.etheller.warsmash.parsers.fdf.datamodel.fields.*;

public class GetStringFieldVisitor implements FrameDefinitionFieldVisitor<String> {
    public static final GetStringFieldVisitor INSTANCE = new GetStringFieldVisitor();

    @Override
    public String accept(final RepeatingFrameDefinitionField field) {
        return null;
    }

    @Override
    public String accept(final StringFrameDefinitionField field) {
        return field.getValue();
    }

    @Override
    public String accept(final StringPairFrameDefinitionField field) {
        return null;
    }

    @Override
    public String accept(final FloatFrameDefinitionField field) {
        return null;
    }

    @Override
    public String accept(final Vector3FrameDefinitionField field) {
        return null;
    }

    @Override
    public String accept(final Vector4FrameDefinitionField field) {
        return null;
    }

    @Override
    public String accept(final Vector2FrameDefinitionField field) {
        return null;
    }

    @Override
    public String accept(final FontFrameDefinitionField field) {
        return null;
    }

    @Override
    public String accept(final TextJustifyFrameDefinitionField field) {
        return null;
    }

    @Override
    public String accept(final MenuItemFrameDefinitionField field) {
        return null;
    }

}
