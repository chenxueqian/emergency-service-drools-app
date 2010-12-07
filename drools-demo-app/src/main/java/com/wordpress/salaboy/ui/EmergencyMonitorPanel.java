/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * EmergencyMonitorPanel.java
 *
 * Created on Nov 25, 2010, 5:58:03 PM
 */

package com.wordpress.salaboy.ui;

import java.awt.Color;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.RectangleInsets;

/**
 *
 * @author esteban
 */
public class EmergencyMonitorPanel extends javax.swing.JPanel {
    
    private static String DATE_PATTERN = "hh:mm:ss";
    private final TimeSeriesCollection dataset = new TimeSeriesCollection();
    private TimeSeries pulseTimeSeries = new TimeSeries("Pulse");

    private EmergencyFrame parent;
    
    /** Creates new form EmergencyMonitorPanel */
    public EmergencyMonitorPanel(EmergencyFrame parent) {
        this.parent = parent;
        initComponents();
        
        pulseTimeSeries.setMaximumItemAge(20000);
        dataset.addSeries(pulseTimeSeries);
        final JFreeChart chart = createTimeSeriesChart();
        ChartPanel cPanel = new ChartPanel(chart);
        cPanel.setPreferredSize(new java.awt.Dimension(parent.getWidth(), 270));
        cPanel.setName("Monitor");
        
        parent.getMainTabPanel().add(cPanel);
        parent.getMainTabPanel().setSelectedComponent(cPanel);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(254, 132, 9));
        setName("Monitor"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    
    private JFreeChart createTimeSeriesChart() {
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"Pulse", 
				"X", 
				"Y",
				dataset, 
				true, 
				true, 
				false
		);

		chart.setBackgroundPaint(Color.white);

		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);
		plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}

		DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat(DATE_PATTERN));
        
                NumberAxis rangeAxis = (NumberAxis)plot.getRangeAxis();
                NumberFormat numberformat = NumberFormat.getInstance();
                numberformat.setMaximumFractionDigits(0);
                numberformat.setMinimumFractionDigits(0);
                rangeAxis.setNumberFormatOverride(numberformat);
                
		return chart;
	}
    
    public void updateMonitorGraph(double pulse){
        pulseTimeSeries.removeAgedItems(false);
        pulseTimeSeries.addOrUpdate(new Millisecond(), pulse-235);
    }
    
}
