drop
database if exists jdbc;
create
database jdbc;
use
jdbc;

create table authors
(
    id         bigint auto_increment primary key,
    created    timestamp(6) null,
    updated    timestamp(6) null,
    visible    bit null,
    first_name varchar(255) not null,
    last_name  varchar(255) null,
    birth_date bigint null,
    death_date bigint null
);

create table genres
(
    id         bigint auto_increment primary key,
    created    timestamp(6) null,
    updated    timestamp(6) null,
    visible    bit null,
    genre_type varchar(255) not null
);

create table books
(
    isbn             varchar(255) not null unique primary key,
    created          timestamp(6) null,
    updated          timestamp(6) null,
    visible          bit null,
    image_url        varchar(255) not null,
    title            varchar(255) not null,
    publication_date bigint       not null,
    pages_number     int          not null,
    summary          text         not null,
    unique key (isbn)
);

create table book_instances
(
    id              bigint auto_increment primary key,
    created         timestamp(6) null,
    updated         timestamp(6) null,
    visible         bit null,
    imprint         varchar(255) not null,
    publishing_date bigint       not null,
    country_code    varchar(255) not null,
    due_back        timestamp(6) null,
    status          varchar(255) not null,
    book_id         varchar(255) not null,
    foreign key (book_id) references books (isbn) on delete cascade on update cascade
);

create table author_book
(
    author_id bigint       not null,
    book_isbn varchar(255) not null,
    primary key (author_id, book_isbn),
    foreign key (book_isbn) references books (isbn) on delete cascade on update cascade,
    foreign key (author_id) references authors (id) on delete cascade on update cascade
);

create table genre_book
(
    genre_id  bigint       not null,
    book_isbn varchar(255) not null,
    primary key (genre_id, book_isbn),
    foreign key (book_isbn) references books (isbn) on delete cascade on update cascade,
    foreign key (genre_id) references genres (id) on delete cascade on update cascade
);