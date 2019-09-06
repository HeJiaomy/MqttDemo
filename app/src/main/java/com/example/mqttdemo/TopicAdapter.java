package com.example.mqttdemo;

import android.util.Log;

/**
 * 具体被观察者
 * 主题
 */
public class TopicAdapter implements SubscribeChangeListener {

    private static final String TAG = "TopicAdapter";
    private DataSetObserver dataSetObserver = new DataSetObserver();

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unRegisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void subscribeUpdate(DataSetObserver observer) {
        dataSetObserver.notifyChange(observer);
        Log.d(TAG, dataSetObserver.toString());
    }
}
