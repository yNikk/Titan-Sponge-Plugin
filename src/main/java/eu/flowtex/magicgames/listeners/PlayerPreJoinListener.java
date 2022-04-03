package eu.flowtex.magicgames.listeners;

import eu.flowtex.magicgames.Main;
import eu.flowtex.magicgames.manager.TeamManager;
import eu.flowtex.magicgames.utils.Data;
import eu.flowtex.magicgames.utils.GameState;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class PlayerPreJoinListener {
    Main main;
    int join_inivincibilty;

    public PlayerPreJoinListener() {
        this.main = Main.getInstance();
    }

    @Listener
    public void onPlayerPreJoin(final ClientConnectionEvent.Auth e) {
        final User p = Data.getPlayerFromUUid(e.getProfile().getUniqueId());
        final TeamManager tm = TeamManager.getInstance();
        if (!tm.isSpectator(p) && !this.main.isState(GameState.PREPARATION)) {
            if (tm.getPlayersTeam(p) != null) {
                if (!tm.getPlayersTeam(p).isAlive()) {
                    e.setCancelled(true);
                    e.setMessage(Text.of(new Object[]{TextColors.RED, "Euer Team ist ", TextColors.GOLD, "endg\u00fcltig", TextColors.RED, " ausgeschieden!"}));
                } else if (!TeamManager.getInstance().getPlayersTeam(p).getAlivePlayers().contains(p) && TeamManager.getInstance().getPlayersTeam(p).getLives() < 1.0f) {
                    e.setCancelled(true);
                    e.setMessage(Text.of(new Object[]{TextColors.RED, "Du bist ", TextColors.GOLD, "vorerst", TextColors.RED, " ausgeschieden!"}));
                }
            } else {
                e.setMessage(Text.of(new Object[]{TextColors.RED, "Du bist nicht registriert!"}));
                e.setCancelled(true);
            }
        }
    }
}
