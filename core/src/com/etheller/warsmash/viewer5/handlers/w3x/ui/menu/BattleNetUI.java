package com.etheller.warsmash.viewer5.handlers.w3x.ui.menu;

import com.badlogic.gdx.utils.IntIntMap;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.etheller.warsmash.datasources.DataSource;
import com.etheller.warsmash.parsers.fdf.GameUI;
import com.etheller.warsmash.parsers.fdf.frames.*;
import com.etheller.warsmash.parsers.fdf.frames.ListBoxFrame.ListBoxSelelectionListener;
import com.etheller.warsmash.parsers.jass.Jass2;
import com.etheller.warsmash.parsers.w3x.War3Map;
import com.etheller.warsmash.parsers.w3x.objectdata.Warcraft3MapObjectData;
import com.etheller.warsmash.parsers.w3x.w3i.War3MapW3i;
import com.etheller.warsmash.parsers.w3x.w3i.War3MapW3iFlags;
import com.etheller.warsmash.units.custom.WTS;
import com.etheller.warsmash.util.WarsmashConstants;
import com.etheller.warsmash.viewer5.Scene;
import com.etheller.warsmash.viewer5.handlers.w3x.War3MapViewer;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.ai.AIDifficulty;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.config.CBasePlayer;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.config.War3MapConfig;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.players.CMapControl;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.players.CRacePreference;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.trigger.enumtypes.CPlayerSlotState;
import com.etheller.warsmash.viewer5.handlers.w3x.ui.mapsetup.MapInfoPane;
import com.etheller.warsmash.viewer5.handlers.w3x.ui.mapsetup.MapListContainer;
import com.etheller.warsmash.viewer5.handlers.w3x.ui.mapsetup.PlayerSlotPaneListener;
import com.etheller.warsmash.viewer5.handlers.w3x.ui.mapsetup.TeamSetupPane;
import net.warsmash.uberserver.ChannelServerMessageType;
import net.warsmash.uberserver.HostedGameVisibility;
import net.warsmash.uberserver.LobbyGameSpeed;
import net.warsmash.uberserver.LobbyPlayerType;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.CRC32;

public class BattleNetUI {
    private final GameUI rootFrame;
    private final Viewport uiViewport;
    private final Scene uiScene;
    private final DataSource dataSource;

    private final UIFrame battleNetMainFrame;
    private final UIFrame battleNetTOSPanel;
    private final UIFrame battleNetNewAccountPanel;
    private final UIFrame battleNetCancelBackdrop;
    private final GlueButtonFrame cancelButton;
    private final UIFrame battleNetOKBackdrop;
    private final UIFrame battleNetLoginPanel;
    private final SpriteFrame battleNetDoors;

    private final EditBoxFrame accountNameEditBox;
    private final EditBoxFrame passwordEditBox;

    private final BattleNetUIActionListener actionListener;
    private final StringFrame selectedRealmValue;
    private final Runnable exitLoginRunnable;
    private final UIFrame battleNetChatPanel;
    private final UIFrame chatPanel;
    private final UIFrame channelPanel;
    private final GlueButtonFrame quitBattleNetButton;
    private final List<GlueButtonFrame> battleNetChatTopButtons = new ArrayList<>();
    // welcome panel:
    private final UIFrame welcomePanel;
    private final StringFrame welcomeMOTDText;
    private final SimpleFrame welcomeQuitBattleNetButtonContainer;
    private final SimpleFrame chatQuitBattleNetButtonContainer;
    private final EditBoxFrame naAccountName;
    private final EditBoxFrame naPassword;
    private final EditBoxFrame naRepeatPassword;
    private final GlueButtonFrame okButton;
    private final StringFrame chatChannelNameLabel;
    private final TextAreaFrame chatTextArea;
    private final EditBoxFrame channelNameField;
    private final UIFrame battleNetCustomJoinPanel;
    private final EditBoxFrame joinGameEditBox;
    private final UIFrame battleNetCustomCreatePanel;
    private final ScrollBarFrame createGameSpeedSlider;
    private final StringFrame createGameSpeedValue;
    private final CheckBoxFrame publicGameRadio;
    private final CheckBoxFrame privateGameRadio;
    private final MapInfoPane customCreateMapInfoPane;
    private final MapInfoPane gameChatroomMapInfoPane;
    private final UIFrame battleNetGameChatroom;
    private final StringFrame gameChatroomGameNameValue;
    private final GlueButtonFrame gameChatroomStartGameButton;
    private final TextAreaFrame gameChatroomChatTextArea;
    private final EditBoxFrame gameChatroomChatEditBox;
    private final ListBoxFrame joinGameListBox;
    private final TeamSetupPane gameChatroomTeamSetupPane;
    private long gamingNetworkSessionToken;
    private String currentChannel;
    private String customCreatePanelCurrentSelectedMapPath;
    private War3MapConfig customCreateCurrentMapConfig;
    private War3MapW3i customCreateCurrentMapInfo;
    private War3Map customCreateCurrentMap;
    private IntIntMap gameChatroomServerSlotToMapSlot;
    private IntIntMap gameChatroomMapSlotToServerSlot;
    private War3MapConfig gameChatroomMapConfig;
    private War3MapW3i gameChatroomMapInfo;

