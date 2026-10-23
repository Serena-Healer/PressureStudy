package scellena.pressurestudy.jframe.layout;

import scellena.pressurestudy.jframe.layout.special.*;
import scellena.pressurestudy.jframe.layout.study.*;
import scellena.pressurestudy.jframe.layout.thumbnail.*;

import java.util.HashMap;
import java.util.Map;

public class LayoutManager {
    public static Map<String, Class<? extends LayoutBase>> layouts = new HashMap<>();

    public static void init(){
        register("title", LayoutTitle.class);
        register("japanese_thumbnail", LayoutJapaneseThumbnail.class);
        register("japanese_thumbnail2", LayoutJapaneseThumbnailLong.class);
        register("hidden_thumbnail", LayoutHiddenThumbnail.class);
        register("image_thumbnail", LayoutImageThumbnail.class);
        register("truefalse", LayoutTrueFalse.class);
        register("2choice", Layout2Choices.class);
        register("2choice_image", Layout2ChoicesImage.class);
        register("sort", LayoutSort.class);
        register("japanese", LayoutJapanese.class);
        register("minesweeper", LayoutMineSweeper.class);
        register("minesweeper_thumbnail", LayoutMineSweeperThumbnail.class);
        register("image", LayoutImage.class);
        register("image_full", LayoutImageFull.class);
        register("movie", LayoutMovie.class);
        register("speedmovie", LayoutSpeedMovie.class);
        register("title_thumbnail", LayoutTitleThumbnail.class);
        register("elimination", LayoutElimination.class);
        register("single_image", LayoutSingleImage.class);
        register("single_movie", LayoutSingleMovie.class);
        register("9hint", Layout9HintRelay.class);
        register("describe", LayoutDescribe.class);
        register("players", LayoutPlayers.class);
    }

    public static Class<? extends LayoutBase> register(String name, Class<? extends LayoutBase> layout){
        layouts.put(name, layout);
        return layout;
    }
}

