package org.crawler

import org.jsoup.nodes.Document

class TissVersion {

    static final Map MESES = [
            janeiro: 1, fevereiro: 2, março: 3, abril: 4, maio: 5, junho: 6, julho: 7, agosto: 8, setembro: 9, outubro: 10, novembro: 11, dezembro: 12
    ]

    static final String PADRAO_VERSAO = /versão\s+(\p{L}+)\/(\d{4})/

    static Map findLatest(Document doc) {
        List<Map> candidatos = []

        doc.select('a[href]').each { link ->
            def matcher = link.text()=~ PADRAO_VERSAO

            if (matcher.find()) {
                String mes = matcher.group(1).toLowerCase()
                int ano = matcher.group(2) as int
                Integer numeroMes = MESES[mes]

                if (numeroMes != null) {
                    candidatos << [
                            rotulo: "${matcher.group(1)}/${ano}",
                            url   : link.absUrl('href'),
                            ordem : ano * 100 + numeroMes
                    ]
                }
            }
        }

        if (candidatos.isEmpty()) {
            throw new IllegalStateException('Nenhum link de versão do Padrão TISS encontrado')
        }

        return candidatos.max { Map c -> c.ordem }
    }
}
