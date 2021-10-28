
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Collator;
import java.util.Random;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.util.Date;

public class WinBM{
    JFrame winBM;
    JPanel panelBM;
    private Preguntas PregResp;
    
    private JButton[][] boton;
    private Minas m[][];
    private PushButton PM[][];
    private Boolean B = true;
    private Clip Bomba, Num, RespCorrecta, RespIncorrecta, finBueno, finMalo;
    private Boolean N = true;
    private Icon cero;
    private Collator comparador;
    
    int puntuacion;
    String nombreAlumno;
    int maxMinas;
    int vidas = 1;
    String Categoria;
    int[] minas;
    
    String Resp = null;
    
    public WinBM(String nombreAlumno, int Dificultad, String Categoria) throws IOException{
        switch(Dificultad){
            case 0:
                maxMinas = 5;
                break;
            case 1:
                maxMinas = 7;
                break;
            case 2:
                maxMinas = 10;
                break;
        }
        minas = new int[maxMinas*2];
        PregResp = new Preguntas();
        this.nombreAlumno = nombreAlumno;
        this.Categoria = Categoria;
        comparador = Collator.getInstance();
        comparador.setStrength(Collator.PRIMARY);
        winBM = new JFrame();
        panelBM = new JPanel();
        winBM.setLayout(null);
        winBM.setUndecorated(true);
        winBM.setFocusable(true);
        winBM.setSize(500,500);
        winBM.setResizable(false);
        winBM.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        winBM.setVisible(true);
        
        IniciarPanel();
        winBM.getContentPane().add(panelBM);
        panelBM.setBounds(0, 0, 500, 500);
    }
    
    public void IniciarPanel(){
        panelBM.setLayout(null);
        panelBM.setFocusable(true);
        try{cero = new ImageIcon("num/num_0.jpg");}catch(Exception ex1){System.out.println("Imagen cero no encontrada");}
        try{
        Bomba = AudioSystem.getClip(null);
        Bomba.open(AudioSystem.getAudioInputStream(new File("sonidos/Bomba.wav").getAbsoluteFile()));
        Num = AudioSystem.getClip(null);
        Num.open(AudioSystem.getAudioInputStream(new File("sonidos/NoMina.wav").getAbsoluteFile()));
        RespCorrecta = AudioSystem.getClip(null);
        RespCorrecta.open(AudioSystem.getAudioInputStream(new File("sonidos/aplausos_rec.wav").getAbsoluteFile()));
        RespIncorrecta = AudioSystem.getClip(null);
        RespIncorrecta.open(AudioSystem.getAudioInputStream(new File("sonidos/error.wav").getAbsoluteFile()));
        finBueno = AudioSystem.getClip(null);
        finBueno.open(AudioSystem.getAudioInputStream(new File("sonidos/Aplausos.wav").getAbsoluteFile()));
        finMalo = AudioSystem.getClip(null);
        finMalo.open(AudioSystem.getAudioInputStream(new File("sonidos/fin.wav").getAbsoluteFile()));
        }catch(Exception ex){
            System.out.println("Error al cargar los sonidos");
        }
        boton = new JButton[10][10];
        m = new Minas[10][10];
        PM = new PushButton[10][10];
        IniciarMapa();
        GenerarMinas();
        try{
        }catch(Exception ex){
            System.out.println("Error al cargar los sonidos");
        }
    }
    
    public void IniciarMapa(){
        try{
            Icon icon = new ImageIcon("imag/ground.jpeg");
            int j,k, x=0,y=0;
            for (j=0; j<10; j++){
               for(k=0; k<10; k++){
                   boton[j][k] = new JButton( icon);
                   PM[j][k] = new PushButton(j , k);
                   m[j][k] = new Minas();
                   boton[j][k].setBorder(BorderFactory.createEmptyBorder());
                   boton[j][k].setBounds(x,y,50,50);
                   boton[j][k].addActionListener(PM[j][k]);
                   panelBM.add(boton[j][k]);
                   x=x+50;
               } 
                x=0;
                y=y+50;
            }
        }
        catch(Exception e){
            System.out.println("No se pudo cargar la imágen ground.jpeg");
        }  
    }

    private void GenerarMinas() {
        int x, y, n=0, i=0;
        while(n < maxMinas){
            x = new Random().nextInt(10);
            y = new Random().nextInt(10);
            if( ! m[x][y].esMinaPreg() ){
                minas[i] = x;
                minas[i+1] = y;
                m[x][y].hacerMina();
                SenMinas(x,y);
                n++;
            }
        }
    }
    
    public void SenMinas( int j, int k){
        int lx = j + 1;
        int ly = k + 1;
        int nx = j - 1;
        int ny = k - 1;
        try{
            while(nx <= lx){
                ny= k - 1;
                while(ny <= ly){
                    if ((nx != j || ny != k) && ny >=0 && nx>=0 && ny<10 && nx <10)
                        m[nx][ny].moreMinaProx();
                    ny++;
                }
                nx++;
            }
        }
        catch(Exception e){ }
    }
    
