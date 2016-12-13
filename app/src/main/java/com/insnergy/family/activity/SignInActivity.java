package com.insnergy.family.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.insnergy.family.R;
import com.insnergy.family.sdk.SdkManager;
import com.insnergy.family.sdk.domain.ApiResult;
import com.insnergy.family.sdk.presenters.ApiCallback;
import com.insnergy.family.sdk.presenters.SignPresenter;
import com.insnergy.family.sdk.utils.StringUtils;
import com.insnergy.family.sdk.utils.ValidateUtils;

import sttrestsample.STTActivity;
public class SignInActivity extends AbstractActivity {

    private static final String EMAIL = "iftest@insnergy.com";
    private static final String PASSWORD = "aaaa1111";
    private EditText txtEmail;
    private EditText txtPassword;
    private Button btnSignIn;
    private Context context;
    private LinearLayout layoutFoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = SignInActivity.this;
        SdkManager.getInstance().initSdk(context);
        initView();
    }

    public void signIn(View view) {
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();
        if (StringUtils.isNotEmpty(email) && ValidateUtils.validateEmail(email) && StringUtils.isNotEmpty(password) && ValidateUtils.validatePassword(password)) {
            showProcessView(layoutFoot);
            SignPresenter.getInstance().checkActivation(email, new ApiCallback() {
                @Override
                protected void onSuccess(ApiResult apiResult) {
                    if (apiResult.getUser().isEnabled()) {
                        authorize();
                    } else {
                        hideProcessView(layoutFoot);
                        Toast.makeText(context, "User isn't Enabled", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                protected void onFail(ApiResult result) {
                    hideProcessView(layoutFoot);
                }
            });
        } else {
            Toast.makeText(context, "Email or Password Error", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        layoutFoot = (LinearLayout) findViewById(R.id.layoutFunctionMenu);
        layoutFoot.setVisibility(View.GONE);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtEmail.setText(EMAIL);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtPassword.setText(PASSWORD);
        btnSignIn = (Button) findViewById(R.id.btnSigIn);
        final TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    btnSignIn.setEnabled(true);
                } else {
                    btnSignIn.setEnabled(false);
                }
            }
        };
        txtEmail.addTextChangedListener(textWatcher);
        txtPassword.addTextChangedListener(textWatcher);
    }

    private void authorize() {
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();
        SignPresenter.getInstance().authorize(email, password, true, new ApiCallback() {
            @Override
            protected void onSuccess(ApiResult apiResult) {
                /*Widget*/

                /*工研院語音轉檔*/
                Intent intent = new Intent();
                intent.setClass(context, STTActivity.class);
                gotoActivity(intent);
            }

            @Override
            protected void onFail(ApiResult result) {
                hideProcessView(layoutFoot);
            }
        });
    }
}
