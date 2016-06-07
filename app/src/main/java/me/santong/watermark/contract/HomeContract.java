package me.santong.watermark.contract;

import android.widget.BaseAdapter;

import java.util.List;

import me.santong.watermark.framework.BaseUserListener;
import me.santong.watermark.framework.BaseView;
import me.santong.watermark.model.FolderBean;

/**
 * Created by santong.
 * At 16/5/20 15:19
 */
public interface HomeContract {
    interface View extends BaseView {
        void init();

        void showPopupWindow();

        void hidePopupWindow();

        void refreshGridView();

        void setCurrentFolder(String folderName);

        void showDrawer();

        void hideDrawer();

        void initFolderPopupWindow(List<FolderBean> folderBeanList);

        void lightOn();

        void lightOff();

        void setGalleryAdapter(BaseAdapter adapter);

    }

    interface UserListener extends BaseUserListener {
        void setUpGalleryData(int pos);
    }
}
