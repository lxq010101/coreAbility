package api.amap.com.mylibrary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import api.amap.com.mylibrary.widgets.AppsAdapter;
import api.amap.com.mylibrary.widgets.GetApp;


/**
 * author wangy
 * Created on 2018\4\9 0009.
 * description
 */

public class AboutAppListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private AppsAdapter adapter;
    private List<GetApp> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutapp_list);
        initview();
        initdata();
    }


    private void initview() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle);
    }

    private void initdata() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            list = (List<GetApp>) bundle.getSerializable("list");
        }
        adapter = new AppsAdapter(AboutAppListActivity.this, R.layout.getapps_items, list);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
