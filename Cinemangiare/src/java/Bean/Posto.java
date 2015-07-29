/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

/**
 *
 * @author Mattia
 */
public class Posto {
    
    public int id;
    public int id_sala;
    public int riga;
    public int colonna;
    public boolean esiste;
    
    public Posto(){
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_sala() {
        return id_sala;
    }

    public void setId_sala(int id_sala) {
        this.id_sala = id_sala;
    }

    public int getRiga() {
        return riga;
    }

    public void setRiga(int riga) {
        this.riga = riga;
    }

    public int getColonna() {
        return colonna;
    }

    public void setColonna(int colonna) {
        this.colonna = colonna;
    }

    public boolean isEsiste() {
        return esiste;
    }

    public void setEsiste(boolean esiste) {
        this.esiste = esiste;
    }
    
}
