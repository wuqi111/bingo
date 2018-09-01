package com.example.wuqi.hw9;

public class Information {

    private String information_name;
    private String information_value;


    public Information(String information_name, String information_value ){
        this.information_name=information_name;
        this.information_value=information_value;

    }

    public String getInformation_name(){
        return information_name;
    }

    public void setInformation_name(String information_name){
        this.information_name=information_name;
    }

    public String getInformation_value() {
        return information_value;
    }

    public void setInformation_value(String information_value){
        this.information_value=information_value;
    }

}
