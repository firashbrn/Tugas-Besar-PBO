package Model;


import java.util.Date;
// Subkelas Motor
public class Motor extends Kendaraan {

    public Motor(String platNomor) {
        super(0, platNomor, "Motor", new Date());  // Set default waktuMasuk dengan waktu saat ini
    }

    
}