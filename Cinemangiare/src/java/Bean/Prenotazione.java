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

public class Prenotazione {

    public int id_prenotazione;
    public int id_film;
    public int id_spettacolo;
    public int id_prezzo;
    public int id_posto;
    public Timestamp data_ora_prenotazione;
    public String titolo;
    public int sala;
    public Timestamp data_ora_spettacolo;
    public String email;

    public int getId_film() {
        return id_film;
    }

    public void setId_film(int id_film) {
        this.id_film = id_film;
    }

    public int getId_spettacolo() {
        return id_spettacolo;
    }

    public void setId_spettacolo(int id_spettacolo) {
        this.id_spettacolo = id_spettacolo;
    }

    public int getId_prezzo() {
        return id_prezzo;
    }

    public void setId_prezzo(int id_prezzo) {
        this.id_prezzo = id_prezzo;
    }

    public int getId_posto() {
        return id_posto;
    }

    public void setId_posto(int id_posto) {
        this.id_posto = id_posto;
    }

    public Timestamp getData_ora_prenotazione() {
        return data_ora_prenotazione;
    }

    public void setData_ora_prenotazione(Timestamp data_ora_prenotazione) {
        this.data_ora_prenotazione = data_ora_prenotazione;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    /**
     * @return the id_prenotazione
     */
    public int getId_prenotazione() {
        return id_prenotazione;
    }

    /**
     * @param id_prenotazione the id_prenotazione to set
     */
    public void setId_prenotazione(int id_prenotazione) {
        this.id_prenotazione = id_prenotazione;
    }

    /**
     * @return the sala
     */
    public int getSala() {
        return sala;
    }

    /**
     * @param sala the sala to set
     */
    public void setSala(int sala) {
        this.sala = sala;
    }

    /**
     * @return the data_ora_spettacolo
     */
    public Timestamp getData_ora_spettacolo() {
        return data_ora_spettacolo;
    }

    /**
     * @param data_ora_spettacolo the data_ora_spettacolo to set
     */
    public void setData_ora_spettacolo(Timestamp data_ora_spettacolo) {
        this.data_ora_spettacolo = data_ora_spettacolo;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
}
