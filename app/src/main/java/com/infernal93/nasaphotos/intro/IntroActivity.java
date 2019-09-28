package com.infernal93.nasaphotos.intro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.material.tabs.TabLayout;
import com.infernal93.nasaphotos.MainActivity;
import com.infernal93.nasaphotos.R;
import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    private ViewPager screenPager;
    private TabLayout tabIndicator;
    private Button NextIntroBtn, btnGetStartedIntro;
    private TextView textViewSkip;
    private Animation btn_get_startedAnim;

    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);


        btn_get_startedAnim = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.btn_get_started_anim);

        textViewSkip = findViewById(R.id.tv_skip);
        btnGetStartedIntro = findViewById(R.id.btn_get_started_intro);
        NextIntroBtn = findViewById(R.id.intro_next_button);

        tabIndicator = findViewById(R.id.tab_indicator_intro);

        if (restorePrefData()) {

            sendUserToMainActivity();
        }

        // title strings
        String title_one = getResources().getString(R.string.intro_title_one);
        String title_two = getResources().getString(R.string.intro_title_two);
        String title_three = getResources().getString(R.string.intro_title_three);
        String title_four = getResources().getString(R.string.intro_title_four);
        // description strings
        String description_one = getResources().getString(R.string.intro_description_one);
        String description_two = getResources().getString(R.string.intro_description_two);
        String description_three = getResources().getString(R.string.intro_description_three);
        String description_four = getResources().getString(R.string.intro_description_four);
        // fill list screen
        final List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem(title_one, description_one, R.drawable.ic_intro_one));
        mList.add(new ScreenItem(title_two, description_two, R.drawable.ic_intro_two));
        mList.add(new ScreenItem(title_three, description_three, R.drawable.ic_intro_three));
        mList.add(new ScreenItem(title_four, description_four, R.drawable.ic_intro_four));

        // setup viewpager
        screenPager = findViewById(R.id.intro_viewpager);
        IntroViewPagerAdapter introViewPagerAdapter = new IntroViewPagerAdapter(this, mList);
        screenPager.setAdapter(introViewPagerAdapter);

        // setup tab layout with viewpager
        tabIndicator.setupWithViewPager(screenPager);

        NextIntroBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position = screenPager.getCurrentItem();
                if (position < mList.size()) {

                    position++;
                    screenPager.setCurrentItem(position);
                }

                if (position == mList.size() - 1) { // when we reach to the last screen
                    loadLastScreen();
                }
            }
        });

        // tab layout add change listener
        tabIndicator.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == mList.size() - 1) {

                    loadLastScreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        // getStarted Button click listener
        btnGetStartedIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // open nextActivity
                sendUserToMainActivity();

                // save new user info
                savePrefData();
                finish();

            }
        });

        textViewSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                screenPager.setCurrentItem(mList.size());
            }
        });
    }

    private void sendUserToMainActivity() {
        Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainActivity);
        finish();
    }

    private boolean restorePrefData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("IntroPrefs", MODE_PRIVATE);
        boolean isIntroActivityOpenedBefore = pref.getBoolean("isIntroOpened", false);
        return isIntroActivityOpenedBefore;
    }

    private void savePrefData() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("IntroPrefs",
                MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpened", true);
        editor.apply();

    }

    private void loadLastScreen() {
        NextIntroBtn.setVisibility(View.INVISIBLE);
        btnGetStartedIntro.setVisibility(View.VISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);
        textViewSkip.setVisibility(View.INVISIBLE);

        btnGetStartedIntro.setAnimation(btn_get_startedAnim);
    }
}
