package util;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LogHandler {
    // Nama file log
    private static final String LOG_FILE = "activity_log.csv";
    // Format Waktu: Tahun-Bulan-Tanggal Jam:Menit:Detik
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // METHOD 1: MENULIS LOG (Dipanggil saat Tambah/Hapus)
    public static void addLog(String aktivitas, String detail) {
        String waktu = LocalDateTime.now().format(formatter);
        // Format simpan: Waktu;Aktivitas;Detail
        String logEntry = waktu + ";" + aktivitas + ";" + detail;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            // 'true' artinya append (menambahkan di baris baru, tidak menimpa)
            writer.write(logEntry);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // METHOD 2: MEMBACA LOG (Untuk ditampilkan di Tabel History)
    public static List<String[]> readLogs() {
        List<String[]> logs = new ArrayList<>();
        File file = new File(LOG_FILE);

        // Jika file belum ada, kembalikan list kosong
        if (!file.exists()) return logs;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                // Pastikan data utuh (ada 3 bagian)
                if (parts.length >= 3) {
                    logs.add(parts);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return logs;
    }
}