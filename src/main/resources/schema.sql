create table if not exists users(username varchar_ignorecase(50) not null primary key,password varchar_ignorecase(500) not null,enabled boolean not null);
create table if not exists authorities (username varchar_ignorecase(50) not null,authority varchar_ignorecase(50) not null,constraint fk_authorities_users foreign key(username) references users(username));
create unique index if not exists ix_auth_username on authorities (username,authority);

merge into users key(username, password, enabled) values ('user1','{bcrypt}$2a$10$2lMRkC9fIbWfUuMFBhumIuyc1D1N89dqZ3T3GbA6/GA3DEZHcNsfq',true) ;
merge into authorities key(username, authority) values ('user1','ROLE_CUSTOMERS_READ') ;

merge into users key(username, password, enabled) values ('user2','{bcrypt}$2a$10$2lMRkC9fIbWfUuMFBhumIuyc1D1N89dqZ3T3GbA6/GA3DEZHcNsfq',true) ;
merge into authorities key(username, authority) values ('user2','ROLE_CUSTOMERS_READ') ;
merge into authorities key(username, authority) values ('user2','ROLE_CUSTOMERS_WRITE') ;
