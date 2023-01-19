package aplicacao;

import supertrunfo.SuperTrunfo;


public class Main {
    public static void main(String[] args) {
        SuperTrunfo superTrunfo = new SuperTrunfo();
        superTrunfo.inicializarScanner();
        superTrunfo.lerArquivo();
        superTrunfo.lerJogadores();
        superTrunfo.realizaPartida();
    }
}
    

