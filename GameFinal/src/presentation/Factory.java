package presentation;
import domain.*;
public class Factory {
	
	public static Player getPlayer(String player) {
		if(player.equalsIgnoreCase("Humano")) {
			return new Humano("","");
		}else if (player.equalsIgnoreCase("Agresiva")) {
			return (Maquina) new Agresiva("","");
		}else if (player.equalsIgnoreCase("Miedosa")) {
			return (Maquina) new Miedosa("","");
		}else {
			return (Maquina) new  Experta("","");
		}
	}
	public static Juego getJuego(String juego){
		if(juego == null) {
			return null;
		}
		if(juego.equalsIgnoreCase("Normal")) {
			return new ModoNormal();
		}else if (juego.equalsIgnoreCase("QuickTime")) {
			return new Quicktime();
		}else {
			return new ModoPiedrasLimitadas();
		}
	}

}
