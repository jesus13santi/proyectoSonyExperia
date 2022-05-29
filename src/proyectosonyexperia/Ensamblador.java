/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectosonyexperia;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import static proyectosonyexperia.ProyectoSonyExperia.Jefe;

/**
 *
 * @author jesus13santi
 */
public class Ensamblador extends Thread {
    public Semaphore semPinCarga;
    public Semaphore semPantalla;
    public Semaphore semCamaras;
    public Semaphore semBotones;
    public Semaphore semSonyExperia;
    public Semaphore mutexEnsamblador;
    public Boolean contratado;
    public float sueldo;
    public float dia;
    public int dia_pago =1;
    public float pago_diario;

    public Ensamblador (Semaphore semPinCarga, Semaphore semPantalla, Semaphore semCamaras, Semaphore semBotones, Semaphore semSonyExperia, float sueldo, Semaphore mutexEnsamblador, float dia){
        this.semPinCarga = semPinCarga;   
        this.semPantalla = semPantalla;   
        this.semCamaras = semCamaras;   
        this.semBotones = semBotones;   
        this.semSonyExperia = semSonyExperia;   
        this.mutexEnsamblador = mutexEnsamblador;
        this.sueldo= sueldo;
        this.dia= dia;
        this.contratado = true;
        
        
    }
    
    
    public void stopToggle(){
        this.contratado = !this.contratado;
    }
    @Override
    public void run(){
        while (this.contratado && !ProyectoSonyExperia.exit){
        try {
            
            if(Jefe.dias_transcurridos ==dia_pago){
                    pago_diario = sueldo*dia_pago;
                   
                    ProyectoSonyExperia.gastosSalarios += sueldo;
                    Interfaz.gastosSalarios.setText(""+ProyectoSonyExperia.gastosSalarios);
                   
                    
                    dia_pago += 1;
                
                
            }
            
            if(ProyectoSonyExperia.numPinCarga>=1 && ProyectoSonyExperia.numBotones >=2 && ProyectoSonyExperia.numCamara >=2 && ProyectoSonyExperia.numPantallas >=1 ){
                
                mutexEnsamblador.acquire();
                semPinCarga.release();
                ProyectoSonyExperia.numPinCarga--;
            
                semBotones.release(2);
                ProyectoSonyExperia.numBotones-=2;
                
                semCamaras.release(2);
                ProyectoSonyExperia.numCamara-=2;
           
                semPantalla.release();
                ProyectoSonyExperia.numPantallas--;
                mutexEnsamblador.release();
                Thread.sleep((long) (dia*1000*2));
                
                
                
                semSonyExperia.acquire();
            
                ProyectoSonyExperia.numSonyExperia++;
                Interfaz.TelefonosProducidos.setText(""+ProyectoSonyExperia.numSonyExperia);
                
            
                semSonyExperia.release();
                
               
            }
            
           
            
           
            

            
        } catch (InterruptedException ex) {
            Logger.getLogger(Ensamblador.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
    public void despedir(){
        contratado = false;
    }
}
