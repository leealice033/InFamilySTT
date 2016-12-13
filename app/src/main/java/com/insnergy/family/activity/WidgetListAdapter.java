package com.insnergy.family.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Switch;

import com.insnergy.family.R;
import com.insnergy.family.sdk.domain.Widget;
import com.insnergy.family.sdk.domain.WidgetInfo;
import com.insnergy.family.sdk.utils.DeviceInfoManager;

import java.util.ArrayList;
import java.util.List;

import static com.insnergy.family.sdk.utils.DeviceInfoManager.DeviceInfoCode.METER_CAPABILITY;

/**
 * Created by lukefan on 2016/9/12.
 */
public class WidgetListAdapter extends BaseExpandableListAdapter {

    private LayoutInflater layoutInflater;
    private List<Widget> widgetList;
    private List<List<WidgetInfo>> widgetInfoList;
    private OnSwitchControlListener onSwitchControlListener;

    public WidgetListAdapter(LayoutInflater layoutInflater, List<Widget> widgetList, OnSwitchControlListener onSwitchControlListener) {
        this.layoutInflater = layoutInflater;
        this.widgetList = widgetList;
        this.widgetInfoList = new ArrayList<>();
        for (Widget widget : widgetList) {
            this.widgetInfoList.add(widget.getWidget_infos());
        }
        this.onSwitchControlListener = onSwitchControlListener;
    }

    public interface OnSwitchControlListener {
        void onControl(Switch switchButton, Widget widget);
    }

