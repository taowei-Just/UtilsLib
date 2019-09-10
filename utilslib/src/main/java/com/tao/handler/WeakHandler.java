package com.tao.handler;

import android.app.Activity;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2019-9-9.
 */

public abstract class WeakHandler<T> extends Handler {
    protected WeakReference<T> objWeakReference;
    private boolean autoRemove =false ;

    //构造私有化,让调用者必须传递一个Activity 或者 Fragment的实例
    private WeakHandler() {
    }

    public WeakHandler(T obj) {
        this.objWeakReference = new WeakReference<>(obj);
    }

    public void setAutoRemove(boolean autoRemove) {
        this.autoRemove = autoRemove;
    }

    @Override
    public void handleMessage(Message msg) {

        if (null == objWeakReference || null == objWeakReference.get()) {
            handleMessage(msg, OBJ_NULL_WHAT);
            return;
        }
        
        T obj = objWeakReference.get();
        if (obj instanceof Activity) {
            if (((Activity) obj).isDestroyed() || ((Activity) obj).isFinishing()) {
                handleMessage(msg, ACTIVITY_ISFINSH_WHAT);
                if (autoRemove)
                    removeCallbacksAndMessages(null);
                return;
            }
        } else if (obj instanceof Fragment) {

            if ( ((Fragment) obj).isRemoving()) {
                handleMessage(msg, FRAGMENT_ISREMOVING_WHAT);
                if (autoRemove)
                removeCallbacksAndMessages(null);
                return;
            }
        }  
        handleMessage(msg, msg.what);
    }

    /**
     * 抽象方法用户实现,用来处理具体的业务逻辑
     *
     * @param msg
     * @param what
     */
    public abstract void handleMessage(Message msg, int what);


    public static final int OBJ_NULL_WHAT = -100000;
    public static final int ACTIVITY_ISFINSH_WHAT = -100001;
    public static final int FRAGMENT_ISREMOVING_WHAT = -100002;
}
 