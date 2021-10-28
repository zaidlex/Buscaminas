import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class WinRegistro {
    
    JFrame winRegistro;
    JPanel panelRegistro;
    
    WinSession winSession;
    AceptarUsuario aceptarUsuario;
    Cancelar cancelar;
    
    JLabel nomUsuario;
    JLabel conUsuario;
    JTextField Usuario;
    JPasswordField Contrasena;
    JLabel tipoUsuario;
    JRadioButton Maestro;
    JRadioButton Alumno;
    JButton Aceptar;
    JButton Cancelar;
    JLabel BackGround;
    Icon Back;
    File cuenta;
    
    String nombreUsuario;
    String contrasena;
    String istipoUsuario = null;
    String ruta;
    
    public WinRegistro(){
        winRegistro = new JFrame();
        winRegistro.setLayout(null);
        winRegistro.setUndecorated(true);
        winRegistro.setFocusable(true);
        winRegistro.setSize(700,300);
        winRegistro.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        winRegistro.setVisible(true);
        
        panelRegistro = new JPanel();
        CompPanel();
        winRegistro.getContentPane().add(panelRegistro);
        panelRegistro.setBounds(0, 0, 700, 300);
        panelRegistro.setLayout(null);
        panelRegistro.add(nomUsuario);
        panelRegistro.add(conUsuario);
        panelRegistro.add(Usuario);
        panelRegistro.add(Contrasena);
        panelRegistro.add(tipoUsuario);
        panelRegistro.add(Maestro);
        panelRegistro.add(Alumno);
        panelRegistro.add(Aceptar);
        panelRegistro.add(Cancelar);
        panelRegistro.add(BackGround);
    }
    
    public void CompPanel(){
        CompTextField();
        CompRadioButtons();
        CompButtons();
        CompBackGround();
    }

    private void CompTextField() {
        nomUsuario = new JLabel("Nombre de usuario");
        conUsuario = new JLabel("Contraseña");
        nomUsuario.setFont(new Font(nomUsuario.getFont().getFontName(),0,16));
        conUsuario.setFont(new Font(conUsuario.getFont().getFontName(),0,16));
        nomUsuario.setBounds(50, 40, 200, 30);
        conUsuario.setBounds(50, 140, 150, 30);
        Usuario = new JTextField();
        Contrasena = new JPasswordField();
        Usuario.setBounds(50, 85, 150, 30);
        Contrasena.setBounds(50, 185, 150, 30);
    }
    
    public void CompRadioButtons(){
        tipoUsuario = new JLabel("Tipo de usuario");
        tipoUsuario.setFont(new Font(tipoUsuario.getFont().getFontName(),0,16));
        tipoUsuario.setBounds(240, 50, 150, 60);
        Maestro = new JRadioButton("Maestro");
        Alumno = new JRadioButton("Alumno");
        Maestro.setOpaque(true);
        Alumno.setOpaque(true);
        Maestro.setBorder(null);
        Alumno.setBorder(null);
        Maestro.setBackground(Color.CYAN);
        Alumno.setBackground(new Color(210, 110, 0));
        Maestro.setBounds(250, 150, 80, 30);
        Alumno.setBounds(250, 200, 80, 30);
    }
    
    public void CompButtons(){
        aceptarUsuario = new AceptarUsuario();
        cancelar = new Cancelar();
        Aceptar = new JButton();
        Cancelar = new JButton();
        Aceptar.setText("Aceptar");
        Cancelar.setText("Cancelar");
        Aceptar.setBounds(500, 150, 100, 30);
        Cancelar.setBounds(500, 250, 100, 30);
        Aceptar.setBorderPainted(false);
        Cancelar.setBorderPainted(false);
        Aceptar.setBackground(Color.cyan);
        Cancelar.setBackground(Color.green);
        Aceptar.addActionListener(aceptarUsuario);
        Cancelar.addActionListener(cancelar);
    }
    
    public void CompBackGround(){
        try{
            Back = new ImageIcon("imag/BackRegistro.jpg");
        }catch(Exception ex){System.out.println("Imagen de background no encontrada");}
        BackGround = new JLabel(Back);
        BackGround.setBounds(0, 0, 700, 300);
    }
    
    public void returnWinSession(){
        winRegistro.dispose();
        winSession= new WinSession();
    }
    
    public boolean checkUsuario(){
        ruta = "Maestro/"+ nombreUsuario + ".txt";
        cuenta = new File(ruta);
        if (cuenta.exists() )
            return true;
        ruta = "Alumno/"+ nombreUsuario + ".txt";
        cuenta = new File(ruta);
        if (cuenta.exists() )
            return true;
        return false;
    }
    
    public void crearUsuario(){
        try{
            String ruta = istipoUsuario+"/" + nombreUsuario+ ".txt";
            cuenta = new File(ruta);
            cuenta.createNewFile();
            FileWriter fw = new FileWriter(cuenta);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(contrasena);
            bw.newLine();
            bw.write("0");
            bw.close();
        }catch(Exception ex){System.out.println("Error al crear archivo");}
    }
    
    class AceptarUsuario implements ActionListener{

        public void actionPerformed(ActionEvent ae) {
            nombreUsuario = Usuario.getText();
            contrasena = new String(Contrasena.getPassword());
            if(Maestro.isSelected())
                istipoUsuario = "Maestro";
            if(Alumno.isSelected())
                istipoUsuario = "Alumno";
            if (istipoUsuario == null){
                JOptionPane.showMessageDialog(null, "Escoga tipo de usuario", "ERROR al crear usuario", JOptionPane.ERROR_MESSAGE);
            }else{
                if (checkUsuario()){
                    JOptionPane.showMessageDialog(null, "Usuario ya creado", "ERROR al crear usuario", JOptionPane.ERROR_MESSAGE);
                }else{
                    crearUsuario();
                    JOptionPane.showMessageDialog(null, "Usuario registrado correctamente", "Registrado",JOptionPane.PLAIN_MESSAGE);
                    returnWinSession();
                }
            }
        }
        
    }
    class Cancelar implements ActionListener{
        public void actionPerformed(ActionEvent ae) {
            returnWinSession();
        }
    }
     
}
