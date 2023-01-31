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

insert into `monster-dex`.`user`(username, password, phone, roles,enabled)
values('admin', 'c1yY0nQdcjVu3KWjGUPbtLemshzW7x53D0q8sxtKuVI=', "0129302193", "ROLE_ADMIN",1);

insert into `monster-dex`.monster_stat(name, description, enabled)
values('HP', 'description for HP', true);

insert into `monster-dex`.monster_stat(name, description, enabled)
values('Attack', 'description for Attack', true);

insert into `monster-dex`.monster_stat(name, description, enabled)
values('Def', 'description for Def', true);

insert into `monster-dex`.monster_stat(name, description, enabled)
values('Speed', 'description for Speed', true);


insert into `monster-dex`.monster_type(name, description, enabled)
values('GRASS', 'description for GRASS', true);

insert into `monster-dex`.monster_type(name, description, enabled)
values('PSYCHIC', 'description for PSYCHIC', true);

insert into `monster-dex`.monster_type(name, description, enabled)
values('FLYING', 'description for FLYING', true);

insert into `monster-dex`.monster_type(name, description, enabled)
values('FIRE', 'description for FIRE', true);

insert into `monster-dex`.monster_type(name, description, enabled)
values('WATER', 'description for WATER', true);

insert into `monster-dex`.monster_type(name, description, enabled)
values('ELECTRIC', 'description for ELECTRIC', true);

insert into `monster-dex`.monster_type(name, description, enabled)
values('BUG', 'description for BUG', true);

CREATE OR REPLACE VIEW vw_monster_dex as
select md.id,md.name, md.sub_name, md.height, md.weight, md.image, md.description,
       GROUP_CONCAT(DISTINCT mt.name  SEPARATOR ', ') as type ,
       GROUP_CONCAT(DISTINCT concat(ms.name, ':', mds.amount)  SEPARATOR ', ') as stat_map
from monster_dex as md
         left join monster_dex_type as mdt on mdt.monster_dex_id = md.id
         left join monster_type as mt on mt.id = mdt.monster_type_id
         left join monster_dex_stat as mds on mds.monster_dex_id = md.id
         left join monster_stat as ms on ms.id = mds.monster_stat_id
where md.enabled = 1
group by md.id

CREATE OR REPLACE VIEW vw_monster_dex_user as
select md.id, mduc.user_id ,md.name, md.sub_name, md.height, md.weight, md.image, md.description,
       GROUP_CONCAT(DISTINCT mt.name  SEPARATOR ', ') as type ,
       GROUP_CONCAT(DISTINCT concat(ms.name, ':', mds.amount)  SEPARATOR ', ') as stat_map,
       COALESCE(case when mduc.monster_dex_id then 1 else 0 end) as captured
from monster_dex as md
         left join monster_dex_type as mdt on mdt.monster_dex_id = md.id
         left join monster_type as mt on mt.id = mdt.monster_type_id
         left join monster_dex_stat as mds on mds.monster_dex_id = md.id
         left join monster_stat as ms on ms.id = mds.monster_stat_id
         left outer join monster_dex_user_catch mduc on mduc.monster_dex_id = md.id
where md.enabled = 1
group by md.id, mduc.user_id;
```