package ru.ratnikoff.lesson5;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

import static ru.ratnikoff.lesson5.MainActivity.CITY_FRAGMENT;

public class CityFragment extends Fragment implements AdapterView.OnItemClickListener {
    private static final String СURENT_CITY = "CurrentCity";
    public static final String WEATHER_FRAGMENT = "WeatherChild";
    private FragmentTransaction transaction;
    private ArrayList<DataClimat> mDataClimatArray;
    private MainActivity mMainActivity;
    private ListView mListView;
    boolean isExistCoatOfArms;
    private LinearLayout fragWeatherView;
    private LinearLayout.LayoutParams mLayoutFrWethParams;
    private WeatherFragment mWeatherFragment;
    private CityBaseAdapter mCityBaseAdapter;


    public CityFragment(MainActivity mMainActivity) {
        this.mMainActivity = mMainActivity;
        //    this.setRetainInstance(false);
        initArray(mMainActivity);
    }

    private void initArray(MainActivity mMainActivity) {
        mDataClimatArray = new ArrayList<DataClimat>();
        String[] hummiduty = mMainActivity.getResources().getStringArray(R.array.humudity);
        String[] pressure = mMainActivity.getResources().getStringArray(R.array.pressure);
        String[] temperature = mMainActivity.getResources().getStringArray(R.array.temperature);
        for (int index = 0; index < hummiduty.length; index++) {
            DataClimat date = new DataClimat();
            date.hummidaty = hummiduty[index];
            date.pressure = pressure[index];
            date.temperature = temperature[index];
            mDataClimatArray.add(date);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_city, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        showOrientation();
    }

    private void showOrientation() {
        isExistCoatOfArms = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        if (isExistCoatOfArms) {
            mLayoutFrWethParams.weight = 1f;
            fragWeatherView.setLayoutParams(mLayoutFrWethParams);
        } else {
            LinearLayout.LayoutParams test = (LinearLayout.LayoutParams) fragWeatherView.getLayoutParams();
            test.weight = 0f;
            fragWeatherView.setLayoutParams(mLayoutFrWethParams);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showOrientation();
    }

    private void initViews(View view) {
        mListView = view.findViewById(R.id.list_city);
        mCityBaseAdapter = new CityBaseAdapter(mMainActivity.getResources().getStringArray(R.array.city)
                , mMainActivity);
        mListView.setAdapter(mCityBaseAdapter);
        mListView.setOnItemClickListener(this);
        fragWeatherView = view.findViewById(R.id.fragment_wether);
        mLayoutFrWethParams = (LinearLayout.LayoutParams) fragWeatherView.getLayoutParams();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        transaction = mMainActivity.getSupportFragmentManager().beginTransaction();
        if (isExistCoatOfArms) {
            if (mWeatherFragment == null) {
                mWeatherFragment = new WeatherFragment(mMainActivity, mDataClimatArray.get(position));
                transaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right);
                transaction.addToBackStack(null);
                transaction.add(R.id.fragment_wether, mWeatherFragment, "Weather");
                transaction.commit();
            }
            mWeatherFragment.setData(mDataClimatArray.get(position));
        } else {
            WeatherFragment weatherFragment = new WeatherFragment(mMainActivity, mDataClimatArray.get(position));
            transaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right);
            transaction.hide(this);
            transaction.addToBackStack(CITY_FRAGMENT);
            transaction.add(R.id.fragment, weatherFragment, WEATHER_FRAGMENT);
            //transaction.show(weatherFragment);
            // transaction.disallowAddToBackStack();
            transaction.commit();

        }
        mCityBaseAdapter.mCurrenPosition = position;
        mCityBaseAdapter.notifyDataSetChanged();

        String city = (String) mListView.getAdapter().getItem(position);
    }

    class DataClimat {
        public String hummidaty;
        public String pressure;
        public String temperature;
    }
}
