package util;

import model.User;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserHandler {
    private static final String FILE_PATH = "data_users.csv";

    // 1. LOAD DATA USER
    public static List<User> loadUsers() {
        List<User> list = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) return list;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length == 2) {
                    list.add(new User(data[0], data[1]));
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
        return list;
    }

    // 2. CEK LOGIN (AUTHENTICATION)
    public static boolean login(String username, String password) {
        List<User> users = loadUsers();
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return true; // Cocok
            }
        }
        return false; // Salah
    }

    // 3. DAFTAR USER BARU (REGISTER)
    public static boolean register(String username, String password) {
        List<User> users = loadUsers();
        // Cek apakah username sudah ada
        for (User u : users) {
            if (u.getUsername().equalsIgnoreCase(username)) return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(username + ";" + password);
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}