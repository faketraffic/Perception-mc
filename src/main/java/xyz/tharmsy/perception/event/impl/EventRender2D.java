package xyz.tharmsy.perception.event.impl;

import xyz.tharmsy.perception.event.Event;
import net.minecraft.client.gui.ScaledResolution;

public class EventRender2D extends Event {
    private final ScaledResolution sr;
    private final float partialTicks;

    public EventRender2D(ScaledResolution sr, float partialTicks) {
        this.sr = sr;
        this.partialTicks = partialTicks;
    }

    public ScaledResolution getSr() {
        return sr;
    }

    public float getPartialTicks() {
        return partialTicks;
    }
}
