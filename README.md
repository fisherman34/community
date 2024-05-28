## 码匠社区

## 资料
[Spring 文档](https://spring.io/guides)
[Spring Web](https://spring.io/guides/gs/serving-web-content)
[Github SSH access](https://docs.github.com/en/authentication/connecting-to-github-with-ssh/adding-a-new-ssh-key-to-your-github-account)
[ES](https://elasticsearch.cn/)
[BootStrap](https://getbootstrap.com/)
[Github OAuth](https://docs.github.com/en/apps/oauth-apps/building-oauth-apps/creating-an-oauth-app)
[Spring Boot Embedded Database Support](https://docs.spring.io/spring-boot/docs/2.1.0.RELEASE/reference/html/boot-features-sql.html)

## 工具
[Git](https://git-scm.com/)
[Visual Paradigm](https://www.visual-paradigm.com/)

## 脚本
```sql
create table PUBLIC.USERTABLE
(
ID           INTEGER auto_increment primary key NOT NULL,
ACCOUNT_ID   CHARACTER VARYING(100),
NAME         CHARACTER VARYING(50),
TOKEN        CHARACTER(36),
GMT_CREATE   BIGINT,
GMT_MODIFIED BIGINT,
PASSWORD     CHARACTER VARYING(100)
);
```