package com.example.wuqi.hw9;

public class Datasecond {


    private String nexticon;
    private String nextname;
    private String nextvicinity;

    public Datasecond(String nexticon, String nextname, String nextvicinity){
        this.nexticon=nexticon;
        this.nextname=nextname;
        this.nextvicinity=nextvicinity;
    }

    public String getNexticon(){
        return nexticon;
    }

    public void setNexticon(String nexticon){
        this.nexticon=nexticon;
    }

    public String getNextvicinity() {
        return nextvicinity;
    }

    public void setNextvicinity(String nextvicinity){
        this.nextvicinity=nextvicinity;
    }

    public String getNextname(){
        return nextname;
    }

    public void setNextname(String nextname){
        this.nextname=nextname;
    }
}
