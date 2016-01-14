package com.example.selectaddress;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class SelectActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mButton;
    private TextView mTextView8,mTextView6,mTextView7;
    private String mCurrentProviceName,mCurrentCityName,mCurrentDistrictName;
    private String mFlag;
    private Button mClear;
    private SharedPreferences mSp = null;


    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initView();
        //initData();
    }
    /*
    * 初始化 数据 展示
    * */
    private void initData() {
        if (mSp==null){
            mSp = this.getSharedPreferences("address", Context.MODE_PRIVATE);
        }
        mCurrentProviceName = mSp.getString("mCurrentProviceName", "");
        mFlag = mSp.getString("flag", "");
        // Toast.makeText(this, mFlag, Toast.LENGTH_SHORT).show();
        mCurrentCityName = mSp.getString("mCurrentCityName", "");
        mCurrentDistrictName = mSp.getString("mCurrentDistrictName", "");
        if ("meili".equals(mFlag)){ // 拿到标记表示已经选择好
            if (mCurrentProviceName!=null){
                mTextView8.setText(mCurrentProviceName);
            }
            if (mCurrentCityName!=null){
                mTextView6.setText(mCurrentCityName);
            }
            if (mCurrentDistrictName!=null){
                mTextView7.setText(mCurrentDistrictName);
            }
        }
    }
    /*
    * 初始化控件
    * */
    private void initView() {
        mButton = (Button) findViewById(R.id.bt_set);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击按钮跳转登录页面
                startActivity(new Intent(SelectActivity.this, LoginActivity.class));
            }
        });
        mClear = (Button) findViewById(R.id.bt_clear);
        mClear.setOnClickListener(this);
        mTextView6 = (TextView) findViewById(R.id.textView6);
        mTextView7 = (TextView) findViewById(R.id.textView7);
        mTextView8 = (TextView) findViewById(R.id.textView8);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_clear:
                mSp.edit().clear().commit();
                clearData();
                Toast.makeText(this, "已清除", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void clearData() {
        mTextView8.setText("");
        mTextView7.setText("");
        mTextView6.setText("");
    }
}
