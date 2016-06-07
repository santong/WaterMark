package me.santong.watermark.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import me.santong.watermark.R;

/**
 * Created by santong.
 * At 16/6/7 15:07
 */
public class GalleryAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private List<String> mPathList;

    public GalleryAdapter(Context context, List<String> objects) {
        super(context, 0, objects);
        mContext = context;
        mPathList = objects;
    }

    @Override
    public String getItem(int position) {
        return mPathList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if (null == convertView) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_gallery, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.imageView = (ImageView) view.findViewById(R.id.id_gallery_item_img);
            view.setTag(viewHolder);

            AutoUtils.auto(view);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String path = mPathList.get(position);
        Glide.with(mContext).load(path).into(viewHolder.imageView);
        return view;
    }

    private class ViewHolder {
        ImageView imageView;
    }
}
