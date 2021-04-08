package sk.kosickaakademia.onofrej.exchange.calc;

import sk.kosickaakademia.onofrej.exchange.api.ApiRequest;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CalcExchangeRates {

    // zoznam mien, do ktorych budeme robit konverziu
    private static final String[] rates= new String[]{"USD","CZK","HUF","BTC","PLN"};

    // tato metoda sa pouziva zvonka na konverziu z eura na ine meny.
    public void calculate(double eur){
        // ak je vstup zaporny, informujeme pouzivate o zlom vstupe a prevod sa nerobi
        if(eur<0){
            System.out.println("Input param cannot be a negative value!");
            return;
        }

        // huraaaaa, vstup je ok
        // prekopirujeme zoznam - pole String[] do kolekcie HashSet
        Set<String> set = new HashSet<>();
        Collections.addAll(set, rates);

        // zavolame metodu getExchangeRates, ktora nam posle vsetky kurzy, ktore pozadujeme
        ApiRequest apiRequest=new ApiRequest();
        Map map = apiRequest.getExchangeRates(set);

        // prechadzame polom pozadovanych mien , pre ktore robime konverziu
        for(String temp:rates){
            // ak dana mena sa nasla a mame aj kurz, robime prepocet
            if(map.containsKey(temp)){  // temp ... skratka meny, na ktoru robime prevod
                double value = (double)map.get(temp);  // vymenny kurz
                double result = eur*value;   // prepocitany vysledok

                // volame pomocnu metodu ktora vypisuje vysledok
                print("EUR",temp, eur, result, value);
            }
        }
    }

    // pomocna metoda, sluzi na vypis vysledkov
    // vypisujeme aj detaily
    private void print(String from, String to, double eur, double result, double rate){
        System.out.println(eur +" "+from+" -> "+result+" "+to+" (exchange rate: "+rate+" )");
    }
}
