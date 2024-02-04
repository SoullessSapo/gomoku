package presentation;
import domain.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.border.EmptyBorder;
import javax.swing.border.*;

public class GomokuGUI extends JFrame implements Observador {
    private JMenuBar menu;
    private JMenu menu1;
    private JMenuItem menu2, menu3, menu4, menu5, menu6;
    private JPanel mainPanel;
    private int filas;
    private int cols;
    private JPanel tableroPanel;
    private JLabel tiempoEtiqueta;
    private JButton cambiarColorButton;
    private JButton[][] botones;
    private int[] clickActual=null;
    private HashMap<Integer,ImageIcon> icons;
    private Color colorElegido;
    private JLabel puntajeJugador2;
    private JLabel puntajeJugador;
    private JLabel turnoJugador;
    String nombreJugador1;
    String nombreJugador2;
    private JPanel puntajePanel;
    private JPanel puntajePanel22;
    private ImageIcon iconoEscalado;
    private ImageIcon iconoEscalado2;
    private String colorPlayer1;
    private String colorPlayer2;
    private String color;
    private int numFichas;
	private int numCasillas;
	private JPanel panelContadorFichas1;
	private JPanel panelContadorFichas2;
	private JLabel[] colum1;
	private JLabel[] colum2;
	private JLabel[] colum11;
	private JLabel[] colum22;
	private JButton nuevoBoton;
	private Timer tiempo;
	private int centesimas=0;
	private int segundos = 0;
	private int minutos = 0;
	private int horas = 0;
	private Player p2;
	private Player p1;
	private Juego juego;
	private boolean jugadoresHumanos=true;
	private int cantidad;
	private Timer maquinaTimer;
	private ActionListener acciones = new ActionListener() {
    	@Override
    	public void actionPerformed(ActionEvent e) {
    		centesimas++;
            if(centesimas == 100) {
            	segundos++;
            	centesimas = 0;
            	}
            if(segundos == 60) {
            	minutos++;
            	segundos = 0;
            	}
            if(minutos == 60) {
            	horas++;
            	minutos = 0;
            	}
            if(horas == 24) {
            	horas = 0;
            }
            actualizarEtiquetaTiempo();  
    	}
    };

