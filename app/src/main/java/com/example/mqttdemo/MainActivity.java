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
    private String publishMessage = "你好，我是发送的消息";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        topicAdapter = new TopicAdapter();
        final DataSetObserver dataSetObserver = assemble(serverURI, clientId, subscriptionTopic, publishTopic, publishMessage);
        DataSetObservable.getInstance().onCreate(dataSetObserver);
        findViewById(R.id.floatButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                topicAdapter.subscribeUpdate(dataSetObserver);
            }
        });

    }


    public DataSetObserver assemble(@NonNull String serverUri, @NonNull String clientId, @NonNull String subscriptionTopic,
                                    @NonNull String publishTopic, @NonNull String publishMessage) {
        dataSetObserver = new DataSetObserver();
        dataSetObserver.setServerURI(serverUri);
        dataSetObserver.setClientId(clientId);
        dataSetObserver.setSubscriptionTopic(subscriptionTopic);
        dataSetObserver.setPublishTopic(publishTopic);
        dataSetObserver.setPublishMessage(publishMessage);
        return dataSetObserver;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