    public BattleNetUI(final GameUI rootFrame, final Viewport uiViewport, Scene uiScene, final DataSource dataSource,
                       final BattleNetUIActionListener actionListener) {
        this.rootFrame = rootFrame;
        this.uiViewport = uiViewport;
        this.uiScene = uiScene;
        this.dataSource = dataSource;
        this.actionListener = actionListener;
        // Create BattleNet frames
        this.battleNetMainFrame = rootFrame.createFrame("BattleNetMainFrame", rootFrame);
        this.battleNetMainFrame.setVisible(false);
        this.battleNetDoors = (SpriteFrame) rootFrame.getFrameByName("BattleNetMainBackground");
        this.battleNetDoors.setVisible(false);
        UIFrame battleNetChangePasswordPanel = rootFrame.getFrameByName("ChangePasswordPanel");
        battleNetChangePasswordPanel.setVisible(false);
        UIFrame battleNetChangeEmailPanel = rootFrame.getFrameByName("ChangeEmailPanel");
        battleNetChangeEmailPanel.setVisible(false);
        UIFrame battleNetPasswordRecoveryPanel = rootFrame.getFrameByName("PasswordRecoveryPanel");
        battleNetPasswordRecoveryPanel.setVisible(false);
        UIFrame battleNetEmailBindPanel = rootFrame.getFrameByName("EmailBindPanel");
        battleNetEmailBindPanel.setVisible(false);

        // *******************************************
        // *
        // * Terms Of Service Panel
        // *
        // ******

        this.battleNetTOSPanel = rootFrame.getFrameByName("TOSPanel");
        this.battleNetTOSPanel.setVisible(false);

        this.battleNetNewAccountPanel = rootFrame.getFrameByName("NewAccountPanel");
        this.battleNetNewAccountPanel.setVisible(false);

        this.naAccountName = (EditBoxFrame) rootFrame.getFrameByName("NAAccountName");
        this.naPassword = (EditBoxFrame) rootFrame.getFrameByName("NAPassword");
        this.naRepeatPassword = (EditBoxFrame) rootFrame.getFrameByName("NARepeatPassword");

        this.battleNetCancelBackdrop = rootFrame.getFrameByName("CancelBackdrop");
        this.battleNetCancelBackdrop.setVisible(false);
        this.cancelButton = (GlueButtonFrame) rootFrame.getFrameByName("CancelButton");
        this.battleNetOKBackdrop = rootFrame.getFrameByName("OKBackdrop");
        this.battleNetOKBackdrop.setVisible(false);
        this.okButton = (GlueButtonFrame) rootFrame.getFrameByName("OKButton");

        // *******************************************
        // *
        // * Main Login Panel
        // *
        // ******

        this.battleNetLoginPanel = rootFrame.getFrameByName("LoginPanel");
        this.battleNetLoginPanel.setVisible(false);

        this.accountNameEditBox = (EditBoxFrame) rootFrame.getFrameByName("AccountName");
        final Runnable logonRunnable = new Runnable() {
            @Override
            public void run() {
                actionListener.logon(BattleNetUI.this.accountNameEditBox.getText(),
                        BattleNetUI.this.passwordEditBox.getText());
            }
        };
        this.accountNameEditBox.setOnEnter(logonRunnable);
        this.passwordEditBox = (EditBoxFrame) rootFrame.getFrameByName("Password");
        this.passwordEditBox.setOnEnter(logonRunnable);
        GlueButtonFrame passwordRecoveryButton = (GlueButtonFrame) rootFrame.getFrameByName("PasswordRecoveryButton");
        passwordRecoveryButton.setOnClick(() -> actionListener.recoverPassword(BattleNetUI.this.accountNameEditBox.getText()));
        this.selectedRealmValue = (StringFrame) rootFrame.getFrameByName("SelectedRealmValue");
        GlueButtonFrame changeEmailButton = (GlueButtonFrame) rootFrame.getFrameByName("ChangeEmailButton");
        changeEmailButton.setEnabled(false);
        GlueButtonFrame newAccountButton = (GlueButtonFrame) rootFrame.getFrameByName("NewAccountButton");
        newAccountButton.setOnClick(() -> {
            BattleNetUI.this.battleNetLoginPanel.setVisible(false);
            BattleNetUI.this.battleNetNewAccountPanel.setVisible(true);
            BattleNetUI.this.battleNetOKBackdrop.setVisible(true);
            BattleNetUI.this.okButton.setOnClick(() -> actionListener.createAccount(BattleNetUI.this.naAccountName.getText(),
                    BattleNetUI.this.naPassword.getText(), BattleNetUI.this.naRepeatPassword.getText()));
            BattleNetUI.this.cancelButton.setOnClick(this::leaveNewAccountPanel);
        });
        GlueButtonFrame tosButton = (GlueButtonFrame) rootFrame.getFrameByName("TOSButton");
        tosButton.setOnClick(() -> {
            boolean success = false;
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                try {
                    Desktop.getDesktop().browse(new URI("https://www.youtube.com/watch?v=dQw4w9WgXcQ"));
                    success = true;
                } catch (final Exception e) {
                    e.printStackTrace();
                }
            }
            if (!success) {
                BattleNetUI.this.battleNetLoginPanel.setVisible(false);
                BattleNetUI.this.battleNetCancelBackdrop.setVisible(false);
                BattleNetUI.this.battleNetTOSPanel.setVisible(true);
            }
        });

        this.exitLoginRunnable = BattleNetUI.this.actionListener::cancelLoginPrompt;

        GlueButtonFrame logonButton = (GlueButtonFrame) rootFrame.getFrameByName("LogonButton");
        logonButton.setOnClick(logonRunnable);

        this.battleNetChatPanel = rootFrame.createFrame("BattleNetChatPanel", rootFrame);
        this.battleNetChatPanel.setVisible(false);

