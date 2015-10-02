CREATE TABLE IF NOT EXISTS USER (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	username varchar(255) not null,
	password varchar(255) not null,
	password_verify varchar(255) not null,
	first_name varchar(255) not null,
	last_name varchar(255) not null,
	email varchar(255) not null,
	subscribed bit(1) not null,
	roles varchar(255) not null,
	type varchar(255) not null
);

CREATE TABLE IF NOT EXISTS PROPERTY (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	administrator_email varchar(255) not null,
	offline bit(1) not null,
	multi_language bit(1) not null
);

CREATE TABLE IF NOT EXISTS SETTING (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	code varchar(255) not null,
	title varchar(255) not null,
	title_header varchar(255) not null,
	tags varchar(255) not null,
	footer varchar(90000) not null,
	offline_message varchar(90000) not null
);

CREATE TABLE IF NOT EXISTS BLACKLIST (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	ip varchar(255) not null,
	timer int(10) not null,
	attemps int(10) not null
);

CREATE TABLE IF NOT EXISTS BLOCK (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	name varchar(255) not null,
	content varchar(90000) not null,
	code varchar(255) not null
);

CREATE TABLE IF NOT EXISTS PAGE (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	name varchar(255) not null,
	title varchar(255) not null,
	content varchar(90000) not null,
	code varchar(255) not null,
	permalink varchar(255) not null,
	author varchar(255) not null,
	hits int(10) not null
);

CREATE TABLE IF NOT EXISTS BLOG (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	bloname varchar(255) not null,
	title varchar(255) not null,
	intro_post varchar(90000) not null,
	full_post varchar(90000) not null,
	code varchar(255) not null,
	permalink varchar(255) not null,
	author varchar(255) not null,
	hits int(10) not null,
	likes int(10) not null,
	blodate date not null
);

CREATE TABLE IF NOT EXISTS COMMENT (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	content varchar(90000) not null,
	code varchar(255) not null,
	author varchar(255) not null,
	blodate date not null,
	post_permalink varchar(255) not null
);

INSERT INTO USER (id, username, password, password_verify, first_name, last_name, email, subscribed, roles, type) VALUES
(1, 'admin', '$2a$10$hdOPxpQhV7sEHoSCZk9pBuQkEUYB0AWk.1DZlNgVwxe.CStQNltxm', '$2a$10$hdOPxpQhV7sEHoSCZk9pBuQkEUYB0AWk.1DZlNgVwxe.CStQNltxm', 'Anibal', 'Gomez', 'anibalgomez@balerocms.com', 1, 'ROLE_ADMIN', 'admin'),
(2, 'user', '$2a$10$OhggAS1e4GiznN2QrPTHn.V1/FK4QkobOmqHFUPPA4inZcCSoqXKu', '$2a$10$OhggAS1e4GiznN2QrPTHn.V1/FK4QkobOmqHFUPPA4inZcCSoqXKu', 'Jon', 'Doe', 'demo@localhost.com', 1, 'ROLE_USER', 'user'),
(3, 'anonymous', '$$$$$$$$', '$$$$$$$$', 'Anonymous', 'Unregistered', 'anonymous@localhost.com', 1, 'ROLE_ANONYMOUS', 'user');

INSERT INTO PROPERTY (id, administrator_email, offline, multi_language) VALUES
(1, 'admin@localhost', 0, 1);

