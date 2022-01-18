package hu.csepel.gyakorlasdb;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class SzineszDb {
    Connection connection;

    public SzineszDb() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/szinesz", "root", "");
    }

    public List<Szinesz> getSzineszek() throws SQLException {
        List<Szinesz> szineszek = new ArrayList<>();
        Statement stmt = connection.createStatement();
        String sql = "SELECT * FROM szinesz;";
        ResultSet result = stmt.executeQuery(sql);
        while (result.next()) {
            int id = result.getInt("id");
            String nev = result.getString("nev");
            int magassag = result.getInt("magassag");
            LocalDate szuletesiDatum = result.getDate("szul_datum").toLocalDate();
            int dijakSzama = result.getInt("dijak_szama");
            boolean forgatasonVan = result.getBoolean("forgatason_van");
            Szinesz szinesz = new Szinesz(id, nev, magassag, szuletesiDatum, dijakSzama, forgatasonVan);
            szineszek.add(szinesz);
        }
        return szineszek;
    }

    public int szineszHozzaadasa(String nev, int magassag, LocalDate szuletesiDatum, int dijakSzama, boolean forgatasonVan) throws SQLException {
        String sql = "INSERT INTO szinesz (nev, magassag, szul_datum, dijak_szama, forgatason_van) VALUES(?, ?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, nev);
        stmt.setInt(2, magassag);
        stmt.setDate(3, Date.valueOf(szuletesiDatum));
        stmt.setInt(4, dijakSzama);
        stmt.setBoolean(5, forgatasonVan);
        return stmt.executeUpdate();
    }

    public boolean szineszTorles(int id) throws SQLException {
        String sql = "DELETE FROM szinesz WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, id);
        int erintettSorok = stmt.executeUpdate();
        return erintettSorok == 1;
    }

    public boolean szineszModositasa(Szinesz modositando) throws SQLException {
        String sql = "UPDATE szinesz SET nev = ?, magassag = ?, szuletesi_datum = ?, dijak_szama = ?, forgatason_van = ? WHERE id = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, modositando.getNev());
        stmt.setInt(2, modositando.getMagassag());
        stmt.setDate(3, Date.valueOf(modositando.getSzuletesiDatum()));
        stmt.setInt(4, modositando.getDijakSzama());
        stmt.setBoolean(5, modositando.getForgatasonVan());
        stmt.setInt(6, modositando.getId());
        int erintettSorok = stmt.executeUpdate();
        return erintettSorok == 1;
    }
}
