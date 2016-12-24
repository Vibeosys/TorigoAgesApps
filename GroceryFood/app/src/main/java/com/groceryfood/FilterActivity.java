package com.groceryfood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

public class FilterActivity extends AppCompatActivity {

    private int mSeekBarStep = 1, mSeekBarMax, mSeekBarMin;
    private int mSeekBarPriceStep = 1, mSeekBarPriceMax = 10000, mSeekBarPriceMin = 10;
    private SeekBar mSeekBar;
    private SeekBar mSeekBarPrice;
    private int selectedRadius = 3;
    private int selectedPrice = 50;
    private TextView mFilterVal, filterPriceVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        mSeekBarMax = 100;
        mSeekBarMin = 1;
        mSeekBar = (SeekBar) findViewById(R.id.skb_radius);
        mSeekBarPrice = (SeekBar) findViewById(R.id.skb_price);
        mFilterVal = (TextView) findViewById(R.id.filterVal);
        filterPriceVal = (TextView) findViewById(R.id.filterPriceVal);
        setTitle(getResources().getString(R.string.filter));
        mSeekBar.setMax((mSeekBarMax - mSeekBarMin) / mSeekBarStep);
        mFilterVal.setText("1 " + getResources().getString(R.string.raduis_selected));
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                selectedRadius = mSeekBarMin + (progress * mSeekBarStep);
                mFilterVal.setText("" + selectedRadius + " " + getResources().getString(R.string.raduis_selected));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mSeekBarPrice.setMax((mSeekBarPriceMax - mSeekBarPriceMin) / mSeekBarPriceStep);
        mSeekBarPrice.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                selectedPrice = mSeekBarPriceMin + (progress * mSeekBarPriceStep);
                filterPriceVal.setText(getResources().getString(R.string.sar) + " " + selectedPrice);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
