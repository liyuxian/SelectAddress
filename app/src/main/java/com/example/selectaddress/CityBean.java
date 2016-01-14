package com.example.selectaddress;

import java.util.List;

/**
 * Created by Administrator on 2016/1/14.
 */
public class CityBean {
    private String name;
    private String id;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private List<DistrictBean> districtList;

    public CityBean() {
        super();
    }

    public CityBean(String name, List<DistrictBean> districtList,String mId) {
        super();
        this.id = mId;
        this.name = name;
        this.districtList = districtList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DistrictBean> getDistrictList() {
        return districtList;
    }

    public void setDistrictList(List<DistrictBean> districtList) {
        this.districtList = districtList;
    }

    @Override
    public String toString() {
        return "CityModel [name=" + name + ", id=" + id + ", districtList="
                + districtList + "]";
    }


}
