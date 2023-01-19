package supertrunfo;

public class Jogador {
    private String nome;
    private FilaCartas cartas;
    private boolean jogadorAtivo;
    
    public Jogador(String nome) {
        this.nome = nome;
        cartas = new FilaCartas();
        jogadorAtivo = true;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void recebeCartas(FilaCartas cartas) {
        this.cartas.insereCartasDeFila(cartas);
    }
    
    public Carta jogaCarta() {
        if (jogadorAtivo && !cartas.filaVazia()) {
            Carta carta = cartas.removeCartaDoInicio();
            return carta;
        }
        return null;
    }
    
    public boolean temCartas() {
        return !cartas.filaVazia();
    }
    
    public boolean estaAtivo() {
        return jogadorAtivo;
    }
    
    public void setJogadorAtivo(boolean ativo) {
        jogadorAtivo = ativo;
    }
}
