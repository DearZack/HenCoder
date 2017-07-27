package io.github.dearzack.hencoder.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Zack on 2017/7/26.
 */

public class HenCoder1_3View extends View {

    private Paint paint1, paint2;
    private TextPaint textPaint1;
    private Path path1;
    private StaticLayout staticLayout;

    public HenCoder1_3View(Context context) {
        super(context);
        init();
    }

    public HenCoder1_3View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HenCoder1_3View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint1 = new Paint();
        paint2 = new Paint();
        textPaint1 = new TextPaint();
        paint1.setAntiAlias(true);
        paint2.setAntiAlias(true);
        textPaint1.setAntiAlias(true);
        textPaint1.setTextSize(40);
        paint1.setTextSize(40);
        paint1.setStyle(Paint.Style.STROKE);
        paint2.setStrikeThruText(true);
        paint2.setTextSize(40);
        path1 = new Path();
        path1.moveTo(100, 200);
        path1.lineTo(200, 300);
        path1.lineTo(300, 100);
        path1.lineTo(400, 200);
        staticLayout = new StaticLayout("paint,draw paint指用颜色画,如油画颜料、水彩或者水墨画,而draw 通常指用铅笔、钢笔或者粉笔画,后者一般并不涂上颜料。两动词的相应名词分别为p ",
                textPaint1, 500, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        PathEffect cornerPathEffect = new CornerPathEffect(40);
        paint1.setPathEffect(cornerPathEffect);
        canvas.drawPath(path1, paint1);
        canvas.drawTextOnPath("Hello HenCoder", path1, 0, 0, paint1);
        canvas.translate(0, 400);
        staticLayout.draw(canvas);
        canvas.drawText("一条删除线哟", 10, 350, paint2);
    }
}