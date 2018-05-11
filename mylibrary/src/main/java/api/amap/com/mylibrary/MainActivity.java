package api.amap.com.mylibrary;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.amap.querry.AmapLocation;
import com.tbruyelle.rxpermissions.RxPermissions;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import api.amap.com.mylibrary.widgets.JsWebView;
import api.amap.com.mylibrary.widgets.MAlertDialog;
import rx.functions.Action1;

//import com.amap.querry.AmapLocation;

/**
 * Created by xueqili on 2018/5/9.
 */

public class MainActivity extends AppCompatActivity {
    //    JsWebView webView;
    //创建属于主线程的handler
    Handler handler = new Handler();
    private String result;
    private int httpversion;
    Class c;
    JsWebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        try {
//            c = Class.forName("com.ustcinfo.mobile.platform.ability.jsbridge.JsWebView");
//            XmlPullParser parser = MainActivity.this.getResources().getXml(R.layout.textview);
//            AttributeSet attributes = Xml.asAttributeSet(parser);
//            int type;
//            try{
//                while ((type = parser.next()) != XmlPullParser.START_TAG &&
//                        type != XmlPullParser.END_DOCUMENT) {
//                    // Empty
//                }
//
//                if (type != XmlPullParser.START_TAG) {
//                    Log.e("","the xml file is error!\n");
//                }
//            } catch (XmlPullParserException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            Log.d("",""+parser.getAttributeCount());
//            Constructor con = c.getConstructor(Context.class);
//            webView = (ViewGroup) con.newInstance(this);
//            webView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//            Method method = c.getMethod("loadUrl",String.class);
//            method.invoke(webView,new Object[]{"file:///android_asset/demo/index.html"});
//
//            setContentView(webView);
//
////            Class JsMethodAdapter = Class.forName("com.ustcinfo.mobile.platform.ability.jsbridge.JsMethodAdapter");
////            Method method2 = JsMethodAdapter.getMethod("register", c);
////            method2.invoke(null, new Object[]{webView});
//
//
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.web_view);

        checkPermissions();
    }

    @SuppressLint("NewApi")
    private void checkPermissions() {

        RxPermissions rxPermissions = RxPermissions.getInstance(this);
        rxPermissions.request
                (Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.
                                RECORD_AUDIO, Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA, Manifest.permission.CALL_PHONE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (!aBoolean) {
                            final MAlertDialog alertDialog = new MAlertDialog(MainActivity.this, false, true);
                            alertDialog.setCancelable(true).setDialogCanceledOnTouchOutside(false).setTitle("应用权限").setContent("请务必给予应用相应的权限")
                                    .setCancelClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            finish();
                                        }
                                    }).setConfirmClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    Intent localIntent = new Intent();
                                    localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    if (Build.VERSION.SDK_INT >= 9) {
                                        localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                                        localIntent.setData(Uri.fromParts("package", getApplication().getPackageName(), null));
                                    } else if (Build.VERSION.SDK_INT <= 8) {
                                        localIntent.setAction(Intent.ACTION_VIEW);
                                        localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
                                        localIntent.putExtra("com.android.settings.ApplicationPkgName", getApplicationContext().getPackageName());
                                    }
                                    startActivity(localIntent);
                                    alertDialog.dismiss();
                                }
                            }).show();
                        } else {
                            try {
                                webView.loadUrl("file:///android_asset/demo/index.html");
                                AmapLocation.get().lunch(MainActivity.this);
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "请放入h5文件", Toast.LENGTH_LONG).show();
                            }
//                            getupdate();
                        }
                    }
                });
    }

//    private void getupdate() {
//        HttpUtil.startGet(MConfig.get(MainActivity.this, "GetUpdate"), null, new HttpCallbackListener() {
//            @Override
//            public void onSucess(String s) {
//                JSONObject object = JSON.parseObject(s);
//                httpversion = object.getIntValue("verson");
//                handler.post(runnableupdate);
//            }
//
//            @Override
//            public void onError(Exception e) {
//                Log.e("请求错误", e.toString());
//            }
//        });
//    }
//
//    // 构建Runnable对象，在runnable中更新界面
//    Runnable runnableupdate = new Runnable() {
//        @Override
//        public void run() {
//            try {
//                CheckJsUpdateUtils.CheckJsUpdate(MainActivity.this, webView, httpversion);
//            } catch (Exception e) {
//            }
//        }
//    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Class JsMethodAdapter = null;
        try {
            JsMethodAdapter = Class.forName("com.ustcinfo.mobile.platform.ability.jsbridge.JsMethodAdapter");
            Method method = JsMethodAdapter.getMethod("getmInstance");
            Object obj = method.invoke(null, null);
            Method method2 = JsMethodAdapter.getMethod("onActivityResult", Integer.class, Integer.class, Intent.class);
            method2.invoke(obj, new Object[]{requestCode, resultCode, data});
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            Class JsMethodAdapter = Class.forName("com.ustcinfo.mobile.platform.ability.jsbridge.JsMethodAdapter");
            Method method = JsMethodAdapter.getMethod("unRegister");
            method.invoke(null, null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
