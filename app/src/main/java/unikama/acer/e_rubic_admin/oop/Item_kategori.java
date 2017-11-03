package unikama.acer.e_rubic_admin.oop;

/**
 * Created by acer on 10/12/2017.
 */

public class Item_kategori {
    String id_kategori,nama_kategori;

    public Item_kategori(String id_kategori, String nama_kategori){
        this.id_kategori = id_kategori;
        this.nama_kategori = nama_kategori;
    }


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
}
