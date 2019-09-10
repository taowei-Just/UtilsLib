package com.tao.handler;

import android.app.Activity;
import android.app.Fragment;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2019-9-9.
 */

public abstract class WeakRunable<T> implements Runnable {

    protected WeakReference<T> objWeakReference;
    private boolean autocancel = false;

    private WeakRunable() {
    }

    public WeakRunable(T obj) {
        this.objWeakReference = new WeakReference<>(obj);
    }

    public void setAutocancel(boolean autocancel) {
        this.autocancel = autocancel;
    }

    @Override
    public void run() {
        if (null == objWeakReference || null == objWeakReference.get()) {
            if (autocancel)
                return;
        }
        T obj = objWeakReference.get();
        if (obj instanceof Activity) {
            if (((Activity) obj).isDestroyed() || ((Activity) obj).isFinishing()) {
                if (autocancel)
                    return;
            }
        } else if (obj instanceof Fragment) {
            if (((Fragment) obj).isRemoving()) {
                if (autocancel)
                    return;
            }
        } else {
            dorun();
        }
    }

    public abstract void dorun();
}
