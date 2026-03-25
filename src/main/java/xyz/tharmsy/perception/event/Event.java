package xyz.tharmsy.perception.event;

public class Event {
    private boolean cancelled;

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public void call() {
        xyz.tharmsy.perception.Perception.getInstance().getEventManager().call(this);
    }
}
