package Brazo;

//Librerias
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
    private JButton btnGuardarPinza,btnGuardarMuneca,btnGuardarCodo,btnGuardarHombro,btnGuardarBase,btnRutina,btnBorrar,btnParo;
    private JLabel lblTitulo,lblPinza,lblMuneca,lblCodo,lblHombro,lblBase,lblImagen,lblGrados;
    private JSpinner spPinza,spMuneca,spCodo,spHombro,spBase; 
    
    public Brazo(){
        super("BRAZO ROBOTICO");
        setLayout(null);
        getContentPane().setBackground(new Color(164,211,238));
        initComponents();
        
        //Clase anonima para las acciones de btnGuardarBase
        btnGuardarBase.addActionListener(new ActionListener(){
          
            public void actionPerformed(ActionEvent e) {
              String data =spBase.getValue().toString();
              System.out.println(data);
               try {
                    ino.sendData(data);
                } catch (ArduinoException | SerialPortException ex) {
                    Logger.getLogger(Brazo.class.getName()).log(Level.SEVERE, null, ex);
                }
                 
            }            
        });
        
        //Clase anonima para las acciones de btnGuardarHombro
        btnGuardarHombro.addActionListener(new ActionListener(){
          
            public void actionPerformed(ActionEvent e) {
               String data =spHombro.getValue().toString();
                System.out.println(data);
                data = data + "H";
               try {
                    ino.sendData(data);
                } catch (ArduinoException | SerialPortException ex) {
                    Logger.getLogger(Brazo.class.getName()).log(Level.SEVERE, null, ex);
                }
                 
            }       
                       
        });
        
        //Clase anonima para las acciones de btnGuardarCodo
        btnGuardarCodo.addActionListener(new ActionListener(){
          
            public void actionPerformed(ActionEvent e) {
               String data =spCodo.getValue().toString();
               System.out.println(data);
               try {
                    ino.sendData(data);
                } catch (ArduinoException | SerialPortException ex) {
                    Logger.getLogger(Brazo.class.getName()).log(Level.SEVERE, null, ex);
                }
              
            }            
        }); 
        
         //Clase anonima para las acciones de btnGuardarMuneca
        btnGuardarMuneca.addActionListener(new ActionListener(){
          
            public void actionPerformed(ActionEvent e) {
               String data =spMuneca.getValue().toString();
               System.out.println(data);
               try {
                    ino.sendData(data);
                } catch (ArduinoException | SerialPortException ex) {
                    Logger.getLogger(Brazo.class.getName()).log(Level.SEVERE, null, ex);
                }
               
            }            
        });
        
         //Clase anonima para las acciones de btnGuardarPinza
        btnGuardarPinza.addActionListener(new ActionListener(){
          
            public void actionPerformed(ActionEvent e) {
               String data =spPinza.getValue().toString();
               System.out.println(data);
               try {
                    ino.sendData(data);
                } catch (ArduinoException | SerialPortException ex) {
                    Logger.getLogger(Brazo.class.getName()).log(Level.SEVERE, null, ex);
                }
             
            }            
        });
        
         //Clase anonima para las acciones de btnParo
        btnParo.addActionListener(new ActionListener(){
          
            public void actionPerformed(ActionEvent e) {
               
               try {
                   //Enviamos el numero 365 ya que no es posible que se mande por medio de los spinners puesto que el # mayor es 360
                    ino.sendData("400");
                } catch (ArduinoException | SerialPortException ex) {
                    Logger.getLogger(Brazo.class.getName()).log(Level.SEVERE, null, ex);
                }
             
            }            
        });
        
       //Clase anonima para las acciones de btnRutina
       btnRutina.addActionListener(new ActionListener(){
          
            public void actionPerformed(ActionEvent e) {
               
               try {
                   //Enviamos el numero 500 ya que no es posible que se mande por medio de los spinners puesto que el # mayor es 360
                    ino.sendData("500");
                } catch (ArduinoException | SerialPortException ex) {
                    Logger.getLogger(Brazo.class.getName()).log(Level.SEVERE, null, ex);
                }
             
            }            
        });
       
       //Clase anonima para las acciones de btnBorrar
       btnBorrar.addActionListener(new ActionListener(){
          
            public void actionPerformed(ActionEvent e) {
               
               try {
                   //Enviamos el numero 500 ya que no es posible que se mande por medio de los spinners puesto que el # mayor es 360
                    ino.sendData("600");
                } catch (ArduinoException | SerialPortException ex) {
                    Logger.getLogger(Brazo.class.getName()).log(Level.SEVERE, null, ex);
                }
             
            }            
        });

    }
    

    public void initComponents(){
        
        ino = new PanamaHitek_Arduino();
        try {
            ino.arduinoTX("COM7", 9600);
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
        
    //Boton para reproducir la rutina almacenada
        btnRutina = new JButton("Reproducir rutina");
        btnRutina.setBounds(800,800,150,40);
        add(btnRutina);
        
    //Boton para borrar lo que se encuentre guardado en la memoria    
        btnBorrar = new JButton("Borrar");
        btnBorrar.setBounds(1000,800,100,40);
        add(btnBorrar);
        
    //Boton para detener el brazo    
        btnParo = new JButton("Detener");
        btnParo.setBounds(1150,800,100,40);
        add(btnParo);
        
    //Acciones a la base del brazo
        lblBase = new JLabel("Base");
        lblBase.setFont(Font.decode("Bodoni MT 24"));
        lblBase.setBounds(200,250,100,40);
        add(lblBase);
        
        spBase = new JSpinner();
        spBase.setBounds(300,250,100,40);
        add(spBase);
        
        btnGuardarBase = new JButton("Guardar");
        btnGuardarBase.setBounds(450,250,100,40);
        add(btnGuardarBase);
        
        
    //Acciones  del hombro del brazo
        lblHombro = new JLabel("Hombro");
        lblHombro.setFont(Font.decode("Bodoni MT 24"));
        lblHombro.setBounds(200,350,100,40);
        add(lblHombro);
        
        spHombro = new JSpinner();
        spHombro.setBounds(300,350,100,40);
        add(spHombro);
        
        btnGuardarHombro = new JButton("Guardar");
        btnGuardarHombro.setBounds(450,350,100,40);
        add(btnGuardarHombro);
        
    //Acciones  del hombro del brazo
        lblCodo = new JLabel("Codo");
        lblCodo.setFont(Font.decode("Bodoni MT 24"));
        lblCodo.setBounds(200,450,100,40);
        add(lblCodo);
        
        spCodo = new JSpinner();
        spCodo.setBounds(300,450,100,40);
        add(spCodo);
        
        btnGuardarCodo = new JButton("Guardar");
        btnGuardarCodo.setBounds(450,450,100,40);
        add(btnGuardarCodo);
    
    //Acciones  de la muñeca del brazo
        lblMuneca = new JLabel("Muñeca");
        lblMuneca.setFont(Font.decode("Bodoni MT 24"));
        lblMuneca.setBounds(200,550,100,40);
        add(lblMuneca);
        
        spMuneca = new JSpinner();
        spMuneca.setBounds(300,550,100,40);
        add(spMuneca);
        
        btnGuardarMuneca = new JButton("Guardar");
        btnGuardarMuneca.setBounds(450,550,100,40);
        add(btnGuardarMuneca);
        
    //Acciones  de las Pinzas del brazo
        lblPinza = new JLabel("Pinza");
        lblPinza.setFont(Font.decode("Bodoni MT 24"));
        lblPinza.setBounds(200,650,100,40);
        add(lblPinza);
        
        spPinza = new JSpinner();
        spPinza.setBounds(300,650,100,40);
        add(spPinza);
        
        btnGuardarPinza = new JButton("Guardar");
        btnGuardarPinza.setBounds(450,650,100,40);
        add(btnGuardarPinza);
        
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


