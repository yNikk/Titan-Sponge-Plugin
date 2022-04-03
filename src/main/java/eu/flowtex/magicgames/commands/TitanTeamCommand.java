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
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class TitanTeamCommand implements CommandExecutor {
    public CommandResult execute(final CommandSource src, final CommandContext cmd) throws CommandException {
        if (src instanceof Player) {
            final Player p = (Player) src;
            final TeamManager tm = TeamManager.getInstance();
            final Optional<String> tname = cmd.getOne("teamname");
            final Optional<String> playername1 = cmd.getOne("playername1");
            final Optional<String> playername2 = cmd.getOne("playername2");
            if (tname.isPresent()) {
                final String name = tname.get();
                if (tm.getTeam(tname.get()) != null) {
                    Chat.send(p, "Dieses Team existiert bereits!", Chat.ERROR);
                } else {
                    final Set<User> players = new HashSet<User>();
                    if (playername1.isPresent()) {
                        if (Data.getPlayerFromString(playername1.get()) == null) {
                            Chat.send(p, "Diese[r] Spieler existiert/existieren nicht!!", Chat.ERROR);
                            return CommandResult.success();
                        }
                        players.add(Data.getPlayerFromString(playername1.get()));
                    }
                    if (playername2.isPresent()) {
                        if (Data.getPlayerFromString(playername2.get()) == null) {
                            Chat.send(p, "Diese[r] Spieler existiert/existieren nicht!!", Chat.ERROR);
                            return CommandResult.success();
                        }
                        players.add(Data.getPlayerFromString(playername2.get()));
                    }
                    p.sendMessage(Text.builder().append(new Text[]{Chat.prefix}).append(new Text[]{Text.of(new Object[]{TextColors.GREEN, "Das Team ", TextColors.YELLOW, TextStyles.ITALIC, name, TextStyles.RESET, TextColors.GREEN, " wurde erfolgreich erstellt!"})}).toText());
                    tm.newTeam(name, players, players);
                }
            }
        } else {
            System.err.println("Du musst ein Spieler sein um diesen Command auszuf\u00fchren!");
        }
        return CommandResult.success();
    }
}
