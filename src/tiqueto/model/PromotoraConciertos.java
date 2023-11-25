package tiqueto.model;

import tiqueto.EjemploTicketMaster;

import java.util.concurrent.ThreadLocalRandom;

public class PromotoraConciertos extends Thread {

    final WebCompraConciertos webCompra;


    public PromotoraConciertos(WebCompraConciertos webCompra) {
        super();
        this.webCompra = webCompra;


    }


    /**
     * Método run de Promotor se ejecuta mientras no hay entradas y las entradas restantes por vender no son igual al TOTAL_ENTRADA
     * muestra un mensaje de reposicion procede a reponer , una vez hecho esto duerme entre 3 y 8 segundos
     *cuando se venden todas las entradas totales sale del bucle y cierra la venta
     *
     */
    @Override
    public void run() {
        while (webCompra.hayEntradas()==false && webCompra.TotalEntradasRestantes() != 0) {
                    try {
                        mensajePromotor("Actualmente no hay entradas , procedo a reponer");
                        webCompra.reponerEntradas(EjemploTicketMaster.REPOSICION_ENTRADAS);
                        //dormir
                        Thread.sleep(ThreadLocalRandom.current().nextInt(3000, 8000));

                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }


        }
        webCompra.cerrarVenta();


    }
    /**
     * Método a usar para cada impresión por pantalla
     * @param mensaje Mensaje que se quiere lanzar por pantalla
     */
    private void mensajePromotor(String mensaje) {
        System.out.println(System.currentTimeMillis() + "| Promotora: " + mensaje);

    }
}

