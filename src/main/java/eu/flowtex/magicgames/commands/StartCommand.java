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

public class StartCommand implements CommandExecutor {
    Main main;

    public StartCommand(final Main main) {
        this.main = main;
    }

    public CommandResult execute(final CommandSource src, final CommandContext cmd) throws CommandException {
        if (src instanceof Player && this.main.isState(GameState.PREPARATION)) {
            final TimerManager tm = TimerManager.getInstance();
            tm.initiateStartTimerTask(this.main);
        }
        return CommandResult.success();
    }
}
