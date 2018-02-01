package com.android.bignerdranch.nerdlaucher;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.bignerdranch.nerdlaucher.Fragments.NerdLauncherFragment;

public class NerdLauncherActivity extends SingleFragmentActivity {


    @Override
    public Fragment createFragment() {
        return new NerdLauncherFragment();
    }
}
