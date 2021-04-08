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

    // kluc ziskany z registracie na API serveri
  private static String key  = "7f193180007ca666e168d9cd573780e6";

    // verejna metoda, tato metoda sa bude volat zvonka (ine triedy)
    // ak niekto bude potrebovat nejaky kurz
    // ako vstup: zoznam mien, ktore pozadujeme v kolekcii Set
    public Map getExchangeRates(Set<String> rates){
        if(rates==null || rates.size() ==0) // kontrola ci mame vstup
            return null;

        // poziadame metodu parseData aby nam vratila zoznam kurzov, ktore jej posleme ako parameter
        return parseData(rates);
    }

    // tato metoda zabezpecuje connection na API server s danym key
    private String getRatesFromAPIServer(){
        try {

            // cesta k endpointu
            URL url = new URL("http://api.exchangeratesapi.io/v1/latest?access_key="+key+"&format=1");

            // objekt premennej na connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");  // api request vyuziva GET method
            conn.connect();

            //Getting the response code
            int responsecode = conn.getResponseCode();

            // ak je navratovy kod zo servera - status : 200 huraaaaaa, vsetko je OK
            // ak to nie je 200, mame problem, volame exception s cislom chyby
            if (responsecode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            } else {
                // huraaa
                String inline = "";  // tu ulozime odpoved zo servera
                Scanner scanner = new Scanner(url.openStream());

                //Write all the JSON data into a string using a scanner
                while (scanner.hasNext()) {
                    inline += scanner.nextLine();
                }

                // v premennej inline mame komplet cely vysledok zo servera, ako String, nie ako JSON object

                //Close the scanner
                scanner.close();

                // vratime odpoved zo servera ako String
                return inline;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // ka nstal nejaky problem, napr. s pripojenim na server alebo nespravny token/key
        // tak namiesto dat vratime null
        return null;
    }

    // tato metoda prijme zoznam kurzov, ktore pozadujeme
    private Map parseData(Set<String> rates){

        // poziadame metodu aby sa pripojila na API server a vyaziadala cely balik kurzov
        String inline=getRatesFromAPIServer();

        // ak zo servera neprisla odpoved, napr. kurzy sa nenali alebo bol zly kluc/token
        // tak musime return null - t.z. ze nemame ziadne vysledky
        if(inline==null)
            return null;

        // mame vysledky v String formate !!!!!!! huraaaaaaa, podme parsovat
        // vysledky - String je ulozeny v inline premennej
        try {
            //Using the JSON simple library parse the string into a json object
                JSONParser parse = new JSONParser();   // vytvorime objekt triedy na parsovanie
                JSONObject data_obj = (JSONObject) parse.parse(inline);  // rozparsujeme string na JSON objekt

                //z clekovejho json objektu, ktory obsahuje aj ine detaily ako kurzy
            // vyberieme len objekt s kurzami
                JSONObject obj = (JSONObject) data_obj.get("rates");

                // vytvorime si objekt hashmapy, ktory budeme naplnat vysledkami a vratime
                Map<String,Double> maps = new HashMap<>();

                // tento cyklu prechadza cele vstupny set, a po jednom vybera pozadovane kurzy
                // postupne sa vsetky kurzy ukladaju do temp a ten sa spracuvava
                for(String temp:rates){

                    // zistime ci dany kurz/key je spravny a ci existuje vo vysledoch zo servera
                    if(obj.containsKey(temp)){
                        // ak ano, vyberieme si value - konkretny kurz pre danu menu
                        double value= (double)obj.get(temp);
                        // pridame klus:value do vysledkovej premennej
                        maps.put(temp,value);
                    }
                }
                //vysledok, cely zoznam kluc:value vratime
                return maps;

        }catch(Exception ex){
            ex.printStackTrace();
        }

        // pokial nastal problem, vratime null
        return null;
    }
}
