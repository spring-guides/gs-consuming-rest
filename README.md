This guide walks you through the process of creating an application that consumes a RESTful web service.

What you'll build
-----------------

You'll build an application that uses Spring's `RestTemplate` to retrieve a company's page data from Facebook's Graph API at:

    http://graph.facebook.com/gopivotal

What you'll need
----------------

 - About 15 minutes
 - A favorite text editor or IDE
 - [JDK 6][jdk] or later
 - [Gradle 1.7+][gradle] or [Maven 3.0+][mvn]

[jdk]: http://www.oracle.com/technetwork/java/javase/downloads/index.html
[gradle]: http://www.gradle.org/
[mvn]: http://maven.apache.org/download.cgi

How to complete this guide
--------------------------

Like all Spring's [Getting Started guides](/guides/gs), you can start from scratch and complete each step, or you can bypass basic setup steps that are already familiar to you. Either way, you end up with working code.

To **start from scratch**, move on to [Set up the project](#scratch).

To **skip the basics**, do the following:

 - [Download][zip] and unzip the source repository for this guide, or clone it using [Git][u-git]:
`git clone https://github.com/springframework-meta/gs-consuming-rest.git`
 - cd into `gs-consuming-rest/initial`.
 - Jump ahead to [Fetch a REST resource](#initial).

**When you're finished**, you can check your results against the code in `gs-consuming-rest/complete`.
[zip]: https://github.com/springframework-meta/gs-consuming-rest/archive/master.zip
[u-git]: /understanding/Git

<a name="scratch"></a>
Set up the project
------------------

First you set up a basic build script. You can use any build system you like when building apps with Spring, but the code you need to work with [Gradle](http://gradle.org) and [Maven](https://maven.apache.org) is included here. If you're not familiar with either, refer to [Building Java Projects with Gradle](/guides/gs/gradle/) or [Building Java Projects with Maven](/guides/gs/maven).

### Create the directory structure

In a project directory of your choosing, create the following subdirectory structure; for example, with `mkdir -p src/main/java/hello` on *nix systems:

    └── src
        └── main
            └── java
                └── hello

### Create a Gradle build file

`build.gradle`
```gradle
buildscript {
    repositories {
        maven { url "http://repo.springsource.org/libs-snapshot" }
        mavenLocal()
    }
}

apply plugin: 'java'
apply plugin: 'eclipse'
apply plugin: 'idea'

jar {
    baseName = 'gs-consuming-rest'
    version =  '0.1.0'
}

repositories {
    mavenCentral()
    maven { url "http://repo.springsource.org/libs-snapshot" }
}

dependencies {
    compile("org.springframework:spring-web:3.2.4.RELEASE")
    compile("org.codehaus.jackson:jackson-mapper-asl:1.9.13")
    testCompile("junit:junit:4.11")
}

task wrapper(type: Wrapper) {
    gradleVersion = '1.7'
}
```

This guide is using [Spring Boot's starter POMs](/guides/gs/spring-boot/).

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

`src/main/java/hello/Page.java`
```java
package hello;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Page {

    private String name;
    private String about;
    private String phone;
    private String website;
    
    public String getName() {
        return name;
    }
    
    public String getAbout() {
        return about;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public String getWebsite() {
        return website;
    }
    
}
```

As you can see, this is a simple Java class with a handful of properties and matching getter methods. It's annotated with `@JsonIgnoreProperties` from the Jackson JSON processing library to indicate that any properties not bound in this type should be ignored.

Make the application executable
-------------------------------

Although it is possible to package this service as a traditional [WAR][u-war] file for deployment to an external application server, the simpler approach demonstrated below creates a standalone application. You package everything in a single, executable JAR file, driven by a good old Java `main()` method. Along the way, you use Spring's support for embedding the [Tomcat][u-tomcat] servlet container as the HTTP runtime, instead of deploying to an external instance.

### Create an Application class
Now you can write the `Application` class that uses `RestTemplate` to fetch the data from Pivotal's page at Facebook into a `Page` object.

`src/main/java/hello/Application.java`
```java
package hello;

import org.springframework.web.client.RestTemplate;

public class Application {

    public static void main(String args[]) {
        RestTemplate restTemplate = new RestTemplate();
        Page page = restTemplate.getForObject("http://graph.facebook.com/gopivotal", Page.class);
        System.out.println("Name:    " + page.getName());
        System.out.println("About:   " + page.getAbout());
        System.out.println("Phone:   " + page.getPhone());
        System.out.println("Website: " + page.getWebsite());
    }

}
```

Because the Jackson JSON processing library is in the classpath, `RestTemplate` will use it (via a [message converter](http://static.springsource.org/spring/docs/4.0.x/javadoc-api/org/springframework/http/converter/HttpMessageConverter.html)) to convert the incoming JSON data into a `Page` object. From there, the contents of the `Page` object will be printed to the console.

Here you've only used `RestTemplate` to make an HTTP `GET` request. But `RestTemplate` also supports other HTTP verbs such as `POST`, `PUT`, and `DELETE`.

### Build an executable JAR

Now that your `Application` class is ready, you simply instruct the build system to create a single, executable jar containing everything. This makes it easy to ship, version, and deploy the service as an application throughout the development lifecycle, across different environments, and so forth.

Update your Gradle `build.gradle` file's `buildscript` section, so that it looks like this:

```groovy
buildscript {
    repositories {
        maven { url "http://repo.springsource.org/libs-snapshot" }
        mavenLocal()
    }
    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:0.5.0.BUILD-SNAPSHOT")
    }
}
```

Further down inside `build.gradle`, add the following to the list of applied plugins:

```groovy
apply plugin: 'spring-boot'
```

The [Spring Boot gradle plugin][spring-boot-gradle-plugin] collects all the jars on the classpath and builds a single "über-jar", which makes it more convenient to execute and transport your service.
It also searches for the `public static void main()` method to flag as a runnable class.

Now run the following command to produce a single executable JAR file containing all necessary dependency classes and resources:

```sh
$ ./gradlew build
```

Now you can run the JAR by typing:

```sh
$ java -jar build/libs/gs-consuming-rest-0.1.0.jar
```

[spring-boot-gradle-plugin]: https://github.com/SpringSource/spring-boot/tree/master/spring-boot-tools/spring-boot-gradle-plugin

> **Note:** The procedure above will create a runnable JAR. You can also opt to [build a classic WAR file](/guides/gs/convert-jar-to-war/) instead.

Run the service
-------------------
Run your service at the command line:

```sh
$ ./gradlew clean build && java -jar build/libs/gs-consuming-rest-0.1.0.jar
```


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
