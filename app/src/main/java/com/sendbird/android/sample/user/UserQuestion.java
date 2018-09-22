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

    private RadioButton rbtnFirst1, rbtnSecond1, rbtnThird1, rbtnFourth1, rbtnFifth1;
    private RadioButton rbtnFirst2, rbtnSecond2, rbtnThird2, rbtnFourth2, rbtnFifth2;
    private RadioButton rbtnFirst3, rbtnSecond3, rbtnThird3, rbtnFourth3, rbtnFifth3;
    private RadioButton rbtnFirst4, rbtnSecond4, rbtnThird4;
    private RadioButton rbtnFirst5, rbtnSecond5, rbtnThird5;
    private RadioButton rbtnFirst6, rbtnSecond6, rbtnThird6;
    private RadioButton rbtnFirst7, rbtnSecond7, rbtnThird7, rbtnFourth7, rbtnFifth7;
    private RadioButton rbtnFirst8, rbtnSecond8, rbtnThird8, rbtnFourth8, rbtnFifth8;
    private RadioButton rbtnFirst9, rbtnSecond9, rbtnThird9, rbtnFourth9, rbtnFifth9;
    private RadioButton rbtnFirst10, rbtnSecond10, rbtnThird10, rbtnFourth10, rbtnFifth10;
    private RadioButton rbtnFirst11, rbtnSecond11, rbtnThird11, rbtnFourth11, rbtnFifth11;
    private RadioButton rbtnFirst12, rbtnSecond12, rbtnThird12;
    private RadioButton rbtnFirst13, rbtnSecond13, rbtnThird13;
    private RadioButton rbtnFirst14, rbtnSecond14, rbtnThird14;
    private RadioButton rbtnFirst15, rbtnSecond15, rbtnThird15, rbtnFourth15, rbtnFifth15;
    private RadioButton rbtnFirst16, rbtnSecond16, rbtnThird16, rbtnFourth16;
    private RadioButton rbtnFirst17, rbtnSecond17, rbtnThird17;

    private int btnCheckCtr;
    public static final String SHARED_PREFS = "sharedPrefs";
    public int setAnsScore;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private QuestionManager questionManager;
    private int[] layouts;
    private TextView[] dots;
    private LinearLayout dotsLayout;
    private Button btnNext;
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
            if (position == layouts.length - 1) {
                btnNext.setText("FINISH");
            } else {
                btnNext.setText("NEXT");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        questionManager = new QuestionManager(this);

        getSupportActionBar().hide();
        if (!questionManager.checked()) {
            questionManager.setFirst(false);
            finish();
            startActivity(new Intent(UserQuestion.this, UserHome.class));
        }

        if (Build.VERSION.SDK_INT > 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_user_question);

        viewPager = findViewById(R.id.viewpager);
        dotsLayout = findViewById(R.id.layoutDots);
        btnNext = findViewById(R.id.btnNext);


        int first = R.layout.activity_question_1;

        layouts = new int[]{R.layout.activity_question_1, R.layout.activity_question_2, R.layout.activity_question_3,
                R.layout.activity_question_4, R.layout.activity_question_5, R.layout.activity_question_6, R.layout.activity_question_7
                , R.layout.activity_question_8, R.layout.activity_question_9, R.layout.activity_question_10, R.layout.activity_question_11,
                R.layout.activity_question_12, R.layout.activity_question_13, R.layout.activity_question_14, R.layout.activity_question_15,
                R.layout.activity_question_16, R.layout.activity_question_17};


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
        rbtnFifth1 = findViewById(R.id.rbtnFifth1);

        rbtnFirst2 = findViewById(R.id.rbtnFirst2);
        rbtnSecond2 = findViewById(R.id.rbtnSecond2);
        rbtnThird2 = findViewById(R.id.rbtnThird2);
        rbtnFourth2 = findViewById(R.id.rbtnFourh2);
        rbtnFifth2 = findViewById(R.id.rbtnFifth2);

        rbtnFirst3 = findViewById(R.id.rbtnFirst3);
        rbtnSecond3 = findViewById(R.id.rbtnSecond3);
        rbtnThird3 = findViewById(R.id.rbtnThird3);
        rbtnFourth3 = findViewById(R.id.rbtnFourh3);
        rbtnFifth1 = findViewById(R.id.rbtnFifth3);

        rbtnFirst4 = findViewById(R.id.rbtnFirst4);
        rbtnSecond4 = findViewById(R.id.rbtnSecond4);
        rbtnThird4 = findViewById(R.id.rbtnThird4);

        rbtnFirst5 = findViewById(R.id.rbtnFirst5);
        rbtnSecond5 = findViewById(R.id.rbtnSecond5);
        rbtnThird5 = findViewById(R.id.rbtnThird5);

        rbtnFirst6 = findViewById(R.id.rbtnFirst6);
        rbtnSecond6 = findViewById(R.id.rbtnSecond6);
        rbtnThird6 = findViewById(R.id.rbtnThird6);

        rbtnFirst7 = findViewById(R.id.rbtnFirst7);
        rbtnSecond7 = findViewById(R.id.rbtnSecond7);
        rbtnThird7 = findViewById(R.id.rbtnThird7);
        rbtnFourth7 = findViewById(R.id.rbtnFourh7);
        rbtnFifth1 = findViewById(R.id.rbtnFifth7);

        rbtnFirst8 = findViewById(R.id.rbtnFirst8);
        rbtnSecond8 = findViewById(R.id.rbtnSecond8);
        rbtnThird8 = findViewById(R.id.rbtnThird8);
        rbtnFourth8 = findViewById(R.id.rbtnFourh8);
        rbtnFifth1 = findViewById(R.id.rbtnFifth8);

        rbtnFirst9 = findViewById(R.id.rbtnFirst9);
        rbtnSecond9 = findViewById(R.id.rbtnSecond9);
        rbtnThird9 = findViewById(R.id.rbtnThird9);
        rbtnFourth9 = findViewById(R.id.rbtnFourh9);
        rbtnFifth1 = findViewById(R.id.rbtnFifth9);

        rbtnFirst10 = findViewById(R.id.rbtnFirst10);
        rbtnSecond10 = findViewById(R.id.rbtnSecond10);
        rbtnThird10 = findViewById(R.id.rbtnThird10);
        rbtnFourth10 = findViewById(R.id.rbtnFourh10);
        rbtnFifth10 = findViewById(R.id.rbtnFifth10);

        rbtnFirst11 = findViewById(R.id.rbtnFirst11);
        rbtnSecond11 = findViewById(R.id.rbtnSecond11);
        rbtnThird11 = findViewById(R.id.rbtnThird11);
        rbtnFourth11 = findViewById(R.id.rbtnFourh11);
        rbtnFifth11 = findViewById(R.id.rbtnFifth11);

        rbtnFirst12 = findViewById(R.id.rbtnFirst12);
        rbtnSecond12 = findViewById(R.id.rbtnSecond12);
        rbtnThird12 = findViewById(R.id.rbtnThird12);

        rbtnFirst13 = findViewById(R.id.rbtnFirst13);
        rbtnSecond13 = findViewById(R.id.rbtnSecond13);
        rbtnThird13 = findViewById(R.id.rbtnThird13);

        rbtnFirst14 = findViewById(R.id.rbtnFirst14);
        rbtnSecond14 = findViewById(R.id.rbtnSecond14);
        rbtnThird14 = findViewById(R.id.rbtnThird14);

        rbtnFirst15 = findViewById(R.id.rbtnFirst15);
        rbtnSecond15 = findViewById(R.id.rbtnSecond15);
        rbtnThird15 = findViewById(R.id.rbtnThird15);
        rbtnFourth15 = findViewById(R.id.rbtnFourh15);
        rbtnFifth15 = findViewById(R.id.rbtnFifth15);

        rbtnFirst16 = findViewById(R.id.rbtnFirst16);
        rbtnSecond16 = findViewById(R.id.rbtnSecond16);
        rbtnThird16 = findViewById(R.id.rbtnThird16);
        rbtnFourth16 = findViewById(R.id.rbtnFourh16);

        rbtnFirst17 = findViewById(R.id.rbtnFirst17);
        rbtnSecond17 = findViewById(R.id.rbtnSecond17);
        rbtnThird17 = findViewById(R.id.rbtnThird17);





        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = getItem(+1);
                if (current < layouts.length) {
                    viewPager.setCurrentItem(current);
                    System.out.println(layouts.length + " " + current);
                } else {
                    Intent i = new Intent(getApplicationContext(), UserHome.class);
                    i.putExtra("openMeter", true);
                    finish();
                    startActivity(i);
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
        switch (view.getId()) {
            case R.id.rbtnFirst1:
                if (checked)
                    setAnsScore = 0;
                editor.putInt("ans1", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnSecond1:
                if (checked)
                    setAnsScore = 1;
                editor.putInt("ans1", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnThird1:
                if (checked)
                    setAnsScore = 2;
                editor.putInt("ans1", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnFourh1:
                if (checked)
                    setAnsScore = 3;
                editor.putInt("ans1", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnFifth1:
                if (checked)
                    setAnsScore = 4;
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
                    setAnsScore = 1;
                editor.putInt("ans2", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnThird2:
                if (checked)
                    setAnsScore = 2;
                editor.putInt("ans2", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnFourh2:
                if (checked)
                    setAnsScore = 3;
                editor.putInt("ans2", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnFifth2:
                if (checked)
                    setAnsScore = 4;
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
                    setAnsScore = 1;
                editor.putInt("ans3", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnThird3:
                if (checked)
                    setAnsScore = 2;
                editor.putInt("ans3", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnFourh3:
                if (checked)
                    setAnsScore = 3;
                editor.putInt("ans3", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnFifth3:
                if (checked)
                    setAnsScore = 4;
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
                    setAnsScore = 1;
                editor.putInt("ans4", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnThird4:
                if (checked)
                    setAnsScore = 2;
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
                    setAnsScore = 1;
                editor.putInt("ans5", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnThird5:
                if (checked)
                    setAnsScore = 2;
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
                    setAnsScore = 1;
                editor.putInt("ans6", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnThird6:
                if (checked)
                    setAnsScore = 2;
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
                    setAnsScore = 1;
                editor.putInt("ans7", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnThird7:
                if (checked)
                    setAnsScore = 2;
                editor.putInt("ans7", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnFourh7:
                if (checked)
                    setAnsScore = 3;
                editor.putInt("ans7", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnFifth7:
                if (checked)
                    setAnsScore = 4;
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
                    setAnsScore = 1;
                editor.putInt("ans8", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnThird8:
                if (checked)
                    setAnsScore = 2;
                editor.putInt("ans8", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnFourh8:
                if (checked)
                    setAnsScore = 3;
                editor.putInt("ans8", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnFifth8:
                if (checked)
                    setAnsScore = 4;
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
                    setAnsScore = 1;
                editor.putInt("ans9", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnThird9:
                if (checked)
                    setAnsScore = 2;
                editor.putInt("ans9", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnFourh9:
                if (checked)
                    setAnsScore = 3;
                editor.putInt("ans9", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnFifth9:
                if (checked)
                    setAnsScore = 4;
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
                    setAnsScore = 1;
                editor.putInt("ans10", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnThird10:
                if (checked)
                    setAnsScore = 2;
                editor.putInt("ans10", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnFourh10:
                if (checked)
                    setAnsScore = 3;
                editor.putInt("ans10", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnFifth10:
                if (checked)
                    setAnsScore = 4;
                editor.putInt("ans10", setAnsScore);
                editor.apply();
                break;

            //11th ques
            case R.id.rbtnFirst11:
                if (checked)
                    setAnsScore = 0;
                editor.putInt("ans11", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnSecond11:
                if (checked)
                    setAnsScore = 1;
                editor.putInt("ans11", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnThird11:
                if (checked)
                    setAnsScore = 2;
                editor.putInt("ans11", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnFourh11:
                if (checked)
                    setAnsScore = 3;
                editor.putInt("ans11", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnFifth11:
                if (checked)
                    setAnsScore = 4;
                editor.putInt("ans11", setAnsScore);
                editor.apply();
                break;

            //12th ques
            case R.id.rbtnFirst12:
                if (checked)
                    setAnsScore = 0;
                editor.putInt("ans12", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnSecond12:
                if (checked)
                    setAnsScore = 1;
                editor.putInt("ans12", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnThird12:
                if (checked)
                    setAnsScore = 2;
                editor.putInt("ans12", setAnsScore);
                editor.apply();
                break;


            //13th ques
            case R.id.rbtnFirst13:
                if (checked)
                    setAnsScore = 0;
                editor.putInt("ans13", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnSecond13:
                if (checked)
                    setAnsScore = 1;
                editor.putInt("ans13", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnThird13:
                if (checked)
                    setAnsScore = 2;
                editor.putInt("ans13", setAnsScore);
                editor.apply();
                break;


            //14th ques
            case R.id.rbtnFirst14:
                if (checked)
                    setAnsScore = 0;
                editor.putInt("ans14", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnSecond14:
                if (checked)
                    setAnsScore = 1;
                editor.putInt("ans14", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnThird14:
                if (checked)
                    setAnsScore = 2;
                editor.putInt("ans14", setAnsScore);
                editor.apply();
                break;


            //15th ques
            case R.id.rbtnFirst15:
                if (checked)
                    setAnsScore = 0;
                editor.putInt("ans15", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnSecond15:
                if (checked)
                    setAnsScore = 1;
                editor.putInt("ans11", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnThird15:
                if (checked)
                    setAnsScore = 2;
                editor.putInt("ans15", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnFourh15:
                if (checked)
                    setAnsScore = 3;
                editor.putInt("ans15", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnFifth15:
                if (checked)
                    setAnsScore = 4;
                editor.putInt("ans15", setAnsScore);
                editor.apply();
                break;

            //16th ques
            case R.id.rbtnFirst16:
                if (checked)
                    setAnsScore = 0;
                editor.putInt("ans16", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnSecond16:
                if (checked)
                    setAnsScore = 1;
                editor.putInt("ans16", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnThird16:
                if (checked)
                    setAnsScore = 2;
                editor.putInt("ans16", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnFourh16:
                if (checked)
                    setAnsScore = 3;
                editor.putInt("ans16", setAnsScore);
                editor.apply();
                break;


            //17th ques
            case R.id.rbtnFirst17:
                if (checked)
                    setAnsScore = 0;
                editor.putInt("ans17", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnSecond17:
                if (checked)
                    setAnsScore = 1;
                editor.putInt("ans17", setAnsScore);
                editor.apply();
                break;
            case R.id.rbtnThird17:
                if (checked)
                    setAnsScore = 2;
                editor.putInt("ans17", setAnsScore);
                editor.apply();
                break;


        }
    }

    private void addBottomDots(int position) {
        dots = new TextView[layouts.length];
        int[] colorActive = getResources().getIntArray(R.array.dotActive);
        int[] colorInactive = getResources().getIntArray(R.array.dotInactive);
        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorInactive[position]);
            dotsLayout.addView(dots[i]);
        }
        if (dots.length > 0) {
            dots[position].setTextColor(colorActive[position]);
        }
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + 1;
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
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
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = layoutInflater.inflate(layouts[position], container, false);
            container.addView(v);
            return v;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            View v = (View) object;
            container.removeView(v);
        }
    }
}
