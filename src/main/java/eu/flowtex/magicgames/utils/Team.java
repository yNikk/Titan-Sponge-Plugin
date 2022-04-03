package eu.flowtex.magicgames.utils;

import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Team {
    private String name;
    public Set<UUID> players;
    public Set<UUID> alivePlayers;
    private TextColor color;
    private float lives;
    boolean alive;

    public Team(final String name) {
        this.name = name;
        this.players = new HashSet<UUID>();
        this.alivePlayers = new HashSet<UUID>();
        this.color = TextColors.WHITE;
        this.lives = 2.0f;
    }

    public void addPlayer(final User p) {
        this.players.add(p.getUniqueId());
    }

    public void removePlayer(final User p) {
        this.players.remove(p.getUniqueId());
        if (this.players.contains(p.getUniqueId())) {
            this.players.remove(p.getUniqueId());
        }
    }

    public void setPlayerAlive(final User p, final boolean alive) {
        if (alive) {
            if (!this.alivePlayers.contains(p.getUniqueId())) {
                this.alivePlayers.add(p.getUniqueId());
            }
        } else if (this.alivePlayers.contains(p.getUniqueId())) {
            this.alivePlayers.remove(p.getUniqueId());
        }
    }

    public Set<User> getAlivePlayers() {
        final Set<User> alivePlayers = new HashSet<User>();
        for (final UUID id : this.alivePlayers) {
            alivePlayers.add(Data.getPlayerFromUUid(id));
        }
        return alivePlayers;
    }

    public boolean hasPlayer(final User p) {
        return this.players.contains(p.getUniqueId());
    }

    public Set<User> getPlayers() {
        final Set<User> players = new HashSet<User>();
        for (final UUID id : this.players) {
            players.add(Data.getPlayerFromUUid(id));
        }
        return players;
    }

    public void setColor(final TextColor color) {
        this.color = color;
    }

    public TextColor getColor() {
        return this.color;
    }

    public int getSize() {
        return this.players.size();
    }

    public String getName() {
        return this.name;
    }

    public boolean isAlive() {
        this.updateAlive();
        return this.alive;
    }

    public void updateAlive() {
        if (this.alivePlayers.isEmpty() && this.lives < 1.0f) {
            this.alive = false;
            this.lives = 0.0f;
        } else {
            this.alive = true;
        }
    }

    public void setAlive(final boolean alive) {
        this.alive = alive;
    }

    public float getLives() {
        return this.lives;
    }

    public void setLives(final float lives) {
        this.lives = lives;
    }
}
