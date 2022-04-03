package eu.flowtex.magicgames.commands;

import eu.flowtex.magicgames.Main;
import eu.flowtex.magicgames.manager.TimerManager;
import eu.flowtex.magicgames.utils.GameState;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

public class TimeCommand implements CommandExecutor {
    Main main;

    public TimeCommand() {
        this.main = Main.getInstance();
    }

    public CommandResult execute(final CommandSource src, final CommandContext cmd) throws CommandException {
        if (src instanceof Player) {
            final Player p = (Player) src;
            final TimerManager tm = TimerManager.getInstance();
            if (Main.getInstance().isState(GameState.RUNNING) && tm.isTracked(p)) {
                final double seconds = tm.getValueForPlayer(p).getTimeLeft();
                final double minutes = seconds / 60.0;
                final double roundedMinutes = Math.round(minutes * 10.0) / 10.0;
                p.sendMessage(Text.of(TextColors.BLUE, "MagicGames4: ", TextColors.AQUA, "Du hast noch ", TextColors.YELLOW, TextStyles.ITALIC, roundedMinutes, TextStyles.RESET, TextColors.AQUA, " Minuten Spielzeit."));
            }
        }
        return CommandResult.success();
    }
}
