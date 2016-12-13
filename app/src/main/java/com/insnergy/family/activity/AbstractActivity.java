package com.insnergy.family.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.insnergy.family.R;

/**
 * Created by lukefan on 2016/9/10.
 */
public class AbstractActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void gotoWidgetList(View view) {
        gotoActivity(new Intent(this, WidgetActivity.class));
    }

    public void gotoStatisticsList(View view) {
        gotoActivity(new Intent(this, StatisticsActivity.class));
    }

    public void gotoActivity(Intent intent) {
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//      finish();
    }

    protected void showProcessView(LinearLayout linearLayout) {
        linearLayout.setVisibility(View.VISIBLE);
    }

    protected void hideProcessView(LinearLayout linearLayout) {
        linearLayout.setVisibility(View.GONE);
    }
}
