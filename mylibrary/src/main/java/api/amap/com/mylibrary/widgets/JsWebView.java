package api.amap.com.mylibrary.widgets;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import api.amap.com.mylibrary.AboutAppListActivity;
import api.amap.com.mylibrary.R;

/**
 * author wangy
 * Created on 2018\4\12 0012.
 * description
 */

public class JsWebView extends MyViewGroup {

    public WebView webView;
    private Context context;
    private RecyclerView appsrecycle;
    private AppsHeadAdapter adapter;
    private DownloadAPK downloadAPK;
    private String fileName;
    private String appId;
    List<GetApp> applist = new ArrayList<>();
    //创建属于主线程的handler
    Handler handler = new Handler();
    private String result;
    Class c;


    public JsWebView(Context context, AttributeSet attrs) {
        super(context,attrs);

        this.context = context;
        this.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        init();
    }

    // 构建Runnable对象，在runnable中更新界面
    Runnable runnableUi = new Runnable() {
        @Override
        public void run() {
            try {
                //更新界面
                JSONObject object = JSON.parseObject(result);
                JSONObject jsonObject = object.getJSONObject("data");
                List<GetApp> list = JSONArray.parseArray(jsonObject.getString("appList"), GetApp.class);
                setappsData(list);
            } catch (Exception e) {
            }

        }

    };

    private void init() {
        try {
            c = Class.forName("com.ustcinfo.mobile.platform.ability.jsbridge.BridgeWebView");
            Constructor con = c.getConstructor(Context.class);
            webView = (WebView) con.newInstance(context);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        webView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.addView(webView);
        this.setType(Type.FOLLOW);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Class JsMethodAdapter = null;
        try {
            JsMethodAdapter = Class.forName("com.ustcinfo.mobile.platform.ability.jsbridge.JsMethodAdapter");
            Method method = JsMethodAdapter.getMethod("register", c);
            method.invoke(null, new Object[]{webView});
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        this.setHeader(new BaseHeader() {
            @Override
            public View getView(LayoutInflater inflater, ViewGroup viewGroup) {
                View view = inflater.inflate(R.layout.tab_layout, viewGroup, true);
                appsrecycle = (RecyclerView) view.findViewById(R.id.appsrecycle);
                Map<String, String> map = new HashMap<>();
                map.put("clientType", "1");
                HttpUtil.startGet(HttpURL.GETAPPLIST, map, new HttpCallbackListener() {
                    @Override
                    public void onSucess(String response) {
                        result = response;
                        handler.post(runnableUi);
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e("请求失败：", e.toString());
                    }
                });
                return view;
            }

            @Override
            public void onPreDrag(View rootView) {

            }

            @Override
            public void onDropAnim(View rootView, int dy) {

            }

            @Override
            public void onLimitDes(View rootView, boolean upORdown) {
            }

            @Override
            public void onStartAnim() {

            }

            @Override
            public void onFinishAnim() {

            }


        });

        this.setListener(new MyViewGroup.OnFreshListener() {
            @Override
            public void onRefresh() {
                //如果当前设置的头部是QQHeader,则不finish
                return;
            }

            @Override
            public void onLoadmore() {
            }
        });
    }

    public void setappsData(final List<GetApp> appslist) {
        List<GetApp> list = new ArrayList<>();
        final List<GetApp> alllist = new ArrayList<>();
        if (appslist != null && appslist.size() > 0) {
            for (int i = 0; i < appslist.size(); i++) {
                if ("apk".equals(appslist.get(i).getAppType())) {
                    list.add(appslist.get(i));
                }
            }
        }
        if (list != null && list.size() > 0) {
            alllist.addAll(list);
            if (list.size() > 3) {
                applist = list.subList(0, 3);
            } else {
                applist.addAll(list);
            }
            GetApp getApp = new GetApp();
            getApp.setAppName("更多");
            applist.add(getApp);
            adapter = new AppsHeadAdapter(context, R.layout.head_item, applist);
            appsrecycle.setAdapter(adapter);
            appsrecycle.setLayoutManager(new GridLayoutManager(context, 4));
            adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    {
                        if (position == applist.size() - 1) {
                             Intent intent = new Intent(context, AboutAppListActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("list", (Serializable) alllist);
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                        } else {
                            fileName = applist.get(position).getAppName();
                            appId = applist.get(position).getAppId();
                            if (!StringUtils.isEmpty(applist.get(position).getPackageName()) && !StringUtils.isEmpty(applist.get(position).getStartUrl())) {
                                if (PackageUtils.isAvilible(context, applist.get(position).getPackageName())) {
                                    PackageUtils.openApp(context, applist.get(position).getPackageName(), applist.get(position).getStartUrl());
                                } else {
                                    showDownloadProgressDialog(context);
                                }
                            } else {
                                showDownloadProgressDialog(context);
                            }
                        }
                    }
                }
            });
        }
    }

    private void showDownloadProgressDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("提示");
        progressDialog.setMessage("正在下载...");
        progressDialog.setIndeterminate(false);
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (downloadAPK != null && !downloadAPK.isCancelled() && downloadAPK.getStatus() == AsyncTask.Status.RUNNING) {
                            downloadAPK.cancel(true);
                            downloadAPK = null;
                        }
                    }
                });
        progressDialog.show();
        downloadAPK = new DownloadAPK(progressDialog);
        String downloadUrl = HttpURL.BASE_APPURL + "?appId=" + appId;
        downloadAPK.execute(downloadUrl);
    }

    public void loadUrl(String url) {

        webView.loadUrl(url);
    }


    public boolean canGoBack() {
        if (webView.canGoBack()) {
            return true;
        } else {
            return false;
        }
    }

    public void goBack() {
        webView.goBack();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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

    /**
     * 下载APK的异步任务
     */

    private class DownloadAPK extends AsyncTask<String, Integer, String> {
        ProgressDialog progressDialog;
        File file;

        public DownloadAPK(ProgressDialog progressDialog) {
            this.progressDialog = progressDialog;
        }

        @Override
        protected String doInBackground(String... params) {
            URL url;
            HttpURLConnection conn;
            BufferedInputStream bis = null;
            FileOutputStream fos = null;
            try {
                url = new URL(params[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);

                int fileLength = conn.getContentLength();
                bis = new BufferedInputStream(conn.getInputStream());

                String path = Environment.getExternalStorageDirectory().getPath() + "/loadapk/" + fileName + ".apk";
                file = new File(path);
                if (!file.exists()) {
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    file.createNewFile();
                }
                fos = new FileOutputStream(file);
                byte data[] = new byte[4 * 1024];
                long total = 0;
                int count;
                while ((count = bis.read(data)) != -1) {
                    if (isCancelled()) {
                        Log.e("cancell", "isCancelled");
                        fos.close();
                        bis.close();
                        break;
                    }
                    total += count;
                    publishProgress((int) (total * 100 / fileLength));
                    fos.write(data, 0, count);
                    fos.flush();
                }
                fos.flush();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (bis != null) {
                        bis.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            if (!isCancelled()) {
                progressDialog.setProgress(progress[0]);
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            openFile(file);
            progressDialog.dismiss();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Log.e("cancell2", "isCancelled");
            progressDialog.dismiss();
        }

        private void openFile(File file) {
            if (file != null) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                context.startActivity(intent);
            }

        }
    }
}
