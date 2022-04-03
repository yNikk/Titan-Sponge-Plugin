package eu.flowtex.magicgames.utils;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;

public class Chat {
    public static int SUCCESS;
    public static int ERROR;
    public static int INFO;
    public static Text prefix;

    static {
        Chat.SUCCESS = 100;
        Chat.ERROR = 101;
        Chat.INFO = 102;
        Chat.prefix = Text.of(new Object[]{TextColors.BLUE, "MagicGames4: ", TextColors.RESET});
    }

    private static TextColor getColor(final int type) {
        TextColor color = null;
        if (type == Chat.SUCCESS) {
            color = TextColors.GREEN;
        } else if (type == Chat.ERROR) {
            color = TextColors.RED;
        } else if (type == Chat.INFO) {
            color = TextColors.AQUA;
        }
        return color;
    }

    public static void broadcastMessage(final Text t) {
        for (final Player p : Sponge.getGame().getServer().getOnlinePlayers()) {
            p.sendMessage(t);
        }
    }

    public static void broadcast(final String t, final int type) {
        broadcastMessage(Text.of(new Object[]{Chat.prefix, getColor(type), t}));
    }

    public static void send(final Player p, final String t, final int type) {
        p.sendMessage(Text.of(new Object[]{Chat.prefix, getColor(type), t}));
    }

    public static void opBroadcast(final String t, final int type) {
        for (final Player p : Sponge.getGame().getServer().getOnlinePlayers()) {
            if (p.hasPermission("magicgames4.admin")) {
                p.sendMessage(Text.of(new Object[]{Chat.prefix, getColor(type), t}));
            }
        }
    }
}
