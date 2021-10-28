import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class WinSession {
    
    JFrame winSession;
    JPanel panelSession;
    WinAlumno winAlumno;
    
    JButton Registrar, Iniciar;
    JLabel Wellcome, background;
    Icon Back;
    CheckUsuario checkU;
    StartRegistro startR;
    JLabel nomUsuario;
    JLabel conUsuario;
    JTextField Usuario;
    JPasswordField Contrasena;
    
    File cuenta;
    String nombreUsuario;
    String contrasena;
    String istipoUsuario = null;
    String ruta;
    boolean contrasenaCorrecta = false;
    
    public WinSession(){
        winSession = new JFrame();
        winSession.setLayout(null);
        winSession.setUndecorated(true);
        winSession.setFocusable(true);
        winSession.setSize(500,500);
        winSession.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        winSession.setVisible(true);
        
        panelSession = new JPanel();
        CompPanel();
        winSession.getContentPane().add(panelSession);
        panelSession.setBounds(0, 0, 500, 500);
        panelSession.setLayout(null);
        panelSession.add(Iniciar);
        panelSession.add(Registrar);
        panelSession.add(Wellcome);
        panelSession.add(nomUsuario);
        panelSession.add(conUsuario);
        panelSession.add(Usuario);
        panelSession.add(Contrasena);
        panelSession.add(background);
    }
    
    public void CompPanel(){
        CompBack();
        CompBotones();
        CompSession();
    }
    
    public void CompBack(){
        try{
            Back = new ImageIcon("imag/BackBandera.jpg");
        }catch(Exception e){System.out.println("Imagen background no encontrada");}
        background = new JLabel(Back);
        background.setBounds(0, 0, 500, 500);
        Wellcome = new JLabel("Bienvenido");
        Wellcome.setFont(new Font("Opel LCD-3 regular",Font.PLAIN,20));
        Wellcome.setBounds(170, 50, 150, 60);
    }
    
    public void CompBotones(){
        checkU = new CheckUsuario();
        startR = new StartRegistro();
        Iniciar  = new JButton("Iniciar");
        Registrar = new JButton("Registrar");
        Iniciar.setBounds(205, 350, 90, 30);
        Registrar.setBounds(350, 455, 120, 30);
        Iniciar.setFont(new Font("Opel LCD-3 regular",Font.PLAIN,17));
        Registrar.setFont(new Font("Opel LCD-3 regular",Font.PLAIN,17));
        Iniciar.setBorderPainted(false);
        Registrar.setBorderPainted(false);
        Iniciar.setBackground(Color.BLACK);
        Iniciar.setForeground(Color.LIGHT_GRAY);
        Registrar.setBackground(Color.LIGHT_GRAY);
        Iniciar.addActionListener(checkU);
        Registrar.addActionListener(startR);
    }
    
    public void CompSession(){
        nomUsuario = new JLabel("Nombre de usuario");
        conUsuario = new JLabel("Contraseña");
        nomUsuario.setFont(new Font(nomUsuario.getFont().getFontName(),0,13));
        conUsuario.setFont(new Font(conUsuario.getFont().getFontName(),0,13));
        nomUsuario.setBounds(60, 140, 150, 30);
        conUsuario.setBounds(60, 240, 150, 30);
        Usuario = new JTextField();
        Contrasena = new JPasswordField();
        Usuario.setBounds(60, 185, 150, 30);
        Contrasena.setBounds(60, 285, 150, 30);
        Usuario.setBackground(Color.lightGray);
        Contrasena.setBackground(Color.lightGray);
    }
    
    public void checkTipoUsuario(){
        ExisteUsuario("Maestro");
        ExisteUsuario("Alumno");
    }
    
    public void ExisteUsuario(String ITU){
        ruta = ITU + "/" +nombreUsuario+".txt";
        cuenta = new File(ruta);
        if(cuenta.exists())
            istipoUsuario = ITU;
    }
    
    public void checkContrasena(){
        try{
            cuenta = new File(istipoUsuario+ "/" +nombreUsuario+".txt");
            FileReader fw = new FileReader(cuenta);
            BufferedReader bw = new BufferedReader(fw);
            if(contrasena.equals(bw.readLine().toString()))
                contrasenaCorrecta = true;
            bw.close();
        }catch(Exception ex){System.out.println("Error al abrir archivo");}
    }

    class StartRegistro implements ActionListener {
        
        public void actionPerformed(ActionEvent ae){
            winSession.dispose();
            WinRegistro winRegistro = new WinRegistro();
        }
    }
    class CheckUsuario implements ActionListener {
        
        public void actionPerformed(ActionEvent ae){
            nombreUsuario = Usuario.getText();
            contrasena = new String(Contrasena.getPassword());
            checkTipoUsuario();
            if(istipoUsuario == null){
                JOptionPane.showMessageDialog(null, "Usuario no registrado", "ERROR al iniciar cuenta", JOptionPane.ERROR_MESSAGE);
            }else{
                checkContrasena();
                if(contrasenaCorrecta){
                    winSession.dispose();
                    if("Maestro".equals(istipoUsuario)){
					WinMaestro winMaestro;
                        winMaestro = new WinMaestro();
                    }else{
                        winAlumno = new WinAlumno(nombreUsuario);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Contraseña incorrecta", "ERROR al inciar sesión", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }    
    
}
