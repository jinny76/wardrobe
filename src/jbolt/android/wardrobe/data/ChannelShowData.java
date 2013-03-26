package jbolt.android.wardrobe.data;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import jbolt.android.wardrobe.models.ChannelShowModel;
import jbolt.android.wardrobe.models.Comments;

public class ChannelShowData {

    public ChannelShowModel getNewest() {
        ChannelShowModel data = new ChannelShowModel();
        data.setType("newest");
        //data.setCollocationModels(collocationModels)
        //data.add(new ChannelShowModel());
        return data;
    }

    public List<Comments> getComments() {
        return addComments(15);
    }

    private List<Comments> addComments(int num) {
        List<String> comments = Arrays.asList("其实衣服要会搭配，就更显得好看", "春天来了，我们应该换换装了，出去游玩一下 ", "穿上单扣西装，戴上自己的太阳镜，你就是街头最惹眼的fashion icon", "很不错的出游装束，太有个性了，单扣西装，配上风衣，太阳镜，时尚", "很喜欢单扣西装这样的装扮，显得非常有个性", "这个美啊 求价格", "哎呦呦，好漂亮，我也想买了哈哈哈，", "它真的非常的有魔力的，好喜欢啊", "这期封面和里面的高圆圆，都美翻了，颜色化妆都好棒，把她的气质表现的淋漓尽致。带来了春天的气息");
        int size = comments.size();
        List<Comments> data = new ArrayList<Comments>();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < num; i++) {
            Comments com = new Comments();
            com.setComments(comments.get(random.nextInt(size)));
            com.setUserId(String.valueOf((long) random.nextInt(20000)));
            data.add(com);
            com = null;
        }
        return data;
    }


}
