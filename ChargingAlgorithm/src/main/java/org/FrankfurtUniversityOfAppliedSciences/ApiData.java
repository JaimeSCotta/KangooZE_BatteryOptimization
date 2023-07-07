package org.FrankfurtUniversityOfAppliedSciences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;

public class ApiData {

    private LinkedList energyValues = new LinkedList();

    public ApiData() throws IOException {
        try {
            getApiData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void getApiData() throws IOException {

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL("https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/Frankfurt?unitGroup=metric&key=VY6RFJH3L2GT57SKLFUUFX7L6&contentType=json&elements=datetime,ghiradiation,dniradiation,difradiation,gtiradiation,sunazimuth,sunelevation&solarTiltAngle=45");
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            try {
                JSONObject jsonObject = new JSONObject(buffer.toString());
                JSONArray array = new JSONArray(jsonObject.getJSONArray("days"));

                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObject2 = array.getJSONObject(i);
                    JSONArray hoursArray = new JSONArray(jsonObject2.getJSONArray("hours"));

                    for (int j = 0; j < hoursArray.length(); j++) {
                        JSONObject jsonObject3 = hoursArray.getJSONObject(j);
                        BigDecimal ghiradiation = jsonObject3.getBigDecimal("ghiradiation");

                        energyValues.add(ghiradiation);

                    }
                }

                if (connection != null) {
                    connection.disconnect();
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public LinkedList getEnergyValues() {
        return energyValues;
    }

    public void readVal(LinkedList energyValues){
        Iterator it = energyValues.iterator();

        while (it.hasNext()){
            System.out.println(it.next());
        }
    }
}