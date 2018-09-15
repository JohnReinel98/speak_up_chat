package com.sendbird.android.sample.user;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.sendbird.android.sample.R;

public class UserQuestion extends AppCompatActivity {
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private QuestionManager questionManager;
    private int[] layouts;
    private TextView[] dots;
    private LinearLayout dotsLayout;
    private Button btnNext;
    private RadioButton rbtnFirst1,rbtnSecond1,rbtnThird1,rbtnFourth1;
    private RadioButton rbtnFirst2,rbtnSecond2,rbtnThird2,rbtnFourth2;
    private RadioButton rbtnFirst3,rbtnSecond3,rbtnThird3,rbtnFourth3;
    private RadioButton rbtnFirst4,rbtnSecond4,rbtnThird4,rbtnFourth4;
    private RadioButton rbtnFirst5,rbtnSecond5,rbtnThird5,rbtnFourth5;
    private RadioButton rbtnFirst6,rbtnSecond6,rbtnThird6,rbtnFourth6;
    private RadioButton rbtnFirst7,rbtnSecond7,rbtnThird7,rbtnFourth7;
    private RadioButton rbtnFirst8,rbtnSecond8,rbtnThird8,rbtnFourth8;
    private RadioButton rbtnFirst9,rbtnSecond9,rbtnThird9,rbtnFourth9;
    private RadioButton rbtnFirst10,rbtnSecond10,rbtnThird10,rbtnFourth10;
    public static final String SHARED_PREFS = "sharedPrefs";
    private int btnCheckCtr;
    public int setAnsScore;
    public int answ1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        questionManager = new QuestionManager(this);

        getSupportActionBar().hide();
        if(!questionManager.checked()){
            questionManager.setFirst(false);
            startActivity(new Intent(UserQuestion.this, UserHome.class));
            finish();
        }

        if(Build.VERSION.SDK_INT>21){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_user_question);

        viewPager = findViewById(R.id.viewpager);
        dotsLayout=findViewById(R.id.layoutDots);
        btnNext=findViewById(R.id.btnNext);





        int first = R.layout.activity_question_1;

        layouts = new int[]{R.layout.activity_question_1,R.layout.activity_question_2,R.layout.activity_question_3,
                R.layout.activity_question_4,R.layout.activity_question_5,R.layout.activity_question_6,R.layout.activity_question_7
                ,R.layout.activity_question_8,R.layout.activity_question_9,R.layout.activity_question_10};


        changeStatusBarColor();



        //editor.putStringSet("key", answerList);

        //Set<String> set = myScores.getStringSet("key", null);


        viewPagerAdapter = new ViewPagerAdapter();
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(viewListener);

        rbtnFirst1 = findViewById(R.id.rbtnFirst1);
        rbtnSecond1 = findViewById(R.id.rbtnSecond1);
        rbtnThird1 = findViewById(R.id.rbtnThird1);
        rbtnFourth1 = findViewById(R.id.rbtnFourh1);

        rbtnFirst2 = findViewById(R.id.rbtnFirst2);
        rbtnSecond2 = findViewById(R.id.rbtnSecond2);
        rbtnThird2 = findViewById(R.id.rbtnThird2);
        rbtnFourth2 = findViewById(R.id.rbtnFourh2);

        rbtnFirst3 = findViewById(R.id.rbtnFirst3);
        rbtnSecond3 = findViewById(R.id.rbtnSecond3);
        rbtnThird3 = findViewById(R.id.rbtnThird3);
        rbtnFourth3 = findViewById(R.id.rbtnFourh3);

        rbtnFirst4 = findViewById(R.id.rbtnFirst4);
        rbtnSecond4 = findViewById(R.id.rbtnSecond4);
        rbtnThird4 = findViewById(R.id.rbtnThird4);
        rbtnFourth4 = findViewById(R.id.rbtnFourh4);

        rbtnFirst5 = findViewById(R.id.rbtnFirst5);
        rbtnSecond5 = findViewById(R.id.rbtnSecond5);
        rbtnThird5 = findViewById(R.id.rbtnThird5);
        rbtnFourth5 = findViewById(R.id.rbtnFourh5);

