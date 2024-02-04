package domain;

import java.util.ArrayList;
import java.util.HashSet;

public class ArrayGomoku<E> extends ArrayList<E>  {
	private int numNormales;
	private int numPesadas;
	private int numTemporales;
	/**
     * Adiciona el elemento a la lista y va contando la cantidad de fichas
     */
	@Override
	public boolean add(E element) {
		boolean added = super.add(element);
		if(element.getClass().getName().equals("domain.NormalFicha")) {
			numNormales++;
		}else if(element.getClass().getName().equals("domain.Pesada")) {
			numPesadas++;
		}else {
			numTemporales++;
		}
		return added;
	}
	/**
     * elimina el elemento a la lista y va restando a la cantidad de fichas
     */
	@Override
	public E remove(int index) {
		E removed= super.remove(index);
		if(removed.getClass().getName().equals("domain.NormalFicha")) {
			numNormales--;
		}else if(removed.getClass().getName().equals("domain.Pesada")) {
			numPesadas--;
		}else {
			numTemporales--;
		}
		return removed;
	}
	/**
     *obtener numero de ficha normales
     */
	public int getContadorNormales() {
		return numNormales;
	}
	/**
     *obtener numero de fichas pesadas
     */
	public int getContadorPesadas() {
		return numPesadas;
	}
	/**
     *obtener numero de fichas temporales
     */
	public int getContadorTemporales() {
		return numTemporales;
	}

}