    @Override
    public int getGroupCount() {
        return widgetList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (widgetInfoList.get(groupPosition) == null) {
            return 0;
        }
        return widgetInfoList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return widgetList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return widgetInfoList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ListItemHandler listItemHandler = getListItem(convertView);
        listItemHandler.getLayoutWidget().setVisibility(View.VISIBLE);
        final Widget widget = widgetList.get(groupPosition);
        listItemHandler.getTxtWidgetName().setText("Device Name : " + widget.getAlias());
        listItemHandler.getTxtWidgetId().setText("Device ID : " + widget.getDev_id());
        if ("OUTLET".equals(widget.getDev_category())) {
            listItemHandler.getSwitchControlDevice().setVisibility(View.VISIBLE);
            for (WidgetInfo widgetInfo : widget.getWidget_infos()) {
                if (widgetInfo.getInfo_name().equals(METER_CAPABILITY.getCode())) {
                    switch (widgetInfo.getInfo_value()) {
                        case "1":
                        case "3":
                            listItemHandler.getSwitchControlDevice().setVisibility(View.VISIBLE);
                            if (widget.getStatus().equals(WidgetActivity.ON)) {
                                listItemHandler.getSwitchControlDevice().setChecked(true);
                            } else {
                                listItemHandler.getSwitchControlDevice().setChecked(false);
                            }

                            listItemHandler.getSwitchControlDevice().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    onSwitchControlListener.onControl((Switch) v, widget);
                                }
                            });
                            break;
                        case "2":
                        default:
                            listItemHandler.getSwitchControlDevice().setVisibility(View.INVISIBLE);
                            break;
                    }
                }
            }
        } else {
            listItemHandler.getSwitchControlDevice().setVisibility(View.INVISIBLE);
        }
        return listItemHandler.getView();
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ListItemHandler listItemHandler = getListItem(convertView);
        listItemHandler.getLayoutWidgetInfo().setVisibility(View.VISIBLE);
        WidgetInfo widgetInfo = widgetInfoList.get(groupPosition).get(childPosition);
        listItemHandler.getTxtWidgetInfoName().setText(getWidgetInfoName(layoutInflater.getContext(), widgetInfo) + " : ");
        listItemHandler.getTxtWidgetInfoValue().setText(widgetInfo.getInfo_value());
        return listItemHandler.getView();
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private ListItemHandler getListItem(View view) {
        ListItemHandler listItemHandler;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_item, null, false);
            listItemHandler = new ListItemHandler(view);
            view.setTag(listItemHandler);
        } else {
            listItemHandler = (ListItemHandler) view.getTag();
        }
        listItemHandler.getLayoutWidget().setVisibility(View.INVISIBLE);
        listItemHandler.getLayoutWidgetInfo().setVisibility(View.INVISIBLE);
        listItemHandler.getLayoutStatisticsData().setVisibility(View.INVISIBLE);
        return listItemHandler;
    }

    public String getWidgetInfoName(Context context, WidgetInfo widgetInfo) {
        try {
            DeviceInfoManager.DeviceInfoCode deviceInfoCode = DeviceInfoManager.DeviceInfoCode.getEnum(widgetInfo.getInfo_name());
            switch (deviceInfoCode) {
                case CODE_100600:
                    return context.getResources().getString(R.string.device_attr_100600);
                case CODE_100700:
                    return context.getResources().getString(R.string.device_attr_100700);
                case CODE_100800:
                    return context.getResources().getString(R.string.device_attr_100800);
                case CONNECTION_STATUS:
                    return context.getResources().getString(R.string.device_attr_100900);
                case SWITCH_STATUS:
                    return context.getResources().getString(R.string.device_attr_101000);
                case CODE_101100:
                    return context.getResources().getString(R.string.device_attr_101100);
                case CODE_101200:
                    return context.getResources().getString(R.string.device_attr_101200);
                case CODE_101800:
                    return context.getResources().getString(R.string.device_attr_101800);
                case CODE_101900:
                    return context.getResources().getString(R.string.device_attr_101900);
                case CODE_102000:
                    return context.getResources().getString(R.string.device_attr_102000);
                case CODE_110100:
                    return context.getResources().getString(R.string.device_attr_110100);
                case CODE_110200:
                    return context.getResources().getString(R.string.device_attr_110200);
                case CODE_110300:
                    return context.getResources().getString(R.string.device_attr_110300);
                case CODE_110400:
                    return context.getResources().getString(R.string.device_attr_110400);
                case CODE_110500:
                    return context.getResources().getString(R.string.device_attr_110500);
                case CODE_110600:
                    return context.getResources().getString(R.string.device_attr_110600);
                case CODE_110700:
                    return context.getResources().getString(R.string.device_attr_110700);
                case TEMP:
                    return context.getResources().getString(R.string.device_attr_400100);
                case HUMID:
                    return context.getResources().getString(R.string.device_attr_400200);
                case CODE_400300:
                    return context.getResources().getString(R.string.device_attr_400300);
                case CODE_400400:
                    return context.getResources().getString(R.string.device_attr_400400);
                case CODE_400500:
                    return context.getResources().getString(R.string.device_attr_400500);
                case CO2:
                    return context.getResources().getString(R.string.device_attr_400600);
                case CO:
                    return context.getResources().getString(R.string.device_attr_400700);
                case CODE_400800:
                    return context.getResources().getString(R.string.device_attr_400800);
                case CODE_400900:
                    return context.getResources().getString(R.string.device_attr_400900);
                case CODE_401000:
                    return context.getResources().getString(R.string.device_attr_401000);
                case CODE_401100:
                    return context.getResources().getString(R.string.device_attr_401100);
                case CODE_401200:
                    return context.getResources().getString(R.string.device_attr_401200);
                case CODE_401300:
                    return context.getResources().getString(R.string.device_attr_401300);
                case CODE_401400:
                    return context.getResources().getString(R.string.device_attr_401400);
                case CODE_401500:
                    return context.getResources().getString(R.string.device_attr_401500);
                case CODE_500500:
                    return context.getResources().getString(R.string.device_attr_500500);
                case CODE_500600:
                    return context.getResources().getString(R.string.device_attr_500600);
                case CODE_500700:
                    return context.getResources().getString(R.string.device_attr_500700);
                case CODE_500800:
                    return context.getResources().getString(R.string.device_attr_500800);
                case ACTIVE_POWER:
                    return context.getResources().getString(R.string.device_attr_500900);
                case CODE_501000:
                    return context.getResources().getString(R.string.device_attr_501000);
                case CODE_501100:
                    return context.getResources().getString(R.string.device_attr_501100);
                case KWH:
                    return context.getResources().getString(R.string.device_attr_501200);
                case CODE_501300:
                    return context.getResources().getString(R.string.device_attr_501300);
                case CODE_501400:
                    return context.getResources().getString(R.string.device_attr_501400);
                case CODE_501500:
                    return context.getResources().getString(R.string.device_attr_501500);
                case CODE_501600:
                    return context.getResources().getString(R.string.device_attr_501600);
                case CODE_501700:
                    return context.getResources().getString(R.string.device_attr_501700);
                case CODE_501800:
                    return context.getResources().getString(R.string.device_attr_501800);
                case CODE_501900:
                    return context.getResources().getString(R.string.device_attr_501900);
                case CODE_502000:
                    return context.getResources().getString(R.string.device_attr_502000);
                case CODE_502100:
                    return context.getResources().getString(R.string.device_attr_502100);
                case CODE_502200:
                    return context.getResources().getString(R.string.device_attr_502200);
                case CODE_502300:
                    return context.getResources().getString(R.string.device_attr_502300);
                case METER_CAPABILITY:
                    return context.getResources().getString(R.string.device_attr_550100);
                case CODE_551300:
                    return context.getResources().getString(R.string.device_attr_551300);
                case CODE_551500:
                    return context.getResources().getString(R.string.device_attr_551500);
                case CODE_551600:
                    return context.getResources().getString(R.string.device_attr_551600);
                case CODE_551700:
                    return context.getResources().getString(R.string.device_attr_551700);
                case CODE_551800:
                    return context.getResources().getString(R.string.device_attr_551800);
                case CODE_551900:
                    return context.getResources().getString(R.string.device_attr_551900);
                case CODE_610100:
                    return context.getResources().getString(R.string.device_attr_610100);
                case CODE_610200:
                    return context.getResources().getString(R.string.device_attr_610200);
                case CODE_610300:
                    return context.getResources().getString(R.string.device_attr_610300);
                case CODE_610400:
                    return context.getResources().getString(R.string.device_attr_610400);
                case CODE_610500:
                    return context.getResources().getString(R.string.device_attr_610500);
                case CODE_610600:
                    return context.getResources().getString(R.string.device_attr_610600);
                case CODE_610800:
                    return context.getResources().getString(R.string.device_attr_610800);
                case CODE_620500:
                    return context.getResources().getString(R.string.device_attr_620500);
                case CODE_640100:
                    return context.getResources().getString(R.string.device_attr_640100);
                case CODE_640200:
                    return context.getResources().getString(R.string.device_attr_640200);
                case CODE_640300:
                    return context.getResources().getString(R.string.device_attr_640300);
                case CODE_640400:
                    return context.getResources().getString(R.string.device_attr_640400);
                case CODE_640500:
                    return context.getResources().getString(R.string.device_attr_640500);
                case CODE_640600:
                    return context.getResources().getString(R.string.device_attr_640600);
                case CODE_640700:
                    return context.getResources().getString(R.string.device_attr_640700);
                case CODE_640800:
                    return context.getResources().getString(R.string.device_attr_640800);
                case CODE_640900:
                    return context.getResources().getString(R.string.device_attr_640900);
                case CODE_641000:
                    return context.getResources().getString(R.string.device_attr_641000);
                case CODE_641100:
                    return context.getResources().getString(R.string.device_attr_641100);
                case CODE_641200:
                    return context.getResources().getString(R.string.device_attr_641200);
                case CODE_641300:
                    return context.getResources().getString(R.string.device_attr_641300);
                case CODE_641400:
                    return context.getResources().getString(R.string.device_attr_641400);
                case CODE_641500:
                    return context.getResources().getString(R.string.device_attr_641500);
                case CODE_650100:
                    return context.getResources().getString(R.string.device_attr_650100);
                case CODE_650200:
                    return context.getResources().getString(R.string.device_attr_650200);
                case CODE_650300:
                    return context.getResources().getString(R.string.device_attr_650300);
                case CODE_650400:
                    return context.getResources().getString(R.string.device_attr_650400);
                case CODE_650500:
                    return context.getResources().getString(R.string.device_attr_650500);
                case CODE_650600:
                    return context.getResources().getString(R.string.device_attr_650600);
                case CODE_650700:
                    return context.getResources().getString(R.string.device_attr_650700);
                case CODE_650800:
                    return context.getResources().getString(R.string.device_attr_650800);
                case CODE_650900:
                    return context.getResources().getString(R.string.device_attr_650900);
                case CODE_651000:
                    return context.getResources().getString(R.string.device_attr_651000);
                case CODE_651100:
                    return context.getResources().getString(R.string.device_attr_651100);
                case CODE_651200:
                    return context.getResources().getString(R.string.device_attr_651200);
                case CODE_651300:
                    return context.getResources().getString(R.string.device_attr_651300);
                case CODE_651400:
                    return context.getResources().getString(R.string.device_attr_651400);
                case CODE_651500:
                    return context.getResources().getString(R.string.device_attr_651500);
                case CODE_651600:
                    return context.getResources().getString(R.string.device_attr_651600);
                case CODE_651700:
                    return context.getResources().getString(R.string.device_attr_651700);
                case CODE_651800:
                    return context.getResources().getString(R.string.device_attr_651800);
                case CODE_651900:
                    return context.getResources().getString(R.string.device_attr_651900);
                case CODE_662001:
                    return context.getResources().getString(R.string.device_attr_662001);
                default:
                    return widgetInfo.getInfo_desc();
            }
        } catch (Exception e) {
            return widgetInfo.getInfo_desc();
        }
    }
}