        rbtnFirst6 = findViewById(R.id.rbtnFirst6);
        rbtnSecond6 = findViewById(R.id.rbtnSecond6);
        rbtnThird6 = findViewById(R.id.rbtnThird6);
        rbtnFourth6 = findViewById(R.id.rbtnFourh6);

        rbtnFirst7 = findViewById(R.id.rbtnFirst7);
        rbtnSecond7 = findViewById(R.id.rbtnSecond7);
        rbtnThird7 = findViewById(R.id.rbtnThird7);
        rbtnFourth7 = findViewById(R.id.rbtnFourh7);

        rbtnFirst8 = findViewById(R.id.rbtnFirst8);
        rbtnSecond8 = findViewById(R.id.rbtnSecond8);
        rbtnThird8 = findViewById(R.id.rbtnThird8);
        rbtnFourth8 = findViewById(R.id.rbtnFourh8);

        rbtnFirst9 = findViewById(R.id.rbtnFirst9);
        rbtnSecond9 = findViewById(R.id.rbtnSecond9);
        rbtnThird9 = findViewById(R.id.rbtnThird9);
        rbtnFourth9 = findViewById(R.id.rbtnFourh9);

        rbtnFirst10 = findViewById(R.id.rbtnFirst10);
        rbtnSecond10 = findViewById(R.id.rbtnSecond10);
        rbtnThird10 = findViewById(R.id.rbtnThird10);
        rbtnFourth10 = findViewById(R.id.rbtnFourh10);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = getItem(+1);
                if (current<layouts.length){
                    viewPager.setCurrentItem(current);
                }else{
                    Intent i = new Intent(UserQuestion.this, UserHome.class);
                    i.putExtra("openMeter", true);
                    startActivity(i);
                    finish();
                }
            }
        });
    }


    public void answerClick(View view) {
        Answers answers = new Answers();
        SharedPreferences sharedPreferences = getSharedPreferences("PREFS", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rbtnFirst1:
                if (checked)
                    setAnsScore = 0;
                    editor.putInt("ans1", setAnsScore);
                    editor.apply();
                break;
            case R.id.rbtnSecond1:
                if (checked)
                    setAnsScore = 25;
                    editor.putInt("ans1", setAnsScore);
                    editor.apply();
                break;
            case R.id.rbtnThird1:
                if (checked)
                    setAnsScore = 75;
                    editor.putInt("ans1", setAnsScore);
                    editor.apply();
                break;
            case R.id.rbtnFourh1:
                if (checked)
                    setAnsScore = 100;
                    editor.putInt("ans1", setAnsScore);
                    editor.apply();
                break;
                //2nd ques
            case R.id.rbtnFirst2:
                if (checked)
                    setAnsScore = 0;
                editor.putInt("ans2", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnSecond2:
                if (checked)
                    setAnsScore = 25;
                editor.putInt("ans2", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnThird2:
                if (checked)
                    setAnsScore = 75;
                editor.putInt("ans2", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnFourh2:
                if (checked)
                    setAnsScore = 100;
                editor.putInt("ans2", setAnsScore);
                editor.apply();
                break;

            //3rd ques
            case R.id.rbtnFirst3:
                if (checked)
                    setAnsScore = 0;
                editor.putInt("ans3", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnSecond3:
                if (checked)
                    setAnsScore = 25;
                editor.putInt("ans3", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnThird3:
                if (checked)
                    setAnsScore = 75;
                editor.putInt("ans3", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnFourh3:
                if (checked)
                    setAnsScore = 100;
                editor.putInt("ans3", setAnsScore);
                editor.apply();
                break;

            //4th ques
            case R.id.rbtnFirst4:
                if (checked)
                    setAnsScore = 0;
                editor.putInt("ans4", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnSecond4:
                if (checked)
                    setAnsScore = 25;
                editor.putInt("ans4", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnThird4:
                if (checked)
                    setAnsScore = 75;
                editor.putInt("ans4", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnFourh4:
                if (checked)
                    setAnsScore = 100;
                editor.putInt("ans4", setAnsScore);
                editor.apply();
                break;

            //5th ques
            case R.id.rbtnFirst5:
                if (checked)
                    setAnsScore = 0;
                editor.putInt("ans5", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnSecond5:
                if (checked)
                    setAnsScore = 25;
                editor.putInt("ans5", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnThird5:
                if (checked)
                    setAnsScore = 75;
                editor.putInt("ans5", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnFourh5:
                if (checked)
                    setAnsScore = 100;
                editor.putInt("ans5", setAnsScore);
                editor.apply();
                break;

            //6th ques
            case R.id.rbtnFirst6:
                if (checked)
                    setAnsScore = 0;
                editor.putInt("ans6", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnSecond6:
                if (checked)
                    setAnsScore = 25;
                editor.putInt("ans6", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnThird6:
                if (checked)
                    setAnsScore = 75;
                editor.putInt("ans6", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnFourh6:
                if (checked)
                    setAnsScore = 100;
                editor.putInt("ans6", setAnsScore);
                editor.apply();
                break;

            //7th ques
            case R.id.rbtnFirst7:
                if (checked)
                    setAnsScore = 0;
                editor.putInt("ans7", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnSecond7:
                if (checked)
                    setAnsScore = 25;
                editor.putInt("ans7", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnThird7:
                if (checked)
                    setAnsScore = 75;
                editor.putInt("ans7", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnFourh7:
                if (checked)
                    setAnsScore = 100;
                editor.putInt("ans7", setAnsScore);
                editor.apply();
                break;

            //8th ques
            case R.id.rbtnFirst8:
                if (checked)
                    setAnsScore = 0;
                editor.putInt("ans8", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnSecond8:
                if (checked)
                    setAnsScore = 25;
                editor.putInt("ans8", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnThird8:
                if (checked)
                    setAnsScore = 75;
                editor.putInt("ans8", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnFourh8:
                if (checked)
                    setAnsScore = 100;
                editor.putInt("ans8", setAnsScore);
                editor.apply();
                break;

            //9th ques
            case R.id.rbtnFirst9:
                if (checked)
                    setAnsScore = 0;
                editor.putInt("ans9", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnSecond9:
                if (checked)
                    setAnsScore = 25;
                editor.putInt("ans9", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnThird9:
                if (checked)
                    setAnsScore = 75;
                editor.putInt("ans9", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnFourh9:
                if (checked)
                    setAnsScore = 100;
                editor.putInt("ans9", setAnsScore);
                editor.apply();
                break;

            //10th ques
            case R.id.rbtnFirst10:
                if (checked)
                    setAnsScore = 0;
                editor.putInt("ans10", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnSecond10:
                if (checked)
                    setAnsScore = 25;
                editor.putInt("ans10", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnThird10:
                if (checked)
                    setAnsScore = 75;
                editor.putInt("ans10", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnFourh10:
                if (checked)
                    setAnsScore = 100;
                editor.putInt("ans10", setAnsScore);
                editor.apply();
                break;
        }
    }

    private void addBottomDots(int position){
        dots = new TextView[layouts.length];
        int[] colorActive = getResources().getIntArray(R.array.dotActive);
        int[] colorInactive = getResources().getIntArray(R.array.dotInactive);
        dotsLayout.removeAllViews();
        for(int i=0;i<dots.length;i++){
            dots[i]=new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorInactive[position]);
            dotsLayout.addView(dots[i]);
        }
        if(dots.length>0){
            dots[position].setTextColor(colorActive[position]);
        }
    }

    private int getItem(int i){
        return viewPager.getCurrentItem() + 1;
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
            if (position==layouts.length-1 ){
                btnNext.setText("FINISH");
            }
            else{
                btnNext.setText("NEXT");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void changeStatusBarColor(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);

        }
    }

    public class ViewPagerAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = layoutInflater.inflate(layouts[position],container,false);
            container.addView(v);
            return v;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            View v = (View)object;
            container.removeView(v);
        }
    }
}
