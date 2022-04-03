package eu.flowtex.magicgames.timer;

import eu.flowtex.magicgames.Main;
import eu.flowtex.magicgames.listeners.EntityDamageListener;
import eu.flowtex.magicgames.manager.TeamManager;
import eu.flowtex.magicgames.manager.TimerManager;
import eu.flowtex.magicgames.utils.Chat;
import eu.flowtex.magicgames.utils.GameState;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.effect.sound.SoundType;
import org.spongepowered.api.effect.sound.SoundTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class StartTimer implements Runnable {
    private Main main;
    private int timeUntilStart;
    private TeamManager tm;

    public StartTimer(final Main main) {
        this.timeUntilStart = 60;
        this.main = main;
        this.tm = TeamManager.getInstance();
    }

    @Override
    public void run() {
        if (Main.getInstance().isState(GameState.PREPARATION)) {
            if (this.timeUntilStart == 60) {
                Chat.broadcastMessage(Text.of(new Object[]{TextColors.RED, "MagicGames VI", TextColors.GRAY, " startet in", TextColors.RED, " 60 Sekunden", TextColors.GRAY, "!"}));
                for (final Player p : Sponge.getGame().getServer().getOnlinePlayers()) {
                    Sponge.getCommandManager().process(p, "gamerule sendCommandFeedback false");
                    Sponge.getCommandManager().process(p, "title @a subtitle {\"text\":\"Server Host\",\"color\":\"gray\"}");
                    Sponge.getCommandManager().process(p, "title @a title [\"\",{\"text\":\"!!\",\"obfuscated\":true,\"color\":\"red\"},{\"text\":\" Flowtex.eu \",\"color\":\"gray\"},{\"text\":\"!!\",\"obfuscated\":true,\"color\":\"red\"}]");
                }
                this.playSound(SoundTypes.ORB_PICKUP);
            } else if (this.timeUntilStart == 50) {
                Chat.broadcastMessage(Text.of(new Object[]{TextColors.RED, "MagicGames VI", TextColors.GRAY, " startet in", TextColors.RED, " 50 Sekunden", TextColors.GRAY, "!"}));
                this.playSound(SoundTypes.ORB_PICKUP);
            } else if (this.timeUntilStart == 40) {
                Chat.broadcastMessage(Text.of(new Object[]{TextColors.RED, "MagicGames VI", TextColors.GRAY, " startet in", TextColors.RED, " 40 Sekunden", TextColors.GRAY, "!"}));
                for (final Player p : Sponge.getGame().getServer().getOnlinePlayers()) {
                    Sponge.getCommandManager().process(p, "title @a subtitle {\"text\":\"Organisation\",\"color\":\"gray\"}");
                    Sponge.getCommandManager().process(p, "title @a title [\"\",{\"text\":\"!!\",\"obfuscated\":true,\"color\":\"red\"},{\"text\":\" Millert \",\"color\":\"gray\"},{\"text\":\"!!\",\"obfuscated\":true,\"color\":\"red\"}]");
                }
                this.playSound(SoundTypes.ORB_PICKUP);
            } else if (this.timeUntilStart == 30) {
                Chat.broadcastMessage(Text.of(new Object[]{TextColors.RED, "MagicGames VI", TextColors.GRAY, " startet in", TextColors.RED, " 30 Sekunden", TextColors.GRAY, "!"}));
                this.playSound(SoundTypes.ORB_PICKUP);
            } else if (this.timeUntilStart == 20) {
                Chat.broadcastMessage(Text.of(new Object[]{TextColors.RED, "MagicGames VI", TextColors.GRAY, " startet in", TextColors.RED, " 20 Sekunden", TextColors.GRAY, "!"}));
                for (final Player p : Sponge.getGame().getServer().getOnlinePlayers()) {
                    Sponge.getCommandManager().process(p, "title @a title [\"\",{\"text\":\"!!\",\"obfuscated\":true,\"color\":\"red\"},{\"text\":\" MagicGames VI \",\"color\":\"gray\"},{\"text\":\"!!\",\"obfuscated\":true,\"color\":\"red\"}]");
                    Sponge.getCommandManager().process(p, "title @a subtitle \"\"");
                }
                this.playSound(SoundTypes.ORB_PICKUP);
            } else if (this.timeUntilStart == 10) {
                Chat.broadcastMessage(Text.of(new Object[]{TextColors.RED, "MagicGames VI", TextColors.GRAY, " startet in", TextColors.RED, " 10 Sekunden", TextColors.GRAY, "!"}));
                this.playSound(SoundTypes.ORB_PICKUP);
            } else if (this.timeUntilStart == 5) {
                Chat.broadcastMessage(Text.of(new Object[]{TextColors.RED, "MagicGames VI", TextColors.GRAY, " startet in", TextColors.RED, " 5 Sekunden", TextColors.GRAY, "!"}));
                for (final Player p : Sponge.getGame().getServer().getOnlinePlayers()) {
                    Sponge.getCommandManager().process(p, "title @a title {\"text\":\"5\",\"color\":\"red\"}");
                }
                this.playSound(SoundTypes.NOTE_BASS);
            } else if (this.timeUntilStart == 4) {
                Chat.broadcastMessage(Text.of(new Object[]{TextColors.RED, "MagicGames VI", TextColors.GRAY, " startet in", TextColors.RED, " 4 Sekunden", TextColors.GRAY, "!"}));
                for (final Player p : Sponge.getGame().getServer().getOnlinePlayers()) {
                    Sponge.getCommandManager().process(p, "title @a title {\"text\":\"4\",\"color\":\"red\"}");
                }
                this.playSound(SoundTypes.NOTE_BASS);
            } else if (this.timeUntilStart == 3) {
                Chat.broadcastMessage(Text.of(new Object[]{TextColors.RED, "MagicGames VI", TextColors.GRAY, " startet in", TextColors.RED, " 3 Sekunden", TextColors.GRAY, "!"}));
                for (final Player p : Sponge.getGame().getServer().getOnlinePlayers()) {
                    Sponge.getCommandManager().process(p, "title @a title {\"text\":\"3\",\"color\":\"red\"}");
                }
                this.playSound(SoundTypes.NOTE_BASS);
            } else if (this.timeUntilStart == 2) {
                Chat.broadcastMessage(Text.of(new Object[]{TextColors.RED, "MagicGames VI", TextColors.GRAY, " startet in", TextColors.RED, " 2 Sekunden", TextColors.GRAY, "!"}));
                for (final Player p : Sponge.getGame().getServer().getOnlinePlayers()) {
                    Sponge.getCommandManager().process(p, "title @a title {\"text\":\"2\",\"color\":\"red\"}");
                }
                this.playSound(SoundTypes.NOTE_BASS);
            } else if (this.timeUntilStart == 1) {
                Chat.broadcastMessage(Text.of(new Object[]{TextColors.RED, "MagicGames VI", TextColors.GRAY, " startet in", TextColors.RED, " 1 Sekunden", TextColors.GRAY, "!"}));
                for (final Player p : Sponge.getGame().getServer().getOnlinePlayers()) {
                    Sponge.getCommandManager().process(p, "title @a title {\"text\":\"1\",\"color\":\"red\"}");
                }
                this.playSound(SoundTypes.NOTE_BASS);
            } else if (this.timeUntilStart == 0) {
                Chat.broadcastMessage(Text.of(new Object[]{TextColors.GREEN, "Mögen die Spiele beginnen!"}));
                for (final Player p : Sponge.getGame().getServer().getOnlinePlayers()) {
                    Sponge.getCommandManager().process(p, "title @a title {\"text\":\"0\",\"color\":\"red\"}");
                    Sponge.getCommandManager().process(p, "title @a subtitle [\"\",{\"text\":\"!!\",\"obfuscated\":true,\"color\":\"red\"},{\"text\":\" Viel Spaß! \",\"color\":\"gray\"},{\"text\":\"!!\",\"obfuscated\":true,\"color\":\"red\"}]");
                    Sponge.getCommandManager().process(p, "gamerule sendCommandFeedback true");
                }
                this.playSound(SoundTypes.LEVEL_UP);
                for (final Player p : Sponge.getGame().getServer().getOnlinePlayers()) {
                    if (this.tm.isSpectator((User) p)) {
                        p.getInventory().clear();
                        p.offer(Keys.GAME_MODE, GameModes.SPECTATOR);
                        p.offer(Keys.FOOD_LEVEL, 20);
                        p.offer(Keys.HEALTH, 20.0);
                        p.offer(Keys.EXPERIENCE_LEVEL, 0);
                    } else if (this.tm.getPlayersTeam((User) p) != null) {
                        p.getInventory().clear();
                        p.offer(Keys.GAME_MODE, GameModes.SURVIVAL);
                        p.offer(Keys.FOOD_LEVEL, 20);
                        p.offer(Keys.HEALTH, 20.0);
                        p.offer(Keys.EXPERIENCE_LEVEL, 0);
                        p.getWorld().getProperties().setWorldTime(1000L);
                    } else {
                        p.kick(Text.of(new Object[]{TextColors.RED, "Du bist nicht registriert!"}));
                    }
                }
                Main.getInstance().setState(GameState.RUNNING);
                this.addTrackedPlayers(900);
            }
            --this.timeUntilStart;
        } else if (Main.getInstance().isState(eu.flowtex.magicgames.utils.GameState.RUNNING)) {
            TimerManager.getInstance().cancelStartTaskTimer();
        }
    }

    private void addTrackedPlayers(final Integer timeLeft) {
        final TeamManager tm2 = TeamManager.getInstance();
        for (final Player p : Sponge.getGame().getServer().getOnlinePlayers()) {
            final TimerManager tm3 = TimerManager.getInstance();
            if (!tm2.isSpectator((User) p)) {
                tm3.newTrackedPlayer(this.main, (User) p, timeLeft, -1);
            }
        }
    }

    private void playSound(final SoundType sound) {
        for (final Player p : Sponge.getGame().getServer().getOnlinePlayers()) {
            p.playSound(sound, p.getLocation().getPosition(), 1.0, 1.0);
        }
    }

    public static void startTimer(final User p) {
        Sponge.getScheduler().createTaskBuilder().delayTicks(100L).execute((Runnable) new Runnable() {
            @Override
            public void run() {
                if (EntityDamageListener.tracker.containsKey(p)) {
                    EntityDamageListener.tracker.put(p, null);
                }
            }
        }).submit((Object) Main.getInstance());
    }
}