    public GomokuGUI() {
        setTitle("Gomoku");
        prepareElementsMenu();
        prepareImages();
        pedirElementos();
        prepareElements();
        prepareActions();
        maquinaTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
					Gomoku.getInstance().jugar();
				} catch (GomokuException e1) {
					Log.record(e1);
				}
                refresh();
                maquinaTimer.stop(); 
            }
        });

    }
   
    
    private void prepareElements(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width / 4;
        int height = screenSize.height / 4;
        setSize(width, height);
        
        mainPanel.setLayout(new GridBagLayout());
        setContentPane(mainPanel);
        
        tiempo = new Timer(10,acciones);
        
        tableroPanel = new JPanel(new GridLayout(filas, cols));
        tableroPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); 
        botones = new JButton[filas][cols];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < cols; j++) {
                botones[i][j] = new JButton();
                botones[i][j].setPreferredSize(new Dimension(30, 30));
                botones[i][j].setOpaque(true);
                botones[i][j].setBorder(new LineBorder(Color.BLACK));
                botones[i][j].setContentAreaFilled(true);
                botones[i][j].setBackground(new Color(205, 133, 63));
                botones[i][j].setEnabled(false);
                tableroPanel.add(botones[i][j]);
            }
        }
        tableroPanel.setBackground(new Color(205, 133, 63));
   
        JPanel panelIzquierdo = new JPanel(new GridLayout(5,1));
        JPanel panelDerecho = new JPanel(new GridLayout(5, 1));
        panelIzquierdo.setBackground(new Color(205, 133, 63,0));
        panelDerecho.setBackground(new Color(205, 133, 63,0));
        for (int i = 0; i < 10; i++) {
        	if(i<5) {
        		JLabel label = new JLabel(icons.get(0));
        		panelIzquierdo.add(label);
        		}else {
        			JLabel label = new JLabel(icons.get(1));
        			panelDerecho.add(label);
        	}
            
        }
        
        GridBagConstraints gbcPanelIzquierdo = new GridBagConstraints();
        gbcPanelIzquierdo.gridx = 0;
        gbcPanelIzquierdo.gridy = 1;
        gbcPanelIzquierdo.insets = new Insets(0, 0, 0,20); 
        gbcPanelIzquierdo.anchor = GridBagConstraints.LINE_END;
        mainPanel.add(panelIzquierdo, gbcPanelIzquierdo);
        
        GridBagConstraints gbcTablero = new GridBagConstraints();
        gbcTablero.gridx = 1;
        gbcTablero.gridy = 1;
        gbcTablero.insets = new Insets(0, 0, 0, 0); 
        gbcTablero.gridheight = 1;
        gbcTablero.weightx = 0.0; 
        gbcTablero.weighty = 0.0;
        gbcTablero.anchor = GridBagConstraints.CENTER;
        gbcTablero.fill = GridBagConstraints.BOTH;
        mainPanel.add(tableroPanel, gbcTablero);
        GridBagConstraints gbcPanelDerecho = new GridBagConstraints();
        gbcPanelDerecho.gridx = 2;
        gbcPanelDerecho.gridy = 1;
        gbcPanelDerecho.insets = new Insets(0, 20, 5, 0);  
        gbcPanelDerecho.anchor = GridBagConstraints.LINE_START;
        mainPanel.add(panelDerecho, gbcPanelDerecho);
        
        Font font3 = new Font("Gang of Three", Font.BOLD, 25); 
        tiempoEtiqueta = new JLabel("00:00:00:00");
        tiempoEtiqueta.setBackground(new Color(193, 154, 107)); 
        tiempoEtiqueta.setFont(font3);
        GridBagConstraints gbcVerFicha = new GridBagConstraints();
        gbcVerFicha.gridx = 1; 
        gbcVerFicha.gridy = 0;  
        gbcVerFicha.anchor = GridBagConstraints.SOUTH;  
        gbcVerFicha.insets = new Insets(0, 0, 10, 0);  
        gbcVerFicha.gridwidth = 1; 
        gbcVerFicha.gridheight = 1;  

        
        mainPanel.add(tiempoEtiqueta, gbcVerFicha);
        
        nuevoBoton = new JButton("Iniciar Juego");
        nuevoBoton.setBackground(new Color(130,227,199));
        nuevoBoton.setPreferredSize(new Dimension(150, 50));
        GridBagConstraints gbcNuevoBoton = new GridBagConstraints();
        gbcNuevoBoton.gridx = 2;  
        gbcNuevoBoton.gridy = 2;  
        gbcNuevoBoton.anchor = GridBagConstraints.SOUTHEAST;  
        gbcNuevoBoton.insets = new Insets(0, 0, 100, 300);  
        mainPanel.add(nuevoBoton, gbcNuevoBoton);
        
        turnoJugador = new JLabel("Gomoku");
        turnoJugador.setForeground(Color.BLACK);
        Font font = new Font("Press Start 2P", Font.BOLD, 20); 
        Font font1 = new Font("Gang of Three", Font.BOLD, 15); 
        turnoJugador.setFont(font1);
        turnoJugador.setHorizontalAlignment(JLabel.CENTER);
        GridBagConstraints gbcLabel = new GridBagConstraints();
        gbcLabel.gridx = 1;  
        gbcLabel.gridy = 2;  
        gbcLabel.insets = new Insets(10, 0, 120, 0); 
        gbcLabel.weightx = 0.0; 
        gbcLabel.weighty = 0.0;
        mainPanel.add(turnoJugador, gbcLabel);
        Font font2 = new Font("Press Start 2P", Font.BOLD, 12); 
        puntajePanel = new JPanel();
        puntajePanel.setPreferredSize(new Dimension(width*2/4,height*2/2));
        puntajePanel.setLayout(new GridLayout(1,1,10,1));
        puntajePanel.setBackground(new Color(241,232,214,100));
        puntajePanel.setBorder(BorderFactory.createLineBorder(new Color(229,194,75))); 
        JLabel nombreJugador = new JLabel(""+nombreJugador1);
        nombreJugador.setHorizontalAlignment(JLabel.CENTER);
        nombreJugador.setFont(font2);
        puntajeJugador = new JLabel(""+Gomoku.getInstance().getPlayer1().getPuntaje());
        puntajeJugador.setHorizontalAlignment(JLabel.CENTER);
        puntajeJugador.setFont(font);
        puntajePanel.add(nombreJugador);
        puntajePanel.add(puntajeJugador);
        puntajePanel22 = new JPanel();
        puntajePanel22.setPreferredSize(new Dimension(width*2/4,height*2/2));
        puntajePanel22.setLayout(new GridLayout(1,1,10,1));
        puntajePanel22.setBackground(new Color(240,227,214,100));
        puntajePanel22.setBorder(BorderFactory.createLineBorder(new Color(229,194,75))); 
        JLabel nombreJugador22 = new JLabel(""+nombreJugador2);
        nombreJugador22.setHorizontalAlignment(JLabel.CENTER);
        nombreJugador22.setFont(font2);
        puntajeJugador2 = new JLabel(""+Gomoku.getInstance().getPlayer2().getPuntaje());
        puntajeJugador.setHorizontalAlignment(JLabel.CENTER);
        puntajeJugador2.setFont(font);
        puntajePanel22.add(nombreJugador22);
        puntajePanel22.add(puntajeJugador2);
        
        GridBagConstraints gbc = new GridBagConstraints();
        
        panelContadorFichas1 = new JPanel(new GridLayout(3,2));
        prepareFichas1();
        panelContadorFichas1.setBackground(new Color(0,0,0));
        panelContadorFichas2 = new JPanel(new GridLayout(3,2));
        prepareFichas2();
        panelContadorFichas2.setBackground(new Color(255,255,255));
        
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.gridx = 0;
        gbc1.gridy = 0;
        gbc1.gridheight = 1;
        gbc1.weightx = 1;
        gbc1.anchor = GridBagConstraints.NORTHWEST;
        gbc1.insets = new Insets(100, 100, 40, 20);
        mainPanel.add(panelContadorFichas1, gbc1);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(0, 100, 40, 20);
        mainPanel.add(puntajePanel, gbc);
        
        
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridx = 2; 
        gbc2.gridy = 0;
        gbc2.weightx = 1;
        gbc2.gridheight = 1; 
        gbc2.anchor = GridBagConstraints.NORTHEAST;
        gbc2.insets = new Insets(100, 20, 40, 100); 
        mainPanel.add(panelContadorFichas2, gbc2);
        
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.insets = new Insets(0, 20, 40, 100);

        mainPanel.add(puntajePanel22, gbc);
        
        
        pack();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    } 
    private void prepareImages() {
		    mainPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imagen = new ImageIcon(getClass().getClassLoader().getResource("images/fondo.jpg"));
                g.drawImage(imagen.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        ImageIcon imagen = new ImageIcon(getClass().getClassLoader().getResource("images/negro.png"));
        Image imagenEscalada = imagen.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        iconoEscalado = new ImageIcon(imagenEscalada);
        ImageIcon imagen2 = new ImageIcon(getClass().getClassLoader().getResource("images/blanco.png"));
        Image imagenEscalada2 = imagen2.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        iconoEscalado2 = new ImageIcon(imagenEscalada2);
    }
    private void prepareFichas1() {
    	colum1 = new JLabel[3];
    	colum2 = new JLabel[3];
    	
    	colum1[0] = new JLabel("Fichas Normales ");
    	colum1[1] = new JLabel("Fichas Temporales ");
    	colum1[2] = new JLabel("Fichas Pesadas ");
    	contarFichas();
    	for(int i = 0;i<6;i++) {
    		if(i<3) {
    			colum1[i].setFont(new Font("Gang of Three", Font.BOLD, 20));
    			colum1[i].setForeground(Color.WHITE);
    		}else {
    			colum2[i-3].setFont(new Font("Gang of Three", Font.BOLD, 20));
    			colum2[i-3].setForeground(Color.WHITE);
    		}
    		
    	}
    	for(int j = 0;j<3;j++) {
    		panelContadorFichas1.add(colum1[j]);
    		panelContadorFichas1.add(colum2[j]);
    	}
    	
    	
    }
    private void prepareFichas2() {
    	colum11 = new JLabel[3];
    	colum22 = new JLabel[3];
    	
    	colum11[0] = new JLabel("Fichas Normales ");
    	colum11[1] = new JLabel("Fichas Temporales ");
    	colum11[2] = new JLabel("Fichas Pesadas ");
    	contarFichas2();
    	for(int i = 0;i<6;i++) {
    		if(i<3) {
    			colum11[i].setFont(new Font("Gang of Three", Font.BOLD, 20));
    			colum11[i].setForeground(Color.BLACK);
    		}else {
    			colum22[i-3].setFont(new Font("Gang of Three", Font.BOLD, 20));
    			colum22[i-3].setForeground(Color.BLACK);
    		}
    		
    	}
    	for(int j = 0;j<3;j++) {
    		panelContadorFichas2.add(colum11[j]);
    		panelContadorFichas2.add(colum22[j]);
    	}
    	
    	
    }
    private void contarFichas() {
    	int contNormal=0;
    	int contTemp = 0;
    	int contPesada = 0;
    	colum2[0] = new JLabel(""+contNormal);
    	colum2[1] = new JLabel(""+contTemp);
    	colum2[2] = new JLabel(""+contPesada);
    }
    private void contarFichas2() {
    	int contNormal=0;
    	int contTemp = 0;
    	int contPesada = 0;
    	colum22[0] = new JLabel(""+contNormal);
    	colum22[1] = new JLabel(""+contTemp);
    	colum22[2] = new JLabel(""+contPesada);
    }
    private void actualizarContadorPlayer2() {
    	ArrayGomoku<Ficha> fichas = Gomoku.getInstance().getPlayer2().getFichas();
    	int contNormal=fichas.getContadorNormales();
    	int contPesada = fichas.getContadorPesadas();
    	int contTemp = fichas.getContadorTemporales();
    	colum22[0].setText(""+contNormal);
    	colum22[1].setText(""+contTemp);
    	colum22[2].setText(""+contPesada);
    }
    private void actualizarContadorPlayer1() {
    	ArrayGomoku<Ficha> fichas = Gomoku.getInstance().getPlayer1().getFichas();
    	int contNormal=fichas.getContadorNormales();
    	int contPesada = fichas.getContadorPesadas();
    	int contTemp = fichas.getContadorTemporales();
    	colum2[0].setText(""+contNormal);
    	colum2[1].setText(""+contTemp);
    	colum2[2].setText(""+contPesada);
    }
    private void prepareElementsMenu() {
        menu = new JMenuBar();
        setJMenuBar(menu);

        menu1 = new JMenu("Opciones");
        menu.add(menu1);

        menu2 = new JMenuItem("Abrir");
        menu1.add(menu2);

        menu3 = new JMenuItem("Terminar");
        menu1.add(menu3);

        menu4 = new JMenuItem("Salvar");
        menu1.add(menu4);
        menu5 = new JMenuItem("Salir");
        menu1.add(menu5);
        menu6 = new JMenuItem("Reiniciar");
        menu1.add(menu6);
        
        cambiarColorButton = new JButton("Cambiar Color");
        menu1.add(cambiarColorButton);

    }
    private void prepareActions() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmarCierre();
            }
        });
        menu3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Gomoku.getInstance().terminarJuego(null);
            }
        });
        menu5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmarCierre();
            }
        });
        menu2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirArchivo();
            }
        });
        menu4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarArchivo();
            }
        });
        menu6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reiniciarJuego();
            }
        });
        cambiarColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarColor();
            }
        });
        nuevoBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarJuego();
            }
        });
        
        for(int i=0;i<filas;i++){
            for(int j=0;j<cols;j++){
                botones[i][j].addActionListener(new ButtonClickListener(i,j));
            }
        }
       
    }
 

    
    
    private void pedirElementos() {
    	int[] tamanio = Dialogos.pedirTamanioTablero();
    	filas = tamanio[0];
    	cols = tamanio[1];
    	cantidad = filas*cols;
    	String tipoJugadores = DialogosJugador.preguntarJugadores();
    	Player[] jugadores = DialogosJugador.crearModoJuego(tipoJugadores);
    	if(tipoJugadores == "Jugador vs Maquina") {
    		jugadoresHumanos = false;
    	}
    	String[] nombres = DialogosJugador.solicitarNombres(this);
    	nombreJugador1 = nombres[0]; 
    	nombreJugador2 = nombres[1];
    	p1 = jugadores[0];
    	p2 = jugadores[1];
    	p1.setName(nombreJugador1);
    	p2.setName(nombreJugador2);
    	color = DialogosJugador.preguntarColor(this, nombreJugador1);
    	icons = new HashMap<>();
        colorPlayer1 = "Black";
        colorPlayer2 = "White";
        if(color.equals("White")){
            colorPlayer1 = "White";
            colorPlayer2 = "Black";
            icons.put(0,iconoEscalado2);
            icons.put(1,iconoEscalado);
        }else{
            icons.put(0,iconoEscalado);
            icons.put(1,iconoEscalado2);
        }
        p1.setColor(colorPlayer1);
        p2.setColor(colorPlayer2);
    	juego = DialogosModoJuego.crearModoJuego();
    	if(juego.getClass().getName().equals("domain.ModoPiedrasLimitadas")) {
    		cantidad = DialogosModoJuego.preguntarCantidadPiedras(filas*cols-1);
    	}
    	numFichas = Dialogos.preguntarFicha(cantidad);
    	numCasillas = Dialogos.preguntarCasilla(filas*cols);
    	Gomoku.getInstance().crearJuego(filas, cols, numCasillas, p1, p2);
    	try {
			juego.configurarJuego(numFichas, cantidad);
		} catch (GomokuException e) {
			System.out.print(e.getMessage());
		}
		Gomoku.getInstance().iniciarJuego(juego);
		Gomoku.getInstance().addObserver((Observador) this);
    }
    private void confirmarCierre() {
        int opcion = JOptionPane.showConfirmDialog(this, "¿Seguro que quieres cerrar la aplicación?",
                "Confirmar Cierre", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

   

    private void salvarArchivo() {
        new Thread(() -> {
            SwingUtilities.invokeLater(() -> {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showSaveDialog(this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        Persistencia.save(selectedFile, Gomoku.getInstance());
                    } catch (Exception e) {
                        e.printStackTrace(); 
                    }
                } else {
                    mostrarMensaje("Opción de guardar cancelada.");
                }
            });
        }).start();
    }

    private void abrirArchivo() {
    	JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                Gomoku gomoku = Persistencia.open(selectedFile);
                actualizarInterfaz(gomoku);
                mostrarMensaje("Juego cargado desde archivo: " + selectedFile.getName());
            } catch (Exception e) {
                e.printStackTrace(); 
            }
        } else {
            mostrarMensaje("Operación de abrir cancelada.");
        }
    }
    
    private void actualizarInterfaz(Gomoku gomoku) {
        filas = gomoku.getTamanioRow();
        cols = gomoku.getTamanioCol();
        nombreJugador1 = gomoku.getPlayer1().getName();
        nombreJugador2 = gomoku.getPlayer2().getName();
        colorPlayer1 = gomoku.getPlayer1().getColor();
        colorPlayer2 = gomoku.getPlayer2().getColor();
        p1 = gomoku.getPlayer1();
        p2 = gomoku.getPlayer2();
        icons = new HashMap<>();
        if(color.equals("White")){
            colorPlayer1 = "White";
            colorPlayer2 = "Black";
            icons.put(0,iconoEscalado2);
            icons.put(1,iconoEscalado);
        }else{
            icons.put(0,iconoEscalado);
            icons.put(1,iconoEscalado2);
        } 
        Gomoku.getInstance().setGomoku(gomoku);
        Gomoku.getInstance().crearObservador();
        Gomoku.getInstance().addObserver((Observador) this);
        tiempo.start();
        refresh();
    }
    
    private void cambiarColor() {
        colorElegido = JColorChooser.showDialog(this, "Seleccionar Color del Tablero", tableroPanel.getBackground());
        if (colorElegido != null) {
            for(int i= 0;i<filas;i++){
                for(int j=0;j<cols;j++){
                   botones[i][j].setBackground(colorElegido);
                }
            }
            tableroPanel.setBackground(colorElegido);
        }
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Funcionalidad en construcción", JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    public void playGame(int[] pos){
    	
		try {
			Gomoku.getInstance().jugarTablero(pos);
			refresh();
			
			if(!jugadoresHumanos && !Gomoku.getInstance().getWin()) {
				 maquinaTimer.start(); 
			}
		} catch (GomokuException e) {
			Log.record(e);
		}
		
    }
    public void refresh(){
        Ficha[][] board = Gomoku.getInstance().getTablero();
        boolean juegoTerminado = Gomoku.getInstance().getWin();
        for(int i=0;i<filas;i++){
            for(int j = 0;j<cols;j++){
                if(board[i][j] != null && board[i][j].getColor().equals(colorPlayer1)){
                        botones[i][j].setIcon(icons.get(0));
                }else if(board[i][j] != null && board[i][j].getColor().equals(colorPlayer2)){
                    botones[i][j].setIcon(icons.get(1));
                }else{
                      botones[i][j].setIcon(null);
                }
                botones[i][j].setEnabled(!juegoTerminado);
            }
        }
        if(!juegoTerminado) {
        	turnoJugador.setText("Turno de "+Gomoku.getInstance().getPlayer().getName());
        }
        puntajeJugador.setText(""+Gomoku.getInstance().getPlayer1().getPuntaje());
        puntajeJugador2.setText(""+Gomoku.getInstance().getPlayer2().getPuntaje());
        actualizarContadorPlayer1();
        actualizarContadorPlayer2();
    }
    public void changeColorCasillas(String casilla,int i, int j) {
    	if(casilla.equals("domain.Golden")) {
    		botones[i][j].setBackground(new Color(229,212,68));
    	}else if(casilla.equals("domain.Mine")) {
    		botones[i][j].setBackground(new Color(211,46,49));
    	}else if(casilla.equals("domain.Teleport")) {
    		botones[i][j].setBackground(new Color(68,229,217));
    	}
    }
    public void finishGame() {
    	tiempo.stop();
    	String ganador = Gomoku.getInstance().getGanador();
    	if(ganador != null) {
    		turnoJugador.setText("Juego terminado! el ganador es "+ganador);
    	}else {
    		turnoJugador.setText("Empate, Juego terminado");
    	}
    	refresh();
    }
    
    public void reiniciarJuego(){
    	if(tiempo.isRunning()) {
    		tiempo.stop();
    	}
    	centesimas= 0;
    	segundos = 0;
    	minutos = 0;
    	horas = 0;
    	actualizarEtiquetaTiempo();
    	Gomoku.getInstance().crearJuego(filas, cols, numCasillas, p1, p2);
    	try {
			juego.configurarJuego(numFichas, cantidad);
		} catch (GomokuException e) {
			System.out.print(e.getMessage());
		}
		Gomoku.getInstance().iniciarJuego(juego);
    	puntajeJugador.setText("0");
    	puntajeJugador2.setText("0");
    	refresh();
    	nuevoBoton.setEnabled(true);
    }
    private void iniciarJuego() {
    	nuevoBoton.setEnabled(false);
    	Gomoku.getInstance().getJuego().iniciarJuego();    	
    	tiempo.start(); 
    	turnoJugador.setText("Turno de "+Gomoku.getInstance().getPlayer().getName());
    	refresh();
    }
    private void actualizarEtiquetaTiempo() {
    	String texto = (horas <= 9?"0":"")+horas+":"+(minutos <= 9?"0":"")+minutos+":"+(segundos<=9?"0":"")+segundos+":"+(centesimas<=9?"0":"")+centesimas;
    	tiempoEtiqueta.setText(texto);
    	
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GomokuGUI());
    }

    private class ButtonClickListener implements ActionListener{
        private int row;
        private int column;
        ButtonClickListener(int row, int col){
            this.row = row;
            this.column = col;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            clickActual = getButton(row, column);
            playGame(clickActual);
            changeColorCasillas(Gomoku.getInstance().getCasilla(clickActual[0], clickActual[1]).getClass().getName(),clickActual[0],clickActual[1]);
            clickActual = null;
                
        }
        public int[] getButton(int r, int c){
            int[] posicion = new int[2];
            posicion[0] = r;
            posicion[1] = c;
            return posicion;
        }
    }
}


