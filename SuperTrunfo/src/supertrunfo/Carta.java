package supertrunfo;

class AtributoCarta {
    private AtributoModelo atributoModelo;
    private int valor;
    
    public AtributoCarta(AtributoModelo atributoModelo,
            int valor) {
        this.atributoModelo = atributoModelo;
        this.valor = valor;
    }
    
    /**
     * Compara este objeto AtributoCarta com outro.
     * @param outroAtributoCarta
     * @return -1, se o valor deste atributo for menor
     *         que o do outro; 0, se o valor deste atributo
     *         for igual ao do outro; 1, se o valor deste
     *         atributo for maior que o do outro.
     */
    int comparaCom(AtributoCarta outroAtributoCarta) {
        if (this.valor < outroAtributoCarta.valor) {
            return -1;
        } else if (this.valor == outroAtributoCarta.valor) {
            return 0;
        } else { // if (this.valor > outroAtributoCarta.valor) {
            return 1;
        }
    }
    
    int getValor() {
        return valor;
    }

    AtributoModelo getAtributoModelo() { 
        return atributoModelo; 
}
}

class Carta {
    private ModeloCarta modelo;

    private String nome;
    private AtributoCarta[] atributos;
    
    public Carta(ModeloCarta modelo, String nome, int[] valoresAtributos) {
        this.modelo = modelo;
        this.nome = nome;
        this.atributos = new AtributoCarta[valoresAtributos.length];
        
        for (int i = 0; i < valoresAtributos.length; ++i) {
            this.atributos[i] = new AtributoCarta(modelo.getAtributoAt(i),
                                                  valoresAtributos[i]);
        }
    }

    public String getNome() {
        return nome;
    }

    public AtributoCarta getAtributoAt(int pos) {
        return atributos[pos];
    }

}
