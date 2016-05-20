package me.santong.watermark.activities;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import me.santong.watermark.R;
import me.santong.watermark.contract.HomeContract;
import me.santong.watermark.framework.BaseActivity;

public class HomeActivity extends BaseActivity implements HomeContract.View {

    private TextView tvName;
    private GridView gvContent;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_home;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initView(){
        tvName = (TextView) findViewById(R.id.id_home_tv_folder_name);
        gvContent = (GridView) findViewById(R.id.id_home_gv_gallery);
    }
}
