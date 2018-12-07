package Main;

import Brazo.Brazo;
import javax.swing.JFrame;

/*
 * Main
 */

/**
 *
 * @author Sandra Luz Godinez
 * @author Angeles Amaro
 * @author Abril Alejandra
 */
public class Main {

    /**
     * @param args the command line arguments
     */
       
    public static void main(String[] args) {
        Brazo obj = new Brazo();
        obj.setVisible(true);
        obj.setSize(1500,1000);
        obj.setLocation(0,0);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    
}
