package com.fatimamostafa.app.newsviewsv2.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.fatimamostafa.app.newsviewsv2.R;
import com.fatimamostafa.app.newsviewsv2.ui.login.LoginActivity;
import com.fatimamostafa.app.newsviewsv2.ui.search.SearchActivity;
import com.fatimamostafa.app.newsviewsv2.ui.search.SearchContract;
import com.fatimamostafa.app.newsviewsv2.utilities.Constants;
import com.fatimamostafa.app.newsviewsv2.utilities.Utilities;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class MainActivity extends AppCompatActivity implements MainContract.View {


    public static final String TAG = "MainActivity";
    boolean doubleBackToExitPressedOnce = false;
    NavigationAdapter adapter;
    String CURRENT_FRAGMENT = "";
    MainContract.Presenter presenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_nav)
    RecyclerView recyclerView;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        init();
    }

    private void init() {
        presenter = new MainPresenter();
        presenter.attachView(this);
        presenter.prepareNavItems(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (!CURRENT_FRAGMENT.equals(Constants.Fragments.HOME)) {
            adapter.openHomeFragment();
        } else {
            exitFromActivity();
        }
    }

    public void exitFromActivity() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;

        Utilities.showShortToast(this, "Please click BACK again to exit");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;

            }
        }, 2000);
    }

    @Override
    public void onNavItemPrepared(List<NavItem> navItemList) {

        adapter = new NavigationAdapter(this, navItemList, new MainContract.OnClickListener() {
            @Override
            public void onNavItemClicked(NavItem item) {

                presenter.onNavItemClicked(item);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        LinearLayoutManager categoryLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(categoryLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void openFragment(NavItem item) {
        if (CURRENT_FRAGMENT.equals(item.getFragment()))
            return;

        CURRENT_FRAGMENT = item.getFragment();


        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = new Fragment();
        Bundle bundle = new Bundle();

        try {
            fragment = (Fragment) (Class.forName(item.getFragment()).newInstance());
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        fragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.rl_container, fragment)
                //.addToBackStack(null)
                .commit();
    }


    @OnClick({R.id.ivSearch, R.id.rl_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivSearch:
                navigateToSearchActivity();
                break;
            case R.id.rl_exit:
                super.onBackPressed();
                break;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    private void navigateToSearchActivity() {
        Intent intent;
        intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
        Utilities.showForwardTransition(this);
    }
}
