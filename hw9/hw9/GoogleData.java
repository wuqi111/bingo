package com.example.wuqi.hw9;

public class GoogleData {


    private String profile_photo_url_google;
    private String author_name_google;
    private String rating_google;
    private String time_google;
    private  String text_google;

    public GoogleData(String profile_photo_url_google, String author_name_google, String rating_google,String time_google,String text_google){
        this.profile_photo_url_google=profile_photo_url_google;
        this.author_name_google=author_name_google;
        this.rating_google=rating_google;
        this.time_google=time_google;
        this.text_google=text_google;
    }

    public String getProfile_photo_url_google(){
        return profile_photo_url_google;
    }

    public void setProfile_photo_url_google(String profile_photo_url_google){
        this.profile_photo_url_google=profile_photo_url_google;
    }

    public String getAuthor_name_google() {
        return author_name_google;
    }

    public void setAuthor_name_google(String author_name_google){
        this.author_name_google=author_name_google;
    }

    public String getRating_google(){
        return rating_google;
    }

    public void setRating_google(String rating_google){
        this.rating_google=rating_google;
    }

    public String getTime_google(){
        return time_google;
    }

    public void setTime_google(String time_google){
        this.time_google=time_google;
    }

    public String getText_google(){
        return text_google;
    }

    public void setText_google(String text_google){
        this.text_google=text_google;
    }

}
