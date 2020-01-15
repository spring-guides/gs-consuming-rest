Adaptation of tutorial https://spring.io/guides/gs/consuming-rest/ for use with <link> https://swapi.co/</link>  
Notes:
* additional method SimpleClientHttpRequestFactory (with helpers) needed to compensate for proxy

###To do: 
* separate coding into: 
  * service
  * web.controller
  * dao
  * model  
  as per company standards (see SDZ_Microcommerce)
    
* create structure for data from site SWAPI
* display data as web page
* save data to db
  * upon next request, search this db first, then SWAPI  
* learn to use this:  
  @Value("${someClass.config-test.someProperty:value}")  
  private String configVersion; 


