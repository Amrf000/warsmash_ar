package com.etheller.warsmash.viewer5.handlers.w3x.simulation.config;

import com.etheller.warsmash.viewer5.handlers.w3x.simulation.players.CMapControl;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.players.CMapFlag;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.players.CMapPlacement;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.trigger.enumtypes.CGameSpeed;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.trigger.enumtypes.CGameType;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.trigger.enumtypes.CMapDensity;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.trigger.enumtypes.CMapDifficulty;

import java.util.EnumMap;

public class War3MapConfig implements CPlayerAPI {
    private final War3MapConfigStartLoc[] startLocations;
    private final CBasePlayer[] players;
    private final EnumMap<CGameType, Boolean> gameTypeToSupported = new EnumMap<>(CGameType.class);
    private final EnumMap<CMapFlag, Boolean> mapFlagToEnabled = new EnumMap<>(CMapFlag.class);
    private String mapName;
    private String mapDescription;
    private int teamCount;
    private int playerCount;
    private CMapPlacement placement;
    private CGameSpeed gameSpeed;
    private CMapDifficulty gameDifficulty;
    private CMapDensity resourceDensity;
    private CMapDensity creatureDensity;
    private CGameType gameTypeSelected;

    public War3MapConfig(final int maxPlayers) {
        // TODO should this be WarsmashConstants.MAX_PLAYERS instead of local
        // constructor arg?
        this.startLocations = new War3MapConfigStartLoc[maxPlayers];
        this.players = new CBasePlayer[maxPlayers];
        for (int i = 0; i < maxPlayers; i++) {
            this.startLocations[i] = new War3MapConfigStartLoc();
            this.players[i] = new War3MapConfigPlayer(i);
            if (i >= (maxPlayers - 4)) {
                this.players[i].setController(CMapControl.NEUTRAL);
            }
        }
    }

    public String getMapName() {
        return this.mapName;
    }

    public void setMapName(final String mapName) {
        this.mapName = mapName;
    }

    public String getMapDescription() {
        return this.mapDescription;
    }

    public void setMapDescription(final String mapDescription) {
        this.mapDescription = mapDescription;
    }

    public void defineStartLocation(final int whichStartLoc, final float x, final float y) {
        final War3MapConfigStartLoc startLoc = this.startLocations[whichStartLoc];
        startLoc.setX(x);
        startLoc.setY(y);
    }

    public War3MapConfigStartLoc getStartLoc(final int whichStartLoc) {
        return this.startLocations[whichStartLoc];
    }

    public void setGameTypeSupported(final CGameType gameType, final boolean supported) {
        this.gameTypeToSupported.put(gameType, supported);
    }

    public void setMapFlag(final CMapFlag mapFlag, final boolean set) {
        this.mapFlagToEnabled.put(mapFlag, set);
    }

    public int getTeamCount() {
        return this.teamCount;
    }

    public void setTeamCount(final int teamCount) {
        this.teamCount = teamCount;
    }

    public int getPlayerCount() {
        return this.playerCount;
    }

    public void setPlayerCount(final int playerCount) {
        this.playerCount = playerCount;
    }

    public boolean isGameTypeSupported(final CGameType gameType) {
        final Boolean supported = this.gameTypeToSupported.get(gameType);
        return (supported != null) && supported;
    }

    public CGameType getGameTypeSelected() {
        return this.gameTypeSelected;
    }

    public boolean isMapFlagSet(final CMapFlag mapFlag) {
        final Boolean flag = this.mapFlagToEnabled.get(mapFlag);
        return (flag != null) && flag;
    }

    public CMapPlacement getPlacement() {
        return this.placement;
    }

    public void setPlacement(final CMapPlacement placement) {
        this.placement = placement;
    }

    public CGameSpeed getGameSpeed() {
        return this.gameSpeed;
    }

    public void setGameSpeed(final CGameSpeed gameSpeed) {
        this.gameSpeed = gameSpeed;
    }

    public CMapDifficulty getGameDifficulty() {
        return this.gameDifficulty;
    }

    public void setGameDifficulty(final CMapDifficulty gameDifficulty) {
        this.gameDifficulty = gameDifficulty;
    }

    public CMapDensity getResourceDensity() {
        return this.resourceDensity;
    }

    public void setResourceDensity(final CMapDensity resourceDensity) {
        this.resourceDensity = resourceDensity;
    }

    public CMapDensity getCreatureDensity() {
        return this.creatureDensity;
    }

    public void setCreatureDensity(final CMapDensity creatureDensity) {
        this.creatureDensity = creatureDensity;
    }

    public float getStartLocationX(final int startLocIndex) {
        return this.startLocations[startLocIndex].getX();
    }

    public float getStartLocationY(final int startLocIndex) {
        return this.startLocations[startLocIndex].getY();
    }

    @Override
    public CBasePlayer getPlayer(final int index) {
        return this.players[index];
    }
}
