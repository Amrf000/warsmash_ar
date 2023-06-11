package com.etheller.warsmash.desktop.editor.mdx.ui;

import com.etheller.warsmash.WarsmashPreviewApplication;
import com.etheller.warsmash.desktop.editor.mdx.listeners.YseraGUIListener;
import com.etheller.warsmash.viewer5.handlers.mdx.MdxComplexInstance;
import com.etheller.warsmash.viewer5.handlers.mdx.SequenceLoopMode;
import com.hiveworkshop.rms.parsers.mdlx.MdlxModel;
import com.hiveworkshop.rms.parsers.mdlx.MdlxSequence;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;
import java.awt.event.ActionListener;

public class AnimationControllerPanel extends JPanel implements YseraGUIListener {
    private final WarsmashPreviewApplication previewApplication;
    private final DefaultComboBoxModel<MdlxSequence> animations;
    private final JComboBox<MdlxSequence> animationBox;
    private final JRadioButton defaultLoopButton;
    private final JRadioButton alwaysLoopButton;
    private final JRadioButton neverLoopButton;
    private final JSlider speedSlider;
    private final JLabel speedSliderLabel;
    private MdlxModel model;

    public AnimationControllerPanel(final WarsmashPreviewApplication previewApplication) {
        this.previewApplication = previewApplication;

        this.animations = new DefaultComboBoxModel<>();
        repopulateSequenceList();
        this.animationBox = new JComboBox<>(this.animations);
        this.animationBox.setRenderer(new BasicComboBoxRenderer() {
            @Override
            public Component getListCellRendererComponent(final JList list, final Object value, final int index,
                                                          final boolean isSelected, final boolean cellHasFocus) {
                Object display = value == null ? "(Unanimated)" : ((MdlxSequence) value).getName();
                if ((value != null) && (AnimationControllerPanel.this.model != null)) {
                    display = "(" + AnimationControllerPanel.this.model.getSequences().indexOf(value) + ") " + display;
                }
                return super.getListCellRendererComponent(list, display, index, isSelected, cellHasFocus);
            }
        });
        this.animationBox.addActionListener(e -> update(true));
        this.animationBox.setMaximumSize(new Dimension(99999999, 35));
        this.animationBox.setFocusable(true);
        this.animationBox.addMouseWheelListener(e -> {
            final int wheelRotation = e.getWheelRotation();
            int previousSelectedIndex = AnimationControllerPanel.this.animationBox.getSelectedIndex();
            if (previousSelectedIndex < 0) {
                previousSelectedIndex = 0;
            }
            int newIndex = previousSelectedIndex + wheelRotation;
            if (newIndex > (AnimationControllerPanel.this.animations.getSize() - 1)) {
                newIndex = AnimationControllerPanel.this.animations.getSize() - 1;
            } else if (newIndex < 0) {
                newIndex = 0;
            }
            if (newIndex != previousSelectedIndex) {
                AnimationControllerPanel.this.animationBox.setSelectedIndex(newIndex);
            }
        });
        this.speedSlider = new JSlider(0, 100, 50);
        this.speedSliderLabel = new JLabel("Speed: 100%");
        this.speedSlider.addChangeListener(e -> update(false));

        final JButton playAnimationButton = new JButton("Play Animation");
        final ActionListener playAnimationActionListener = e -> update(true);
        playAnimationButton.addActionListener(playAnimationActionListener);

        this.defaultLoopButton = new JRadioButton("Default Loop");
        this.alwaysLoopButton = new JRadioButton("Always Loop");
        this.neverLoopButton = new JRadioButton("Never Loop");

        final ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(this.defaultLoopButton);
        buttonGroup.add(this.alwaysLoopButton);
        buttonGroup.add(this.neverLoopButton);
        final ActionListener setLoopTypeActionListener = e -> update(true);
        this.defaultLoopButton.addActionListener(setLoopTypeActionListener);
        this.alwaysLoopButton.addActionListener(setLoopTypeActionListener);
        this.neverLoopButton.addActionListener(setLoopTypeActionListener);

        final JLabel levelOfDetailLabel = new JLabel("Level of Detail");
        final JSpinner levelOfDetailSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 5, 1));
        levelOfDetailSpinner.addChangeListener(e -> {
//				listener.setLevelOfDetail(((Number) levelOfDetailSpinner.getValue()).intValue());
        });
        levelOfDetailSpinner.setMaximumSize(new Dimension(99999, 25));
        levelOfDetailLabel.setVisible(false);
        levelOfDetailSpinner.setVisible(false);

        final GroupLayout groupLayout = new GroupLayout(this);

        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup().addComponent(this.animationBox)
                .addGroup(groupLayout.createSequentialGroup().addGap(8)
                        .addGroup(groupLayout.createParallelGroup().addComponent(playAnimationButton)
                                .addComponent(this.defaultLoopButton).addComponent(this.alwaysLoopButton)
                                .addComponent(this.neverLoopButton).addComponent(this.speedSliderLabel)
                                .addComponent(this.speedSlider).addComponent(levelOfDetailLabel)
                                .addComponent(levelOfDetailSpinner))
                        .addGap(8)

                ));
        groupLayout.setVerticalGroup(groupLayout.createSequentialGroup().addComponent(this.animationBox).addGap(32)
                .addComponent(playAnimationButton).addGap(16).addComponent(this.defaultLoopButton)
                .addComponent(this.alwaysLoopButton).addComponent(this.neverLoopButton).addGap(16)
                .addComponent(this.speedSliderLabel).addComponent(this.speedSlider).addGap(16)
                .addComponent(levelOfDetailLabel).addComponent(levelOfDetailSpinner)

        );
        setLayout(groupLayout);

        this.defaultLoopButton.doClick();
    }

    private void update(final boolean playSequence) {
        SequenceLoopMode loopType;
        if (this.defaultLoopButton.isSelected()) {
            loopType = SequenceLoopMode.MODEL_LOOP;
        } else if (this.alwaysLoopButton.isSelected()) {
            loopType = SequenceLoopMode.ALWAYS_LOOP;
        } else if (this.neverLoopButton.isSelected()) {
            loopType = SequenceLoopMode.NEVER_LOOP;
        } else {
            throw new IllegalStateException();
        }
        this.speedSliderLabel.setText("Speed: " + (this.speedSlider.getValue() * 2) + "%");
        final MdxComplexInstance mainInstance = this.previewApplication.getMainInstance();
        if (mainInstance != null) {
            mainInstance.setAnimationSpeed(this.speedSlider.getValue() / 50f);
            mainInstance.setSequenceLoopMode(loopType);
            if (playSequence) {
                mainInstance.setSequence(this.animationBox.getSelectedIndex() - 1);
            }
        }
    }

    @Override
    public void openModel(final MdlxModel model) {
        this.model = model;

        repopulateSequenceList();
    }

    private void repopulateSequenceList() {
        this.animations.removeAllElements();
        this.animations.addElement(null);
        if (this.model != null) {
            for (final MdlxSequence animation : this.model.getSequences()) {
                this.animations.addElement(animation);
            }
        }
    }

    @Override
    public void stateChanged() {

    }
}
