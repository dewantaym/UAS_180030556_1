package com.bh183.dewanta;



public class Film {
    private int idFilm;
    private String judul;
    private String rilis;
    private String cover;
    private String genre;
    private String durasi;
    private String rating;
    private String synopsis;


    public Film(int idFilm, String judul, String rilis, String cover, String genre, String durasi, String rating, String synopsis) {
        this.idFilm = idFilm;
        this.judul = judul;
        this.rilis = rilis;
        this.cover = cover;
        this.genre = genre;
        this.durasi = durasi;
        this.rating = rating;
        this.synopsis = synopsis;

    }

    public Film(int csrInt, String string, String tempDate, String string1, String string2, String string3, String string4, String string5, String string6) {
    }

    public int getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(int idFilm) {
        this.idFilm = idFilm;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getRilis() {
        return rilis;
    }

    public void setRilis(String rilis) {
        this.rilis = rilis;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDurasi() {
        return durasi;
    }

    public void setDurasi(String durasi) {
        this.durasi = durasi;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }


    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }
}
