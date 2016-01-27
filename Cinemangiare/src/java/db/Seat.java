/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

/**
 *
 * Classe che definisce una Sedia
 * @author Mattia
 */
class Seat {
    
    private static final long serialVersionUID = 1L;
    private boolean exists = false;
    private boolean booked = false;
    private int row, column;

    /**
     * Inizializza la sedia con i suoi parametri
     * @param exists É una sedia esistente?
     * @param booked Se é gia prenotata
     */
    public Seat(boolean exists, boolean booked) {
        this.exists = exists;
        this.booked = booked;
    }

    /**
     * imposta le coordinate della sedia
     * @param row riga
     * @param column colonna
     */
    public void setCoordinates(int row, int column) {
        this.row = row;
        this.column = column;
    }
    /**
     * setta se la sedia esiste
     * @param exists valore booleano
     */
    public void setExistence(boolean exists) {
        this.exists = exists;
    }
    /**
     * setta se la sedia é prenotata
     * @param booked valore booleano
     */
    public void setBooked(boolean booked){
        this.booked = booked;
    }
    /**
     * @return riga della sedia
     */
    public int getRow() {
        return row;
    }
    /**
     * @return colonna della sedia
     */
    public int getCol() {
        return column;
    }
    /**
     * @return ritorna se la sedia esiste
     */
    public boolean isAvailable() {
        return exists;
    }
    /**
     * @return ritorna vero se la sedia puó essere prenotata
     */
    public boolean canBeBooked() {
        return (isAvailable() && !booked);
    }
}

