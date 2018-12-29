import java.util.ArrayList;
import java.util.Random;
import java.lang.StringBuilder;
import java.lang.Math;

class Populacao {
    private ArrayList<Elemento> element = new ArrayList(); // Arraylist que irá guardar cada membro da população
    private char letra[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
            's', 't', 'u', 'v', 'w', 'x', 'y', 'z', ' ' }; // Caracteres auxiliares
    private ArrayList<Elemento> matingPool = new ArrayList();
    private ArrayList<Elemento> filho = new ArrayList();
    private double mediaFit;

    // Construtor que cria um array com vários elementos aleatórios
    public Populacao(int quantidade, String palavraDesejada) {
        for (int i = 0; i < quantidade; i++) {
            Random rand = new Random(); // Sorteia um número aleatório entre 0 e 26
            int aux;
            StringBuilder auxiliar = new StringBuilder(); // Cria uma string auxiliar que irá formar as palavras
                                                          // aleatórias
            for (int j = 0; j < palavraDesejada.length(); j++) {
                aux = rand.nextInt(27);
                auxiliar.append(this.letra[aux]);
            }
            // Agora, vamos criar um objeto do tipo elemento
            Elemento elem = new Elemento();
            elem.setDna(auxiliar.toString());
            // Agora, vamos adicionar o elemento na população
            this.element.add(elem);
        }
    }

    // Printa todos os elementos da população na tela
    public void printa_elementos() {
        int aux = -1;
        double auxFit = -1;
        for (int i = 0; i < element.size(); i++) {
            System.out.println("ELEMENTO: " + element.get(i).getDna() + "  || FITNESS: " + Math.pow(element.get(i).getFitness(), 0.5));
            if (element.get(i).getFitness() > auxFit) {
                auxFit = element.get(i).getFitness();
                aux = i;
            }
        }
        if (aux != -1) {
            System.out.println("****************************************************");
            System.out.println(
                    "MELHOR ELEMENTO: " + element.get(aux).getDna() + "  || FITNESS: " + Math.pow(element.get(aux).getFitness(), 0.5));
            System.out.println("****************************************************");
        }
        media_fit();
    }

    // Método para calcula a média de adequação da população total
    public void media_fit() {
        for (int i = 0; i < element.size(); i++) {
            this.mediaFit += element.get(i).getFitness();
        }
        this.mediaFit = this.mediaFit / element.size();
        System.out.println("*******************************");
        System.out.println("Media de Acerto: " + Math.pow((this.mediaFit), 0.5));
        System.out.println("********************************");
    }

    // Método Gambiarra para limpar a tela
    public void limpa_tela() {
        for (int i = 0; i < 20; i++)
            System.out.println(" ");
    }

    // Calcula a adequação de um elemento ao elemento desejado
    public void calculateFitness(int quantidade, String palavraDesejada) {
        char nomeDesejado[] = palavraDesejada.toCharArray();
        int score = 0; // Cada vez que um caractere bater com o desejado, soma +1
        for (int i = 0; i < quantidade; i++) {
            char nome[] = (this.element.get(i).getDna()).toCharArray(); // Convete a string para um array de char
            if (nome.length == nomeDesejado.length) // Confere se o tamanho dos nomes é realmente o mesmo
            {
                for (int j = 0; j < nome.length; j++) {
                    if (nome[j] == nomeDesejado[j]) {
                        score++;
                    }
                }
            } else {
                System.out.print("ERRO NO TAMANHO");
                break;
            }

            double fitness = ((double) score / (double) palavraDesejada.length()) * 100;
            fitness = Math.pow(fitness, 2); //Eleva o fitness ao quadrado para que a curva de adequação passe a ser exponencial
            this.element.get(i).setFitness(Math.floor(fitness));
            score = 0; // Reinicia a contagem para a próxima palavra
        }
    }

    // Gera um novo arraylist levando em conta as proporções do fitness. Quanto
    // maior o fitness, mais vezes aparece o elemento
    public void natural_selection(int quantidade, String palavraDesejada) {
        for (int i = 0; i < quantidade; i++) {
            int aux = (int) (this.element.get(i).getFitness());

            for (int n = 0; n < aux; n++) // Repete o elemento N vezes dentro do novo arraylist, sendo N proporcional ao
                                          // fitness do elemento
            {
                this.matingPool.add(this.element.get(i));
            }
        }
    }

