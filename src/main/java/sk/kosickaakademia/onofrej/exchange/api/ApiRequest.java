package sk.kosickaakademia.onofrej.exchange.api;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class ApiRequest {
    public Map getExchangeRates(Set<String> rates){
        if(rates==null || rates.size() ==0)
            return null;

        return parseData(rates);
    }

    private String getRatesFromAPIServer(){
        try {

            URL url = new URL("http://api.exchangeratesapi.io/v1/latest?access_key=7f193180007ca666e168d9cd573780e6&format=1");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Getting the response code
            int responsecode = conn.getResponseCode();

            if (responsecode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            } else {

                String inline = "";
                Scanner scanner = new Scanner(url.openStream());

                //Write all the JSON data into a string using a scanner
                while (scanner.hasNext()) {
                    inline += scanner.nextLine();
                }

                //Close the scanner
                scanner.close();

                return inline;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Map parseData(Set<String> rates){
        String inline=getRatesFromAPIServer();
        if(inline==null)
            return null;

        try {
            //Using the JSON simple library parse the string into a json object
                JSONParser parse = new JSONParser();
                JSONObject data_obj = (JSONObject) parse.parse(inline);

                //Get the required object from the above created object
                JSONObject obj = (JSONObject) data_obj.get("rates");

                Map<String,Double> maps = new HashMap<>();
                for(String temp:rates){
                    if(obj.containsKey(temp)){
                        double value= (double)obj.get(temp);
                        maps.put(temp,value);
                    }
                }
                return maps;

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
