package com.sendbird.android.sample.user;

public class User {
    public String fname,lname,mname,birthday,gender,street,city,province,server,joined,rating_total,rooms_total,reports;
    public String depTest, user;
    //public Uri filepath;



    public User(String fname, String lname, String mname,String birthday, String gender, String street, String city, String province,
                String user, String depTest, String server, String joined, String rating_total, String rooms_total, String reports) {
        this.fname = fname;
        this.lname = lname;
        this.mname = mname;
        this.birthday = birthday;
        this.gender = gender;
        this.street = street;
        this.city = city;
        this.province = province;
        this.user = user;
        this.server = server;
        this.joined = joined;
        this.depTest = depTest;
        this.rating_total = rating_total;
        this.rooms_total = rooms_total;
        this.reports = reports;
    }


    public String getBirthday() { return birthday; }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getMname() {
        return mname;
    }

    public String getGender() {
        return gender;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public String getUser() {
        return user;
    }

    public String getServer() {
        return server;
    }

    public String getJoined() {
        return joined;
    }

    public String getDepTest() { return depTest;
    }
    public String getRating_total() {
        return rating_total;
    }

    public String getRooms_total() {
        return rooms_total;
    }

    public String getReports() {
        return reports;
    }


}


