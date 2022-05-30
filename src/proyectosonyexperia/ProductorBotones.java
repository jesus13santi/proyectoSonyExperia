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
public class ProductorBotones extends Thread {
    public Semaphore semBotones;
    public Semaphore mutexBotones;
    public Semaphore semProdutores;
    public Boolean contratado;
    public boolean stop;
    public float sueldo;
    public float dia;
    public int dia_pago =1;
    public float pago_diario;

    public ProductorBotones (Semaphore semBotones, float sueldo, Semaphore mutexBotones, float dia){
        this.semBotones = semBotones;
        this.stop = true;    
        this.mutexBotones = mutexBotones;
        this.sueldo= sueldo;
        this.dia= dia;
        this.contratado = true;
        
    }
    
    
    
    @Override
    public void run(){
        while (this.contratado && !ProyectoSonyExperia.exit){
            
        try {
             //PAGO 
            if(Jefe.dias_transcurridos ==dia_pago){
                pago_diario += sueldo;
                ProyectoSonyExperia.mutexSalario.acquire();
                ProyectoSonyExperia.gastosSalarios += sueldo;
                Interfaz.gastosSalarios.setText(""+ProyectoSonyExperia.gastosSalarios);
                ProyectoSonyExperia.mutexSalario.release();
                dia_pago += 1;
                
                
                
            }
            
            Thread.sleep((long) (dia*1000/4));
            semBotones.acquire();
            mutexBotones.acquire();
            
            ProyectoSonyExperia.numBotones++;
            Interfaz.botonesProducidos.setText(""+ProyectoSonyExperia.numBotones);
           
         
            mutexBotones.release();
            
            
            
           
            

            
        } catch (InterruptedException ex) {
            Logger.getLogger(ProductorBotones.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
    public void despedir(){
        contratado = false;
    }
}
