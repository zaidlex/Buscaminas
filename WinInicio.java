import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.io.File;

public class WinInicio {

    JFrame winInicio;
    JPanel panelInicio;
        
    JButton Inicio;
    JButton Salir;
    JLabel BackGround;
    Icon Back;
    StartSesion startS;
 
    File carpetas;
    File archivos;
    
    public WinInicio(){
        checkFolders();
        winInicio = new JFrame();
        winInicio.setLayout(null);
        winInicio.setUndecorated(true);
        winInicio.setFocusable(true);
        winInicio.setSize(700,500);
        winInicio.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        winInicio.setVisible(true);
        
        panelInicio = new JPanel();
        CompPanel();        
        winInicio.getContentPane().add(panelInicio);
        panelInicio.setBounds(0, 0, 700, 500);
        panelInicio.add(Inicio);
        panelInicio.add(Salir);
        panelInicio.add(BackGround);
    }
    
    
    public void CompPanel(){
        CompBotones();
        CompBack();
    }
    
    public void CompBack(){
        try{
            Back = new ImageIcon("imag/MineSweeper.jpg");
        }catch(Exception ex){System.out.println("Imagen background no encontrada");}
        BackGround = new JLabel(Back);
        BackGround.setBounds(0, 0, 700, 500);
    }
    
    public void CompBotones(){
        Exit salir = new Exit();
        startS = new StartSesion(); 
        Inicio = new JButton("Empezar");
        Salir = new JButton("Salir");   
        Inicio.setBounds(240,90,120,30);
        Salir.setBounds(330,380,80,30);
        Inicio.setFont(new Font("Opel LCD-3 regular",Font.PLAIN,17));
        Inicio.setBorderPainted(false);
        Salir.setBorderPainted(false);
        Inicio.setBackground(Color.LIGHT_GRAY);
        Salir.setBackground(Color.LIGHT_GRAY);
        Salir.addActionListener(salir);
        Inicio.addActionListener(startS);    
    }
    
    public void checkFolders(){
        carpetas = new File("Maestro");
        if(! carpetas.exists())
            carpetas.mkdir();
        carpetas = new File("Alumno");
        if(! carpetas.exists())
            carpetas.mkdir();
        carpetas = new File("Categorias");
        if(! carpetas.exists())
            carpetas.mkdir();
    }

    class StartSesion implements ActionListener{

        public void actionPerformed(ActionEvent ae) {   
            winInicio.dispose();
            WinSession winSession;
            winSession = new WinSession();
        }
    }
}
