package unikama.acer.e_rubic_admin.oop;

/**
 * Created by acer on 10/15/2017.
 */

public class Item_detail_histori {
    String mapel;
    String sekolah;
    String kelas;
    String nid,id_nilai_histori,nama_kategori,nama_penilaian,hari_tanggal,  waktu,score,ulasan,komen,solusi;

    public Item_detail_histori(String mapel, String sekolah, String kelas, String nama_kategori, String nama_penilaian, String hari_tanggal, String waktu, String score, String ulasan/*, String komen, String solusi*/) {
        this.mapel = mapel;
        this.sekolah = sekolah;
        this.kelas = kelas;
        this.nama_kategori = nama_kategori;
        this.nama_penilaian = nama_penilaian;
        this.hari_tanggal = hari_tanggal;
        this.waktu = waktu;
        this.score = score;
        this.ulasan = ulasan;
//        this.komen = komen;
//        this.solusi = solusi;
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

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getId_nilai_histori() {
        return id_nilai_histori;
    }

    public void setId_nilai_histori(String id_nilai_histori) {
        this.id_nilai_histori = id_nilai_histori;
    }

    public String getNama_kategori() {
        return nama_kategori;
    }

    public void setNama_kategori(String nama_kategori) {
        this.nama_kategori = nama_kategori;
    }

    public String getNama_penilaian() {
        return nama_penilaian;
    }

    public void setNama_penilaian(String nama_penilaian) {
        this.nama_penilaian = nama_penilaian;
    }

    public String getHari_tanggal() {
        return hari_tanggal;
    }

    public void setHari_tanggal(String hari_tanggal) {
        this.hari_tanggal = hari_tanggal;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

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

    public String getKomen() {
        return komen;
    }

    public void setKomen(String komen) {
        this.komen = komen;
    }

    public String getSolusi() {
        return solusi;
    }

    public void setSolusi(String solusi) {
        this.solusi = solusi;
    }
}
