import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.BufferedReader;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.io.IOException;

public class WinReporte{
	JFrame winReporte;
	JPanel panelReporte;
	Botones alBotones;

	private JButton regresar, verDatos;
	private JLabel Instrucciones;
	private Choice ListaAlumnos;
	private JTextArea Datos;
     Icon back;
     JLabel Background;
     
     public WinReporte(){
     	winReporte = new JFrame();
    		winReporte.setLayout(null);
    		winReporte.setUndecorated(true);
   		winReporte.setFocusable(true);
	     winReporte.setSize(500,500);
    		winReporte.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    		winReporte.setVisible(true);
    
    		panelReporte = new JPanel();
    		CompPanel();
	     winReporte.getContentPane().add(panelReporte);
		panelReporte.setBounds(0, 0, 500, 500);
    		panelReporte.setLayout(null);
    		AddComp();
     }
     
     public void CompPanel(){
        CompChoice();
        CompButtons();
        CompLabels();
        CompText();
        CompBack();
     }
     
     public void AddComp(){
     	panelReporte.add(regresar);
     	panelReporte.add(verDatos);
     	panelReporte.add(Instrucciones);
     	panelReporte.add(ListaAlumnos);
     	panelReporte.add(Datos);
     	panelReporte.add(Background);
     }
     
    public void CompBack(){
        try{
            back = new ImageIcon("imag/BackBandera.jpg");
        }catch(Exception ex){System.out.println("Background no encontado");}
        Background = new JLabel(back);
        Background.setBounds(0, 0, 500, 500);
    }
    
   private void CompButtons() {
        alBotones = new Botones();
        verDatos = new JButton("Ver reporte de alumnos");
        regresar = new JButton("Regresar");
        verDatos.setBounds(250, 50, 200, 30);
        regresar.setBounds(400, 460, 100, 30);
        verDatos.setBorderPainted(false);
        regresar.setBorderPainted(false);
        verDatos.setBackground(Color.GRAY);
        regresar.setBackground(Color.GRAY);
        verDatos.addActionListener(alBotones);
        regresar.addActionListener(alBotones);
    }
   private void CompLabels() {
        Instrucciones = new JLabel("<html>Los datos están ordenados de la forma <br> Categoria - Puntuacion- mes - dia - hora<html>");
        Instrucciones.setFont(new Font(Instrucciones.getFont().getFontName(),Font.BOLD,13));
        Instrucciones.setBounds(50, 80, 300, 60);
    }
    
    private void CompChoice() {
        ListaAlumnos = new Choice();
        ListaAlumnos.setBounds(50, 50, 180, 30);
        String[] List = agregarListaCategorias();
        for(String Cat : List){
            ListaAlumnos.add(Cat.substring(0, Cat.length()-4));
        }
    }
    public String[] agregarListaCategorias(){
        String[] Lista = null;
        File cat = new File("Alumno/");
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
    
   public void CompText(){
        Datos = new JTextArea();
        Datos.setBounds(20, 150, 460, 300);
        Datos.setEditable(false);
        Datos.setOpaque(false);
        Datos.setBackground(Color.lightGray);
    }
    
    public void cargarDatos(){
        Datos.setText(null);
        Datos.setOpaque(true);
        BufferedReader  br = null;
        FileReader fr = null;
        String ruta = "Alumno/"+ ListaAlumnos.getItem(ListaAlumnos.getSelectedIndex()) +".txt";
        try{
            File F = new File(ruta);
            fr = new FileReader(F.getAbsoluteFile());
            br = new BufferedReader(fr);
            String score = "No hay datos";
            br.readLine();
            br.readLine();
            while((score = br.readLine() ) != null){
                Datos.append(score);
                Datos.append("\n");
            }
        }catch(IOException ex){System.out.println("Error al cargar los datos del alumno " + ex.getMessage());}
    }
    
    public class Botones implements ActionListener{
        public void actionPerformed(ActionEvent ae) {
            if(ae.getActionCommand().equals(verDatos.getText())){
                cargarDatos();
            }
            if(ae.getActionCommand().equals(regresar.getText())){
                winReporte.dispose();
                WinMaestro winMaestro;
                winMaestro = new WinMaestro();
            }
        }
        
    }
}
