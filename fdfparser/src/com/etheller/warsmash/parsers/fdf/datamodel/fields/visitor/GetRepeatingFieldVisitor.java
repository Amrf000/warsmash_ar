package com.etheller.warsmash.parsers.fdf.datamodel.fields.visitor;

import com.etheller.warsmash.parsers.fdf.datamodel.fields.*;

import java.util.List;

public class GetRepeatingFieldVisitor implements FrameDefinitionFieldVisitor<List<FrameDefinitionField>> {
    public static final GetRepeatingFieldVisitor INSTANCE = new GetRepeatingFieldVisitor();

    @Override
    public List<FrameDefinitionField> accept(final RepeatingFrameDefinitionField field) {
        return field.getFields();
    }

    @Override
    public List<FrameDefinitionField> accept(final StringFrameDefinitionField field) {
        return null;
    }

    @Override
    public List<FrameDefinitionField> accept(final StringPairFrameDefinitionField field) {
        return null;
    }

    @Override
    public List<FrameDefinitionField> accept(final FloatFrameDefinitionField field) {
        return null;
    }

    @Override
    public List<FrameDefinitionField> accept(final Vector3FrameDefinitionField field) {
        return null;
    }

    @Override
    public List<FrameDefinitionField> accept(final Vector4FrameDefinitionField field) {
        return null;
    }

    @Override
    public List<FrameDefinitionField> accept(final Vector2FrameDefinitionField field) {
        return null;
    }

    @Override
    public List<FrameDefinitionField> accept(final FontFrameDefinitionField field) {
        return null;
    }

    @Override
    public List<FrameDefinitionField> accept(final TextJustifyFrameDefinitionField field) {
        return null;
    }

    @Override
    public List<FrameDefinitionField> accept(final MenuItemFrameDefinitionField field) {
        return null;
    }

}
