package eu.flowtex.magicgames.listeners;

import eu.flowtex.magicgames.Main;
import eu.flowtex.magicgames.manager.TeamManager;
import eu.flowtex.magicgames.manager.TimerManager;
import eu.flowtex.magicgames.utils.GameState;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.ai.AITaskEvent;

public class AITaskListener {

    Main main;
    public AITaskListener() {
        this.main = Main.getInstance();
    }

    @Listener
    public void onTarget(final AITaskEvent.Add e) {
        if (e.getTargetEntity() instanceof Player) {
            final Player target = (Player) e.getTargetEntity();
            e.setCancelled(this.shouldCancel(target));
        }
    }

    public static boolean shouldCancel(final Player p) {
        if (Main.getInstance().isState(GameState.RUNNING)) {
            final TimerManager tm = TimerManager.getInstance();
            if (tm.isTracked((User) p) && tm.getValueForPlayer((User) p).isInvincible()) {
                return true;
            }
        } else {
            if (TeamManager.getInstance().isSpectator((User) p)) {
                return true;
            }
            if (Main.getInstance().isState(GameState.PREPARATION)) {
                return true;
            }
        }
        return false;
    }
}