        // ********************************
        // * The chat panel
        // ********************************
        BackdropFrame adFrame = (BackdropFrame) rootFrame.getFrameByName("AdFrame");
        adFrame.setVisible(false);
        BackdropFrame logoFrame = (BackdropFrame) rootFrame.getFrameByName("LogoFrame");
        logoFrame.setVisible(false);
        GlueButtonFrame standardGameButton = (GlueButtonFrame) rootFrame.getFrameByName("StandardGameButton");
        standardGameButton.setEnabled(false);
        this.battleNetChatTopButtons.add(standardGameButton);
        GlueButtonFrame quickStandardGameButton = (GlueButtonFrame) rootFrame.getFrameByName("QuickStandardGameButton");
        quickStandardGameButton.setEnabled(false);
        this.battleNetChatTopButtons.add(quickStandardGameButton);
        GlueButtonFrame standardTeamGameButton = (GlueButtonFrame) rootFrame.getFrameByName("StandardTeamGameButton");
        standardTeamGameButton.setEnabled(false);
        this.battleNetChatTopButtons.add(standardTeamGameButton);
        GlueButtonFrame customGameButton = (GlueButtonFrame) rootFrame.getFrameByName("CustomGameButton");
        customGameButton.setOnClick(actionListener::openCustomGameMenu);
        this.battleNetChatTopButtons.add(customGameButton);
        GlueButtonFrame tournamentButton = (GlueButtonFrame) rootFrame.getFrameByName("TournamentButton");
        tournamentButton.setEnabled(false);
        this.battleNetChatTopButtons.add(tournamentButton);
        GlueButtonFrame ladderButton = (GlueButtonFrame) rootFrame.getFrameByName("LadderButton");
        ladderButton.setEnabled(false);
        this.battleNetChatTopButtons.add(ladderButton);
        GlueButtonFrame profileButton = (GlueButtonFrame) rootFrame.getFrameByName("ProfileButton");
        profileButton.setEnabled(false);
        this.battleNetChatTopButtons.add(profileButton);
        this.chatPanel = rootFrame.getFrameByName("ChatPanel");
        this.chatPanel.setVisible(false);
        this.chatChannelNameLabel = (StringFrame) rootFrame.getFrameByName("ChatChannelNameLabel");
        GlueTextButtonFrame chatChannelButton = (GlueTextButtonFrame) rootFrame.getFrameByName("ChatChannelButton");
        chatChannelButton.setOnClick(actionListener::showChannelChooserPanel);
        this.chatTextArea = (TextAreaFrame) rootFrame.getFrameByName("ChatTextArea");
        final EditBoxFrame chatEditBox = (EditBoxFrame) rootFrame.getFrameByName("BattleNetChatEditBox");
        chatEditBox.setFilterAllowAny();
        chatEditBox.setOnEnter(() -> {
            actionListener.submitChatText(chatEditBox.getText());
            chatEditBox.setText("", rootFrame, uiViewport);
        });
        this.chatQuitBattleNetButtonContainer = (SimpleFrame) rootFrame
                .getFrameByName("ChatQuitBattleNetButtonContainer");

        // ********************************
        // * The channel panel
        // ********************************
        this.channelPanel = rootFrame.getFrameByName("ChannelPanel");
        this.channelPanel.setVisible(false);

        GlueButtonFrame channelPanelBackButton = (GlueButtonFrame) rootFrame.getFrameByName("BackButton");
        channelPanelBackButton.setOnClick(actionListener::returnToChat);
        this.channelNameField = (EditBoxFrame) rootFrame.getFrameByName("ChannelNameField");
        GlueButtonFrame channelPanelJoinChannelButton = (GlueButtonFrame) rootFrame.getFrameByName("JoinChannelButton");
        final Runnable onJoinChannelClick = () -> actionListener.requestJoinChannel(BattleNetUI.this.channelNameField.getText());
        this.channelNameField.setOnEnter(onJoinChannelClick);
        channelPanelJoinChannelButton.setOnClick(onJoinChannelClick);

        // ********************************
        // * The welcome panel
        // ********************************
        this.welcomePanel = rootFrame.getFrameByName("WelcomePanel");
        this.welcomePanel.setVisible(false);
        StringFrame welcomeNewItemCount = (StringFrame) rootFrame.getFrameByName("WelcomeNewItemCount");
        rootFrame.setText(welcomeNewItemCount, "(0)");
        this.welcomeMOTDText = (StringFrame) rootFrame.getFrameByName("WelcomeMOTDText");
        UIFrame welcomeUpcomingTournamentPanel = rootFrame.getFrameByName("UpcomingTournamentPanel");
        welcomeUpcomingTournamentPanel.setVisible(false);
        GlueButtonFrame welcomeEnterChatButton = (GlueButtonFrame) rootFrame.getFrameByName("EnterChatButton");
        welcomeEnterChatButton.setOnClick(actionListener::enterDefaultChat);
        this.welcomeQuitBattleNetButtonContainer = (SimpleFrame) rootFrame
                .getFrameByName("WelcomeQuitBattleNetButtonContainer");

        // ********************************
        // * The quit button
        // ********************************
        this.quitBattleNetButton = (GlueButtonFrame) rootFrame.getFrameByName("QuitBattleNetButton");
        this.quitBattleNetButton.setOnClick(actionListener::quitBattleNet);

        // *******************************************
        // *
        // * New Account Panel NCS (Patch 1.31ish)
        // *
        // ******
        final UIFrame newAccountPanelNCS = rootFrame.getFrameByName("NewAccountPanelNCS");
        if (newAccountPanelNCS != null) {
            newAccountPanelNCS.setVisible(false);
        }

        // *******************************************
        // *
        // * Battle Net Custom Join Panel
        // *
        // ******

