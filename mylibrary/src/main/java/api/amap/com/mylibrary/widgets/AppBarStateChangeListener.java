package api.amap.com.mylibrary.widgets;

/**
 * Created by xueqili on 2018/4/11.
 */
public abstract class AppBarStateChangeListener {

    public enum State {
        EXPANDED,
        COLLAPSED,
        IDLE
    }

    private State mCurrentState = State.IDLE;

}

