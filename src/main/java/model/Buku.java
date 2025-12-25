package model;

// Inheritance (Extends)
public class Buku extends ItemKoleksi {
    private String penulis;
    private String penerbit;
    private int tahun;
    private String status;

    //  Class & Constructor
    public Buku(String id, String judul, String penulis, String penerbit, int tahun, String status) {
        super(id, judul); // Memanggil constructor parent
        this.penulis = penulis;
        this.penerbit = penerbit;
        this.tahun = tahun;
        this.status = status;
    }

    // Overriding Method dari Parent/Abstract
    @Override
    public String getTipe() {
        return "Buku Cetak";
    }

    //  Polymorphism (Custom toString)
    @Override
    public String toString() {
        return judul + " oleh " + penulis;
    }

    // Format untuk CSV (Pemisah titik koma)
    public String toCsv() {
        return id + ";" + judul + ";" + penulis + ";" + penerbit + ";" + tahun + ";" + status;
    }

    // Getter methods
    public String getPenulis() { return penulis; }
    public String getPenerbit() { return penerbit; }
    public int getTahun() { return tahun; }
    public String getStatus() { return status; }
}