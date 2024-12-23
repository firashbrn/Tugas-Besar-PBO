package Model;

import java.util.Date;

// Subkelas Mobil
public class Mobil extends Kendaraan {
    public Mobil(String platNomor) {
        super(0, platNomor, "Mobil", new Date());  // Set default waktuMasuk dengan waktu saat ini
}
}
