package com.sber.filters

import org.springframework.core.annotation.Order
import java.time.Instant
import java.util.logging.Logger
import javax.servlet.FilterChain
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Order(1)
@WebFilter(urlPatterns = ["/*"])
class LogFilter: HttpFilter() {
    override fun doFilter(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        LOG.info("Время ${Instant.now()} " +
                "Тип ${request.method} " +
                "Протокол ${request.protocol} " +
                "Порт ${request.serverPort} " +
                "URL ${request.requestURL}" )
        super.doFilter(request, response, chain)
    }

    companion object {
        val LOG: Logger = Logger.getLogger(LogFilter::class.java.name)
    }
}