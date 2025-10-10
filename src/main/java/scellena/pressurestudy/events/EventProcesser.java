package scellena.pressurestudy.events;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import scellena.pressurestudy.Main;

import java.util.ArrayList;
import java.util.List;

public class EventProcesser {

    static List<Listener> listeners = new ArrayList<>();
    static List<Listener> listenersGoingToBeKilled = new ArrayList<>();

    public static Listener addListener(Listener listener){
        if(!listeners.contains(listener)) {
            Bukkit.getPluginManager().registerEvents(listener, Main.plugin);
            listeners.add(listener);
        }
        listenersGoingToBeKilled.remove(listener);
        return listener;
    }

    /**
     * 毎ティック実行
     * そのティック内で追加されなかったリスナーの存在を抹消する
     * @return 生存者たち
     */
    public static List<Listener> clean(){
        listenersGoingToBeKilled.forEach(l -> {
            HandlerList.unregisterAll(l);
            listeners.remove(l);
        });
        listenersGoingToBeKilled = new ArrayList<>();
        listenersGoingToBeKilled.addAll(listeners);
        return listeners;
    }

}
