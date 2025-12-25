package model;

//  Abstract Class
public abstract class ItemKoleksi {
    // Encapsulation (Protected agar bisa diakses anak class)
    protected String id;
    protected String judul;

    //  Constructor
    public ItemKoleksi(String id, String judul) {
        this.id = id;
        this.judul = judul;
    }

    //  Abstract Method (Wajib di-Override oleh anak)
    public abstract String getTipe();

    public String getJudul() {
        return judul;
    }

    public String getId() {
        return id;
    }
}