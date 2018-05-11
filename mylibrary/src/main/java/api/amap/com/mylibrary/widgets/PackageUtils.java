package api.amap.com.mylibrary.widgets;

/**
 * Created by Administrator on 2018/1/2.
 */

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * 获取包名
 */
public class PackageUtils {
    public static JSONObject getAppInfo(Context context) {
        JSONObject jsonObject = new JSONObject();
        try {
            String pkName = context.getPackageName();
            jsonObject.put("data", pkName);
            jsonObject.put("errCode", "0");
            return jsonObject;
        } catch (Exception e) {
            jsonObject.put("data", "获取包名失败");
            jsonObject.put("errCode", "-1");
        }
        return jsonObject;
    }

    public static void openApp(Context context, String packageName, String packstartname) {
        Intent intent = new Intent();
        ComponentName cn = new ComponentName(packageName,
                packstartname);
        intent.setComponent(cn);
        context.startActivity(intent);
    }

    public static boolean isAvilible(Context context, String packageName) {

        final PackageManager packageManager = context.getPackageManager();

        // 获取所有已安装程序的包信息
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        for (int i = 0; i < pinfo.size(); i++) {

            // 循环判断是否存在指定包名
            if (pinfo.get(i).packageName.equalsIgnoreCase(packageName)) {
                return true;
            }

        }
        return false;
    }
}
