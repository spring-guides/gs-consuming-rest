package com.example.consumingrest.service;

import com.example.consumingrest.dao.QuoteDao;
import com.example.consumingrest.model.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private QuoteDao quoteDao;

    private final String url= "http://jsonplaceholder.typicode.com/posts/" ;
    //structure: https://jsonplaceholder.typicode.com/posts
/*    {
        "userId": 6,
            "id": 60,
            "title": "consequatur placeat omnis quisquam quia reprehenderit fugit veritatis facere",
            "body": "asperiores sunt ab assumenda cumque modi velit\nqui esse omnis\nvoluptate et fuga perferendis voluptas\nillo ratione amet aut et omnis"
    },*/

    public String getPostPlainString() {
       return this.restTemplate.getForObject(url,String.class);
    }


   public Quote getQuote(int id) {

        Quote quote = quoteDao.findById(id);

        if(quote == null){
            quote = this.restTemplate.getForObject(url+id,Quote.class);
            quoteDao.save(quote);
            System.out.println("from site");
        }

        return quote;
    }


}
