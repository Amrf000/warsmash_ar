package com.etheller.warsmash.networking;

public interface GameTurnManager {
    GameTurnManager PAUSED = new GameTurnManager() {
        @Override
        public int getLatestCompletedTurn() {
            return Integer.MIN_VALUE;
        }

        @Override
        public void turnCompleted(final int gameTurnTick) {
            System.err.println("got turnCompleted(" + gameTurnTick + ") while paused !!");
        }

        @Override
        public void framesSkipped(final float skippedCount) {
        }
    };

    int getLatestCompletedTurn();

    void turnCompleted(int gameTurnTick);

    void framesSkipped(float skippedCount);

}