    public void CheckMinasCero(int j, int k){
        int xi = j - 1; int xf = j + 1;
        int yi = k - 1; int yf = k + 1;
        try{
            while(xi <= xf){
                yi= k - 1;
                while(yi <= yf){
                    if ((xi != j || yi != k) && yi >=0 && xi>=0 && yi<10 && xi <10  )
                        if (m[xi][yi].estaActiva() && m[xi][yi].getMinasProx() == 0){
                            boton[xi][yi].setIcon(cero);
                            puntuacion += 10;
                            m[xi][yi].DesActivar();
                            CheckMinasCero(xi,yi);
                        }
                    yi ++;
                }
                xi ++;
            }
        }
        catch(Exception e){ }
        CheckMinasNums(j,k);
    }
    
    public void CheckMinasNums(int j, int k){
        int xi = j - 1; int xf = j + 1;
        int yi = k - 1; int yf = k + 1;
        int num;
        Icon icon;
        String nombreImg;
        try{
            while(xi <= xf){
                yi= k - 1;
                while(yi <= yf){
                    if ((xi != j || yi != k) && yi >=0 && xi>=0 && yi<10 && xi <10)
                        if (m[xi][yi].estaActiva() && m[xi][yi].getMinasProx() != 0){
                            num = m[xi][yi].getMinasProx();
                            nombreImg = "num/num_" + (String.valueOf(num)) + ".jpg";
                            puntuacion += 10*( num + 1);
                            icon = new ImageIcon(nombreImg);
                            boton[xi][yi].setIcon(icon);
                            m[xi][yi].DesActivar();
                        }
                    yi ++;
                }
                xi ++;
            }
        }
        catch(Exception e){ }
    }
    
    public void EndGame(){
        checkBanderas();
        JOptionPane.showMessageDialog(null, "Tú puntuación es: "+ puntuacion, nombreAlumno, JOptionPane.PLAIN_MESSAGE);
        guardarPuntuacion();
        winBM.dispose();
        WinAlumno winAlumno;
        winAlumno = new WinAlumno(nombreAlumno);
    }
    
    public void checkBanderas(){
        for(int i = 0; i < (maxMinas*2) ; i = i+2){
           if (m[minas[i]][minas[i+1]].getBandera())
               puntuacion += 20;
        }
    }
    
    public boolean checkGameover(){
        
        for(int x = 0; x < 10; x++){
            for(int y = 0; y < 10; y++){
                if(m[x][y].estaActiva() && !(m[x][y].esMinaPreg()) )
                    return false;
            }
        }
        return true;
    }
    
    public void guardarPuntuacion(){
        BufferedWriter  bw = null;
        FileWriter fw = null;
        Date D = new Date();
	   String dv = D.toString().substring(4, 19);
        String ruta = "Alumno/" + nombreAlumno + ".txt";
        File F = new File(ruta);
        try{
            fw = new FileWriter(F.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);
            bw.newLine();
            bw.write(Categoria +" " + Integer.toString(puntuacion) + " "+ dv);
            bw.close();
            fw.close();
        }catch(Exception ex ){System.out.println("ERROR al guardar la puntuación");}
    }
    
    public void GenerarPregunta(){
        PregResp.cargarPregunta(Categoria);
        Resp = JOptionPane.showInputDialog(null,PregResp.getPregunta() ,PregResp.getCategoria() , JOptionPane.QUESTION_MESSAGE);
        if (Resp != null && comparador.compare(Resp, PregResp.getRespuesta()) == 0){
            RespCorrecta.loop(0);
            puntuacion -= 100;
        }else{  
            RespIncorrecta.loop(0);
            JOptionPane.showMessageDialog(null, ("Respuesta correcta: "+ PregResp.getRespuesta()),"Tú respuesta fue incorrecta :C",JOptionPane.ERROR_MESSAGE);
            vidas--;
        }
        Resp = null;
    }
    
    class PushButton implements ActionListener{
        int x, y;
        public PushButton( int x, int y){
            this.x = x;     this.y = y;
        }
        
        public void actionPerformed(ActionEvent e){
            if ( m[x][y].estaActiva() )
                if( m[x][y].esMinaPreg() ){//Sí es mina
            	  	if (B){
                       Bomba.loop(0);
                       B = false;
                   }else
                       Bomba.loop(1);
                    try{
                        Icon icon = new ImageIcon("imag/boom.jpg");
                        boton[x][y].setIcon(icon);
                    }
                    catch(Exception ex){
                        System.out.println("No se pudo cargar la imágen boom.jpg");
                    }
                    GenerarPregunta();
                    if (vidas == 0){
                        finMalo.loop(0);
                        EndGame();
                    }
                    m[x][y].DesActivar();
                }else{//Sí es número
                    int num = m[x][y].getMinasProx();
                    String nombreImg = "num/num_" + (String.valueOf(num)) + ".jpg";
                    puntuacion += (num+1) * 10;
                    try{
                        Icon icon = new ImageIcon(nombreImg);
                        boton[x][y].setIcon(icon);
                    }catch(Exception ex){
                        System.out.println("No se pudo cargar las imágenes de los números");
                    }                    
                    m[x][y].DesActivar();
                    if (m[x][y].getMinasProx() == 0 )CheckMinasCero(x,y);
                    if (N){
                        Num.loop(0);
                        N = false;
                    }else{
                        Num.loop(1);
                    }
                    if( checkGameover() ){
                        finBueno.loop(0);
                        EndGame();
                    }
                }
        }
    };
}
