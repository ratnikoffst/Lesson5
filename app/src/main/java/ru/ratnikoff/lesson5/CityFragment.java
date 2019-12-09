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
import java.util.List;

public class CityFragment extends Fragment implements AdapterView.OnItemClickListener {
    private static final String СURENT_CITY = "CurrentCity";
    public static final String WEATHER_FRAGMENT = "WeatherChild";
    private FragmentTransaction mTransaction;
    private ArrayList<DataClimat> mDataClimatArray;
    private MainActivity mMainActivity;
    private ListView mListView;
    boolean isExistCoatOfArms;
    private LinearLayout mFragWeatherView;
    private LinearLayout.LayoutParams mLayoutFrWethParams;
    private WeatherFragment mWeatherFragment;
    private CityBaseAdapter mCityBaseAdapter;
    private LinearLayout mFragCityView;
    private LinearLayout.LayoutParams mLayoutFrCityParams;


    public CityFragment(MainActivity mMainActivity) {
        this.mMainActivity = mMainActivity;
        mTransaction = mMainActivity.getSupportFragmentManager().beginTransaction();
        initArray(mMainActivity);
    }

    private void initArray(MainActivity mMainActivity) {
        mDataClimatArray = new ArrayList<>();
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
        setRetainInstance(true);
        initViews(view);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        showOrientation();
        super.onConfigurationChanged(newConfig);

    }

    private void showOrientation() {
        isExistCoatOfArms = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        if (isExistCoatOfArms) { // ориентация альбом
            mLayoutFrWethParams.weight = 1f;
            mFragWeatherView.setLayoutParams(mLayoutFrWethParams);
        } else {
            if (mWeatherFragment == null) {
                mLayoutFrWethParams.weight = 0f;
                mFragWeatherView.setLayoutParams(mLayoutFrWethParams);
            } else { // если есть fragment
                List<Fragment> list = mMainActivity.getSupportFragmentManager().getFragments();
                mLayoutFrWethParams= (LinearLayout.LayoutParams) mFragWeatherView.getLayoutParams();
                mLayoutFrWethParams.weight = 1f;
                mFragWeatherView.setLayoutParams(mLayoutFrWethParams);

                mLayoutFrCityParams.weight = 0f;
                mFragCityView.setLayoutParams(mLayoutFrCityParams);

            }
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

        mFragWeatherView = view.findViewById(R.id.fragment_wether);
        mFragCityView = view.findViewById(R.id.layout_city);

        mLayoutFrWethParams = (LinearLayout.LayoutParams) mFragWeatherView.getLayoutParams();
        mLayoutFrCityParams = (LinearLayout.LayoutParams) mFragCityView.getLayoutParams();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mWeatherFragment == null) {
            mWeatherFragment = new WeatherFragment(mMainActivity, mDataClimatArray.get(position));
            mTransaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right);
            mTransaction.replace(R.id.fragment_wether, mWeatherFragment, WEATHER_FRAGMENT);
            mTransaction.commit();
            isExistCoatOfArms = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
            List<Fragment> list = mMainActivity.getSupportFragmentManager().getFragments();
            if (!isExistCoatOfArms) {
                mLayoutFrCityParams.weight = 0;
                mLayoutFrWethParams.weight = 1;
                mFragCityView.setLayoutParams(mLayoutFrCityParams);
                mFragWeatherView.setLayoutParams(mLayoutFrWethParams);
            }
        }
        mCityBaseAdapter.mCurrenPosition = position;
        mCityBaseAdapter.notifyDataSetChanged();
    }

    class DataClimat {
        public String hummidaty;
        public String pressure;
        public String temperature;
    }
}
