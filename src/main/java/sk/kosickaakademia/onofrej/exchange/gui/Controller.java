package sk.kosickaakademia.onofrej.exchange.gui;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import sk.kosickaakademia.onofrej.exchange.calc.CalcExchangeRates;

import java.text.DecimalFormat;
import java.util.Map;

public class Controller {
    private static final String[] rates= new String[]{"HRK","CZK","UAH","BTC","PLN","TRY"};
    public TextField txtEur;
    public TextField txtUAH;
    public TextField txtPLN;
    public TextField txtHRK;
    public TextField txtCZK;
    public TextField txtBTC;
    public TextField txtTRY;

    public void clickConvert(ActionEvent actionEvent) {
        String value = txtEur.getText();
        double valueEur=Double.parseDouble(value);
        CalcExchangeRates cer=new CalcExchangeRates();
        Map results = cer.calculate(valueEur,rates);
        txtCZK.setText(convertTo2Decimal((double)results.get("CZK")));
        txtHRK.setText(convertTo2Decimal((double)results.get("HRK")));
        txtUAH.setText(convertTo2Decimal((double)results.get("UAH")));
        txtPLN.setText(convertTo2Decimal((double)results.get("PLN")));
        txtTRY.setText(convertTo2Decimal((double)results.get("TRY")));
        txtBTC.setText(results.get("BTC").toString());
    }

    private String convertTo2Decimal(double value){
        DecimalFormat df = new DecimalFormat("#.00");
        String angleFormated = df.format(value);
        return angleFormated;
    }
}
