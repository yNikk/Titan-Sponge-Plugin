package eu.flowtex.magicgames.commands;

import eu.flowtex.magicgames.manager.TeamManager;
import eu.flowtex.magicgames.utils.Team;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import java.util.ArrayList;
import java.util.List;

public class TeamsCommand implements CommandExecutor {
    public CommandResult execute(final CommandSource src, final CommandContext cmd) throws CommandException {
        if (src instanceof Player) {
            final Player p = (Player) src;
            final TeamManager tm = TeamManager.getInstance();
            p.sendMessage(Text.of(new Object[]{TextColors.DARK_GREEN, TextStyles.ITALIC, "Alle lebenden Teams:"}));
            final List<Team> sorted = this.sortTeamsByLives(tm.getAliveTeams());
            for (int i = 0; i < sorted.size(); ++i) {
                final Team t = sorted.get(i);
                p.sendMessage(Text.of(new Object[]{TextColors.DARK_PURPLE, t.getName(), TextColors.YELLOW, " ", t.getLives()}));
            }
        } else {
            System.err.println("Du musst ein Spieler sein um diesen Command auszuf\u00fchren!");
        }
        return CommandResult.success();
    }

    private List<Team> sortTeamsByLives(final List<Team> teamList) {
        final List<Team> sortedTeams = new ArrayList<Team>();
        System.out.println(teamList.size());
        while (!teamList.isEmpty()) {
            float maximum = -1.0f;
            Team team = null;
            for (final Team t : teamList) {
                if (t.getLives() > maximum) {
                    team = t;
                    maximum = t.getLives();
                }
            }
            if (team != null) {
                sortedTeams.add(team);
                teamList.remove(team);
                System.out.println(String.valueOf(team.getName()) + ": " + maximum);
            }
        }
        return sortedTeams;
    }
}
