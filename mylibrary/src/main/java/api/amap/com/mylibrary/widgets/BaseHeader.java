package api.amap.com.mylibrary.widgets;

import android.view.View;

/**
 * Created by xueqili on 2018/4/11.
 */
public abstract class BaseHeader implements JsWebView.DragHander {

    @Override
    public int getDragLimitHeight(View rootView) {
        return 0;
    }

    @Override
    public int getDragMaxHeight(View rootView) {
        return 0;
    }

    @Override
    public int getDragSpringHeight(View rootView) {
        return 0;
    }
}
