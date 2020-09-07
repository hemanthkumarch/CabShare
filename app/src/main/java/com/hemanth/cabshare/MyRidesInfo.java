package com.hemanth.cabshare;

public class MyRidesInfo {
    private String date;
    private String dest;
    private String time;

    public MyRidesInfo() {
    }

    public MyRidesInfo(String date, String dest, String time) {
        this.date = date;
        this.dest = dest;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
