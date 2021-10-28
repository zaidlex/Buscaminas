
public class Minas {
    
    private int Minas_prox;
    private boolean Activa;
    private boolean isMine;
    private boolean bandera;
    
    public Minas(){
       Minas_prox = 0;
       Activa = true;
       isMine = false;
       bandera = false;
    }
    
    public void moreMinaProx(){
        Minas_prox = Minas_prox + 1;
    }
    
    public int getMinasProx(){
        return Minas_prox;
    }
    
    public boolean estaActiva(){
        return Activa;
    }
    
    public void DesActivar(){
        Activa = false;
    }
    
    public boolean esMinaPreg(){
        return isMine;
    }
    
    public void hacerMina(){
        isMine = true;
    }
    
    public boolean getBandera(){
        return bandera;
    }
    
    public void setBandera(){
        bandera = true;
    }
    
    public void quitBandera(){
        bandera = false;
    }
}

