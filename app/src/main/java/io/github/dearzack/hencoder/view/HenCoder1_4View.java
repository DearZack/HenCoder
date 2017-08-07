package io.github.dearzack.hencoder.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import io.github.dearzack.hencoder.R;

/**
 * Created by Zack on 2017/8/4.
 */

public class HenCoder1_4View extends View {

    private Paint paint1;
    private Path path1;
    private Bitmap bitmap1;
    private Matrix matrix1;
    private float[] pointsSrc, pointsDst;
    private Camera camera;

    public HenCoder1_4View(Context context) {
        super(context);
        init();
    }

    public HenCoder1_4View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HenCoder1_4View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint1 = new Paint();
        paint1.setAntiAlias(true);
        path1 = new Path();
        bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.maps);
        matrix1 = new Matrix();
        camera = new Camera();
        float left = 0, top = 0, right = bitmap1.getWidth(), bottom = bitmap1.getHeight();
        pointsSrc = new float[]{left, top, right, top, left, bottom, right, bottom};
        pointsDst = new float[]{left - 10, top + 50, right + 120, top - 90, left + 20, bottom + 30, right + 20, bottom + 60};
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //1.剪裁一个矩形
//        canvas.clipRect(200, 50, 620, 500);
        //2.剪裁path
//        path1.setFillType(Path.FillType.INVERSE_WINDING); //取外面
//        path1.addCircle(300, 300, 200, Path.Direction.CW);
//        canvas.clipPath(path1);
        //3.几何变换
//        canvas.translate(200, 0);//平移
//        canvas.rotate(45, 300, 300);//旋转
//        canvas.scale(0.5f, 0.5f, bitmap1.getWidth() / 2, bitmap1.getHeight() / 2);//缩放
//        canvas.skew(0, -0.5f);//错切
//        matrix1.reset();
//        matrix1.setRotate(45, 300, 300);
//        canvas.setMatrix(matrix1);
        //Canvas.setMatrix(matrix)：用 Matrix 直接替换 Canvas 当前的变换矩阵，即抛弃 Canvas 当前的变换，改用 Matrix 的变换；
        //Canvas.concat(matrix)：用 Canvas 当前的变换矩阵和 Matrix 相乘，即基于 Canvas 当前的变换，叠加上 Matrix 中的变换。


//        matrix1.setPolyToPoly(pointsSrc, 0, pointsDst, 0, 4);
//        canvas.setMatrix(matrix1);
        canvas.save();
        camera.save();
        camera.rotateX(30); // 旋转 Camera 的三维空间
        canvas.translate(bitmap1.getWidth() / 2, bitmap1.getHeight() / 2);
        camera.applyToCanvas(canvas); // 把旋转投影到 Canvas
        canvas.translate(-bitmap1.getWidth() / 2, -bitmap1.getHeight() / 2);
        canvas.drawBitmap(bitmap1, 0, 0, paint1);
        canvas.restore();
    }
}