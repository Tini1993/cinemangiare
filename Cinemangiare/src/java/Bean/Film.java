package Bean;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mattia
 */
public class Film {
    private int id_film;
    private String titolo;
    private int durata;
    private int id_genere;
    private String url_trailer;
    private String trama;
    private String uri_locandina;
    private double incassi;
    
    public Film() {
    }

    public int getId() {
        return id_film;
    }

    public void setId(int id_film) {
        this.id_film = id_film;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public int getDurata() {
        return durata;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    public int getId_genere() {
        return id_genere;
    }

    public void setId_genere(int id_genere) {
        this.id_genere = id_genere;
    }

    public String getUrl_trailer() {
        return url_trailer;
    }

    public void setUrl_trailer(String url_trailer) {
        this.url_trailer = url_trailer;
    }

    public String getTrama() {
        return trama;
    }

    public void setTrama(String trama) {
        this.trama = trama;
    }

    public String getUrl_locandina() {
        return uri_locandina;
    }

    public void setUrl_locandina(String uri_locandina) {
        this.uri_locandina = uri_locandina;
    }
    
    public double getIncassi(){
        return incassi;
    }
    
    public void setIncassi(double incassi){
        this.incassi=incassi;
    }
            
    
    
 
}
