package Service;

import Model.Kendaraan;
import Database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Parkir {
    private static final double TARIF_PER_JAM_MOBIL = 5000.0;
    private static final double TARIF_PER_JAM_MOTOR = 2000.0;

    // Create: Menambahkan kendaraan ke parkir
    public void parkirKendaraan(Kendaraan kendaraan) {
        String sql = "INSERT INTO parkir (platNomor, jenisKendaraan, waktuMasuk) VALUES (?, ?, ?)";
    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, kendaraan.getPlatNomor());
            pstmt.setString(2, kendaraan.getJenisKendaraan());
            pstmt.setTimestamp(3, new Timestamp(kendaraan.getWaktuMasuk().getTime()));
            pstmt.executeUpdate();
    
            // Ambil ID yang di-generate oleh database (jika menggunakan AUTO_INCREMENT)
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    kendaraan.setidParkir(rs.getInt(1));  // Set ID kendaraan setelah data dimasukkan
                }
            }
            System.out.println("Kendaraan berhasil diparkir: " + kendaraan.getPlatNomor());
        } catch (SQLException e) {
            System.out.println("Error saat memarkir kendaraan: " + e.getMessage());
        }
    }
    

    // Read: Menampilkan kendaraan yang diparkir
    public List<Kendaraan> tampilkanKendaraan() {
        List<Kendaraan> kendaraanList = new ArrayList<>();
        String sql = "SELECT idParkir, platNomor, jenisKendaraan, waktuMasuk, waktuKeluar, tarif FROM parkir";
    
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
    
            System.out.println("\n=== Daftar Kendaraan ===");
            System.out.printf("%-10s %-15s %-15s %-25s %-25s %-10s\n", 
                              "ID", "Plat Nomor", "Jenis", "Waktu Masuk", "Waktu Keluar", "Tarif");
    
            while (rs.next()) {
                int id = rs.getInt("idParkir");
                String platNomor = rs.getString("platNomor");
                String jenisKendaraan = rs.getString("jenisKendaraan");
                Timestamp waktuMasuk = rs.getTimestamp("waktuMasuk");
                Timestamp waktuKeluar = rs.getTimestamp("waktuKeluar");
                double tarif = rs.getDouble("tarif");
    
                System.out.printf("%-10d %-15s %-15s %-25s %-25s %-10.2f\n",
                                  id, platNomor, jenisKendaraan, 
                                  waktuMasuk != null ? waktuMasuk.toString() : "N/A",
                                  waktuKeluar != null ? waktuKeluar.toString() : "N/A", 
                                  tarif);
            }
            System.out.println("=========================");
        } catch (SQLException e) {
            System.out.println("Error saat menampilkan kendaraan: " + e.getMessage());
        }
        return kendaraanList;
    }
    
    

    // Method untuk mengeluarkan kendaraan dan menghitung tarif
  public void keluarkanKendaraan(int id) {
    // SQL untuk mengambil data kendaraan berdasarkan ID
    String sqlParkir = "SELECT platNomor, jenisKendaraan, waktuMasuk FROM parkir WHERE idParkir = ?";
    
    // SQL untuk memperbarui tabel parkir dengan waktu keluar dan tarif
    String sqlUpdateParkir = "UPDATE parkir SET waktuKeluar = ?, tarif = ? WHERE idParkir = ?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmtSelect = conn.prepareStatement(sqlParkir);
         PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdateParkir)) {

        // Ambil informasi kendaraan berdasarkan ID
        pstmtSelect.setInt(1, id);
        ResultSet rs = pstmtSelect.executeQuery();

        if (rs.next()) {
            String platNomor = rs.getString("platNomor");
            String jenisKendaraan = rs.getString("jeniskendaraan");
            Timestamp waktuMasuk = rs.getTimestamp("waktuMasuk");

            // Menghitung durasi parkir dalam jam
            long durasiMillis = System.currentTimeMillis() - waktuMasuk.getTime();
            long durasiJam = (durasiMillis / (1000 * 60 * 60)) + 1; // Membulatkan ke atas jika durasi kurang dari 1 jam

            // Menghitung tarif berdasarkan jenis kendaraan
            double tarif = 0.0;
            if ("Mobil".equalsIgnoreCase(jenisKendaraan)) {
                tarif = durasiJam * 5000.0; // Tarif per jam untuk mobil
            } else if ("Motor".equalsIgnoreCase(jenisKendaraan)) {
                tarif = durasiJam * 2000.0; // Tarif per jam untuk motor
            }

            // Set waktu keluar
            Timestamp waktuKeluar = new Timestamp(System.currentTimeMillis());

            // Update tabel parkir dengan waktu keluar dan tarif
            pstmtUpdate.setTimestamp(1, waktuKeluar);
            pstmtUpdate.setDouble(2, tarif);
            pstmtUpdate.setInt(3, id);
            pstmtUpdate.executeUpdate();

            // Menampilkan struk parkir
            System.out.println("\n=== Struk Parkir ===");
            System.out.println("ID Kendaraan    : " + id);
            System.out.println("Plat Nomor      : " + platNomor);
            System.out.println("Jenis Kendaraan : " + jenisKendaraan);
            System.out.println("Waktu Masuk     : " + waktuMasuk);
            System.out.println("Waktu Keluar    : " + waktuKeluar);
            System.out.println("Durasi Parkir   : " + durasiJam + " jam");
            System.out.println("Total Tarif     : Rp " + tarif);
            System.out.println("====================");
        } else {
            System.out.println("Kendaraan dengan ID " + id + " tidak ditemukan.");
        }
    } catch (SQLException e) {
        System.out.println("Error saat mengeluarkan kendaraan: " + e.getMessage());
    }
}



    // Hitung biaya parkir berdasarkan jenis kendaraan
    public double hitungBiayaParkir(Kendaraan kendaraan) {
        long durasiMillis = new java.util.Date().getTime() - kendaraan.getWaktuMasuk().getTime();
        long durasiJam = (durasiMillis / (1000 * 60 * 60)) + 1; // Dibulatkan ke atas jika kurang dari 1 jam

        if (kendaraan.getJenisKendaraan().equals("Mobil")) {
            return durasiJam * TARIF_PER_JAM_MOBIL;
        } else if (kendaraan.getJenisKendaraan().equals("Motor")) {
            return durasiJam * TARIF_PER_JAM_MOTOR;
        }
        return 0.0;
    }


    public void updateKendaraan(Kendaraan kendaraanUpdate) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateKendaraan'");
    }

}

