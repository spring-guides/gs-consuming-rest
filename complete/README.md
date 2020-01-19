Adaptation of tutorial https://spring.io/guides/gs/consuming-rest/ for use with <link> https://swapi.co/</link>  
Notes:
* additional method SimpleClientHttpRequestFactory (with helpers) needed to compensate for proxy

###To do: 
* separate coding - as per company standards - into: 
  * service
  * web.controller: provides the web pages
  * dao provides CRUD interface
  * model: structures the json data 
   (see SDZ_Microcommerce)
    
* create structure for data from site ~~SWAPI~~ http://jsonplaceholder.typicode.com/
* display data in browser
* save data to db
  * upon next request, search this db first, then remote site 
* learn to use this:  
  @Value("${someClass.config-test.someProperty:value}")  
  private String configVersion; 


