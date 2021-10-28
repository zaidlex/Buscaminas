import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;

public class Buscaminas {
    
    public static void main(String[] args) throws IOException {
	   Buscaminas b = new Buscaminas();	
        b.Musica();
        b.CreaWinInicio();
    }
    
    public void Musica(){
		Clip musica;
        try{
            musica = AudioSystem.getClip(null);            
            musica.open(AudioSystem.getAudioInputStream(new File("sonidos/music_mine_sweeper.wav")));
            musica.loop(Clip.LOOP_CONTINUOUSLY);
        }catch(Exception ex){System.out.println(ex +"Error al cargar la musica de fondo");}
    }
    
    public void CreaWinInicio(){
	   WinInicio winInicio = new WinInicio();
    }
     
}
