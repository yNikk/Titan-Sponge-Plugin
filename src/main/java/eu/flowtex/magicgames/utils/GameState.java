package eu.flowtex.magicgames.utils;

import org.spongepowered.api.entity.living.player.gamemode.GameMode;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;

public enum GameState {
    PREPARATION("PREPARATION", 0, GameModes.ADVENTURE),
    INITIAL_GRACE_PERIOD("INITIAL_GRACE_PERIOD", 1, GameModes.ADVENTURE),
    RUNNING("RUNNING", 2, GameModes.SURVIVAL),
    ENDED("ENDED", 3, GameModes.CREATIVE),
    STARTED("STARTED", 4, GameModes.ADVENTURE);

    private GameMode gameMode;

    private GameState(final String s, final int n, final GameMode gm) {
        this.gameMode = gm;
    }

    public GameMode getGameMode() {
        return this.gameMode;
    }
}
