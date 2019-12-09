package ru.ratnikoff.lesson5;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

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
            transaction.replace(R.id.fragment, mCityFragment, CITY_FRAGMENT);
            transaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        mCityFragment.doBack();
        //super.onBackPressed();
    }
}
