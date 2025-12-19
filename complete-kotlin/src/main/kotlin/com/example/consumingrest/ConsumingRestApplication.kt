package com.example.consumingrest

import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.web.client.RestClient
import org.springframework.web.client.toEntity

private val log = LoggerFactory.getLogger(ConsumingRestApplication::class.java)

@SpringBootApplication
class ConsumingRestApplication {

  @Bean
  @Profile("!test")
  fun run(builder: RestClient.Builder) = ApplicationRunner {
    val quote = builder.build().get().uri("http://localhost:8080/api/random")
      .retrieve().toEntity<Quote>()
    log.info(quote.toString())
  }
}

fun main(args: Array<String>) {
  runApplication<ConsumingRestApplication>(*args)
}
