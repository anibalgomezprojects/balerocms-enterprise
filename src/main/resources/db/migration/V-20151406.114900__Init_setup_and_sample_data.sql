CREATE TABLE USER (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	username varchar(255) not null,
	password varchar(255) not null,
	password_verify varchar(255) not null,
	first_name varchar(255) not null,
	last_name varchar(255) not null,
	roles varchar(255) not null
);

CREATE TABLE MAIL (
	email_id BIGINT AUTO_INCREMENT PRIMARY KEY,
	address varchar(255) not null,
	user_id BIGINT not null,
	CONSTRAINT fk_user_email FOREIGN KEY (user_id) REFERENCES USER (id)
);

CREATE TABLE SETTING (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	code varchar(255) not null,
	title varchar(255) not null,
	title_header varchar(255) not null,
	administrator_email varchar(255) not null,
	tags varchar(255) not null,
	footer varchar(1000) not null,
	offline int(10) not null,
	offline_message varchar(1000) not null
);

CREATE TABLE BLACKLIST (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	ip varchar(255) not null,
	timer int(10) not null,
	attemps int(10) not null
);

CREATE TABLE BLOCK (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	name varchar(255) not null,
	content varchar(1000) not null,
	code varchar(255) not null
);

INSERT INTO USER (id, username, password, password_verify, first_name, last_name, roles) VALUES
(1, 'admin', '$2a$10$hdOPxpQhV7sEHoSCZk9pBuQkEUYB0AWk.1DZlNgVwxe.CStQNltxm', '$2a$10$hdOPxpQhV7sEHoSCZk9pBuQkEUYB0AWk.1DZlNgVwxe.CStQNltxm', 'Anibal', 'Gomez', 'ROLE_ADMIN'),
(2, 'user', '$2a$10$OhggAS1e4GiznN2QrPTHn.V1/FK4QkobOmqHFUPPA4inZcCSoqXKu', '$2a$10$OhggAS1e4GiznN2QrPTHn.V1/FK4QkobOmqHFUPPA4inZcCSoqXKu', 'Jon', 'Doe', 'ROLE_USER'),
(3, 'anonymous', '$$$$$$$$', '$$$$$$$$', 'Anonymous', 'Unregistered', 'ROLE_ANONYMOUS');

INSERT INTO MAIL (email_id, address, user_id) VALUES
(1, 'admin@localhost.com', 1),
(2, 'user@localhost.com', 2),
(3, 'anonymous@localhost.com', 3),
(4, 'guest@localhost.com', 3);

INSERT INTO SETTING (id, code, title, title_header, administrator_email, tags, footer, offline, offline_message) VALUES
(1, 'en_US', 'Balerocms v2', '<h1>Welcome</h1><h3>Example Portal</h3><hr class="intro-divider" /><p>Congratulations! Installation success!</p>', 'admin@localhost', 'Business, Enterprise, Company, Etc...', '<ul class="list-inline"><li><a href="#home">Home</a></li><li class="footer-menu-divider">&sdot;</li><li><a href="#about">About</a></li><li class="footer-menu-divider">&sdot;</li><li><a href="#services">Services</a></li><li class="footer-menu-divider">&sdot;</li><li><a href="#contact">Contact</a></li></ul><br />(c) 2015. Your company. Powered by <a href="http://www.balerocms.com/">BaleroCMS</a>.', '0', 'Site under maintenance. We will back shortly.');

INSERT INTO BLOCK (id, name, content, code) VALUES
(1, 'tablet_en', '<div class="content-section-a">    <div class="container">        <div class="row">            <div class="col-lg-5 col-sm-6"><hr class="section-heading-spacer">                <div class="clearfix"></div>                <h2 class="section-heading">Enterprise CMS</h2>                <p class="lead">Made with the powerful of Java 8, Hibernate And Spring. You can edit                 this content from your <a href="/admin">Admin</a> Dashboard Panel. You can optimize                this website from your tablet.</p>            </div>            <div class="col-lg-5 col-lg-offset-2 col-sm-6">                <img class="img-responsive" src="/images/ipad.png" alt="" />            </div>        </div>    </div></div>', 'en_US'),
(2, 'desktop_en', '<div class="content-section-b">    <div class="container">            <div class="row">                <div class="col-lg-5 col-lg-offset-1 col-sm-push-6  col-sm-6">                    <hr class="section-heading-spacer">                    <div class="clearfix"></div>                    <h2 class="section-heading">Demo Content</h2>                    <p class="lead">Demo content example! You cant optimize tis content from your desktop too.</p>                </div>                <div class="col-lg-5 col-sm-pull-6  col-sm-6">                    <img class="img-responsive" src="/images/dog.png" alt="">                </div>            </div>        </div>    </div>', 'en_US'),
(3, 'phone_en', '<div class="content-section-a">	<div class="container">		<div class="row">			<div class="col-lg-5 col-sm-6">				<hr class="section-heading-spacer">				<div class="clearfix"></div>				<h2 class="section-heading">Simple, Beautiful, Easy</h2>				<p class="lead">You can optimize this website from your mobile too.				This website has been built with Bootstrap.</p>			</div>			<div class="col-lg-5 col-lg-offset-2 col-sm-6">				<img class="img-responsive" src="/images/phones.png" alt="" />			</div>		</div>	</div></div>', 'en_US'),
(4, 'newsletter_en', '<div class="content-section-b">    <div class="container">        <div class="row">            <h2 class="section-heading">Subscribe to our newsletter</h2>            <form action="/mail/list" method="post">                 <input type="email" name="email" class="form-control" placeholder="Your E-mail">                 <br />                 <button class="btn btn-lg btn-primary" type="submit">Subscribe</button>            </form>        </div>     </div></div>', 'en_US');