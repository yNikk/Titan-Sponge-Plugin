package eu.flowtex.magicgames.utils;

import eu.flowtex.magicgames.Main;
import org.spongepowered.api.entity.living.player.User;

public class TimerTuple {
    private User p;
    private Integer timeleft;
    private Integer invincibleTimeLeft;

    public TimerTuple(final Main main, final User p, final Integer timeleft, final Integer invincibleTime) {
        this.p = p;
        this.timeleft = timeleft;
        this.invincibleTimeLeft = invincibleTime;
    }

    public User getPlayer() {
        return this.p;
    }

    public Integer getTimeLeft() {
        return this.timeleft;
    }

    public void setTimeLeft(final Integer timeLeft) {
        this.timeleft = timeLeft;
    }

    public boolean isInvincible() {
        return this.invincibleTimeLeft >= 0;
    }

    public Integer getInvincibleLeft() {
        return this.invincibleTimeLeft;
    }

    public void setInvincibleLeft(final Integer invincLeft) {
        this.invincibleTimeLeft = invincLeft;
    }
}
