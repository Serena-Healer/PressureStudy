package scellena.pressurestudy.data;

import scellena.pressurestudy.system.MainSystem;
import scellena.pressurestudy.system.Utilities;

import java.util.ArrayList;
import java.util.List;


public class QuizInformationBase {

    String filePath;
    String title;
    int timer;
    int repeat;
    List<SingleQuizInformation> quizList;
    int waCountAllowed = 0;
    int acCountRequired = 10;
    String layout;
    String layoutThumbnail;
    int startId = 0;
    boolean noAutoPass = false;

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

    public int getAcCountRequired() {
        return acCountRequired;
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

    public boolean isNoAutoPass() {
        return noAutoPass;
    }

    public void load(List<String> list){
        quizList = new ArrayList<>();
        mines = new ArrayList<>();
        for(String str : list){
            if(str.startsWith("#")) continue;
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
            }else if(str.startsWith("ac=")){
                acCountRequired = Integer.parseInt(str.substring(3));
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
            }else if(str.startsWith("noautopass=true")){
                noAutoPass = true;
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

    public List<String> getAdminString(){
        List<String> ans = new ArrayList<>();
        ans.add(getCategory().replace("\n", " "));
        if(mines.size() > 0){
            ans.add("ドボン一覧");
            for(int i : mines){
                SingleQuizInformation q = getQuizList().get(i);
                StringBuilder builder = new StringBuilder();
                builder.append(Utilities.getNumberString(i));
                builder.append(q.getTitle());
                ans.add(builder.toString());
            }
        }else{
            for(int i=0; i<getQuizList().size(); i++){
                SingleQuizInformation q = getQuizList().get(i);
                StringBuilder builder = new StringBuilder();
                builder.append(Utilities.getNumberString(i));
                builder.append(q.getTitle()).append(" -> ").append(q.getAnswer());
                ans.add(builder.toString());
            }
        }
        return ans;
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
        String musicPath;
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

        public String getMusicPath() {
            if(musicPath == null || musicPath.length() <= 1)return "";
            return parent.getFilePath() + "\\" + musicPath;
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
            choice = new ArrayList<>();

            for(int i=0; i<args.length; i++){
                String text = args[i];
                if(text.startsWith("q=")){
                    title = text.substring(2);
                }else if(text.startsWith("a=")){
                    answer = text.substring(2);
                }else if(text.startsWith("h=")){
                    hint = text.substring(2);
                }else if(text.startsWith("i=")){
                    imagePath = text.substring(2);
                }else if(text.startsWith("m=")){
                    musicPath = text.substring(2);
                }else if(text.startsWith("c=")){
                    choice.add(text.substring(2));
                }else if(i == 0){
                    title = text;
                }else if(i == 1){
                    answer = text;
                }else if(i == 2){
                    hint = text;
                }else if(i == 3){
                    imagePath = text;
                }else{
                    choice.add(text);
                }
            }
            this.parent = parent;
        }
    }

}
