/*Brazo Robótico de 5 grados  de libertad
  El propósito de esta práctica es desarrollar paso a paso un proyecto para controlar y
  programar un Brazo Robot, simulando las funciones básicas de un robot industrial.

  Sandra Luz Godinez Guerrero
  Abril Alejandra Santos Salas
  Maria de los Angeles Espinoza Amaro
*/
//************************************************ LIBRERIAS **********************************************************************************************
//Libreria del LCD
#include <LiquidCrystal.h>
//Libreria para el control de los micro servos
#include <Servo.h>
//Librería EEPROM
#include <EEPROM.h>
//instanciar los led y el boton de paro
#define ledVerde 6
#define ledRojo 7
#define ledNaranja 5
#define interrupcion 2
//Crear el objeto LCD con los números correspondientes (rs, en, d4, d5, d6, d7)
LiquidCrystal lcd(30, 28, 32, 22, 24, 26);

//************************************************ VARIABLES **********************************************************************************************
//Se crea una variable con el valor de la posición de memoria
//en la que se va a almacenar el byte.
int direccionMemoria = 0;
int cont;//Contador para datos que llegan del puerto serial
int posiciones[5]; //arreglo de posiciones que llegan por el puerto serial
int dato = 0;
int dato_rx;//variable donde se almacenara el valor del motor a pasos
int numero_pasos = 0; //numero de pasos dados

Servo  servos [4]; //Arreglo de microServos

//Variables booleanas
boolean abortar = false;
boolean problema = false;
boolean mover = false;
boolean registro = false;


//************************************************* SETUP **************************************************************************************************
void setup() {
  cont = 0;
  
  //Instanciar servos
  servos[0].attach(12); //servoPinza
  servos[1].attach(3); //servoMuñeca
  servos[2].attach(4); //servoCodo
  servos[3].attach(13); //servoHombro

  pinMode(ledVerde, OUTPUT);
  pinMode(ledRojo, OUTPUT);
  pinMode(ledNaranja, OUTPUT);
  pinMode(interrupcion, INPUT);

 //Motor a pasos
  pinMode(8, OUTPUT);     //conectar a IN1
  pinMode(9, OUTPUT);     //conectar a IN2
  pinMode(10, OUTPUT);    //conectar a IN3
  pinMode(11, OUTPUT);     //conectar a IN4

  //Inicar LCD, es de 16 columnas y dos filas
  lcd.begin(16, 2);
  lcd.setCursor(0, 0);
  lcd.print("BRAZO ROBOTICO  ");
  //Llamar al metodo para cargar la memoria EEPROM
  cargarEEPROM();
  //Interrupción del boton de paro
  attachInterrupt(digitalPinToInterrupt(interrupcion), interrup, RISING);
  Serial.begin(9600);
}

