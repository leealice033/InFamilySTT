package sttrestsample;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import domain.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.insnergy.family.R;
//import com.insnergy.family.activity.WidgetActivity;
import com.insnergy.family.activity.*;
import com.insnergy.family.sdk.SdkManager;
import com.insnergy.family.sdk.domain.ApiResult;
import com.insnergy.family.sdk.domain.Widget;
import com.insnergy.family.sdk.presenters.ApiCallback;
import com.insnergy.family.sdk.presenters.DevicePresenter;
import com.insnergy.family.sdk.presenters.WidgetPresenter;

import org.json.JSONException;
import org.json.JSONObject;

import itri.org.record.ITRIAudioRecorder;
import itri.org.record.ITRIAudioRecorder.OnRecorderListener;

public class STTActivity extends AbstractActivity implements View.OnClickListener, OnRecorderListener {
    private static final boolean DBG = true;
    private static final String TAG = "SSTSample";
    private Context context;
    ITRIAudioRecorder itriRecord;
    private String dev_ID;

    Button buttonRecord;
    Boolean recordStatus = false;
    TextView textInput, textOutput;
    public ProgressDialog myDialog = null;
    Spinner selectModel;

    Handler myHandler = new Handler();
    public int countI = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stt);

        context = STTActivity.this;
        SdkManager.getInstance().initSdk(context);

        recordInit();
        setContentView(R.layout.activity_stt);
        setContentView(R.layout.activity_stt);
        textInput = (TextView) findViewById(R.id.input);
        textOutput = (TextView) findViewById(R.id.output);

        buttonRecord = (Button) findViewById(R.id.Record);
        buttonRecord.setOnClickListener(this);

        WidgetPresenter.getInstance().getWidgets(new ApiCallback() {
            @Override
            protected void onSuccess(ApiResult apiResult) {
                WidgetPresenter.getInstance().widgetList.addAll(apiResult.getWidgets());
            }

            @Override
            protected void onFail(ApiResult result) {

            }
        });
    }

    private void recordInit() {
        String AuthorizationID = "3c6187ed3ea3d40aefa58527c8c64fd8";
        String locName = "useUserDefineSTT";
        String taskName = "IF燈控與查詢";
        int rate = 16000;
        itriRecord = new ITRIAudioRecorder();
        itriRecord.setOnRecorderListener(this);
        itriRecord.setSTTVariable(AuthorizationID, locName, taskName, rate);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int ID = v.getId();
        switch (ID) {
            case R.id.Record:
                if (recordStatus) {
                    recordStatus = false;
                    buttonRecord.setText("Record");
                    itriRecord.stopRecord();
                } else {
                    recordStatus = true;
                    buttonRecord.setText("Stop");
                    itriRecord.startRecord("", false);    //單次 if連續 itriRecord.startRecord(true,"", false)
                }
                break;
        }
    }

    /***
     * OnRecorderListener function
     */
    @Override
    public void onErr(int errCode, String errMsg) {
        // TODO Auto-generated method stub
        Log.e(TAG, "RECORD_ERROR!errcode: " + errCode + " errMsg: " + errMsg);
    }

    @Override
    public void onSTTResult(String jsonResult) {
        String groupID;
        // TODO Auto-generated method stub
        Log.e(TAG, "onSTTResult:" + jsonResult);
        textInput.setText(jsonResult);//看Json結果
        try {
            /*Result*/
            String Result = new JSONObject(jsonResult).getJSONArray("sents").getJSONObject(0).getString("sent");
            //textOutput.setText(Result);//中文指令
            Log.e(TAG, "ChineseResult:" + Result);
            /*Json轉Gson*/
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss"); // 设置日期的格式，遇到这个格式的数据转为Date对象
            SttResult sResult = new Gson().fromJson(jsonResult, SttResult.class);//Json->Gson
            Log.e(TAG, "Sresult:" + sResult);
            /*先抓到action(command):CMD_TurnON;CMD_TurnOFF;GroupReport;CMD_Report再從對應的指令格式中抓到對應的裝置
            */
            String command = sResult.getSents().get(0).getAction();
            switch (command) {
                case "CMD_TurnON":
                    Log.e(TAG, "CMDResult:" + command);
                    Log.e(TAG, "Device:" + sResult.getSents().get(0).getTransword().get(1).getGroup_id());
                    groupID = sResult.getSents().get(0).getTransword().get(1).getGroup_id();
                    switch (deviceCheck(groupID)) {
                        case "LivingRoom":
                            devControl("on");
                            break;
                        case "Kitchen":
                            devControl("on");
                            break;
                        default:
                            textOutput.setText("查無此裝置");
                            break;
                    }
                    break;
                case "CMD_TurnOFF":
                    Log.e(TAG, "CMDResult:" + command);
                    groupID = sResult.getSents().get(0).getTransword().get(1).getGroup_id();
                    switch (deviceCheck(groupID)) {
                        case "LivingRoom":
                            devControl("off");
                            break;
                        case "Kitchen":
                            devControl("off");
                            break;
                        default:
                            textOutput.setText("查無此裝置");
                            break;
                    }
                    break;
                case "GroupReport"://報表
                    groupID = sResult.getSents().get(0).getTransword().get(0).getGroup_id();
                    switch (deviceCheck(groupID)) {
                        case "LivingRoom":
                            textOutput.setText("客廳燈況");
                            break;
                        case "Kitchen":
                            textOutput.setText("廚房燈況");
                            break;
                        default:
                            textOutput.setText("查無此裝置");
                            break;
                    }
                    break;
                case "CMD_Report"://查詢報表
                    Log.e(TAG, "CMDResult:" + command);
                    Log.e(TAG, "Control:" + sResult.getSents().get(0).getTransword().get(0).getGroup_id());
                    Log.e(TAG, "Device:" + sResult.getSents().get(0).getTransword().get(1).getGroup_id());
                    groupID = sResult.getSents().get(0).getTransword().get(1).getGroup_id();
                    switch (deviceCheck(groupID)) {
                        case "LivingRoom":
                            Intent intent = new Intent();
                            intent.setClass(context, WidgetActivity.class);
                            gotoActivity(intent);
                            textOutput.setText("顯示客廳燈報表");
                            break;
                        case "Kitchen":
                            Intent intent2 = new Intent();
                            intent2.setClass(context, WidgetActivity.class);
                            gotoActivity(intent2);
                            textOutput.setText("顯示廚房燈報表");
                            break;
                        default:
                            textOutput.setText("查無此裝置");
                            break;
                    }
                    break;
                default:
                    textOutput.setText("指令錯誤");
                    break;
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void onStartRecord() {
        // TODO Auto-generated method stub
        Log.e(TAG, "main onStartRecord");
    }

    @Override
    public void onStopRecord() {
        // TODO Auto-generated method stub
        Log.e(TAG, "main onStopRecord");
        textOutput.setText("辨識中...");
        recordStatus = false;
        buttonRecord.setText("Record");
        itriRecord.stopRecord();
    }

    @Override
    public void onVadNoVoice() {
        // TODO Auto-generated method stub
        Log.e(TAG, "main onVadNoVoice");
        itriRecord.stopRecord();
    }

    @Override
    public void onVolume(double arg0) {
        // TODO Auto-generated method stub
    }

    public String deviceCheck(String s) {
        String deviceName = "";
        if (s.contains("Device_LivingRoom")) {
            deviceName = "LivingRoom";
        } else if (s.contains("Device_Kitchen")) {
            deviceName = "Kitchen";
        }
        return deviceName;
    }

    public void devControl(String action){
        for (Widget object: WidgetPresenter.getInstance().widgetList) {
            if(object.getAlias().equals("Outlet")){
                dev_ID = object.getDev_id();
                Log.e(TAG, "id:" + object.getDev_id());
                DevicePresenter.getInstance().controlDevice(object.getDev_id(), action, new ApiCallback() {
                    @Override
                    protected void onSuccess(ApiResult apiResult) {
                        Log.e(TAG, "status:" + apiResult.getStatus());
                        textOutput.setText("success");
                    }

                    @Override
                    protected void onFail(ApiResult apiResult) {
                        Log.e(TAG, "status:" + apiResult.getStatus());
                        textOutput.setText("fail");
                    }
                });
            } else{
                textOutput.setText("waiting");
            }
        }
    }


}
