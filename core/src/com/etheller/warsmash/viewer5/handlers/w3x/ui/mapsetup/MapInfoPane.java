package com.etheller.warsmash.viewer5.handlers.w3x.ui.mapsetup;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.etheller.warsmash.parsers.fdf.GameUI;
import com.etheller.warsmash.parsers.fdf.datamodel.FramePoint;
import com.etheller.warsmash.parsers.fdf.frames.*;
import com.etheller.warsmash.parsers.w3x.War3Map;
import com.etheller.warsmash.parsers.w3x.w3i.War3MapW3i;
import com.etheller.warsmash.parsers.w3x.w3i.War3MapW3iFlags;
import com.etheller.warsmash.units.DataTable;
import com.etheller.warsmash.units.Element;
import com.etheller.warsmash.units.StandardObjectData;
import com.etheller.warsmash.util.ImageUtils;
import com.etheller.warsmash.util.WarsmashConstants;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.config.CBasePlayer;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.config.War3MapConfig;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.players.CMapControl;

public class MapInfoPane {
    private final UIFrame mapInfoPaneFrame;
    private final StringFrame maxPlayersValue;
    private final StringFrame mapNameValue;
    private final TextureFrame minimapImageTextureFrame;
    private final StringFrame suggestedPlayersValue;
    private final StringFrame mapSizeValue;
    private final StringFrame mapDescValue;
    private final StringFrame mapTilesetValue;
    private final BackdropFrame maxPlayersIcon;

    public MapInfoPane(final GameUI rootFrame, final Viewport uiViewport, final SimpleFrame container) {
        this.mapInfoPaneFrame = rootFrame.createFrame("MapInfoPane", container);
        this.mapInfoPaneFrame.setSetAllPoints(true);
        container.add(this.mapInfoPaneFrame);

        this.maxPlayersIcon = (BackdropFrame) rootFrame.getFrameByName("MaxPlayersIcon");
        this.maxPlayersValue = (StringFrame) rootFrame.getFrameByName("MaxPlayersValue");
        this.suggestedPlayersValue = (StringFrame) rootFrame.getFrameByName("SuggestedPlayersValue");
        this.mapSizeValue = (StringFrame) rootFrame.getFrameByName("MapSizeValue");
        this.mapDescValue = (StringFrame) rootFrame.getFrameByName("MapDescValue");
        this.mapNameValue = (StringFrame) rootFrame.getFrameByName("MapNameValue");
        this.mapTilesetValue = (StringFrame) rootFrame.getFrameByName("MapTilesetValue");
        UIFrame authIconFrame = rootFrame.getFrameByName("AuthIcon");
        final UIFrame minimapImageFrame = rootFrame.getFrameByName("MinimapImage");
        this.minimapImageTextureFrame = rootFrame.createTextureFrame("MinimapImageTexture", minimapImageFrame, false,
                TextureFrame.DEFAULT_TEX_COORDS);
        rootFrame.remove(this.minimapImageTextureFrame);
        ((SimpleFrame) this.mapInfoPaneFrame).add(this.minimapImageTextureFrame);
        this.minimapImageTextureFrame.setSetAllPoints(true);

        final UIFrame suggestedPlayersLabel = rootFrame.getFrameByName("SuggestedPlayersLabel");
        final UIFrame mapSizeLabel = rootFrame.getFrameByName("MapSizeLabel");
        final UIFrame mapTilesetLabel = rootFrame.getFrameByName("MapTilesetLabel");
        final UIFrame mapDescLabel = rootFrame.getFrameByName("MapDescLabel");

        // TODO there might be some kind of layout manager system that is supposed to do
        // the below anchoring automatically but I don't have that atm
        this.maxPlayersIcon
                .addSetPoint(new SetPoint(FramePoint.TOPLEFT, this.mapInfoPaneFrame, FramePoint.TOPLEFT, 0, 0));

        minimapImageFrame.addSetPoint(new SetPoint(FramePoint.TOPLEFT, this.maxPlayersIcon, FramePoint.BOTTOMLEFT, 0,
                GameUI.convertY(uiViewport, -0.01f)));
        suggestedPlayersLabel.addSetPoint(new SetPoint(FramePoint.TOPLEFT, minimapImageFrame, FramePoint.BOTTOMLEFT, 0,
                GameUI.convertY(uiViewport, -0.01f)));
        this.suggestedPlayersValue
                .addSetPoint(new SetPoint(FramePoint.TOPLEFT, suggestedPlayersLabel, FramePoint.TOPRIGHT, 0, 0));
        mapSizeLabel.addSetPoint(new SetPoint(FramePoint.TOPLEFT, suggestedPlayersLabel, FramePoint.BOTTOMLEFT, 0, 0));
        this.mapSizeValue.addSetPoint(new SetPoint(FramePoint.TOPLEFT, mapSizeLabel, FramePoint.TOPRIGHT, 0, 0));
        mapTilesetLabel.addSetPoint(new SetPoint(FramePoint.TOPLEFT, mapSizeLabel, FramePoint.BOTTOMLEFT, 0, 0));
        this.mapTilesetValue.addSetPoint(new SetPoint(FramePoint.TOPLEFT, mapTilesetLabel, FramePoint.TOPRIGHT, 0, 0));
        mapDescLabel.addSetPoint(new SetPoint(FramePoint.TOPLEFT, mapTilesetLabel, FramePoint.BOTTOMLEFT, 0, 0));
        this.mapDescValue.clearFramePointAssignments();
        this.mapDescValue.addSetPoint(new SetPoint(FramePoint.TOPLEFT, mapDescLabel, FramePoint.BOTTOMLEFT, 0, 0));
        this.mapDescValue.setWidth(GameUI.convertX(uiViewport, 0.23f));

        authIconFrame.setVisible(false);
    }

