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
        def link = doc.select('a[href]').find { it.text().contains(textoDoLink) }

        if (link == null) {
            throw new IllegalStateException("Link não encontrado: '${textoDoLink}'")
        }
        return link.absUrl('href')
    }
}