package scellena.pressurestudy.data;

import scellena.pressurestudy.system.MainSystem;

import java.util.ArrayList;
import java.util.List;


public class QuizInformationBase {

    String filePath;
    String title;
    int timer;
    int repeat;
    List<SingleQuizInformation> quizList;
    int waCountAllowed = 0;
    String layout;
    String layoutThumbnail;
    int startId = 0;

    String category;
    String bgmNormalName = "プレッシャーSTUDY フル(1代目).wav";
    String bgmAlternateName = "プレッシャーSTUDY フル(1代目).wav";
    String bgmLastName = "Qさま30秒前(1代目).wav";
    String bgmTimeUpName = "Qさま失敗.wav";

    List<Integer> mines;
    /**
     * 上級問題=true
     */
    boolean difficultyFlag = false;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getTitle() {
        return title;
    }

    public int getTimer() {
        return timer;
    }

    public int getRepeat() {
        return repeat;
    }

    public List<SingleQuizInformation> getQuizList() {
        return quizList;
    }

    public String getCategory() {
        return category;
    }

    public int getWaCountAllowed() {
        return waCountAllowed;
    }

    public String getBgmNormalName() {
        return bgmNormalName;
    }

    public String getBgmAlternateName() {
        return bgmAlternateName;
    }

    public String getBgmLastName() {
        return bgmLastName;
    }

    public String getBgmTimeUpName() {
        return bgmTimeUpName;
    }

    public String getLayoutThumbnail() {
        return layoutThumbnail;
    }

    public int getStartId() {
        return startId;
    }

    public void load(List<String> list){
        quizList = new ArrayList<>();
        mines = new ArrayList<>();
        for(String str : list){
            if(str.startsWith("title=")){
                title = str.substring(6);
            }else if(str.startsWith("timer=")){
                timer = Integer.parseInt(str.substring(6));
            }else if(str.startsWith("repeat=")){
                repeat = Integer.parseInt(str.substring(7));
            }else if(str.startsWith("category=")){
                category = str.substring(9);
            }else if(str.startsWith("wa=")){
                waCountAllowed = Integer.parseInt(str.substring(3));
            }else if(str.startsWith("layout=")){
                layout = str.substring(7);
            }else if(str.startsWith("layout_thumbnail=")){
                layoutThumbnail = str.substring(17);
            }else if(str.startsWith("bgm=")){
                bgmNormalName = str.substring(4);
            }else if(str.startsWith("bgm2=")){
                bgmAlternateName = str.substring(5);
            }else if(str.startsWith("bgmlast=")){
                bgmLastName = str.substring(8);
            }else if(str.startsWith("bgmtimeup=")){
                bgmTimeUpName = str.substring(10);
            }else if(str.startsWith("difficulty=true")){
                difficultyFlag = true;
            }else if(str.startsWith("startid=")){
                startId = Integer.parseInt(str.substring(8));
            }else if(str.startsWith("mine=")){
                String mine = str.substring(5);
                String[] s = mine.split(",");
                for(String t : s){
                    mines.add(Integer.parseInt(t));
                }
            }else {
                quizList.add(new SingleQuizInformation(this, str));
            }
        }
    }

    public List<Integer> getMines() {
        return mines;
    }

    public boolean isDifficultyFlag() {
        return difficultyFlag;
    }

    public String getLayout() {
        return layout;
    }

    public void setup(){
        MainSystem.gameData.timeLeft = 0;
        MainSystem.gameData.timeMax = timer * 20;
        MainSystem.gameData.currentId = startId;
    }


    public static class SingleQuizInformation{
        QuizInformationBase parent;
        String title;
        String imagePath;
        String hint;
        String answer;
        List<String> choice = new ArrayList<>();

        public String getTitle() {
            return title;
        }

        public String getImagePath() {
            if(imagePath == null || imagePath.length() <= 1)return "";
            return parent.getFilePath() + "\\" + imagePath;
        }

        public String getHint() {
            return hint;
        }

        public String getAnswer() {
            return answer;
        }

        public List<String> getChoice() {
            return choice;
        }

        public SingleQuizInformation(QuizInformationBase parent, String s){
            String[] args = s.split(",");
            title = args[0];
            choice = new ArrayList<>();
            if(args.length > 1) answer = args[1];
            if(args.length > 2) hint = args[2];
            if(args.length > 3) imagePath = args[3];
            if(args.length > 4){
                for(int i=4; i<args.length; i++){
                    choice.add(args[i]);
                }
            }
            this.parent = parent;
        }
    }

}
