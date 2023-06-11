package com.etheller.warsmash.viewer5.handlers.w3x.ui.mapsetup;

import com.badlogic.gdx.utils.viewport.Viewport;
import com.etheller.warsmash.parsers.fdf.GameUI;
import com.etheller.warsmash.parsers.fdf.datamodel.MenuItem;
import com.etheller.warsmash.parsers.fdf.frames.*;
import com.etheller.warsmash.viewer5.handlers.mdx.ReplaceableIds;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.ai.AIDifficulty;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.players.CMapControl;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.players.CPlayerJass;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.players.CRacePreference;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.trigger.enumtypes.CPlayerSlotState;
import net.warsmash.uberserver.LobbyPlayerType;

import java.util.ArrayList;
import java.util.List;

public class PlayerSlotPane {
    private final SimpleFrame playerSlotFrame;
    private final StringFrame downloadValue;
    private final PopupMenuFrame nameMenu;
    private final PopupMenuFrame raceMenu;
    private final GlueButtonFrame teamButtonFrame;
    private final GlueButtonFrame colorButtonFrame;
    private final BackdropFrame colorButtonValueFrame;
    private final int index;
    private final PlayerSlotPaneListener playerSlotPaneListener;

    public PlayerSlotPane(final GameUI rootFrame, final Viewport uiViewport, final SimpleFrame container,
                          final int index, PlayerSlotPaneListener playerSlotPaneListener) {
        this.index = index;
        this.playerSlotPaneListener = playerSlotPaneListener;
        this.playerSlotFrame = (SimpleFrame) rootFrame.createFrameByType("SIMPLEFRAME", "PlayerSlot", container,
                "WITHCHILDREN");
        container.add(this.playerSlotFrame);

        this.downloadValue = (StringFrame) rootFrame.getFrameByName("DownloadValue");
        this.nameMenu = (PopupMenuFrame) rootFrame.getFrameByName("NameMenu");
        this.raceMenu = (PopupMenuFrame) rootFrame.getFrameByName("RaceMenu");
        this.teamButtonFrame = (GlueButtonFrame) rootFrame.getFrameByName("TeamButton");
        this.colorButtonFrame = (GlueButtonFrame) rootFrame.getFrameByName("ColorButton");

        // TODO this is a hole in my API to need to do this -- instead it should
        // probably inflate all frames within a frame by default
        this.colorButtonValueFrame = (BackdropFrame) rootFrame.createFrame("ColorButtonValue", this.colorButtonFrame
        );
        this.playerSlotFrame.add(this.colorButtonValueFrame);
    }

    public void setForPlayer(final GameUI rootFrame, final Viewport uiViewport, final CPlayerJass player,
                             boolean fixedPlayerSettings) {
        final List<MenuItem> nameMenuItems = new ArrayList<>();
        nameMenuItems.add(new MenuItem(rootFrame.getTemplates().getDecoratedString("OPEN"), -2));
        nameMenuItems.add(new MenuItem(rootFrame.getTemplates().getDecoratedString("CLOSED"), -2));
        nameMenuItems.add(new MenuItem(rootFrame.getTemplates().getDecoratedString("COMPUTER_NEWBIE"), -2));
        nameMenuItems.add(new MenuItem(rootFrame.getTemplates().getDecoratedString("COMPUTER_NORMAL"), -2));
        nameMenuItems.add(new MenuItem(rootFrame.getTemplates().getDecoratedString("COMPUTER_INSANE"), -2));
        setNameMenuTextByPlayer(rootFrame, player, nameMenuItems, fixedPlayerSettings);
        rootFrame.setText(this.downloadValue, "");
        this.downloadValue.setVisible(false);
        setTextFromRacePreference(rootFrame, player);
        this.colorButtonValueFrame.setBackground(rootFrame.loadTexture("ReplaceableTextures\\"
                + ReplaceableIds.getPathString(1) + ReplaceableIds.getIdString(player.getColor()) + ".blp"));

        ((MenuFrame) this.nameMenu.getPopupMenuFrame()).setItems(uiViewport, nameMenuItems);
        this.nameMenu.setMenuClickListener((button, menuItemIndex) -> {
            switch (menuItemIndex) {
                case 0:
                    // open
                    PlayerSlotPane.this.playerSlotPaneListener.setPlayerSlot(PlayerSlotPane.this.index,
                            LobbyPlayerType.OPEN);
                    break;
                case 1:
                    // close
                    PlayerSlotPane.this.playerSlotPaneListener.setPlayerSlot(PlayerSlotPane.this.index,
                            LobbyPlayerType.CLOSED);
                    break;
                case 2:
                    PlayerSlotPane.this.playerSlotPaneListener.setPlayerSlot(PlayerSlotPane.this.index,
                            LobbyPlayerType.COMPUTER_NEWBIE);
                    break;
                case 3:
                    PlayerSlotPane.this.playerSlotPaneListener.setPlayerSlot(PlayerSlotPane.this.index,
                            LobbyPlayerType.COMPUTER_NORMAL);
                    break;
                case 4:
                    PlayerSlotPane.this.playerSlotPaneListener.setPlayerSlot(PlayerSlotPane.this.index,
                            LobbyPlayerType.COMPUTER_INSANE);
                    break;
            }
        });

        this.raceMenu.setMenuClickListener((button, menuItemIndex) -> PlayerSlotPane.this.playerSlotPaneListener.setPlayerRace(PlayerSlotPane.this.index, menuItemIndex));
        this.raceMenu.setEnabled(player.isRaceSelectable());
        this.teamButtonFrame.setEnabled(player.isRaceSelectable());
        this.colorButtonFrame.setEnabled(player.isRaceSelectable());
    }

