package eu.flowtex.magicgames.commands;

import eu.flowtex.magicgames.utils.Data;
import eu.flowtex.magicgames.manager.TeamManager;
import eu.flowtex.magicgames.utils.Chat;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;

public class SpectatorCommand implements CommandExecutor {
    public CommandResult execute(final CommandSource src, final CommandContext cmd) throws CommandException {
        if (src instanceof Player) {
            final Player p = (Player) src;
            final TeamManager tm = TeamManager.getInstance();
            if (cmd.getOne("playername").isPresent()) {
                final String playername = (String) cmd.getOne("playername").get();
                if (Data.getPlayerFromString(playername) != null) {
                    final User spec = Data.getPlayerFromString(playername);
                    tm.addSpectator(spec);
                    Chat.send(p, "Der Spectator wurde hinzugef\u00fcgt!", Chat.SUCCESS);
                } else {
                    Chat.send(p, "Der Spieler existiert nicht!", Chat.ERROR);
                }
            }
        } else {
            System.err.println("Du musst ein Spieler sein um diesen Command auszuf\u00fchren!");
        }
        return CommandResult.success();
    }
}