INSERT INTO SETTING (id, code, title, title_header, tags, footer, offline_message) VALUES
(1, 'en', 'Demo', '<h1>Welcome</h1><h3>Example Portal</h3><hr class="intro-divider" /><p>Congratulations! Installation success!</p>', 'Business, Enterprise, Company, Etc...', '<ul class="list-inline"><li><a href="#home">Home</a></li><li class="footer-menu-divider">&sdot;</li><li><a href="#about">About</a></li><li class="footer-menu-divider">&sdot;</li><li><a href="#services">Services</a></li><li class="footer-menu-divider">&sdot;</li><li><a href="#contact">Contact</a></li></ul><br />(c) 2015. Your company. Powered by <a href="http://www.balerocms.com/">BaleroCMS</a>.', 'Site under maintenance. We will back shortly.'),
(2, 'es', 'Demo', '<h1>Bienvenido</h1><h3>Portal De Ejemplo</h3><hr class="intro-divider" /><p>Felicidades! La instalación fue un éxito!</p>', 'Business, Enterprise, Company, Etc...', '<ul class="list-inline"><li><a href="#home">Inicio</a></li><li class="footer-menu-divider">&sdot;</li><li><a href="#about">Acerca de</a></li><li class="footer-menu-divider">&sdot;</li><li><a href="#services">Servicios</a></li><li class="footer-menu-divider">&sdot;</li><li><a href="#contact">Contacto</a></li></ul><br />(c) 2015. Tu empresa. Soportado por <a href="http://www.balerocms.com/">BaleroCMS</a>.', 'Sitio en mantenimiento. Regresaremos pronto.');

