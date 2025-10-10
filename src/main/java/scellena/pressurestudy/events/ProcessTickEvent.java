package scellena.pressurestudy.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ProcessTickEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList(){
        return HANDLERS;
    }
}
