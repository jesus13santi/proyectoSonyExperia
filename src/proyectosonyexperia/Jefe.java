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

/**
 *
 * @author jesus13santi
 */
public class Jefe extends Thread {
    public Semaphore semJefeGerente;
    public Semaphore semJefe;
    public int diasParaDespachar;
    public int contador;
    public int dias_transcurridos;
    public String estado;
    public float sueldo;
    public float dia;
    public int horas_restantes;
    public boolean revisar;
    public boolean contratado;
    
    
    public float horas20;
    public float quincemin;
    public int dia_pago =0;
    public float pago_diario;

    public Jefe (Semaphore semJefeGerente, int diasParaDespachar, float sueldo, float dia, Semaphore semJefe){
        this.semJefeGerente = semJefeGerente;   
        this.contador = diasParaDespachar;
        this.dias_transcurridos = 0;
        this.sueldo= sueldo;
        this.dia= dia;
        this.estado= "jugando";
        this.semJefe = semJefe;
        this.revisar = true;
        this.contratado = true;
        this.horas_restantes = 1;
        this.quincemin = ProyectoSonyExperia.dia_minuto*15;
        
    }
    
    
    
    @Override
    public void run(){
        while (!ProyectoSonyExperia.exit){
        try {        
            
            
            semJefeGerente.acquire();
            estado = "actualizando";     
            Interfaz.JefeEstado.setText(estado);
            Thread.sleep((long) (dia*1000/24));
            //Pago
            if(dias_transcurridos ==dia_pago){
                pago_diario += sueldo;
                Interfaz.SalarioJefe.setText("$"+pago_diario);
                ProyectoSonyExperia.mutexSalario.acquire();
                ProyectoSonyExperia.gastosSalarios += sueldo;
                Interfaz.gastosSalarios.setText(""+ProyectoSonyExperia.gastosSalarios);
                ProyectoSonyExperia.mutexSalario.release();
                dia_pago += 1;
                
                
            }
            horas20=23;
            contador --;
        
            
            
            dias_transcurridos++;
            Interfaz.DiasTranscurridos.setText(""+dias_transcurridos);
            Interfaz.DiasRestantes.setText(""+contador);
//            System.out.println("dias Transcurridos " + dias_transcurridos + "###############################");
            Gerente.revisar = true;
            semJefeGerente.release();
            
            while(horas20 > 0 && !ProyectoSonyExperia.exit){
                estado= "jugando";
                Interfaz.JefeEstado.setText(estado);
               
                
                
                Thread.sleep((long) (dia*1000/96));
                horas20-=0.25;

                estado = "revisando";
                
                
                Interfaz.JefeEstado.setText(estado);
                
                Thread.sleep((long) (dia*1000/96));
                horas20-=0.25;
//                System.out.println(horas20);
//                Thread.sleep((long) (dia*1000/96));
//                dia2--;
                
                
            }
       
          
            
            
            
            
            
            
            
            
            
            
            ///////FORMA DE DOS ESTADOS
//             semJefeGerente.acquire();
//                horas_restantes =1 ;
//                estado = "Actualizando";
//                Interfaz.JefeEstado.setText(estado);
//                Thread.sleep((long) (1000*(horas_restantes/24)));
//                dias_transcurridos++;
//                contador--;
//                Interfaz.DiasTranscurridos.setText(""+dias_transcurridos);
//                Interfaz.DiasRestantes.setText(""+contador);
//                semJefeGerente.release();
//                
//                
//                
//                estado = "Trabajando";
//                Interfaz.JefeEstado.setText(estado);
//                Thread.sleep((long) (1000*dia*23/24));
                
                
       //////////////FORMA 1 DE HACERLO     
//            semJefeGerente.acquire();
//            estado = "actualizando";
//            
//            revisar =true;
//            
//            
//            System.out.println(estado);
//            revisar = false;
//            Interfaz.JefeEstado.setText(estado);
//            Thread.sleep((long) (dia*1000/24));
//            
////            dia2 --;
//            contador --; 
//            dias_transcurridos++;
//            Interfaz.DiasTranscurridos.setText(""+dias_transcurridos);
//            Interfaz.DiasRestantes.setText(""+contador);
//            System.out.println("dias Transcurridos " + dias_transcurridos + "###############################");
//            Gerente.revisar = true;
//            semJefeGerente.release();
//            
//            while(!revisar){
//                estado= "jugando";
//                Interfaz.JefeEstado.setText(estado);
//                System.out.println(estado);
//                Thread.sleep((long) (ProyectoSonyExperia.dia_minuto*15));// 15 MIN
////                dia2--;
//            
//         
//                estado = "revisando";
//                System.out.println(estado);
//                Interfaz.JefeEstado.setText(estado);
//                Thread.sleep((long) (ProyectoSonyExperia.dia_minuto*15));
////                Thread.sleep((long) (dia*1000/96));
////                dia2--;
//                
//                
//            }
//       
////            while(dia2 >0){
////                estado= "jugando";
////                System.out.println(estado);
////                Thread.sleep((long) (dia*1000/12));// 15 MIN
////                dia2--;
////            
////         
////                estado = "revisando";
////                System.out.println(estado);
////                Thread.sleep((long) (dia*1000/12));
////                dia2--;
////                
////                
////            }
            
            
            
            
            
            
           
               
            
               
//            if (contador == 0){
//                break;
//            }
            
           
            
           
            

            
        } catch (InterruptedException ex) {
            Logger.getLogger(Jefe.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
}
