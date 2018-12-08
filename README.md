# Práctica 4: Brazo Robotico de 5 ejes

**INSTITUTO TECNOLÓGICO DE LEÓN**

Integrantes:<br>
				- Espinoza Amaro Maria de los Angeles.<br>
				- Godinez Guerrero Sandra Luz.<br>
				- Santos Salas Abril Alejandra.<br><br>
	Contacto<br>
				angeles.espinoza.amaro@gmail.com<br>
        			sandy.goboo@gmail.com<br>
				alejandra.santos.97@hotmail.com<br><br>
						
        
	
   Sistemas Programables.<br>
				-Ing. Carlos Rafael Levy Rojas.<br>
        
Práctica 4 Brazo Robotico de 5 ejes<br>
El robot se debe controlar en modo "remoto"(a través de un programa en Java por medio del puerto serial)<br>
La información para el usuario se podrá proporcionar a través de colores, una pantalla LCD y/ó sonido (buzzer).<br>
Debe de contener un botón de para de emergencia (Físico).<br>
Si existe un fallo y/o corte de energía, después de reestablecer la corriente el robot debe de continuar el<br>
programa (aunque este no se encuentre conectado a la aplicación).<br>
<br>
    Los brazos roboticos se pueden clasificar de acuerdo con el número de "articulaciones" o "grados de libertad" que tienen<br>
    *La base puede girar el brazo 360°.<br>
    *El hombro es el responsable de levantar o bajar el brazo verticalmente.<br>
    *El codo hará que el brazo avance o retroceda.<br>
    *La muñeca hará que gire la pinza.<br>
    *La garra o pinza funciona abriendo o cerrándose para agarrar las cosas.<br>
    <br><br><br>
    
**Proposito.**
<br>
  El proposito de dicha fue el ser capaces de usar nuestros conocimientos y las herramientas que conocemos para  <br>
  controlar un brazo robotico con la ayuda de una interfaz que permitieran la comunicación via Serial.<br><br>
	
**Entorno.**
<br>
  Para el desarrollo de esta practica se utilizo el sistema operativo Elementary OS 0.4.14, complementandose con windows 10, <br>
  con apoyo de IDE ARDUINO, Netbeans para el envio de mensajes con Java y la libreria PanamaHitek_Arduino-3.0.0.<br>
	<br>
**Materiales usados.**
<br>
	-Arduino MEGA <br>
	-Cable USB <br>
	-Placa de prototipado o protoboard <br>
	-Cables<br>
        -Brazo robotico<br>
        -micro servomotor 9g<br>
        -motor a pasos<br>
	-Pantalla LCD 16x2<br>
	-LEDS<br>
	-Joystick<br>
	-Potenciometro<br>
	-Buzzer
	-Push Button
	<br><br>
	
**Procedimiento.**
<br>
Para desarrollar el proyecto creamos la interfaz en Java con ayuda de la libreria antes mencionada, se usaron JSpinner para mandar el <br>
Numero de grados a arduino, en este caso se mandaron separados por comas los datos de los cinco movimientos que haria
<br>
Se implementarios botones para guardar los movimientos, crear rutinas y ejecutar una rutina por default<br>
En arduino se uso la memoria EEPROM para guardar los movimientos y ejecutar las rutinas, se usaron distintos métodos para detectar<br>
que acción debia realizar el brazo de acuerdo al botón elegido<br>
Como funcionalidad extra se implementaron 2 Joystick para manejar el brazo de formas más sencilla<br><br>
