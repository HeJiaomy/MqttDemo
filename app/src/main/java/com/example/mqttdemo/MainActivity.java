package com.example.mqttdemo;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    private TopicAdapter topicAdapter;
    private DataSetObserver dataSetObserver;

    private String serverURI = "tcp://192.168.43.123:1883";
    private String clientId = "HJ'AndroidClient";
    private String subscriptionTopic = "exampleAndroidTopic";
    private String publishTopic = "exampleAndroidPublishTopic";
    private String publishMessage = "Hello World!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        topicAdapter= new TopicAdapter();

        findViewById(R.id.floatButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataSetObservable.getInstance(getApplicationContext()).onCreate();
                assemble(serverURI,clientId,subscriptionTopic,publishTopic,publishMessage);
                topicAdapter.registerDataSetObserver(dataSetObserver);
                topicAdapter.subscribeUpdate(dataSetObserver);
            }
        });

    }


    public void assemble(@NonNull String serverUri, @NonNull String clientId, @NonNull String subscriptionTopic,
                         @NonNull String publishTopic, @NonNull String publishMessage) {

        dataSetObserver= new DataSetObserver();
        dataSetObserver.setServerURI(serverUri);
        dataSetObserver.setClientId(clientId);
        dataSetObserver.setSubscriptionTopic(subscriptionTopic);
        dataSetObserver.setPublishTopic(publishTopic);
        dataSetObserver.setPublishMessage(publishMessage);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        topicAdapter.unRegisterDataSetObserver(dataSetObserver);
    }
}
