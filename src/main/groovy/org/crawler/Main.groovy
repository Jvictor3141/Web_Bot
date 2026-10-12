package org.crawler

class Main {
    static void main(String[] args) {
        def client = new AnsClient()

        def home = client.fetch('https://www.gov.br/ans/pt-br')
        String urlPrestador = client.findLink(home, 'Espaço do Prestador de Serviços de Saúde')
        println "Prestador: ${urlPrestador}"

        def paginaPrestador = client.fetch(urlPrestador)
        println "Título: ${paginaPrestador.title()}"

        String urlTiss = client.findLink(paginaPrestador, 'Padrão para Troca de Informação de Saúde Suplementar')
        println "TISS: ${urlTiss}"

        def paginaTiss = client.fetch(urlTiss)
        println "Título: ${paginaTiss.title()}"

        def versao = TissVersion.findLatest(paginaTiss)
        println "Versão mais recente: ${versao.rotulo}"
        println "URL: ${versao.url}"

        def paginaVersao = client.fetch(versao.url)
        def componentes = TissComponents.parse(paginaVersao)
        componentes.each { println "${it.nome} | ${it.versao} | ${it.url}" }

        def comunicacao = TissComponents.findByName(componentes, 'Comunica')
        println "Componente de Comunicação: ${comunicacao.url}"

        File pasta = new File('Downloads/Aquivos_padrao_TISS')
        File arquivo = client.download(comunicacao.url, pasta)

        println "Salvo em: ${arquivo.absolutePath}"
        println "Tamanho: ${arquivo.length()} bytes"

        String urlHistorico = client.findLink(paginaTiss, 'todas as versões dos Componentes')
        def paginaHistorico = client.fetch(urlHistorico)

        List<TissHistoryEntry> historico = TissHistory.parse(paginaHistorico)
        File csv = new File('Downloads/historico_versoes_tiss.csv')
        TissHistory.writeCsv(historico, csv)

        println "Task 2: ${historico.size()} versões a partir de jan/2016 -> ${csv.absolutePath}"

        String urlTabelas = client.findLink(paginaTiss, 'acessar as planilhas')
        def paginaTabelas = client.fetch(urlTabelas)

        String urlErros = client.findLink(paginaTabelas, 'Tabela de erros no envio para a ANS')
        File erros = client.download(urlErros, new File('Downloads/Tabelas_relacionadas'))

        println "Task 3: ${erros.absolutePath} (${erros.length()} bytes)"
    }
}