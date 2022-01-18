package hu.csepel.gyakorlasdb;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Locale;

public class HozzaadController extends Controller {
    @FXML
    private TextField nevTextField;
    @FXML
    private CheckBox forgatasonVanCheckBox;
    @FXML
    private Spinner<Integer> magassagSpinner;
    @FXML
    private DatePicker szuletesiDatumDatePicker;
    @FXML
    private Spinner<Integer> dijakSzamaSpinner;

    @FXML
    public void onHozzaadasClick(ActionEvent actionEvent) {
        String nev = nevTextField.getText().trim();
        int magassag = 0;
        LocalDate szuletesiDatum = szuletesiDatumDatePicker.getValue();
        int dijakSzama = 0;
        boolean forgatasonVan = forgatasonVanCheckBox.isSelected();

        if (nev.isEmpty()) {
            alert("Név mező kitöltése kötelező");
            return;
        }

        try {
            magassag = magassagSpinner.getValue();
        } catch (NullPointerException e) {
            alert("Magasság megadása kötelező");
            return;
        } catch (Exception e) {
            alert("A magasságnak 1 és 999 közötti számnak kell lennie");
            return;
        }
        if (magassag < 1 || magassag > 999) {
            alert("A magasságnak 1 és 999 közötti számnak kell lennie");
            return;
        }

        try {
            szuletesiDatum = szuletesiDatumDatePicker.getValue();
        } catch (NullPointerException e) {
            alert("Születési dátum megadása kötelező");
            return;
        }

        try {
            dijakSzama = dijakSzamaSpinner.getValue();
        } catch (NullPointerException e) {
            alert("Díjak számának megadása kötelező");
            return;
        } catch (Exception e) {
            alert("A díjak számának 1 és 999 közötti számnak kell lennie");
            return;
        }
        if (dijakSzama < 0 || dijakSzama > 999) {
            alert("A díjak számának 0 és 999 közötti számnak kell lennie");
            return;
        }

        try {
            SzineszDb db = new SzineszDb();
            int siker = db.szineszHozzaadasa(nev, magassag, szuletesiDatum, dijakSzama, forgatasonVan);
            if (siker == 1) {
                alert("Sikeres felvétel");
            } else {
                alert("Sikertelen felvétel");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
