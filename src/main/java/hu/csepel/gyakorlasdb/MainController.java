package hu.csepel.gyakorlasdb;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainController extends Controller {

    @FXML
    private TableColumn<Szinesz, Integer> magassagCol;
    @FXML
    private TableColumn<Szinesz, Boolean> forgatasonVanCol;
    @FXML
    private TableColumn<Szinesz, Integer> dijakSzamaCol;
    @FXML
    private TableColumn<Szinesz, String> nevCol;
    @FXML
    private TableView<Szinesz> szineszekTableView;
    @FXML
    private TableColumn<Szinesz, LocalDate> szuletesiDatumCol;

    private SzineszDb db;

    public void initialize() {
        nevCol.setCellValueFactory(new PropertyValueFactory<>("nev"));
        magassagCol.setCellValueFactory(new PropertyValueFactory<>("magassag"));
        szuletesiDatumCol.setCellValueFactory(new PropertyValueFactory<>("szuletesiDatum"));
        dijakSzamaCol.setCellValueFactory(new PropertyValueFactory<>("dijakSzama"));
        forgatasonVanCol.setCellValueFactory(new PropertyValueFactory<>("forgatasonVan"));

        try {
            db = new SzineszDb();
            List<Szinesz> szineszLista = db.getSzineszek();
            for (Szinesz szinesz : szineszLista) {
                szineszekTableView.getItems().add(szinesz);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onTorlesClick(ActionEvent actionEvent) {
        int selectedIndex = szineszekTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            alert("A törléshez válaszzon ki egy elemet");
            return;
        }

        Szinesz torlendoSzinesz = szineszekTableView.getSelectionModel().getSelectedItem();
        if (!confirm("Biztos törölni szeretné az alábbi színészt: " + torlendoSzinesz.getNev())) {
            return;
        }
        try {
            db.szineszTorles(torlendoSzinesz.getId());
            alert("Sikeres törlés");
            szineszListaFeltoltes();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onHozzaadasClick(ActionEvent actionEvent) {
        try {
            Controller hozzaadas = ujAblak("hozzaad-view.fxml", "Színész hozzáadása", 323, 245);
            hozzaadas.getStage().setOnCloseRequest(event -> szineszListaFeltoltes());
            hozzaadas.getStage().show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onModositasClick(ActionEvent actionEvent) {
        int selectedIndex = szineszekTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            alert("A módosításhoz válaszzon ki egy elemet");
            return;
        }
        Szinesz modositando = szineszekTableView.getSelectionModel().getSelectedItem();
        try {
            ModositasController modositas = (ModositasController) ujAblak("modositas-view.fxml", "Színész módosítása", 323, 245);
            modositas.setModositandoSzinesz(modositando);
            modositas.getStage().setOnHiding(event -> szineszekTableView.refresh());
            modositas.getStage().show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void szineszListaFeltoltes() {
        try {
            List<Szinesz> szineszLista = db.getSzineszek();
            szineszekTableView.getItems().clear();
            for (Szinesz szinesz : szineszLista) {
                szineszekTableView.getItems().add(szinesz);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}