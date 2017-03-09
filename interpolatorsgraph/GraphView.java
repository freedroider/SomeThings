package com.chisw.test;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class GraphView extends View {
    private final Paint gridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint textPaint = new Paint();
    private final Path path = new Path();
    private final Point size = new Point();
    private int numColumns = 4, numRows = 3;
    private int cellWidth, cellHeight;
    private float lastX = -1f, lastY = -1f;
    private float coef;
    private float anotherCoef;

    public GraphView(Context context) {
        super(context);
        init();
    }

    public GraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GraphView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public GraphView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    public void addLine(float x, float y) {
        x = (x * coef) + 50;
        y = (float) getHeight() - ((y + cellHeight) * anotherCoef);
        Log.d("Line", "new line: x = " + x + "; y = " + y);
        if (lastX == -1f || lastY == -1f) {
            lastX = x;
            lastY = y;
        }
        path.moveTo(lastX, lastY);
        path.quadTo(lastX, lastY, (x + lastX) / 2, (y + lastY) / 2);
        path.lineTo(x, y);
        lastX = x;
        lastY = y;
        invalidate();
    }

    public void clear() {
        path.reset();
        lastX = -1f;
        lastY = -1f;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawGrid(canvas);
        canvas.drawPath(path, linePaint);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        calculateDimensions();
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        display.getSize(size);
        coef = (float) getWidth() / (2000L + cellWidth + 50);
        anotherCoef = (float) getHeight() / (size.y - cellHeight + 100);
    }

    private void drawGrid(Canvas canvas) {
        if (numColumns == 0 || numRows == 0) {
            return;
        }
        int width = getWidth();
        int height = getHeight();
        for (int i = 1; i < numColumns; i++) {
            canvas.drawLine(i * cellWidth, 0, i * cellWidth, height - 50, gridPaint);
        }
        for (int i = 1; i < numRows; i++) {
            canvas.drawLine(50, i * cellHeight, width, i * cellHeight, gridPaint);
        }
        canvas.drawText("Time axis", (width / 2) - 50, height, textPaint);
        canvas.save();
        canvas.rotate(-90, 30, (height / 2) + 50);
        canvas.drawText("Value axis", 0, (height / 2) + 50, textPaint);
        canvas.restore();
    }

    private void init() {
        gridPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        gridPaint.setColor(Color.LTGRAY);
        gridPaint.setStrokeWidth(1f);

        linePaint.setColor(Color.WHITE);
        linePaint.setDither(true);
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(5f);
        linePaint.setStyle(Paint.Style.STROKE);

        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(32);
    }


    private void calculateDimensions() {
        if (numColumns < 1 || numRows < 1) {
            return;
        }
        cellWidth = getWidth() / numColumns;
        cellHeight = getHeight() / numRows;
        invalidate();
    }

}
