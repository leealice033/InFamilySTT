package com.insnergy.family.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.insnergy.family.sdk.domain.ApiResult;
import com.insnergy.family.sdk.domain.Widget;
import com.insnergy.family.sdk.presenters.ApiCallback;
import com.insnergy.family.sdk.presenters.DevicePresenter;
import com.insnergy.family.sdk.presenters.WidgetPresenter;
import com.insnergy.family.R;

public class WidgetActivity extends AbstractActivity {

    public static final String ON = "on";
    public static final String OFF = "off";

    private LinearLayout layoutFoot;
    private Context context;
    private WidgetListAdapter.OnSwitchControlListener onSwitchControlListener = new WidgetListAdapter.OnSwitchControlListener() {
        @Override
        public void onControl(final Switch switchButton, Widget widget) {
            final String action = ON.equals(widget.getStatus()) ? OFF : ON;
            showProcessView(layoutFoot);
            DevicePresenter.getInstance().controlDevice(widget.getDev_id(), action, new ApiCallback() {
                @Override
                protected void onSuccess(ApiResult apiResult) {
                    hideProcessView(layoutFoot);
                    Toast.makeText(context, "Control Success", Toast.LENGTH_SHORT).show();
                }

                @Override
                protected void onFail(ApiResult apiResult) {
                    hideProcessView(layoutFoot);
                    Toast.makeText(context, "Control Fail", Toast.LENGTH_SHORT).show();
                    if (ON.equals(action)) {
                        switchButton.setChecked(false);
                    } else {
                        switchButton.setChecked(true);
                    }
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget);
        context = WidgetActivity.this;
        LinearLayout layoutFunctionMenu = (LinearLayout) findViewById(R.id.layoutFunctionMenu);
        layoutFunctionMenu.setVisibility(View.VISIBLE);
        layoutFoot = (LinearLayout) findViewById(R.id.layoutFoot);
        layoutFoot.setVisibility(View.GONE);
        getWidgets();
    }

    private void getWidgets() {
        showProcessView(layoutFoot);
        WidgetPresenter.getInstance().getWidgets(new ApiCallback() {
            @Override
            protected void onSuccess(ApiResult apiResult) {
                hideProcessView(layoutFoot);
                ExpandableListView listViewWidgetList = (ExpandableListView) findViewById(R.id.listViewWidgetList);
                listViewWidgetList.setAdapter(new WidgetListAdapter(getLayoutInflater(), apiResult.getWidgets(), onSwitchControlListener));
            }

            @Override
            protected void onFail(ApiResult result) {
                hideProcessView(layoutFoot);
            }
        });
    }


}
