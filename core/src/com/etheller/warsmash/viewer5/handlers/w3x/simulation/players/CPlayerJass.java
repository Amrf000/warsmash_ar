package com.etheller.warsmash.viewer5.handlers.w3x.simulation.players;

import com.etheller.interpreter.ast.scope.GlobalScope;
import com.etheller.interpreter.ast.scope.trigger.RemovableTriggerEvent;
import com.etheller.interpreter.ast.scope.trigger.Trigger;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.ai.AIDifficulty;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.trigger.JassGameEventsWar3;
import com.etheller.warsmash.viewer5.handlers.w3x.simulation.trigger.enumtypes.CPlayerSlotState;

public interface CPlayerJass {
    int getId();

    void forceStartLocation(int startLocIndex);

    void setAlliance(int otherPlayerIndex, CAllianceType whichAllianceSetting, boolean value);

    boolean hasAlliance(int otherPlayerIndex, CAllianceType allianceType);

    void setTaxRate(int otherPlayerIndex, CPlayerState whichResource, int rate);

    void setRacePref(CRacePreference whichRacePreference);

    void setOnScoreScreen(boolean flag);

    int getTeam();

    void setTeam(int team);

    int getStartLocationIndex();

    void setStartLocationIndex(int startLocIndex);

    int getColor();

    void setColor(int colorIndex);

    boolean isRaceSelectable();

    void setRaceSelectable(boolean selectable);

    CMapControl getController();

    void setController(CMapControl mapControl);

    CPlayerSlotState getSlotState();

    void setSlotState(CPlayerSlotState slotState);

    AIDifficulty getAIDifficulty();

    void setAIDifficulty(AIDifficulty aiDifficulty);

    int getTaxRate(int otherPlayerIndex, CPlayerState whichResource);

    boolean isRacePrefSet(CRacePreference pref);

    String getName();

    void setName(String name);

    RemovableTriggerEvent addEvent(final GlobalScope globalScope, final Trigger whichTrigger,
                                   final JassGameEventsWar3 eventType);

    void removeEvent(CPlayerEvent playerEvent);
}
