package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import Database.DatabaseConnection;

// Kelas induk Kendaraan
public class Kendaraan {
    protected static int idParkir;
    private String platNomor;
    private String jenisKendaraan;
    protected Date waktuMasuk;
   

    public Kendaraan(int i, String platNomor2, String string, Date date) {
        this.idParkir = idParkir;
        this.platNomor = platNomor;
        this.jenisKendaraan = jenisKendaraan;
        this.waktuMasuk = waktuMasuk;
    }

    // Getters and Setters
    public int getidParkir() {
        return idParkir;
    }

    public void setIdKendaraan(int idParkir) {
        this.idParkir = idParkir;
    }

    public String getPlatNomor() {
        return platNomor;
    }

    public void setPlatNomor(String platNomor) {
        this.platNomor = platNomor;
    }

    public String getJenisKendaraan() {
        return jenisKendaraan;
    }

    public void setJenisKendaraan(String jenisKendaraan) {
        this.jenisKendaraan = jenisKendaraan;
    }

    public Date getWaktuMasuk() {
        return waktuMasuk;
    }

    public void setWaktuMasuk(Date waktuMasuk) {
        this.waktuMasuk = waktuMasuk;
    }


    public void tambahKendaraan(Kendaraan kendaraanBaru) {
  
        throw new UnsupportedOperationException("Unimplemented method 'tambahKendaraan'");
    }

    public Object tampilkanKendaraan() {
        
        throw new UnsupportedOperationException("Unimplemented method 'tampilkanKendaraan'");
    }

    public void setidParkir(int int1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setidParkir'");
    }

    public void updateKendaraan() {
        String sql = "UPDATE parkir SET platNomor = ?, jenisKendaraan = ?, waktuMasuk = ? WHERE idParkir = ?";
    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Set parameter untuk query
            pstmt.setString(1, getPlatNomor());
            pstmt.setString(2, getJenisKendaraan());
            pstmt.setTimestamp(3, new Timestamp(getWaktuMasuk().getTime()));
            pstmt.setInt(4, getidParkir());
    
            // Eksekusi query
            int rowsUpdated = pstmt.executeUpdate();
    
            // Beri konfirmasi apakah data berhasil diupdate
            if (rowsUpdated > 0) {
                System.out.println("Kendaraan berhasil diperbarui: " + getPlatNomor());
            } else {
                System.out.println("Gagal memperbarui kendaraan. ID tidak ditemukan.");
            }
        } catch (SQLException e) {
            System.out.println("Error saat memperbarui kendaraan: " + e.getMessage());
        }
    }
}
