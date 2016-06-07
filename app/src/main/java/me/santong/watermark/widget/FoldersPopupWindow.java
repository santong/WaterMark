package me.santong.watermark.widget;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import me.santong.watermark.R;
import me.santong.watermark.model.FolderBean;

/**
 * Created by santong.
 * At 16/5/21 13:30
 */
public class FoldersPopupWindow extends PopupWindow {

    private Context mContext;

    private int mWidth;
    private int mHeight;

    private View mConvertView;

    private List<FolderBean> mFolderList;

    private RecyclerView rvDirs;

    private OnFolderItemClick onFolderItemClick;

    public FoldersPopupWindow(Context context, List<FolderBean> folderBeanList) {
        mContext = context;
        mFolderList = folderBeanList;

        Log.e("====", folderBeanList.size() + "");

        calWidthAndHeight(context);

        mConvertView = LayoutInflater.from(context).inflate(R.layout.popup_window_layout, null);

        setContentView(mConvertView);
        setWidth(mWidth);
        setHeight(mHeight);

        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());

        setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    dismiss();
                    return true;
                }
                return false;
            }
        });

        initView();
        initEvent();
    }

    public interface OnFolderItemClick {
        void onClick(int position, View view);
    }

    private void calWidthAndHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);

        mHeight = (int) (outMetrics.heightPixels * 0.7);
        mWidth = outMetrics.widthPixels;
    }

    private void initView() {
        rvDirs = (RecyclerView) mConvertView.findViewById(R.id.id_popup_rv);
        rvDirs.setLayoutManager(new LinearLayoutManager(mContext,
                LinearLayoutManager.VERTICAL, false));
        rvDirs.setAdapter(new DirAdapter(mContext, mFolderList));
    }

    private void initEvent() {

    }

    private class DirAdapter extends RecyclerView.Adapter<DirAdapter.ViewHolder> {

        private List<FolderBean> mFolderBeanList;
        private Context mContext;

        public DirAdapter(Context context, List<FolderBean> objects) {
            mContext = context;
            mFolderBeanList = objects;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(mContext)
                    .inflate(R.layout.item_popup_window, parent, false));
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            FolderBean bean = mFolderBeanList.get(position);
            Glide.with(mContext).load(bean.getCoverPath()).into(holder.ivFirstImg);
            holder.tvFolderName.setText(bean.getDir());
            holder.tvFolderSize.setText(String.valueOf(bean.getSize()));

            onFolderItemClick = (OnFolderItemClick) mContext;
            if (null != onFolderItemClick) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onFolderItemClick.onClick(holder.getLayoutPosition()
                                , holder.itemView);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return mFolderBeanList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ImageView ivFirstImg;
            TextView tvFolderName;
            TextView tvFolderSize;

            public ViewHolder(View itemView) {
                super(itemView);
                ivFirstImg = (ImageView) itemView.findViewById(R.id.id_pop_item_img);
                tvFolderName = (TextView) itemView.findViewById(R.id.id_pop_item_folder_name);
                tvFolderSize = (TextView) itemView.findViewById(R.id.id_pop_item_folder_size);
            }
        }
    }
}
