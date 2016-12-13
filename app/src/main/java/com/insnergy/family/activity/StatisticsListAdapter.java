package com.insnergy.family.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.insnergy.family.sdk.domain.Data;
import com.insnergy.family.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by lukefan on 2016/9/12.
 */
public class StatisticsListAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<Data> dataList;

    public StatisticsListAdapter(LayoutInflater layoutInflater, List<Data> dataList) {
        this.layoutInflater = layoutInflater;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListItemHandler listItemHandler;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item, null, false);
            listItemHandler = new ListItemHandler(convertView);
            convertView.setTag(listItemHandler);
        } else {
            listItemHandler = (ListItemHandler) convertView.getTag();
        }

        listItemHandler.getLayoutWidget().setVisibility(View.INVISIBLE);
        listItemHandler.getLayoutWidgetInfo().setVisibility(View.INVISIBLE);
        listItemHandler.getLayoutStatisticsData().setVisibility(View.VISIBLE);

        Data data = dataList.get(position);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.valueOf(data.getTime()));
        calendar.add(Calendar.DATE, -1);
        listItemHandler.getTxtStatisticsDate().setText("Time : " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime()));
        listItemHandler.getTxtStatisticsValue().setText("Value : " + data.getValue());
        return listItemHandler.getView();
    }
}
