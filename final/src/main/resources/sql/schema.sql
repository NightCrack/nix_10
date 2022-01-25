drop database if exists jdbc;
create database jdbc;
use jdbc;

create table authors (
-- private Long id;
-- private Instant created;
-- private Instant updated;
-- private Boolean visible;
-- private String firstName;
-- private String lastName;
-- private String birthDate;
-- private String deathDate;
 id bigint auto_increment primary key,
 created timestamp(6) null,
 updated timestamp(6) null,
 visible bit null,
 first_name varchar(255) not null,
 last_name varchar(255) null,
 birth_date bigint null,
 death_date bigint null
);

create table genres (
-- private Instant created;
-- private Instant updated;
-- private Boolean visible;
-- private Long id;
-- private GenreType genreType;
 id bigint auto_increment primary key,
 created timestamp(6) null,
 updated timestamp(6) null,
 visible bit null,
 genre_type varchar(255) not null
);

create table books (
-- private String isbn;
-- private Instant created;
-- private Instant updated;
-- private Boolean visible;
-- private String imageUrl;
-- private String title;
-- private String publicationDate;
-- private Integer pagesNumber;
-- private String summary;
 isbn varchar(255) not null primary key,
 created timestamp(6) null,
 updated timestamp(6) null,
 visible bit null,
 image_url varchar(255) not null,
 title varchar(255) not null,
 publication_date bigint not null,
 pages_number int not null,
 summary text not null
);

create table book_instances (
-- private Long id;
-- private Instant created;
-- private Instant updated;
-- private Boolean visible;
-- private String imprint;
-- private String publishingDate;
-- private CountryCode countryCode;
-- private Instant dueBack;
-- private StatusType status;
-- private Book book;

 id bigint auto_increment primary key,
 created timestamp(6) null,
 updated timestamp(6) null,
 visible bit null,
 imprint varchar(255) not null,
 publishing_date varchar(255) not null,
 country_code varchar(255) not null,
 due_back timestamp(6) null,
 status varchar(255) not null,
 book_id varchar(255) not null,
 foreign key (book_id) references books (isbn) on delete cascade
);

create table departments (
-- private Long id;
-- private Instant created;
-- private Instant updated;
-- private Boolean visible;
-- private DepartmentType departmentType;
-- private String name;

 id bigint auto_increment primary key,
 created timestamp(6) null,
 updated timestamp(6) null,
 visible bit null,
 department_type varchar(255) not null,
 name varchar(255) not null
);

create table employees (
-- private Long id;
-- private Instant created;
-- private Instant updated;
-- private Boolean visible;
-- private String firstName;
-- private String lastName;
-- private Integer age;
-- private Department department;

 id bigint auto_increment primary key,
 created timestamp(6) null,
 updated timestamp(6) null,
 visible bit null,
 first_name varchar(255) not null,
 last_name varchar(255) null,
 age int not null,
 department_id bigint not null,
 foreign key (department_id) references departments (id) on delete cascade
);

create table author_book (
 author_id bigint not null,
 book_isbn varchar(255) not null,
 primary key (author_id, book_isbn),
 foreign key (book_isbn) references books (isbn),
 foreign key (author_id) references authors (id) on delete cascade
);

create table genre_book (
 genre_id bigint not null,
 book_isbn varchar(255) not null,
 primary key (genre_id, book_isbn),
 foreign key (book_isbn) references books (isbn),
 foreign key (genre_id) references genres (id)
);