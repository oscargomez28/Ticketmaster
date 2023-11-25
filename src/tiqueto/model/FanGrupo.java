package tiqueto.model;

import tiqueto.EjemploTicketMaster;

import java.util.concurrent.ThreadLocalRandom;

public class FanGrupo extends Thread {

    final WebCompraConciertos webCompra;
    int numeroFan = 0;
    private boolean salirbucle = false;


    public int EntradasFan = 0;

    //el numero de entradas que lleva el fan
    private String tabuladores = "\t\t\t\t";


    public FanGrupo(WebCompraConciertos web, int numeroFan) {
        super();
        this.numeroFan = numeroFan;
        this.webCompra = web;

    }
    /**
     * Método run , mientras el promotor no cierre la venta y el fan no llegue al numero máximo de entradas
     * Mientras pueda comprar una entrada suma EntradasFan(contador de entradas) y muestra el mensaje
     * Luego duerme entre 1 y 3 segundos
     */
    @Override
    public void run() {
        while (webCompra.finventas==false) {
            if (EntradasFan!= EjemploTicketMaster.MAX_ENTRADAS_POR_FAN) {
                mensajeFan("Voy a comprar una entrada...");
                try {
                    if (webCompra.comprarEntrada()==true) {
                        EntradasFan++;
                        //	Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 3000)); //
                        mensajeFan("Acabo de conseguir 1 entrada!! , en total llevo "+ EntradasFan+" Entradas");
                        try {
                            Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 3000));
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            System.out.println("Thread interrupted: " +e);
                        }
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }


            }
        }
    }
    /**
     * Método dimeEntradasCompradas muestra el valor de entradas comrpadas que lleva el fan (contador de entradas compradas EntradasFan)
     */
    public void dimeEntradasCompradas() {
        mensajeFan("Sólo he conseguido: " + EntradasFan);
    }

    /**
     * Método a usar para cada impresión por pantalla
     *
     * @param mensaje Mensaje que se quiere lanzar por pantalla
     */
    private void mensajeFan(String mensaje) {
        System.out.println(System.currentTimeMillis() + "|" + tabuladores + " Fan " + this.numeroFan + ": " + mensaje);
    }
}
