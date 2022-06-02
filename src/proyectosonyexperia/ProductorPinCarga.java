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
public class ProductorPinCarga extends Thread {
    public Semaphore semPinCarga;
    public Semaphore mutexPinCarga;
    public Semaphore semProdutores;
    public Boolean contratado;
    public boolean stop;
    public float sueldo;
    public float dia;
    public int dia_pago=1;
    public float pago_diario;

    public ProductorPinCarga (Semaphore semPinCarga, float sueldo, Semaphore mutexPinCarga, float dia){
        this.semPinCarga = semPinCarga;
        this.stop = true;    
        this.mutexPinCarga = mutexPinCarga;
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
                
                //DashBoard
                ProyectoSonyExperia.gastosTotalesDash += sueldo;
                
                ProyectoSonyExperia.mutexSalario.release();
                dia_pago += 1;
                
                
            }
            
            Thread.sleep((long) (dia*1000*2));
            semPinCarga.acquire();
            mutexPinCarga.acquire();
            
            ProyectoSonyExperia.numPinCarga++;
            Interfaz.PinCargaProducidos.setText(""+ProyectoSonyExperia.numPinCarga);
         
            mutexPinCarga.release();
            

           
            

            
        } catch (InterruptedException ex) {
            Logger.getLogger(ProductorPinCarga.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
    public void despedir(){
        contratado = false;
    }
}
