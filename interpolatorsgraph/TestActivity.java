package com.chisw.test;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {
    private static final long DEFAULT_ANIMATION_DURATION = 2000L;

    private GraphView graphView;
    private ImageView doughnut;
    private View floor;
    private Spinner spinner;
    private ValueAnimator valueAnimator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        spinner = (Spinner) findViewById(R.id.spinner);
        graphView = (GraphView) findViewById(R.id.graphView);
        doughnut = (ImageView) findViewById(R.id.doughnut);
        floor = findViewById(R.id.floor);
        valueAnimator = ValueAnimator.ofFloat();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, generate());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animate();
            }
        });
    }

    private void animate() {
        graphView.clear();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) doughnut.getLayoutParams();
        if(valueAnimator.isRunning() || valueAnimator.isStarted()) {
            valueAnimator.cancel();
        }
        valueAnimator.setFloatValues(0, floor.getY() - params.topMargin - doughnut.getHeight());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                graphView.addLine(animation.getCurrentPlayTime(), value);
                doughnut.setTranslationY(value);
            }
        });
        valueAnimator.setInterpolator(InterpolatorFactory.getInstance((String) spinner.getSelectedItem()));
        valueAnimator.setDuration(DEFAULT_ANIMATION_DURATION);
        valueAnimator.start();
    }

    private ArrayList<String> generate() {
        ArrayList<String> items = new ArrayList<>();
        items.add(InterpolatorFactory.ACCELERATE);
        items.add(InterpolatorFactory.ACCELERATE_DECELERATE);
        items.add(InterpolatorFactory.ANTICIPATE);
        items.add(InterpolatorFactory.ANTICIPATE_OVERSHOOT);
        items.add(InterpolatorFactory.BOUNCE);
        items.add(InterpolatorFactory.CYCLE);
        items.add(InterpolatorFactory.DECELERATE);
        items.add(InterpolatorFactory.FAST_OUT_LINEAR_IN);
        items.add(InterpolatorFactory.FAST_OUT_SLOW_IN);
        items.add(InterpolatorFactory.LINEAR);
        items.add(InterpolatorFactory.LINEAR_OUT_SLOW_IN);
        items.add(InterpolatorFactory.OVERSHOOT);
        return items;
    }
}
