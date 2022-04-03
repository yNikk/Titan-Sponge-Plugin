package eu.flowtex.magicgames.listeners;

import eu.flowtex.magicgames.manager.TeamManager;
import eu.flowtex.magicgames.utils.Chat;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.format.TextColors;

import java.util.Optional;

public class PlayerChatListener {
    @Listener
    public void onChat(final MessageChannelEvent.Chat e) {
        e.setChannel((MessageChannel) null);
        final Optional<Player> player = (Optional<Player>) e.getCause().first((Class) Player.class);
        if (player.isPresent()) {
            final Player p = player.get();
            final Text msg = e.getRawMessage();
            Text prefix = (Text) Text.of("");
            e.setCancelled(true);
            final TeamManager tm = TeamManager.getInstance();
            if (tm.getPlayersTeam((User) p) != null) {
                prefix = Text.of(new Object[]{"[", tm.getPlayersTeam((User) p).getColor(), tm.getPlayersTeam((User) p).getName(), TextColors.RESET, "] "});
            }
            Chat.broadcastMessage(Text.builder().append(new Text[]{prefix}).append(new Text[]{Text.of(new Object[]{TextColors.GRAY, p.getName(), ": ", TextColors.RESET})}).append(new Text[]{msg}).build());
        }
    }
}
