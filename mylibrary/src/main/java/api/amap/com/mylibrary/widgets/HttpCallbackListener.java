package api.amap.com.mylibrary.widgets;

/**
 * author wangy
 * Created on 2018\4\26 0026.
 * description
 */

public interface HttpCallbackListener {
    void onSucess(String response);
    void onError(Exception e);
}
