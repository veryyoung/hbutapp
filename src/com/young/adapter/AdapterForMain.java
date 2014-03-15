package com.young.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.young.R;

public class AdapterForMain extends BaseAdapter {
    private List<Map<String, Object>> mData;
    private LayoutInflater inflater;
    private Context context;

    public AdapterForMain(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        mData = getData();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mData.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListItemView itemView = null;
        if (null == convertView) {
            itemView = new ListItemView();
            convertView = inflater.inflate(R.layout.activity_main_item, null);
            itemView.textView = (TextView) convertView
                    .findViewById(R.id.text_main);
            convertView.setTag(itemView);
        } else {
            itemView = (ListItemView) convertView.getTag();
        }
        itemView.textView.setText(mData.get(position).get("text").toString());
        Drawable drawAble = (BitmapDrawable) context.getResources()
                .getDrawable((Integer) mData.get(position).get("imag"));
        itemView.textView.setCompoundDrawablesWithIntrinsicBounds(drawAble,
                null, null, null);
        return convertView;
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("imag", R.drawable.schedule);
        map.put("text", "      教学课表");
        list.add(map);

        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("imag", R.drawable.info);
        map1.put("text", "      学籍管理");
        list.add(map1);

        Map<String, Object> map3 = new HashMap<String, Object>();
        map3.put("imag", R.drawable.score);
        map3.put("text", "      成绩管理");
        list.add(map3);

        Map<String, Object> map4 = new HashMap<String, Object>();
        map4.put("imag", R.drawable.gerenpeizhi);
        map4.put("text", "      修改密码");
        list.add(map4);

        return list;
    }

    public final class ListItemView {
        public TextView textView;
    }

}
