package ru.ratnikoff.lesson5;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

import static ru.ratnikoff.lesson5.CityFragment.WEATHER_FRAGMENT;

public class MainActivity extends AppCompatActivity {

    public static final String CITY_FRAGMENT = "CITY";
    private FragmentTransaction transaction;
    private CityFragment mCityFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (mCityFragment == null) {
            mCityFragment = new CityFragment(this);
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right);
            transaction.disallowAddToBackStack();
            transaction.add(R.id.fragment, mCityFragment, CITY_FRAGMENT);
            transaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        
        Fragment t = getSupportFragmentManager().findFragmentByTag(WEATHER_FRAGMENT);
        Fragment t3 = getSupportFragmentManager().findFragmentByTag(CITY_FRAGMENT);
        List<Fragment> t2 = getSupportFragmentManager().getFragments();

        if (t != null) {
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right);
            transaction.remove(t);
            transaction.commit();
        }
    }
}
