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
    private int id;
    private String name;
    private int length;
    
    public Film() {
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public int getLength() {
        return length;
    }
    public void setLength(int length) {
        this.length = length;
    }
    
 
}
