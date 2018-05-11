package api.amap.com.mylibrary.widgets;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import api.amap.com.mylibrary.R;

/**
 * author wangy
 * Created on 2018\4\12 0012.
 * description
 */

public class AppsHeadAdapter extends BaseAdapter<GetApp, BaseViewHolder> {
    RequestOptions requestOptions = new RequestOptions()
            .centerCrop()
            .priority(Priority.HIGH)
            .diskCacheStrategy(DiskCacheStrategy.ALL);
    private Context context;

    public AppsHeadAdapter(Context context, int layoutResId, List<GetApp> datas) {
        super(context, layoutResId, datas);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, GetApp item) {
        holder.getTextView(R.id.appname).setText(item.getAppName());
        if (!"更多".equals(item.getAppName())) {
            Glide.with(context).load(HttpURL.BASE_IMAGEURL + "?appId=" + item.getAppId()).apply(requestOptions).into(holder.getImageView(R.id.appicon));
        }

//        holder.getView(R.id.ll_headitems).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                fileName = item.getAppName();
//                if (!StringUtils.isEmpty(item.getPackageName()) && !StringUtils.isEmpty(item.getStartUrl())) {
//                    if (PackageUtils.isAvilible(context, item.getPackageName())) {
//                        PackageUtils.openApp(context, item.getPackageName(), item.getPackageName());
//                    } else {
//                        showDownloadProgressDialog(context);
//                    }
//                } else {
//                    showDownloadProgressDialog(context);
//                }
//            }
//        });
    }
}
