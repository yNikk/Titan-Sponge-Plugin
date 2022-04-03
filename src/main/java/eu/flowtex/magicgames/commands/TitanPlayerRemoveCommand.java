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

public class TitanPlayerRemoveCommand implements CommandExecutor {
    public CommandResult execute(final CommandSource src, final CommandContext cmd) throws CommandException {
        if (src instanceof Player) {
            final Player p = (Player) src;
            if (cmd.getOne("teamname").isPresent() && cmd.getOne("playername1").isPresent()) {
                final String teamname = (String) cmd.getOne("teamname").get();
                final String playername = (String) cmd.getOne("playername1").get();
                final TeamManager tm = TeamManager.getInstance();
                if (tm.teamExists(teamname)) {
                    if (Data.getPlayerFromString(playername) != null) {
                        tm.getTeam(teamname).removePlayer(Data.getPlayerFromString(playername));
                        Chat.send(p, "Der Spieler wurde entfernt!", Chat.SUCCESS);
                    } else {
                        Chat.send(p, "Dieser Spieler existiert nicht!", Chat.ERROR);
                    }
                } else {
                    Chat.send(p, "Das Team existiert nicht!", Chat.ERROR);
                }
            }
        } else {
            System.err.println("Du musst ein Spieler sein um den Command auszuf\u00fchren!!");
        }
        return CommandResult.success();
    }
}