        this.battleNetCustomJoinPanel = rootFrame.createFrame("BattleNetCustomJoinPanel", rootFrame);
        this.battleNetCustomJoinPanel.setVisible(false);

        GlueButtonFrame customJoinPanelBackButton = (GlueButtonFrame) rootFrame.getFrameByName("CancelButton");
        customJoinPanelBackButton.setOnClick(actionListener::returnToChat);

        GlueButtonFrame customJoinPanelCreateGameButton = (GlueButtonFrame) rootFrame.getFrameByName("CreateGameButton");
        customJoinPanelCreateGameButton.setOnClick(() -> {

            // clear these out when leaving a screen
            BattleNetUI.this.customCreateCurrentMapConfig = null;
            BattleNetUI.this.customCreateCurrentMapInfo = null;
            BattleNetUI.this.customCreateCurrentMap = null;
            BattleNetUI.this.customCreatePanelCurrentSelectedMapPath = null;
            actionListener.showCreateGameMenu();
        });

        GlueButtonFrame customJoinPanelLoadGameButton = (GlueButtonFrame) rootFrame.getFrameByName("LoadGameButton");
        customJoinPanelLoadGameButton.setEnabled(false);

        final SimpleFrame joinGameListContainer = (SimpleFrame) this.rootFrame.getFrameByName("JoinGameListContainer"
        );
        this.joinGameListBox = (ListBoxFrame) this.rootFrame.createFrameByType("LISTBOX", "MapListBox",
                joinGameListContainer, "WITHCHILDREN");
        this.joinGameListBox.setSetAllPoints(true);
        final StringFrame joinGameListLabel = (StringFrame) this.rootFrame.getFrameByName("JoinGameListLabel");
        this.joinGameListBox.setFrameFont(joinGameListLabel.getFrameFont());

        this.joinGameEditBox = (EditBoxFrame) this.rootFrame.getFrameByName("JoinGameNameEditBox");

        this.joinGameListBox.setSelectionListener((newSelectedIndex, newSelectedItem) -> {
            if (newSelectedItem != null) {
                BattleNetUI.this.joinGameEditBox.setText(newSelectedItem, rootFrame, uiViewport);
            }
        });
        joinGameListContainer.add(this.joinGameListBox);
        GlueButtonFrame joinGameButton = (GlueButtonFrame) this.rootFrame.getFrameByName("JoinGameButton");
        joinGameButton.setOnClick(() -> {
            final String text = BattleNetUI.this.joinGameEditBox.getText();
            if (text.isEmpty()) {
                actionListener.showError("NETERROR_NOGAMESPECIFIED");
            } else {
                actionListener.requestJoinGame(text);
            }

        });

        // *******************************************
        // *
        // * Battle Net Custom Create Panel
        // *
        // ******
        this.battleNetCustomCreatePanel = rootFrame.createFrame("BattleNetCustomCreatePanel", rootFrame);
        this.battleNetCustomCreatePanel.setVisible(false);

        final EditBoxFrame customCreatePanelNameEditBox = (EditBoxFrame) rootFrame
                .getFrameByName("CreateGameNameEditBox");

        GlueButtonFrame customCreatePanelBackButton = (GlueButtonFrame) rootFrame.getFrameByName("CancelButton");
        customCreatePanelBackButton.setOnClick(actionListener::openCustomGameMenu);

        GlueButtonFrame customCreatePanelCreateButton = (GlueButtonFrame) rootFrame.getFrameByName("CreateGameButton");

        final StringFrame mapListLabel = (StringFrame) this.rootFrame.getFrameByName("MapListLabel");
        final MapListContainer mapListContainer = new MapListContainer(this.rootFrame, this.uiViewport,
                "MapListContainer", dataSource, mapListLabel.getFrameFont());
        mapListContainer.addSelectionListener(new ListBoxSelelectionListener() {

            @Override
            public void onSelectionChanged(final int newSelectedIndex, final String newSelectedItem) {
                BattleNetUI.this.customCreateCurrentMapConfig = null;
                BattleNetUI.this.customCreateCurrentMapInfo = null;
                if (newSelectedItem != null) {
                    BattleNetUI.this.customCreatePanelCurrentSelectedMapPath = newSelectedItem;

                    try {
                        final War3Map map = War3MapViewer.beginLoadingMap(dataSource, newSelectedItem);

                        final War3MapW3i mapInfo = map.readMapInformation();
                        final WTS wtsFile = Warcraft3MapObjectData.loadWTS(map);
                        rootFrame.setMapStrings(wtsFile);
                        final War3MapConfig war3MapConfig = new War3MapConfig(WarsmashConstants.MAX_PLAYERS);
                        for (int i = 0; (i < WarsmashConstants.MAX_PLAYERS) && (i < mapInfo.getPlayers().size()); i++) {
                            final CBasePlayer player = war3MapConfig.getPlayer(i);
                            player.setName(rootFrame.getTrigStr(mapInfo.getPlayers().get(i).getName()));
                        }
                        war3MapConfig.setMapName(rootFrame.getTrigStr(mapInfo.getName()));
                        war3MapConfig.setMapDescription(rootFrame.getTrigStr(mapInfo.getDescription()));
                        BattleNetUI.this.customCreateMapInfoPane.setMap(rootFrame, uiViewport, map, mapInfo,
                                war3MapConfig);
                        BattleNetUI.this.customCreateCurrentMapConfig = war3MapConfig;
                        BattleNetUI.this.customCreateCurrentMapInfo = mapInfo;
                        BattleNetUI.this.customCreateCurrentMap = map;
                    } catch (final Exception exc) {
                        exc.printStackTrace();
                        actionListener.showError("NETERROR_MAPLOADERROR");
                    }
                } else {
                    BattleNetUI.this.customCreatePanelCurrentSelectedMapPath = null;
                }
            }
        });

