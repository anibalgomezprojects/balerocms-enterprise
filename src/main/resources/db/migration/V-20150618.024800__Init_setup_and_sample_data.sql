CREATE TABLE USER (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	username varchar(255) not null,
	password varchar(255) not null,
	password_verify varchar(255) not null,
	first_name varchar(255) not null,
	last_name varchar(255) not null,
	email varchar(255) not null,
	roles varchar(255) not null
);

CREATE TABLE SETTINGS (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	code varchar(255) not null,
	title varchar(255) not null,
	title_header varchar(255) not null,
	administrator_email varchar(255) not null,
	tags varchar(255) not null,
	footer varchar(1000000) not null
);

CREATE TABLE BLACKLIST (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	ip varchar(255) not null,
	timer int(10) not null,
	attemps int(10) not null,
);

CREATE TABLE HOMEPAGE (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	code varchar(255) not null,
	content varchar(1000000) not null
);

insert into USER (username, password, password_verify, first_name, last_name, email, roles) values ('admin', '$2a$10$hdOPxpQhV7sEHoSCZk9pBuQkEUYB0AWk.1DZlNgVwxe.CStQNltxm', '$2a$10$hdOPxpQhV7sEHoSCZk9pBuQkEUYB0AWk.1DZlNgVwxe.CStQNltxm', 'Anibal', 'Gomez', 'anibalgomez@icloud.com', 'ROLE_ADMIN');
insert into USER (username, password, password_verify, first_name, last_name, email, roles) values ('user', '$2a$10$OhggAS1e4GiznN2QrPTHn.V1/FK4QkobOmqHFUPPA4inZcCSoqXKu', '$2a$10$OhggAS1e4GiznN2QrPTHn.V1/FK4QkobOmqHFUPPA4inZcCSoqXKu', 'Jon', 'Doe', 'noreply@balerocms.com', 'ROLE_USER');

insert into SETTINGS (code, title, title_header, administrator_email, tags, footer) values ('en_US', 'Balerocms v2', '<h1>Welcome</h1><h3>Example Portal</h3><hr class="intro-divider" /><p>Congratulations! Installation success!</p>', 'admin@localhost', 'Business, Enterprise, Company, Etc...', '<ul class="list-inline"><li><a href="#home">Home</a></li><li class="footer-menu-divider">&sdot;</li><li><a href="#about">About</a></li><li class="footer-menu-divider">&sdot;</li><li><a href="#services">Services</a></li><li class="footer-menu-divider">&sdot;</li><li><a href="#contact">Contact</a></li></ul><br />(c) 2015. Your company.');

insert into HOMEPAGE (code, content) values ('en_US', 'test');