package model;

// MODUL 5: Abstract Class
public abstract class ItemKoleksi {
    // MODUL 3: Encapsulation (Protected agar bisa diakses anak class)
    protected String id;
    protected String judul;

    // MODUL 2: Constructor
    public ItemKoleksi(String id, String judul) {
        this.id = id;
        this.judul = judul;
    }

    // MODUL 5: Abstract Method (Wajib di-Override oleh anak)
    public abstract String getTipe();

    public String getJudul() {
        return judul;
    }

    public String getId() {
        return id;
    }
}