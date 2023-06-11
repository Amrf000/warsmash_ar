package com.etheller.warsmash.parsers.fdf.datamodel.fields.visitor;

import com.etheller.warsmash.parsers.fdf.datamodel.fields.*;

public class GetStringPairFieldVisitor implements FrameDefinitionFieldVisitor<StringPairFrameDefinitionField> {
    public static final GetStringPairFieldVisitor INSTANCE = new GetStringPairFieldVisitor();

    @Override
    public StringPairFrameDefinitionField accept(final RepeatingFrameDefinitionField field) {
        return null;
    }

    @Override
    public StringPairFrameDefinitionField accept(final StringFrameDefinitionField field) {
        return null;
    }

    @Override
    public StringPairFrameDefinitionField accept(final StringPairFrameDefinitionField field) {
        return field;
    }

    @Override
    public StringPairFrameDefinitionField accept(final FloatFrameDefinitionField field) {
        return null;
    }

    @Override
    public StringPairFrameDefinitionField accept(final Vector3FrameDefinitionField field) {
        return null;
    }

    @Override
    public StringPairFrameDefinitionField accept(final Vector4FrameDefinitionField field) {
        return null;
    }

    @Override
    public StringPairFrameDefinitionField accept(final Vector2FrameDefinitionField field) {
        return null;
    }

    @Override
    public StringPairFrameDefinitionField accept(final FontFrameDefinitionField field) {
        return null;
    }

    @Override
    public StringPairFrameDefinitionField accept(final TextJustifyFrameDefinitionField field) {
        return null;
    }

    @Override
    public StringPairFrameDefinitionField accept(final MenuItemFrameDefinitionField field) {
        return null;
    }

}
