![BaleroCMS v2](images/logo.png)

## SOFTWARE DOCUMENTATION

### System Requirements

* Java 8 (jdk1.8.0_25)
* Apache Maven (3.2.5 or Higher)
* MySQL (5 or Higher; Only for production mode)

## Welcome to Balero CMS Enterprise.

### About this project

Balero CMS Enterprise is a Lightweight, Clean and Ultra-Fast Content Management System.
It uses Java Back-End for MVC Controllers and AngularJS Front-End for the API REST Services.

### Get Started

Cloning project:

    $ git clone https://github.com/neblina-software/balerocms-enterprise.git

Navigate to root folder and set read/write permissions:

    $ sudo chmod -R 077 balerocms-enterprise
    $ cd balerocms-enterprise

And then, run:

    $ mvn test
    
### Development Deploy:

For testing, development and demo purposes. (H2 Memory-RAM Database)

    $ mvn spring-boot:run

If having troubles, then type:

    $ mvn -Pdev spring-boot:run

It will activate the development profile.
    
### Production Deploy:

For live portals and production (MySQL) with resource minification (HTML/CSS/JS).
First you need a MySQL Database called 'balerocms_enterprise'.

    $ mvn -Pprod spring-boot:run
    
Note: Make sure your MySQL Server is running on port 3306.

    telnet localhost 3306
    
See **Changing Authentication For Production Database**.

Congratulations!, you successfully installed Balero CMS EE.
Open http://localhost:8080/ in your favourite browser.
    
#### Minification For Production Mode

Minification (also minimisation or minimization), is the process of removing all unnecessary
characters from source code without changing its functionality. These unnecessary characters 
usually include white space characters, new line characters, comments, and sometimes block 
delimiters, which are used to add readability to the code but are not required for it to execute.

If you activate it, you website will be load faster.

To activate/deactivate:

    application-dev.yml
    balerocms:
        minification: true or false

## QuickStar Guide

To login to your administrator panel:

http://localhost:8080/admin/dashboard (Hidden link)

And login with the sample data:

* admin:admin

To login to user's dashboard panel:

http://localhost:8080/user/dashboard (Public link)

And login with the sample data:

* user:user

**Note: In a live website, please delete 'user' account and change your admin password**.

## Configuration

### Updating Software

Replace your old files with the new and updated files.

Be sure, your db/migrations are added successfully. It will be executed automatically.

    Execute Command:
    $ mvn test -P prod

See **Database Repair/Migration Tool**.

### Changing Authentication For Production Database

Edit:

* "balerocms-enterprise/pom.xml" starting at Line 173.
* "balerocms-enterprise/src/main/resources/application-prod.yml" starting at Line 2.

1. Add you MYSQL DB username.
2. Add your MYSQL DB password.
        
Note: Usually "root" and "".

### Configuring Default Server Port

Default server port is: 8080. To use different port edit **application.yml** -> server.port.

### Configuring E-Mail Newsletter (SMTP Client)

Edit **application.yml** under resources folder, set your credentials
for you email account.

## Unit Testing

Unit tests will be run in Development Mode (In-Memory Database), you can find this configuration in: application-dev.yml.

### Running Unit Tests with MySQL

Sometimes you need debug a real database, or just want to analyze fields. To do this edit
application-prod.yml. 

    spring:
        datasource:
            dataSourceClassName: com.mysql.jdbc.jdbc2.optional.MysqlDataSource
            url: jdbc:mysql://localhost:3306/balerocms_enterprise
            username: root
            password:
            
Usually "root" and "".        

Then run:

    $ mvn test -P prod
    
### Database Repair/Migration Tool

If you updated your software and/or mysql/db has been changed, or
if you get a database error migration, you can fix with:

    Repair Command:
    $ mvn compile flyway:repair -P prod
    
    Migration Command:
    $ mvn compile flyway:migrate -P prod
 
NOTE: To enable advanced database tool repeair/migration 
See **Changing Authentication For Production Database**.

## Hot Swap

Balero CMS provides a way to develop and make changes in real time application after
your application is compiled and/or running. After editing a Java Class, HTML Resource, etc:

    $ mvn compile
    
Note: In IntelliJ you need to check "Reload changes after compillation > Always".
    
### Bower Components

To install/update your javascript libraries, use:

$ bower install

It will be downloaded in resources folder **/static/bower_components**.

## Template Engine

Balero CMS use ThymeLeaf as template engine, and extends it with AngularJS.

## Security Bugs / Reports

Tested with Mantra WebBrowser. If you found a bug. Please, notify us.

### Responsive Content

For Homepage Blocks, Page Content and Section Content.

Add fluid containers before insert rich text content (Source Code Mode), example:

> &lt;div class="container-fluid"&gt;

> &lt;div class="row"&gt;

> ...

> &lt;/div&gt;

> &lt;/div&gt;
 
Please, read the Bootstrap CSS documentation:

[http://getbootstrap.com/css/](http://getbootstrap.com/css/)

### Database Changelog Timestamps

In **db/migration** folder Timestamps Convention:

V-YEAR-MONTH-DAY.HOUR-MINUTES-SECONDS__Message.sql

Example:

V-20150107.010600__Message_example_etc.sql

Version Control Convention:

V1.0__Message_example_etc.sql

### Internationalization

You can choose the main language for you content from Dashboard.

Go to **Global Settings > Main Language**.

## NOTES

### Frameworks, Features And Toolchains in this software: 

* Spring Boot
* Spring MVC
* Spring Security
* AngularJS
* Bootstrap / Font-Awesome
* Flyway DB
* Hibernate JPA
* Asset Pipeline

#### Features and Plugins: 

* Spring MVC Controllers
* AngularJS Asynchronous Spring REST Controllers
* DB Migrations
* Development Profiles
* Embedded Server / Self-Executable Application
* i18n Embedded / Both Front-End / Back-End Multi-Language
* Method Security Protection Level (@Secure)
* Resource Minification (HTML, CSS, Javascript)
* Multiple Databases (SQL Standars)
* User / Admin Dashboard
* UX Elements
* UX/Responsive Email Newsletter
* Hot Swap
* Spring Security Password Hash Encryption / Protection
* AntiCross-site Scripting (XSS) / OWASP Java HTML Sanitizer

## IoT Support on Enterprise version

* Arduino
* Raspberry Pi

## COMPILING DOCUMENTATION

To compile this source, use:

    $ pandoc -o docs/documentation.pdf docs/README.md
    
Note: Require pandoc software.

Support or Contact

You feedback is welcome! anibalgomez@balerocms.com.

This project is under development, test only for development and not for production before release date.

## ABOUT

Project development by **Neblina Software Company** and **Anibal Gomez**.

Official page: [www.balerocms.com](http://www.balerocms.com/).

![Powered By](images/powered.png)
![Powered By](images/redhat.png)
![Powered By](images/osx.png)

Documentation Version: 0.0.1