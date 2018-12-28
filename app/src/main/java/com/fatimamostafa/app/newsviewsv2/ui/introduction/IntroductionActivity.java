package com.fatimamostafa.app.newsviewsv2.ui.introduction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fatimamostafa.app.newsviewsv2.R;
import com.fatimamostafa.app.newsviewsv2.ui.login.LoginActivity;
import com.fatimamostafa.app.newsviewsv2.ui.main.MainActivity;
import com.fatimamostafa.app.newsviewsv2.utilities.Constants;
import com.fatimamostafa.app.newsviewsv2.utilities.SharedPreferenceManager;
import com.fatimamostafa.app.newsviewsv2.utilities.Utilities;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class IntroductionActivity extends AppCompatActivity {

    @BindView(R.id.pager_introduction)
    ViewPager viewPager;
    @BindView(R.id.viewPagerCountDots)
    LinearLayout viewPagerCountDots;
    @BindView(R.id.ll_footer)
    LinearLayout llFooter;
    @BindView(R.id.btn_get_started)
    Button btnGetStarted;
    IntroductionAdapter adapter;
    int previousPos = 0;
    List<IntroductionItem> itemList = new ArrayList<>();
    public static final String TAG = "IntroductionActivity";

    private int dotsCount;
    private ImageView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
        ButterKnife.bind(this);

        SharedPreferenceManager.getInstance().setBoolean(Constants.APP_LAUNCHED_SECOND_TIME, true);

        loadData();


        adapter = new IntroductionAdapter(this, itemList);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                // Change the current position intimation

                for (int i = 0; i < dotsCount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(IntroductionActivity.this, R.drawable.non_selected_item_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(IntroductionActivity.this, R.drawable.selected_item_dot));


                int pos = position + 1;

                if (pos == dotsCount && previousPos == (dotsCount - 1))
                    Utilities.showAnimation(IntroductionActivity.this, btnGetStarted);
                else if (pos == (dotsCount - 1) && previousPos == dotsCount)
                    Utilities.hideAnimation(IntroductionActivity.this, btnGetStarted);

                previousPos = pos;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setUiPageViewController();
    }


    // setup the
    private void setUiPageViewController() {

        dotsCount = adapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(IntroductionActivity.this, R.drawable.non_selected_item_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(6, 0, 6, 0);

            viewPagerCountDots.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(IntroductionActivity.this, R.drawable.selected_item_dot));
    }

    private void loadData() {
        int[] header = {R.string.header1, R.string.header2, R.string.header3};
        int[] desc = {R.string.desc1, R.string.desc2, R.string.desc3};
        int[] imageId = {R.drawable.shortbrief, R.drawable.newsviewsintro, R.drawable.funfacts};

        for (int i = 0; i < imageId.length; i++) {
            IntroductionItem item = new IntroductionItem();
            item.setImageId(imageId[i]);
            item.setTitle(getResources().getString(header[i]));
            item.setDescription(getResources().getString(desc[i]));

            itemList.add(item);
        }
    }

    @OnClick(R.id.btn_get_started)
    public void onViewClicked() {
        Log.d(TAG, "onViewClicked: ");
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        Utilities.showForwardTransitionFadeIn(this);
        finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

}
