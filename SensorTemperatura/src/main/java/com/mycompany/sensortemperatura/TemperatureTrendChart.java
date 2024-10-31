/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sensortemperatura;

/**
 *
 * @author javiergiron
 */
import java.util.ArrayList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;

public class TemperatureTrendChart extends JFrame {

    public TemperatureTrendChart(String title, ArrayList<SensorData> top3) {
        super(title);
        
        // Crear el conjunto de datos para el gráfico
        CategoryDataset dataset = createDataset(top3);
        
        // Crear el gráfico
        JFreeChart chart = ChartFactory.createBarChart(
                "Top 3 Temperaturas",
                "ID",
                "Temperatura (°C)",
                dataset
        );

        // Crear el panel y añadirlo a la ventana
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        setContentPane(chartPanel);
    }

    private CategoryDataset createDataset(ArrayList<SensorData> top3) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (SensorData data : top3) {
            dataset.addValue(data.getTemperatura(), "Temperatura", data.getId());
        }

        return dataset;
    }
}

