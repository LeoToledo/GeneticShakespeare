/*
    LEONARDO VINÍCIUS DE OLIVEIRA TOLEDO
    ENGENHARIA DE COMPUTAÇÃO - USP SÃO CARLOS

    Algoritmo evolutivo que busca formar a frase escrita pelo usuário
    
    Primeiramente, é criada uma população de N elementos aleatório. Para cada elemento é 
    calculada a taxa de aproximação da frase desejada que ele possui. 
    De acordo com essa taxa, os elementos que possuem maior adequação possuirão maior chance de avançar à próxima geração.
    Para se formar a próxima geração, é pego um elemento "pai" e um elemento "mae". Eles são "cruzados", de modo a formar um filho.
    Posteriormente à sua formação, o filho sofre uma mutação, que pode ou não ocorrer 
    (a taxa de probabilidade de ocorrer uma mutação está definida na linha 170 da classe População. O usuário pode alterá-la conforme desejado).

    Essa sequência de eventos entra em um Loop, que vai formando a cada iteração uma nova geração. O tamanho de gerações que serão feitas
    é definido na linha 212 da classe Populacao. O usuário também pode alterá-lo.

*/
class Main{
    public static void main(String[] args)
    {
        //Cria uma população aleatória
        Populacao pop = new Populacao(100, "kodex"); //Primeiro parâmetro: Quantidade de elementos da população a cada geração
                                                     //Segundo parâmetro: Nome ao qual se deseja que a população evolua
        pop.evolui(100, "kodex"); //Evolui a população
        
    }
}