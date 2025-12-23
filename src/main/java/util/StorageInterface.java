package util;

import model.Buku;
import java.util.List;

// MODUL 5: Interface
public interface StorageInterface {
    void save(List<Buku> data);
    List<Buku> load();
}