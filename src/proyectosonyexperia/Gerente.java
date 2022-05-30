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
    public int horas_restantes;
    public int aleatorio_horas;
    public float aleatorio_minutos;
    public float maximo_numero_aleatorio = 18;
    public float minimo_numero_aleatorio = 12;
    public boolean revisar;
    public boolean contratado;
    public int jefeEncontradoJugando=0;
    public int horas_aux; 
    
    
    
    public int aleatorio2;
    public float dias2;
    public float aleatorio_minutos2;
    public float aux_minutos;
    public float aux_descansando;
    public int dia_pago=1;
    public float pago_diario;
    
    
    
    public Gerente (Semaphore semJefeGerente, int diasParaDespachar, float sueldo, float dia, Semaphore semJefe){
        this.semJefeGerente = semJefeGerente;   
        this.contador = diasParaDespachar;
        this.dias_transcurridos = 0;
        this.sueldo= sueldo;
        this.dia= dia;
        this.estado= "REVISANDO";
        this.semJefe = semJefe;
        this.contratado = true;
        
        this.dias2 = 0;
       
        
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
            dias2=0;
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
             
            
            
            
            while(dias2 < aleatorio2  && !ProyectoSonyExperia.exit){
                aleatorio_minutos2=(int)(Math.random()*(90-30+1)+30);
                aux_minutos = aleatorio_minutos2/60;
                

                
                dias2+=0.25;
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

                 
                 
                dias2+=aux_minutos;
                estado= "Descansando";
                Interfaz.GerenteEstado.setText(estado);
                aux_descansando=((90-aleatorio_minutos2)/60); 
      
                Thread.sleep((long) (dia*1000/(24/aux_descansando)));     
//                Thread.sleep((long) (dia*1000*(90-aleatorio_minutos2)/60));     
                dias2+=aux_descansando;
                
//                System.out.println(dias2);
                
                
            }
            
            
            
            
            
            
            
            
            
            
            
            
           /////////FORMA 1 DE HACERLO
//            semJefeGerente.acquire();
//            estado = "REVISANDO";
//
//            
////            revisar= true;
//            aleatorio_horas = (int)(Math.random()*(maximo_numero_aleatorio-minimo_numero_aleatorio+1)+minimo_numero_aleatorio);
//            
//
//            System.out.println(estado);
//            
//            revisar = false;
//            Interfaz.GerenteEstado.setText(estado);
//            Thread.sleep((long) (dia*1000*aleatorio_horas/24));
//            
//            Jefe.revisar = true;
//            
//            semJefeGerente.release();
//            
//            
//        
//            while(aleatorio_horas < 24){
//                
//                estado = "VIGILA AL JEFE";
//           
//                System.out.println(estado);
//                Interfaz.GerenteEstado.setText(estado);
//                if(Jefe.estado == "jugando" && Gerente.estado == "VIGILA AL JEFE"){
//                    jefeEncontradoJugando++;
//                    Interfaz.EncontroJefe.setText(""+jefeEncontradoJugando);
//                    System.out.println("LO ENCONTRO #########################################");
//                }
//                aleatorio_minutos= (int)(Math.random()*(90-30+1)+30);
//                Thread.sleep((long) (ProyectoSonyExperia.dia_minuto*(aleatorio_minutos)));
////                Thread.sleep((long) (dia*1000*((int)(Math.random()*(3-1+1)+1)/24)));
//                aleatorio_horas += aleatorio_minutos/60;
//                estado = "TRABAJANDO";
//                Interfaz.GerenteEstado.setText(estado);
//                System.out.println(estado);
////          
////                Thread.sleep((long) (dia*1000*((int)(Math.random()*(3-1+1)+1)/24)));
//            }
//            
//            
////           
//            
////            estado = "revisando";
////            System.out.println(estado);
////            Thread.sleep((long) (dia*1000/96));
            
            
            
            
            
           
               
            
               
//            if (contador == 0){
//                break;
//            }
            
           
            ///////////////FORMA DE DOS ESTADOS
//        aleatorio_horas = (int)(Math.random()*(maximo_numero_aleatorio-minimo_numero_aleatorio+1)+minimo_numero_aleatorio); 
////        semJefeGerente.acquire();
//                estado = "Revisando";
//                Interfaz.GerenteEstado.setText(estado);
//                Thread.sleep((long) (1000*dia*aleatorio_horas/24));
//               
////                semJefeGerente.release();
//                
//                horas_aux =24-aleatorio_horas;
//                estado = "Trabajando";
//                Interfaz.GerenteEstado.setText(estado);
//                Thread.sleep((long) (1000*horas_aux/24)); 
                
                

            
        } catch (InterruptedException ex) {
            Logger.getLogger(Gerente.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
}
