package io.github.dearzack.hencoder.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Zack on 2017/7/25.
 */

public class HenCoder1_1View extends View {

    Paint paint = new Paint();

    public HenCoder1_1View(Context context) {
        super(context);
        init();
    }

    public HenCoder1_1View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HenCoder1_1View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制一个圆
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(30);
        canvas.drawCircle(300, 300, 200, paint);
//        canvas.drawColor(Color.BLACK);
        canvas.drawColor(Color.parseColor("#88880000"));

        paint.setStrokeWidth(20);
//        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawPoint(50, 50, paint);
        canvas.drawPoints(new float[]{60, 60, 100, 100, 140, 140}, paint);

        paint.setStyle(Paint.Style.FILL);
        canvas.drawOval(new RectF(50, 50, 350, 200), paint);
        canvas.save();

        paint.setColor(Color.BLACK);
        float[] points = {20, 20, 120, 20, 70, 20, 70, 120, 20, 120, 120, 120, 150, 20, 250, 20, 150, 20, 150, 120, 250, 20, 250, 120, 150, 120, 250, 120};
        canvas.drawLines(points, paint);

        paint.setStyle(Paint.Style.FILL); // 填充模式
        canvas.drawArc(new RectF(200, 100, 800, 500), -110, 100, true, paint); // 绘制扇形
        canvas.drawArc(new RectF(200, 100, 800, 500), 20, 140, false, paint); // 绘制弧形
        paint.setStyle(Paint.Style.STROKE); // 画线模式
        canvas.drawArc(new RectF(200, 100, 800, 500), 180, 60, false, paint); // 绘制不封口的弧形

    }
}
