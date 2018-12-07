package Brazo;

//Librerias
import Beans.DatosBrazo;
import Beans.DatosRutina;
import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import jssc.SerialPortException;

/*
 * Interfaz de control de Brazo Robotico
 */

/**
 *
 * @author Sandra Luz Godinez
 * @author Angeles Amaro
 * @author Abril Alejandra
 */
public class Brazo extends JFrame{
     PanamaHitek_Arduino ino;
     
    //Componentes de la interfaz
    private JButton btnGuardar,btnRutinaDefault,btnAgregar;
    private JButton btnRutina,btnCrearRutina,btnParo;
    private JLabel lblTitulo,lblPinza,lblMuneca,lblCodo,lblHombro,lblBase,lblImagen,lblGrados;
    private JSpinner spPinza,spMuneca,spCodo,spHombro,spBase; 
    private JComboBox cbRutina,cbPosicion;
    
    int posicion = 0; //contador de posiciones
    int rutina = 0; // contador de rutinas
    
    public Brazo(){
        super("BRAZO ROBOTICO");
        setLayout(null);
        getContentPane().setBackground(new Color(164,211,238));
        initComponents();
        
        //Clase anonima para las acciones de btnGuardar en el que se guardan las posiciones de los servos
        btnGuardar.addActionListener(new ActionListener(){
          
            public void actionPerformed(ActionEvent e) {
               DatosBrazo b = new DatosBrazo();
                posicion++;
                b.setBase((int) spBase.getValue());
                b.setHombro((int) spHombro.getValue());
                b.setCodo((int) spCodo.getValue());
                b.setMuneca((int) spMuneca.getValue());
                b.setPinza((int) spPinza.getValue());
                b.setDescripcion("Movimiento " + posicion);
                cbPosicion.addItem(b);
                
              String data =spPinza.getValue().toString()+","+spMuneca.getValue().toString()+","+spCodo.getValue().toString()+","+spHombro.getValue().toString()+","+spBase.getValue().toString();
              System.out.println(data);
              //Mandamos los numeros de cada JSpinner concatenados
               try {
                    ino.sendData(data);
                } catch (ArduinoException | SerialPortException ex) {
                    Logger.getLogger(Brazo.class.getName()).log(Level.SEVERE, null, ex);
                }
                 
            }            
        });
        
        //Clase anonima para las acciones de btnCrearRutina
       btnCrearRutina.addActionListener(new ActionListener(){
          
            public void actionPerformed(ActionEvent e) {
                DatosBrazo b = (DatosBrazo) cbPosicion.getSelectedItem();
                DatosRutina r = new DatosRutina(); 
                rutina++;
                r.setRutina(String.valueOf(b.getHombro()) + ",");
                r.setRutina(String.valueOf(b.getBase()) + ",");
                r.setRutina(String.valueOf(b.getCodo()) + ",");
                r.setRutina(String.valueOf(b.getMuneca()) + ",");
                r.setRutina(String.valueOf(b.getPinza()));
                r.setDescripcion("Rutina" + rutina);
                cbRutina.addItem(r);
            }            
        });
        
        //Clase anonima para las acciones de btnAgregar
        btnAgregar.addActionListener(new ActionListener(){
          
            public void actionPerformed(ActionEvent e) {
                DatosBrazo b = (DatosBrazo) cbPosicion.getSelectedItem();
                DatosRutina r = (DatosRutina) cbRutina.getSelectedItem();
                r.setRutina(",");//Se le agrega una  ,
                
                r.setRutina(String.valueOf(b.getHombro()) + ",");
                r.setRutina(String.valueOf(b.getBase()) + ",");
                r.setRutina(String.valueOf(b.getCodo()) + ",");
                r.setRutina(String.valueOf(b.getMuneca()) + ",");
                r.setRutina(String.valueOf(b.getPinza()));
                
                cbRutina.removeItemAt(cbRutina.getSelectedIndex());
                cbRutina.addItem(r);
            }      
                     
        });
        
         //Clase anonima para las acciones de btnParo
        btnParo.addActionListener(new ActionListener(){
          
            public void actionPerformed(ActionEvent e) {
               
               try {
                   //Enviamos el numero 400 ya que no es posible que se mande por medio de los spinners puesto que el # mayor es 360
                    ino.sendData("400");
                } catch (ArduinoException | SerialPortException ex) {
                    Logger.getLogger(Brazo.class.getName()).log(Level.SEVERE, null, ex);
                }
             
            }            
        });
        
       //Clase anonima para las acciones de btnRutina
       btnRutina.addActionListener(new ActionListener(){
          
            public void actionPerformed(ActionEvent e) {
               
             DatosRutina datosR =  (DatosRutina) cbRutina.getItemAt(cbRutina.getSelectedIndex()); 
                String datosP = datosR.getRutina();
                try {
                    ino.sendData(datosP);
                    System.out.println(datosP);
                } catch (ArduinoException | SerialPortException ex) {
                    Logger.getLogger(Brazo.class.getName()).log(Level.SEVERE, null, ex);
                }
             
            }            
        });
       
       
       //Clase anonima para las acciones de btnRutinaDefault para ejecutar la rutina guardada predeterminada
       btnRutinaDefault.addActionListener(new ActionListener(){
          
            public void actionPerformed(ActionEvent e) {
               
               try {
                   //Enviamos el numero 700 ya que no es posible que se mande por medio de los spinners puesto que el # mayor es 360
                    ino.sendData("700");
                } catch (ArduinoException | SerialPortException ex) {
                    Logger.getLogger(Brazo.class.getName()).log(Level.SEVERE, null, ex);
                }
             
            }            
        });

    }
    

