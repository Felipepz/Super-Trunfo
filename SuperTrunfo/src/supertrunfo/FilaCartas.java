package supertrunfo;

class ItemFilaCartas {
    private Carta carta;
    private ItemFilaCartas proximoItem;
    
    public ItemFilaCartas(Carta carta) {
        this.carta = carta;
        proximoItem = null;
    }
    
    public Carta getCarta() {
        return carta;
    }
    
    public ItemFilaCartas getProximoItem() {
        return proximoItem;
    }
    
    public void setProximoItem(ItemFilaCartas proximoItem) {
        this.proximoItem = proximoItem;
    }
}

public class FilaCartas {
    private ItemFilaCartas inicioDaFila;
    private ItemFilaCartas fimDaFila;
    
    public FilaCartas() {
        inicioDaFila = null;
        fimDaFila = null;
    }
    
    public boolean filaVazia() {
        return inicioDaFila == null;
    }
    
    public void insereCarta(Carta carta) {
        ItemFilaCartas novoItem = new ItemFilaCartas(carta);
        if (fimDaFila != null) {
            fimDaFila.setProximoItem(novoItem);
            fimDaFila = novoItem;
        } else {
            inicioDaFila = novoItem;
            fimDaFila = novoItem;
        }
    }
    
    public Carta removeCartaDoInicio() {
        if (inicioDaFila != null) {
            Carta cartaDoInicio = inicioDaFila.getCarta();
            inicioDaFila = inicioDaFila.getProximoItem();
            if (inicioDaFila == null) {
                fimDaFila = null;
            }
            return cartaDoInicio;
        } else {
            return null;
        }
    }
    
    public void insereCartasDeFila(FilaCartas outraFila) {
//        fimDaFila.setProximoItem(outraFila.inicioDaFila);
//        fimDaFila = outraFila.fimDaFila;
//        outraFila.inicioDaFila = null;
//        outraFila.fimDaFila = null;
        
        while (outraFila != null && !outraFila.filaVazia()) {
            Carta carta = outraFila.removeCartaDoInicio();
            insereCarta(carta);
        }
    }
}
