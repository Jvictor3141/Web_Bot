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
    }
}