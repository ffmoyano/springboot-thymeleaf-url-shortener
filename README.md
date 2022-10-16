# URL SHORTENER

Made with Spring Boot, Hibernate JPA, Thymeleaf, Mariadb.

### application.properties

#### db parameters

spring.datasource.url=jdbc:mariadb://localhost:3306/url_shortener

spring.datasource.username=root

spring.datasource.password=password

spring.datasource.driver-class-name=org.mariadb.jdbc.Driver

spring.jpa.show-sql=true

spring.jpa.hibernate.ddl-auto=update

spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MariaDB103Dialect

#### email parameters
spring.mail.host=smtp.gmail.com

spring.mail.port=587

spring.mail.username=mimail@mail.com

spring.mail.password=mipassword

spring.mail.properties.mail.smtp.auth=true

spring.mail.properties.mail.smtp.starttls.enable=true

#### port parameter
server.port=666