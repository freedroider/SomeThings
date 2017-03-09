package com.freedroider.jstempproject.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.freedroider.jstempproject.R;
import com.freedroider.jstempproject.ui.customview.ArcProgressView;

public class MainActivity extends AppCompatActivity {
    private ArcProgressView jsProgressView;
    private Button restart;
    private SeekBar seekBar;
    private int value;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_some);
        jsProgressView = (ArcProgressView) findViewById(R.id.progress);
        restart = (Button) findViewById(R.id.restart);
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        seekBar.setMax(1000);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                value = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsProgressView.setValue(value);
            }
        });
    }
}
