package api.amap.com.mylibrary.widgets;


import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;


/**
 * author wangy
 * Created on 2018\4\26 0026.
 * description
 */

public class HttpUtil {
    //get请求
    public static void startGet(final String path,final Map<String, String> mData,final HttpCallbackListener httpCallbackListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BufferedReader in = null;
                StringBuilder result = new StringBuilder();
                try {
                    //GET请求直接在链接后面拼上请求参数
                    String mPath = path + "?";
                    if (mData != null) {
                        for (String key : mData.keySet()) {
                            mPath += key + "=" + mData.get(key) + "&";
                        }
                    }
                    URL url = new URL(mPath);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    //Get请求不需要DoOutPut
                    conn.setDoOutput(false);
                    conn.setDoInput(true);
                    //设置连接超时时间和读取超时时间
                    conn.setConnectTimeout(10000);
                    conn.setReadTimeout(10000);
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    //连接服务器
                    conn.connect();
                    // 取得输入流，并使用Reader读取
                    in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                    String line;
                    while ((line = in.readLine()) != null) {
                        result.append(line);
                    }
                    Log.e("请求成功：", result.toString());
                    httpCallbackListener.onSucess(result.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    httpCallbackListener.onError(e);
                }
                //关闭输入流
                finally {
                    try {
                        if (in != null) {
                            in.close();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        httpCallbackListener.onError(ex);
                    }
                }
            }
        }).start();
    }

    //post请求
    public static void startPost(final String path, final Map<String, String> mData, final HttpCallbackListener httpCallbackListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OutputStreamWriter out = null;
                BufferedReader in = null;
                StringBuilder result = new StringBuilder();
                try {
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("Post");
                    // 发送POST请求必须设置为true
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    //设置连接超时时间和读取超时时间
                    conn.setConnectTimeout(10000);
                    conn.setReadTimeout(10000);
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                    out = new OutputStreamWriter(conn.getOutputStream());
                    // POST的请求参数写在正文中
                    if (mData != null) {
                        for (String key : mData.keySet()) {
                            out.write(key + "=" + mData.get(key) + "&");
                        }
                    }
                    out.flush();
                    out.close();
                    // 取得输入流，并使用Reader读取
                    in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                    String line;
                    while ((line = in.readLine()) != null) {
                        result.append(line);
                    }
                    Log.e("请求成功：", result.toString());
                    httpCallbackListener.onSucess(result.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    httpCallbackListener.onError(e);
                }
                //关闭输出流、输入流
                finally {
                    try {
                        if (out != null) {
                            out.close();
                        }
                        if (in != null) {
                            in.close();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        httpCallbackListener.onError(ex);
                    }
                }
            }
        }).start();
    }
}
