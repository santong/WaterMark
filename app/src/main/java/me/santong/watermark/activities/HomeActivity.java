package me.santong.watermark.activities;

import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.List;

import me.santong.watermark.R;
import me.santong.watermark.contract.HomeContract;
import me.santong.watermark.framework.BaseActivity;
import me.santong.watermark.model.FolderBean;
import me.santong.watermark.presenter.HomePresenter;
import me.santong.watermark.widget.FoldersPopupWindow;

public class HomeActivity extends BaseActivity implements HomeContract.View, View.OnClickListener {

    private TextView tvName;
    private GridView gvContent;

    private FoldersPopupWindow popupWindow;

    private HomeContract.UserListener mPresenter;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_home;
    }

    @Override
    protected void initActivity() {
        initView();
        initData();
        initEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mPresenter = new HomePresenter(this, this);
        mPresenter.start();
    }

    private void initView() {
        tvName = (TextView) findViewById(R.id.id_home_tv_folder_name);
        gvContent = (GridView) findViewById(R.id.id_home_gv_gallery);
    }

    private void initData() {
        new HomePresenter(this, this);
    }

    private void initEvent() {
        tvName.setOnClickListener(this);
    }

    @Override
    public void init() {
        initView();
        initData();
        initEvent();
    }

    @Override
    public void showPopupWindow() {

    }

    @Override
    public void hidePopupWindow() {

    }

    @Override
    public void refreshGridView() {

    }

    @Override
    public void setCurrentFolder(String folderName) {

    }

    @Override
    public void showDrawer() {

    }

    @Override
    public void hideDrawer() {

    }

    @Override
    public void initFolderPopupWindow(List<FolderBean> folderBeanList) {
        popupWindow = new FoldersPopupWindow(this, folderBeanList);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });
    }

    @Override
    public void lightOn() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1.0f;
        getWindow().setAttributes(lp);
    }

    @Override
    public void lightOff() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.3f;
        getWindow().setAttributes(lp);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_home_tv_folder_name:
                if (popupWindow != null) {
                    popupWindow.showAsDropDown(tvName, 0, 0);
                    lightOff();
                }
                break;
        }
    }
}
