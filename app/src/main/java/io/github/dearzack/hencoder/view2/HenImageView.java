package io.github.dearzack.hencoder.view2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import io.github.dearzack.hencoder.BuildConfig;

/**
 * Created by Zack on 2017/8/14.
 */

public class HenImageView extends AppCompatImageView {

    private Paint paint1;

    public HenImageView(Context context) {
        super(context);
        init();
    }

    public HenImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HenImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint1 = new Paint();
        paint1.setAntiAlias(true);
        paint1.setColor(Color.RED);
        paint1.setTextSize(20);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (BuildConfig.DEBUG) {
            String text = "尺寸：" + this.getWidth() + "*" + this.getHeight();
            canvas.drawText(text, 20, 20, paint1);
        }
    }
}
