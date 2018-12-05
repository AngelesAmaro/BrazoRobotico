
package Beans;


public class DatosBrazo {
    //set y get
    private int base;  
    private int hombro;
    private int codo;
    private int muneca;
    private int pinza;
    private String descripcion;
    
    public DatosBrazo() {

    }
    public DatosBrazo(int base, int hombro, int codo, int muneca, int pinza, String descripcion) { 
        this.base = base;
        this.hombro = hombro;
        this.codo = codo;
        this.muneca = muneca;
        this.pinza = pinza;
        this.descripcion = descripcion;
    }

    public int getBase() { 
        return base;
    }

    public void setBase(int base) { 
        this.base = base;
    }

    public int getHombro() {
        return hombro;
    }

    public void setHombro(int hombro) { 
        this.hombro = hombro;
    }

    public int getCodo() { 
        return codo;
    }

    public void setCodo(int codo) { 
        this.codo = codo;
    }

    public int getMuneca() { 
        return muneca;
    }

    public void setMuneca(int muneca) {
        this.muneca = muneca;
    }

    public int getPinza() {
        return pinza;
    }

    public void setPinza(int pinza) { 
        this.pinza = pinza;
    }


    public void setDescripcion(String descripcion) { 
        this.descripcion = descripcion;
    }

    @Override
    public String toString() { // Método sobreescrito para que el JComboBox muestre la descripción
        return descripcion;
    }
}
