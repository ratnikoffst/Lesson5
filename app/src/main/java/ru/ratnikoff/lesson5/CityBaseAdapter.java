package ru.ratnikoff.lesson5;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class CityBaseAdapter extends BaseAdapter {
    private final MainActivity mMainActivity;
    public int mCurrenPosition = -1;
    //  private final CityFragment mCityFragment;
    ArrayList<String> mCity;

    public CityBaseAdapter(String[] mCity, MainActivity mMainActivity) {
        this.mCity = new ArrayList<>(Arrays.asList(mCity));
        this.mMainActivity = mMainActivity;
        //mCityFragment=cityFragment;
    }

    @Override
    public int getCount() {
        return mCity.size();
    }

    @Override
    public Object getItem(int position) {
        return mCity.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mMainActivity).inflate(R.layout.item_city, null);
        fillView(convertView, position);
        return convertView;
    }

    private void fillView(View convertView, int position) {
        TextView textView = convertView.findViewById(R.id.number_item);
        textView.setText("" + (position + 1));
        textView = convertView.findViewById(R.id.city);
        textView.setText(mCity.get(position));
        if (position == mCurrenPosition) {
            convertView.setBackgroundResource(R.drawable.spinner_selected);
        }
    }
}
