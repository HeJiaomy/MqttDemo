package com.example.mqttdemo;

public class DataSetObserver implements ObservableChangeListener{

    private String clientId = "HJ'AndroidClient";
    private String serverURI = "tcp://192.168.43.123:1883";
    private String subscriptionTopic = "exampleAndroidTopic";
    private String publishTopic = "exampleAndroidPublishTopic";
    private String publishMessage = "Hello World!";

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getServerURI() {
        return serverURI;
    }

    public void setServerURI(String serverURI) {
        this.serverURI = serverURI;
    }

    public String getSubscriptionTopic() {
        return subscriptionTopic;
    }

    public void setSubscriptionTopic(String subscriptionTopic) {
        this.subscriptionTopic = subscriptionTopic;
    }

    public String getPublishMessage() {
        return publishMessage;
    }


    public void setPublishMessage(String publishMessage) {
        this.publishMessage = publishMessage;
    }

    public String getPublishTopic() {
        return publishTopic;
    }

    public void setPublishTopic(String publishTopic) {
        this.publishTopic = publishTopic;
    }

    @Override
    public String toString() {
        return "DataSetObserver{" +
                "clientId='" + clientId + '\'' +
                ", serverURI='" + serverURI + '\'' +
                ", subscriptionTopic='" + subscriptionTopic + '\'' +
                ", publishTopic='" + publishTopic + '\'' +
                ", publishMessage='" + publishMessage + '\'' +
                '}';
    }

    @Override
    public void notifyChange(DataSetObserver observer) {
        DataSetObservable.getInstance().publishMessage(observer);
    }
}
