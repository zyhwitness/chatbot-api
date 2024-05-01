package cn.chabot.api.domain.zsxq.model.vo;

/**
 * @Description: TODO
 * @Author: iWitness
 * @Date: 2024/4/29 17:28
 * @Version 1.0
 */
public class UserSpecific {

    private boolean liked;

    private boolean subscribed;

    public void setLiked(boolean liked){
        this.liked = liked;
    }
    public boolean getLiked(){
        return this.liked;
    }
    public void setSubscribed(boolean subscribed){
        this.subscribed = subscribed;
    }
    public boolean getSubscribed(){
        return this.subscribed;
    }
}
