import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class WinMaestroEdit {
    JFrame winMaestroEdit;
    JPanel panelMaestroEdit;
    Botones alBotones;
    
    private JButton Guardar, eliminarCategoria, regresar, eliminarPregResp;
    JLabel Precaucion, Instrucciones;
    private Choice ListaCategorias;
    private JTextArea PregResp;
    Icon back;
    JLabel Background;
    
    File cat;
    
    public WinMaestroEdit(){
    winMaestroEdit = new JFrame();
    winMaestroEdit.setLayout(null);
    winMaestroEdit.setUndecorated(true);
    winMaestroEdit.setFocusable(true);
    winMaestroEdit.setSize(500,500);
    winMaestroEdit.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    winMaestroEdit.setVisible(true);
    
    panelMaestroEdit = new JPanel();
    CompPanel();
    winMaestroEdit.getContentPane().add(panelMaestroEdit);
    panelMaestroEdit.setBounds(0, 0, 500, 500);
    panelMaestroEdit.setLayout(null);
    AddComp();
    }

    public void CompPanel() {
        CompChoice();
        CompButtons();
        CompLabels();
        CompText();
        CompBack();
    }
    
    public void AddComp(){
        panelMaestroEdit.add(ListaCategorias);
        panelMaestroEdit.add(Instrucciones);
        panelMaestroEdit.add(Precaucion);
        panelMaestroEdit.add(Guardar);
        panelMaestroEdit.add(eliminarCategoria);
        panelMaestroEdit.add(regresar);
        panelMaestroEdit.add(PregResp);
        panelMaestroEdit.add(eliminarPregResp);
        panelMaestroEdit.add(Background);
    }
    
    public void CompBack(){
        try{
            back = new ImageIcon("imag/BackBandera.jpg");
        }catch(Exception ex){System.out.println("Background no encontado");}
        Background = new JLabel(back);
        Background.setBounds(0, 0, 500, 500);
    }

    private void CompLabels() {
        Precaucion = new JLabel("<html>Sí elimina una categoria <br>también se eliminarán las preguntas <html>");
        Instrucciones = new JLabel("<html>Elimine la pregunta con su respuesta <br>no deje lineas en blanco entre preguntas<html>");
        Precaucion.setFont(new Font(Precaucion.getFont().getFontName(),Font.BOLD,13));
        Instrucciones.setFont(new Font(Instrucciones.getFont().getFontName(),Font.BOLD,13));
        Precaucion.setBounds(20, 60, 300, 60);
        Instrucciones.setBounds(20, 440, 300, 60);
    }

    private void CompButtons() {
        alBotones = new Botones();
        Guardar = new JButton("Guardar");
        regresar = new JButton("Regresar");
        eliminarCategoria = new JButton("Eliminar categoría");
        eliminarPregResp = new JButton("Eliminar preguntas");
        Guardar.setBounds(295, 460, 95, 30);
        regresar.setBounds(400, 460, 100, 30);
        eliminarCategoria.setBounds(290, 20, 180, 30);
        eliminarPregResp.setBounds(250,60,200,30);
        Guardar.setBorderPainted(false);
        regresar.setBorderPainted(false);
        eliminarCategoria.setBorderPainted(false);
        eliminarPregResp.setBorderPainted(false);
        Guardar.setBackground(Color.GRAY);
        regresar.setBackground(Color.GRAY);
        eliminarCategoria.setBackground(Color.LIGHT_GRAY);
        eliminarPregResp.setBackground(Color.LIGHT_GRAY);
        eliminarPregResp.addActionListener(alBotones);
        Guardar.addActionListener(alBotones);
        regresar.addActionListener(alBotones);
        eliminarCategoria.addActionListener(alBotones);
    }

    private void CompChoice() {
        ListaCategorias = new Choice();
        ListaCategorias.setBounds(20, 20, 180, 30);
        String[] List = agregarListaCategorias();
        for(String Cat : List){
            ListaCategorias.add(Cat.substring(0, Cat.length()-4));
        }
    }
    
    public void CompText(){
        PregResp = new JTextArea();
        PregResp.setBounds(20, 140, 460, 280);
        PregResp.setEditable(false);
        PregResp.setOpaque(false);
        PregResp.setBackground(Color.lightGray);
    }
    
    public void agregarPregRespText() throws IOException{
    	   PregResp.setText(null);
        PregResp.setOpaque(true);
        PregResp.setEditable(true);
        BufferedReader  br = null;
        FileReader fr = null;
        String ruta = "Categorias/"+ ListaCategorias.getItem(ListaCategorias.getSelectedIndex()) +".txt";
        try{
            File F = new File(ruta);
            fr = new FileReader(F.getAbsoluteFile());
            br = new BufferedReader(fr);
            String Spreg;
            String Sresp;
            while((Spreg = br.readLine() ) != null){
                Sresp = br.readLine();
                PregResp.append(Spreg);
                PregResp.append("\n");
                PregResp.append(Sresp);
                PregResp.append("\n");
            }
        }catch(IOException ex){System.out.println("Error al cargar el archivo " + ex.getMessage());}
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
    
    public void EliminarCategoria(int n){
        try{
            String ruta = "Categorias/"+ListaCategorias.getItem(ListaCategorias.getSelectedIndex()) + ".txt";
            cat = new File(ruta);
            cat.delete();
            ListaCategorias.remove(n);
            JOptionPane.showMessageDialog(null, "categoría eliminada exitosamente", "categoria eliminada", JOptionPane.DEFAULT_OPTION);
        }catch(Exception ex){System.out.println("Erroral eliminar categoría");}
    }
    
    public void GuardarPreguntas(){
        BufferedWriter  bw = null;
        FileWriter fw = null;
        String ruta = "Categorias/"+ ListaCategorias.getItem(ListaCategorias.getSelectedIndex()) + ".txt";
        try{
        File F = new File(ruta); 
        F.delete();
        fw = new FileWriter(ruta);
        bw = new BufferedWriter(fw);
        bw.write(PregResp.getText());
        bw.close();
        fw.close();
        JOptionPane.showMessageDialog(null, "Datos guardados correctamente","Datos guardados",JOptionPane.PLAIN_MESSAGE);
        }catch(Exception ex){System.out.println("Error al guardar el archivo");}
    }
    
    public class Botones implements ActionListener{
        public void actionPerformed(ActionEvent ae) {
            if(ae.getActionCommand().equals(eliminarCategoria.getText())){
                EliminarCategoria(ListaCategorias.getSelectedIndex());
            }
            if(ae.getActionCommand().equals(Guardar.getText())){
                GuardarPreguntas();
            }
            if(ae.getActionCommand().equals(eliminarPregResp.getText())){
                try{
                    agregarPregRespText();
                }catch(Exception ex){System.out.println(ex);}
            }
            if(ae.getActionCommand().equals(regresar.getText())){
                winMaestroEdit.dispose();
                WinMaestro winMaestro;
                winMaestro = new WinMaestro();
            }
        }
        
    }
}
