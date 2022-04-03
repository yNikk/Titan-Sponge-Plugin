package eu.flowtex.magicgames.listeners;

import eu.flowtex.magicgames.Main;
import eu.flowtex.magicgames.manager.FileManager;
import eu.flowtex.magicgames.manager.TeamManager;
import eu.flowtex.magicgames.manager.TimerManager;
import eu.flowtex.magicgames.utils.Chat;
import eu.flowtex.magicgames.utils.Data;
import eu.flowtex.magicgames.utils.GameState;
import eu.flowtex.magicgames.utils.Team;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class PlayerJoinListener {

    Main main;
    int join_inivincibilty;

    @Listener
    public void onPlayerJoin(final ClientConnectionEvent.Join e) {
        e.setChannel((MessageChannel) null);
        final Player p = e.getTargetEntity();
        final FileManager fm = FileManager.getInstance();
        final int next = fm.getNextInt(204, p.getName());
        final Calendar cal = new GregorianCalendar();
        final Date date = cal.getTime();
        fm.write(204, String.valueOf(p.getName()) + "." + next, String.valueOf(date.toString()) + " - ");
        this.join_inivincibilty = (int) fm.get(205, "playing.join_invincibility");
        EntityDamageListener.tracker.put((User) p, null);
        if (Main.getInstance().isState(GameState.RUNNING)) {
            final TimerManager tm = TimerManager.getInstance();
            if (!TeamManager.getInstance().isSpectator((User) p)) {
                final Team t = TeamManager.getInstance().getPlayersTeam((User) p);
                if (!((Team) t).getAlivePlayers().contains(Data.getPlayerFromUUid(p.getUniqueId()))) {
                    System.out.println(String.valueOf(p.getName()) + " ist nicht am Leben in Team " + t.getName());
                    if ((double) p.getHealthData().health().get() != 0.0) {
                        t.setPlayerAlive((User) p, true);
                        t.setLives(t.getLives() - 1.0f);
                    }
                }
                p.offer(Keys.GAME_MODE, GameModes.SURVIVAL);
                if (tm.isTracked((User) p)) {
                    final int secondsLeft = tm.getValueForPlayer((User) p).getTimeLeft();
                    Chat.broadcastMessage(Text.of(new Object[]{TextColors.BLUE, "MagicGames4: ", TextColors.AQUA, "Der Spieler ", TextColors.YELLOW, TextStyles.ITALIC, p.getName(), TextColors.AQUA, TextStyles.RESET, " hat den Server betreten und hat noch ", TextColors.YELLOW, secondsLeft, " Sekunden", TextColors.AQUA, " Spielzeit."}));
                } else {
                    Chat.broadcastMessage(Text.of(new Object[]{TextColors.BLUE, "MagicGames4: ", TextColors.AQUA, "Der Spieler ", TextColors.YELLOW, TextStyles.ITALIC, p.getName(), TextColors.AQUA, TextStyles.RESET, " hat den Server betreten."}));
                    tm.newTrackedPlayer(this.main, (User) p, 900, this.join_inivincibilty);
                }
            } else {
                p.offer(Keys.GAME_MODE, GameModes.SPECTATOR);
            }
        } else if (Main.getInstance().isState(GameState.PREPARATION)) {
            p.offer(Keys.GAME_MODE, GameModes.ADVENTURE);
        } else {
            return;
        }
    }
}
