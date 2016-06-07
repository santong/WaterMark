package me.santong.watermark.presenter;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import me.santong.watermark.adapters.GalleryAdapter;
import me.santong.watermark.contract.HomeContract;
import me.santong.watermark.model.FolderBean;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by santong.
 * At 16/5/20 15:20
 */
public class HomePresenter implements HomeContract.UserListener {

    private Context mContext;

    private HomeContract.View mView;

    private List<FolderBean> folderBeanList;

    private List<String> mPathList;

    public HomePresenter(Context context, HomeContract.View view) {
        mContext = context;
        mView = view;
    }

    @Override
    public void start() {
        mPathList = new LinkedList<>();
        findFoldersAndPhotos();
    }

    @Override
    public void loadPhotosByFolder(int pos) {

    }

    @Override
    public void setUpGalleryData(int pos) {
        if (null == folderBeanList || folderBeanList.size() < pos)
            return;
        FolderBean folderBean = folderBeanList.get(pos);

        mPathList.clear();
        mPathList.addAll(folderBean.getPathList());
        GalleryAdapter adapter = new GalleryAdapter(mContext, mPathList);
        mView.setGalleryAdapter(adapter);
        Log.e("===1", folderBean.getName());

        String folderDetail = folderBean.getName() + "(" + folderBean.getSize() + ")";
        mView.setCurrentFolder(folderDetail);
    }

    private void findFoldersAndPhotos() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            mView.showToast("当前存储卡不可用");
            return;
        }
        mView.showProgress();
        folderBeanList = new LinkedList<>();

        Observable.create(new Observable.OnSubscribe<List<FolderBean>>() {
            @Override
            public void call(Subscriber<? super List<FolderBean>> subscriber) {
                Uri mImgUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver cr = mContext.getContentResolver();

                Cursor cursor = cr.query(mImgUri, null, MediaStore.Images.Media.MIME_TYPE
                                + " = ? or " + MediaStore.Images.Media.MIME_TYPE
                                + " = ? ", new String[]{"image/jpeg", "image/png"}
                        , MediaStore.Images.Media.DATE_MODIFIED);

                Set<String> mDirPaths = new TreeSet<>();

                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        String path = cursor.getString(cursor
                                .getColumnIndex(MediaStore.Images.Media.DATA));
                        File parentFile = new File(path).getParentFile();

                        if (parentFile == null)
                            continue;

                        String dirPath = parentFile.getAbsolutePath();
                        FolderBean folder;

                        if (TextUtils.isEmpty(dirPath)) {
                            continue;
                        }
                        if (mDirPaths.contains(dirPath)) {
                            continue;
                        } else {
                            mDirPaths.add(dirPath);
                            folder = new FolderBean();
                            folder.setDir(dirPath);
                            folder.setCoverPath(path);
                        }

                        if (parentFile.list() == null)
                            continue;

                        List<String> pathList = new LinkedList<>();
                        folder.setPathList(pathList);

                        for (String temp : parentFile.list()) {
                            folder.getPathList().add(dirPath + "/" + temp);
                        }
                        folder.getPathList().addAll(Arrays.asList(parentFile.list()));

                        int picSize = parentFile.list(new FilenameFilter() {
                            @Override
                            public boolean accept(File dir, String filename) {
                                return filename.endsWith(".jpg")
                                        || filename.endsWith(".jpeg")
                                        || filename.endsWith(".png");
                            }
                        }).length;

                        folder.setSize(picSize);

                        folderBeanList.add(folder);
                    }
                    cursor.close();
                }

                subscriber.onNext(folderBeanList);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<FolderBean>>() {
                    @Override
                    public void onCompleted() {
                        mView.hideProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("===", Arrays.toString(e.getStackTrace()) + "\n" + e.getLocalizedMessage());
                        mView.hideProgress();
                        mView.showToast(e.getMessage());
                    }

                    @Override
                    public void onNext(List<FolderBean> folderBeanList) {
                        mView.initFolderPopupWindow(folderBeanList);
                    }
                });
    }
}
