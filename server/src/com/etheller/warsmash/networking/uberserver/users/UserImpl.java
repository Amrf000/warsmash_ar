package com.etheller.warsmash.networking.uberserver.users;

public final class UserImpl implements User {
    private final int id;
    private String username;
    private String passwordHash;
    private String userHash;
    private final UserStats userStats;
    private final UserRanking userRanking;
    private int level;
    private int experience;
    private transient UserManager changeListener;

    public UserImpl(final String username, final String passwordHash, final int id, final String userHash,
                    final UserManager changeListener) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.id = id;
        this.changeListener = changeListener;
        this.userStats = new UserStats();
        this.userRanking = new UserRanking();
        this.level = 1;
        this.experience = 0;
    }

    public void resumeTransientFields(final UserManager changeListener) {
        this.changeListener = changeListener;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public void setUsername(final String username) {
        this.username = username;
        this.changeListener.notifyUsersUpdated();
    }

    @Override
    public String getPasswordHash() {
        return this.passwordHash;
    }

    @Override
    public void setPasswordHash(final String passwordHash) {
        this.passwordHash = passwordHash;
        this.changeListener.notifyUsersUpdated();
    }

    @Override
    public UserStats getUserStats() {
        return this.userStats;
    }

    @Override
    public UserRanking getUserRanking() {
        return this.userRanking;
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public int getExperience() {
        return this.experience;
    }

    @Override
    public void addExperience(final int amount) {
        this.experience += amount;
        while (this.experience >= Math.pow(200, this.level + 1)) {
            this.level++;
        }
        this.changeListener.notifyUsersUpdated();
    }

    @Override
    public void addWin(final boolean ranked) {
        this.userStats.setGamesWon(this.userStats.getGamesWon() + 1);
        this.userStats.setGamesPlayed(this.userStats.getGamesPlayed() + 1);
        if (ranked) {
            this.userRanking.setRankedGamesPlayed(this.userRanking.getRankedGamesPlayed() + 1);
            this.userRanking.setRankedGamesWon(this.userRanking.getRankedGamesWon() + 1);
        }
        addExperience(225);
        // add experience will call the notifyUsersUpdated for us, for now
    }

    @Override
    public void addLoss(final boolean ranked) {
        this.userStats.setGamesLost(this.userStats.getGamesLost() + 1);
        this.userStats.setGamesPlayed(this.userStats.getGamesPlayed() + 1);
        if (ranked) {
            this.userRanking.setRankedGamesPlayed(this.userRanking.getRankedGamesPlayed() + 1);
            this.userRanking.setRankedGamesLost(this.userRanking.getRankedGamesLost() + 1);
        }
        addExperience(125);
        // add experience will call the notifyUsersUpdated for us, for now
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getHash() {
        return this.userHash;
    }
}
