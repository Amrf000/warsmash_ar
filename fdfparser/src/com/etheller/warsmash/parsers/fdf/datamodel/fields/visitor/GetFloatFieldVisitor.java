package com.etheller.warsmash.parsers.fdf.datamodel.fields.visitor;

import com.etheller.warsmash.parsers.fdf.datamodel.fields.*;

public class GetFloatFieldVisitor implements FrameDefinitionFieldVisitor<Float> {
    public static final GetFloatFieldVisitor INSTANCE = new GetFloatFieldVisitor();

    @Override
    public Float accept(final RepeatingFrameDefinitionField field) {
        return null;
    }

    @Override
    public Float accept(final StringFrameDefinitionField field) {
        return null;
    }

    @Override
    public Float accept(final StringPairFrameDefinitionField field) {
        return null;
    }

    @Override
    public Float accept(final FloatFrameDefinitionField field) {
        return field.getValue();
    }

    @Override
    public Float accept(final Vector3FrameDefinitionField field) {
        return null;
    }

    @Override
    public Float accept(final Vector4FrameDefinitionField field) {
        return null;
    }

    @Override
    public Float accept(final Vector2FrameDefinitionField field) {
        return null;
    }

    @Override
    public Float accept(final FontFrameDefinitionField field) {
        return null;
    }

    @Override
    public Float accept(final TextJustifyFrameDefinitionField field) {
        return null;
    }

    @Override
    public Float accept(final MenuItemFrameDefinitionField field) {
        return null;
    }

}
