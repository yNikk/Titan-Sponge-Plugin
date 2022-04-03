package eu.flowtex.magicgames.timer;

import eu.flowtex.magicgames.utils.Data;
import eu.flowtex.magicgames.Main;
import eu.flowtex.magicgames.manager.FileManager;
import eu.flowtex.magicgames.manager.TeamManager;
import eu.flowtex.magicgames.manager.TimerManager;
import eu.flowtex.magicgames.utils.Chat;
import eu.flowtex.magicgames.utils.GameState;
import eu.flowtex.magicgames.utils.Team;
import eu.flowtex.magicgames.utils.TimerTuple;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import java.util.ArrayList;
import java.util.List;

public class DailyTimer implements Runnable {
    private final int noKickRange;
    private final int extraTime;
    private Main main;
    private ArrayList<Player> toKick;

    public DailyTimer(final Main main) {
        this.toKick = new ArrayList<Player>();
        this.main = main;
        this.extraTime = (int) FileManager.getInstance().get(205, "combat.extra_time");
        this.noKickRange = (int) FileManager.getInstance().get(205, "combat.no_kick_range");
    }

    @Override
    public void run() {
        if (this.main.isState(GameState.RUNNING)) {
            final TimerManager tm = TimerManager.getInstance();
            if (!tm.getTracked().values().isEmpty()) {
                for (final TimerTuple tt : tm.getTracked().values()) {
                    final User user = tt.getPlayer();
                    if (user.isOnline()) {
                        final Player p = user.getPlayer().get();
                        Integer invincibleLeft = tt.getInvincibleLeft();
                        Integer timeLeft = tt.getTimeLeft();
                        if (invincibleLeft >= 0) {
                            if (invincibleLeft == 0) {
                                Chat.broadcastMessage(Text.of(new Object[]{Chat.prefix, TextColors.AQUA, "Der Spieler ", TextColors.YELLOW, TextStyles.ITALIC, p.getName(), TextColors.AQUA, TextStyles.RESET, " ist nun verwundbar!"}));
                            } else if (invincibleLeft == 60 || invincibleLeft == 30 || invincibleLeft == 10 || (invincibleLeft <= 5 && invincibleLeft != 4)) {
                                Chat.broadcastMessage(Text.of(new Object[]{Chat.prefix, TextColors.AQUA, "Der Spieler ", TextColors.YELLOW, TextStyles.ITALIC, p.getName(), TextColors.AQUA, TextStyles.RESET, " ist in ", TextColors.YELLOW, TextStyles.ITALIC, invincibleLeft, " Sekunden", TextColors.AQUA, TextStyles.RESET, " verwundbar!"}));
                            }
                            --invincibleLeft;
                            tt.setInvincibleLeft(invincibleLeft);
                        }
                        if (timeLeft < this.extraTime) {
                            final List<Entity> nearbyEntities = Data.getNearbyEntities(p, this.noKickRange);
                            if (!nearbyEntities.isEmpty()) {
                                boolean foundEnemy = false;
                                for (final Entity entity : nearbyEntities) {
                                    if (entity instanceof Player) {
                                        final Player pNearby = (Player) entity;
                                        final Team team = TeamManager.getInstance().getPlayersTeam((User) p);
                                        if (!team.hasPlayer((User) pNearby) && !TeamManager.getInstance().isSpectator((User) pNearby)) {
                                            timeLeft = this.extraTime;
                                            tt.setTimeLeft(timeLeft);
                                            Chat.send(p, "Du wirst nicht gekickt, weil ein Gegner in der N\u00e4he ist!", Chat.INFO);
                                            foundEnemy = true;
                                            break;
                                        }
                                        continue;
                                    }
                                }
                                if (foundEnemy) {
                                    break;
                                }
                            }
                        }
                        if (timeLeft == 60 || timeLeft == 30 || timeLeft == 5 || timeLeft == 10 || timeLeft == 15) {
                            Chat.broadcastMessage(Text.of(new Object[]{TextColors.AQUA, "Der Spieler ", TextColors.YELLOW, TextStyles.ITALIC, p.getName(), TextColors.AQUA, TextStyles.RESET, " wird in ", TextStyles.ITALIC, timeLeft, " Sekunden ", TextColors.AQUA, TextStyles.RESET, "gekickt!"}));
                        } else if (timeLeft == 0) {
                            Chat.broadcastMessage(Text.of(new Object[]{TextColors.AQUA, "Der Spieler ", TextColors.YELLOW, TextStyles.ITALIC, p.getName(), TextColors.AQUA, TextStyles.RESET, " wird nun gekickt!"}));
                            p.kick(Text.of(new Object[]{TextColors.RED, "Deine Aufnahmezeit ist aufgebraucht!"}));
                            this.toKick.add(p);
                        }
                        --timeLeft;
                        tt.setTimeLeft(timeLeft);
                    }
                }
                for (final Player p2 : this.toKick) {
                    TimerManager.getInstance().removeTrackedPlayer((User) p2);
                }
                this.toKick.clear();
            }
        }
    }
}