    // Função que gera a próxima geração, realizando o crossover entre dois pais
    public void copulate() {
        while (filho.size() != element.size()) {
            Random random = new Random(); // Sorteia o índice do pai e da mãe que será usado
            int tam = matingPool.size();
            int auxPai = random.nextInt(tam);
            int auxMae = random.nextInt(tam);
            crossover(auxPai, auxMae);
        }
    }

    // Função que faz o crossover do pai e da mae
    public void crossover(int auxPai, int auxMae) {
        char pai[] = (matingPool.get(auxPai).getDna()).toCharArray(); // Transforma o nome do pai e da mae em um array
                                                                      // de char
        char mae[] = (matingPool.get(auxMae).getDna()).toCharArray();

        // Vamos pegar metade do nome do pai e metade do nome da mãe
        StringBuilder auxiliar = new StringBuilder();
        int med = (int) (Math.floor((pai.length) / 3));
        if (matingPool.get(auxMae).getFitness() >= matingPool.get(auxPai).getFitness()) // Se o fitness da mae for
                                                                                        // maior, ela passa mais genes
                                                                                        // aos filhos
        {
            for (int i = 0; i < med; i++) {
                auxiliar.append(mae[i]);
            }
            for (int k = med; k < 2 * med; k++) {
                auxiliar.append(pai[k]);
            }
            for (int j = 2 * med; j < pai.length; j++) {
                auxiliar.append(mae[j]);
            }

        } else if (matingPool.get(auxPai).getFitness() > matingPool.get(auxMae).getFitness()) // Se o fitness do pai for
                                                                                              // maior, ele passa mais
                                                                                              // genes aos filhos
        {
            for (int i = 0; i < med; i++) {
                auxiliar.append(pai[i]);
            }
            for (int k = med; k < 2 * med; k++) {
                auxiliar.append(pai[k]);
            }
            for (int j = 2 * med; j < pai.length; j++) {
                auxiliar.append(mae[j]);
            }

        }

        Elemento aux = new Elemento();
        aux.setDna(auxiliar.toString());
        mutate(aux, 0.01); // Faz a mutação. O segundo parâmetro é a porcentagem de mutação desejada
    }

    // Função que realiza a mutação
    public void mutate(Elemento temp, double rate) {
        double auxTaxa = (rate * 100);
        int taxa = (int) auxTaxa;

        // Cria um vetor que irá auxiliar no uso da portentagem escolhida. Ele valerá 1
        // "rate%" vezes
        int probabilidades[];
        probabilidades = new int[100];
        for (int i = 0; i < 100; i++) {
            probabilidades[i] = 0;
        }
        for (int i = 0; i < taxa; i++) {
            probabilidades[i] = 1;
        }

        Random random = new Random();
        int indice, ind2; // Variável auxiliar para sortear um indice que valerá 0 ou 1
        char aux[] = (temp.getDna()).toCharArray(); // Converte o elemento para um array de char

        for (int i = 0; i < aux.length; i++) {
            indice = random.nextInt(100);
            if (probabilidades[indice] == 1) // Se valer 1, realiza uma mutação, escolhendo um novo valor para o
                                             // caractere
            {
                ind2 = random.nextInt(27);
                aux[i] = this.letra[ind2];
            }
        }
        Elemento aux2 = new Elemento();
        String auxString = new String(aux);
        aux2.setDna(auxString);
        filho.add(aux2);
    }

    // Função que gera o loop evolutivo(função recursiva)
    public void evolui(int quantidade, String palavraDesejada) {
        int gen = 0;
        while (gen <= 2000) {
            gen++;
            calculateFitness(quantidade, palavraDesejada);

            printa_elementos();

            this.mediaFit = 0;
             System.out.println("*********");
             System.out.println("GERACAO: " + gen);
             System.out.println("*********");
            limpa_tela();
            try {
                Thread.sleep(0);
            } catch (InterruptedException ex) {
            } 

            natural_selection(quantidade, palavraDesejada); // Seleciona os elementos que vão para a próxima geração
            copulate(); // Gera os elementos da próxima geração
            matingPool.clear();
            // Até aqui, todos os novos elementos estão no arraylist filho. Vamos passa-los
            // para o arraylist principal
            element.clear();
            for (int i = 0; i < filho.size(); i++) // Adiciona a próxima geração no arraylist principal
            {
                element.add(filho.get(i));
            }
            filho.clear(); // Limpa o arraylist de filhos
        }
    }
}