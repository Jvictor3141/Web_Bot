package org.crawler

import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class AnsClient {

    static final String USER_AGENT = 'Mozilla/5.0 (projeto de estudo)'

    Document fetch(String url) {
        def http = new HTTPBuilder(url)
        http.headers = ['User-Agent': USER_AGENT]

        String html = http.get(contentType: ContentType.TEXT) { resp, reader ->
            reader.text
        }

        return Jsoup.parse(html, url)
    }

    String findLink(Document doc, String textoDoLink) {
        String procurado = normalizar(textoDoLink)
        def link = doc.select('a[href]').find { normalizar(it.text()).contains(procurado) }

        if (link == null) {
            throw new IllegalStateException("Link não encontrado: '${textoDoLink}'")
        }
        return link.absUrl('href')
    }

    static String normalizar(String texto) {
        String semAcento = java.text.Normalizer
                .normalize(texto, java.text.Normalizer.Form.NFD)
                .replaceAll('\\p{M}', '')
        return semAcento.replace('\u00a0', ' ')
                .replaceAll('\\s+', ' ')
                .trim()
                .toLowerCase()
    }

    File download(String url, File pasta) {
        pasta.mkdirs()
        File destino = new File(pasta, fileNameFromUrl(url))

        def http = new HTTPBuilder(url)
        http.headers = ['User-Agent': USER_AGENT]

        http.get(contentType: ContentType.BINARY) { resp, inputStream ->
            destino.withOutputStream { saida -> saida << inputStream}
        }

        return destino
    }

    static String fileNameFromUrl(String url) {
        String limpa = url.replaceAll('[?#].*$', '').replaceAll('/@@download/file$', '')
        String nome = limpa.substring(limpa.lastIndexOf('/') + 1)
        return URLDecoder.decode(nome, 'UTF-8')
    }
}