INSERT INTO BLOCK (id, name, content, code) VALUES
(1, 'tablet_en', '\n<div class="content-section-a">\n    <div class="container">\n        <div class="row">\n            <div class="col-lg-5 col-sm-6">\n                <hr class="section-heading-spacer">\n                <div class="clearfix"></div>\n                <h2 class="section-heading">Enterprise CMS</h2>\n                <p class="lead">Made with the powerful of Java 8, Hibernate And Spring. You can edit\n                this content from your <a href="/admin">Admin</a> Dashboard Panel. You can optimize\n                this website from your tablet.</p>\n            </div>\n            <div class="col-lg-5 col-lg-offset-2 col-sm-6">\n                <img class="img-responsive" src="/images/ipad.png" alt="" />\n            </div>\n        </div>\n    </div>\n</div>', 'en'),
(2, 'desktop_en', '\n<div class="content-section-b">\n    <div class="container">\n        <div class="row">\n            <div class="col-lg-5 col-lg-offset-1 col-sm-push-6  col-sm-6">\n                <hr class="section-heading-spacer">\n                <div class="clearfix"></div>\n                <h2 class="section-heading">Demo Content</h2>\n                <p class="lead">Demo content example! You cant optimize tis content from your desktop too.</p>\n            </div>\n            <div class="col-lg-5 col-sm-pull-6  col-sm-6">\n                <img class="img-responsive" src="/images/dog.png" alt="">\n            </div>\n        </div>\n    </div>\n</div>', 'en'),
(3, 'phone_en', '\n<div class="content-section-a">\n    <div class="container">\n        <div class="row">\n            <div class="col-lg-5 col-sm-6">\n                <hr class="section-heading-spacer">\n                <div class="clearfix"></div>\n                <h2 class="section-heading">Simple, Beautiful, Easy</h2>\n                <p class="lead">You can optimize this website from your mobile too.\n                This website has been built with Bootstrap.</p>\n            </div>\n            <div class="col-lg-5 col-lg-offset-2 col-sm-6">\n                <img class="img-responsive" src="/images/phones.png" alt="" />\n            </div>\n        </div>\n    </div>\n</div>', 'en'),
(4, 'newsletter_en', '\n<div class="content-section-b">\n    <div class="container">\n        <div class="row">\n            <h2 class="section-heading">Subscribe to our newsletter</h2>\n            <form action="/mail/list" method="post"><div class="row">\n                <div class="col-xs-4"><input type="text" name="firstname" class="form-control" placeholder="Your Name"></div><div class="col-xs-4"><input type="email" name="email" class="form-control" placeholder="Your E-mail"></div>\n                <div class="col-xs-4">\n                <button class="btn btn-lg btn-primary" type="submit">Subscribe</button></div>\n            </div></form>\n        </div>\n    </div>\n</div>', 'en'),
(5, 'tablet_es', '\n<div class="content-section-a">\n    <div class="container">\n        <div class="row">\n            <div class="col-lg-5 col-sm-6">\n                <hr class="section-heading-spacer">\n                <div class="clearfix"></div>\n                <h2 class="section-heading">CMS Empresarial</h2>\n                <p class="lead">Hecho con el poder de Java 8, Hibernate y Spring. Puedes editar\n                este contenido desde tu <a href="/admin">Panel</a> de administración. Puedes optimizar\n                este contenido desde tu tablet.</p>\n            </div>\n            <div class="col-lg-5 col-lg-offset-2 col-sm-6">\n                <img class="img-responsive" src="/images/ipad.png" alt="" />\n            </div>\n        </div>\n    </div>\n</div>', 'es'),
(6, 'desktop_es', '\n<div class="content-section-b">\n    <div class="container">\n        <div class="row">\n            <div class="col-lg-5 col-lg-offset-1 col-sm-push-6  col-sm-6">\n                <hr class="section-heading-spacer">\n                <div class="clearfix"></div>\n                <h2 class="section-heading">Contenido De Demostración</h2>\n                <p class="lead">Contenido de ejemplo! Puedes optimizar éste contenido desde Escritorios también.</p>\n            </div>\n            <div class="col-lg-5 col-sm-pull-6  col-sm-6">\n                <img class="img-responsive" src="/images/dog.png" alt="">\n            </div>\n        </div>\n    </div>\n</div>', 'es'),
(7, 'phone_es', '\n<div class="content-section-a">\n    <div class="container">\n        <div class="row">\n            <div class="col-lg-5 col-sm-6">\n                <hr class="section-heading-spacer">\n                <div class="clearfix"></div>\n                <h2 class="section-heading">Simple, Hermoso, Fácil</h2>\n                <p class="lead">Puedes optimizar éste contenido desde tu móvil.\n                Éste website ha sido creado con Bootstrap.</p>\n            </div>\n            <div class="col-lg-5 col-lg-offset-2 col-sm-6">\n                <img class="img-responsive" src="/images/phones.png" alt="" />\n            </div>\n        </div>\n    </div>\n</div>', 'es'),
(8, 'newsletter_es', '\n<div class="content-section-b">\n    <div class="container">\n        <div class="row">\n            <h2 class="section-heading">Suscribete a nuestro boletín</h2>\n            <form action="/mail/list" method="post"><div class="row">\n                <div class="col-xs-4"><input type="text" name="firstname" class="form-control" placeholder="Tu Nombre"></div><div class="col-xs-4"><input type="email" name="email" class="form-control" placeholder="Tu E-mail"></div>\n                <div class="col-xs-4">\n                <button class="btn btn-lg btn-primary" type="submit">Suscribirme</button></div>\n            </div></form>\n        </div>\n    </div>\n</div>', 'es');

INSERT INTO PAGE (id, name, title, content, code, permalink, author, hits) VALUES
(1, 'demo_en', 'Demo Page Example', 'This is a demo content.', 'en', 'demo-page', 'admin', 1),
(2, 'demo_es', 'Página De Demostración', 'Esto es un ejemplo.', 'es', 'demo-pagina', 'admin', 1);

INSERT INTO BLOG (id, bloname, title, intro_post, full_post, code, permalink, author, hits, likes, blodate) VALUES
(1, 'demo_post_en', 'What is Lorem Ipsum?', 'intro.', 'full.', 'en', 'demostration-post', 'admin', 1, 0, '2015-09-15'),
(2, 'test_post_en', 'Test demo?', '<h3>demo</h3>', 'full.', 'en', 'demostration-post-123', 'admin', 1, 10, '2015-09-15'),
(3, 'demo_post_es', 'La Niña Del Ñoño', 'intro.', 'full.', 'es', 'post-de-demostracion', 'admin', 1, 0, '2015-09-15');

INSERT INTO BLACKLIST (id, ip, timer, attemps) VALUES
(1, '123.456.789.123', '600000', 10);