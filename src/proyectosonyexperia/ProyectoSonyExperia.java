/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectosonyexperia;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;



import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author jesus13santi
 */
public class ProyectoSonyExperia {

   public static Semaphore semPantallas = new Semaphore(40);  
   public static Semaphore semBotones = new Semaphore(45);  
   public static Semaphore semPinCarga = new Semaphore(15);  
   public static Semaphore semCamaras = new Semaphore(20);  
   public static Semaphore semSonyExperia = new Semaphore(1);  
   public static Semaphore semJefeGerente = new Semaphore(1,true);  
   public static Semaphore semProductores = new Semaphore(10);  
   public static Semaphore mutexPantallas = new Semaphore(1);
   public static Semaphore mutexCamaras = new Semaphore(1);
   public static Semaphore mutexBotones = new Semaphore(1);
   public static Semaphore mutexPinCarga = new Semaphore(1);
   public static Semaphore mutexSalario = new Semaphore(1);
   
   
   public static float dia = 10;
   public static float dia_minuto = dia*1000/24/60;
   public static boolean exit = true;
   public static int diasDespacho = 30;
   public static int max_trabajadores = 10;
   
   
   
   public static int max_botones = 45;
  
   public static int max_camaras = 20;
  
   public static int max_pantallas = 40;
   
   public static int max_pinCarga = 15;     
   
   public static int numProd_botones = 1;
   
   public static int sueldoProd_botones = 4;
 
   public static int numProd_camaras = 1 ;
   
   public static int sueldoProd_camaras = 5;
   
   public static int numProd_pantallas = 1;
           
   public static int sueldoProd_pantalla = 3;
            
   public static int numProd_pinCarga = 1;
            
   public static int sueldoProd_pinCarga = 5;
   
   
            
   public static int num_ensambladores = 1;
            
   public static int sueldo_ensamblador = 6;
            
   public static int sueldo_Jefe = 7;
          
   public static int sueldo_Gerente = 180;
   
   public static volatile int numSonyExperiaVendidos = 0;
   public static volatile float gastosSalarios;       
   public static volatile float dinero_telefonosVendidos;       
   public static volatile float costo_telefono=600;
   
   
   public static volatile ProductorPantalla[] prod_pantalla;
   public static volatile ProductorCamara[] prod_camara;
   public static volatile ProductorPinCarga[] prod_pinCarga;

   public static volatile ProductorBotones[] prod_botones;
   public static volatile Ensamblador[] ensambladores;
   
   
   
//   public static Semaphore mutexBotones = new Semaphore(1);
//   public static Semaphore mutexPinCarga = new Semaphore(1);
 
    
//    
    public static ProductorPantalla productorPantalla1;
    public static ProductorBotones productorBotones1;
    public static ProductorPinCarga productorPinCarga1;
    public static ProductorCamara productorCamara1;
    public static Ensamblador Ensamblador1;
    public static Jefe Jefe;
    public static Gerente Gerente;
  
  
    
    public static volatile int numPantallas=0;
    public static volatile int numBotones=0;
    public static volatile int numPinCarga=0;
    public static volatile int numCamara=0;
    public static volatile int numSonyExperia=0;
    
    
    
    //Dashboard
    public static volatile int prod_botonesDash;
    public static volatile int prod_camarasDash;
    public static volatile int prod_pantallasDash;
    public static volatile int prod_pinCargaDash;
    public static volatile int ensambladoresDash;
    
    public static volatile float gastosTotalesDash;
    public static volatile float telefonosVendidosDash;
    public static volatile float dineroTelefonosVendidos;
    public static volatile float diasDash;
    
