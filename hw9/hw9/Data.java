package com.example.wuqi.hw9;

public class Data {

    private String icon;
    private String name;
    private String address;

    public Data(String icon, String name, String address){
        this.icon=icon;
        this.name=name;
        this.address=address;
    }

    public String getIcon(){
        return icon;
    }

    public void setIcon(String icon){
        this.icon=icon;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address){
        this.address=address;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name=name;
    }

}
