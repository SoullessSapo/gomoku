package domain;

public interface Observable {
	 public void delObserver(Observador o);
	 public void addObserver(Observador o);
	 public void notifyObservers();
	

}
