package com.example.selectaddress;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;

/**
 * Created by Administrator on 2016/1/14.
 */
public class PopupWindowActivity extends Activity implements OnWheelChangedListener, View.OnClickListener {


    private TextView mBtnConfirm;
    private WheelView mIdProvince;
    private WheelView mIdCity;
    private WheelView mIdDistrict;
    private List<ProvinceBean> provinceList = null;
    private String[] mProvinceDatas;
    /**
     * key - 省 value - 市
     */
    protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    /**
     * key - 市 values - 区
     */
    protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();

    /**
     * key - 区 values - 邮编
     */
    protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();
    /**
     * 当前市区的ID
     */
    protected Map<String, String> mIdMap = new HashMap<String, String>();
    /**
     * 当前市区的ID
     */
    protected Map<String, String> mUidMap = new HashMap<String, String>();

    /**
     * 初始化当前显示区域
     */
    private int provinceItem = 0;
    private int cityItem = 0;
    private int countyItem = 0;

    /**
     * 当前区域ID
     */
    //县级ID
    protected String mCurrentZipCode = "";
    //市级ID
    protected String mCurrentId = "";
    //省级ID
    protected String mCuurrentUid = "";

    /**
     * 当前省的名称
     */
    protected String mCurrentProviceName;
    /**
     * 当前市的名称
     */
    protected String mCurrentCityName;
    /**
     * 当前区的名称
     */
    protected String mCurrentDistrictName = "";
    private TextView mTextViewBackBTn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_mainwheel);
        initView();
        initProvinceDatas();//解析省市区的XML数据
        setUpListener(); //设置变化监听
        setUpData();
    }

    /*
        更新数据
     */
    private void setUpData() {
        initDefaultDatas();
        mIdProvince.setViewAdapter(new ArrayWheelAdapter<String>(this, mProvinceDatas));
        //初始化可见条目
        mIdProvince.setVisibleItems(7);
        mIdCity.setVisibleItems(7);
        mIdDistrict.setVisibleItems(7);
        updateCities();
        updateAreas();
        updateDis();

    }

    /*
    * 更新区
    * */
    private void updateDis() {
        countyItem = mIdDistrict.getCurrentItem();
        mCurrentDistrictName = "";
        mCurrentZipCode = "";
        if (mDistrictDatasMap.get(mCurrentCityName).length != 0) {
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[countyItem];
            mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
        }
    }

    /*
    * 更新地
    * */
    private void updateAreas() {
        cityItem = mIdCity.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[cityItem];
        mCurrentId = mIdMap.get(mCurrentCityName);
        String[] areas = mDistrictDatasMap.get(mCurrentCityName);
        if (areas == null || areas.length == 0) {
            areas = new String[]{""};
        }
        mIdDistrict.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
        mIdDistrict.setCurrentItem(0);
    }

    /*
    * 更新省
    * */
    private void updateCities() {
        provinceItem = mIdProvince.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[provinceItem];
        mCuurrentUid = mUidMap.get(mCurrentProviceName);
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null || cities.length == 0) {
            cities = new String[]{""};
        }
        mIdCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
        mIdCity.setCurrentItem(0);
        updateAreas();
    }

    /*
        设置初始默认地区
    * */
    private void initDefaultDatas() {
        if (provinceList != null && !provinceList.isEmpty()) {
            mCurrentProviceName = provinceList.get(0).getName();
            List<CityBean> cityList = provinceList.get(0).getCityList();
            if (cityList != null && !cityList.isEmpty()) {
                mCurrentCityName = cityList.get(0).getName();
                List<DistrictBean> districtList = cityList.get(0).getDistrictList();
                //判断所在的市是否有下级
                if (districtList != null && !districtList.isEmpty()) {
                    mCurrentDistrictName = districtList.get(0).getName();
                }
            }
        }
    }

    private void setUpListener() {
        mIdProvince.addChangingListener(this);
        mIdCity.addChangingListener(this);
        mIdDistrict.addChangingListener(this);

    }

    private void initProvinceDatas() {

        AssetManager asset = this.getAssets();
        try {
            if (provinceList == null || provinceList.size() == 0) {
                provinceList = JsonRegionHandler.getInstance().getDataList();
            }
            mProvinceDatas = new String[provinceList.size()];
            for (int i = 0; i < provinceList.size(); i++) {
                // 遍历所有省的数据
                mProvinceDatas[i] = provinceList.get(i).getName();
                List<CityBean> cityList = provinceList.get(i).getCityList();
                String[] cityNames = new String[cityList.size()];
                mUidMap.put(provinceList.get(i).getName(), provinceList.get(i).getUid());
                for (int j = 0; j < cityList.size(); j++) {
                    // 遍历省下面的所有市的数据
                    cityNames[j] = cityList.get(j).getName();
                    List<DistrictBean> districtList = cityList.get(j).getDistrictList();
                    String[] distrinctNameArray = new String[districtList.size()];
                    DistrictBean[] distrinctArray = new DistrictBean[districtList.size()];
                    mIdMap.put(cityList.get(j).getName(), cityList.get(j).getId());
                    for (int k = 0; k < districtList.size(); k++) {
                        // 遍历市下面所有区/县的数据
                        DistrictBean districtModel = new DistrictBean(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        // 区/县对于的邮编，保存到mZipcodeDatasMap
                        mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());

                        distrinctArray[k] = districtModel;
                        distrinctNameArray[k] = districtModel.getName();
                    }
                    // 市-区/县的数据，保存到mDistrictDatasMap
                    mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
                }
                // 省-市的数据，保存到mCitisDatasMap
                mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }

    }

    private void initView() {
        mTextViewBackBTn = (TextView) findViewById(R.id.TextViewBackBTn);
        mBtnConfirm = (TextView) findViewById(R.id.btn_confirm);
        mBtnConfirm.setOnClickListener(this);
        mIdProvince = (WheelView) findViewById(R.id.id_province);
        mIdCity = (WheelView) findViewById(R.id.id_city);
        mIdDistrict = (WheelView) findViewById(R.id.id_district);
    }

    /*
    *   WheelView改变的回调
    * */
    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == mIdProvince) {
            updateCities();
            updateDis();
            //Toast.makeText(this, mCurrentProviceName, Toast.LENGTH_SHORT).show();
        } else if (wheel == mIdCity) {
            updateAreas();
            updateDis();
            //Toast.makeText(this, mCurrentCityName, Toast.LENGTH_SHORT).show();
        } else if (wheel == mIdDistrict) {
            updateDis();
            //Toast.makeText(this, mCurrentDistrictName, Toast.LENGTH_SHORT).show();
        }
    }

    /*
    *   确定 点击时间
    * */
    @Override
    public void onClick(View v) {
        SharedPreferences sp = this.getSharedPreferences("address", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("mCurrentProviceName", mCurrentProviceName).putString("mCurrentCityName",
                mCurrentCityName).putString("mCurrentDistrictName",mCurrentDistrictName)
                .putString("flag","meili").commit();
        finish();
        //Toast.makeText(this, "选择确定,结束当前的页面", Toast.LENGTH_SHORT).show();
    }
}
