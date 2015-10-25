CREATE TABLE IF NOT EXISTS `user` (
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
) ENGINE=InnoDB DEFAULT CHARACTER SET=utf8;

CREATE TABLE IF NOT EXISTS `property` (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	administrator_email varchar(255) not null,
	offline bit(1) not null,
	multi_language bit(1) not null,
	url varchar(255) not null,
	main_language varchar(255) not null
) ENGINE=InnoDB DEFAULT CHARACTER SET=utf8;

CREATE TABLE IF NOT EXISTS `setting` (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	code varchar(255) not null,
	title varchar(255) not null,
	title_header varchar(255) not null,
	tags varchar(255) not null,
	footer text not null,
	offline_message text not null
) ENGINE=InnoDB DEFAULT CHARACTER SET=utf8;

CREATE TABLE IF NOT EXISTS `blacklist` (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	ip varchar(255) not null,
	timer int(10) not null,
	attemps int(10) not null
) ENGINE=InnoDB DEFAULT CHARACTER SET=utf8;

CREATE TABLE IF NOT EXISTS `block` (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	name varchar(255) not null,
	content text not null,
	code varchar(255) not null
) ENGINE=InnoDB DEFAULT CHARACTER SET=utf8;

CREATE TABLE IF NOT EXISTS `page` (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	name varchar(255) not null,
	title varchar(255) not null,
	content text not null,
	code varchar(255) not null,
	permalink varchar(255) not null,
	author varchar(255) not null,
	hits int(10) not null
) ENGINE=InnoDB DEFAULT CHARACTER SET=utf8;

CREATE TABLE IF NOT EXISTS `blog` (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	bloname varchar(255) not null,
	title varchar(255) not null,
	intro_post text not null,
	full_post text not null,
	code varchar(255) not null,
	permalink varchar(255) not null,
	author varchar(255) not null,
	hits int(10) not null,
	likes int(10) not null,
	blodate date not null,
	status varchar(255) not null,
	comments int(10) not null
) ENGINE=InnoDB DEFAULT CHARACTER SET=utf8;

CREATE TABLE IF NOT EXISTS `comment` (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	content text not null,
	code varchar(255) not null,
	author varchar(255) not null,
	blodate date not null,
	post_permalink varchar(255) not null
) ENGINE=InnoDB DEFAULT CHARACTER SET=utf8;

INSERT INTO user (id, username, password, password_verify, first_name, last_name, email, subscribed, roles, type) VALUES
(1, 'admin', '$2a$10$hdOPxpQhV7sEHoSCZk9pBuQkEUYB0AWk.1DZlNgVwxe.CStQNltxm', '$2a$10$hdOPxpQhV7sEHoSCZk9pBuQkEUYB0AWk.1DZlNgVwxe.CStQNltxm', 'Anibal', 'Gomez', 'anibalgomez@balerocms.com', 1, 'ROLE_ADMIN', 'admin'),
(2, 'user', '$2a$10$OhggAS1e4GiznN2QrPTHn.V1/FK4QkobOmqHFUPPA4inZcCSoqXKu', '$2a$10$OhggAS1e4GiznN2QrPTHn.V1/FK4QkobOmqHFUPPA4inZcCSoqXKu', 'Jon', 'Doe', 'demo@localhost.com', 1, 'ROLE_USER', 'user'),
(3, 'anonymous', '$$$$$$$$', '$$$$$$$$', 'Anonymous', 'Unregistered', 'anonymous@localhost.com', 1, 'ROLE_ANONYMOUS', 'user');

INSERT INTO property (id, administrator_email, offline, multi_language, url, main_language) VALUES
	(1, 'admin@localhost', 0, 1, 'http://localhost:8080/', 'en');

INSERT INTO setting (id, code, title, title_header, tags, footer, offline_message) VALUES
(1, 'en', 'Demo', '<h1>Welcome</h1><h3>Example Portal</h3><hr class="intro-divider" /><p>Congratulations! Installation success!</p>', 'Business, Enterprise, Company, Etc...', '<ul class="list-inline"><li><a href="#home">Home</a></li><li class="footer-menu-divider">&sdot;</li><li><a href="#about">About</a></li><li class="footer-menu-divider">&sdot;</li><li><a href="#services">Services</a></li><li class="footer-menu-divider">&sdot;</li><li><a href="#contact">Contact</a></li></ul><br />(c) 2015. Your company. Powered by <a href="http://www.balerocms.com/">BaleroCMS</a>.', 'Site under maintenance. We will back shortly.'),
(2, 'es', 'Demo', '<h1>Bienvenido</h1><h3>Portal De Ejemplo</h3><hr class="intro-divider" /><p>Felicidades! La instalación fue un éxito!</p>', 'Business, Enterprise, Company, Etc...', '<ul class="list-inline"><li><a href="#home">Inicio</a></li><li class="footer-menu-divider">&sdot;</li><li><a href="#about">Acerca de</a></li><li class="footer-menu-divider">&sdot;</li><li><a href="#services">Servicios</a></li><li class="footer-menu-divider">&sdot;</li><li><a href="#contact">Contacto</a></li></ul><br />(c) 2015. Tu empresa. Soportado por <a href="http://www.balerocms.com/">BaleroCMS</a>.', 'Sitio en mantenimiento. Regresaremos pronto.');

