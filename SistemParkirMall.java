import Model.*;
import Service.*;

import java.util.Date;
import java.util.Scanner;

public class SistemParkirMall {
        public static void main(String[] args) {
        Parkir parkirService = new Parkir();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nSistem Parkir Mall");
            System.out.println("1. Parkir Kendaraan");
            System.out.println("2. Keluarkan Kendaraan");
            System.out.println("3. Tampilkan Kendaraan");
            System.out.println("4. Update Data Parkir"); // Tambahan menu
            System.out.println("5. Keluar");
            System.out.print("Pilih menu: ");

            int pilihan = scanner.nextInt();
            scanner.nextLine(); // Membaca newline setelah input angka

            switch (pilihan) {
                case 1:
                    System.out.print("Masukkan plat nomor: ");
                    String platNomor = scanner.nextLine();
                    System.out.print("Masukkan jenis kendaraan (Mobil/Motor): ");
                    String jenisKendaraan = scanner.nextLine();
                    Kendaraan kendaraanBaru = new Kendaraan(pilihan, platNomor, jenisKendaraan, new Date());
                    parkirService.parkirKendaraan(kendaraanBaru);
                    break;
                case 2:
                    System.out.print("Masukkan ID kendaraan yang keluar: ");
                    int idParkir = scanner.nextInt();
                    scanner.nextLine();
                    parkirService.keluarkanKendaraan(idParkir);
                    break;
                case 3:
                    System.out.println("\nDaftar Kendaraan yang Diparkir:");
                    parkirService.tampilkanKendaraan().forEach(k -> {
                        System.out.println("ID: " + k.getidParkir() + ", Plat: " + k.getPlatNomor() + ", Jenis: " + k.getJenisKendaraan() + ", Waktu Masuk: " + k.getWaktuMasuk());
                    });
                    break;
                case 4:
                    System.out.print("Masukkan ID kendaraan yang ingin diperbarui: ");
                    int idUpdate = scanner.nextInt();
                    scanner.nextLine(); // Membersihkan buffer
                
                    System.out.print("Masukkan plat nomor baru: ");
                    String platBaru = scanner.nextLine();
                    System.out.print("Masukkan jenis kendaraan baru (Mobil/Motor): ");
                    String jenisBaru = scanner.nextLine();
                
                    // Siapkan objek kendaraan dengan data baru
                    final Kendaraan kendaraanUpdate = new Kendaraan(idUpdate, platBaru, platBaru, null);
                    kendaraanUpdate.setIdKendaraan(idUpdate);
                    kendaraanUpdate.setPlatNomor(platBaru);
                    kendaraanUpdate.setJenisKendaraan(jenisBaru);
                    kendaraanUpdate.setWaktuMasuk(new Date()); // Jika waktu masuk diperbarui
                
                    // Panggil service untuk update data
                    kendaraanUpdate.updateKendaraan();
                    break;
                case 5:
                    System.out.println("Keluar dari sistem. Terima kasih!");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        }
    }
}


