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
    }
}