        this.createGameSpeedValue = (StringFrame) this.rootFrame.getFrameByName("CreateGameSpeedValue");

        this.createGameSpeedSlider = (ScrollBarFrame) this.rootFrame.getFrameByName("CreateGameSpeedSlider");
        this.createGameSpeedSlider.setChangeListener((gameUI, uiViewport1, newValue) -> {
            if ((newValue >= 0) && (newValue < LobbyGameSpeed.VALUES.length)) {
                gameUI.setDecoratedText(BattleNetUI.this.createGameSpeedValue,
                        LobbyGameSpeed.VALUES[newValue].name());
            }
        });
        this.createGameSpeedSlider.setValue(rootFrame, uiViewport, LobbyGameSpeed.NORMAL.ordinal());

        this.publicGameRadio = (CheckBoxFrame) this.rootFrame.getFrameByName("PublicGameRadio");
        this.privateGameRadio = (CheckBoxFrame) this.rootFrame.getFrameByName("PrivateGameRadio");

        this.publicGameRadio.setOnClick(() -> {
            if (BattleNetUI.this.privateGameRadio.isEnabled()) {
                BattleNetUI.this.privateGameRadio.setChecked(!BattleNetUI.this.publicGameRadio.isChecked());
            } else {
                BattleNetUI.this.publicGameRadio.setChecked(true);
            }
        });
        this.privateGameRadio.setOnClick(() -> BattleNetUI.this.publicGameRadio.setChecked(!BattleNetUI.this.privateGameRadio.isChecked()));
        this.publicGameRadio.setChecked(true);
//		this.privateGameRadio.setEnabled(false);

        {
            final SimpleFrame mapInfoPaneContainer = (SimpleFrame) this.rootFrame.getFrameByName("MapInfoPaneContainer"
            );
            this.customCreateMapInfoPane = new MapInfoPane(this.rootFrame, this.uiViewport, mapInfoPaneContainer);
        }

        customCreatePanelCreateButton.setOnClick(new Runnable() {
            private final CRC32 mapChecksumCalculator = new CRC32();

            @Override
            public void run() {
                if (BattleNetUI.this.customCreateCurrentMapInfo == null) {
                    actionListener.showError("NETERROR_NOMAPSELECTED");
                    return;
                }
                final String gameName = customCreatePanelNameEditBox.getText();
                if ((gameName == null) || gameName.isEmpty()) {
                    actionListener.showError("NETERROR_NOGAMENAMESPECIFIED");
                    return;
                }
                boolean nonSpace = false;
                for (int i = 0; i < gameName.length(); i++) {
                    if (gameName.charAt(i) != ' ') {
                        nonSpace = true;
                        break;
                    }
                }
                if (!nonSpace) {
                    actionListener.showError("NETERROR_EMPTYGAMENAMESPECIFIED");
                    return;
                }
                final int speedSliderValue = BattleNetUI.this.createGameSpeedSlider.getValue();

                LobbyGameSpeed gameSpeed;
                if ((speedSliderValue >= 0) && (speedSliderValue < LobbyGameSpeed.VALUES.length)) {
                    gameSpeed = LobbyGameSpeed.VALUES[speedSliderValue];
                } else {
                    actionListener.showError("NETERROR_DEFAULTERROR");
                    return;
                }
                final HostedGameVisibility hostedGameVisibility = BattleNetUI.this.publicGameRadio.isChecked()
                        ? HostedGameVisibility.PUBLIC
                        : HostedGameVisibility.PRIVATE;
                Jass2.loadConfig(BattleNetUI.this.customCreateCurrentMap, uiViewport, uiScene, rootFrame,
                        BattleNetUI.this.customCreateCurrentMapConfig, "Scripts\\common.j", "Scripts\\Blizzard.j",
                        "Scripts\\war3map.j").config();
                int mapPlayerSlots = 0;
                for (int i = 0; (i < (WarsmashConstants.MAX_PLAYERS - 4))
                        && (i < BattleNetUI.this.customCreateCurrentMapConfig.getPlayerCount()); i++) {
                    if (BattleNetUI.this.customCreateCurrentMapConfig.getPlayer(i)
                            .getController() == CMapControl.USER) {
                        mapPlayerSlots++;
                    }
                }

                final long mapChecksum = BattleNetUI.this.customCreateCurrentMap
                        .computeChecksum(this.mapChecksumCalculator);

                String mapName = BattleNetUI.this.customCreatePanelCurrentSelectedMapPath;
                mapName = mapName.substring(Math.max(mapName.lastIndexOf('/'), mapName.lastIndexOf('\\')) + 1);

                actionListener.createGame(gameName, mapName, mapPlayerSlots, gameSpeed, hostedGameVisibility,
                        mapChecksum, BattleNetUI.this.customCreateCurrentMap);
            }
        });

        // *******************************************
        // *
        // * Battle Net Custom Create Panel
        // *
        // ******
        this.battleNetGameChatroom = rootFrame.createFrame("GameChatroom", rootFrame);
        this.battleNetGameChatroom.setVisible(false);

        {
            final SimpleFrame teamSetupContainer = (SimpleFrame) this.rootFrame.getFrameByName("TeamSetupContainer");
            this.gameChatroomTeamSetupPane = new TeamSetupPane(this.rootFrame, this.uiViewport, teamSetupContainer);
        }

        {
            final SimpleFrame mapInfoPaneContainer = (SimpleFrame) this.rootFrame.getFrameByName("MapInfoPaneContainer"
            );
            this.gameChatroomMapInfoPane = new MapInfoPane(this.rootFrame, this.uiViewport, mapInfoPaneContainer);
        }

