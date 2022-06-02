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
    public Semaphore mutexPantallas;
    public Semaphore mutexBotones;
    public Semaphore mutexCamaras;
    public Semaphore mutexPinCarga;
    public Boolean contratado;
    public float sueldo;
    public float dia;
    public int dia_pago =1;
    public float pago_diario;

    public Ensamblador (Semaphore semPinCarga, Semaphore semPantalla, Semaphore semCamaras, Semaphore semBotones, Semaphore semSonyExperia, float sueldo, Semaphore mutexCamaras, Semaphore mutexBotones, Semaphore mutexPantallas, Semaphore mutexPinCarga,float dia){
        this.semPinCarga = semPinCarga;   
        this.semPantalla = semPantalla;   
        this.semCamaras = semCamaras;   
        this.semBotones = semBotones;   
        this.semSonyExperia = semSonyExperia;   
        this.mutexPantallas = mutexPantallas;
        this.mutexBotones = mutexBotones;
        this.mutexCamaras = mutexCamaras;
        this.mutexPinCarga = mutexPinCarga;
        this.sueldo= sueldo;
        this.dia= dia;
        this.contratado = true;
        
        
    }
    
 
    @Override
    public void run(){
        while (this.contratado && !ProyectoSonyExperia.exit){
        try {
            
            if(Jefe.dias_transcurridos ==dia_pago){
                    pago_diario += sueldo;
                    ProyectoSonyExperia.mutexSalario.acquire();
                    ProyectoSonyExperia.gastosSalarios += sueldo;
                    Interfaz.gastosSalarios.setText(""+ProyectoSonyExperia.gastosSalarios);
                    
                    //DashBoard
                    ProyectoSonyExperia.gastosTotalesDash += sueldo;
                    
                    ProyectoSonyExperia.mutexSalario.release();
                    
                    
                   
                    
                    dia_pago += 1;
                
                
            }
            mutexPinCarga.acquire();
            mutexBotones.acquire();
            mutexCamaras.acquire();
            mutexPantallas.acquire();
            
            if(ProyectoSonyExperia.numPinCarga>=1 && ProyectoSonyExperia.numBotones >=2 && ProyectoSonyExperia.numCamara >=2 && ProyectoSonyExperia.numPantallas >=1 ){
                
                
                semPinCarga.release();
                ProyectoSonyExperia.numPinCarga--;
                mutexPinCarga.release();
                
                
                semBotones.release(2);
                ProyectoSonyExperia.numBotones-=2;
                mutexBotones.release();
                
                
                
                semCamaras.release(2);
                ProyectoSonyExperia.numCamara-=2;
                mutexCamaras.release();
           
                
                semPantalla.release();
                ProyectoSonyExperia.numPantallas--;
                mutexPantallas.release();
                
                Thread.sleep((long) (dia*1000*2));
                
                
                
                semSonyExperia.acquire();
            
                ProyectoSonyExperia.numSonyExperia++;
                Interfaz.TelefonosProducidos.setText(""+ProyectoSonyExperia.numSonyExperia);
                //DashBoard
                ProyectoSonyExperia.telefonosVendidosDash++;
            
                semSonyExperia.release();
                
               
            }else{
                mutexCamaras.release();
                mutexBotones.release();
                mutexPantallas.release();
                mutexPinCarga.release();
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
