package eu.flowtex.magicgames.commands;

import eu.flowtex.magicgames.utils.Chat;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class PingCommand implements CommandExecutor {
    public CommandResult execute(final CommandSource src, final CommandContext cmd) throws CommandException {
        if (src instanceof Player) {
            final Player p = (Player) src;
            try {
                p.sendMessage(Text.of(new Object[]{Chat.prefix, TextColors.GREEN, "Dein Ping betr\u00e4gt: ", TextColors.YELLOW, TextStyles.ITALIC, this.getPing(p)}));
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException ex2) {
                final Exception ex = null;
                final Exception e = ex;
                e.printStackTrace();
            }
        } else {
            System.err.println("Du musst ein Spieler sein um diesen Command auszuf\u00fchren!");
        }
        return CommandResult.success();
    }

    public int getPing(final Player player) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException {
        final Object nms_player = player.getClass().getMethod("getHandle", (Class<?>[]) new Class[0]).invoke(player, new Object[0]);
        final Field fieldPing = nms_player.getClass().getDeclaredField("ping");
        fieldPing.setAccessible(true);
        return fieldPing.getInt(nms_player);
    }
}
