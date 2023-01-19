package supertrunfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author alexandre-kde
 */
public class SuperTrunfo {
    
    private ModeloCarta modelo;
    private Carta[] cartas;
    
    private Scanner sc;
    
    private Jogador[] jogadores;
    private int jogadorDaRodada;
    private int numJogadoresAtivos;

    Carta cartaJogadorDaRodada;
    private FilaCartas cartasNaMesa;
    private int indiceDoAtributoParaComparar;
    private Carta[] cartasDosAdversarios;
    private int[] indicesDosAdversarios;
    
    public void inicializarScanner() {
        sc = new Scanner(System.in);
    }

   
    
    public void lerModelo(File arquivo, Scanner leitor) {
        String nomeModelo = leitor.nextLine();
        System.out.println("Nome: " + nomeModelo);
        int numAtributos = leitor.nextInt();
        modelo = new ModeloCarta(nomeModelo, numAtributos);
        leitor.nextLine();
        for (int i = 0; i < numAtributos; i++) {
            String nomeAtributo;
            int min, max;
            nomeAtributo = leitor.nextLine();
            min = leitor.nextInt();
            max = leitor.nextInt();
              leitor.nextLine();
              AtributoModelo atr = new AtributoModelo(nomeAtributo, min, max);
              modelo.setAtributo(i, atr);
            }      
    }
    
    public void lerCartas(File arquivo, Scanner leitor) {
        int numCartas = leitor.nextInt();
        cartas = new Carta[numCartas];
        System.out.println("Número de cartas: " + numCartas);
        leitor.nextLine(); // posiciona o cursor de leitura na próxima linha
        for (int i = 0; i < numCartas; ++i) {
            String nome = leitor.nextLine();
            int[] valores = new int[modelo.getNumAtributos()];
            for (int j = 0; j < modelo.getNumAtributos(); ++j) {
                valores[j] = leitor.nextInt();
            }
            if(leitor.hasNextLine()){
                leitor.nextLine();
            }
            Carta c = new Carta(modelo, nome, valores);
            cartas[i] = c;
        }
    } 

    public void lerArquivo(){
        String nome;
        System.out.print("Informe o nome do arquivo com os jogadores: ");
        nome = sc.nextLine();
        nome = nome + ".txt";
        try{
            File arquivo = new File(nome);
            Scanner leitor = new Scanner(arquivo);
            lerModelo(arquivo, leitor);
            lerCartas(arquivo, leitor);
            leitor.close();
        } catch(FileNotFoundException e){
            System.out.println("\nErro ao ler o arquivo");
            e.printStackTrace();
            System.exit(0);
        }
    }
    
    public void lerJogadores() {
        System.out.print("Digite o número de jogadores: ");
        int numJogadores = sc.nextInt();
        sc.nextLine();
        jogadores = new Jogador[numJogadores];
        for (int i = 0; i < numJogadores; ++i) {
            System.out.print("Nome do jogador " + (i + 1) + ": ");
            String nome = sc.nextLine();
            jogadores[i] = new Jogador(nome);
        }
    }
    
    public void realizaPartida() {
        cartasNaMesa = new FilaCartas();
        cartasDosAdversarios = new Carta[jogadores.length - 1];
        indicesDosAdversarios = new int[jogadores.length - 1];
        fazDistribuicaoInicial();
        numJogadoresAtivos = jogadores.length;
        jogadorDaRodada = new Random().nextInt(numJogadoresAtivos);
        while (numJogadoresAtivos > 1) {
            efetuaRodada();
            numJogadoresAtivos = verificaJogadoresAtivos();
        }
        anunciaVencedor();
    }

    private void anunciaVencedor() {
        System.out.println("\n\nVencedor da partida: " + jogadores[jogadorDaRodada].getNome() + "\n");
    }

    public void embaralha() {
        List<Carta> listCarta = Arrays.asList(cartas);
        Collections.shuffle(listCarta);
        listCarta.toArray(cartas);
    }
    
