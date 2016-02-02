
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.util.ArrayList;
import java.util.Iterator;

/*
 * @author Mattia
 * Classe che definisce e gestisce tutte le funzioni relative alla sala cinematografica
 */
public class Hall {
    
    private ArrayList<ArrayList<Seat>> seatsArray;
    private Iterator<ArrayList<Seat>> iteratorRow;
    private Iterator<Seat> iteratorCol;

    /**
     * Costruttore, inizializza il primo array
     */
    public Hall() {
        seatsArray = new ArrayList<ArrayList<Seat>>();
    }
    /**
     * Aggiunge una sedia alla sala.
     * Attenzione, le righe devono essere aggiunte sequenzialmente e buchi nella matrice bloccheranno l'iteratore.
     * @param row
     * @param column
     * @param exists
     */
    public void addSeat(int row, int column, boolean exists) {
        Seat seat = new Seat(exists, false);        
        addSeat(row, column, seat);
    }
    /**
     * Aggiunge una sedia alla sala.
     * Attenzione, le righe devono essere aggiunte sequenzialmente e buchi nella matrice bloccheranno l'iteratore.
     * @param row
     * @param column
     * @param exists
     * @param booked
     */
    public void addSeat(int row, int column, boolean exists, boolean booked) {
        Seat seat = new Seat(exists, booked);
        addSeat(row, column, seat);
    }
    /**
     * Aggiunge una sedia alla sala.
     * Attenzione, le righe devono essere aggiunte sequenzialmente e buchi nella matrice bloccheranno l'iteratore.
     * @param row
     * @param column
     * @param seat Oggetto sedia da inserire
     */
    private void addSeat(int row, int column, Seat seat) {
        if (seatsArray.size() <= row) {
            seatsArray.add(new ArrayList<Seat>());
        }
        seatsArray.get(row).add(column, seat);
    }
    /**
     * Ritorna la stringa necessaria per il frontend jquery
     * @return Stringa formattata
     */
    public String getMapString() {
        String map = "[";
        initializeIterator();

        while (hasMoreRows()) {
            goToNextRow();
            map += "'";
            while (hasMoreSeatsInRow()) {
                Seat read = getNextSeat();
                if (read.isAvailable()) {
                    if (read.canBeBooked()) {
                        map += "s";
                    } else {
                        map += "u";
                    }
                } else {
                    map += "_";
                }
            }
            map += "'";
            if (hasMoreRows()) {
                map += ",";
            }
        }
        return map + "]";
    }
    /**
     * @return ci sono altre sedie nella riga?
     */
    public boolean hasMoreSeatsInRow() {
        return iteratorCol.hasNext();
    }
    /**
     * @return la sedia definita dall'iteratore
     */
    public Seat getNextSeat() {
        return iteratorCol.next();
    }
    /**
     * @return ci sono altre ile di sedie nella sala?
     */
    public boolean hasMoreRows() {
        return iteratorRow.hasNext();
    }
    /**
     * passaggio dell'iteratore nella riga successiva
     */
    public void goToNextRow() {
        ArrayList<Seat> row = iteratorRow.next();
        iteratorCol = row.iterator();
    }
    /**
     * @return ritorna la matrice di sedie as-is
     */
    public ArrayList<ArrayList<Seat>> getSeatsArray() {
        return seatsArray;
    }
    /**
     * Inizializza l'iteratore della matrice
     */
    public void initializeIterator() {
        iteratorRow = seatsArray.iterator();
        iteratorCol = seatsArray.get(0).iterator();
    }
    /**
     * Modifica lo stato di prenotazione di una sedia giá nella sala
     * @param row
     * @param col
     * @param booked
     */
    public void modifySeatBookedStatus(int row, int col,boolean booked){
        Seat seat = getSeat(row, col);
        seat.setBooked(booked);
    }
    /**
     * @param coord coordinate formattate in stringa
     * @return la sedia alle coordinate specificate
     */
    public Seat getSeat(String coord) {
        return getSeat(getRowCoordinate(coord), getColumnCoordinate(coord));
    }
    /**
     * @param row riga
     * @param col colonna
     * @return la sedia presente nella riga e colonna definita
     */
    public Seat getSeat(int row, int col) {
        return seatsArray.get(row).get(col);
    }
    /**
     * @param string stringa di coordinate formattata
     * @return indice della riga per la stringa "coodinate" che é stata fornita
     */
    static public int getRowCoordinate(String string) {
        String[] split = string.split("_");
        return Integer.parseInt(split[0])-1;
    }
    /**
     * @param string Stringa di coordinate frmattata
     * @return indice della colonna per la stringa "coordinate" che é stata fornita
     */
    static public int getColumnCoordinate(String string) {
        String[] split = string.split("_");
        return Integer.parseInt(split[1])-1;
    }
    
}