    public void setNameMenuTextByPlayer(final GameUI rootFrame, final CPlayerJass player,
                                        final List<MenuItem> nameMenuItems, boolean fixedPlayerSettings) {
        String name = player.getName();
        final CPlayerSlotState slotState = player.getSlotState();
        final CMapControl controller = player.getController();
        AIDifficulty aiDifficulty = player.getAIDifficulty();
        this.nameMenu.setEnabled(true);
        if (slotState == CPlayerSlotState.EMPTY) {
            name = nameMenuItems.get(0).getText();
        } else if (slotState == CPlayerSlotState.PLAYING) {
            if (controller == CMapControl.USER) {
                nameMenuItems.add(new MenuItem(name, -2));
                this.nameMenu.setEnabled(false);
            } else if (controller == CMapControl.NONE) {
                name = nameMenuItems.get(1).getText();
            } else if (controller == CMapControl.COMPUTER) {
                if (aiDifficulty == null) {
                    aiDifficulty = AIDifficulty.NORMAL;
                }
                switch (aiDifficulty) {
                    case NEWBIE:
                        name = nameMenuItems.get(2).getText();
                        break;
                    case INSANE:
                        name = nameMenuItems.get(4).getText();
                        break;
                    case NORMAL:
                        name = nameMenuItems.get(3).getText();
                    default:
                        break;
                }
                if (fixedPlayerSettings) {
                    this.nameMenu.setEnabled(false);
                }
            }
        } else {
            name = rootFrame.getTemplates().getDecoratedString("UNKNOWN");
        }
        rootFrame.setText(((StringFrame) ((GlueTextButtonFrame) this.nameMenu.getPopupTitleFrame()).getButtonText()),
                name);
    }

    public void setTextFromRacePreference(final GameUI rootFrame, final CPlayerJass player) {
        final MenuFrame menuFrame = (MenuFrame) this.raceMenu.getPopupMenuFrame();
        if (player.isRacePrefSet(CRacePreference.RANDOM) && (menuFrame.getMenuItemCount() > 0)) {
            rootFrame.setText(
                    ((StringFrame) ((GlueTextButtonFrame) this.raceMenu.getPopupTitleFrame()).getButtonText()),
                    menuFrame.getMenuItem(0).getText());
        } else if (player.isRacePrefSet(CRacePreference.HUMAN) && (menuFrame.getMenuItemCount() > 1)) {
            rootFrame.setText(
                    ((StringFrame) ((GlueTextButtonFrame) this.raceMenu.getPopupTitleFrame()).getButtonText()),
                    menuFrame.getMenuItem(1).getText());
        } else if (player.isRacePrefSet(CRacePreference.ORC) && (menuFrame.getMenuItemCount() > 2)) {
            rootFrame.setText(
                    ((StringFrame) ((GlueTextButtonFrame) this.raceMenu.getPopupTitleFrame()).getButtonText()),
                    menuFrame.getMenuItem(2).getText());
        } else if (player.isRacePrefSet(CRacePreference.UNDEAD) && (menuFrame.getMenuItemCount() > 3)) {
            rootFrame.setText(
                    ((StringFrame) ((GlueTextButtonFrame) this.raceMenu.getPopupTitleFrame()).getButtonText()),
                    menuFrame.getMenuItem(3).getText());
        } else if (player.isRacePrefSet(CRacePreference.NIGHTELF) && (menuFrame.getMenuItemCount() > 4)) {
            rootFrame.setText(
                    ((StringFrame) ((GlueTextButtonFrame) this.raceMenu.getPopupTitleFrame()).getButtonText()),
                    menuFrame.getMenuItem(4).getText());
        }
    }

    public UIFrame getPlayerSlotFrame() {
        return this.playerSlotFrame;
    }
}
