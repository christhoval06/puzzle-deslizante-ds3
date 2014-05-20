import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Christhoval on 5/5/14.
 */
public class Proyecto2 extends JFrame implements ActionListener {

    private int FRAME_W = 800, FRAME_H = 600, BTN_W = 100, FILAS = 4, X = 80, Y = 75;
    private int BTN_H = BTN_W, COLUMNAS = FILAS, BOTONES = FILAS * COLUMNAS, segundos = 0, minutos = 0, intentos = 0, dirMove, move = 0, STEP = 25, STEP_TIME=30;
    private String VERSION = "v. 1.0.5", TITULO = "RompeCabezas";
    private boolean isMove = false, isStartPlay = false, isPlay = false, isSelected = false;
    private String map;
    private Random rnd = new Random();
    private Point comodin;
    private Color color1 = Color.decode("#aa92ff"),
            color2 = Color.decode("#554980"),
            color3 = Color.decode("#393155"),
            color4 = Color.decode("#2b2540"),
            color5 = Color.decode("#221d33");

    private List<ImageButton> piezas = null;
    private JPanel tablero = null, botones = null, presentacion = null, juegoOps = null;
    private JLabel lbl_tiempo, lbl_intentos;
    private ImageButton btnToMove,btnRevolver;
    private Timer tiempo = new Timer(1000, new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (segundos == 60) {
                segundos = 0;
                minutos += 1;
            }
            segundos += 1;
            lbl_tiempo.setText("<html><h1>" + String.format("%02d", minutos) + ":" + String.format("%02d", segundos) + "</h1></html>");
        }
    }), animation = new Timer(STEP_TIME, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (isMove) {
                int toMoveX = (dirMove == 'r' ? 1 : (dirMove == 'l' ? -1 : 0));
                int toMoveY = (dirMove == 'd' ? 1 : (dirMove == 'u' ? -1 : 0));
                move += STEP;
                if (move <= BTN_W) {
                    if (toMoveX != 0) {
                        btnToMove.setLocation(btnToMove.getLocation().x + STEP * toMoveX, btnToMove.getLocation().y);
                    }
                    if (toMoveY != 0) {
                        btnToMove.setLocation(btnToMove.getLocation().x, btnToMove.getLocation().y + STEP * toMoveY);
                    }
                } else {
                    animation.stop();
                    isMove = false;
                }
            }
        }
    });


    public Proyecto2() {
        setLayout(null);
        setTitle(TITULO + VERSION);
        setSize(FRAME_W, FRAME_H);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        getContentPane().setBackground(color3);
        init();
    }

    private void init() {
        crearPanelBotones();
        crearPanelPresentacion();
        repaint();
    }

    private JPanel panelLateral() {
        JPanel p = new JPanel();
        p.setLayout(null);
        p.setBounds(10, 10, 200, 540);
        p.setBackground(color2);
        p.setBorder(new CompoundBorder(new LineBorder(color5), new EmptyBorder(20, 20, 20, 20)));
        return p;
    }

    private void crearPanelBotones() {
        botones = panelLateral();
        add(botones);
        crearMenuBotones();
    }

    private void crearMenuBotones() {

        JLabel titulo = new JLabel("<html><h1>Diseños</h1></html>");
        titulo.setBounds(40, 20, 160, 30);
        titulo.setForeground(color3);
        botones.add(titulo);

        setFirmaOnPanel(botones);

        ImageButton btn = new ImageButton(getClass().getResource("maps/naruto/naruto.png"));
        btn.setBounds(20, 180, 75, 75);
        btn.setActionCommand("naruto");
        btn.addActionListener(this);
        botones.add(btn);

        btn = new ImageButton(getClass().getResource("maps/pokemon/pokemon.png"));
        btn.setBounds(100, 180, 75, 75);
        btn.setActionCommand("pokemon");
        btn.addActionListener(this);
        botones.add(btn);

        btn = new ImageButton(getClass().getResource("maps/shingeki/shingeki.png"));
        btn.setBounds(20, 260, 75, 75);
        btn.setActionCommand("shingeki");
        btn.addActionListener(this);
        botones.add(btn);

        btn = new ImageButton(getClass().getResource("maps/link/link.png"));
        btn.setBounds(100, 260, 75, 75);
        btn.setActionCommand("link");
        btn.addActionListener(this);
        botones.add(btn);


        btn = new ImageButton(getClass().getResource("maps/numeros/numeros.png"));
        btn.setBounds(20, 340, 75, 75);
        btn.setActionCommand("numeros");
        btn.addActionListener(this);
        botones.add(btn);

        btn = new ImageButton(getClass().getResource("maps/goku/goku.png"));
        btn.setBounds(100, 340, 75, 75);
        btn.setActionCommand("goku");
        btn.addActionListener(this);
        botones.add(btn);

        btn = new ImageButton(getClass().getResource("maps/evangelion/evangelion.png"));
        btn.setBounds(20, 420, 75, 75);
        btn.setActionCommand("evangelion");
        btn.addActionListener(this);
        botones.add(btn);

        btn = new ImageButton(getClass().getResource("maps/utp/utp.png"));
        btn.setBounds(100, 420, 75, 75);
        btn.setActionCommand("utp");
        btn.addActionListener(this);
        botones.add(btn);

    }

    private void setFirmaOnPanel(JPanel p) {
        JLabel titulo = new JLabel("<html><h5>by: christhoval</h5></html>");
        titulo.setBounds(110, 520, 100, 20);
        titulo.setForeground(color4);
        p.add(titulo);
    }

    private void crearPanelAyuda() {
        juegoOps = panelLateral();
        add(juegoOps);
        JLabel titulo = new JLabel("<html><h2>\"" + map.toUpperCase() + "\"</h2></html>");
        titulo.setBounds(45, 120, 130, 30);
        titulo.setForeground(color1);
        juegoOps.add(titulo);

        setFirmaOnPanel(juegoOps);

        ImageButton btn = new ImageButton(getClass().getResource("maps/" + map + "/" + map + ".png"));
        btn.setBounds(20, 180, 150, 150);
        btn.setActionCommand(map);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarImagen(map, false);
            }
        });
        juegoOps.add(btn);

        repaint();
    }

    private void crearPanelPresentacion(){
        presentacion = panelContenido();
        add(presentacion);

        JLabel titulo = new JLabel("<html><h2>Universidad Tecnologica de Panamá</h2></html>");
        titulo.setBounds(100, 20, 420, 40);
        titulo.setForeground(color1);
        presentacion.add(titulo);

        titulo = new JLabel("<html><h2>Desarrollo de Software III</h2></html>");
        titulo.setBounds(130, 70, 420, 40);
        titulo.setForeground(color1);
        presentacion.add(titulo);

        titulo = new JLabel("<html><h1>Cristóbal Barba</h1></html>");
        titulo.setBounds(60, 110, 250, 40);
        titulo.setForeground(color1);
        presentacion.add(titulo);

        titulo = new JLabel("<html><h2>5-706-1803</h2></html>");
        titulo.setBounds(320, 110, 420, 40);
        titulo.setForeground(color1);
        presentacion.add(titulo);

        titulo = new JLabel("<html><h1>" +TITULO+ " " + VERSION + "</h1></html>");
        titulo.setBounds(100, 250, 330, 60);
        titulo.setForeground(color3);
        presentacion.add(titulo);

    }

    private JPanel panelContenido() {
        JPanel p = new JPanel();
        p.setLayout(null);
        p.setBounds(220, 10, 560, 540);
        p.setBackground(color2);
        p.setBorder(new CompoundBorder(new LineBorder(color5), new EmptyBorder(20, 20, 20, 20)));
        return p;
    }

    private void crearPanelTablero() {
        minutos = segundos = intentos = 0;
        isMove = isPlay = isSelected = isStartPlay = false;
        tablero = panelContenido();

        JLabel titulo = new JLabel("<html><h2>Tiempo</h2></html>");
        titulo.setBounds(50, 20, 100, 30);
        titulo.setForeground(color3);
        tablero.add(titulo);

        lbl_tiempo = new JLabel("<html><h1>" + String.format("%02d", minutos) + ":" + String.format("%02d", segundos) + "</h1></html>");
        lbl_tiempo.setBounds(130, 20, 160, 30);
        lbl_tiempo.setForeground(color1);
        tablero.add(lbl_tiempo);

        titulo = new JLabel("<html><h2>Movimientos</h2></html>");
        titulo.setBounds(300, 20, 130, 30);
        titulo.setForeground(color3);
        tablero.add(titulo);

        lbl_intentos = new JLabel("<html><h1>" + intentos + "</h1></html>");
        lbl_intentos.setBounds(435, 20, 160, 30);
        lbl_intentos.setForeground(color1);
        tablero.add(lbl_intentos);

        addBotones();

        ImageButton btn = new ImageButton("CANCELAR", color1, color3);
        btn.setBounds(350, 490, 130, 40);
        btn.setActionCommand("cancelar");
        btn.addActionListener(this);
        tablero.add(btn);

        btnRevolver = new ImageButton("REVOLVER", color1, color3);
        btnRevolver.setBounds(80, 490, 130, 40);
        btnRevolver.setActionCommand("revolver");
        btnRevolver.addActionListener(this);
        tablero.add(btnRevolver);

        add(tablero);
        repaint();
    }

    private void addBotones() {
        if (piezas != null) piezas.clear();
        piezas = new ArrayList<ImageButton>();
        comodin = new Point(4, 4);
        for (int i = 0; i < BOTONES; i++)
            tablero.add(crearBoton(i));
    }

    private JButton crearBoton(int index) {
        ImageButton button = new ImageButton(String.valueOf(index), getClass().getResource("maps/" + map + "/" + index + ".jpg"));
        button.setBounds(X + (BTN_W * (index % FILAS)), Y + (BTN_H * (index / COLUMNAS)), BTN_W, BTN_H);
        button.addActionListener(this);
        button.setActionCommand("pieza");
        button.setFocusable(false);
        button.setIndex(index);
        button.setVisible(!(index == BOTONES - 1));
        piezas.add(button);
        return button;
    }

    private void reOrdenarBotones() {
        for (int i = 0; i < BOTONES - 1; i++) {
            int j = rnd.nextInt(BOTONES - 1);
            ImageButton bi = piezas.get(i), bj = piezas.get(j);
            Point l = piezas.get(i).getLocation();
            int index = bi.getIndex();

            bi.setIndex(bj.getIndex());
            bi.setLocation(bj.getLocation());
            bj.setLocation(l);
            bj.setIndex(index);
        }
    }

    private int getX(ImageButton b) {
        return (((b.getLocation().x - X) / BTN_W) + 1);
    }

    private int getY(ImageButton b) {
        return (((b.getLocation().y - Y) / BTN_H) + 1);
    }

    private void getDireccion(ImageButton btn) {
        Point p = new Point(getX(btn), getY(btn));
        int msg = 0;
        int dx = Math.abs(comodin.x - p.x), dy = Math.abs(comodin.y - p.y);
        if (dx == 0 && dy == 1 && p.y < comodin.y) msg = 'd';
        else if (dx == 0 && dy == 1 && p.y > comodin.y) msg = 'u';
        else if (p.x < comodin.x && dx == 1 && dy == 0) msg = 'r';
        else if (p.x > comodin.x && dx == 1 && dy == 0) msg = 'l';

        movePieza(btn, msg);
    }

    private void movePieza(ImageButton p, int dir) {
        switch (dir) {
            case 'd':
            case 'u':
            case 'r':
            case 'l':
                dirMove = dir;
                btnToMove = p;
                move = 0;
                isMove = true;
                animation.start();
                ImageButton btnComodin = piezas.get(BOTONES - 1);
                int piezaIndice = p.getIndex(), moveIndex = btnComodin.getIndex();
                Point piezaLocation = p.getLocation();
                comodin = new Point(getX(p), getY(p));
                btnComodin.setLocation(piezaLocation);
                btnComodin.setIndex(piezaIndice);
                p.setIndex(moveIndex);
                isStartPlay = true;
                intentos += 1;
                lbl_intentos.setText("<html><h1>" + intentos + "</h1></html>");
                break;
            default:
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ImageButton btn = (ImageButton) e.getSource();
        String action = e.getActionCommand();
        if (action.equalsIgnoreCase("revolver")) {
            if (!isStartPlay) {
                reOrdenarBotones();
                isPlay = true;
            } else btn.setEnabled(false);
        } else if (action.equalsIgnoreCase("cancelar")) {
            tiempo.stop();
            remove(juegoOps);
            remove(tablero);
            init();
        } else if (action.equalsIgnoreCase("pieza")) {
            if (isPlay) {
                if (!tiempo.isRunning()){
                    tiempo.start();
                    btnRevolver.setEnabled(false);
                }
                if (!isMove) {
                    getDireccion(btn);
                    if (revisar())
                        mostrarImagen();
                }
            }
        } else
            mostrarImagen(btn, true);
    }

    private void mostrarImagen(ImageButton b, boolean jugar) {
        mostrarImagen(b.getActionCommand(), jugar);
    }

    private void mostrarImagen(final String img, boolean jugar) {
        final JDialog dialog = new JDialog(this, "", true);
        dialog.setSize(442, 500);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.setUndecorated(true);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setLayout(null);
        dialog.getContentPane().setBackground(color1);
        dialog.getRootPane().setBackground(color1);
        dialog.getRootPane().setBorder(new CompoundBorder(new LineBorder(color3), new EmptyBorder(20, 20, 20, 20)));
        ImageButton btn = new ImageButton(getClass().getResource("maps/" + img + "/" + img + ".png"));
        btn.setBounds(0, 0, 400, 400);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        dialog.getContentPane().add(btn);

        if (jugar) {
            btn = new ImageButton("JUGAR", color1, color3);
            btn.setBounds(10, 410, 130, 40);
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dialog.dispose();
                    map = img;
                    remove(botones);
                    remove(presentacion);
                    crearPanelAyuda();
                    crearPanelTablero();
                }
            });
            dialog.add(btn);
        } else {
            JLabel titulo = new JLabel("<html><h2>" + map.toUpperCase() + "</h2></html>");
            titulo.setBounds(10, 410, 130, 40);
            titulo.setForeground(color3);
            dialog.add(titulo);
        }
        btn = new ImageButton("CERRAR", color1, color3);
        btn.setBounds(260, 410, 130, 40);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        dialog.add(btn);
        dialog.setVisible(true);
    }

    private void mostrarImagen() {
        final JDialog dialog = new JDialog(this, "", true);
        dialog.setSize(442, 500);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.setUndecorated(true);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setLayout(null);
        dialog.getContentPane().setBackground(color1);
        dialog.getRootPane().setBackground(color1);
        dialog.getRootPane().setBorder(new CompoundBorder(new LineBorder(color3), new EmptyBorder(20, 20, 20, 20)));

        JLabel titulo = new JLabel("<html><h1>Lo Has Logrado!!!</h1></html>");
        titulo.setBounds(95, 10, 250, 40);
        titulo.setForeground(color3);
        dialog.add(titulo);

        ImageButton btn = new ImageButton(getClass().getResource("maps/" + map + "/" + map + ".png"));
        btn.setBounds(75, 50, 250, 250);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        dialog.add(btn);

        titulo = new JLabel("<html><h1>Tiempo: " + String.format("%02d", minutos) + ":" + String.format("%02d", segundos) + "</h1></html>");
        titulo.setBounds(20, 310, 250, 40);
        titulo.setForeground(color3);
        dialog.add(titulo);

        titulo = new JLabel("<html><h1>Move.: " + intentos + "</h1></html>");
        titulo.setBounds(240, 310, 250, 40);
        titulo.setForeground(color3);
        dialog.add(titulo);

        btn = new ImageButton("REPETIR", color1, color3);
        btn.setBounds(20, 410, 110, 40);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
                tiempo.stop();
                isStartPlay = true;
                remove(tablero);
                crearPanelTablero();
            }
        });
        dialog.add(btn);

        btn = new ImageButton("ELEJIR OTRO", color1, color3);
        btn.setBounds(129, 410, 140, 40);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
                tiempo.stop();
                remove(juegoOps);
                remove(tablero);
                init();

            }
        });
        dialog.add(btn);

        btn = new ImageButton("SALIR", color1, color3);
        btn.setBounds(268, 410, 110, 40);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
                System.exit(0);
            }
        });
        dialog.add(btn);

        dialog.setVisible(true);
    }

    private boolean revisar() {
        Component[] fichas = tablero.getComponents();
        ImageButton btn;
        for (int i = 0; i < fichas.length; i++) {
            if (fichas[i].getClass().getSimpleName().equalsIgnoreCase("ImageButton")) {

                btn = (ImageButton) fichas[i];
                if (btn.getActionCommand().equalsIgnoreCase("pieza")) {
                    if ((Integer.parseInt(btn.getText())) != (btn.getIndex()))
                        return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        new Proyecto2();
    }
}
