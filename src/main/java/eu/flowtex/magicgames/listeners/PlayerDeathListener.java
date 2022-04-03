package eu.flowtex.magicgames.listeners;

import eu.flowtex.magicgames.Main;
import eu.flowtex.magicgames.manager.TeamManager;
import eu.flowtex.magicgames.manager.TimerManager;
import eu.flowtex.magicgames.utils.Chat;
import eu.flowtex.magicgames.utils.GameState;
import eu.flowtex.magicgames.utils.Team;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

public class PlayerDeathListener {

    @Listener
    public void onDeath(final DestructEntityEvent.Death e) {
        e.setChannel((MessageChannel) null);
        if (e.getTargetEntity() instanceof Player) {
            final TeamManager tm = TeamManager.getInstance();
            final TimerManager tm2 = TimerManager.getInstance();
            final Player p = (Player) e.getTargetEntity();
            p.offer(Keys.GAME_MODE, GameModes.SURVIVAL);
            if (EntityDamageListener.getLastDamager(p) != null) {
                final User attacker = EntityDamageListener.getLastDamager(p);
                final Team t = tm.getPlayersTeam(attacker);
                Chat.broadcastMessage(Text.of(new Object[]{TextColors.YELLOW, TextStyles.ITALIC, p.getName(), TextColors.DARK_RED, TextStyles.RESET, " wurde von ", TextColors.YELLOW, TextStyles.ITALIC, attacker.getName(), TextColors.DARK_RED, TextStyles.RESET, " get\u00f6tet"}));
                if (t.getLives() <= 2.5) {
                    t.setLives(t.getLives() + 0.5f);
                }
            } else {
                Chat.broadcastMessage(Text.of(new Object[]{TextColors.YELLOW, TextStyles.ITALIC, p.getName(), TextColors.DARK_RED, TextStyles.RESET, " ist gestorben."}));
            }
            final Team team = tm.getPlayersTeam((User) p);
            team.setPlayerAlive((User) p, false);
            if (!team.isAlive()) {
                tm2.removeTrackedPlayer((User) p);
                p.kick(Text.of(new Object[]{TextColors.RED, "Euer Team ist ", TextColors.GOLD, "endg\u00fcltig", TextColors.RED, " ausgeschieden!"}));
                Chat.broadcastMessage(Text.builder().append(new Text[]{Chat.prefix}).append(new Text[]{Text.of(new Object[]{TextColors.YELLOW, TextStyles.ITALIC, team.getName(), TextColors.DARK_RED, TextStyles.RESET, " ist ausgeschieden."})}).build());
            } else if (team.getLives() < 1.0f) {
                tm2.removeTrackedPlayer((User) p);
                p.kick(Text.of(new Object[]{TextColors.RED, "Du bist ", TextColors.GOLD, "vorerst", TextColors.RED, " ausgeschieden!"}));
            }
            if (tm.getAliveTeams().size() == 1 && !Main.getInstance().isState(GameState.ENDED)) {
                Main.getInstance().setState(GameState.ENDED);
                Chat.broadcastMessage(Text.of(new Object[]{TextColors.GOLD, "Lucky Titan ", TextColors.AQUA, "ist beendet!"}));
                Chat.broadcastMessage(Text.of(new Object[]{TextColors.AQUA, "Herzlichen Gl\u00fcckwunsch an ", TextColors.GOLD, tm.getAliveTeams().get(0).getName(), "!"}));
            }
        }
    }
}
