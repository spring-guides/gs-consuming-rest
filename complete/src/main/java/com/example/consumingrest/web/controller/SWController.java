package com.example.consumingrest.web.controller;

import com.example.consumingrest.dao.QuoteDao;
import com.example.consumingrest.model.Quote;
import com.example.consumingrest.service.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SWController {

   @Autowired
   private QuoteDao quoteDao;

   @Autowired
   private RestService restService;

   @GetMapping(value = "/jsonPosts")
   public String getStringFromJSON(){
      return restService.getPostPlainString();
   }


 @RequestMapping(value = "/quotes",method = RequestMethod.GET)
 public List<Quote> listeQuote(){
    return quoteDao.findAll();
 }

   @GetMapping(value = "/getquote/{id}")
   public Quote getQuotebyId(@PathVariable int id){
     return restService.getQuote(id);

   }


/*   @PutMapping(value = "/ajout")
   public void ajoutQuote(@RequestBody Quote quote){
      quoteDao.save(quote);
   }*/


   @GetMapping(value="/test")
   public String voirDeQuoi() {
      return "voir de quoi";
   }


}
