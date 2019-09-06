package com.example.mqttdemo;

import android.util.Log;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * 具体观察者
 */
public class DataSetObservable {

    private static DataSetObservable dataSetObservable;

    public static DataSetObservable getInstance() {
        if (dataSetObservable == null) {
            synchronized (DataSetObservable.class) {
                if (dataSetObservable == null) {
                    dataSetObservable = new DataSetObservable();
                }
            }
        }
        return dataSetObservable;
    }

    private static final String TAG = "DataSetObservable";

    private MqttAndroidClient mqttAndroidClient;

    private DataSetObserver dataSetObserver;

    public void onCreate(final DataSetObserver dataSetObserver) {
        this.dataSetObserver = dataSetObserver;
        if (dataSetObserver == null)
            throw new NullPointerException("NullPointerException:DataSetObserver is a null object!");
        mqttAndroidClient = new MqttAndroidClient(MyApplication.getContext(), dataSetObserver.getServerURI(), dataSetObserver.getClientId());
        mqttAndroidClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                if (reconnect) {
                    Log.d(TAG, "Reconnect serverURI:" + serverURI);
                    subscribeToTopic();
                } else {
                    Log.d(TAG, "connect serverURI:" + serverURI);
                }
            }

            @Override
            public void connectionLost(Throwable cause) {
                Log.d(TAG, "The Connection was lost.");
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {
                Log.d(TAG, "InComing message: " + new String(message.getPayload()));
                showMessage("InComing message: " + new String(message.getPayload()));
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });

        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(false);

        try {
            mqttAndroidClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.d(TAG, "Success to connect: " + dataSetObserver.getServerURI());
                    DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                    disconnectedBufferOptions.setBufferEnabled(true);
                    disconnectedBufferOptions.setBufferSize(100);
                    disconnectedBufferOptions.setPersistBuffer(false);
                    disconnectedBufferOptions.setDeleteOldestMessages(false);
                    mqttAndroidClient.setBufferOpts(disconnectedBufferOptions);
                    subscribeToTopic();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    showMessage("Failed to connect: " + dataSetObserver.getServerURI());
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void subscribeToTopic() {

        try {
            mqttAndroidClient.subscribe(dataSetObserver.getSubscriptionTopic(), 0, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.d(TAG, "Subscribed!");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.d(TAG, "Failed to subscribe!");
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void publishMessage(DataSetObserver dataSetObserver) {
        try {
            MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setPayload(dataSetObserver.getPublishMessage().getBytes());
            mqttAndroidClient.publish(dataSetObserver.getPublishTopic(), mqttMessage);
//            if (!mqttAndroidClient.isConnected()) {
//                showMessage(mqttAndroidClient.getBufferedMessageCount() + " messages in buffer.");
//            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void showMessage(String mainText) {
//        Snackbar.make(findViewById(android.R.id.content), mainText, Snackbar.LENGTH_LONG)
//                .setAction("Action",null).show();
        Toast.makeText(MyApplication.getContext(), mainText, Toast.LENGTH_LONG).show();
    }
}
