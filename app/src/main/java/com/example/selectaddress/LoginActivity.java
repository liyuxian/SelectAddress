package com.example.selectaddress;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/1/13.
 */
public class LoginActivity extends Activity implements View.OnClickListener, TextWatcher {

    private EditText mEditText;
    private EditText mEditText2;
    private String mUserName;
    private String mPassWorld;
    private TextView mSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        initView();
    }

    /*
    *   获取数据
    * */
    private void initData() {
        mUserName = mEditText.getText().toString().trim();
        mPassWorld = mEditText2.getText().toString().trim();
    }

    /*
    *   初始化 控件
    * */
    private void initView() {
        mEditText = (EditText) findViewById(R.id.editText);
        mEditText2 = (EditText) findViewById(R.id.editText2);
        mEditText.addTextChangedListener(this);
        mEditText2.addTextChangedListener(this);
        mSubmit = (TextView) findViewById(R.id.textView);
        mSubmit.setOnClickListener(this);
    }

    /*
    *   登录点击时间
     *  */
    @Override
    public void onClick(View v) {
        initData();
        //Toast.makeText(this, mUserName+"***8"+mPassWorld, Toast.LENGTH_SHORT).show();
        if ("meili".equals(mUserName) && mUserName.equals(mPassWorld)) {
            // 用户名字为 meili 且用户名和密码一致
            startActivity(new Intent(LoginActivity.this, PopupWindowActivity.class));
            finish();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //Toast.makeText(this, "改变前", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //Toast.makeText(this, "正在改变", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void afterTextChanged(Editable s) {
       // Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        if ("meili".equals(s.toString())) {
            Toast.makeText(this, "美丽最最漂亮了!", Toast.LENGTH_SHORT).show();
        }
    }
}
