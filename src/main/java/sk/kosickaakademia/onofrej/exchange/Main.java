package sk.kosickaakademia.onofrej.exchange;

import sk.kosickaakademia.onofrej.exchange.api.ApiRequest;
import sk.kosickaakademia.onofrej.exchange.calc.CalcExchangeRates;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Hello world!
 *
 */
public class Main
{
    public static void main( String[] args )
    {
        System.out.println( "Welcome to the Exchange Rates App!" );
        Set<String> set = new HashSet<>();
        set.add("USD");
        set.add("HUF");
        set.add("CZK");
        set.add("BTC");

        CalcExchangeRates calcExchangeRates = new CalcExchangeRates();
        calcExchangeRates.calculate(85);

    }
}
