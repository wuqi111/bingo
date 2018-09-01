package com.example.wuqi.hw9;

public class FavoriteData {


    private String icon_favorite;
    private String name_favorite;
    private String address_favorite;

    public FavoriteData(String icon_favorite, String name_favorite, String address_favorite){
        this.icon_favorite= icon_favorite;
        this.name_favorite= name_favorite;
        this.address_favorite=address_favorite;
    }

    public String getIcon_favorite(){
        return icon_favorite;
    }

    public void setIcon_favorite(String icon_favorite){
        this.icon_favorite=icon_favorite;
    }

    public String getAddress_favorite() {
        return address_favorite;
    }

    public void setAddress_favorite(String address_favorite){
        this.address_favorite=address_favorite;
    }

    public String getName_favorite(){
        return name_favorite;
    }

    public void setName_favorite(String name_favorite){
        this.name_favorite=name_favorite;
    }
}
