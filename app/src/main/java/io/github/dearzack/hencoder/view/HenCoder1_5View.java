package io.github.dearzack.hencoder.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Zack on 2017/8/4.
 * 1.在 ViewGroup 的子类中重写除 dispatchDraw() 以外的绘制方法时，可能需要调用  setWillNotDraw(false)；
 * 2.在重写的方法有多个选择时，优先选择 onDraw()。
 */

public class HenCoder1_5View extends View {

    private Paint paint1, paint2;

    public HenCoder1_5View(Context context) {
        super(context);
        init();
    }

    public HenCoder1_5View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HenCoder1_5View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint1 = new Paint();
        paint1.setAntiAlias(true);
        paint1.setColor(Color.RED);
        paint2 = new Paint();
        paint2.setAntiAlias(true);
        paint2.setColor(Color.BLUE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(100, 100, 300, 300, paint2);
        canvas.drawCircle(200, 200, 120, paint1);
    }
}
