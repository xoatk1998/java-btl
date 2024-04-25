
create database store_management;
use store_management;

drop table if exists user;
CREATE TABLE user (
  id int primary key auto_increment,
  email varchar(255),
  name varchar(255),
  password varchar(255),
  phone varchar(255),
  role varchar(255),
  username varchar(255),
);

insert into user(id, email, name, password, phone, role, username) values('1', 'admin@gmail.com', 'admin', '$2a$10$62I9/dZHBuoj6vbq2EDtuOG.BF62ANPnyTxkOIm6DphlO2hKyS9G2', '0978776056', 'ADMIN', 'admin'
);


