package io.github.dearzack.hencoder.activity2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.github.dearzack.hencoder.R;
import io.github.dearzack.hencoder.bean.LockBean;

public class NormalActivity extends AppCompatActivity {

    private static final String TAG = "NormalActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);
//        threadLocalTest();
        deadLockTest();
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

    /**
     *
      产生死锁的四个必要条件：
     （1） 互斥条件：一个资源每次只能被一个进程使用。
     （2） 请求与保持条件：一个进程因请求资源而阻塞时，对已获得的资源保持不放。
     （3） 不剥夺条件:进程已获得的资源，在末使用完之前，不能强行剥夺。
     （4） 循环等待条件:若干进程之间形成一种头尾相接的循环等待资源关系。
     */
    private void deadLockTest() {
        LockBean lockBean1 = new LockBean("A");
        LockBean lockBean2 = new LockBean("B");

        DeadLockThread deadLockThread1 = new DeadLockThread(lockBean1, lockBean2);
        DeadLockThread deadLockThread2 = new DeadLockThread(lockBean2, lockBean1);
        deadLockThread1.start();
        deadLockThread2.start();

        //如果没有打印出"OVER",说明产生了死锁
    }

    class DeadLockThread extends Thread {
        private LockBean lockBean1;
        private LockBean lockBean2;

        public DeadLockThread(LockBean lockBean1, LockBean lockBean2) {
            this.lockBean1 = lockBean1;
            this.lockBean2 = lockBean2;
        }

        public void run() {
            synchronized (lockBean1) {
                try {
                    Log.e(TAG, Thread.currentThread() + " synchronized " + lockBean1.toString());
                    Thread.sleep(1000);
                    synchronized (lockBean2) {
                        Log.e(TAG, "OVER");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
