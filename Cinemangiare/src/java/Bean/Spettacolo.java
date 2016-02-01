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
    
     public int get_prezzo(){
        return prezzo;
    }
     
     public void set_prezzo(int prezzo){
         this.prezzo=prezzo;
     }
     
     public int get_posti(){
        return posti;
    }
     
     public void set_posti(int posti){
         this.posti=posti;
     }
}
