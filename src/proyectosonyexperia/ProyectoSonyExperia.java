/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectosonyexperia;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

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
   public static Semaphore mutexPantalla = new Semaphore(1);
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
   
   public static int numSonyExperiaVendidos = 0;
   public static float gastosSalarios;       
   
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
    
    
    public static void ensamblar(){
        productorPantalla1 = new ProductorPantalla(semPantallas, 10,mutexPantalla, dia );
        productorBotones1 = new ProductorBotones(semBotones, 10,mutexPantalla, dia );
        productorPinCarga1 = new ProductorPinCarga(semPinCarga, 10,mutexPantalla, dia );
        productorCamara1 = new ProductorCamara(semCamaras, 10,mutexPantalla, dia );
        Ensamblador1 = new Ensamblador(semPinCarga, semPantallas, semCamaras, semBotones, semSonyExperia,  10, mutexPantalla, dia);
        Jefe = new Jefe(semJefeGerente, 30, 10, dia, semJefeGerente);
        Gerente = new Gerente(semJefeGerente, 30, 10, dia, semJefeGerente);
        
        Interfaz.botonesProducidos.setText(""+numBotones);
        Interfaz.pantallasProducidas.setText(""+numPantallas);
        Interfaz.PinCargaProducidos.setText(""+numPinCarga);
        Interfaz.CamarasProducidas.setText(""+numCamara);
        productorPantalla1.start();
        productorBotones1.start();
        productorPinCarga1.start();
        productorCamara1.start();
        Ensamblador1.start();
        Jefe.start();
        Gerente.start();
    
    }
    
    
    public static void ensamblar2(){
        
        numBotones = 0;
        numPinCarga = 0;
        numPantallas = 0;
        numCamara = 0;
        
        numSonyExperia = 0;
        numSonyExperiaVendidos = 0;
        
        semPantallas = new Semaphore(max_pantallas);  
        semBotones = new Semaphore(max_botones);  
        semPinCarga = new Semaphore(max_pinCarga);  
        semCamaras = new Semaphore(max_camaras); 

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
            prod_botones[i] = new ProductorBotones(semBotones, sueldoProd_botones,mutexPantalla, dia );
            prod_botones[i].start();
        }
        
        for( int i = 0; i<numProd_pantallas; i++) {
            prod_pantalla[i] = new ProductorPantalla(semPantallas, sueldoProd_pantalla,mutexPantalla, dia );
            prod_pantalla[i].start();
        }
        
        for( int i = 0; i<numProd_pinCarga; i++) {
            prod_pinCarga[i] = new ProductorPinCarga(semPinCarga, sueldoProd_pinCarga,mutexPantalla, dia );
            prod_pinCarga[i].start();
        }
        
        for( int i = 0; i<numProd_camaras; i++) {
            prod_camara[i] = new ProductorCamara(semCamaras, sueldoProd_camaras,mutexPantalla, dia );
            prod_camara[i].start();
        }
        

        for(int i = 0; i<num_ensambladores; i++){
            ensambladores[i] = new Ensamblador(semPinCarga, semPantallas, semCamaras, semBotones, semSonyExperia,  sueldo_ensamblador, mutexPantalla, dia);
            ensambladores[i].start();
        }
        
        Interfaz.numProductoresPinCarga.setText(""+numProd_pinCarga);
        Interfaz.numProductoresPantallas.setText(""+numProd_pantallas);
        Interfaz.numProductoresCamaras.setText(""+numProd_camaras);
        Interfaz.numProductoresBotones.setText(""+numProd_botones);
        
        Interfaz.numEnsambladores.setText(""+num_ensambladores);
        
        
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
            
            
            
            

            return true;
//            return !(  max_trabajadores < 1 || numProd_pantallas < 1 || numProd_botones < 1 || numProd_pinCarga < 1 ||numProd_camaras < 1 || num_ensambladores < 1 || dia < 1 || diasDespacho < 1 );

            
        } catch (Exception e) {
            return false;
        }
      
        
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        Interfaz.main(args);
        
        
        
        
        
    }
    
}
