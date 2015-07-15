![BaleroCMS v2](images/logo.png)

SOFTWARE DOCUMENTATION
======================

### System Requirements

* Java 8 (jdk1.8.0_25)
* Apache Maven (3.2.5 or Higher)
* MySQL (5 or Higher; Only for production mode)

### Welcome to Balero CMS v2.

Cloning project:

    $ git clone https://github.com/neblina-software/balerocms-v2.git

Navigate to root folder:

    $ cd balerocms-v2

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
First you need a MySQL Database called 'balerocms_v2'.

    $ mvn -Pprod spring-boot:run
    
Note: Make sure your MySQL Server is running on port 3306.

    telnet localhost 3306
    
Open http://localhost:8080/ in your favourite browser.

## QuickStar Guide

To login to your administrator panel:

http://localhost:8080/admin (Hidden link)

And login with the sample data:

* admin:admin

To login to user's dashboard panel:

http://localhost:8080/user/dashboard (Public link)

And login with the sample data:

* user:user

**Note: In a live website, please delete 'user' account and change your admin password.**

### Updating Software

Replace your old files with the new and updated files.

Be sure, your db/migrations are added successfully. It will be executed automatically.

### i18n

To change your language, set param in the URL: lang=CODE param.

Example: **http://localhost:8080/?lang=es**

### Configuring E-Mail Newsletter (SMTP Client)

Edit **application.yml** under resources folder, set your credentials
for you email account.

### Database Chengelog Timestamps

Convention:

V-YEAR-MONTH-DAY.HOUR-MINUTES-SECONDS__Message.sql

### About this project

In most cases, Enterprise applications are heavy and very slow. Balero CMS v2
is a Lightweight, Clean and Ultra-Fast, Full Stack, Portal, CMS. it uses Java Back-End for MVC Controllers and AngularJS Front-End for the API REST Services.

NOTES
=====

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
* UX/Responsive Email Newsletter
* Hot Swap
* Spring Security Password Hash Encryption / Protection

IoT Support on v2
=================

* Arduino
* Raspberry Pi

COMPILING DOCUMENTATION
=======================

To compile this source, use:

    $ pandoc -o documentation.pdf documentation.md
    
Note: Require pandoc software.

Support or Contact

You feedback is welcome! anibalgomez@balerocms.com.

This project is under development, test only for development and not for production before release date.

ABOUT
=====

Project development by **Neblina Software Company** and **Anibal Gomez**.

Official page: [www.balerocms.com](http://balerocms.com/).

Documentation Version: 0.0.1