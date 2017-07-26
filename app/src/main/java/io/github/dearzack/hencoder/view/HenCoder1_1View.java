package io.github.dearzack.hencoder.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
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
//
        drawPath(canvas);
    }

    private void drawPath(Canvas canvas) {
        Path path = new Path();
//        path.addArc(new RectF(200, 200, 400, 400), -225, 225);
//        path.arcTo(new RectF(400, 200, 600, 400), -180, 225, false);
//        path.lineTo(400, 542);
//        path.close();
//        path.addCircle(600, 600, 50, Path.Direction.CW);
//        paint.setStyle(Paint.Style.STROKE);
//        canvas.drawPath(path, paint);
        path.addCircle(300, 400, 125 ,Path.Direction.CW);
        path.addCircle(400, 400, 125 ,Path.Direction.CW);
        paint.setStyle(Paint.Style.FILL);
        //有四种类型 EVEN_ODD, WINDING(默认),已经这两种的反色
        path.setFillType(Path.FillType.EVEN_ODD);
        canvas.drawPath(path, paint);
    }
}
