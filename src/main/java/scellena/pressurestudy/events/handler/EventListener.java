package scellena.pressurestudy.events.handler;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import scellena.pressurestudy.data.PlayerDataHandler;
import scellena.pressurestudy.system.MainSystem;

public class EventListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        PlayerDataHandler data = MainSystem.getPlayerData(player);
        data.score.setOwner(player);
    }

}
