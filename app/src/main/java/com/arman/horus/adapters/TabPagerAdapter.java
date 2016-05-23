package com.arman.horus.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.arman.horus.fragments.BoardTabFragment;
import com.arman.horus.fragments.MapTabFragment;
import com.arman.horus.fragments.PlansTabFragment;
import com.arman.horus.fragments.ProfileTabFragment;

/**
 * Created by arman on 3/24/16.
 */
public class TabPagerAdapter extends FragmentPagerAdapter {

    Fragment[] tabFragments = new Fragment[]{
            new BoardTabFragment(),
            new MapTabFragment(),
            new PlansTabFragment(),
            new ProfileTabFragment(),
    };

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return tabFragments[position];
    }

    @Override
    public int getCount() {
        return tabFragments.length;
    }
}