//************************************************************ LOOP *****************************************************************************************
void loop() {
  digitalWrite(ledRojo, LOW);
  digitalWrite(ledNaranja, LOW);
  digitalWrite(ledVerde, HIGH);

  if (Serial.available()) { 
    delay(200);
    //Verificar que hay datos en el Serial
    while (Serial.available() > 0) {
      //Obtener los datos que estan antes de una coma
      String texto = Serial.readStringUntil(',');
      //Convertir el dato a entero
      int data = texto.toInt();

      //Colocar en el arreglo la variable data
      posiciones[cont] = data;
      cont++;
      
      if (data == 800) { //Si se recibe el 800 quiere decir que ejecutara la rutina creada, al puerto serial llega el 800 más los datos de la rutina
        registro = true;
        cont = 0;
      }
      
      if (registro == true) {
        if (cont == 5) {
          digitalWrite(ledVerde, LOW);
          digitalWrite(ledNaranja, HIGH);
          digitalWrite(ledRojo, LOW);
          lcd.clear();
          lcd.setCursor(0, 0);
          lcd.print("   Movimiento   ");
          lcd.setCursor(0, 1);
          lcd.print("   de Brazo     ");
          guardaEEPROM();
          Ejecutar();//Llamar al metodo que ejecuta los pasos registrados
          delay(1000);
        }
      }
      //Si se recibe el numero 400 entonces se activaran las acciones de paro
      if (data == 400) { 
        abortar = true;
      }

      if (data == 700) {//Si se recibe el 800 quiere decir que ejecutara la rutina por default
        mover = true;

      }
    }
    registro = false;
  }

  if (abortar == true) {
    digitalWrite(ledVerde, LOW);
    digitalWrite(ledNaranja, HIGH);
    digitalWrite(ledRojo, LOW);
    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print("    ESPERE      ");
    lcd.setCursor(0, 1);
    lcd.print("  EJECUTANDOSE  ");
    guardaEEPROM();
    Ejecutar();//Metodo de ejecucion de pasos

  }

  if (mover == true) { //Rutina para mover un objeto de posición
    digitalWrite(ledVerde, LOW);
    digitalWrite(ledNaranja, HIGH);
    digitalWrite(ledRojo, LOW);
    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print("     RUTINA     ");
    tomarObjeto();
    abortar = false;
    cont = 0;
    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print(" ............... ");

  }

  //Si hay un problema se ejecutan movimientos predeterminados
  while (problema == true) {
    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print("   TENEMOS     ");
    lcd.setCursor(0, 1);
    lcd.print("    PROBLEMAS   ");
    digitalWrite(ledRojo, HIGH);
    digitalWrite(ledVerde, LOW);
    digitalWrite(ledNaranja, LOW);
    Emergencia();
    abortar = false;
    cont = 0;
    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print("...............");
  }

}

//************************************************************METODO PARA EJECUTAR LAS POSICIONES***************************************************
void Ejecutar() {
  servos[3].write(posiciones[0]);
  delay(800);

  dato_rx = posiciones[1];
  dato_rx *= 1.42222222222; // Ajuste de 512 vueltas a los 360 grados
  while (dato_rx > numero_pasos) { // Girohacia la izquierda en grados
    paso_izquierda();
    numero_pasos = numero_pasos + 1;
  }
  while (dato_rx < numero_pasos) { // Giro hacia la derecha en grados
    paso_derecha();
    numero_pasos = numero_pasos - 1;
  }
  apagado();         // Apagado del Motor

  // Se ejecutan cada una de las posiciones con su respectivo servo
  servos[2].write(posiciones[2]);
  delay(800);
  servos[1].write(posiciones[3]);
  delay(800);
  servos[0].write(posiciones[4]);
  delay(800);

  abortar = false;
  cont = 0;
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print(".................");
  delay(1000);

}

//********************************************METODO PARA EJECUTAR INTERRUPCIÓN*****************************************************
void interrup() {
  problema = !problema;
  Emergencia();
}

//**********************************METODO DE MOVIMIENTOS DE EMERGENCIA************************************************************
void Emergencia() {
  servos[0].write(0);
  delay(100);
  servos[1].write(90);
  delay(100);
  servos[2].write(90);
  delay(100);
  servos[3].write(130);
  delay(100);
}

//*******************************METODO PARA GUARDAR EN LA MEMORIA EEPROM ***********************************
void guardaEEPROM() {
      for(int i=0; i <= sizeof(posiciones);i++){ //guardar en la memoria EEPROM las posiciones del brazo robótico
       // EEPROM.put(direccionMemoria,posiciones[i]);
       EEPROM.update(direccionMemoria,posiciones[i]);
       direccionMemoria++;
      }
      direccionMemoria=0;
}

//***********************************METODO PARA CARGAR LO QUE HAY EN LA MEMORIA EEPROM**************************
void cargarEEPROM() {
  
     direccionMemoria = sizeof(posiciones);
      for(int i = direccion-1 ;i >= 0 ;i--){
         EEPROM.get(direccionMemoria,dato);
        posiciones[i]=dato;
    }
  

}

