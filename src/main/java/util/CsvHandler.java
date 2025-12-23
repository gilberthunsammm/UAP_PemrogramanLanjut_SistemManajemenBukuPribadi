package util;

import model.Buku;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvHandler implements StorageInterface {
    // Gunakan path absolut agar file tidak hilang saat rebuild
    // Atau gunakan nama file sederhana agar tersimpan di Root Project
    private static final String FILE_PATH = "data_buku.csv";

    @Override
    public void save(List<Buku> bukuList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Buku b : bukuList) {
                writer.write(b.toCsv());
                writer.newLine();
            }
            // DEBUG: Cek lokasi file saat menyimpan
            System.out.println("Data berhasil disimpan ke: " + new File(FILE_PATH).getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Gagal menyimpan: " + e.getMessage());
        }
    }

    @Override
    public List<Buku> load() {
        List<Buku> list = new ArrayList<>();
        File file = new File(FILE_PATH);

        // DEBUG: Cek apakah file ditemukan saat load
        System.out.println("Mencoba load dari: " + file.getAbsolutePath());

        if (!file.exists()) {
            System.out.println("File database belum ada, membuat baru...");
            return list;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length == 6) {
                    list.add(new Buku(data[0], data[1], data[2], data[3], Integer.parseInt(data[4]), data[5]));
                }
            }
            System.out.println("Berhasil memuat " + list.size() + " buku.");
        } catch (Exception e) {
            System.err.println("Gagal load data: " + e.getMessage());
        }
        return list;
    }
}