    public void initComponents(){
        
        ino = new PanamaHitek_Arduino();
        try {
            ino.arduinoTX("COM3", 9600);
        } catch (ArduinoException ex) {
            Logger.getLogger(Brazo.class.getName()).log(Level.SEVERE, null, ex);
        }
    //Imagen que sirve de referecia del brazo
        ImageIcon Ibrazo = new ImageIcon("src/Imagenes/brazoo.jpg");
    
    //Agregar la imagen en un JLabel 
        lblImagen = new JLabel(Ibrazo);
        lblImagen.setBounds(700,100,700,700);
        add(lblImagen);
       
        
    //Agregar un JLabel como titulo 
        lblTitulo = new JLabel("Controlar Brazo Robotico");
        lblTitulo.setFont(Font.decode("Bodoni MT Black-BOLD-28"));
        lblTitulo.setBounds(400,50,600,40);
        add(lblTitulo);
        
        lblGrados = new JLabel("Grados");
        lblGrados.setFont(Font.decode("Bodoni MT Black-BOLD-20"));
        lblGrados.setBounds(300,200,100,40);
        add(lblGrados);
        
    //Boton para reproducir rutina por default
        btnRutinaDefault = new JButton("Rutina Default");
        btnRutinaDefault.setBounds(200,800,150,40);
        add(btnRutinaDefault);
    
    //Boton para guardar los movimientos
        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(450,650,100,40);
        add(btnGuardar);
        
    //JComboBox para guardar las posiciones
        cbPosicion = new JComboBox();
        cbPosicion.setBounds(350,800,150,40);
        add(cbPosicion);
        
     //Boton para crear rutina    
        btnCrearRutina = new JButton("Crear Rutina");
        btnCrearRutina.setBounds(500,800,150,40);
        add(btnCrearRutina);  
        
   //Boton para agregar los movimientos
        btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(750,800,100,40);
        add(btnAgregar);
        
    //JComboBox para guardar las posiciones
        cbRutina = new JComboBox();
        cbRutina.setBounds(900,800,100,40);
        //add(cbRutina);
    
    //Boton para reproducir la rutina almacenada
        btnRutina = new JButton("Ejecutar Rutina");
        btnRutina.setBounds(1050,800,150,40);
        add(btnRutina);
    
    //Boton para detener el brazo    
        btnParo = new JButton("Abortar");
        btnParo.setBounds(1300,800,100,40);
        add(btnParo);
        
    //Acciones a la base del brazo
        lblBase = new JLabel("Base");
        lblBase.setFont(Font.decode("Bodoni MT 24"));
        lblBase.setBounds(200,250,100,40);
        add(lblBase);
        
        spBase = new JSpinner();
        spBase.setBounds(300,250,100,40);
        add(spBase);
        
    //Acciones  del hombro del brazo
        lblHombro = new JLabel("Hombro");
        lblHombro.setFont(Font.decode("Bodoni MT 24"));
        lblHombro.setBounds(200,350,100,40);
        add(lblHombro);
        
        spHombro = new JSpinner();
        spHombro.setBounds(300,350,100,40);
        add(spHombro);

        
    //Acciones  del hombro del brazo
        lblCodo = new JLabel("Codo");
        lblCodo.setFont(Font.decode("Bodoni MT 24"));
        lblCodo.setBounds(200,450,100,40);
        add(lblCodo);
        
        spCodo = new JSpinner();
        spCodo.setBounds(300,450,100,40);
        add(spCodo);
        
    
    //Acciones  de la muñeca del brazo
        lblMuneca = new JLabel("Muñeca");
        lblMuneca.setFont(Font.decode("Bodoni MT 24"));
        lblMuneca.setBounds(200,550,100,40);
        add(lblMuneca);
        
        spMuneca = new JSpinner();
        spMuneca.setBounds(300,550,100,40);
        add(spMuneca);

        
    //Acciones  de las Pinzas del brazo
        lblPinza = new JLabel("Pinza");
        lblPinza.setFont(Font.decode("Bodoni MT 24"));
        lblPinza.setBounds(200,650,100,40);
        add(lblPinza);
        
        spPinza = new JSpinner();
        spPinza.setBounds(300,650,100,40);
        add(spPinza);
        
        
    //Modelo para los spinners del motor a pasos
    //El JSpinner solo podra tomar valores de 0 a 360
        SpinnerNumberModel nm = new SpinnerNumberModel();
        nm.setMaximum(360);
        nm.setMinimum(0);
        spBase.setModel(nm);
        
    //Modelo para los spinners de los micro servos
    //El JSpinner solo podra tomar valores de 0 a 180
        SpinnerNumberModel nmHombro = new SpinnerNumberModel();
        nmHombro.setMaximum(180);
        nmHombro.setMinimum(0);
        spHombro.setModel(nmHombro);
        
        SpinnerNumberModel nmCodo = new SpinnerNumberModel();
        nmCodo.setMaximum(180);
        nmCodo.setMinimum(0);
        spCodo.setModel(nmCodo);
        
        SpinnerNumberModel nmMuneca = new SpinnerNumberModel();
        nmMuneca.setMaximum(180);
        nmMuneca.setMinimum(0);
        spMuneca.setModel(nmMuneca);
        
        SpinnerNumberModel nmPinza = new SpinnerNumberModel();
        nmPinza.setMaximum(180);
        nmPinza.setMinimum(0);
        spPinza.setModel(nmPinza);


    }
    

}


