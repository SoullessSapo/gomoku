package domain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuicktimeTest {

    private Quicktime quicktime;

    @BeforeEach
    public void setUp() {
        quicktime = new Quicktime();
        
		Gomoku.getInstance().crearJuego(10, 10, 0,(Player) new Humano("p1","Black"),(Player) new Humano("p2","White"));
    }

    @Test
    public void iniciarShouldStartTimer() throws InterruptedException {
        quicktime.setTime(5000); // 5 seconds initial time
        Gomoku.getInstance().iniciarJuego(quicktime);
        quicktime.iniciarJuego();

        // Let the timer run for 3 seconds
        Thread.sleep(3000);

        assertTrue(quicktime.isEnEjecucion());
    }

    @Test
    public void detenerShouldStopTimer() throws InterruptedException {
        quicktime.setTime(5000);
        quicktime.iniciarJuego();

        // Let the timer run for 2 seconds
        Thread.sleep(2000);

        quicktime.detener();

        assertFalse(quicktime.isEnEjecucion());
        assertTrue(quicktime.getTiempoRestantePlayer1().getTiempoRestante() < 5000);
    }

    @Test
    public void reiniciarShouldRestartTimer() throws InterruptedException {
        quicktime.setTime(5000);
        quicktime.iniciarJuego();

        // Let the timer run for 2 seconds
        Thread.sleep(2000);

        quicktime.reiniciar();

        assertTrue(quicktime.isEnEjecucion());
        assertEquals(2500, quicktime.getTiempoRestantePlayer1().getTiempoRestante());
    }
}


