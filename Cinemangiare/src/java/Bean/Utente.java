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
public class Utente {
    
    public int id;
    public String email;
    public String password;
    public int credito;
    public int id_ruolo;
    
    public Utente(){
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCredito() {
        return credito;
    }

    public void setCredito(int credito) {
        this.credito = credito;
    }

    public int getId_ruolo() {
        return id_ruolo;
    }

    public void setId_ruolo(int id_ruolo) {
        this.id_ruolo = id_ruolo;
    }
    
}
