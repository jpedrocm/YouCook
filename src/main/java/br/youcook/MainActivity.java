package br.youcook;

import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;

import br.youcook.fragments.CreateRecipeFragment;
import br.youcook.fragments.ProfileFragment;
import br.youcook.fragments.SearchRecipeFragment;
import br.youcook.fragments.UseNoRecipeFragment;
import br.youcook.fragments.adapter.TabsPagerAdapter;

public class MainActivity extends FragmentActivity implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {

    private TabHost tabHost;
    private ViewPager viewPager;
    private TabsPagerAdapter pagerAdapter;
    public static MainActivity self;

    class TabFactory implements TabContentFactory {

        private final Context mContext;

        public TabFactory(Context context) {
            mContext = context;
        }

        public View createTabContent(String tag) {
            View v = new View(mContext);
            v.setMinimumWidth(0);
            v.setMinimumHeight(0);
            return v;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        self = this;
        this.initialiseTabHost(savedInstanceState);
        if (savedInstanceState != null) {
            tabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
        }
        this.intialiseViewPager();
    }

    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("tab", tabHost.getCurrentTabTag());
        super.onSaveInstanceState(outState);
    }

    private void intialiseViewPager() {

        List<Fragment> fragments = new Vector<>();
        fragments.add(Fragment.instantiate(this, ProfileFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, CreateRecipeFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, SearchRecipeFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, UseNoRecipeFragment.class.getName()));
        this.pagerAdapter  = new TabsPagerAdapter(super.getSupportFragmentManager(), fragments);

        this.viewPager = (ViewPager)super.findViewById(R.id.viewpager);
        this.viewPager.setAdapter(this.pagerAdapter);
        this.viewPager.setOnPageChangeListener(this);
    }

    private void initialiseTabHost(Bundle args) {
        tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.setup();
        MainActivity.AddTab(this, this.tabHost, this.tabHost.newTabSpec("Tab1").setIndicator("Profile"));
        MainActivity.AddTab(this, this.tabHost, this.tabHost.newTabSpec("Tab2").setIndicator("Create"));
        MainActivity.AddTab(this, this.tabHost, this.tabHost.newTabSpec("Tab3").setIndicator("Search"));
        MainActivity.AddTab(this, this.tabHost, this.tabHost.newTabSpec("Tab4").setIndicator("Use"));

        tabHost.setOnTabChangedListener(this);
    }

    private static void AddTab(MainActivity activity, TabHost tabHost, TabHost.TabSpec tabSpec) {
        tabSpec.setContent(activity.new TabFactory(activity));
        tabHost.addTab(tabSpec);
    }

    public void onTabChanged(String tag) {
        int position = this.tabHost.getCurrentTab();
        this.viewPager.setCurrentItem(position);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        this.tabHost.setCurrentTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void mudarAba(int position){
        this.onPageSelected(position);
    }
}