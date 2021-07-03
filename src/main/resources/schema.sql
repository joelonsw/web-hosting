CREATE TABLE IF NOT EXISTS USERFILE
(
    id bigint auto_increment not null,
    user_id varchar(255) not null,
    file_path varchar(255) not null unique,
    file_type varchar(10),
    primary key (id)
);