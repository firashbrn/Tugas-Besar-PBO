package Service;

import Model.Kendaraan;

// Interface untuk sistem parkir
public interface ParkirKendaraan {
    void parkirKendaraan(Kendaraan kendaraan) throws Exception;
    void keluarkanKendaraan(String platNomor);
    void tampilkanKendaraan();
    void updateKendaraan(Kendaraan kendaraan);
}
