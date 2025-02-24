# SQL operation
```bash
docker run --name some-mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=my-secret-pw mysql
mysql -u root -p
```
```sql
CREATE DATABASE todo_management;
GRANT ALL PRIVILEGES ON todo_management.* TO 'myuser'@'%';
SHOW DATABASES;
```
