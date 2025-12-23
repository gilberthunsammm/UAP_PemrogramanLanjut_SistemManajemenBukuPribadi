# 📚 Sistem Manajemen Buku Pribadi

**Sistem Manajemen Buku Pribadi** adalah aplikasi desktop berbasis **Java Swing** untuk mengelola koleksi buku. Aplikasi ini menerapkan konsep **CRUD** (Create, Read, Update, Delete) dengan penyimpanan file **CSV** dan dilengkapi fitur keamanan Login/Register serta pencatatan Log Aktivitas.

Dibuat untuk tugas **Ujian Akhir Praktikum (UAP)** Pemrograman Lanjut.

## ✨ Fitur Unggulan

### 🔐 1. Keamanan & User
* **Login & Register:** Pengguna dapat mendaftar akun baru dan login. Data user disimpan aman di `data_users.csv`.
* **Logout:** Fitur keluar aplikasi dengan aman.

### 📖 2. Manajemen Buku (CRUD)
* **Tambah Buku:** Input Judul, Penulis, Penerbit, Tahun, dan Status Baca.
* **Lihat Koleksi:** Tampilan tabel interaktif yang memuat semua data buku.
* **Pencarian:** Cari buku berdasarkan Judul/Penulis secara *real-time*.
* **Hapus Buku:** Menghapus buku yang dipilih dari database.
* **Sorting:** Mengurutkan data di tabel (klik header kolom).

### 📊 3. Laporan & Log (History)
* **Ringkasan Statistik:** Melihat total buku, jumlah buku selesai dibaca, dan yang belum dibaca.
* **Activity Log:** Mencatat otomatis setiap aktivitas (contoh: *"Menghapus buku X pada jam 12.00"*) ke dalam file `activity_log.csv`.

### 🎨 4. Desain Antarmuka (UI)
* **Glassmorphism:** Panel transparan dengan efek kaca yang modern.
* **Custom Background:** Menggunakan gambar latar belakang (`bacgggground.jpg`).

---

## 🛠️ Teknologi

* **Bahasa:** Java (JDK 21/23)
* **Build Tool:** Maven
* **GUI Library:** Java Swing (`javax.swing`)
* **Penyimpanan:** CSV (Comma Separated Values) - Tanpa Database SQL.

---

## 📂 Struktur Folder & File

```text
uap/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── main/           # Main.java (Jalankan ini)
│   │   │   ├── model/          # Class Buku, User, ItemKoleksi
│   │   │   ├── view/           # Tampilan (Login, Dashboard, List, History)
│   │   │   └── util/           # Handler (CsvHandler, UserHandler, LogHandler)
│   │   └── resources/          # Aset Gambar (bacgggground.jpg)
├── data_buku.csv               # Database Buku (Otomatis dibuat)
├── data_users.csv              # Database User (Otomatis dibuat)
├── activity_log.csv            # Log Aktivitas (Otomatis dibuat)
└── pom.xml                     # Setting Maven