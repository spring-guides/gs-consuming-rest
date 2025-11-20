package com.example.consumingrest

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject

private val log = LoggerFactory.getLogger(ConsumingRestApplication::class.java)

@SpringBootApplication
class ConsumingRestApplication {

    @Bean
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate {
        return builder.build()
    }

    @Bean
    @Profile("!test")
    fun run(restTemplate: RestTemplate) = CommandLineRunner {
        val quote = restTemplate.getForObject<Quote>("http://localhost:8080/api/random")
        log.info(quote.toString())
    }
}

fun main(args: Array<String>) {
    runApplication<ConsumingRestApplication>(*args)
}
