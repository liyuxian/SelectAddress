package com.example.selectaddress;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class JsonRegionHandler {

    private final String TAG = "JsonRegionHandler";

    private List<ProvinceBean> provinceList = new ArrayList<ProvinceBean>();

    private static class JsonRegionHandlerInstance {
        private final static JsonRegionHandler mCcClientConfig = new JsonRegionHandler();
    }

    public static JsonRegionHandler getInstance() {
        return JsonRegionHandlerInstance.mCcClientConfig;
    }

    public List<ProvinceBean> getDataList() {
        if (provinceList != null && provinceList.size() > 0) {
            return provinceList;
        }
        String districtJson = getJsonData("region.txt");
        try {
            JSONObject job = new JSONObject(districtJson);
            JSONArray list = job.getJSONArray("list");
            for (int i = 0; i < list.length(); i++) {
                JSONObject listObj = list.getJSONObject(i);
                ProvinceBean pb = new ProvinceBean();
                pb.setUid(listObj.optString("Id"));
                pb.setName(listObj.optString("Name"));
                List<CityBean> cityList = new ArrayList<CityBean>();
                JSONArray cityJsonList = listObj.getJSONArray("CityList");
                for (int j = 0; j < cityJsonList.length(); j++) {
                    JSONObject listCityObj = cityJsonList.getJSONObject(j);
                    CityBean cb = new CityBean();
                    cb.setId(listCityObj.optString("Id"));
                    cb.setName(listCityObj.optString("Name"));

                    List<DistrictBean> districtList = new ArrayList<DistrictBean>();
                    JSONArray districJsonList = listCityObj.getJSONArray("DistrictList");
                    for (int n = 0; n < districJsonList.length(); n++) {
                        JSONObject listDistricObj = districJsonList.getJSONObject(n);
                        DistrictBean db = new DistrictBean();
                        db.setName(listDistricObj.optString("Name"));
                        db.setZipcode(listDistricObj.optString("Id"));
                        districtList.add(db);
                    }
                    cb.setDistrictList(districtList);
                    cityList.add(cb);
                }
                pb.setCityList(cityList);
                provinceList.add(pb);
            }

        } catch (JSONException e) {
           Log.d("JsonRegionHandler", e.toString());
        }
        return provinceList;
    }

    private String getJsonData(String path) {
        String jsonData;
        try {
            InputStream is =this.getClass().getClassLoader().getResourceAsStream("assets/" + path);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonData = new String(buffer, "UTF-8");
        } catch (IOException e) {
            // Should never happen!  
            throw new RuntimeException(e);
        }
        return jsonData;
    }
}
