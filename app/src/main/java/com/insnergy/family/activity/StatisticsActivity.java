package com.insnergy.family.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.insnergy.family.sdk.domain.ApiResult;
import com.insnergy.family.sdk.domain.Widget;
import com.insnergy.family.sdk.presenters.ApiCallback;
import com.insnergy.family.sdk.presenters.DataStatisticsPresenter;
import com.insnergy.family.sdk.utils.DatetimeUtils;
import com.insnergy.family.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StatisticsActivity extends AbstractActivity {

    private LinearLayout layoutFoot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statisitcs);
        LinearLayout layoutFunctionMenu = (LinearLayout) findViewById(R.id.layoutFunctionMenu);
        layoutFunctionMenu.setVisibility(View.VISIBLE);
        layoutFoot = (LinearLayout) findViewById(R.id.layoutFoot);
        layoutFoot.setVisibility(View.GONE);
        getStatisticsData();
    }

    private void getStatisticsData() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-01 00:00:00");
        date = DatetimeUtils.stringToDate(simpleDateFormat.format(date));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        ApiCallback apiCallback = new ApiCallback() {
            @Override
            protected void onSuccess(ApiResult apiResult) {
                hideProcessView(layoutFoot);
                ListView listViewStatisticsList = (ListView) findViewById(R.id.listViewStatisticsList);
                listViewStatisticsList.setAdapter(new StatisticsListAdapter(getLayoutInflater(), apiResult.getDevices().get(0).getData()));
            }

            @Override
            protected void onFail(ApiResult result) {
                hideProcessView(layoutFoot);
            }
        };
        Widget widget = new Widget();
        widget.setDev_id("RS02000D6F000172C18F");
        showProcessView(layoutFoot);
        DataStatisticsPresenter.getInstance().getStatisticsData(false,
                widget,
                DataStatisticsPresenter.TIME_TYPE.MONTH,
                DataStatisticsPresenter.DATA_TYPE.COST,
                calendar.getTimeInMillis(),
                apiCallback);
    }
}
