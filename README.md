# Web Bot — Web Crawler e Web Scraping com Groovy

Projeto desenvolvido para praticar **Web Crawling e Web Scraping**, automatizando a navegação entre páginas do site da Agência Nacional de Saúde Suplementar (ANS) e a extração de informações relacionadas ao Padrão TISS.

A aplicação percorre páginas da ANS, identifica links relevantes, extrai dados de tabelas HTML, realiza downloads de arquivos e exporta informações históricas para CSV.

## Funcionalidades

- **Navegação automatizada:** percorre páginas da ANS a partir de links identificados pelo texto.
- **Requisições HTTP:** realiza requisições para obter o conteúdo das páginas.
- **Análise de HTML:** utiliza Jsoup para interpretar documentos HTML e localizar elementos.
- **Identificação de versões:** identifica a versão mais recente do Padrão TISS com base nos links encontrados na página.
- **Extração de componentes:** coleta nomes, versões e URLs dos componentes disponibilizados pela ANS.
- **Download de arquivos:** baixa arquivos relacionados ao Padrão TISS e às tabelas da ANS.
- **Histórico de versões:** extrai registros históricos a partir de janeiro de 2016 e exporta os resultados para um arquivo CSV.

## Tecnologias utilizadas

- **Groovy:** implementação da lógica de crawling, scraping e processamento dos dados.
- **Java 17:** plataforma de execução do projeto.
- **Gradle:** gerenciamento de dependências e execução da aplicação.
- **HTTPBuilder:** realização de requisições HTTP e download de arquivos.
- **Jsoup:** análise do HTML e seleção de elementos por meio de seletores CSS.

## Como funciona

O projeto executa três tarefas principais:

### 1. Download do componente de comunicação

A aplicação acessa o portal da ANS, navega até a página do Padrão TISS, identifica a versão mais recente e localiza o componente de comunicação para realizar seu download.

### 2. Extração do histórico de versões

O crawler identifica a página com o histórico dos componentes TISS, extrai informações como competência, publicação e início de vigência, filtra os registros a partir de janeiro de 2016 e salva os dados em CSV.

### 3. Download da tabela de erros

A aplicação acessa a página de planilhas relacionadas ao Padrão TISS, localiza a tabela de erros no envio para a ANS e realiza o download do arquivo.

## Estrutura do projeto

```text
Web_Bot/
├── src/
│   └── main/
│       └── groovy/
│           └── org/
│               └── crawler/
│                   ├── Main.groovy
│                   ├── AnsClient.groovy
│                   ├── TissVersion.groovy
│                   ├── TissComponents.groovy
│                   ├── TissComponent.groovy
│                   ├── TissHistory.groovy
│                   ├── TissHistoryEntry.groovy
│                   └── Competencia.groovy
├── build.gradle
├── settings.gradle
├── gradlew
└── gradlew.bat
```

### Responsabilidade das classes

| Classe | Responsabilidade |
|---|---|
| `Main` | Coordena a execução das tarefas do crawler. |
| `AnsClient` | Realiza requisições HTTP, localiza links e baixa arquivos. |
| `TissVersion` | Identifica a versão mais recente do Padrão TISS. |
| `TissComponents` | Extrai componentes e permite localizar um componente pelo nome. |
| `TissComponent` | Representa os dados de um componente TISS. |
| `TissHistory` | Extrai o histórico de versões e gera o arquivo CSV. |
| `TissHistoryEntry` | Representa um registro do histórico. |
| `Competencia` | Responsável pelo processamento das competências. |

## Pré-requisitos

- Java Development Kit (JDK) 17 ou superior.
- Git.
- Acesso à internet para consultar as páginas da ANS e baixar os arquivos.

O Gradle Wrapper já está incluído no repositório.

## Como executar

Clone o repositório:

```bash
git clone https://github.com/Jvictor3141/Web_Bot.git
cd Web_Bot
```

Execute o projeto:

**Linux / macOS**

```bash
./gradlew run
```

## Arquivos gerados

Durante a execução, o projeto cria arquivos nos seguintes diretórios:

```text
Downloads/
├── Aquivos_padrao_TISS/
├── Tabelas_relacionadas/
└── historico_versoes_tiss.csv
```

O arquivo CSV contém as colunas `competencia`, `publicacao` e `inicio_vigencia`, separadas por ponto e vírgula.

## Objetivos de aprendizagem

Este projeto foi desenvolvido para praticar conceitos de:

- Web Crawling e Web Scraping.
- Requisições HTTP e obtenção de documentos HTML.
- Seletores CSS e manipulação de elementos HTML.
- Expressões regulares e normalização de texto.
- Processamento e filtragem de dados.
- Manipulação de arquivos e exportação para CSV.
- Organização de código em classes com responsabilidades específicas.
- Gerenciamento de dependências e execução com Gradle.

## Autor

Desenvolvido por [Jvictor3141](https://github.com/Jvictor3141).