    public static void vender(){
        
        try {
            
            semSonyExperia.acquire();
            dinero_telefonosVendidos += numSonyExperia*costo_telefono;
            Interfaz.venta$telefonos.setText(""+dinero_telefonosVendidos);
            System.out.println(dinero_telefonosVendidos);
            numSonyExperia = 0;
            semSonyExperia.release();
            
        } catch (InterruptedException ex) {
            Logger.getLogger(ProyectoSonyExperia.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public static void cambiarVarDashboard(){
            gastosTotalesDash = 0;
            telefonosVendidosDash = 0;
            dineroTelefonosVendidos  =0;
            diasDash = 0;

}
    
    
    public static void ensamblar2(){
        
        numBotones = 0;
        numPinCarga = 0;
        numPantallas = 0;
        numCamara = 0;
        
        numSonyExperia = 0;
        numSonyExperiaVendidos = 0;
        
        
        gastosTotalesDash = 0;
        telefonosVendidosDash=0;
        dineroTelefonosVendidos=0;
        diasDash =0;
        
        
        semPantallas = new Semaphore(max_pantallas);  
        semBotones = new Semaphore(max_botones);  
        semPinCarga = new Semaphore(max_pinCarga);  
        semCamaras = new Semaphore(max_camaras); 
        
        dinero_telefonosVendidos = 0;
        semJefeGerente = new Semaphore(1, true);
        
        Jefe = new Jefe(semJefeGerente, diasDespacho, sueldo_Jefe, dia, semJefeGerente);
        Gerente = new Gerente(semJefeGerente, diasDespacho, sueldo_Gerente, dia, semJefeGerente);
        
        
        prod_pantalla = new ProductorPantalla[max_trabajadores];
        prod_botones = new ProductorBotones[max_trabajadores];
        prod_camara = new ProductorCamara[max_trabajadores];
        prod_pinCarga = new ProductorPinCarga[max_trabajadores];

        ensambladores = new Ensamblador[max_trabajadores];
        gastosSalarios = 0;
        Jefe.start();
        Gerente.start();
        
        
        

        
        for( int i = 0; i<numProd_botones; i++) {
            prod_botones[i] = new ProductorBotones(semBotones, sueldoProd_botones,mutexBotones, dia );
            prod_botones[i].start();
        }
        
        for( int i = 0; i<numProd_pantallas; i++) {
            prod_pantalla[i] = new ProductorPantalla(semPantallas, sueldoProd_pantalla,mutexPantallas, dia );
            prod_pantalla[i].start();
        }
        
        for( int i = 0; i<numProd_pinCarga; i++) {
            prod_pinCarga[i] = new ProductorPinCarga(semPinCarga, sueldoProd_pinCarga,mutexPinCarga, dia );
            prod_pinCarga[i].start();
        }
        
        for( int i = 0; i<numProd_camaras; i++) {
            prod_camara[i] = new ProductorCamara(semCamaras, sueldoProd_camaras,mutexCamaras, dia );
            prod_camara[i].start();
        }
        

        for(int i = 0; i<num_ensambladores; i++){
            ensambladores[i] = new Ensamblador(semPinCarga, semPantallas, semCamaras, semBotones, semSonyExperia,  sueldo_ensamblador, mutexCamaras, mutexBotones, mutexPantallas, mutexPinCarga, dia);
            ensambladores[i].start();
        }
        
        Interfaz.numProductoresPinCarga.setText(""+numProd_pinCarga);
        Interfaz.numProductoresPantallas.setText(""+numProd_pantallas);
        Interfaz.numProductoresCamaras.setText(""+numProd_camaras);
        Interfaz.numProductoresBotones.setText(""+numProd_botones);
        
        Interfaz.numEnsambladores.setText(""+num_ensambladores);
        
        Interfaz.venta$telefonos.setText(""+dinero_telefonosVendidos);
        Interfaz.botonesProducidos.setText(""+numBotones);
        Interfaz.pantallasProducidas.setText(""+numPantallas);
        Interfaz.PinCargaProducidos.setText(""+numPinCarga);
        Interfaz.CamarasProducidas.setText(""+numCamara);

        
        Interfaz.TelefonosProducidos.setText(""+numSonyExperia);
        Interfaz.numTelefonosVendidos.setText(""+numSonyExperiaVendidos);
        
    }
    


    public static boolean readCSV(File file){
        
        try {
            
            String tmp;
            
            Scanner load = new Scanner( file );
            load.useDelimiter(",|\n|\r");
            
            load.nextLine();
            
            tmp = load.next();
            dia =  Float.parseFloat(tmp);
           
            tmp = load.next();
            diasDespacho = Integer.parseInt(tmp);
            
            tmp = load.next();
            max_botones = Integer.parseInt(tmp);
            tmp = load.next();
            max_camaras = Integer.parseInt(tmp);
            tmp = load.next();
            max_pantallas = Integer.parseInt(tmp);
            tmp = load.next();
            max_pinCarga = Integer.parseInt(tmp);     
            tmp = load.next();
            numProd_botones = Integer.parseInt(tmp);
            tmp = load.next();
            sueldoProd_botones = Integer.parseInt(tmp);
            tmp = load.next();
            numProd_camaras = Integer.parseInt(tmp);
            tmp = load.next();
            sueldoProd_camaras = Integer.parseInt(tmp);
            tmp = load.next();
            numProd_pantallas = Integer.parseInt(tmp);
            tmp = load.next();
            sueldoProd_pantalla = Integer.parseInt(tmp);
            tmp = load.next();
            numProd_pinCarga = Integer.parseInt(tmp);
            tmp = load.next();
            sueldoProd_pinCarga = Integer.parseInt(tmp);
            tmp = load.next();
            max_trabajadores = Integer.parseInt(tmp);
            tmp = load.next();
            num_ensambladores = Integer.parseInt(tmp);
            tmp = load.next();
            sueldo_ensamblador = Integer.parseInt(tmp);
            tmp = load.next();
            sueldo_Jefe = Integer.parseInt(tmp);
            System.out.println(tmp);
            tmp = load.next();
            
            sueldo_Gerente = Integer.parseInt(tmp);
            
            
            
            


            return !((numProd_pantallas + numProd_botones + numProd_pinCarga + numProd_camaras + num_ensambladores) >10 ||  max_trabajadores < 1 || numProd_pantallas < 1 || numProd_botones < 1 || numProd_pinCarga < 1 ||numProd_camaras < 1 || num_ensambladores < 1 || dia < 1 || diasDespacho < 1 );

            
        } catch (Exception e) {
            return false;
        }
      
        
    }
    

    
    public static void read(String filePath) {
    File file = new File(filePath);
    String guardar = null;
    boolean es = false;
    
    
    
    char[] aCaracteres = null;
    if (!file.exists()) {
                 System.out.println ("¡El archivo no existe!");
        return;
    }
    try {
                 // Crear objeto de lectura CSV
        CsvReader csvReader = new CsvReader(filePath);
                 // lee el encabezado del medidor
        csvReader.readHeaders();
//        System.out.println(csvReader.readRecord());
        while (csvReader.readRecord() && !es) {
                         // lee una fila de datos
            guardar = csvReader.getRawRecord();
            aCaracteres = guardar.toCharArray();
            if(aCaracteres[0] == '2'){
                System.out.println("Hola");
                es= true;
                
            }
//            System.out.println(csvReader.getRawRecord());
            
        }
        
        
        System.out.println(aCaracteres);
    } catch (IOException e) {
        e.printStackTrace();
    }
}
    public static void writeCsv(String filePath) {
    try {
        File file = new File(filePath);
        if (!file.exists()) {
            file.createNewFile();
        }
        
        // Crear objeto de escritura CSV
        CsvWriter csvWriter = new CsvWriter(filePath);
                 // escribir encabezado
        String[] headers = {"Productores Botones","Productores Camaras","Productores Pin Carga","Productores Pantallas","Ensambladores", "diasTranscurridos", "Telefonos Producidos", "Venta Telefonos $","Gastos Totales" };
       
        csvWriter.writeRecord(headers, false);

        String[] content1;
        content1 = new String[headers.length];
        content1[0]= "2";
        content1[1]= "1";
        content1[2]= "1";
        content1[3]= "1";
        content1[4]= "1";
        content1[5]= "0";
        content1[6]= "0";
        content1[7]= "0";
        content1[8]= "0";
       
        
        String[] content2 = {"2","2","5","4","5", "0","0","0", "0"};
        String[] content3 = {"1","1","1","1","1","0","0","0","0"};
        
        csvWriter.writeRecord(content1,true);
       
//        csvWriter.writeRecord(content2,false);
//        csvWriter.writeRecord(content3,false);
        
        
        
        
        
        csvWriter.close();
        
        
        
    } catch (IOException e) {
        e.printStackTrace();
        
    }
}
    
    public static void leerEscribircsv(String filePath){
        File file = new File(filePath);
        String guardar = null;
        boolean es = false;
        char[] aCaracteres = null;
        String[] aCarac = null;
        String[] headers;
        float sumaDias;
        float sumaTelefonosVendidosDash;
        float sumaVentaTelefono;
        float sumaGastosTotales;
        
        
        if(!ProyectoSonyExperia.exit){
             prod_botonesDash = ProyectoSonyExperia.numProd_botones;
           
            prod_camarasDash = ProyectoSonyExperia.numProd_camaras;
     
            prod_pantallasDash = ProyectoSonyExperia.numProd_pantallas;
        
            prod_pinCargaDash = ProyectoSonyExperia.numProd_pinCarga;
       
            ensambladoresDash = ProyectoSonyExperia.num_ensambladores;
        
        }
       
        
//        String proBotAux = null;
//        String proCamAux =null;
//        String proPanAux = null;
//        String proPinAux = null;
//        String EnsambladorAux = null;
        
        
        
        
        
    if (!file.exists()) {
                 System.out.println ("¡El archivo no existe!");
        return;
    }
    try {
                 // Crear objeto de lectura CSV
        CsvReader csvReader = new CsvReader(filePath);
        
        CsvWriter csvWriter = new CsvWriter(filePath);
                 // lee el encabezado del medidor
        csvReader.readHeaders();
        headers = csvReader.getHeaders();
        csvWriter.writeRecord(headers, true);
        aCarac = new String[headers.length];
//        System.out.println(csvReader.readRecord());
        while (csvReader.readRecord() && !es) {
                         // lee una fila de datos
            guardar = csvReader.getRawRecord();
//            aCaracteres = guardar.toCharArray();

            aCarac = guardar.split(",");
            System.out.println(Arrays.toString(aCarac));
            
            if(aCarac[0].equals(""+prod_botonesDash) && aCarac[1].equals(""+prod_camarasDash) && aCarac[2].equals(""+prod_pinCargaDash) && aCarac[3].equals(""+prod_pantallasDash) && aCarac[4].equals(""+ensambladoresDash) ) {
//                aCarac[0]= "3";
                sumaDias = Float.parseFloat(aCarac[5]) + diasDash;
                aCarac[5]= ""+sumaDias;
                sumaTelefonosVendidosDash = Float.parseFloat(aCarac[6]) + telefonosVendidosDash;
                aCarac[6]= ""+sumaTelefonosVendidosDash;
                sumaVentaTelefono = Float.parseFloat(aCarac[6])*600;
                aCarac[7]= ""+sumaVentaTelefono;
                sumaGastosTotales = Float.parseFloat(aCarac[8]) + gastosTotalesDash;
                aCarac[8]= ""+sumaGastosTotales;
                System.out.println("ENTRA");
                es= true;
                
            }
            csvWriter.writeRecord(aCarac, true);
//            System.out.println(csvReader.getRawRecord());
            
        }
        if (!es){
            aCarac[0] = ""+prod_botonesDash;
            aCarac[1] = ""+prod_camarasDash;
            aCarac[2] = ""+prod_pinCargaDash;
            aCarac[3] = ""+prod_pantallasDash;
            aCarac[4] = ""+ensambladoresDash;
            aCarac[5] = ""+diasDash;
            aCarac[6] = ""+telefonosVendidosDash;
            sumaVentaTelefono= telefonosVendidosDash*600;
            aCarac[7] = ""+sumaVentaTelefono;
            aCarac[8] = ""+gastosTotalesDash;
            csvWriter.writeRecord(aCarac, true);
        }
        
        csvWriter.close();
        cambiarVarDashboard();
//        System.out.println(aCaracteres);
    } catch (IOException e) {
        e.printStackTrace();
    }
    
}
    public static void leerDash(String filePath){
        
        File file = new File(filePath);
    String guardar = null;
    boolean es = false;
    
    
    DefaultCategoryDataset datos_telefonosProducidos = new DefaultCategoryDataset();
    DefaultCategoryDataset datos_telVendidos = new DefaultCategoryDataset();
    DefaultCategoryDataset datos_gastosTotales = new DefaultCategoryDataset();
    DefaultCategoryDataset datos_ganancia = new DefaultCategoryDataset();

        
        //Se crea el gráfico y se asignan algunas caracteristicas
        JFreeChart grafico_barras_Telproducidos = ChartFactory.createBarChart("Telefonos Producidos en 30 Dias","Combinaciones", "Telefonos",datos_telefonosProducidos, PlotOrientation.VERTICAL, true, true, false);
        JFreeChart grafico_barras_telVendidos = ChartFactory.createBarChart("Telefonos Vendidos 30 Dias","Combinaciones", "Dinero Telefonos $",datos_telVendidos, PlotOrientation.VERTICAL, true, true, false);
        JFreeChart grafico_barras_gastos = ChartFactory.createBarChart("Gastos de 30 Dias","Combinaciones", "Gastos $",datos_gastosTotales, PlotOrientation.VERTICAL, true, true, false);
        JFreeChart grafico_barras_ganancias = ChartFactory.createLineChart("Ganancia de 30 Dias","Combinaciones", "Ganancia $",datos_ganancia, PlotOrientation.VERTICAL, true, true, false);
        
        //Se guarda el grafico
        
    
    
    
    String[] aCaracteres = null;
    String aCarac = null;
    if (!file.exists()) {
                 System.out.println ("¡El archivo no existe!");
        return;
    }
    try {
                 // Crear objeto de lectura CSV
        CsvReader csvReader = new CsvReader(filePath);
                 // lee el encabezado del medidor
        csvReader.readHeaders();
//        System.out.println(csvReader.readRecord());
        while (csvReader.readRecord() && !es) {
                         // lee una fila de datos
            guardar = csvReader.getRawRecord();
            aCaracteres = guardar.split(",");
            aCarac = aCaracteres[0]+aCaracteres[1]+aCaracteres[2]+aCaracteres[3]+aCaracteres[4];
            
            datos_telefonosProducidos.setValue((Float.parseFloat(aCaracteres[6])/Float.parseFloat(aCaracteres[5])*30),"B-C-Pc-P-E",aCarac);
            datos_telVendidos.setValue((Float.parseFloat(aCaracteres[6])/Float.parseFloat(aCaracteres[5])*30)*600,"B-C-Pc-P-E",aCarac);
            if(Float.parseFloat(aCaracteres[5]) !=0){
                datos_gastosTotales.setValue((Float.parseFloat(aCaracteres[8])/Float.parseFloat(aCaracteres[5])*30),"B-C-Pc-P-E",aCarac);
                datos_ganancia.setValue((((Float.parseFloat(aCaracteres[7]))/Float.parseFloat(aCaracteres[5]))*30)-(Float.parseFloat(aCaracteres[8])/Float.parseFloat(aCaracteres[5]))*30,"B-C-Pc-P-E",aCarac);
                
            }
            
            System.out.println(aCaracteres[5]);
//            System.out.println(aCarac);
//            System.out.println(csvReader.getRawRecord());
            
        }
        
        BufferedImage image = grafico_barras_Telproducidos.createBufferedImage(500,300);
        BufferedImage image1 = grafico_barras_telVendidos.createBufferedImage(500,300);
        BufferedImage image2 = grafico_barras_gastos.createBufferedImage(500,300);
        BufferedImage image3 = grafico_barras_ganancias.createBufferedImage(500,300);
        
        
        
        


        ImageIcon imageIcon = new ImageIcon(image);
        ImageIcon imageIcon1 = new ImageIcon(image1);
        ImageIcon imageIcon2 = new ImageIcon(image2);
        ImageIcon imageIcon3 = new ImageIcon(image3);
        
        
        
        JFrame jFrame = new JFrame();
        JFrame jFrame1 = new JFrame();
        JFrame jFrame2 = new JFrame();
        JFrame jFrame3 = new JFrame();

        jFrame.setLayout(new FlowLayout());
        
        
        jFrame.setSize(1500, 700);
       
        JLabel jLabel = new JLabel();
        JLabel jLabe2 = new JLabel();
        JLabel jLabe3 = new JLabel();
        JLabel jLabe4 = new JLabel();

        jLabel.setIcon(imageIcon);
        jLabe2.setIcon(imageIcon1);
       
        jLabe3.setIcon(imageIcon2);
        jLabe4.setIcon(imageIcon3);
        jFrame.add(jLabel);
        jFrame.add(jLabe2);
        jFrame.add(jLabe3);
        jFrame.add(jLabe4);
        jFrame.setVisible(true);

        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        
        
//        System.out.println(aCaracteres);
    } catch (IOException e) {
        e.printStackTrace();
    }
    
    }

   
    
    public static void main(String[] args) throws CsvValidationException, IOException  {
        // TODO code application logic here
        Interfaz.main(args);
//        writeCsv("estadistica.csv");
//        read("estadistica.csv");
//          leerDash("estadistica.csv");
//          leerEscribircsv("estadistica.csv");
        
        

        
        
        
               
    }
    
}
