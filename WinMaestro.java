
import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class WinMaestro {
    JFrame winMaestro;
    JPanel panelMaestro;
    ButtonListener ButtonL;
    
    JButton Salir, guardarPregunta, cerrarSession, guardarCategoria, eliminarPregCategorias, PuntAlumnos;
    JLabel setCategoria, selectCategoria, LPregunta, LRespuesta;
    JTextField Categoria, TPregunta, TRespuesta;
    Choice ListaCategorias;
    Icon back;
    JLabel backGround;
    
    public WinMaestro(){
        winMaestro = new JFrame();
        winMaestro.setLayout(null);
        winMaestro.setUndecorated(true);
        winMaestro.setFocusable(true);
        winMaestro.setSize(500,500);
        winMaestro.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        winMaestro.setVisible(true);
        
        panelMaestro = new JPanel();
        CompPanel();
        winMaestro.getContentPane().add(panelMaestro);
        panelMaestro.setBounds(0, 0, 500, 500);
        panelMaestro.setLayout(null);
        AgregarComp();
        panelMaestro.add(backGround);
    }
    
    public void CompPanel(){
        CompBotones();
        CompLabels();
        CompTexts();
        CompChoice();
        CompBackGround();
    }
    
    public void CompBotones(){
        Exit exit = new Exit();
        ButtonL = new ButtonListener();
        Salir = new JButton("Salir");
        guardarCategoria = new JButton("Guardar categoría");
        guardarPregunta = new JButton("Guardar Pregunta");
        cerrarSession = new JButton("Cerrar sesión");
        eliminarPregCategorias = new JButton("Eliminar pregunta/categoria");
        PuntAlumnos = new JButton("Reporte de alumnos");
        Salir.setBounds(390, 435, 80, 30);
        guardarCategoria.setBounds(145, 85, 200, 30);
        guardarPregunta.setBounds(50,385, 200,30);
        cerrarSession.setBounds(225, 435, 150, 30);
        eliminarPregCategorias.setBounds(20, 435, 200, 30);
        PuntAlumnos.setBounds(270, 385, 180, 30);
        Salir.setBorderPainted(false);
        guardarCategoria.setBorderPainted(false);
        guardarPregunta.setBorderPainted(false);
        cerrarSession.setBorderPainted(false);
        eliminarPregCategorias.setBorderPainted(false);
        PuntAlumnos.setBorderPainted(false);
        Salir.setBackground(Color.LIGHT_GRAY);
        cerrarSession.setBackground(Color.LIGHT_GRAY);
        guardarCategoria.setBackground(Color.GRAY);
        guardarPregunta.setBackground(Color.GRAY);
        eliminarPregCategorias.setBackground(Color.LIGHT_GRAY);
        PuntAlumnos.setBackground(Color.LIGHT_GRAY);
        Salir.addActionListener(exit);
        cerrarSession.addActionListener(ButtonL);
        guardarCategoria.addActionListener(ButtonL);
        guardarPregunta.addActionListener(ButtonL);
        eliminarPregCategorias.addActionListener(ButtonL);
        PuntAlumnos.addActionListener(ButtonL);
    }
    
    public void CompLabels(){
        setCategoria = new JLabel("Agregar categoría");
        selectCategoria = new JLabel("Seleccionar categoría");
        LPregunta = new JLabel("Pregunta");
        LRespuesta = new JLabel("Respuesta");
        setCategoria.setFont(new Font(setCategoria.getFont().getFontName(),0,13));
        selectCategoria.setFont(new Font(selectCategoria.getFont().getFontName(),0,13));
        LPregunta.setFont(new Font(LPregunta.getFont().getFontName(),0,13));
        LRespuesta.setFont(new Font(LRespuesta.getFont().getFontName(),0,13));
        setCategoria.setBounds(50, 35, 150, 30);
        selectCategoria.setBounds(50, 135, 180, 30);
        LPregunta.setBounds(50, 185, 100, 30);
        LRespuesta.setBounds(50, 285, 100, 30);
    }
    
    public void CompTexts(){
        Categoria = new JTextField();
        TPregunta = new JTextField();
        TRespuesta = new JTextField();
        Categoria.setBounds(250, 35, 150, 30);
        TPregunta.setBounds(50, 235, 300, 30);
        TRespuesta.setBounds(50, 335, 300, 30);
        Categoria.setBackground(Color.lightGray);
        TPregunta.setBackground(Color.lightGray);
        TRespuesta.setBackground(Color.lightGray);
    }
    
    public void CompChoice(){
        ListaCategorias = new Choice();
        ListaCategorias.setBounds(270, 135, 180, 30);
        ListaCategorias.setBackground(Color.lightGray);
        String[] List = agregarListaCategorias();
        for(String Cat : List){
            ListaCategorias.add(Cat.substring(0, Cat.length()-4));
        }
    }
    
    public void CompBackGround(){
        try{
            back = new ImageIcon("imag/BackBandera.jpg");
        }catch(Exception ex){System.out.println("Background no encontado");}
        backGround = new JLabel(back);
        backGround.setBounds(0, 0, 500, 500);
    }
    
    public void AgregarComp(){
        panelMaestro.add(Salir);
        panelMaestro.add(guardarPregunta);
        panelMaestro.add(guardarCategoria);
        panelMaestro.add(cerrarSession);
        panelMaestro.add(eliminarPregCategorias);
        panelMaestro.add(PuntAlumnos);
        
        panelMaestro.add(setCategoria);
        panelMaestro.add(selectCategoria);
        panelMaestro.add(LPregunta);
        panelMaestro.add(LRespuesta);
        
        panelMaestro.add(Categoria);
        panelMaestro.add(TPregunta);
        panelMaestro.add(TRespuesta);
        
        panelMaestro.add(ListaCategorias);
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
    
    public void GuardarCategoria(){
    	   if (Categoria.getText() != null){
		   String ruta = "Categorias/" + Categoria.getText() + ".txt";
		   File C = new File(ruta);
		   if (! C.exists()){
		       try{
		           C.createNewFile();
		           ListaCategorias.add(Categoria.getText());
		           Categoria.setText(null);
		       }catch(Exception ex){System.out.println("Error al crear la categoría");}
		       JOptionPane.showMessageDialog(null, "Se ha guardado la categoría", "Categoria guardada", JOptionPane.DEFAULT_OPTION);
		   }else{JOptionPane.showMessageDialog(null, "Categoria ya registrada", "Error al guardar categoria", JOptionPane.ERROR_MESSAGE);}     
        }   
    }
    
    public void GuardarPregunta(){
        BufferedWriter bw = null;
        FileWriter fw = null;
        String Preg = TPregunta.getText();
        String Resp = TRespuesta.getText();
        String ruta = "Categorias/"+ ListaCategorias.getItem(ListaCategorias.getSelectedIndex()) + ".txt";
        try{
            File F = new File(ruta);
            fw = new FileWriter(F.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);
            bw.write(Preg);
            bw.newLine();
            bw.write(Resp);
            bw.newLine();
            bw.close();
            fw.close();
            TPregunta.setText(null);
            TRespuesta.setText(null);
            JOptionPane.showMessageDialog(null, "Pregunta guardada correctamente", "Pregunta guardada",JOptionPane.PLAIN_MESSAGE);
        }catch(Exception ex){System.out.println("Error al escribir en el fichero");}   
    }
    
    class ButtonListener implements ActionListener{

        public void actionPerformed(ActionEvent ae) {
            if(ae.getActionCommand().equals(cerrarSession.getText())){
                winMaestro.dispose();
                WinSession winSession = new WinSession();
            }
            if(ae.getActionCommand().equals(guardarCategoria.getText()))
                GuardarCategoria();
            if(ae.getActionCommand().equals(guardarPregunta.getText()))
                GuardarPregunta();
            if(ae.getActionCommand().equals(eliminarPregCategorias.getText())){
                winMaestro.dispose();
                WinMaestroEdit winMaestroEdit = new WinMaestroEdit();
            }
            if(ae.getActionCommand().equals(PuntAlumnos.getText())){
            	 winMaestro.dispose();
            	 WinReporte winReporte = new WinReporte();
            }
        }
        
    }

}
