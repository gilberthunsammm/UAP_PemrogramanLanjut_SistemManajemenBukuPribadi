package util;

import model.Buku;
import java.util.List;

// Interface
public interface StorageInterface {
    void save(List<Buku> data);
    List<Buku> load();
}