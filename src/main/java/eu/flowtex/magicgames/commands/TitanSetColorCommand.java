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

public class TitanSetColorCommand implements CommandExecutor {
    public CommandResult execute(final CommandSource src, final CommandContext cmd) throws CommandException {
        if (src instanceof Player) {
            final Player p = (Player) src;
            if (cmd.getOne("teamname").isPresent() && cmd.getOne("color").isPresent()) {
                final String name = (String) cmd.getOne("teamname").get();
                final String color = (String) cmd.getOne("color").get();
                final TeamManager tm = TeamManager.getInstance();
                if (tm.teamExists(name)) {
                    if (Data.getColorByString(color) != null) {
                        tm.getTeam(name).setColor(Data.getColorByString(color));
                        Chat.send(p, "Die Farbe wurde gesetzt!", Chat.SUCCESS);
                    } else {
                        Chat.send(p, "Diese Farbe gibt es nicht!", Chat.ERROR);
                    }
                } else {
                    Chat.send(p, "Dieses Team existiert nicht!", Chat.ERROR);
                }
            }
        } else {
            System.err.println("Du musst ein Spieler sein um den Command auszuf\u00fchren!!");
        }
        return CommandResult.success();
    }
}
