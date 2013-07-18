<#assign project_id="gs-consuming-rest">

# Getting Started: Consuming a RESTful Web Service

What you'll build
-----------------

This guide walks you through the process of building an application that uses Spring's `RestTemplate` to consume a RESTful web service. Specifically, you'll use `RestTemplate` to retrieve a company's page data from Facebook's Graph API at:

    http://graph.facebook.com/gopivotal

What you'll need
----------------

 - About 15 minutes
 - <@prereq_editor_jdk_buildtools/>

## <@how_to_complete_this_guide jump_ahead='Fetch a REST resource'/>

<a name="scratch"></a>
Set up the project
------------------

<@build_system_intro/>

<@create_directory_structure_hello/>

### Create a Maven POM

    <@snippet path="pom.xml" prefix="initial"/>

<@bootstrap_starter_pom_disclaimer/>

<a name="initial"></a>
Fetch a REST resource
------------------------

With project setup complete, you can create a simple application that consumes a RESTful service.

Suppose that you want to find out what Facebook knows about Pivotal. Knowing that Pivotal has a page on Facebook and that the ID is "gopivotal", you should be able to query Facebook's Graph API via this URL:

    http://graph.facebook.com/gopivotal

If you request that URL through your web browser or curl, you'll receive a JSON document that looks something like this:

```javascript
{
   "id": "161112704050757",
   "about": "At Pivotal, our mission is to enable customers to build a new class of applications, leveraging big and fast data, and do all of this with the power of cloud independence. ",
   "app_id": "0",
   "can_post": false,
   "category": "Internet/software",
   "checkins": 0,
   "cover": {
      "cover_id": 163344023827625,
      "source": "http://sphotos-d.ak.fbcdn.net/hphotos-ak-frc1/s720x720/554668_163344023827625_839302172_n.png",
      "offset_y": 0,
      "offset_x": 0
   },
   "founded": "2013",
   "has_added_app": false,
   "is_community_page": false,
   "is_published": true,
   "likes": 126,
   "link": "https://www.facebook.com/gopivotal",
   "location": {
      "street": "1900 South Norfolk St.",
      "city": "San Mateo",
      "state": "CA",
      "country": "United States",
      "zip": "94403",
      "latitude": 37.552261,
      "longitude": -122.292152
   },
   "name": "Pivotal",
   "phone": "650-286-8012",
   "talking_about_count": 15,
   "username": "gopivotal",
   "website": "http://www.gopivotal.com",
   "were_here_count": 0
}
```

Easy enough, but not terribly useful when fetched through a browser or through curl.

A more useful way to consume a REST web service is programmatically. To help you with that task, Spring provides a convenient template class called [`RestTemplate`](http://static.springsource.org/spring/docs/4.0.x/javadoc-api/org/springframework/http/converter/HttpMessageConverter.html). `RestTemplate` makes interacting with most RESTful services a one-line incantation. And it can even bind that data to custom domain types.

First, create a domain class to contain the data that you need. If all you need to know are Pivotal's name, phone number, website URL, and what the gopivotal page is about, then the following domain class should do fine:

    <@snippet path="src/main/java/hello/Page.java" prefix="complete"/>

As you can see, this is a simple Java class with a handful of properties and matching getter methods. It's annotated with `@JsonIgnoreProperties` from the Jackson JSON processing library to indicate that any properties not bound in this type should be ignored.

Make the application executable
-------------------------------

Although it is possible to package this service as a traditional [WAR][u-war] file for deployment to an external application server, the simpler approach demonstrated below creates a standalone application. You package everything in a single, executable JAR file, driven by a good old Java `main()` method. Along the way, you use Spring's support for embedding the [Tomcat][u-tomcat] servlet container as the HTTP runtime, instead of deploying to an external instance.

### Create a main class
Now you can write the main class that uses `RestTemplate` to fetch the data from Pivotal's page at Facebook into a `Page` object.

    <@snippet path="src/main/java/hello/Application.java" prefix="complete"/>

Because the Jackson JSON processing library is in the classpath, `RestTemplate` will use it (via a [message converter](http://static.springsource.org/spring/docs/4.0.x/javadoc-api/org/springframework/http/converter/HttpMessageConverter.html)) to convert the incoming JSON data into a `Page` object. From there, the contents of the `Page` object will be printed to the console.

Here you've only used `RestTemplate` to make an HTTP `GET` request. But `RestTemplate` also supports other HTTP verbs such as `POST`, `PUT`, and `DELETE`.

<@build_an_executable_jar_subhead/>

<@build_an_executable_jar/>

<@run_the_application/>

You should see the following output:

```sh
Name:    Pivotal
About:   At Pivotal, our mission is to enable customers to build a new class of applications, leveraging big and fast data, and do all of this with the power of cloud independence. 
Phone:   650-286-8012
Website: http://www.gopivotal.com
```

Summary
----------
Congratulations! You have just developed a simple REST client using Spring.  
