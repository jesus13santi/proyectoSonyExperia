/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectosonyexperia;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import static proyectosonyexperia.ProyectoSonyExperia.Gerente;
import static proyectosonyexperia.ProyectoSonyExperia.Jefe;

/**
 *
 * @author jesus13santi
 */
public class Gerente extends Thread {
    public Semaphore semJefeGerente;
    public Semaphore semJefe;
    public int diasParaDespachar;
    public int contador;
    public int dias_transcurridos;
    public String estado;
    public float sueldo;
    public float dia;
    public float maximo_numero_aleatorio = 18;
    public float minimo_numero_aleatorio = 12;
    
    public boolean contratado;
    public int jefeEncontradoJugando=0;
  
    
    
    
    public int aleatorio2;
    public float dias_revisar_descansar;
    public float aleatorio_minutos2;
    public float aux_minutos;
    public float aux_descansando;
    public int dia_pago=1;
    public float pago_diario;
    public float costo_telefono = 600;
    
    
    public Gerente (Semaphore semJefeGerente, int diasParaDespachar, float sueldo, float dia, Semaphore semJefe){
        this.semJefeGerente = semJefeGerente;   
        this.contador = diasParaDespachar;
        this.dias_transcurridos = 0;
        this.sueldo= sueldo;
        this.dia= dia;
        this.estado= "REVISANDO";
        this.semJefe = semJefe;
        this.contratado = true;
        
        this.dias_revisar_descansar = 0;
       
        
    }
   
    
    
    @Override
    public void run(){
        while (!ProyectoSonyExperia.exit){
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
            
            semJefeGerente.acquire();
            aleatorio2 = (int)(Math.random()*(maximo_numero_aleatorio-minimo_numero_aleatorio+1)+minimo_numero_aleatorio);
            dias_revisar_descansar=0;
            estado="revisando";
            if(Jefe.contador ==0){
                Jefe.contador =ProyectoSonyExperia.diasDespacho;
                ProyectoSonyExperia.numSonyExperiaVendidos += ProyectoSonyExperia.numSonyExperia;
                Interfaz.numTelefonosVendidos.setText(""+ProyectoSonyExperia.numSonyExperiaVendidos);
                ProyectoSonyExperia.vender();
                Interfaz.TelefonosProducidos.setText(""+ProyectoSonyExperia.numSonyExperia);
                
            }
            Interfaz.GerenteEstado.setText(estado);
            Thread.sleep((long) (dia*1000/(24/(24-aleatorio2))));
            
//            Thread.sleep((long) (dia*1000/(24/aleatorio2)));
            semJefeGerente.release();
             
            
            
            
            while(dias_revisar_descansar < aleatorio2  && !ProyectoSonyExperia.exit){
                aleatorio_minutos2=(int)(Math.random()*(90-30+1)+30);
                aux_minutos = aleatorio_minutos2/60;
                

                
                dias_revisar_descansar+=0.25;
                estado= "REVISANDO JEFE";
                Interfaz.GerenteEstado.setText(estado);
                if(Gerente.estado == "REVISANDO JEFE" && Jefe.estado == "jugando"){
                    jefeEncontradoJugando+=1;
                    Interfaz.EncontroJefe.setText(""+jefeEncontradoJugando);
                    
                    Jefe.pago_diario-= 2;
                    Interfaz.SalarioJefe.setText("$"+Jefe.pago_diario);
                    ProyectoSonyExperia.mutexSalario.acquire();
                    ProyectoSonyExperia.gastosSalarios -= 2;
                    Interfaz.gastosSalarios.setText(""+ProyectoSonyExperia.gastosSalarios);
                    ProyectoSonyExperia.mutexSalario.release();
                 
                    
                    
                }
                Thread.sleep((long) (dia*1000/(24/aux_minutos)));

                 
                 
                dias_revisar_descansar+=aux_minutos;
                estado= "Descansando";
                Interfaz.GerenteEstado.setText(estado);
                aux_descansando=((90-aleatorio_minutos2)/60); 
      
                Thread.sleep((long) (dia*1000/(24/aux_descansando)));     
//                Thread.sleep((long) (dia*1000*(90-aleatorio_minutos2)/60));     
                dias_revisar_descansar+=aux_descansando;
                
//                System.out.println(dias2);
                
                
            }
            
            

                
                

            
        } catch (InterruptedException ex) {
            Logger.getLogger(Gerente.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
}
