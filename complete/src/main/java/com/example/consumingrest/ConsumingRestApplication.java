package com.example.consumingrest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

@SpringBootApplication
public class ConsumingRestApplication {

	private static final Logger log = LoggerFactory.getLogger(ConsumingRestApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ConsumingRestApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {

		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		InetSocketAddress address = new InetSocketAddress(
				"proxy-internet-vol.pub.desjardins.com", 8080);
		Proxy proxy = new Proxy(Proxy.Type.HTTP, address);
		factory.setProxy(proxy);

		RestTemplate restTemplate = new RestTemplate();
		 restTemplate.setRequestFactory(factory);
		 return  restTemplate;
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {

			String url="https://swapi.co/api/planets/1";// "https://gturnquist-quoters.cfapps.io/api/random";

			String str=restTemplate.getForObject(url,String.class);
/*			Quote quote = restTemplate.getForObject(
					url, Quote.class);
			//log.info(quote.toString());*/
			System.out.println(str.toString());
		};
	}
}
