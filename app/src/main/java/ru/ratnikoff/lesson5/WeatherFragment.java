package ru.ratnikoff.lesson5;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.view.View.INVISIBLE;
import static android.widget.RadioGroup.VISIBLE;

public class WeatherFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    private final MainActivity mMainActivity;
    private CityFragment.DataClimat mDataClimat;
    private TextView textHummidity, textPressure, textTemperature;

    public WeatherFragment(MainActivity mainActivity, CityFragment.DataClimat dataClimat) {
        mMainActivity = mainActivity;
        mDataClimat = dataClimat;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        fillView();
    }


    private void initView(View view) {
        CheckBox checkBox = view.findViewById(R.id.humidity);
        checkBox.setOnCheckedChangeListener(this);
        checkBox = view.findViewById(R.id.pressure);
        checkBox.setOnCheckedChangeListener(this);

        textHummidity = view.findViewById(R.id.text_humidity);
        textPressure = view.findViewById(R.id.text_pressure);
        textTemperature = view.findViewById(R.id.text_temperature);
    }

    private void fillView() {
        textHummidity.setText(mDataClimat.hummidaty);
        textPressure.setText(mDataClimat.pressure);
        textTemperature.setText(mDataClimat.temperature);
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

    public void setData(CityFragment.DataClimat dataClimat) {
        mDataClimat = dataClimat;
        fillView();
    }

}
