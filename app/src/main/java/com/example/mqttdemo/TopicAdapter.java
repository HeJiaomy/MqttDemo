package com.example.mqttdemo;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * 具体被观察者
 * 主题
 */
public class TopicAdapter  implements SubscribeChangeListener{

    public static final String TAG = "TopicAdapter";
    private List<DataSetObserver> observerList= new ArrayList<>();

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        observerList.add(observer);
    }

    @Override
    public void unRegisterDataSetObserver(DataSetObserver observer) {
        observerList.remove(observer);
    }

    @Override
    public void subscribeUpdate(DataSetObserver observer) {
        for (DataSetObserver dataSetObserver : observerList) {
            dataSetObserver.notifyChange(observer);
            Log.e(TAG,dataSetObserver.toString());
        }
    }
}
