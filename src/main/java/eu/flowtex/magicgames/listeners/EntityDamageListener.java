package eu.flowtex.magicgames.listeners;

import eu.flowtex.magicgames.timer.StartTimer;
import eu.flowtex.magicgames.utils.Data;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.entity.damage.source.EntityDamageSource;
import org.spongepowered.api.event.entity.DamageEntityEvent;

import java.util.HashMap;

public class EntityDamageListener {
    public static HashMap<User, User> tracker;

    static {
        EntityDamageListener.tracker = new HashMap<User, User>();
    }

    @Listener
    public void onEntityDamage(final DamageEntityEvent e) {
        Player attacker0 = null;
        Player attacked0 = null;
        if (e.getTargetEntity() instanceof Player) {
            final Player attacked = (Player) e.getTargetEntity();
            if (e.getCause().first((Class) EntityDamageSource.class).isPresent()) {
                final EntityDamageSource source = (EntityDamageSource) e.getCause().first((Class) EntityDamageSource.class).get();
                if (Data.getAttackerIfPlayer(source) != null) {
                    final Player attacker = Data.getAttackerIfPlayer(source);
                    if (!Data.inSameTeam(attacker, attacked)) {
                        EntityDamageListener.tracker.put((User) attacked, (User) attacker);
                        StartTimer.startTimer((User) attacked);
                    }
                }
            }
        }
        if (e.getCause().first((Class) EntityDamageSource.class).isPresent()) {
            final EntityDamageSource source = (EntityDamageSource) e.getCause().first((Class) EntityDamageSource.class).get();
            if (Data.getAttackerIfPlayer(source) != null && e.getTargetEntity() instanceof Player) {
                attacker0 = Data.getAttackerIfPlayer(source);
                if (!e.isCancelled()) {
                    e.setCancelled(AITaskListener.shouldCancel(attacker0));
                }
            }
        }
        if (e.getTargetEntity() instanceof Player) {
            attacked0 = (Player) e.getTargetEntity();
            if (!e.isCancelled()) {
                e.setCancelled(AITaskListener.shouldCancel(attacked0));
            }
        }
        if (attacker0 != null && attacked0 != null && Data.inSameTeam(attacker0, attacked0)) {
            e.setCancelled(true);
        }
    }

    public static User getLastDamager(final Player p) {
        return EntityDamageListener.tracker.get(p);
    }
}
