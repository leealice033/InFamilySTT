package com.insnergy.family.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.insnergy.family.R;

/**
 * Created by lukefan on 2016/9/12.
 */
public class ListItemHandler {
    private View view;
    //Widget Item
    private LinearLayout layoutWidget;
    private TextView txtWidgetName;
    private TextView txtWidgetId;
    private Switch switchControlDevice;
    //Widget Info Item
    private LinearLayout layoutWidgetInfo;
    private TextView txtWidgetInfoName;
    private TextView txtWidgetInfoValue;
    //Statistics Item
    private LinearLayout layoutStatisticsData;
    private TextView txtStatisticsDate;
    private TextView txtStatisticsValue;

    public ListItemHandler(View view) {
        this.view = view;

        this.layoutWidget = (LinearLayout) view.findViewById(R.id.layoutWidget);
        this.txtWidgetName = (TextView) view.findViewById(R.id.txtWidgetName);
        this.txtWidgetId = (TextView) view.findViewById(R.id.txtWidgetId);
        this.switchControlDevice = (Switch) view.findViewById(R.id.switchControlDevice);

        this.layoutWidgetInfo = (LinearLayout) view.findViewById(R.id.layoutWidgetInfo);
        this.txtWidgetInfoName = (TextView) view.findViewById(R.id.txtWidgetInfoName);
        this.txtWidgetInfoValue = (TextView) view.findViewById(R.id.txtWidgetInfoValue);

        this.layoutStatisticsData = (LinearLayout) view.findViewById(R.id.layoutStatisticsData);
        this.txtStatisticsDate = (TextView) view.findViewById(R.id.txtStatisticsDate);
        this.txtStatisticsValue = (TextView) view.findViewById(R.id.txtStatisticsValue);
    }

    public View getView() {
        return view;
    }

    public LinearLayout getLayoutWidget() {
        return layoutWidget;
    }

    public TextView getTxtWidgetName() {
        return txtWidgetName;
    }

    public TextView getTxtWidgetId() {
        return txtWidgetId;
    }

    public LinearLayout getLayoutWidgetInfo() {
        return layoutWidgetInfo;
    }

    public TextView getTxtWidgetInfoName() {
        return txtWidgetInfoName;
    }

    public TextView getTxtWidgetInfoValue() {
        return txtWidgetInfoValue;
    }

    public LinearLayout getLayoutStatisticsData() {
        return layoutStatisticsData;
    }

    public TextView getTxtStatisticsDate() {
        return txtStatisticsDate;
    }

    public TextView getTxtStatisticsValue() {
        return txtStatisticsValue;
    }

    public Switch getSwitchControlDevice() {
        return switchControlDevice;
    }
}
