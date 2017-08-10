package io.github.dearzack.hencoder.activity2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.github.dearzack.hencoder.R;

public class NormalActivity extends AppCompatActivity {

    private static final String TAG = "NormalActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);
        threadLocalTest();
    }

    /**
     * @see ThreadLocal 学习使用这个类
     * 不同的线程set的值都会有单独的副本
     * 参考资料 http://blog.csdn.net/singwhatiwanna/article/details/48350919
     */
    private void threadLocalTest() {
        final ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
        threadLocal.set(9999);
        for (int i = 0; i < 10; i++) {
            final int j = i;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, "目前在线程---" + Thread.currentThread().getName()
                            + "---ThreadLocal.get()的值为" + threadLocal.get());
                    threadLocal.set(j);
                    Log.e(TAG, "目前在线程---" + Thread.currentThread().getName()
                            + "---ThreadLocal.get()的值为" + threadLocal.get());
                }
            }, "我是线程:" + i);
            thread.start();
        }
        Log.e(TAG, "目前在线程---" + Thread.currentThread().getName()
                + "---ThreadLocal.get()的值为" + threadLocal.get());
    }
}
