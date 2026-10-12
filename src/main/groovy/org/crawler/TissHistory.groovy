package org.crawler

import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

import java.time.YearMonth

class TissHistory {
    static final YearMonth A_PARTIR_DE = YearMonth.of(2016, 1)

    static List<TissHistoryEntry> parse(Document doc) {
        List<TissHistoryEntry> resultado = []

        for (Element tabela : doc.select('table')) {
            def linhas = tabela.select('tr')
            if (linhas.isEmpty()) continue

            List<String> cabecalho = linhas[0].select('th, td')*.text()
            Map<String, Integer> col = mapearColunas(cabecalho)
            if (col == null) continue

            println "Cabeçalho encontrado: ${cabecalho}"

            for (Element tr : linhas.drop(1)) {
                List<String> celulas = tr.select('th, td')*.text()*.trim()
                if (celulas.size() <= col.values().max()) continue

                String competencia = celulas[col.competencia]
                YearMonth periodo = Competencia.parse(competencia)
                if (periodo == null) {
                    throw new IllegalStateException(
                            "Competência em formato desconhecido: '${competencia}' (linha: ${celulas})")
                }

                if (!periodo.isBefore(A_PARTIR_DE)) {
                    resultado << new TissHistoryEntry(
                            competencia   : competencia,
                            publicacao    : celulas[col.publicacao],
                            inicioVigencia: celulas[col.inicio],
                            periodo       : periodo
                    )
                }
            }
        }

        if (resultado.isEmpty()) {
            throw new IllegalStateException(
                    'Nenhuma linha do histórico encontrada (tabela ausente ou colunas com outros nomes)')
        }
        return resultado
    }

    static Map<String, Integer> mapearColunas(List<String> cabecalho) {
        List<String> nomes = cabecalho*.toLowerCase()
        int c = nomes.findIndexOf { it.contains('compet') }
        int p = nomes.findIndexOf { it.contains('public') }
        int v = nomes.findIndexOf { it.contains('vig') }

        if (c < 0 || p < 0 || v < 0) return null
        return [competencia: c, publicacao: p, inicio: v]
    }

    static void writeCsv(List<TissHistoryEntry> entradas, File destino) {
        destino.parentFile?.mkdirs()

        List<String> linhas = ['competencia;publicacao;inicio_vigencia']
        entradas.each {
            linhas << [it.competencia, it.publicacao, it.inicioVigencia].collect { escapar(it) }.join(';')
        }
        destino.write(linhas.join('\n') + '\n', 'UTF-8')
    }

    static String escapar(String valor) {
        if (valor.contains(';') || valor.contains('"') || valor.contains('\n')) {
            return '"' + valor.replace('"', '""') + '"'
        }
        return valor
    }
}
