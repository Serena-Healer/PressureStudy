package scellena.pressurestudy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import scellena.pressurestudy.data.AnswererInformation;
import scellena.pressurestudy.data.QuizInformationBase;
import scellena.pressurestudy.events.handler.EventListener;
import scellena.pressurestudy.jframe.FrameHandler;
import scellena.pressurestudy.jframe.RenderUtils;
import scellena.pressurestudy.jframe.clips.ClipPlayerHandler;
import scellena.pressurestudy.jframe.layout.LayoutManager;
import scellena.pressurestudy.jframe.layout.LayoutPlayers;
import scellena.pressurestudy.jframe.layout.study.LayoutTitle;
import scellena.pressurestudy.jframe.layout.thumbnail.LayoutList;
import scellena.pressurestudy.system.*;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static scellena.pressurestudy.system.MainSystem.gameData;

public final class Main extends JavaPlugin {

    public static Plugin plugin;
    public static String version = "1.3.1";

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        Bukkit.getServer().getPluginManager().registerEvents(new EventListener(), this);

        FrameHandler.create();
        MainSystem.onTick();
        LayoutManager.init();

        System.out.println("Plugin " + plugin.getName() + " started successfully!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("Plugin " + plugin.getName() + " stopped successfully!");
        MainSystem.exportStats();
        ClipPlayerHandler.stopBGM();
        FrameHandler.jFrame.setVisible(false);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equals("start")){
            MainSystem.gameData.bgmId = 0;
            if(args.length == 0) {
                TimerHelper.chargeAndStart(MainSystem.gameData.quiz.getTimer() * 20);
            }else if(args[0].equals("instant")){
                TimerHelper.startWithoutCharge(MainSystem.gameData.quiz.getTimer() * 20);
            }else if(args.length >= 2){
                TimerHelper.startWithoutCharge(Integer.parseInt(args[0]) * 20);
                MainSystem.gameData.bgmId = 1;
            }else{
                TimerHelper.chargeAndStart(Integer.parseInt(args[0]) * 20);
            }
            gameData.repeat = MainSystem.gameData.quiz.getRepeat();
            MainSystem.onStart();

            if(MainSystem.gameData.quiz.getLayout() != null){
                try {
                    MainSystem.gameData.currentLayout = LayoutManager.layouts.get(MainSystem.gameData.quiz.getLayout()).newInstance();
                }catch(Exception e){
                }
            }

            if(MainSystem.gameData.quiz.getWaCountOption() == -1){
                RenderUtils.showTelop("リーダー以外は1回まで間違えてもOK");
            }else if(MainSystem.gameData.quiz.getWaCountOption() == 1){
                RenderUtils.showTelop("一度でも間違えたら即終了");
            }else if(MainSystem.gameData.quiz.getWaCountOption() > 0){
                RenderUtils.showTelop("1人が" + MainSystem.gameData.quiz.getWaCountOption() + "回間違えたら即終了");
            }

            if(args.length >= 2){
                MainSystem.gameData.relayHint = args[1];
                MainSystem.gameData.currentId = 9;
            }else{
                MainSystem.gameData.relayHint = "";
            }
            return true;
        }
        if(command.getName().equals("thumbnail")){
            ClipPlayerHandler.playBGM("Qさま問題一覧(1代目).wav", false);
            if(MainSystem.gameData.quiz.getLayoutThumbnail() != null){
                try {
                    MainSystem.gameData.currentLayout = LayoutManager.layouts.get(MainSystem.gameData.quiz.getLayoutThumbnail()).newInstance();
                }catch(Exception e){
                }
            }
            return true;
        }
        if(command.getName().equals("extend")){
            if(args.length == 0)return false;
            TimerHelper.extend(Integer.parseInt(args[0]) * 20);
            return true;
        }
        if(command.getName().equals("layout")){
            if(args.length == 0)return false;
            try {
                MainSystem.gameData.currentLayout = LayoutManager.layouts.get(args[0]).newInstance();
            }catch(Exception e){
                return false;
            }
            return true;
        }
        if(command.getName().equals("cd")){
            MainSystem.gameData.cd = Utilities.getPathFromArgs(args) + "\\";
            System.out.println("cd " + MainSystem.gameData.cd + " executed!");
            return true;
        }
        if(command.getName().equals("asset")){
            MainSystem.gameData.assetPath = Utilities.getPathFromArgs(args) + "\\";
            System.out.println("asset " + MainSystem.gameData.assetPath + " executed!");
            return true;
        }
        if(command.getName().equals("loadplayers")){
            List<String> list = TextFileLoader.load(args[0]);
            MainSystem.gameData.answerBox = new ArrayList<>();
            java.awt.Color defaultColor = java.awt.Color.BLUE;
            for(String str : list){
                if(str.startsWith("name=")){
                    MainSystem.gameData.teamName = str.substring(5);
                }else if(str.startsWith("color=")) {
                    if(str.contains("blue")){
                        defaultColor = new Color(0, 64, 224);
                    }else if(str.contains("yellow")){
                        defaultColor = new Color(224, 128, 0);
                    }else{
                        defaultColor = new Color(224, 0, 0);
                    }
                }else{
                    AnswererInformation info = MainSystem.getAnswerer(str.split(",")[0]);
                    info.setColor(defaultColor);
                    info.loadInformation(str);
                    MainSystem.gameData.answerBox.add(info);
                }
            }
            if(!(MainSystem.gameData.currentLayout instanceof LayoutPlayers)) {
                MainSystem.gameData.currentLayout = new LayoutPlayers();
                ClipPlayerHandler.playBGM("Qさま!!DS メニューBGM.wav");
            }
            return true;
        }
        if(command.getName().equals("loadquiz")){
            MainSystem.reset();
            List<String> list = TextFileLoader.load(args[0]);
            MainSystem.gameData.quiz = new QuizInformationBase();
            MainSystem.gameData.quiz.load(list);
            MainSystem.gameData.quiz.setup();
            MainSystem.gameData.quiz.setFilePath(new File(MainSystem.gameData.cd + args[0]).getParent());
            ClipPlayerHandler.stopBGM();
            ClipPlayerHandler.playBGM("Qさまドラムロール.wav", false);
            MainSystem.gameData.currentLayout = new LayoutTitle();
            return true;
        }
        if(command.getName().equals("save")){
            TextFileLoader.save("answer.txt", gameData.quiz.getAdminString());
             return true;
        }
        if(command.getName().equals("ac")){
            if(args.length > 0) {
                QuizSystem.select(Integer.parseInt(args[0]) - 1);
            }
            if(MainSystem.gameData.selectedNumber >= 0) {
                QuizSystem.ac(MainSystem.gameData.selectedNumber);
            }
            return true;
        }
        if(command.getName().equals("wa")){
            if(args.length > 0) {
                QuizSystem.select(Integer.parseInt(args[0]) - 1);
            }
            if(MainSystem.gameData.selectedNumber >= 0) {
                QuizSystem.wa(MainSystem.gameData.selectedNumber);
            }
            return true;
        }
        if(command.getName().equals("select")){
            if(args.length > 0) {
                QuizSystem.select(Integer.parseInt(args[0]) - 1);
                return true;
            }
        }
        if(command.getName().equals("suspend")){
            MainSystem.onEnd();
            ClipPlayerHandler.stopBGM();
        }
        if(command.getName().equals("demo")){
            if(MainSystem.gameData.quiz.getLayout() != null){
                try {
                    MainSystem.gameData.currentLayout = new LayoutList();
                }catch(Exception e){
                }
            }
            ClipPlayerHandler.playBGM("Qさま問題出すとき(1代目).wav", false);
            MainSystem.gameData.demoTick = 1;
            MainSystem.gameData.selectedNumber = -1;
            return true;
        }
        if(command.getName().equals("garbage")){
            RenderUtils.clearCache();
            Utilities.sendMessageToAll(ChatColor.RED + "画像キャッシュをクリアしました！");
            return true;
        }
        if(command.getName().equals("judge")){
            if(args.length > 0) {
                QuizSystem.select(Integer.parseInt(args[0]) - 1);
            }
            if(MainSystem.gameData.selectedNumber >= 0) {
                if(MainSystem.gameData.quiz.getMines().contains(MainSystem.gameData.selectedNumber)){
                    QuizSystem.wa(MainSystem.gameData.selectedNumber);
                }else {
                    QuizSystem.ac(MainSystem.gameData.selectedNumber);
                }
            }
            return true;
        }
        if(command.getName().equals("pass")){
            MainSystem.gameData.currentId++;
            return true;
        }
        if(command.getName().equals("qpass")){
            QuizSystem.select(gameData.selectedNumber + 1);
            return true;
        }
        if(command.getName().equals("rs_movie")){
            MainSystem.gameData.sizeElapsed[MainSystem.gameData.selectedNumber] = 2;
            return true;
        }
        if(command.getName().equals("sizepause")){
            gameData.sizePaused = !gameData.sizePaused;
            return true;
        }
        if(command.getName().equals("pause")){
            gameData.paused = !gameData.paused;
            return true;
        }
        if(command.getName().equals("export")){
            MainSystem.exportStats();
            return true;
        }
        if(command.getName().equals("telop")){
            StringBuilder builder = new StringBuilder();
            for(String str : args){
                if(builder.length() > 0) builder.append(" ");
                builder.append(str);
            }
            RenderUtils.showTelop(builder.toString());
            return true;
        }
        if(command.getName().equals("telop_list")){
            StringBuilder builder = new StringBuilder();
            for(String str : args){
                if(builder.length() > 0) builder.append(" ");
                builder.append(str);
            }
            gameData.programTelop = builder.toString();
            return true;
        }
        return super.onCommand(sender, command, label, args);
    }
}
