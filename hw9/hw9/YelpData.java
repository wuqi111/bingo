package com.example.wuqi.hw9;

public class YelpData {

    private String profile_photo_url_yelp;
    private String author_name_yelp;
    private String rating_yelp;
    private String time_yelp;
    private  String text_yelp;

    public YelpData(String profile_photo_url_yelp, String author_name_yelp, String rating_yelp,String time_yelp,String text_yelp){
        this.profile_photo_url_yelp=profile_photo_url_yelp;
        this.author_name_yelp=author_name_yelp;
        this.rating_yelp=rating_yelp;
        this.time_yelp=time_yelp;
        this.text_yelp=text_yelp;
    }

    public String getProfile_photo_url_yelp(){
        return profile_photo_url_yelp;
    }

    public void setProfile_photo_url_yelp(String profile_photo_url_yelp){
        this.profile_photo_url_yelp=profile_photo_url_yelp;
    }

    public String getAuthor_name_yelp() {
        return author_name_yelp;
    }

    public void setAuthor_name_yelp(String author_name_yelp){
        this.author_name_yelp=author_name_yelp;
    }

    public String getRating_yelp(){
        return rating_yelp;
    }

    public void setRating_yelp(String rating_yelp){
        this.rating_yelp=rating_yelp;
    }

    public String getTime_yelp(){
        return time_yelp;
    }

    public void setTime_yelp(String time_yelp){
        this.time_yelp=time_yelp;
    }

    public String getText_yelp(){
        return text_yelp;
    }

    public void setText_yelp(String text_yelp){
        this.text_yelp=text_yelp;
    }
}
