package ru.ratnikoff.lesson5;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static ru.ratnikoff.lesson5.CityBaseAdapter.NO_SELECTION;

public class CityFragment extends Fragment implements AdapterView.OnItemClickListener, CompoundButton.OnCheckedChangeListener, OnBackPressedListener {

    private ArrayList<DataClimat> mDataClimatArray;
    private MainActivity mMainActivity;
    private ListView mListView;
    boolean isExistCoatOfArms;
    private LinearLayout mFragWeatherView;
    private LinearLayout.LayoutParams mLayoutFrWethParams;
    private CityBaseAdapter mCityBaseAdapter;
    private LinearLayout mFragCityView;
    private LinearLayout.LayoutParams mLayoutFrCityParams;
    private TextView textHummidity;
    private TextView textPressure;
    private TextView textTemperature;

    public CityFragment(MainActivity mMainActivity) {
        this.mMainActivity = mMainActivity;
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
//        setRetainInstance(true);
        initViews(view);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        showOrientation();
    }

    private void showOrientation() {
        isExistCoatOfArms = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        if (isExistCoatOfArms) { // ориентация альбом
            mLayoutFrCityParams.weight = 1f;
            mFragCityView.setLayoutParams(mLayoutFrCityParams);

            mLayoutFrWethParams.weight = 1f;
            mFragWeatherView.setLayoutParams(mLayoutFrWethParams);
        } else {
            if (mFragWeatherView.getVisibility() == VISIBLE) {
                mLayoutFrWethParams.weight = 1f;
                mFragWeatherView.setLayoutParams(mLayoutFrWethParams);
                mLayoutFrCityParams.weight = 0f;
                mFragCityView.setLayoutParams(mLayoutFrCityParams);

            } else {
                mLayoutFrWethParams.weight = 0f;
                mFragWeatherView.setLayoutParams(mLayoutFrWethParams);
                mLayoutFrCityParams.weight = 1f;
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

        CheckBox checkBox = view.findViewById(R.id.humidity);
        checkBox.setOnCheckedChangeListener(this);
        checkBox = view.findViewById(R.id.pressure);
        checkBox.setOnCheckedChangeListener(this);

        textHummidity = view.findViewById(R.id.text_humidity);
        textPressure = view.findViewById(R.id.text_pressure);
        textTemperature = view.findViewById(R.id.text_temperature);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!isExistCoatOfArms) {
            mLayoutFrCityParams.weight = 0;
            mLayoutFrWethParams.weight = 1;
            mFragCityView.setLayoutParams(mLayoutFrCityParams);
            mFragWeatherView.setLayoutParams(mLayoutFrWethParams);
        }
        textHummidity.setText(mDataClimatArray.get(position).hummidaty);
        textPressure.setText(mDataClimatArray.get(position).pressure);
        textTemperature.setText(mDataClimatArray.get(position).temperature);
        mFragWeatherView.setVisibility(VISIBLE);

        mCityBaseAdapter.mCurrenPosition = position;
        mCityBaseAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        switch (buttonView.getId()) {
            case R.id.humidity:
                if (isChecked) {
                    textHummidity.setVisibility(VISIBLE);
                } else {
                    textHummidity.setVisibility(INVISIBLE);
                }
                break;
            case R.id.pressure:
                if (isChecked) {
                    textPressure.setVisibility(VISIBLE);
                } else {
                    textPressure.setVisibility(INVISIBLE);
                }
                break;
        }
    }

    @Override
    public void doBack() {
        if (mFragWeatherView.getVisibility() == VISIBLE) {
            mFragWeatherView.setVisibility(INVISIBLE);
            ((CityBaseAdapter) mListView.getAdapter()).mCurrenPosition = NO_SELECTION;
            ((CityBaseAdapter) mListView.getAdapter()).notifyDataSetChanged();
            showOrientation();
        } else {
           DialogExitApp dialogExitApp=new DialogExitApp();
           dialogExitApp.show(mMainActivity.getSupportFragmentManager(),"Exit");
        }
    }

    class DataClimat {
        public String hummidaty;
        public String pressure;
        public String temperature;
    }
}
