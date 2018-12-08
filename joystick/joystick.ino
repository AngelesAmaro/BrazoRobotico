#include <Servo.h>
//Libreria del LCD
#include <LiquidCrystal.h>

//Crear el objeto LCD con los números correspondientes (rs, en, d4, d5, d6, d7)
LiquidCrystal lcd(30, 28, 32, 22, 24, 26);

//Motor a pasos
#include <Stepper.h>

//Variables para mover el motor a pasos 
int pasos=2048;
int pasosPorLectura=5;
int leer;
int rotacion;
//pines del motor
Stepper stepper(pasos,8,10,9,11);
int velocidadMotor;

//Servos
Servo hombroServo;//asignamos un eje a un servo y otro eje al otro servo
Servo codoServo;
Servo pinzaServo;
Servo munecaServo;

int servoHombroPosition = 90;//posición de referencia
int servoCodoPosition = 90;
int servoMunecaPosition = 90;

int joystickHombroSpeed = 0;
int joystickCodoSpeed = 0;
int joystickMunecaSpeed = 0;

int joystickHombroPin = A0; //marcamos entradas analógicas que medirán el ángulo del joystick
int joystickCodoPin = A1;
int joystickMunecaPin = A2;

int servoHombroPin = 13;//declaramos los pines digitales por donde le entrará la instrucción del joystick al servo.
int servoCodoPin = 4;
int servoPinzaPin = 12;
int servoMunecaPin = 3;
int boton1 = 52;
int boton2 = 50;

int buttonState1 = 0;
int buttonState2 = 0;

void setup()
{

  pinMode(servoHombroPin, OUTPUT);//se declara igual que declarariamos un led
  pinMode(servoCodoPin, OUTPUT);
  pinMode(servoMunecaPin, OUTPUT);
  pinMode (A3, INPUT);
  stepper.setSpeed(400);
  hombroServo.attach(servoHombroPin);
  codoServo.attach(servoCodoPin);
  pinzaServo.attach(servoPinzaPin);
  munecaServo.attach(servoMunecaPin);
  
  pinMode(boton1, INPUT_PULLUP); 
  pinMode(boton2, INPUT_PULLUP);
  lcd.begin(16, 2);

  lcd.setCursor(0, 0);
  lcd.print("BRAZO ROBOTICO  ");
  Serial.begin(9600);
}


void loop()
{
  //Leer joystick del motor a pasos
    leer=analogRead(A3);
    velocidadMotor=abs(map(leer, 0, 1023, -20, 20))*10;
    if (velocidadMotor>10){
      rotacion = leer>511?1:-1;
      stepper.setSpeed(velocidadMotor);
      stepper.step(rotacion*5);
    }


  joystickHombroSpeed = (analogRead(joystickHombroPin) - 512) / 50;
  // -512 to provide equal +/- numbers
  joystickCodoSpeed = (analogRead(joystickCodoPin) - 512) / -50;
  // negative 50 to reverse direction
  joystickMunecaSpeed = (analogRead(joystickMunecaPin) - 512) / -50;
  // negative 50 to reverse direction

  servoHombroPosition = constrain((servoHombroPosition + joystickHombroSpeed), 1, 180);
  servoCodoPosition = constrain((servoCodoPosition + joystickCodoSpeed), 1, 180);
  servoMunecaPosition = constrain((servoMunecaPosition + joystickMunecaSpeed), 1, 180);

  hombroServo.write(servoHombroPosition);
  codoServo.write(servoCodoPosition);
  munecaServo.write(servoMunecaPosition);

  delay(50);
  //Boton Joystick 1
  buttonState1 = digitalRead(boton1);
  buttonState2 = digitalRead(boton2);
  if (buttonState1 == HIGH) {     
    pinzaServo.write(90); 
  }
  if (buttonState2 == HIGH) {
    pinzaServo.write(0); 
  }
  
  delay(15);
}
