package unikama.acer.e_rubic_admin.oop;

/**
 * Created by acer on 10/12/2017.
 */

public class Item {
    String id_kategori,nama_kategori;
    String id_penilaian, nama_penilaian;

    public String getId_kategori() {
        return id_kategori;
    }

    public void setId_kategori(String id_kategori) {
        this.id_kategori = id_kategori;
    }

    public String getNama_kategori() {
        return nama_kategori;
    }

    public void setNama_kategori(String nama_kategori) {
        this.nama_kategori = nama_kategori;
    }

    public String getId_penilaian() {
        return id_penilaian;
    }

    public void setId_penilaian(String id_penilaian) {
        this.id_penilaian = id_penilaian;
    }

    public String getNama_penilaian() {
        return nama_penilaian;
    }

    public void setNama_penilaian(String nama_penilaian) {
        this.nama_penilaian = nama_penilaian;
    }
}
