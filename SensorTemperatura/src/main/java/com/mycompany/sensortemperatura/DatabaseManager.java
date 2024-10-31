/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sensortemperatura;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author javiergiron
 */
public class DatabaseManager {

    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    conexion acceso = new conexion();

    public ArrayList<SensorData> listar() {
        String sql = "SELECT * FROM sensor_data";

        try {
            con = acceso.Conectar(); // Asegúrate de que 'acceso' es tu clase de conexión
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            ArrayList<SensorData> datos = new ArrayList<>();

            while (rs.next()) {
                String id = rs.getString("id");
                float humedad = rs.getFloat("humedad");
                float temperatura = rs.getFloat("temperatura");
                String timestamp = rs.getString("timestamp");

                SensorData sensorData = new SensorData(id, humedad, temperatura);
                datos.add(sensorData);
            }

            return datos;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            // Cierra recursos en el bloque finally para asegurar que se cierran incluso si hay una excepción
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public void crear(SensorData sensorData) {
        String sql = "INSERT INTO sensor_data (id, humedad, temperatura) VALUES (?, ?, ?)";

        try {
            con = acceso.Conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, sensorData.getId());
            ps.setFloat(2, sensorData.getHumedad());
            ps.setFloat(3, sensorData.getTemperatura());
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            // Cierra recursos en el bloque finally
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    
    public void limpiarRegistros() {
    String sql = "TRUNCATE TABLE sensor_data"; // Usar TRUNCATE en lugar de DELETE
    
    try {
        con = acceso.Conectar();
        ps = con.prepareStatement(sql);
        ps.executeUpdate();
        System.out.println("Todos los registros han sido eliminados.");

    } catch (Exception e) {
        System.out.println("Error al limpiar registros: " + e.getMessage());
    } finally {
        // Cierra recursos en el bloque finally
        try {
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

    public void exportarAPDF(String filePath) {
    ArrayList<SensorData> datos = listar(); // Obtener registros

    Document document = new Document();
    try {
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();

        // Agregar título con formato
        Paragraph title = new Paragraph("Registro de Sensor de Temperatura y Humedad");
        title.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER); // Centrar el título
        title.setSpacingAfter(20); // Espacio después del título
        title.setFont(com.itextpdf.text.FontFactory.getFont(com.itextpdf.text.FontFactory.HELVETICA_BOLD, 16)); // Negrita y tamaño
        document.add(title);

        // Crear tabla
        PdfPTable table = new PdfPTable(4); // 4 columnas: ID, Humedad, Temperatura, Timestamp
        table.addCell("ID");
        table.addCell("Humedad");
        table.addCell("Temperatura");
        table.addCell("Timestamp");

        // Llenar la tabla con datos
        for (SensorData sensorData : datos) {
            table.addCell(sensorData.getId());
            table.addCell(String.valueOf(sensorData.getHumedad()));
            table.addCell(String.valueOf(sensorData.getTemperatura()));
            table.addCell(sensorData.getTimestamp());
        }

        document.add(table);
        System.out.println("PDF generado exitosamente en: " + filePath);

    } catch (DocumentException | IOException e) {
        System.out.println("Error al generar PDF: " + e.getMessage());
    } finally {
        document.close(); // Cerrar el documento
    }
}

    
    public ArrayList<SensorData> obtenerTop3Temperaturas() {
        String sql = "SELECT * FROM sensor_data ORDER BY temperatura DESC LIMIT 3";
        ArrayList<SensorData> datos = new ArrayList<>();

        try {
            con = acceso.Conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                String id = rs.getString("id");
                float humedad = rs.getFloat("humedad");
                float temperatura = rs.getFloat("temperatura");
                String timestamp = rs.getString("timestamp");

                SensorData sensorData = new SensorData(id, humedad, temperatura);
                datos.add(sensorData);
            }

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return datos;
    }
    }
