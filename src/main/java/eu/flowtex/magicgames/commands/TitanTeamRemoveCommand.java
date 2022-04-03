package eu.flowtex.magicgames.commands;

import eu.flowtex.magicgames.manager.TeamManager;
import eu.flowtex.magicgames.utils.Chat;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

public class TitanTeamRemoveCommand implements CommandExecutor {
    public CommandResult execute(final CommandSource src, final CommandContext cmd) throws CommandException {
        if (src instanceof Player) {
            final Player p = (Player) src;
            if (cmd.getOne("teamname").isPresent()) {
                final String teamname = (String) cmd.getOne("teamname").get();
                final TeamManager tm = TeamManager.getInstance();
                if (tm.teamExists(teamname)) {
                    tm.remove(tm.getTeam(teamname));
                    Chat.send(p, "Das Team wurde erfolgreich gel\u00f6scht!", Chat.SUCCESS);
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
