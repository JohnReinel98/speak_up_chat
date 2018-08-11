package com.sendbird.android.sample.user;

public class User {
    public String fname,lname,mname,birthday,gender,street,city,province,server,joined;
    public int depTest;
    //public Uri filepath;



    public User(String fname, String lname, String mname,String birthday, String gender, String street, String city, String province,
                int depTest, String server, String joined) {
        this.fname = fname;
        this.lname = lname;
        this.mname = mname;
        this.birthday = birthday;
        this.gender = gender;
        this.street = street;
        this.city = city;
        this.province = province;
        this.server = server;
        this.joined = joined;
        this.depTest = depTest;
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

    public String getServer() {
        return server;
    }

    public String getJoined() {
        return joined;
    }

    public int getDepTest() { return depTest; }

    public int setDepTest(){ return this.depTest = depTest; }

}