    public void embaralha2() {
        Random rand = new Random();
        for (int i = 0; i < cartas.length; ++i) {
            int indiceParaTroca = rand.nextInt(cartas.length);
            Carta aux = cartas[i];
            cartas[i] = cartas[indiceParaTroca];
            cartas[indiceParaTroca] = aux;
        }
    }
    
    public void fazDistribuicaoInicial() {
        embaralha2();
        FilaCartas[] filasJogadores = new FilaCartas[jogadores.length];
        for (int i = 0; i < jogadores.length; ++i) {
            filasJogadores[i] = new FilaCartas();
        }
        for (int i = 0; i < cartas.length; ++i) {
            int indJogador = i % jogadores.length;
            filasJogadores[indJogador].insereCarta(cartas[i]);
        }
        for (int i = 0; i < jogadores.length; ++i) {
            jogadores[i].recebeCartas(filasJogadores[i]);
        }
    }
    
    public int verificaJogadoresAtivos() {
        numJogadoresAtivos = 0;
        for (int i = 0; i < jogadores.length; ++i) {
            if (jogadores[i].estaAtivo()) {
                if (!jogadores[i].temCartas()) {
                    jogadores[i].setJogadorAtivo(false);
                } else {
                    ++numJogadoresAtivos;
                }
            }
        }
        return numJogadoresAtivos;
    }
    
    public void efetuaRodada() {
        recolheCartaDoJogadorDaRodada();
        exibeCartaDoJogadorDaRodada();
        recolheCartasDosAdversariosAtivos();
        exibeCartasDosAdversariosAtivos();
        identificaVencedorDaRodada();
        entregaCartasAoVencedorDaRodada();
    }

    private void identificaVencedorDaRodada() {
        int indVencedor = jogadorDaRodada;
        AtributoCarta atributoVencedor = cartaJogadorDaRodada.getAtributoAt(indiceDoAtributoParaComparar);
        for (int i = 0; i < numJogadoresAtivos - 1; ++i) {
            AtributoCarta atributoAdversario = cartasDosAdversarios[i].getAtributoAt(indiceDoAtributoParaComparar);
            if (atributoAdversario.comparaCom(atributoVencedor) == 1) {
                indVencedor = indicesDosAdversarios[i];
                atributoVencedor = atributoAdversario;
            }
        }
        jogadorDaRodada = indVencedor;
    }

    private String abreviaTexto(String texto, int limiteCaracteres) {
        if (texto.length() <= limiteCaracteres) {
            return texto;
        }
        return texto.substring(0, limiteCaracteres - 3) + "...";
    }
    
    private String centralizaTexto(String texto, int numCaracteres) {
        int tamanhoTexto = texto.length();
        int dif = numCaracteres - tamanhoTexto;
        StringBuilder textoCentralizado = new StringBuilder(numCaracteres);
        int tamanhoPrefixo = dif / 2;
        for (int i = 0; i < tamanhoPrefixo; ++i) {
            textoCentralizado.append(' ');
        }
        textoCentralizado.append(texto);
        int tamanhoSufixo = dif - tamanhoPrefixo;
        for (int i = 0; i < tamanhoSufixo; ++i) {
            textoCentralizado.append(' ');
        }
        return textoCentralizado.toString();
    }
    
    private int maxCaracteresNomeAtributo() {
        int max = 18;
        // a fazer: deve retornar o número de caracteres do nome de atributo mais longo do modelo de cartas em uso
        return max;
    }
    
    /*private String preencheTextoDireita(String texto, int numCaracteres, char caractere) {
        StringBuilder novoTexto = new StringBuilder();
        if (texto != null && texto.length() <= numCaracteres) {
            novoTexto.append(texto);
        }
        int tamanhoTexto = texto != null ? texto.length() : 0;
        for (int i = 0; i < numCaracteres - tamanhoTexto; ++i) {
            novoTexto.append(caractere);
        }
        return novoTexto.toString();
    }*/
    
