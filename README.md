# TESTE DE NIVELAMENTO 💻

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Python](https://img.shields.io/badge/python-3670A0?style=for-the-badge&logo=python&logoColor=ffdd54)
![Vue.js](https://img.shields.io/badge/vuejs-%2335495e.svg?style=for-the-badge&logo=vuedotjs&logoColor=%234FC08D)

O projeto tem como objetivo responder as etapas do Teste de Nivelamento.

As tarefas número 1, 2 e 3 foram desenvolvidas em Java. Já a tarefa número 4 foi desenvolvida em Python e Vue.js. O arquivo .sql é compatível com PostgreSQL.

## 🚀 Primeiros passos

Para utilizar a aplicação localmente, é necessário ter instalado em sua máquina:

### Recomendado:

- Uma IDE compatível com Java (IntelliJ IDEA).

- Uma IDE compatível com Python e Vue.js (Visual Studio Code)

### Clolar:

Para clocar este projeto na sua máquina local:

```bash
    git clone https://github.com/victorzld/teste-nivelamento.git
```

## Configurar ambiente

Para testar o projeto localmente, é necessário instalar algumas bibliotecas. Serão elas:

- jopendocument

- jsoup

- opencsv

- Apache PDFBox

### Passo a passo de instalação:

Para executar as tarefas 1, 2 e 3:

Dentro do IntelliJ IDEA vá em:

- File -> Project Structure -> New Project Library -> Java -> Selecione os arquivos com as bibliotecas.

## O que o projeto faz?

Na pasta `teste-nivelamento-tarefa1-2-3` ao rodar o código da classe `Main` serão executados as classes `Teste1`, `Teste2` e `Teste3`.

- A classe `Teste1` realiza a tarefa 1. Acessa o site indicado no tópico 1.1, faz download dos anexos em PDF, cria automaticamente a pasta `/downloads` para armazenar os arquivos, compacta os arquivos baixados no ZIP `anexos.zip` e faz a verificação se os arquivos foram baixados corretamente antes de adicioná-los ao ZIP.

- A classe `Teste2` realiza a tarefa 2. Descompacta o arquivo ZIP e salva na pasta `/extracted`, lê o PDF e extrai os dados tabelados, substitiu as abreviações pelas descrições completas, conforme a legenda no rodapé, salva os dados em um arquivo CSV (e o nomeando de "Teste_LuizVictor.zip") e compacta o CSV em um novo arquivo ZIP.

- O pacote `Teste3` realiza a tarefa 3 em duas etapas, divididas em `TarefasDePreparacao1` e `TarefasDePreparacao2`. A classe `TarefasDePreparacao1` baixa os arquivos ZIP do site da ANS (como instruído no tópicos 3.1), extrai todos os arquivos CSV contidos nos arquivos ZIPs, unifica os dados dos arquivos de 2023 num único arquivo `/Demonstracoes_Contabeis_2023.csv` e unifica todos os dados dos arquivos de 2024 num único arquivo `/Demonstracoes_Contabeis_2024.csv`. Após isso, apaga os ZIPs que foram utilizados para extração. A classe `TarefasDePreparacao2` baixa automaticamente o arquivo CSV do site da ANS (como instruído no tópico 3.2), salva o arquivo na pasta `/downloadsDados`, substitui o arquivo existente caso já tenha sido baixado antes, e exibirá no console uma mensagem informando se o download foi bem sucedido ou se houve um erro.

Dentro da pasta `/teste-nivelamento/teste-nivelamento-tarefa1-2-3` tem o arquivo `script.sql` que executa as seguintes tarefas (como instruído nos tópicos 3.3, 3.4 e 3.5):

### 1 - Cria a tabela `operadoras` que:

    - Contém informações sobre operadoras de planos de saúde.

    -Campos: id (chave primária), registro_ans, nome_fantasia, razao_social, modalidade, uf, municipio e data_registro.

### 2 - Cria a tabela `demonstracoes_contabeis` que:

    - Contém informações financeiras das operadoras de planos de saúde.

    - Campos: id (chave primária), registro_ans, ano, trimestre, receita_total e despesas_eventos.

### 3 - Importa os dados (COPY):

    - operadoras: Importa dados do arquivo CSV Operadoras_Ativas_Na_ANS.csv para a tabela operadoras.

    - demonstracoes_contabeis:

        - Importa dados financeiros de 2023 do arquivo "Demonstracoes_Contabeis_2023.csv".

        - Importa dados financeiros de outro arquivo (provavelmente de 2024).

Dentro da pasta `teste-nivelamento-tarefa4` estão os códigos para execução da tarefa 4.

- Na subpasta `/teste-nivelamento-tarefa4/servidor` temos o arquivo `Relatorio_cadop.csv` que foi adquirido no tópico 3.2. Também está um código em Python que implementa um servidor HTTP simples (como instruído no tópico 4.2) que fornece uma API para buscar operadoras de planos de saúde com base no nome fantasia ou razão social. Este servidor utiliza o módulo `http.server` para criar o servidor e `csv` para carregar os dados do arquivo `Relatorio_cadop.csv`.

- Na subpasta `/teste-nivelamento-tarefa4/front-end` temos uma interface simples em Vue.js que interage com o servidor em Python, permite realizar buscar por operadoras de planos de saúde e exibe os resultados. Na interface, o usuário digita o nome no campo de entrada, ao cliclar em "Enter" ou no botão "Buscar", a função `buscar()` faz uma requisição para o servidor em Python `http://localhost:8000/buscar`. O servidor retorna uma lista de operadoras de saúde que contêm o termo pesquisado no nome. Os resultados serão exibidos numa interface abaixo, e se não houver resultados válidos, aparecerá uma mensagem "Nenhum resultado encontrado".

```bash

    Importante lembrar que, o servidor em Python deve estar rodando localmente para que as buscas via interface possam ser realizadas.
```

Por último, dentro da pasta `/teste-nivelamento` tem o arquivo `postman_collection.json` que realiza a tarefa 4.3.

- O arquivo `postman_collection.json` contém uma coleção do Postman chamada "Servidor Busca Operadoras", que permite testar a API para buscar operadoras de plano de saúde.

- Este arquivo foi criado para testar a API em Python que roda no `http.server` e busca operadoras no arquivo CSV `Relatorio_cadop.csv`. Ele facilita a verificação do funcionamento da API sem precisar de um navegador ou outro cliente HTTP.
