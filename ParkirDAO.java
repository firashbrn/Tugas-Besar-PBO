package Database;

import Model.Kendaraan;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParkirDAO {
    private Connection connection;

    public ParkirDAO() throws SQLException {
        connection = DatabaseConnection.getConnection();
    }

    // Create
    public void tambahKendaraan(Kendaraan kendaraan) throws SQLException {
        String sql = "INSERT INTO parkir (plat_nomor, jenis_kendaraan, waktu_masuk) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, kendaraan.getPlatNomor());
        statement.setString(2, kendaraan.getJenisKendaraan());
        statement.setTimestamp(3, new Timestamp(kendaraan.getWaktuMasuk().getTime()));
        statement.executeUpdate();
    }

    // Read
    public List<Kendaraan> ambilSemuaKendaraan() throws SQLException {
        String sql = "SELECT * FROM parkir";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        List<Kendaraan> kendaraanList = new ArrayList<>();
        while (resultSet.next()) {
            String platNomor = resultSet.getString("plat_nomor");
            String jenisKendaraan = resultSet.getString("jenis_kendaraan");
            resultSet.getTimestamp("waktu_masuk");
            Kendaraan kendaraan = jenisKendaraan.equals("Mobil") ? new Model.Mobil(platNomor) : new Model.Motor(platNomor);
            kendaraanList.add(kendaraan);
        }
        return kendaraanList;
    }

    // Update
    public void perbaruiKendaraan(String platNomorLama, Kendaraan kendaraanBaru) throws SQLException {
        String sql = "UPDATE parkir SET plat_nomor = ?, jenis_kendaraan = ? WHERE plat_nomor = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, kendaraanBaru.getPlatNomor());
        statement.setString(2, kendaraanBaru.getJenisKendaraan());
        statement.setString(3, platNomorLama);
        statement.executeUpdate();
    }

    // Delete
    public void hapusKendaraan(String platNomor) throws SQLException {
        String sql = "DELETE FROM parkir WHERE plat_nomor = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, platNomor);
        statement.executeUpdate();
    }
}