/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bean;

/**
 *
 * @author jack
 */
public class Price {
    public int id_price;
    public String tipo;
    public double prezzo;
    
    public Price() {
    }

    public int getId() {
        return id_price;
    }

    public void setId(int id_price) {
        this.id_price = id_price;
    }
    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }
}
