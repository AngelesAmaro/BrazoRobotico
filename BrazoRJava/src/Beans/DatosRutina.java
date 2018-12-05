
package Beans;

public class DatosRutina {
   
    private String rutina ="800,";
    private String descripcion;
    
    public DatosRutina(){      
        
    }

    public String getRutina() { // Método para obtener datos de la rutina
        return rutina;
    }

    public void setRutina(String rutina) { // Método para definir datos en la rutina
        this.rutina += rutina;
    }


    public void setDescripcion(String descripcion) {  // Método para definir la descripción y será usada en el método toString
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {  // Método sobreescrito para que el JComboBox muestre la descripción
        return descripcion;
    }
    
    
}
