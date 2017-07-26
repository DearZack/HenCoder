package io.github.dearzack.hencoder.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ComposeShader;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.EmbossMaskFilter;
import android.graphics.LightingColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import io.github.dearzack.hencoder.R;

/**
 * Created by Zack on 2017-7-26.
 */

public class HenCoder1_2View extends View {

    private Shader shader1, shader2, shader3, shader4, shader5, shader5_1;
    private Paint paint1, paint2, paint3, paint4, paint5, paint6, paint7;
    private Paint paint8, paint9;
    private Matrix matrix1, matrix2;
    private Bitmap bitmap2;
    private PathEffect pathEffect;
    private Path path;

    public HenCoder1_2View(Context context) {
        super(context);
        init();
    }

    public HenCoder1_2View(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HenCoder1_2View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // 线性渐变
        shader1 = new LinearGradient(100, 100, 300, 300, Color.parseColor("#E91E63"),
                Color.parseColor("#2196F3"), Shader.TileMode.CLAMP);
        // 辐射渐变
        shader2 = new RadialGradient(200, 400, 100, Color.parseColor("#E91E63"),
                Color.parseColor("#2196F3"), Shader.TileMode.CLAMP);
        // 扫描渐变
        shader3 = new SweepGradient(200, 600, Color.parseColor("#E91E63"),
                Color.parseColor("#2196F3"));
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.hen_coder);
        shader4 = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint1 = new Paint();
        paint2 = new Paint();
        paint3 = new Paint();
        paint4 = new Paint();
        paint5 = new Paint();
        paint6 = new Paint();
        paint7 = new Paint();
        paint8 = new Paint();
        paint9 = new Paint();
        paint1.setAntiAlias(true);
        paint2.setAntiAlias(true);
        paint3.setAntiAlias(true);
        paint4.setAntiAlias(true);
        paint5.setAntiAlias(true);
        paint6.setAntiAlias(true);
        paint7.setAntiAlias(true);
        paint8.setAntiAlias(true);
        paint9.setAntiAlias(true);
        matrix1 = new Matrix();
        matrix2 = new Matrix();


        // 第一个 Shader：头像的 Bitmap
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        shader5 = new BitmapShader(bitmap1, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        // 第二个 Shader：从上到下的线性渐变（由透明到黑色）
        shader5_1 = new LinearGradient(100, 900, 300, 1100, Color.parseColor("#00E91E63"),
                Color.parseColor("#FF2196F3"), Shader.TileMode.CLAMP);

        // ComposeShader：结合两个 Shader
        matrix2.setTranslate(100, 900);
        shader5.setLocalMatrix(matrix2);
        Shader shader = new ComposeShader(shader5_1, shader5, PorterDuff.Mode.DST_IN);//有17种模式
        paint5.setShader(shader);

        bitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.batman);
        ColorFilter lightingColorFilter = new LightingColorFilter(0x00ffff, 0x000000);//移除红色
        paint6.setColorFilter(lightingColorFilter);

        pathEffect = new DashPathEffect(new float[]{10, 5}, 10);

        path = new Path();
        path.moveTo(400, 400);
        path.lineTo(400, 500);
        path.lineTo(500, 400);
        path.lineTo(550, 500);
        path.lineTo(650, 300);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint1.setShader(shader1);
        paint2.setShader(shader2);
        paint3.setShader(shader3);
        matrix1.setTranslate(100, 700);
        shader4.setLocalMatrix(matrix1);//对shader4做一个平移
        paint4.setShader(shader4);


        canvas.drawCircle(200, 200, 100, paint1);
        canvas.drawCircle(200, 400, 100, paint2);
        canvas.drawCircle(200, 600, 100, paint3);
        canvas.drawCircle(200, 800, 100, paint4);
        canvas.drawCircle(200, 1000, 100, paint5);
        canvas.drawBitmap(bitmap2, 100, 1100, paint6);
        paint7.setStyle(Paint.Style.STROKE);
        paint7.setStrokeWidth(10);
        paint7.setPathEffect(pathEffect);
        canvas.drawCircle(500, 200, 100, paint7);
        canvas.drawPath(path, paint7);
        PathEffect cornerPathEffect = new CornerPathEffect(20);
        paint7.setPathEffect(cornerPathEffect);
        canvas.translate(0, 150);
        canvas.drawPath(path, paint7);
        PathEffect discretePathEffect = new DiscretePathEffect(20, 5);
        paint7.setPathEffect(discretePathEffect);
        canvas.translate(0, 150);
        canvas.drawPath(path, paint7);
        canvas.translate(0, -300);
        paint8.setShadowLayer(10, 0, 0, Color.RED);
        paint8.setTextSize(60);
        canvas.drawText("HenCoder", 400, 900, paint8);

//        paint9.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.NORMAL));//需要关闭硬件加速
        paint9.setMaskFilter(new EmbossMaskFilter(new float[]{0, 1, 1}, 0.2f, 8, 10));
        canvas.drawBitmap(bitmap2, 400, 900, paint9);

//        Path det = new Path();
//        paint8.getTextPath("HenCoder",0, 8, 400, 900, det);
//        canvas.drawPath(det, paint1);

    }
}
