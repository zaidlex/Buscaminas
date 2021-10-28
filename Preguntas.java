
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.Random;

public class Preguntas {

    private String Pregunta;
    private String Respuesta;
    private String Categoria;
    private String[] Lista = null;
    
    public Preguntas(){ObtenerListaCategorias();}
    
    public void cargarPregunta(String Categoria){
    	   this.Categoria = Categoria;
        generarPregResp();
    }
    
    public String getPregunta(){
        return Pregunta;
    }
    
    public String getRespuesta(){
        return Respuesta;
    }
    
    public String getCategoria(){
        return Categoria.substring(0, Categoria.length()-4);
    }
    
    
    private  void generarPregResp(){
        int k, l = 0, lineas = 0;
        BufferedReader  br = null;
        FileReader fr = null;
        File F;
        String ruta = "Categorias/" + Categoria+ ".txt";
        try{
            F = new File(ruta);
            fr = new FileReader(F);
            br = new BufferedReader(fr);
		  while (br.readLine() != null) lineas++;
		  k = new Random().nextInt((int) (lineas/ 2));
            //k = new Random().nextInt((int) (br.lines().count()/ 2));
            fr.close();
            br.close();
            fr = new FileReader(F);
            br = new BufferedReader(fr);
            System.out.println(Categoria);
            System.out.println(lineas);
            System.out.println("l="+ l + " k= "+k);
            while(l <= k){
                Pregunta = br.readLine();
                Respuesta = br.readLine();
            	 System.out.println(Pregunta);
            	 System.out.println(Respuesta);
                l++; 
            }
            fr.close();
            br.close();
        }catch(Exception ex){System.out.println("Error al generar la pregunta");}
    }
    
    private void ObtenerListaCategorias(){
        File cat = new File("Categorias/");
        FilenameFilter catFile = new FilenameFilter(){
                public boolean accept(File dir, String name){
                    return !(name.startsWith("."));
                }
        };
        Lista = cat.list(catFile);
    }
}
