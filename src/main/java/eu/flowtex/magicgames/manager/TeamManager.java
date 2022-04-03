package eu.flowtex.magicgames.manager;

import eu.flowtex.magicgames.utils.Data;
import eu.flowtex.magicgames.utils.Team;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.text.format.TextColor;

import java.util.*;

public class TeamManager {
    private static TeamManager instance;
    private List<Team> teams;
    private List<User> specs;

    static {
        TeamManager.instance = null;
    }

    private TeamManager() {
        this.teams = new ArrayList<Team>();
        this.specs = new ArrayList<User>();
    }

    public static TeamManager getInstance() {
        if (TeamManager.instance == null) {
            TeamManager.instance = new TeamManager();
        }
        return TeamManager.instance;
    }

    public void loadTeams() {
        final FileManager fm = FileManager.getInstance();
        if (fm.contains(201, "teamnames")) {
            final List<String> teamnames = fm.getStringList(201, "teamnames");
            if (!teamnames.isEmpty()) {
                for (final String name : teamnames) {
                    final String colorstring = String.valueOf(fm.get(201, "teams." + name + ".color"));
                    final TextColor color = Data.getColorByString(colorstring);
                    final Set<User> players = new HashSet<User>();
                    if (fm.contains(201, "teams." + name + ".players")) {
                        final List<String> playeruuids = fm.getStringList(201, "teams." + name + ".players");
                        for (final String uuid : playeruuids) {
                            players.add(Data.getPlayerFromUUid(UUID.fromString(uuid)));
                        }
                    }
                    final Set<User> alivePlayers = new HashSet<User>();
                    if (fm.contains(201, "teams." + name + ".alive_players")) {
                        final List<String> alivePlayerUUIDS = fm.getStringList(201, "teams." + name + ".alive_players");
                        for (final String uuid2 : alivePlayerUUIDS) {
                            alivePlayers.add(Data.getPlayerFromUUid(UUID.fromString(uuid2)));
                        }
                    }
                    final float lives = Float.parseFloat(String.valueOf(fm.get(201, "teams." + name + ".lives")));
                    final Team t = this.newTeam(name, players, alivePlayers);
                    t.setColor(color);
                    t.setLives(lives);
                }
                this.saveTeams();
            }
        }
        if (fm.contains(201, "specs")) {
            final List<String> specUUIDS = fm.getStringList(201, "specs");
            for (final String stringUuid : specUUIDS) {
                this.specs.add(Data.getPlayerFromUUid(UUID.fromString(stringUuid)));
            }
        }
    }

    public Team newTeam(final String name, final Set<User> players, final Set<User> alivePlayers) {
        final Team team = new Team(name);
        if (!players.isEmpty()) {
            for (final User p : players) {
                team.addPlayer(p);
            }
        }
        if (!alivePlayers.isEmpty()) {
            for (final User p : alivePlayers) {
                team.setPlayerAlive(p, true);
            }
        }
        this.teams.add(team);
        this.saveTeams();
        return team;
    }

    public Team getPlayersTeam(final User p) {
        Team team = null;
        if (!this.teams.isEmpty()) {
            for (final Team t : this.teams) {
                if (t.hasPlayer(p)) {
                    team = t;
                    break;
                }
            }
        }
        return team;
    }

    public List<Team> getTeams() {
        return this.teams;
    }

    public void remove(final Team t) {
        this.teams.remove(t);
    }

    public Team getTeam(final String name) {
        Team t = null;
        if (!this.teams.isEmpty()) {
            for (final Team team : this.teams) {
                if (team.getName().equalsIgnoreCase(name)) {
                    t = team;
                }
            }
        }
        return t;
    }

    public List<Team> getAliveTeams() {
        final List<Team> aliveTeams = new ArrayList<Team>();
        for (final Team t : this.teams) {
            if (t.isAlive()) {
                aliveTeams.add(t);
            }
        }
        return aliveTeams;
    }

    public void addSpectator(final User p) {
        this.specs.add(p);
    }

    public void removeSpectator(final User p) {
        if (this.specs.contains(p)) {
            this.specs.remove(p);
        }
    }

    public boolean isSpectator(final User p) {
        return this.specs.contains(Data.getPlayerFromUUid(p.getUniqueId()));
    }

    public boolean teamExists(final String name) {
        return this.getTeam(name) != null;
    }

    public void saveTeams() {
        final FileManager fm = FileManager.getInstance();
        final List<String> teamnames = new ArrayList<String>();
        if (!this.teams.isEmpty()) {
            for (final Team t : this.teams) {
                teamnames.add(t.getName());
                final String path = "teams." + t.getName();
                final String color = Data.getStringbyColor(t.getColor());
                fm.write(201, String.valueOf(path) + ".color", color);
                fm.write(201, String.valueOf(path) + ".lives", t.getLives());
                if (!t.players.isEmpty()) {
                    final List<String> players = new ArrayList<String>();
                    for (final UUID uuid : t.players) {
                        players.add(uuid.toString());
                    }
                    fm.write(201, String.valueOf(path) + ".players", players);
                }
                final List<String> alivePlayers = new ArrayList<String>();
                for (final UUID id : t.alivePlayers) {
                    alivePlayers.add(id.toString());
                }
                fm.write(201, String.valueOf(path) + ".alive_players", alivePlayers);
            }
            fm.write(201, "teamnames", teamnames);
        }
        if (!this.specs.isEmpty()) {
            final List<String> specUUIDs = new ArrayList<String>();
            for (final User p : this.specs) {
                specUUIDs.add(p.getUniqueId().toString());
            }
            fm.write(201, "specs", specUUIDs);
        }
        System.out.println("Die Teams wurden erfolgreich gespeichert!");
    }
}
