package unikama.acer.e_rubic_admin.oop;

/**
 * Created by acer on 10/15/2017.
 */

public class Item_history {
    String mapel;
    String sekolah;
    String kelas;
    String nid,id_nilai_histori;

    public String getId_nilai_histori() {
        return id_nilai_histori;
    }

    public void setId_nilai_histori(String id_nilai_histori) {
        this.id_nilai_histori = id_nilai_histori;
    }

    public Item_history(String nid, String mapel, String sekolah, String kelas, String id_nilai_histori) {
        this.nid = nid;
        this.sekolah = sekolah;
        this.kelas = kelas;
        this.mapel = mapel;
        this.id_nilai_histori = id_nilai_histori;

    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }


    public String getMapel() {
        return mapel;
    }

    public void setMapel(String mapel) {
        this.mapel = mapel;
    }

    public String getSekolah() {
        return sekolah;
    }

    public void setSekolah(String sekolah) {
        this.sekolah = sekolah;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }
}
