package eu.flowtex.magicgames.listeners;

import eu.flowtex.magicgames.manager.FileManager;
import eu.flowtex.magicgames.manager.TeamManager;
import eu.flowtex.magicgames.utils.Chat;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class PlayerQuitListener {

    @Listener
    public void onPlayerQuit(final ClientConnectionEvent.Disconnect e) {
        e.setChannel((MessageChannel) null);
        final FileManager fm = FileManager.getInstance();
        final Player p = e.getTargetEntity();
        final int actual = fm.getActualInt(204, p.getName());
        final Calendar cal = new GregorianCalendar();
        final Date date = cal.getTime();
        final String first = String.valueOf(fm.get(204, String.valueOf(p.getName()) + "." + actual));
        EntityDamageListener.tracker.remove(p);
        fm.write(204, String.valueOf(p.getName()) + "." + actual, String.valueOf(first) + date.toString());
        if (TeamManager.getInstance().getPlayersTeam((User) p) != null && (TeamManager.getInstance().getPlayersTeam((User) p).getAlivePlayers().contains(p) || TeamManager.getInstance().getPlayersTeam((User) p).getLives() >= 1.0f) && !TeamManager.getInstance().isSpectator((User) p)) {
            Chat.broadcastMessage(Text.of(new Object[]{TextColors.BLUE, "MagicGames4: ", TextColors.YELLOW, TextStyles.ITALIC, p.getName(), TextColors.AQUA, TextStyles.RESET, " hat den Server verlassen."}));
        }
    }
}
