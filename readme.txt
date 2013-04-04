#####################
Nikolas Moya - 144678
#####################

javac /src/main/*.java /src/pckThread/*.java

A execução do programa começa em Begin.java, dentro da pasta main.
Para todas as execuções, é definido um número bem alto de iterações ao invés do while(true). Ao final da execução, é impresso um vetor de quantas vezes cada filósofo comeu. Com este vetor, foi possível concluir se o algorito tem inanição ausente ou presente.
Impressões de quando um filósofo começou e parou de comer são exibidas para verificar que em nenhum cenário, dois filósofos consecutivos comem simultaneamente.

----------------------------------------------------------------------------------------------------------------------------------
1- Execução com N filósofos e com Deadlock. 
Abordagem onde o filósofo tenta segurar um garfo separadamente e somente quando conseguir pegar o segundo garfo vai comer. Eventualmente, cada filósofo segurará um garfo, causando a dependência de um filósofo soltar o seu garfo para que outro filósofo possa comer. Cada filósofo fica esperando o próximo. Deadlock.


2- Execução sem deadlock e sem inanição para N threads. 
É a junção dos itens 2, 3 e 4 do exercício proposto no livro. Foi utilizado o código do Herlihy para a implementação da barreira pelo algoritmo da padaria. Neste modelo de execução, cada filósofo tenta segurar os dois garfos para comer, se não for possível, nenhum garfo é segurado. Como o algoritmo de exclusão mútua já trata Inanição, é garantido que todos os filósofos comam.


3- Execução sem deadlock e com inanição para N threads. 
Implementado por curiosidade, exatamente o mesmo algoritmo do item 2 foi utilizado, nenhuma linha a mais alterada. Ao invés de utilizar as barreiras do algoritmo da Padaria, foi utilizado a classe 'semaphore' do Java. O cenário de filósofos passando fome é comum.