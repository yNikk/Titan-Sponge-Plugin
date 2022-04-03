package eu.flowtex.magicgames.listeners;

import eu.flowtex.magicgames.manager.TeamManager;
import eu.flowtex.magicgames.manager.TimerManager;
import eu.flowtex.magicgames.utils.Chat;
import eu.flowtex.magicgames.utils.Team;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.living.humanoid.player.RespawnPlayerEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class PlayerRespawnListener {
    @Listener
    public void onRespawn(final RespawnPlayerEvent e) {
        final Player p = e.getTargetEntity();
        final TeamManager tm = TeamManager.getInstance();
        final TimerManager tm2 = TimerManager.getInstance();
        if (tm.getPlayersTeam((User) p) != null) {
            final Team t = tm.getPlayersTeam((User) p);
            if (t.getLives() >= 1.0f) {
                t.setLives(t.getLives() - 1.0f);
                tm2.getValueForPlayer((User) p).setInvincibleLeft(120);
                t.setPlayerAlive((User) p, true);
                p.sendMessage(Text.builder().append(new Text[]{Chat.prefix}).append(new Text[]{Text.of(new Object[]{TextColors.AQUA, "Du hast ", TextColors.YELLOW, "2 Minuten", TextColors.AQUA, " Schutzzeit!"})}).build());
            } else {
                tm2.removeTrackedPlayer((User) p);
                p.kick(Text.of(new Object[]{TextColors.RED, "Du kannst nicht respawnen!"}));
            }
        }
    }
}
