<div id="top"></div>
<!--
*** Thanks for checking out the CoolProduct project. If you have a suggestion
*** that would make this better, please fork the repo and create a pull request
*** or simply open an issue with the tag "enhancement".
*** Don't forget to give the project a star!
*** Thanks again!
-->


<!-- PROJECT LINK -->
<br />
<div align="center">
  <a href="https://github.com/tripti29/coolproduct">
  </a>

<h3 align="center">Spring boot with gradle</h3>

  <p align="center">
    This project will build a simple java application with Spring Boot using gradle.
    <br />
    <a href="https://github.com/tripti29/coolproduct"><strong>Create Spring boot application with gradle»</strong></a>
    <br />
    <br />
    <a href="https://github.com/tripti29/coolproduct">View Demo</a>
    ·
    <a href="https://github.com/tripti29/coolproduct/issues">Report Bug</a>
    ·
    <a href="https://github.com/tripti29/coolproduct/issues">Request Feature</a>
  </p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
        <li><a href="#Tools-used">Tools used</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#Running-the-application-in-Eclipse">Running the application in Eclipse</a></li>
        <li><a href="#Running-the-application-in-Terminal-(Ubuntu)">Running the application in Terminal(Ubuntu)</a></li>
        <li><a href="#Setup-Jenkins-Job">Setup Jenkins Job</a></li>
      </ul>
    </li>
    <li><a href="#Useful-Commands">Useful Commands</a></li>
    <li><a href="#Upcoming-features">Upcoming features</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>




<!-- ABOUT THE PROJECT -->
## About The Project

This project will give a quick setup to build a spring boot application which has at least 5 products
in a database with gradle. It uses intuitive API to query for these products. It is
possible to query this API using CURL/Postman. The whole code of this application can be built, tested and deployed automatically using Jenkins.

<p align="right">(<a href="#top">back to top</a>)</p>



### Built With

* [[JDK11]][JDK-url]
* [[Gradle4+]][Gradle-url]
* [[Jenkins2]][Jenkins-url]

<p align="right">(<a href="#top">back to top</a>)</p>

### Tools used
* [Terminal]
* [[Eclipse]][Eclipse-url]
* [[Jenkins2]][Jenkins-url]


<!-- GETTING STARTED -->
## Getting Started

### Running the application in Eclipse

1. Download the zip or clone the Git repository.
	`git clone https://github.com/tripti29/coolproduct`
2. Unzip the zip file (if you downloaded one)
3. Open Command Prompt and Change directory (cd) to folder containing pom.xml
4. Open Eclipse
	File -> Import -> Existing Gradle Project -> Navigate to the folder where you unzipped the zip
5. Select the right project
6. Choose the Spring Boot Application file (search for @SpringBootApplication)
7. Right Click on the file and Run as Java Application
8. You are ready to go

### Running the application in Terminal(Ubuntu)

1. Download the zip or clone the Git repository.
	`git clone https://github.com/tripti29/coolproduct`
2. Unzip the zip file (if you downloaded one)
3. Open Command Prompt and Change directory (cd) to folder containing gradlew
4. Clean test application 
	`./gradlew clean test`
4. Build application 
	`./gradlew jar`
4. Run application 
	`./gradlew bootRun`


### Setup Jenkins Job

1. Start your jenkins service
	sudo systemctl status jenkins
2. Create new Item
3. Set multibranch pipeline


<!-- USAGE EXAMPLES -->
## Useful Commands

Use following CURL commands
1. to check application health
	`curl http://localhost:8085/actuator/health`
2. to add new product
	`curl -X POST http://localhost:8085/products -H "Content-Type: application/json" -d '{"name": "samplename", "category": "samplecategory",  "amount":<sampleamount>}'`
3. to get all products
	`curl http://localhost:8085/products`
4. to get products for a category
	`curl http://localhost:8085/products?category=samplecategory`
5. to get products for a name
	`curl http://localhost:8085/products?name=samplename`
6. to get product for an id
	`curl http://localhost:8085/products/<idLong>`

<p align="right">(<a href="#top">back to top</a>)</p>


<!-- ROADMAP -->
## Upcoming features

- [ ] Refactoring java code with mapstruct
- [ ] Frontend with ease to use GUI

See the [open issues](https://github.com/tripti29/coolproduct/issues) for a full list of proposed features (and known issues).

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- CONTRIBUTING -->
## Contributing

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<p align="right">(<a href="#top">back to top</a>)</p>


<!-- CONTACT -->
## Contact

Tripti Gupta: [https://www.linkedin.com/in/tripti-gupta-9b146622/](https://www.linkedin.com/in/tripti-gupta-9b146622/)

Project Link: [https://github.com/tripti29/coolproduct](https://github.com/tripti29/coolproduct)

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- ACKNOWLEDGMENTS -->
## Acknowledgments

* [https://spring.io/guides/gs/spring-boot/](https://spring.io/guides/gs/spring-boot/)
* [https://gradle.org/](https://gradle.org/)
* [https://stackoverflow.com/](https://stackoverflow.com/)


<p align="right">(<a href="#top">back to top</a>)</p>



<!-- MARKDOWN LINKS -->
[JDK-url]: https://www.oracle.com/java/technologies/downloads/archive/
[Gradle-url]: https://gradle.org/install/
[Jenkins-url]: https://www.jenkins.io/download/
[Eclipse-url]: https://www.eclipse.org/downloads/