
import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.io.FilenameFilter;

public class WinAlumno {
    JFrame winAlumno;
    JPanel panelAlumno;
    Empezar E;
    
    String nombreAlumno, Categoria;
    JLabel Dificultad, PuntMax, Alumno, selecCategoria;
    Choice diff, categ;
    JButton Start, Salir, cerrarSession;
    Icon Back;
    JLabel Background;
    Exit SalirWin;
    String ultimaPuntuacion;
    
    public WinAlumno(String nombreAlumno){
        this.nombreAlumno = nombreAlumno;
        winAlumno = new JFrame();
        winAlumno.setLayout(null);
        winAlumno.setUndecorated(true);
        winAlumno.setFocusable(true);
        winAlumno.setSize(700,300);
        winAlumno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        winAlumno.setVisible(true);
        
        panelAlumno = new JPanel();
        CompPanel();
        winAlumno.getContentPane().add(panelAlumno);
        panelAlumno.setBounds(0, 0, 700, 300);
        panelAlumno.setLayout(null);
        addComp();
    }
    
    public void addComp(){
        panelAlumno.add(PuntMax);
        panelAlumno.add(Dificultad);
        panelAlumno.add(Alumno);
        panelAlumno.add(Start);
        panelAlumno.add(cerrarSession);
        panelAlumno.add(diff);
        panelAlumno.add(Salir);
        panelAlumno.add(categ);
        panelAlumno.add(selecCategoria);
        panelAlumno.add(Background);
    }
    
    public void CompPanel(){
        CompButtons();
        CompLabels();
        CompChoice();
        CompBackground();
    }

    public void CompButtons() {
        SalirWin = new Exit();
        E = new Empezar();
        Start = new JButton("Empezar juego");
        cerrarSession = new JButton("Cerrar Session");
        Salir = new JButton("Salir");
        Start.setBounds(400, 135, 150, 30);
        cerrarSession.setBounds(400, 200, 150, 30);
        Salir.setBounds(400, 250, 100, 30);
        Start.addActionListener(E);
        cerrarSession.addActionListener(E);
        Salir.addActionListener(SalirWin);
        Start.setBackground(Color.CYAN);
        cerrarSession.setBackground(new Color(210, 110, 0));
        Salir.setBackground(Color.green);
    }
    
    public void CompLabels(){
        getPunt();
        Dificultad = new JLabel("Selecciona tu dificultad");
        selecCategoria = new JLabel("Selecciona tu categoria");
        PuntMax = new JLabel("Tú útima puntuación es: " + ultimaPuntuacion);
        Alumno = new JLabel("Alumno: " + nombreAlumno);
        Alumno.setBounds(50 , 50 , 150, 30);
        PuntMax.setBounds( 260, 50, 400, 30);
        Dificultad.setBounds(50 , 100 , 170, 30);
        selecCategoria.setBounds(50, 200, 170, 30);
        Alumno.setFont(new Font(Alumno.getFont().getFontName(),0,12));
        Dificultad.setFont(new Font(Dificultad.getFont().getFontName(),0,12));
        PuntMax.setFont(new Font(PuntMax.getFont().getFontName(),0,12));
    }
    
    public void CompChoice(){
        diff = new Choice();
        diff.setBounds(50, 140, 150, 30);
        diff.setBackground(Color.CYAN);
        diff.add("Fácil");
        diff.add("Medio");
        diff.add("Difícil");
        categ = new Choice();
        categ.setBounds(50, 240, 180, 30);
        categ.setBackground(Color.GREEN);
    	   String[] List = agregarListaCategorias();
        for(String Cat : List){
            categ.add(Cat.substring(0, Cat.length()-4));
        }
    }
    
    public String[] agregarListaCategorias(){
        String[] Lista = null;
        File cat = new File("Categorias/");
        FilenameFilter catFile = new FilenameFilter() 
        {
                public boolean accept(File dir, String name) 
                {
                    return !(name.startsWith("."));
                }
        };
        Lista = cat.list(catFile);
        return Lista;
    } 
    
    public void CompBackground(){
        try{
            Back = new ImageIcon("imag/BackRegistro.jpg");
        }catch(Exception ex ){System.out.println("Error al cargar el background");}
        Background = new JLabel(Back);
        Background.setBounds(0, 0, 700, 300);
    }
    
    public void getPunt(){
        BufferedReader  br = null;
        FileReader fr = null;
        String ruta = "Alumno/" + nombreAlumno + ".txt";
        String linea;
        try{ 
            File F = new File(ruta);
            fr = new FileReader(F.getAbsoluteFile());
            br = new BufferedReader(fr);
            while( (linea = br.readLine()) != null )
            	ultimaPuntuacion =linea;
        }catch(Exception ex){System.out.println("Error al obtener la última puntuación");}
    }
    
    class Empezar implements ActionListener{

        public void actionPerformed(ActionEvent ae) {
            winAlumno.dispose();
            if (ae.getActionCommand().equals(Start.getText())){
                try {
                	Categoria = categ.getItem(categ.getSelectedIndex());
                	WinBM winBM;
                    winBM = new WinBM(nombreAlumno,diff.getSelectedIndex(), Categoria);
                } catch (IOException ex){System.out.println("Error al empezar el juego");}
            }
            if (ae.getActionCommand().equals(cerrarSession.getText())){
            	 WinSession winSession;
                winSession = new WinSession();
            }
        }
        
    }
}
