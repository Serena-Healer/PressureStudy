package scellena.pressurestudy.data;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import scellena.pressurestudy.Main;
import scellena.pressurestudy.system.MainSystem;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ScoreboardHandler {

    Player owner;
    Scoreboard board;
    int updateTick = 0;
    Objective obj;
    List<String> linesEntry = new ArrayList<>();
    List<Team> linesTeam = new ArrayList<>();

    public ScoreboardHandler(Player owner){
        this.owner = owner;
        board = owner.getServer().getScoreboardManager().getNewScoreboard();

        obj = board.registerNewObjective("display", "dummy", ChatColor.WHITE + Main.plugin.getName() + ChatColor.DARK_GRAY +" Ver." + Main.version);
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        for(ChatColor color : ChatColor.values()){
            String entry = color.toString();
            Team team = board.registerNewTeam("score_" + linesEntry.size());
            linesEntry.add(entry);
            linesTeam.add(team);
            team.addEntry(entry);
        }

        owner.setScoreboard(board);
    }

    public void updateBoard(Player owner){
        updateTick++;
        if(updateTick % 20 == 0) {
            this.owner = owner;
            PlayerDataHandler data = MainSystem.getPlayerData(owner);

            List<String> entries = new ArrayList<>();

            entries.add("");

            if(MainSystem.gameData.charge){
                entries.add(ChatColor.YELLOW + " 解答時間Charge中");
            }else{
                int seconds = MainSystem.gameData.timeLeft / 20;
                entries.add(" 残り時間: " + ChatColor.GREEN + seconds + "s");
            }
            entries.add("");

            entries.add(" 残り問題");
            int size = 10;
            if(MainSystem.gameData.quiz != null){
                size = MainSystem.gameData.quiz.getQuizList().size();
            }
            StringBuilder builder = new StringBuilder();
            for(int i=0; i<size; i++){
                builder.append(" ");
                if(MainSystem.gameData.answered[i]){
                    builder.append(ChatColor.DARK_GRAY);
                }else if(MainSystem.gameData.failed[i]){
                    builder.append(ChatColor.AQUA);
                }else if(MainSystem.gameData.selectedNumber == i){
                    builder.append(ChatColor.GOLD);
                }else{
                    builder.append(ChatColor.WHITE);
                }
                builder.append(i + 1);
                if(i % 5 == 4 || i == size - 1){
                    entries.add(builder.toString());
                    builder = new StringBuilder();
                }
            }

            int n = MainSystem.gameData.currentId;
            if(MainSystem.gameData.answerBox.size() > n) {
                AnswererInformation info = MainSystem.gameData.answerBox.get(n);
                entries.add("");
                entries.add(" 解答者: " + ChatColor.GREEN + (n + 1) + ". " + info.getName());
            }

            entries.add("");

            List<String> finalEntries = entries;

            for(int i=0; i<15; i++){
                if(i >= finalEntries.size()){
                    board.resetScores(linesEntry.get(i));
                }else{
                    linesTeam.get(i).setPrefix(finalEntries.get(i));
                    obj.getScore(linesEntry.get(i)).setScore(Math.min(finalEntries.size(), 15) - i);
                }
            }

        }
    }

    public void unregister(){
        Objective obj = board.getObjective("display");
        if(obj != null){
            obj.unregister();
        }
    }

    public void setOwner(Player player){
        owner = player;
        player.setScoreboard(board);
    }

}
