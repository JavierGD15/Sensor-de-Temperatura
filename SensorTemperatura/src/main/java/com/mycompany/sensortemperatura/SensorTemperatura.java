/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.sensortemperatura;


import com.fazecast.jSerialComm.SerialPort;

public class SensorTemperatura {
    public static void main(String[] args) {
        // Mostrar puertos disponibles
        SerialPort[] ports = SerialPort.getCommPorts();
        for (SerialPort port : ports) {
            System.out.println(port.getSystemPortName());
            if (port.getSystemPortName().equals("cu.usbserial-A5069RR4") && port.openPort()) {
                // Si se encuentra el puerto correcto, se inicia la interfaz de usuario
                
                Informacion ventana = new Informacion(port);
                ventana.setVisible(true);
                break;
            }
        }
    }
}