//*************************************METODO PARA MOVER EL MOTOR A PASOS HACIA LA DERECHA ********************************************
void paso_derecha() {        
  digitalWrite(11, LOW);
  digitalWrite(10, LOW);
  digitalWrite(9, HIGH);
  digitalWrite(8, HIGH);
  delay(5);
  digitalWrite(11, LOW);
  digitalWrite(10, HIGH);
  digitalWrite(9, HIGH);
  digitalWrite(8, LOW);
  delay(5);
  digitalWrite(11, HIGH);
  digitalWrite(10, HIGH);
  digitalWrite(9, LOW);
  digitalWrite(8, LOW);
  delay(5);
  digitalWrite(11, HIGH);
  digitalWrite(10, LOW);
  digitalWrite(9, LOW);
  digitalWrite(8, HIGH);
  delay(5);
}

//*****************************************************************METODO PARA MOVER EL MOTOR A PASOS HACIA LA IZQUIERDA ********************************************
void paso_izquierda() {       
  digitalWrite(11, HIGH);
  digitalWrite(10, HIGH);
  digitalWrite(9, LOW);
  digitalWrite(8, LOW);
  delay(5);
  digitalWrite(11, LOW);
  digitalWrite(10, HIGH);
  digitalWrite(9, HIGH);
  digitalWrite(8, LOW);
  delay(5);
  digitalWrite(11, LOW);
  digitalWrite(10, LOW);
  digitalWrite(9, HIGH);
  digitalWrite(8, HIGH);
  delay(5);
  digitalWrite(11, HIGH);
  digitalWrite(10, LOW);
  digitalWrite(9, LOW);
  digitalWrite(8, HIGH);
  delay(5);
}

//************************************************************METODO PARA APAGAR EL MOTOR A PASOS****************************************************
void apagado() {
  digitalWrite(11, LOW);
  digitalWrite(10, LOW);
  digitalWrite(9, LOW);
  digitalWrite(8, LOW);
}

//*******************************************************METODO PARA EJECUTAR RUTINA PARA TOMAR UN OBJETO Y MOVERLO DE LUGAR*************************
void tomarObjeto() {
  servos[3].write(90);
  delay(500);
  servos[2].write(90);
  delay(500);
  servos[1].write(90);
  delay(500);
  servos[0].write(90);
  delay(500);

  servos[3].write(138);
  delay(500);
  servos[2].write(60);
  delay(500);
  servos[1].write(90);
  delay(500);
  servos[0].write(50);
  delay(500);


  servos[3].write(90);
  delay(500);
  servos[2].write(90);
  delay(500);
  servos[1].write(90);
  delay(500);
  servos[0].write(50);
  delay(500);

  dato_rx = 180;
  dato_rx *= 1.42222222222; // Ajuste de 512 vueltas a los 360 grados
  while (dato_rx > numero_pasos) { // Girohacia la izquierda en grados
    paso_izquierda();
    numero_pasos = numero_pasos + 1;
  }
  while (dato_rx < numero_pasos) { // Giro hacia la derecha en grados
    paso_derecha();
    numero_pasos = numero_pasos - 1;
  }
  apagado();         // Apagado del Motor para que no se caliente
  //gira180

  servos[3].write(138);
  delay(500);
  servos[2].write(60);
  delay(500);
  servos[1].write(90);
  delay(500);
  servos[0].write(180);
  delay(500);


  servos[3].write(90);
  delay(500);
  servos[2].write(90);
  delay(500);
  servos[1].write(90);
  delay(500);
  servos[0].write(90);
  delay(500);

  dato_rx = 0;
  dato_rx *= 1.42222222222; // Ajuste de 512 vueltas a los 360 grados
  while (dato_rx > numero_pasos) { // Girohacia la izquierda en grados
    paso_izquierda();
    numero_pasos = numero_pasos + 1;
  }
  while (dato_rx < numero_pasos) { // Giro hacia la derecha en grados
    paso_derecha();
    numero_pasos = numero_pasos - 1;
  }
  apagado();         // Apagado del Motor para que no se caliente
  //gira 0
  mover = false;
}

