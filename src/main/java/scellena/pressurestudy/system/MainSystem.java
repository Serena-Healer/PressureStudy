package scellena.pressurestudy.system;

import org.bukkit.*;
import org.bukkit.entity.*;
import scellena.pressurestudy.Main;
import scellena.pressurestudy.data.AnswererInformation;
import scellena.pressurestudy.data.GameDataHandler;
import scellena.pressurestudy.data.PlayerDataHandler;
import scellena.pressurestudy.enums.EnumScene;
import scellena.pressurestudy.events.EventProcesser;
import scellena.pressurestudy.jframe.FrameHandler;
import scellena.pressurestudy.jframe.clips.ClipPlayerHandler;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class MainSystem {

    public static Map<String, PlayerDataHandler> playerData = new HashMap<>();
    public static Set<String> gamePlayers = new HashSet<>();
    public static GameDataHandler gameData = new GameDataHandler();
    public static Map<String, AnswererInformation> answerer = new HashMap<>();

    public static void onTick(){
        try{
            Bukkit.getScheduler().runTaskLater(Main.plugin, new Runnable() {
                @Override
                public void run() {
                    onTick();
                }
            }, 1);

            gameData.frames++;

            Bukkit.getOnlinePlayers().forEach(p -> {
                getPlayerData(p).score.updateBoard(p);
            });

            gameData.waDisplayTick--;
            gameData.resultDisplayTick--;
            gameData.finePlayDisplayTick--;

            gameData.demoTick--;
            if(gameData.demoTick == 0){
                gameData.selectedNumber++;
                if(gameData.selectedNumber == MainSystem.gameData.quiz.getQuizList().size()){
                    gameData.selectedNumber = -1;
                }else{
                    gameData.demoTick = 20 * 45 / MainSystem.gameData.quiz.getQuizList().size();
                    ClipPlayerHandler.play("問題が切り替わる音.wav");
                }
            }

            if(gameData.selectedNumber >= 0 && gameData.selectedNumber <= 9 && !gameData.sizePaused){
                gameData.sizeElapsed[gameData.selectedNumber]++;
            }

            TimerHelper.onTick();
            FrameHandler.onTick();

            EventProcesser.clean();
        }catch(Exception e){
            Utilities.error(e);
        }
    }

    public static void onStart(){
        MainSystem.gameData.scene = EnumScene.IN_GAME;
    }

    public static void reset(){
        gameData.currentId = 0;
        gameData.selectedNumber = -1;
        for(int i=0; i<256; i++){
            gameData.sizeElapsed[i] = 0;
            gameData.answered[i] = false;
            gameData.failed[i] = false;
        }
        MainSystem.gameData.demoTick = -1;
    }

    public static void onEnd(){
        MainSystem.gameData.scene = EnumScene.LOBBY;
    }

    public static PlayerDataHandler getPlayerData(Player player){
        if(player == null)return null;
        if(!playerData.containsKey(player.getName())) {
            playerData.put(player.getName(), new PlayerDataHandler(player));
        }
        PlayerDataHandler data = playerData.get(player.getName());
        data.owner = player;
        return data;
    }

    public static PlayerDataHandler getPlayerData(String name){
        /*
        if(!playerData.containsKey(name)){
            playerData.put(name, new PlayerDataHandler(Bukkit.getPlayer(name)));
        }
        return playerData.get(name);
        */
        AtomicReference<Player> ans = new AtomicReference<>();
        Bukkit.getOnlinePlayers().forEach(e -> {
            if(e.getName().equals(name)){
                ans.set(e);
            }
        });
        return getPlayerData(ans.get());
    }

    public static Map<Player, PlayerDataHandler> getAllPlayers(){
        Map<Player, PlayerDataHandler> ret = new HashMap<>();
        if(gameData.scene != EnumScene.LOBBY) {
            gamePlayers.forEach(p -> {
                Player player = Bukkit.getPlayer(p);
                if(player != null) {
                    ret.put(player, getPlayerData(p));
                }
            });
        }else{
            Bukkit.getOnlinePlayers().forEach(p -> {
                if(p.getGameMode() != GameMode.CREATIVE) {
                    ret.put(p, getPlayerData(p));
                }
            });
        }
        return ret;
    }

    public static Map<Player, PlayerDataHandler> getAllServerMembers(){
        Map<Player, PlayerDataHandler> ret = new HashMap<>();
        Bukkit.getOnlinePlayers().forEach(p -> {
            ret.put(p, getPlayerData(p));
        });
        return ret;
    }

    public static AnswererInformation getAnswerer(String player){
        answerer.putIfAbsent(player, new AnswererInformation());
        return answerer.get(player);
    }

    public static void exportStats(){
        for(Map.Entry<String, AnswererInformation> entry : answerer.entrySet()){
            StringBuilder ans = new StringBuilder();
            ans.append(entry.getKey());
            ans.append(" ");
            ans.append("正解:").append(entry.getValue().ac).append(" ");
            ans.append("Fine Play:").append(entry.getValue().finePlay).append(" ");
            ans.append("超Fine Play:").append(entry.getValue().superFinePlay).append(" ");
            System.out.println(ans.toString());
        }
    }

}
