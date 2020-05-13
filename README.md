# co-climbr: Spring Boot meets VueJS

## Technologies
Build & Deployment: Maven, Docker, Heroku
Backend: Spring Boot 2, Spring REST Docs, Webflux, JUnit, AssertJ
Frontend: VueJs, Pug Templates, Jest

[![Build Status](https://travis-ci.org/evainga/co-climbr.svg?branch=master)](https://travis-ci.org/evainga/co-climbr)

## How-to start the app locally
1. clone or fork the project
2. from root folder use ```mvn clean install``` and then ```mvn --projects backend clean spring-boot:run``` to start the backend
3. go to frontend folder and use ```npm run serve```

## Links for production

The API documentation of the backend-service:
http://co-climbr-backend.herokuapp.com/docs/climber-documentation.html
http://co-climbr-backend.herokuapp.com/docs/search-documentation.html

Frontend: https://co-climbr.herokuapp.com

## Further links
This project was mainly inspired by
https://github.com/evainga/rememberbrall
https://github.com/WTMBerlin/jscc2019
https://github.com/jonashackt/spring-boot-vuejs
