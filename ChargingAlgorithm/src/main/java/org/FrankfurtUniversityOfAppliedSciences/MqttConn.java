package org.FrankfurtUniversityOfAppliedSciences;


import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttConn {

    private String topic, clientID;
    private MqttClient client;
    public double batteryCharge;

    public MqttConn() throws MqttException {
        try {
            init();
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    public void init() throws MqttException {
        clientID = "xxx";
        topic = "solpiplog/socvalue";
        client = new MqttClient("tcp://broker.hivemq.com:1883", clientID, new MemoryPersistence());
        connect();
    }

    private void connect() {
        try {
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            client.connect();
            sub();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void sub() {
        try {
            client.subscribe(topic, 2);
            client.setCallback(new MqttCallback() {

                public void connectionLost(Throwable cause) {
                }

                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String data = new String(message.getPayload());
                    batteryCharge = Double.parseDouble(data);
                    client.close();
                }

                public void deliveryComplete(IMqttDeliveryToken token) {
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public double getBatteryCharge() {
        return batteryCharge;
    }
}