    public void clearMap(final GameUI rootFrame, final Viewport uiViewport, String mapPreviewName) {
        rootFrame.setText(this.mapNameValue, mapPreviewName);
        rootFrame.setText(this.maxPlayersValue, rootFrame.getTemplates().getDecoratedString("UNKNOWNMAP_PLAYERCOUNT"));
        rootFrame.setText(this.suggestedPlayersValue,
                rootFrame.getTemplates().getDecoratedString("UNKNOWNMAP_SUGGESTEDPLAYERS"));
        rootFrame.setText(this.mapDescValue, rootFrame.getTemplates().getDecoratedString("UNKNOWNMAP_DESCRIPTION"));
        rootFrame.setText(this.mapSizeValue, rootFrame.getTemplates().getDecoratedString("UNKNOWNMAP_MAPSIZE"));
        this.maxPlayersIcon.setBackground(rootFrame.loadTexture("ui\\widgets\\glues\\icon-file-ums.blp"));

        rootFrame.setText(this.mapTilesetValue, rootFrame.getTemplates().getDecoratedString("UNKNOWNMAP_TILESET"));

        Texture minimapTexture;
        minimapTexture = rootFrame.loadTexture("ui\\widgets\\glues\\minimap-unknown.blp");
        this.minimapImageTextureFrame.setTexture(minimapTexture);

        this.mapInfoPaneFrame.positionBounds(rootFrame, uiViewport);
    }

    public void setMap(final GameUI rootFrame, final Viewport uiViewport, final War3Map map, final War3MapW3i mapInfo,
                       final War3MapConfig war3MapConfig) {
        rootFrame.setText(this.mapNameValue, rootFrame.getTrigStr(war3MapConfig.getMapName()));
        int usedPlayerCount = 0;
        for (int i = 0; i < WarsmashConstants.MAX_PLAYERS; i++) {
            final CBasePlayer player = war3MapConfig.getPlayer(i);
            if (player.getController() == CMapControl.USER) {
                usedPlayerCount++;
            }
        }
        rootFrame.setText(this.maxPlayersValue, Integer.toString(usedPlayerCount));
        rootFrame.setText(this.suggestedPlayersValue, rootFrame.getTrigStr(mapInfo.getRecommendedPlayers()));
        rootFrame.setText(this.mapDescValue, rootFrame.getTrigStr(war3MapConfig.getMapDescription()));
        rootFrame.setText(this.mapSizeValue, rootFrame.getTrigStr(Integer.toString(mapInfo.getPlayableSize()[0])));
        if (mapInfo.hasFlag(War3MapW3iFlags.MELEE_MAP)) {
            this.maxPlayersIcon.setBackground(rootFrame.loadTexture("ui\\widgets\\glues\\icon-file-melee.blp"));
        } else {
            this.maxPlayersIcon.setBackground(rootFrame.loadTexture("ui\\widgets\\glues\\icon-file-ums.blp"));
        }

        final StandardObjectData standardObjectData = new StandardObjectData(map);
        final DataTable worldEditData = standardObjectData.getWorldEditData();
        final Element tilesets = worldEditData.get("TileSets");
        final String tileSetNameKey = tilesets.getField(Character.toString(mapInfo.getTileset()), 0);
        final String tileSetNameString = worldEditData.getLocalizedString(tileSetNameKey);
        rootFrame.setText(this.mapTilesetValue, rootFrame.getTrigStr(tileSetNameString));

        Texture minimapTexture;
        if (mapInfo.hasFlag(War3MapW3iFlags.HIDE_MINIMAP_IN_PREVIEW_SCREENS)) {
            minimapTexture = rootFrame.loadTexture("ui\\widgets\\glues\\minimap-unknown.blp");
        } else {
            try {
                minimapTexture = ImageUtils.getAnyExtensionTexture(map, "war3mapPreview.blp");
            } catch (final Exception exc) {
                minimapTexture = ImageUtils.getAnyExtensionTexture(map, "war3mapMap.blp");
            }
        }
        this.minimapImageTextureFrame.setTexture(minimapTexture);

        this.mapInfoPaneFrame.positionBounds(rootFrame, uiViewport);
    }

}
