# Price Tracker: Backend

Backend for a small project using Spring Boot. Something that can be done with this project it split it into microservices, where the API would then queue a current price check on a given item's name onto another microservice using a system like Kafka, for example, and having another service do that update onto the DB entry for us. Instead, I think that for the scope of this project it would be simpler for us to focus and learn about what an API is, what RESTful architecture is, and DB connections.

So, instead of creating a separate microservice, we'll just try to section everything into layers, so in the event that whenever we're done with building our project we'd like to add more to it, there's always the option to split the original into microservices. An advantage of this is that we could call an update for all items in the database every 24-48 hours automatically, without ever calling the main API.

A demo of this layering can be seen in the initial commit's version. There's the API layer with the routes, there's the controller layer between the API and the DB that requests information to the DB and handles errors, and the DB layer where the information is given back from a repository. This makes it so in the event that we'd want to swap PostgreSQL for something else, like MongoDB for example, we can always do so with ease, or with the most amount of it, at least.

&nbsp;

## Content

---

- [Setup](#setup)
  - [Installing Java](#installing-java)
  - [Installing Maven](#installing-maven)
  - [IDE](#ide)
  - [PostgreSQL](#postgresql)
- [Running the app](#running-the-app)
- [Endpoints](#endpoints)
  - [/parts](#parts)
  - [/parts/addPart](#partsaddpart)
  - [/parts/{id}](#partsid)
- [Models](#models)
  - [Part](#part-model)

&nbsp;

&nbsp;

# Setup

I'm debating if instead of having everyone install Java, Maven, and PostgreSQL, I should've made a Docker instance so that we could just run it, ssh into it and have it contain it all. I guess for now we'll have to do the whole shebang.

&nbsp;

- ## Installing Java

You can download Java from [this link](https://www.oracle.com/java/technologies/downloads/#java11), and you're free to install whichever version of Java that you want. However, please keep in mind that this project was started with Java 11, which is why I linked directly to it 😀. You can select your OS, and when the install process ends, feel free to go to the next step.

&nbsp;

- ## Installing Maven

This is a bit more tricky. It will depend on which platform you're on, however, Maven has instructions for all platforms. Please download [Maven](https://maven.apache.org/download.cgi). If you're confused on which file you should download, I went with the Binary tar.gz archive. Unzip it, place the unzipped directory in a place where it'll never move from, and add the bin directory's location to the Path variable in your environment variables.

If you're on Windows, please either take a look at [this](https://www.youtube.com/watch?v=gb9e3m98avk) video or, if you have my Discord, shoot me a message; it can be tricky to add things to Path the first time on Windows.

If you're on a Unix-based OS, this should be familiar: open either your .bashrc or .bash_profile file, and add the following line:

```bash
export PATH="<path-to-your-apache-maven-#.#.#>/bin:$PATH"
```

Remove the angular brackets and replace what's inside of it with the actual location of your unzipped Maven download. Note that there's already a `/bin` in there. For example, a proper line would look like this:

```bash
export PATH="~/apache-maven-3.8.5/bin:$PATH"
```

&nbsp;

- ## IDE

---

While you can always use whichever IDE you want, I strongly recommend to use [IntelliJ IDEA Community Edition](https://www.jetbrains.com/idea/) because it's a very powerful Java IDE that comes with a lot of tools that make our lives easier, like getter/setter creation, app runners, reloading the project when the pom.xml changes, and a few others. I like it a lot because I can run the app with a single button and not worry about anything else. Whatever you're using, you're gonna want it set up before continuing.

&nbsp;

- ## PostgreSQL

---

I highly recommend watching a [video](https://www.youtube.com/watch?v=uoJjDbL-Y_E) on how to start a PostgreSQL server and database, because it can be very confusing. This solely was one of the few reasons that made me consider MongoDB for this project.

Keep in mind that the name of the database we're using (yes, it has to match) is `shopdb`. The username and password is just `postgres` for both. We're not going to deploy this anywhere, and if we were, we'd make this safer by **not showing the credentials to the database, taking those in from environment variables to be distributed among the team**.

&nbsp;

&nbsp;

# Running the app

After you `git clone` this repo, feel free to open it in your IDE of choice. If it was IntelliJ, you're in luck! IntelliJ is literally one-button-run 😊. If it wasn't, then please run this command inside of the directory:

```bash
mvn spring-boot:run
```

And if everything went alright, then you should see a Spring ASCII-Art logo, a bunch of printed lines and no errors at all. If you look closely, there's a line that says in which port the server is listening in (I'll leave that to you as homework 🙂, a hint is that it says Tomcat initialized...).

After that, you can request to the server on any way shape or form that you'd like, on the valid endpoints, that is.

&nbsp;

&nbsp;

# Endpoints

## /parts

This returns an array with all of the parts in the database. If there's no parts, then it returns an empty array.

&nbsp;&nbsp;&nbsp;&nbsp;Methods: GET  
&nbsp;&nbsp;&nbsp;&nbsp;Returns: A list of all added parts in the database

&nbsp;

An example response:

```json
[
  {
    "id": 1,
    "name": "Logitech Mouse",
    "priceBoughtAt": 35.0,
    "description": "Replaced my old daedalus",
    "currentPrice": 60.0,
    "dateBoughtAt": "2018-08-10"
  },
  {
    "id": 2,
    "name": "Logitech Keyboard",
    "priceBoughtAt": 35.0,
    "description": "Replaced my old daedalus",
    "currentPrice": 60.0,
    "dateBoughtAt": "2018-08-18"
  }
]
```

&nbsp;

## /parts/addPart

This uses the body in the request to add a part to the database. The fields on the request body must match with the [Part Model](#part-model)'s required fields. Otherwise, it returns a bad request, with an `errors` object, that maps the name of the error field to its error message.

&nbsp;&nbsp;&nbsp;&nbsp;Methods: POST  
&nbsp;&nbsp;&nbsp;&nbsp;Returns: Nothing  
&nbsp;&nbsp;&nbsp;&nbsp;Input: [Part Model](#part-model)

This payload is a valid one:

```json
{
  "name": "Logitech Keyboard",
  "priceBoughtAt": 35.0,
  "description": "Replaced my old daedalus",
  "dateBoughtAt": "2018-08-18"
}
```

An example payload that can generate an error is this:

```json
{
  "priceBoughtAt": 35.0,
  "description": "Replaced my old daedalus"
}
```

with a consequence of this:

```json
{
  "timestamp": "2022-05-15T23:01:06.4613658",
  "status": 400,
  "errors": {
    "name": "Name cannot be empty",
    "dateBoughtAt": "Date bought At is missing"
  }
}
```

&nbsp;

## /parts/{id}

This endpoint attempts to find a part with a given ID. In the event that the path parameter is not a valid integer, it simply returns a 400 bad request.

&nbsp;&nbsp;&nbsp;&nbsp;Methods: GET  
&nbsp;&nbsp;&nbsp;&nbsp;Returns: [Part Model](#part-model)  
&nbsp;&nbsp;&nbsp;&nbsp;Input: Path Parameter

This is the response of a valid input:

```json
{
  "id": 1,
  "name": "Logitech Mouse",
  "priceBoughtAt": 35.0,
  "description": "Replaced my old daedalus",
  "currentPrice": 60.0,
  "dateBoughtAt": "2018-08-10"
}
```

And this is the response of an invalid input:

```json
{
  "timestamp": "2022-05-16T03:28:04.054+00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Nothing found for ID #1123",
  "path": "/parts/1123"
}
```

# Models

## Part Model

Used as a way to create the PostgreSQL table and handle data from/to it. We receive the PostgreSQL rows as Part instances.

    Long id (auto)
    String name (required)
    Float priceBoughtAt (required)
    String description (required)
    Float currentPrice
    LocalDate dateBoughtAt (required)

Please note that the dateBoughtAt field is a LocalDate, not LocalDateTime. A valid input for this would be `2018-08-11` ignoring all other time metrics.
