/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sensortemperatura;

/**
 *
 * @author javiergiron
 */
public class SensorData {
    private String id; // ID basado en la fecha (YYYYMMDD)
    private float humedad; // Valor de humedad
    private float temperatura; // Valor de temperatura
    private String timestamp; // Marca de tiempo

    // Constructor
    public SensorData(String id, float humedad, float temperatura) {
        this.id = id;
        this.humedad = humedad;
        this.temperatura = temperatura;
        this.timestamp = getCurrentTimestamp(); // Obtener la marca de tiempo actual
    }

    // Método para obtener la marca de tiempo actual
    private String getCurrentTimestamp() {
        return java.time.LocalDateTime.now().toString(); // Genera un timestamp en formato ISO
    }

    // Getters
    public String getId() {
        return id;
    }

    public float getHumedad() {
        return humedad;
    }

    public float getTemperatura() {
        return temperatura;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
