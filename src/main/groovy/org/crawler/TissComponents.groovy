package org.crawler

import org.jsoup.nodes.Document

class TissComponents {
    static List<TissComponent> parse(Document doc) {
        List<TissComponent> componentes = []

        doc.select('table tr').each { tr ->
            def celulas = tr.select('td')
            def link = tr.selectFirst('a[href]')

            if (celulas.size() >= 3 && link != null) {
                componentes << new TissComponent(
                        nome  : celulas[0].text().trim(),
                        versao: celulas[1].text().trim(),
                        url   : link.absUrl('href')
                )
            }
        }
        return componentes
    }

    static TissComponent findByName(List<TissComponent> componentes, String trecho) {
        def achado = componentes.find { it.nome.toLowerCase().contains(trecho.toLowerCase()) }

        if (achado == null) {
            throw new IllegalStateException("Componente não encontrado: '${trecho}'")
        }
        return achado
    }
}
