package com.example.mqttdemo;

public interface SubscribeChangeListener {
    void registerDataSetObserver(DataSetObserver observer);
    void unRegisterDataSetObserver(DataSetObserver observer);
    void subscribeUpdate(DataSetObserver observer);
}
