package hu.csepel.gyakorlasdb;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.time.LocalDate;

public class ModositasController extends Controller {
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
    private Szinesz modositandoSzinesz;

    public void setModositandoSzinesz(Szinesz modositandoSzinesz) {
        this.modositandoSzinesz = modositandoSzinesz;
        ertekekBeallitasa();
    }

    private void ertekekBeallitasa() {
        nevTextField.setText(modositandoSzinesz.getNev());
        magassagSpinner.getValueFactory().setValue(modositandoSzinesz.getMagassag());
        szuletesiDatumDatePicker.setValue(modositandoSzinesz.getSzuletesiDatum());
        dijakSzamaSpinner.getValueFactory().setValue(modositandoSzinesz.getDijakSzama());
        forgatasonVanCheckBox.selectedProperty().setValue(modositandoSzinesz.getForgatasonVan());
    }

    @FXML
    public void onModositasClick(ActionEvent actionEvent) {
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

        modositandoSzinesz.setNev(nev);
        modositandoSzinesz.setMagassag(magassag);
        modositandoSzinesz.setSzuletesiDatum(szuletesiDatum);
        modositandoSzinesz.setDijakSzama(dijakSzama);
        modositandoSzinesz.setForgatasonVan(forgatasonVan);

        try {
            SzineszDb db = new SzineszDb();
            if (db.szineszModositasa(modositandoSzinesz)) {
                this.stage.close();
            } else {
                alert("Sikertelen módosítás");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
