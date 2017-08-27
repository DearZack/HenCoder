package io.github.dearzack.hencoder.activity2;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import io.github.dearzack.hencoder.BuildConfig;
import io.github.dearzack.hencoder.R;
import io.github.dearzack.hencoder.bean.LockBean;

public class NormalActivity extends AppCompatActivity {

    private static final String TAG = "NormalActivity";

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);
//        threadLocalTest();
//        deadLockTest();
        //gradle高级配置 相关文章
        //https://mp.weixin.qq.com/s?__biz=MzAxMTI4MTkwNQ==&mid=2650823843&idx=1&sn=b37cdf30f2e1938b0ac88cf4fc1eb331&chksm=80b7883db7c0012bfd66cefc3669f25d3ebda8b840b592d7c7d439d0e730061735ecdebf0de6&mpshare=1&scene=1&srcid=0823tCkOaYqaBNfzQh7PNrsL#rd
        //http://git.oschina.net/janus77/advancedgradledemo
        Log.e(TAG, BuildConfig.val);
        imageView = (ImageView) findViewById(R.id.image);
        imageView.setImageResource(R.drawable.maps);
        Log.e(TAG, "imageView.getHeight()"+imageView.getHeight() +"mageView.getWidth()" +imageView.getWidth());

    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.e(TAG, "imageView.getHeight()"+imageView.getHeight() +"mageView.getWidth()" +imageView.getWidth());
//        imageView.setImageBitmap(BitmapUtils.decodeSampledBitmapFromResource(getResources(),
//                R.drawable.maps, imageView.getWidth(), imageView.getHeight()));
        imageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

            }
        });
    }

    /**
     * 备忘录模式
     * 依次存储当前窗口的视图、Fragment、ActivityLifecycleCallbacks
     * <p>
     * 需要注意的是没有ID的view的状态是不会被保存的，
     * 保存状态有一个map，key就是这个view的id，如果没有id会被跳过
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
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
     * 产生死锁的四个必要条件：
     * （1） 互斥条件：一个资源每次只能被一个进程使用。
     * （2） 请求与保持条件：一个进程因请求资源而阻塞时，对已获得的资源保持不放。
     * （3） 不剥夺条件:进程已获得的资源，在末使用完之前，不能强行剥夺。
     * （4） 循环等待条件:若干进程之间形成一种头尾相接的循环等待资源关系。
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

    /**
     * 优先用obtain而不是new对象出来
     * 用到了享元模式，Message内部维护了一个pool（链表形式）
     * 如果有缓存会先去取，如果没有缓存会去new
     * 享元模式可以大大减少应用程序创建的对象，减低程序内存的占用
     */
    private void messageTest() {
        Message message = Message.obtain();
    }

    private class DeadLockThread extends Thread {
        private final LockBean lockBean1;
        private final LockBean lockBean2;

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
