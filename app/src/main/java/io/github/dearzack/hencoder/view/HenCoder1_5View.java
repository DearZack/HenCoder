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
 *
 * 绘制流程
 * 1.drawBackground()  不能被重写
 * 2.onDraw()
 * 3.dispatchDraw()   一般是viewGroup会有这一步
 * 4.onDrawForeground()
 *
 *
 * 1、2、3、4这四部都是在draw（）函数里被调用的，顺序永远不会被改变
 * 逻辑写在不同的地方会有不同的效果
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
