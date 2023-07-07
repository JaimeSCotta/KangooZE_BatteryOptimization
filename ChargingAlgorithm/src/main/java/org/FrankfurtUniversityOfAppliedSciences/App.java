package org.FrankfurtUniversityOfAppliedSciences;

import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.IOException;
import java.util.Calendar;
import java.util.ListIterator;

public class App
{
    public static void main(String[] args) throws IOException, MqttException {
        App c1 = new App();
        if(args.length != 2){
            System.out.println("!! Parameter required: battery level desired and actual Kangoo SoC !!");
            System.exit(0);
        }
        double battLev = Double.parseDouble(args[0]);
        double kangooBatt = Double.parseDouble(args[1]);

        if(battLev < kangooBatt){
            System.out.println("!! Parameter required: battery level desired should be higher than actual Kangoo SoC !!");
            System.exit(0);
        }
        c1.charge(battLev, kangooBatt);
    }
    private ApiData apiData;
    private MqttConn mqttConn;

    /* Constants */
    private double KangooCapacity = 22; //kWh
    private double pylontechCapacity = 3.6; //kWh
    private double surfacePV = 4.958; //m2

    /* Variables */
    private boolean chargingKangooBattery = false;
    private boolean chargingPylontechBattery = false;
    private boolean completeKangooCharge = false;
    private boolean completePylontechCharge = false;
    private boolean posibleCharge = false;
    private boolean pylontechDischarged = false;
    private double kangooBatteryLevel;
    private double pylontechSoC;
    private double percentageToKwhBattery;
    private double percentageToKwhRequested;
    private double percentageToKwhPylontech;
    private double chargeNecessaryKangooBatt;
    private double chargeNecessaryPylontechBatt;
    private double accumulatedKangooCharge = 0.0;
    private double accumulatedPylontechCharge = 0.0;

    private int necessaryHours = 0;



    public App() throws IOException, MqttException {
        apiData = new ApiData();
        mqttConn = new MqttConn();
    }

    private void charge(double batteryLevelCharge, double kangooBatteryLevel){

        ListIterator iterator = apiData.getEnergyValues().listIterator();
        pylontechSoC = mqttConn.getBatteryCharge();

        while (mqttConn.getBatteryCharge() == 0.0) {
            try {
                Thread.sleep(1000); //wait till it receives the data from the Pylontech battery
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        pylontechSoC = mqttConn.getBatteryCharge();
        double pvEnergy =0.0;

        percentageToKwhBattery = (kangooBatteryLevel * KangooCapacity)/100;
        percentageToKwhRequested =(batteryLevelCharge * KangooCapacity)/100;
        percentageToKwhPylontech = (pylontechSoC * pylontechCapacity)/100;

        chargeNecessaryKangooBatt = percentageToKwhRequested - percentageToKwhBattery; //kWh
        chargeNecessaryPylontechBatt = pylontechCapacity - percentageToKwhPylontech;

        Calendar calendar = Calendar.getInstance();
        int h = calendar.get(Calendar.HOUR_OF_DAY);
        int hAux1 = 0,hAux2 = 0;

        if(chargeNecessaryPylontechBatt == 0){
            chargingPylontechBattery = false;
            accumulatedPylontechCharge = pylontechCapacity;
        }
        else {
            chargingPylontechBattery = true;
        }

        if(chargeNecessaryKangooBatt == 0){
            chargingKangooBattery = false;
        }
        else {
            chargingKangooBattery = true;
        }

        // "Do not read values before the current time.
        // Start receiving energy values from the moment the application is executed"
        for (int i = 0; i < h; i++) {
            iterator.next();
            hAux1 ++;
            hAux2 ++;
        }



        while(!completeKangooCharge){
            while (!completePylontechCharge) {
                if (hAux2 >= 21) {
                    posibleCharge = false;
                    completePylontechCharge = true;
                }
                else {
                    if(pylontechDischarged){
                        chargeNecessaryPylontechBatt = pylontechCapacity;
                    }
                    double energyReceived = Double.parseDouble(iterator.next().toString());
                    pvEnergy = ((surfacePV * energyReceived)/ 1000) * 0.90;
                    accumulatedPylontechCharge = accumulatedPylontechCharge + pvEnergy;
                    necessaryHours++;
                    hAux2++;
                    hAux1++;
                    System.out.println("Charge Pylontech");

                    if (accumulatedPylontechCharge >= chargeNecessaryPylontechBatt) {
                        completePylontechCharge = true;
                        accumulatedPylontechCharge = pylontechCapacity;
                    }
                }
            }

            if (hAux1 >= 21) {
                posibleCharge = false;
                completeKangooCharge = true;
            }

            pvEnergy = ((surfacePV * Double.parseDouble(iterator.next().toString()))/1000)*0.90;

            accumulatedKangooCharge = accumulatedKangooCharge + pvEnergy + (0.288*0.75);
            necessaryHours ++;
            System.out.println("Charge Kangoo battery");
            hAux2 ++;
            hAux1 ++;
            //pylon discharge:
            accumulatedPylontechCharge = accumulatedPylontechCharge - 0.288;

            if (accumulatedPylontechCharge < 1.08){ //do not go down of the ~30% of the Pylontech battery charge
                completePylontechCharge = false;
                pylontechDischarged = true;
            }

            if(accumulatedKangooCharge >= chargeNecessaryKangooBatt){
                completeKangooCharge = true;
                posibleCharge = true;
            }
        }
    }
}