INSERT INTO block (id, name, content, code) VALUES
(1, 'tablet_en', '\n<div class="content-section-a">\n    <div class="container">\n        <div class="row">\n            <div class="col-lg-5 col-sm-6">\n                <hr class="section-heading-spacer">\n                <div class="clearfix"></div>\n                <h2 class="section-heading">Enterprise CMS</h2>\n                <p class="lead">Made with the powerful of Java 8, Hibernate And Spring. You can edit\n                this content from your <a href="/admin">Admin</a> Dashboard Panel. You can optimize\n                this website from your tablet.</p>\n            </div>\n            <div class="col-lg-5 col-lg-offset-2 col-sm-6">\n                <img class="img-responsive" src="/images/ipad.png" alt="" />\n            </div>\n        </div>\n    </div>\n</div>', 'en'),
(2, 'desktop_en', '\n<div class="content-section-b">\n    <div class="container">\n        <div class="row">\n            <div class="col-lg-5 col-lg-offset-1 col-sm-push-6  col-sm-6">\n                <hr class="section-heading-spacer">\n                <div class="clearfix"></div>\n                <h2 class="section-heading">Demo Content</h2>\n                <p class="lead">Demo content example! You cant optimize tis content from your desktop too.</p>\n            </div>\n            <div class="col-lg-5 col-sm-pull-6  col-sm-6">\n                <img class="img-responsive" src="/images/dog.png" alt="">\n            </div>\n        </div>\n    </div>\n</div>', 'en'),
(3, 'phone_en', '\n<div class="content-section-a">\n    <div class="container">\n        <div class="row">\n            <div class="col-lg-5 col-sm-6">\n                <hr class="section-heading-spacer">\n                <div class="clearfix"></div>\n                <h2 class="section-heading">Simple, Beautiful, Easy</h2>\n                <p class="lead">You can optimize this website from your mobile too.\n                This website has been built with Bootstrap.</p>\n            </div>\n            <div class="col-lg-5 col-lg-offset-2 col-sm-6">\n                <img class="img-responsive" src="/images/phones.png" alt="" />\n            </div>\n        </div>\n    </div>\n</div>', 'en'),
(4, 'tablet_es', '\n<div class="content-section-a">\n    <div class="container">\n        <div class="row">\n            <div class="col-lg-5 col-sm-6">\n                <hr class="section-heading-spacer">\n                <div class="clearfix"></div>\n                <h2 class="section-heading">CMS Empresarial</h2>\n                <p class="lead">Hecho con el poder de Java 8, Hibernate y Spring. Puedes editar\n                este contenido desde tu <a href="/admin">Panel</a> de administración. Puedes optimizar\n                este contenido desde tu tablet.</p>\n            </div>\n            <div class="col-lg-5 col-lg-offset-2 col-sm-6">\n                <img class="img-responsive" src="/images/ipad.png" alt="" />\n            </div>\n        </div>\n    </div>\n</div>', 'es'),
(5, 'desktop_es', '\n<div class="content-section-b">\n    <div class="container">\n        <div class="row">\n            <div class="col-lg-5 col-lg-offset-1 col-sm-push-6  col-sm-6">\n                <hr class="section-heading-spacer">\n                <div class="clearfix"></div>\n                <h2 class="section-heading">Contenido De Demostración</h2>\n                <p class="lead">Contenido de ejemplo! Puedes optimizar éste contenido desde Escritorios también.</p>\n            </div>\n            <div class="col-lg-5 col-sm-pull-6  col-sm-6">\n                <img class="img-responsive" src="/images/dog.png" alt="">\n            </div>\n        </div>\n    </div>\n</div>', 'es'),
(6, 'phone_es', '\n<div class="content-section-a">\n    <div class="container">\n        <div class="row">\n            <div class="col-lg-5 col-sm-6">\n                <hr class="section-heading-spacer">\n                <div class="clearfix"></div>\n                <h2 class="section-heading">Simple, Hermoso, Fácil</h2>\n                <p class="lead">Puedes optimizar éste contenido desde tu móvil.\n                Éste website ha sido creado con Bootstrap.</p>\n            </div>\n            <div class="col-lg-5 col-lg-offset-2 col-sm-6">\n                <img class="img-responsive" src="/images/phones.png" alt="" />\n            </div>\n        </div>\n    </div>\n</div>', 'es');

