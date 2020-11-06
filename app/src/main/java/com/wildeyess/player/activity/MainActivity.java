package com.wildeyess.player.activity;

import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.wildeyess.player.R;
import com.wildeyess.player.base.BaseActivity;
import com.wildeyess.player.base.CommPagerAdapter;
import com.wildeyess.player.bean.MainPageChangeEvent;
import com.wildeyess.player.bean.PauseVideoEvent;
import com.wildeyess.player.fragment.MainFragment;
import com.wildeyess.player.fragment.PersonalHomeFragment;
import com.wildeyess.player.utils.RxBus;
import java.util.ArrayList;
import butterknife.BindView;
import rx.functions.Action1;

/**
 * create by wildeyess
 * create on 2020/5/19
 * description 主页面
 */
public class MainActivity extends BaseActivity {
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    private CommPagerAdapter pagerAdapter;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    public static int curMainPage;
    private MainFragment mainFragment = new MainFragment();
    private PersonalHomeFragment personalHomeFragment = new PersonalHomeFragment();
    /** 上次点击返回键时间 */
    private long lastTime;
    /** 连续按返回键退出时间 */
    private final int EXIT_TIME = 2000;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {

        fragments.add(mainFragment);
        fragments.add(personalHomeFragment);
        pagerAdapter = new CommPagerAdapter(getSupportFragmentManager(), fragments, new String[]{"",""});
        viewPager.setAdapter(pagerAdapter);

        //点击头像切换页面
        RxBus.getDefault().toObservable(MainPageChangeEvent.class)
                .subscribe((Action1<MainPageChangeEvent>) event -> {
                    if (viewPager != null) {
                        viewPager.setCurrentItem(event.getPage());
                    }
                });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                curMainPage = position;

                if (position == 0) {
                    RxBus.getDefault().post(new PauseVideoEvent(true));
                } else if (position == 1){
                    RxBus.getDefault().post(new PauseVideoEvent(false));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        //双击返回退出App
        if (System.currentTimeMillis() - lastTime > EXIT_TIME) {
            if (viewPager.getCurrentItem() == 1) {
                viewPager.setCurrentItem(0);
            }else{
                Toast.makeText(getApplicationContext(), "再按一次退出", Toast.LENGTH_SHORT).show();
                lastTime = System.currentTimeMillis();
            }
        } else {
            super.onBackPressed();
        }
    }
}
