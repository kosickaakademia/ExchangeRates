package sk.kosickaakademia.onofrej.exchange;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sk.kosickaakademia.onofrej.exchange.api.ApiRequest;
import sk.kosickaakademia.onofrej.exchange.calc.CalcExchangeRates;
import sk.kosickaakademia.onofrej.exchange.database.DatabaseMongo;

import java.io.File;
import java.net.URL;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Hello world!
 *
 */
public class Main extends Application
{
    public static void main( String[] args )
    {
        /*DatabaseMongo dm = new DatabaseMongo();
        dm.test();*/
        /*System.out.println( "Welcome to the Exchange Rates App!" );
        Set<String> set = new HashSet<>();
        set.add("USD");
        set.add("HUF");
        set.add("CZK");
        set.add("BTC");*/

        CalcExchangeRates calcExchangeRates = new CalcExchangeRates();
        calcExchangeRates.calculate(85);
        launch(Main.class, (java.lang.String)null);


    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        URL url = new File("src/main/java/sk/kosickaakademia/onofrej/exchange/gui/form.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);

        primaryStage.setTitle("Exchange convertor");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();
    }
}
