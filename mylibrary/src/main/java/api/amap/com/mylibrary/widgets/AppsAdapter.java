package api.amap.com.mylibrary.widgets;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import api.amap.com.mylibrary.R;

/**
 * author wangy
 * Created on 2018\4\9 0009.
 * description
 */

public class AppsAdapter extends BaseAdapter<GetApp, BaseViewHolder> {

    Context context;
    private boolean isnegative = false;
    private DownloadAPK downloadAPK;
    private String fileName;
    private String appId;
    RequestOptions requestOptions = new RequestOptions()
            .centerCrop()
            .placeholder(R.mipmap.ic_launcher_round)
            .error(R.mipmap.ic_launcher_round)
            .priority(Priority.HIGH)
            .diskCacheStrategy(DiskCacheStrategy.ALL);

    public AppsAdapter(Context context, int layoutResId, List<GetApp> datas) {
        super(context, layoutResId, datas);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder holder,final GetApp item) {
        holder.getTextView(R.id.appname2).setText(item.getAppName());
        Glide.with(context).load(HttpURL.BASE_IMAGEURL + "?appId=" + item.getAppId()).apply(requestOptions).into(holder.getImageView(R.id.appicon2));
        final String packname = item.getPackageName();
        final String packstartname = item.getStartUrl();
        if (!StringUtils.isEmpty(packname) && !StringUtils.isEmpty(packstartname)) {
            if (PackageUtils.isAvilible(context, packname)) {
                holder.getButton(R.id.load).setText("打开");
            }
        }
        holder.getButton(R.id.load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fileName = item.getAppName();
                appId = item.getAppId();
                if (!StringUtils.isEmpty(packname) && !StringUtils.isEmpty(packstartname)) {
                    if (PackageUtils.isAvilible(context, packname)) {
                        PackageUtils.openApp(context, packname, packstartname);
                    } else {
                        showDownloadProgressDialog(context);
                    }
                } else {
                    showDownloadProgressDialog(context);
                }

            }
        });
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
                ((Activity) context).finish();
            }
        }
    }

}
