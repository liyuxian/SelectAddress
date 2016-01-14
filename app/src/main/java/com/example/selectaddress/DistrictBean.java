package com.example.selectaddress;

/**
 * Created by Administrator on 2016/1/14.
 */
public class DistrictBean {

    private String name;
    private String zipcode;


    public DistrictBean() {
        super();
    }

    public DistrictBean(String name, String zipcode) {
        super();
        this.name = name;
        this.zipcode = zipcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DistrictModel [name=" + name + ", zipcode=" + zipcode + "]";
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

}
