package com.sendbird.android.sample.user;

import java.util.ArrayList;

public class Answers {
    private ArrayList<Integer> list = new ArrayList<>();


    //Set<String> set = myScores.getStringSet("key", null);
    public Answers(){

    }

    public int pushAnswer(int key){
        list.add(key);
        return 0;
    }

    public ArrayList<Integer> getAnswer(){
        return list;
    }

}
