package com.fatimamostafa.app.newsviewsv2.ui.splash;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fatimamostafa.app.newsviewsv2.R;
import com.fatimamostafa.app.newsviewsv2.ui.login.LoginActivity;
import com.fatimamostafa.app.newsviewsv2.ui.main.MainActivity;
import com.fatimamostafa.app.newsviewsv2.ui.introduction.IntroductionActivity;
import com.fatimamostafa.app.newsviewsv2.utilities.Constants;
import com.fatimamostafa.app.newsviewsv2.utilities.SharedPreferenceManager;
import com.fatimamostafa.app.newsviewsv2.utilities.Utilities;

import java.util.concurrent.TimeUnit;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends AppCompatActivity {
    Disposable splashDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(SharedPreferenceManager.getInstance().getBoolean(Constants.APP_LAUNCHED_SECOND_TIME)) {
            splashDisposable = Observable.timer(1000, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onComplete);
        }
        else {
            Intent intent;
            intent = new Intent(this, IntroductionActivity.class);
            startActivity(intent);
            Utilities.showForwardTransitionFadeIn(this);
            finish();
        }
    }

    private void onComplete(Long aLong) {
        Intent intent;
        intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        Utilities.showForwardTransitionFadeIn(this);
        finish();
    }


    @Override
    public void onPause() {
        super.onPause();
        if(splashDisposable != null)
        splashDisposable.dispose();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }


}
