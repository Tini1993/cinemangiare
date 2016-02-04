/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

import java.sql.Timestamp;



/**
 *
 * @author Mattia
 */

public class Spettacolo {

    public int id_spettacolo;
    public int id_film;
    public Timestamp data_ora;
    public int id_sala;
    public int prezzo;
    public int posti;

    public int getId_spettacolo() {
        return id_spettacolo;
    }

    public void setId_spettacolo(int id_spettacolo) {
        this.id_spettacolo = id_spettacolo;
    }

    public int getId_film() {
        return id_film;
    }

   
    public void setId_film(int id_film) {
        this.id_film = id_film;
    }

    public Timestamp getData_ora() {
        return data_ora;
    }

    public void setData_ora(Timestamp data_ora) {
        this.data_ora = data_ora;
    }

    public int getId_sala() {
        return id_sala;
    }

    public void setId_sala(int id_sala) {
        this.id_sala = id_sala;
    }

    /**
     * @return the prezzo
     */
    public int getPrezzo() {
        return prezzo;
    }

    /**
     * @param prezzo the prezzo to set
     */
    public void setPrezzo(int prezzo) {
        this.prezzo = prezzo;
    }

    /**
     * @return the posti
     */
    public int getPosti() {
        return posti;
    }

    /**
     * @param posti the posti to set
     */
    public void setPosti(int posti) {
        this.posti = posti;
    }
    
     
}
