package tiqueto.model;

import tiqueto.EjemploTicketMaster;
import tiqueto.IOperacionesWeb;

import java.util.concurrent.ThreadLocalRandom;

public class WebCompraConciertos implements IOperacionesWeb{

	private int EntradasAVender=0 ;

	private int EntradasVendidas=0;

public boolean finventas=false;
	public WebCompraConciertos() {
		super();
	}


	public synchronized boolean comprarEntrada() throws InterruptedException {
		if(hayEntradas()) {
			EntradasAVender--;
			EntradasVendidas++;

			mensajeWeb("Entrada comprada, Actualmente quedan "+ entradasRestantes()+" Entradas ");
			return true;
		}else {
			mensajeWeb("No hay entradas disponibles , voy a esperar");
			wait();
			return false;
		}
	}
	@Override
	public synchronized int reponerEntradas(int numeroEntradas) throws InterruptedException {
		notifyAll();

		if (EntradasVendidas+EntradasAVender+EjemploTicketMaster.REPOSICION_ENTRADAS>EjemploTicketMaster.TOTAL_ENTRADAS) {
			return  EntradasAVender += EjemploTicketMaster.TOTAL_ENTRADAS-(EntradasAVender+EntradasVendidas);
		}else{
			EntradasAVender+=numeroEntradas;
			return EntradasAVender;
		}

    }
	@Override
	public synchronized  void cerrarVenta() {
		mensajeWeb("La venta se ha cerrado ");
		finventas=true;
		notifyAll();
	}

	@Override
	public synchronized boolean hayEntradas( ) {
		// TODO Auto-generated method stub
		if(entradasRestantes()>0){
			return true;
		}
		return false;
		}
	@Override
	public synchronized int entradasRestantes() {
		// TODO Auto-generated method stub
		return EntradasAVender;
	}
public synchronized int TotalEntradasRestantes(){
		return EjemploTicketMaster.TOTAL_ENTRADAS-EntradasVendidas-entradasRestantes();
}


	/**
	 * Método a usar para cada impresión por pantalla
	 * @param mensaje Mensaje que se quiere lanzar por pantalla
	 */
	private void mensajeWeb(String mensaje) {
		System.out.println(System.currentTimeMillis() + "| WebCompra: " + mensaje);
		
	}

}
