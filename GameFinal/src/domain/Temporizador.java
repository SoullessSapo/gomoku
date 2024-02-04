package domain;

public class Temporizador {
    private long tiempoInicial;
    private long tiempoRestante;
    private boolean enEjecucion;

    public Temporizador(long tiempo) {
    	this.tiempoInicial = tiempo;
        this.tiempoRestante = tiempo;
        this.enEjecucion = false;
    }


    public void iniciar() {
        if (!enEjecucion) {
            enEjecucion = true;
            Thread hilo = new Thread(() -> {
                while (tiempoRestante > 0 && enEjecucion) {
                    try {
                        Thread.sleep(1000);
                        tiempoRestante -= 1000;
                        System.out.println("Tiempo restante: " + tiempoRestante / 1000 + " segundos");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if (enEjecucion) {
                    enEjecucion = false;
                    System.out.println("Tiempo agotado");
                }
            });

            hilo.start();
        }
    }

    public void reiniciar() {
        detener();
        tiempoRestante = tiempoInicial;
        iniciar();
    }

    public void detener() {
        enEjecucion = false;
    }

    public long getTiempoRestante() {
        return tiempoRestante;
    }

    public boolean isEnEjecucion() {
        return enEjecucion;
    }
}
