package unikama.acer.e_rubic_admin.oop;

/**
 * Created by acer on 10/12/2017.
 */

public class Item_penilaian {
    String id_penilaian;
    String nama_penilaian;
    String score;
    String ulasan;

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getUlasan() {
        return ulasan;
    }

    public void setUlasan(String ulasan) {
        this.ulasan = ulasan;
    }



    public Item_penilaian(String id_penilaian, String nama_penilaian/*, String score, String ulasan*/){
        this.id_penilaian = id_penilaian;
        this.nama_penilaian = nama_penilaian;
        this.score = score;
        this.ulasan = ulasan;
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
