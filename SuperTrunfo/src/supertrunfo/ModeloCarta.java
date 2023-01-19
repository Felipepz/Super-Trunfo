package supertrunfo;

class AtributoModelo {
    private String nome;
    private int valorMinimo;
    private int valorMaximo;
    
    public AtributoModelo(String nome, int valorMinimo, int valorMaximo) {
        this.nome = nome;
        this.valorMinimo = valorMinimo;
        this.valorMaximo = valorMaximo;
    }
    
    public String getNome() {
        return nome;
    }
}

class ModeloCarta {
    private String nome;
    private AtributoModelo[] atributos;
    
    public ModeloCarta(String nome, int numAtributos) {
        this.nome = nome;
        atributos = new AtributoModelo[numAtributos];
    }
    
    public void setAtributo(int pos, AtributoModelo atr) {
        atributos[pos] = atr;
    }
    
    public int getNumAtributos() {
        return atributos.length;
    }
    
    AtributoModelo getAtributoAt(int pos) {
        return atributos[pos];
    }
}