        this.gameChatroomGameNameValue = (StringFrame) this.rootFrame.getFrameByName("GameNameValue");

        this.gameChatroomStartGameButton = (GlueButtonFrame) rootFrame.getFrameByName("StartGameButton");

        this.gameChatroomStartGameButton.setOnClick(actionListener::startGame);

        GlueButtonFrame gameChatroomCancelButton = (GlueButtonFrame) rootFrame.getFrameByName("CancelButton");
        gameChatroomCancelButton.setOnClick(actionListener::leaveCustomGame);

        this.gameChatroomChatTextArea = (TextAreaFrame) rootFrame.getFrameByName("ChatTextArea");
        this.gameChatroomChatEditBox = (EditBoxFrame) rootFrame.getFrameByName("ChatEditBox");
        this.gameChatroomChatEditBox.setFilterAllowAny();
        this.gameChatroomChatEditBox.setOnEnter(() -> {
            actionListener.submitChatText(BattleNetUI.this.gameChatroomChatEditBox.getText());
            BattleNetUI.this.gameChatroomChatEditBox.setText("", rootFrame, uiViewport);
        });
    }

    public void setTopButtonsVisible(final boolean flag) {
        for (final GlueButtonFrame frame : this.battleNetChatTopButtons) {
            frame.setVisible(flag);
        }
    }

    public SpriteFrame getDoors() {
        return this.battleNetDoors;
    }

    public void setVisible(final boolean b) {
        this.battleNetMainFrame.setVisible(b);
    }

    public void showLoginPrompt(final String selectedRealm) {
        this.rootFrame.setText(this.selectedRealmValue, selectedRealm);
        this.battleNetLoginPanel.setVisible(true);
        this.battleNetCancelBackdrop.setVisible(true);
        this.cancelButton.setOnClick(this.exitLoginRunnable);
    }

    public void hide() {
        this.battleNetLoginPanel.setVisible(false);
        this.battleNetCancelBackdrop.setVisible(false);
        this.battleNetChatPanel.setVisible(false);
        hideCurrentScreen();
    }

    public void loginAccepted(final long sessionToken, final String welcomeMessage) {
        this.battleNetLoginPanel.setVisible(false);
        this.battleNetCancelBackdrop.setVisible(false);
        this.gamingNetworkSessionToken = sessionToken;
        this.rootFrame.setText(this.welcomeMOTDText, welcomeMessage);
    }

    public void showWelcomeScreen() {
        setTopButtonsVisible(true);
        this.battleNetChatPanel.setVisible(true);
        this.welcomePanel.setVisible(true);
        this.quitBattleNetButton.setVisible(true);
        this.quitBattleNetButton.setParent(this.welcomeQuitBattleNetButtonContainer);
        this.quitBattleNetButton.setWidth(0); // TODO set width/height 0 probably shouldnt be necessary
        this.quitBattleNetButton.setHeight(0);
        this.quitBattleNetButton.clearFramePointAssignments();
        this.quitBattleNetButton.setSetAllPoints(true);
        this.quitBattleNetButton.positionBounds(this.rootFrame, this.uiViewport);
    }

    public void showCustomGameMenu() {
        this.battleNetCustomJoinPanel.setVisible(true);
    }

    public void showCustomGameCreateMenu() {
        this.battleNetCustomCreatePanel.setVisible(true);
    }

    public void hideCurrentScreen() {
        this.welcomePanel.setVisible(false);
        this.chatPanel.setVisible(false);
        this.channelPanel.setVisible(false);
        this.welcomePanel.setVisible(false);
        this.quitBattleNetButton.setVisible(false);
        this.battleNetCustomJoinPanel.setVisible(false);
        this.battleNetCustomCreatePanel.setVisible(false);
        this.battleNetGameChatroom.setVisible(false);
        setTopButtonsVisible(false);
    }

    public void showChatChannel() {
        setTopButtonsVisible(true);
        this.chatPanel.setVisible(true);
        this.quitBattleNetButton.setVisible(true);
        this.quitBattleNetButton.setParent(this.chatQuitBattleNetButtonContainer);
        this.quitBattleNetButton.setWidth(0); // TODO set width/height 0 probably shouldnt be necessary
        this.quitBattleNetButton.setHeight(0);
        this.quitBattleNetButton.clearFramePointAssignments();
        this.quitBattleNetButton.setSetAllPoints(true);
        this.quitBattleNetButton.positionBounds(this.rootFrame, this.uiViewport);
    }

    public void accountCreatedOk() {
        leaveNewAccountPanel();
    }

    private void leaveNewAccountPanel() {
        BattleNetUI.this.battleNetLoginPanel.setVisible(true);
        BattleNetUI.this.battleNetNewAccountPanel.setVisible(false);
        BattleNetUI.this.battleNetOKBackdrop.setVisible(false);
        BattleNetUI.this.cancelButton.setOnClick(BattleNetUI.this.exitLoginRunnable);
    }

    public long getGamingNetworkSessionToken() {
        return this.gamingNetworkSessionToken;
    }

    public void joinedChannel(final String channelName) {
        this.currentChannel = channelName;
        this.rootFrame.setText(this.chatChannelNameLabel, channelName);
        final String messageText = String.format(this.rootFrame.getTemplates().getDecoratedString("BNET_JOIN_CHANNEL"),
                channelName);
        this.chatTextArea.addItem(messageText, this.rootFrame, this.uiViewport);
    }

    public void joinedGame(String gameName, boolean host) {
        this.currentChannel = null;
        this.gameChatroomChatTextArea.removeAllItems();
        this.rootFrame.setText(this.gameChatroomGameNameValue, gameName);
        this.gameChatroomStartGameButton.setEnabled(host);
    }

    public void channelMessage(final String userName, final String message) {
        final String messageText = String.format(this.rootFrame.getTemplates().getDecoratedString("CHATEVENT_ID_TALK"),
                this.rootFrame.getTemplates().getDecoratedString("CHATCOLOR_TALK_USER"), userName,
                this.rootFrame.getTemplates().getDecoratedString("CHATCOLOR_TALK_MESSAGE"), message);
        if (this.currentChannel == null) {
            this.gameChatroomChatTextArea.addItem(messageText, this.rootFrame, this.uiViewport);
        } else {
            this.chatTextArea.addItem(messageText, this.rootFrame, this.uiViewport);
        }
    }

    public void channelEmote(final String userName, final String message) {
        final String messageText = String.format(this.rootFrame.getTemplates().getDecoratedString("CHATEVENT_ID_EMOTE"),
                this.rootFrame.getTemplates().getDecoratedString("CHATCOLOR_EMOTE_MESSAGE"), userName, message);
        if (this.currentChannel == null) {
            this.gameChatroomChatTextArea.addItem(messageText, this.rootFrame, this.uiViewport);
        } else {
            this.chatTextArea.addItem(messageText, this.rootFrame, this.uiViewport);
        }
    }

    public void channelServerMessage(String userName, ChannelServerMessageType messageType) {
        String msgKey;
        switch (messageType) {
            case JOIN_GAME: {
                msgKey = "NETMESSAGE_PLAYERJOINED";
                break;
            }
            case LEAVE_GAME: {
                msgKey = "NETMESSAGE_PLAYERLEFT";
                break;
            }
            default: {
                msgKey = "NETERROR_DEFAULTERROR";
                break;
            }
        }

        final String messageText = String.format(this.rootFrame.getTemplates().getDecoratedString(msgKey), userName);
        if (this.currentChannel == null) {
            this.gameChatroomChatTextArea.addItem(messageText, this.rootFrame, this.uiViewport);
        } else {
            this.chatTextArea.addItem(messageText, this.rootFrame, this.uiViewport);
        }
    }

    public void showChannelMenu() {
        this.channelPanel.setVisible(true);
    }

    public String getCurrentChannel() {
        return this.currentChannel;
    }

    public void showCustomGameLobby() {
        this.battleNetGameChatroom.setVisible(true);
    }

    public void beginGamesList() {
        this.joinGameListBox.removeAllItems();
    }

    public void gamesListItem(String gameName) {
        this.joinGameListBox.addItem(gameName, this.rootFrame, this.uiViewport);
    }

    public void endGamesList() {

    }

    public void setJoinGamePreviewMap(File mapLookupFile) {
        War3Map map;
        try {
            map = War3MapViewer.beginLoadingMap(this.dataSource, mapLookupFile.getPath());

            final War3MapW3i mapInfo = map.readMapInformation();
            final WTS wtsFile = Warcraft3MapObjectData.loadWTS(map);
            this.rootFrame.setMapStrings(wtsFile);
            final War3MapConfig war3MapConfig = new War3MapConfig(WarsmashConstants.MAX_PLAYERS);
            setGameChatroomMap(map, mapInfo, war3MapConfig);
        } catch (final IOException e) {
            e.printStackTrace();
            this.actionListener.showError("NETERROR_MAPFILEREAD");
        }

    }

    private void setGameChatroomMap(War3Map map, final War3MapW3i mapInfo, final War3MapConfig war3MapConfig)
            throws IOException {
        this.gameChatroomMapInfo = mapInfo;
        this.gameChatroomMapConfig = war3MapConfig;
        for (int i = 0; (i < WarsmashConstants.MAX_PLAYERS) && (i < mapInfo.getPlayers().size()); i++) {
            final CBasePlayer player = war3MapConfig.getPlayer(i);
            player.setName(this.rootFrame.getTrigStr(mapInfo.getPlayers().get(i).getName()));
        }
        Jass2.loadConfig(map, this.uiViewport, this.uiScene, this.rootFrame, war3MapConfig, "Scripts\\common.j",
                "Scripts\\Blizzard.j", "Scripts\\war3map.j").config();
        final IntIntMap serverSlotToMapSlot = new IntIntMap(WarsmashConstants.MAX_PLAYERS);
        final IntIntMap mapSlotToServerSlot = new IntIntMap(WarsmashConstants.MAX_PLAYERS);
        int serverSlot = 0;
        for (int i = 0; i < WarsmashConstants.MAX_PLAYERS; i++) {
            final CBasePlayer player = war3MapConfig.getPlayer(i);
            if (player.getController() == CMapControl.COMPUTER) {
                if (mapInfo.hasFlag(War3MapW3iFlags.FIXED_PLAYER_SETTINGS_FOR_CUSTOM_FORCES)) {
                    player.setSlotState(CPlayerSlotState.PLAYING);
                }
            } else if (player.getController() == CMapControl.USER) {
                serverSlotToMapSlot.put(serverSlot, i);
                mapSlotToServerSlot.put(i, serverSlot);
                serverSlot++;
            }
        }
        this.gameChatroomServerSlotToMapSlot = serverSlotToMapSlot;
        this.gameChatroomMapSlotToServerSlot = mapSlotToServerSlot;
        this.gameChatroomMapInfoPane.setMap(this.rootFrame, this.uiViewport, map, mapInfo, war3MapConfig);
        this.gameChatroomTeamSetupPane.setMap(this.rootFrame, this.uiViewport, war3MapConfig,
                mapInfo.getPlayers().size(), mapInfo, new PlayerSlotPaneListener() {

                    @Override
                    public void setPlayerSlot(int index, LobbyPlayerType lobbyPlayerType) {
                        final int serverSlot = BattleNetUI.this.gameChatroomMapSlotToServerSlot.get(index, -1);
                        BattleNetUI.this.actionListener.gameLobbySetPlayerSlot(serverSlot, lobbyPlayerType);
                    }

                    @Override
                    public void setPlayerRace(int index, int raceItemIndex) {
                        final int serverSlot = BattleNetUI.this.gameChatroomMapSlotToServerSlot.get(index, -1);
                        BattleNetUI.this.actionListener.gameLobbySetPlayerRace(serverSlot, raceItemIndex);
                    }
                });

    }

    public void gameLobbySlotSetPlayerType(int slot, LobbyPlayerType playerType) {
        if (this.gameChatroomServerSlotToMapSlot != null) {
            final int mapSlot = this.gameChatroomServerSlotToMapSlot.get(slot, -1);
            if (mapSlot != -1) {
                final CBasePlayer player = this.gameChatroomMapConfig.getPlayer(mapSlot);
                switch (playerType) {
                    case OPEN:
                        player.setController(CMapControl.NONE);
                        player.setSlotState(CPlayerSlotState.EMPTY);
                        player.setAIDifficulty(null);
                        break;
                    case CLOSED:
                        player.setController(CMapControl.NONE);
                        player.setSlotState(CPlayerSlotState.PLAYING);
                        player.setAIDifficulty(null);
                        break;
                    case COMPUTER_NEWBIE:
                        player.setController(CMapControl.COMPUTER);
                        player.setSlotState(CPlayerSlotState.PLAYING);
                        player.setAIDifficulty(AIDifficulty.NEWBIE);
                        break;
                    case COMPUTER_NORMAL:
                        player.setController(CMapControl.COMPUTER);
                        player.setSlotState(CPlayerSlotState.PLAYING);
                        player.setAIDifficulty(AIDifficulty.NORMAL);
                        break;
                    case COMPUTER_INSANE:
                        player.setController(CMapControl.COMPUTER);
                        player.setSlotState(CPlayerSlotState.PLAYING);
                        player.setAIDifficulty(AIDifficulty.INSANE);
                        break;
                    case USER:
                        player.setController(CMapControl.USER);
                        player.setSlotState(CPlayerSlotState.PLAYING);
                        player.setAIDifficulty(null);
                        break;
                }
                this.gameChatroomTeamSetupPane.notifyPlayerDataUpdated(slot, this.rootFrame, this.uiViewport,
                        this.gameChatroomMapConfig, this.gameChatroomMapInfo);
            }
        }
    }

    public void gameLobbySlotSetPlayer(int slot, String userName) {
        if (this.gameChatroomServerSlotToMapSlot != null) {
            final int mapSlot = this.gameChatroomServerSlotToMapSlot.get(slot, -1);
            if (mapSlot != -1) {
                final CBasePlayer player = this.gameChatroomMapConfig.getPlayer(mapSlot);
                player.setName(userName);
                this.gameChatroomTeamSetupPane.notifyPlayerDataUpdated(slot, this.rootFrame, this.uiViewport,
                        this.gameChatroomMapConfig, this.gameChatroomMapInfo);
            }
        }
    }

    public void gameLobbySlotSetPlayerRace(int slot, int raceItemIndex) {
        if (this.gameChatroomServerSlotToMapSlot != null) {
            final int mapSlot = this.gameChatroomServerSlotToMapSlot.get(slot, -1);
            if (mapSlot != -1) {
                final CBasePlayer player = this.gameChatroomMapConfig.getPlayer(mapSlot);
                switch (raceItemIndex) {
                    case 0:
                        player.setRacePref(CRacePreference.RANDOM);
                        break;
                    case 1:
                        player.setRacePref(CRacePreference.HUMAN);
                        break;
                    case 2:
                        player.setRacePref(CRacePreference.ORC);
                        break;
                    case 3:
                        player.setRacePref(CRacePreference.UNDEAD);
                        break;
                    case 4:
                        player.setRacePref(CRacePreference.NIGHTELF);
                        break;

                    default:
                        break;
                }
                this.gameChatroomTeamSetupPane.notifyPlayerDataUpdated(slot, this.rootFrame, this.uiViewport,
                        this.gameChatroomMapConfig, this.gameChatroomMapInfo);
            }
        }
    }

    public void setJoinGamePreviewMapToHostedMap() {
        try {
            setGameChatroomMap(this.customCreateCurrentMap, this.customCreateCurrentMapInfo,
                    this.customCreateCurrentMapConfig);
        }
        catch (final IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public String getLastHostedGamePath() {
        return this.customCreatePanelCurrentSelectedMapPath;
    }

    public void clearJoinGamePreviewMap(String mapPreviewName) {
        this.gameChatroomMapInfoPane.clearMap(this.rootFrame, this.uiViewport, mapPreviewName);
        this.gameChatroomTeamSetupPane.clearMap(this.rootFrame, this.uiViewport);
        this.gameChatroomServerSlotToMapSlot = null;
        this.gameChatroomMapSlotToServerSlot = null;
    }

    public IntIntMap getGameChatroomMapSlotToServerSlot() {
        return this.gameChatroomMapSlotToServerSlot;
    }

    public IntIntMap getGameChatroomServerSlotToMapSlot() {
        return this.gameChatroomServerSlotToMapSlot;
    }

    public War3MapConfig getGameChatroomMapConfig() {
        return this.gameChatroomMapConfig;
    }

}