    public void exibeCartaDoJogadorDaRodada() {
        System.out.println("Jogador.......: " + jogadores[jogadorDaRodada].getNome());
        System.out.println("                +------------------+");
        String nome = centralizaTexto(cartaJogadorDaRodada.getNome(), maxCaracteresNomeAtributo());
        if (nome.length() > maxCaracteresNomeAtributo()){
            nome = abreviaTexto(nome, maxCaracteresNomeAtributo());
        } 
        System.out.println("   Carta......: |" + nome + "|");
        System.out.println("                +------------------+");
        for (int i = 0; i < modelo.getNumAtributos(); i++) {
            System.out.printf("%d %-14s%s%9d%10s", (i + 1), modelo.getAtributoAt(i).getNome(), "|",cartaJogadorDaRodada.getAtributoAt(i).getValor(), "|");
            System.out.println();
        }
        System.out.println("                +------------------+");
        System.out.println();
        System.out.print("Escolha um atributo de 1 a " + Integer.toString(modelo.getNumAtributos()) + ": ");
        indiceDoAtributoParaComparar = sc.nextInt() - 1;
        sc.nextLine();
    }

    private void exibeCartasDosAdversariosAtivos() {   
        System.out.println("\n=========================================================");
        System.out.print("Jogador.......: ");
        for (int i = 0; i < numJogadoresAtivos - 1; i++) {
            System.out.printf("%s" , centralizaTexto(jogadores[indicesDosAdversarios[i]].getNome(), 23));
        }
        System.out.println("");
        for (int i = 0; i < numJogadoresAtivos - 1; i++){
            if(i == 0){
                System.out.printf("%37s", "+------------------+");
            } else{
                System.out.printf(" %s", "+------------------+");
            }
        }        
        System.out.print("\n   Carta......: ");
        for (int i = 0; i < numJogadoresAtivos - 1; i++) {
            String nome = centralizaTexto(cartasDosAdversarios[i].getNome(), maxCaracteresNomeAtributo());
            if (nome.length() > maxCaracteresNomeAtributo()){
                nome = abreviaTexto(nome, maxCaracteresNomeAtributo());
            } 
        System.out.printf( "%s%s%s","|", nome,"| "); 
        }
        System.out.println("");
        for (int i = 0; i < numJogadoresAtivos - 1; i++){
            if(i == 0){
                System.out.printf("%37s", "+------------------+");
            } else{
                System.out.printf(" %s", "+------------------+");
            }
        }
        System.out.println("");
        for (int i = 0; i < modelo.getNumAtributos(); i++){
            System.out.printf("%d %-15s", (i + 1), modelo.getAtributoAt(i).getNome());
            for(int j = 0; j< numJogadoresAtivos - 1; j++){
                System.out.printf("%s%10s%11s ", "|", cartasDosAdversarios[j].getAtributoAt(i).getValor(), "|");
            }
            System.out.println(""); 
        }
        for (int i = 0; i < numJogadoresAtivos - 1; i++){
            if(i == 0){
                System.out.printf("%37s", "+------------------+");
            } else{
                System.out.printf(" %s", "+------------------+");
            }
        }
        System.out.println("\n=========================================================");
    }
    
    public void recolheCartaDoJogadorDaRodada() {
        cartaJogadorDaRodada = jogadores[jogadorDaRodada].jogaCarta();
        cartasNaMesa.insereCarta(cartaJogadorDaRodada);
    }
    
    public void recolheCartasDosAdversariosAtivos() {
        int indAdversarios = 0;
        for (int i = 0; i < jogadores.length; ++i) {
            if (i != jogadorDaRodada && jogadores[i].estaAtivo()) {
                indicesDosAdversarios[indAdversarios] = i;
                cartasDosAdversarios[indAdversarios] = jogadores[i].jogaCarta();
                cartasNaMesa.insereCarta(cartasDosAdversarios[indAdversarios]);
                ++indAdversarios;
            }
        }
    }
    
    public void entregaCartasAoVencedorDaRodada() {
        jogadores[jogadorDaRodada].recebeCartas(cartasNaMesa);
    }

   
}
