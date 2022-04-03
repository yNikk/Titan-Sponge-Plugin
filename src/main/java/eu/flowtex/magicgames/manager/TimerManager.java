package eu.flowtex.magicgames.manager;

import eu.flowtex.magicgames.utils.Data;
import eu.flowtex.magicgames.Main;
import eu.flowtex.magicgames.timer.DailyTimer;
import eu.flowtex.magicgames.timer.StartTimer;
import eu.flowtex.magicgames.utils.TimerTuple;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.scheduler.Task;

import java.util.*;

public class TimerManager {
    private static TimerManager instance;
    private HashMap<UUID, TimerTuple> tracked;
    private Task dailyTimerTask;
    private Task startTimerTask;

    static {
        TimerManager.instance = null;
    }

    private TimerManager() {
        this.tracked = new HashMap<UUID, TimerTuple>();
    }

    public static TimerManager getInstance() {
        if (TimerManager.instance == null) {
            TimerManager.instance = new TimerManager();
        }
        return TimerManager.instance;
    }

    public void loadTracked(final Main main) {
        final FileManager fm = FileManager.getInstance();
        final List<String> uuids = fm.getStringList(202, "tracked_uuids");
        for (final String uuidString : uuids) {
            final int timeLeft = (int) fm.get(202, String.valueOf(uuidString) + ".time_left");
            final int invincLeft = (int) fm.get(202, String.valueOf(uuidString) + ".invincibility_left");
            final UUID uuid = UUID.fromString(uuidString);
            final TimerTuple tt = new TimerTuple(main, Data.getPlayerFromUUid(uuid), timeLeft, invincLeft);
            this.tracked.put(uuid, tt);
        }
    }

    public void saveTracked() {
        if (!this.tracked.isEmpty()) {
            final FileManager fm = FileManager.getInstance();
            final List<String> trackedUUIDs = new ArrayList<String>();
            for (final Map.Entry<UUID, TimerTuple> entry : this.tracked.entrySet()) {
                final UUID uuid = entry.getKey();
                trackedUUIDs.add(uuid.toString());
                final int timeLeft = entry.getValue().getTimeLeft();
                final int invincLeft = entry.getValue().getInvincibleLeft();
                fm.write(202, uuid + ".time_left", timeLeft);
                fm.write(202, uuid + ".invincibility_left", invincLeft);
            }
            fm.write(202, "tracked_uuids", trackedUUIDs);
        }
        System.out.println("Die TimerTuple wurden erfolgreich gespeichert!");
    }

    public void newTrackedPlayer(final Main main, final User p, final Integer timeLeft, final Integer invincibleTimeLeft) {
        final TimerTuple tt = new TimerTuple(main, p, timeLeft, invincibleTimeLeft);
        this.tracked.put(p.getUniqueId(), tt);
    }

    public boolean isTracked(final User p) {
        return this.tracked.containsKey(p.getUniqueId());
    }

    public TimerTuple getValueForPlayer(final User p) {
        return this.tracked.get(p.getUniqueId());
    }

    public void removeTrackedPlayer(final User p) {
        this.tracked.remove(p.getUniqueId());
    }

    public HashMap<UUID, TimerTuple> getTracked() {
        return this.tracked;
    }

    public void initiateDailyTimerTask(final Main main) {
        this.setDailyTimerTask(Sponge.getScheduler().createTaskBuilder().execute((Runnable) new DailyTimer(main)).intervalTicks(20L).submit((Object) main));
    }

    public void initiateStartTimerTask(final Main main) {
        this.setStartTimerTask(Sponge.getScheduler().createTaskBuilder().execute((Runnable) new StartTimer(main)).intervalTicks(20L).submit((Object) main));
    }

    private void setDailyTimerTask(final Task task) {
        this.dailyTimerTask = task;
    }

    private void setStartTimerTask(final Task task) {
        this.startTimerTask = task;
    }

    public void cancelStartTaskTimer() {
        this.startTimerTask.cancel();
        this.setStartTimerTask(null);
    }

    public void cancelDailyTimerTask() {
        this.dailyTimerTask.cancel();
        this.setDailyTimerTask(null);
    }
}
