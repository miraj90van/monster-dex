# Monster Dex - Offline assignment

- [Requirements](#requirements)
- [Technology Stack](#Technology)
- [Query](#Query)
- [Instructions](#Instructions)

## Requirements
- JDK 1.8 or later.
- Database Mysql
- IDE that you like, here I use Intellij IDEA

## Technology
- Spring Webflux 
- Spring Security JWT
- Reactive Redis
- R2DBC Mysql

## Instructions
- make sure in your local computer have database mysql 
- execute query for create table
- if you use an IDE, in order to run the code you need to import the project first

## Query
Before run application, execute this query for create table:
```
CREATE TABLE `monster-dex`.user (
                                      id bigint AUTO_INCREMENT NOT NULL,
                                      username varchar(100) NOT NULL,
                                      `password` varchar(100) NOT NULL,
                                      phone varchar(20) NOT NULL,
                                      roles varchar(50),
                                      enabled boolean,
                                      created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      updated_date TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                      deleted_date TIMESTAMP,
                                      created_at bigint(10),
                                      updated_at bigint(10),
                                      deleted_at bigint(10),
                                      PRIMARY KEY (id)
);

CREATE TABLE `monster-dex`.monster_dex (
                                      id bigint AUTO_INCREMENT NOT NULL,
                                      name varchar(100) NOT NULL,
                                      sub_name varchar(100) NOT NULL,
                                      height decimal(15,2),
                                      weight decimal(15,2),
                                      image varchar(255),
                                      `description` varchar(255),
                                      enabled boolean,
                                      created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      updated_date TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                      deleted_date TIMESTAMP,
                                      created_at bigint(10),
                                      updated_at bigint(10),
                                      deleted_at bigint(10),
                                      PRIMARY KEY (id)
);

CREATE TABLE `monster-dex`.monster_type (
                                      id bigint AUTO_INCREMENT NOT NULL,
                                      name varchar(100) NOT NULL,
                                      `description` varchar(255),
                                      enabled boolean,
                                      created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      updated_date TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                      deleted_date TIMESTAMP,
                                      created_at bigint(10),
                                      updated_at bigint(10),
                                      deleted_at bigint(10),
                                      PRIMARY KEY (id)
);

CREATE TABLE `monster-dex`.monster_stat (
                                      id bigint AUTO_INCREMENT NOT NULL,
                                      name varchar(100) NOT NULL,
                                      `description` varchar(255),
                                      enabled boolean,
                                      created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      updated_date TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                      deleted_date TIMESTAMP,
                                      created_at bigint(10),
                                      updated_at bigint(10),
                                      deleted_at bigint(10),
                                      PRIMARY KEY (id)
);

CREATE TABLE `monster-dex`.monster_dex_type (
                                      id bigint AUTO_INCREMENT NOT NULL,
                                      monster_type_id bigint NOT NULL,
                                      monster_dex_id bigint NOT NULL,
								
                                      created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      updated_date TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                      deleted_date TIMESTAMP,
                                      created_at bigint(10),
                                      updated_at bigint(10),
                                      deleted_at bigint(10),
                                      PRIMARY KEY (id)
);

CREATE TABLE `monster-dex`.monster_dex_stat (
                                      id bigint AUTO_INCREMENT NOT NULL,
                                      monster_stat_id bigint NOT NULL,
                                      monster_dex_id bigint NOT NULL,
								
                                      created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      updated_date TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                      deleted_date TIMESTAMP,
                                      created_at bigint(10),
                                      updated_at bigint(10),
                                      deleted_at bigint(10),
                                      PRIMARY KEY (id)
);

CREATE TABLE `monster-dex`.monster_dex_user_catch (
                                      id bigint AUTO_INCREMENT NOT NULL,
                                      monster_dex_id bigint NOT NULL,
                                      user_id bigint NOT NULL,
								
                                      created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      updated_date TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                      deleted_date TIMESTAMP,
                                      created_at bigint(10),
                                      updated_at bigint(10),
                                      deleted_at bigint(10),
                                      PRIMARY KEY (id)
);
```