INSERT INTO page (id, name, title, content, code, permalink, author, hits) VALUES
(1, 'demo_en', 'Demo Page Example', 'In publishing and graphic design, lorem ipsum (derived from Latin dolorem ipsum, translated as "pain itself") is a filler text commonly used to demonstrate the graphic elements of a document or visual presentation. Replacing meaningful content with placeholder text allows viewers to focus on graphic aspects such as font, typography, and page layout without being distracted by the content. It also reduces the need for the designer to come up with meaningful text, as they can instead use quickly-generated lorem ipsum.', 'en', 'demo-page', 'admin', 1),
(2, 'demo_es', 'Página De Demostración', 'Lorem ipsum es el texto que se usa habitualmente en diseño gráfico en demostraciones de tipografías o de borradores de diseño para probar el diseño visual antes de insertar el texto final. Aunque no posee actualmente fuentes para justificar sus hipótesis, el profesor de filología clásica Richard McClintock asegura que su uso se remonta a los impresores de comienzos del siglo XVI.1 Su uso en algunos editores de texto muy conocidos en la actualidad ha dado al texto lorem ipsum nueva popularidad.', 'es', 'demo-pagina', 'admin', 1);

INSERT INTO blog (id, bloname, title, intro_post, full_post, code, permalink, author, hits, likes, blodate, status, comments) VALUES
(1, 'demo_post_en', 'What is Lorem Ipsum?', 'In publishing and graphic design, lorem ipsum (derived from Latin dolorem ipsum, translated as "pain itself") is a filler text commonly used to demonstrate the graphic elements of a document or visual presentation.', 'Replacing meaningful content with placeholder text allows viewers to focus on graphic aspects such as font, typography, and page layout without being distracted by the content. It also reduces the need for the designer to come up with meaningful text, as they can instead use quickly-generated lorem ipsum.', 'en', 'demostration-post', 'admin', 1, 0, '2015-09-15', 'success', '0'),
(2, 'test_post_en', 'Test Demo', 'In publishing and graphic design, lorem ipsum (derived from Latin dolorem ipsum, translated as "pain itself") is a filler text commonly used to demonstrate the graphic elements of a document or visual presentation.', 'Replacing meaningful content with placeholder text allows viewers to focus on graphic aspects such as font, typography, and page layout without being distracted by the content. It also reduces the need for the designer to come up with meaningful text, as they can instead use quickly-generated lorem ipsum.', 'en', 'demo-test', 'admin', 1, 10, '2015-09-15', 'success', '0'),
(3, 'user_post_en', 'User Post Test', 'In publishing and graphic design, lorem ipsum (derived from Latin dolorem ipsum, translated as "pain itself") is a filler text commonly used to demonstrate the graphic elements of a document or visual presentation.', 'Replacing meaningful content with placeholder text allows viewers to focus on graphic aspects such as font, typography, and page layout without being distracted by the content. It also reduces the need for the designer to come up with meaningful text, as they can instead use quickly-generated lorem ipsum.', 'en', 'user-post-en', 'user', 1, 10, '2015-09-15', 'pending', '0'),
(4, 'user_post_es', 'Post De Prueba Por User', 'Lorem ipsum es el texto que se usa habitualmente en diseño gráfico en demostraciones de tipografías o de borradores de diseño para probar el diseño visual antes de insertar el texto final.', 'Aunque no posee actualmente fuentes para justificar sus hipótesis, el profesor de filología clásica Richard McClintock asegura que su uso se remonta a los impresores de comienzos del siglo XVI.1 Su uso en algunos editores de texto muy conocidos en la actualidad ha dado al texto lorem ipsum nueva popularidad.', 'es', 'user-post-es', 'user', 1, 10, '2015-09-15', 'pending', '0'),
(5, 'demo_post_es', 'Probando Caracter Ñ (eñe)', 'Si usted no puede ver éste caracter <b>Ñ</b>, probablemente tenga un error de codificación.', 'Si usted puede ver éste caracter <b>Ñ</b>, significa que todo ha salido a la perfección.', 'es', 'post-de-demostracion', 'admin', 1, 0, '2015-09-15', 'success', '0');

INSERT INTO blacklist (id, ip, timer, attemps) VALUES
(1, '123.456.789.123', '600000', 10);