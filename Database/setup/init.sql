
create sequence global_seq increment by 1 start with 1 no cycle;
create table room (
    id integer default nextval('global_seq') not null unique,
    create_date timestamp not null,
    update_date timestamp not null
);
create table member (
    id integer default nextval('global_seq') not null unique,
    member_index integer not null,
    user_id integer not null,
    room_id integer not null
);
create table talk (
    id integer default nextval('global_seq') not null unique,
    create_date timestamp not null,
    user_id varchar(64) not null,
    message varchar(512) not null,
    room_id integer not null
);
create table app_user (
    id integer default nextval('global_seq') not null unique,
    user_id varchar(64) not null,
    passwd varchar(64) not null,
    name varchar(128) not null,
    email varchar(128) not null,
    auth varchar(64) not null
);