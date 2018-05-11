package api.amap.com.mylibrary.widgets;

import java.io.Serializable;

/**
 * author wangy
 * Created on 2018\4\9 0009.
 * description
 */

public class GetApp implements Serializable {
    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    private String appType;
    private String appId;

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setStartUrl(String startUrl) {
        this.startUrl = startUrl;
    }

    private String appName;
    private String packageName;
    private String startUrl;

    public String getAppId() {
        return appId;
    }

    public String getAppName() {
        return appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getStartUrl() {
        return startUrl;
    }


}
