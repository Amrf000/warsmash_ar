package com.etheller.warsmash.viewer5.handlers.mdx;

import com.etheller.warsmash.viewer5.handlers.EmitterObject;

import java.util.ArrayList;
import java.util.List;

public class SetupGroups {
    public static int getPrio(final Batch object) {
        return object.layer.priorityPlane;
    }

    public static int getPrio(final ParticleEmitter2Object object) {
        return object.priorityPlane;
    }

    public static int getPrio(final RibbonEmitterObject object) {
        return object.layer.priorityPlane;
    }

    public static int getPrio(final Object object) {
        if (object instanceof Batch) {
            return getPrio((Batch) object);
        } else if (object instanceof RibbonEmitterObject) {
            return getPrio((RibbonEmitterObject) object);
        } else if (object instanceof ParticleEmitter2Object) {
            return getPrio((ParticleEmitter2Object) object);
        } else {
            throw new IllegalArgumentException(object.getClass().getName());
        }
    }

    public static int getBackupPrio(final Batch object) {
        return object.layer.filterMode;
    }

    public static int getBackupPrio(final ParticleEmitter2Object object) {
        return object.filterModeForSort;
    }

    public static int getBackupPrio(final RibbonEmitterObject object) {
        return object.layer.filterMode;
    }

    public static float getBackup2Prio(final Batch object) {
        return (object.geoset.mdlxGeoset.extent.max[2] + object.geoset.mdlxGeoset.extent.min[2]) / 2;
    }

    public static float getBackup2Prio(final ParticleEmitter2Object object) {
        return object.pivot[2];
    }

    public static float getBackup2Prio(final RibbonEmitterObject object) {
        return object.pivot[2];
    }

    public static int getBackupPrio(final Object object) {
        if (object instanceof Batch) {
            return getBackupPrio((Batch) object);
        } else if (object instanceof RibbonEmitterObject) {
            return getBackupPrio((RibbonEmitterObject) object);
        } else if (object instanceof ParticleEmitter2Object) {
            return getBackupPrio((ParticleEmitter2Object) object);
        } else {
            throw new IllegalArgumentException(object.getClass().getName());
        }
    }

    public static float getBackup2Prio(final Object object) {
        if (object instanceof Batch) {
            return getBackup2Prio((Batch) object);
        } else if (object instanceof RibbonEmitterObject) {
            return getBackup2Prio((RibbonEmitterObject) object);
        } else if (object instanceof ParticleEmitter2Object) {
            return getBackup2Prio((ParticleEmitter2Object) object);
        } else {
            throw new IllegalArgumentException(object.getClass().getName());
        }
    }

    public static boolean matchingGroup(final Object group, final Object object) {
        if (group instanceof BatchGroup) {
            return (!(object instanceof Batch)) || (((Batch) object).skinningType != ((BatchGroup) group).skinningType)
                    || (((Batch) object).hd != ((BatchGroup) group).hd);
        } else {
            // All of the emitter objects are generic objects.
            return (!(object instanceof GenericObject));
        }
    }

    public static GenericGroup createMatchingGroup(final MdxModel model, final Object object) {
        if (object instanceof Batch) {
            return new BatchGroup(model, ((Batch) object).skinningType, ((Batch) object).hd);
        } else {
            return new EmitterGroup();
        }
    }

    public static void setupGroups(final MdxModel model) {
        final List<Batch> opaqueBatches = new ArrayList<>();
        final List<Batch> translucentBatches = new ArrayList<>();

        for (final Batch batch : model.batches) {// TODO reforged
            if (batch.layer.filterMode < 2) {
                opaqueBatches.add(batch);
            } else {
                translucentBatches.add(batch);
            }
        }

        final List<GenericGroup> opaqueGroups = model.opaqueGroups;
        final List<GenericGroup> translucentGroups = model.translucentGroups;
        GenericGroup currentGroup = null;

        for (final Batch object : opaqueBatches) {
            if ((currentGroup == null) || matchingGroup(currentGroup, object)) {
                currentGroup = createMatchingGroup(model, object);

                opaqueGroups.add(currentGroup);
            }

            final int index = object.index;
            currentGroup.objects.add(index);
        }

        // Sort between all of the translucent batches and emitters that have priority
        // planes
        final List<Object> sorted = new ArrayList<>();
        sorted.addAll(translucentBatches);
        sorted.addAll(model.particleEmitters2);
        sorted.addAll(model.ribbonEmitters);
        sorted.sort((o1, o2) -> {
            final int priorityDifference = getPrio(o1) - getPrio(o2);
            if (priorityDifference == 0) {
                final int backupPriorityDifference = getBackupPrio(o1) - getBackupPrio(o2);
                if (backupPriorityDifference == 0) {
                    return Float.compare(getBackup2Prio(o1), getBackup2Prio(o2));
                }
                return backupPriorityDifference;
            }
            return priorityDifference;
        });

        // Event objects have no priority planes, so they might as well always be last.
        final List<Object> objects = new ArrayList<>();
        objects.addAll(sorted);
        objects.addAll(model.eventObjects);

        currentGroup = null;

        for (final Object object : objects) { // TODO reforged
            if ((object instanceof Batch /* || object instanceof ReforgedBatch */)
                    || (object instanceof EmitterObject)) {
                if ((currentGroup == null) || matchingGroup(currentGroup, object)) {
                    currentGroup = createMatchingGroup(model, object);

                    translucentGroups.add(currentGroup);
                }

                final int index = ((GenericIndexed) object).getIndex();
                currentGroup.objects.add(index);
            }
        }
    }
}
