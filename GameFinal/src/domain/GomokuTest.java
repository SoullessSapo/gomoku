/**
 * 
 */
package domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test;

class GomokuTest {
	private Gomoku g;
	@BeforeEach
    public void setUp()
    {
		g = Gomoku.getInstance();
		g.crearJuego(10, 10, 0,(Player) new Humano("p1","Black"),(Player) new Humano("p2","White"));
    }

	@Test
    public void shouldPutTokenOnBoard(){
		Juego normal = new ModoNormal();
		try {
			normal.configurarJuego(0, 100);
			g.iniciarJuego(normal);
		} catch (GomokuException e) {
		}
		
	    int[] posicion = new int[]{0,0};
	    g.colocarFicha(new NormalFicha(g.getPlayer().getColor(),g.getPlayer()), posicion);
	    Ficha[][] tablero = g.getTablero();
	    assertNotNull(tablero[0][0]);
    }
    @Test
    public void shouldNotPutTokenOnBoard(){
	    	Juego normal = new ModoNormal();
			try {
				normal.configurarJuego(0, 100);
				g.iniciarJuego(normal);
			} catch (GomokuException e) {
			}
            int[] posicion = new int[]{11,0};
            g.colocarFicha(new NormalFicha(g.getPlayer().getColor(),g.getPlayer()), posicion);
            assertEquals(null,Gomoku.getInstance().getTablero()[0][0]);
    	
    	
    }
    @Test
    public void shouldVerify5EnRaja(){
	    	Juego normal = new ModoNormal();
			try {
				normal.configurarJuego(0, 100);
				g.iniciarJuego(normal);
			} catch (GomokuException e) {
			}
            int[] posicion1 = new int[]{0,0};
            int[] posicion2 = new int[]{0,1};
            int[] posicion3 = new int[]{0,2};
            int[] posicion4 = new int[]{0,3};
            int[] posicion5 = new int[]{0,4};
            ArrayList<int[]> posiciones = new ArrayList<>();
            posiciones.add(posicion1);
            posiciones.add(posicion2);
            posiciones.add(posicion3);
            posiciones.add(posicion4);
            posiciones.add(posicion5);
            for(int i = 0;i<posiciones.size();i++) {
            	g.colocarFicha(new NormalFicha(g.getPlayer().getColor(),g.getPlayer()), posiciones.get(i));
            }
            boolean gano = normal.verificar5EnRaja(posiciones,g.getPlayer().getColor(),g.getTablero());
            assertEquals(true,gano);
    	
    	
    }
    @Test
    public void shouldCreateNormalCasilla(){
        int posx = 9;
        int posy = 9;
        g.asignarCasilla("domain.NormalCasilla", posx, posy);
        assertEquals(g.getCasilla(posx, posy).getClass().getName(),"domain.NormalCasilla");
    }
    @Test
    public void shouldCreateTeleportCasilla(){
        int posx = 5;
        int posy = 5;
        g.asignarCasilla("domain.Teleport", posx, posy);
        assertEquals(g.getCasilla(posx, posy).getClass().getName(),"domain.Teleport");
    }
    @Test
    public void shouldCreateMineCasilla(){
        int posx = 3;
        int posy = 5;
        g.asignarCasilla("domain.Mine", posx, posy);
        assertEquals(g.getCasilla(posx, posy).getClass().getName(),"domain.Mine");
    }
    @Test
    public void shouldCreateGoldenCasilla(){
        int posx = 2;
        int posy = 2;
        g.asignarCasilla("domain.Golden", posx, posy);
        assertEquals(g.getCasilla(posx, posy).getClass().getName(),"domain.Golden");
    }
    @Test
    public void shouldNotCreateUnkownCasilla(){
        int posx = 7;
        int posy = 7;
        g.asignarCasilla("domain.Nueva", posx, posy);
        assertEquals("domain.NormalCasilla",g.getCasilla(posx, posy).getClass().getName()); 
    }
    @Test
    public void shouldDetonateToken(){
        int posx = 1;
        int posy = 8;
        g.asignarCasilla("domain.Mine", posx, posy);
        g.asignarCasilla("domain.NormalCasilla", 0, 8);
        g.colocarFicha(new NormalFicha(g.getPlayer().getColor(),g.getPlayer()),new int[] {0,8});
        g.colocarFicha(new NormalFicha(g.getPlayer().getColor(),g.getPlayer()),new int[] {posx,posy});
        assertNull(g.getCasilla(0, 8).getFicha()); 
    }    
}
