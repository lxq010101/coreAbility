package api.amap.com.mylibrary.widgets;

/**
 * author wangy
 * Created on 2018\4\26 0026.
 * description
 */

public interface HttpURL {
    //生产环境
    String BASE_URL = "http://60.174.249.204:30002/eip-app-shandong/static/index/";
    String BASE_IMAGEURL = BASE_URL + "api/downloadAppIcon";
    String BASE_APPURL = BASE_URL + "api/downloadApp";
    String GETAPPLIST=BASE_URL +"api/getAppList";
}
