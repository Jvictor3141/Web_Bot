package org.crawler

import java.time.YearMonth

class Competencia {
    static final Map<String, Integer> MESES = [
            jan: 1, fev: 2, mar: 3, abr: 4, mai: 5, jun: 6,
            jul: 7, ago: 8, set: 9, out: 10, nov: 11, dez: 12
    ]

    static YearMonth parse(String texto) {
        String t = texto.trim().toLowerCase()

        def m = t =~ /(?<!\d)(\d{4})(\d{2})(?!\d)/          // 202601
        if (m.find()) return criar(m.group(1) as int, m.group(2) as int)

        m = t =~ /(\d{1,2})\s*[\/\-]\s*(\d{4})/             // 01/2016
        if (m.find()) return criar(m.group(2) as int, m.group(1) as int)

        m = t =~ /(\d{4})\s*[\/\-]\s*(\d{1,2})/             // 2016/01
        if (m.find()) return criar(m.group(1) as int, m.group(2) as int)

        m = t =~ /(\p{L}{3,})\D*(\d{4})/                    // jan/2016, janeiro de 2016
        if (m.find()) {
            Integer mes = MESES[m.group(1).substring(0, 3)]
            if (mes != null) return criar(m.group(2) as int, mes)
        }
        return null
    }

    private static YearMonth criar(int ano, int mes) {
        return (mes in 1..12) ? YearMonth.of(ano, mes) : null
    }
}
