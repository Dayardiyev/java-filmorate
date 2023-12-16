drop table if exists users cascade;
drop table if exists user_friends cascade;
drop table if exists genres cascade;
drop table if exists rating_mpa cascade;
drop table if exists films cascade;
drop table if exists likes cascade;
drop table if exists films_genres cascade;

create table if not exists rating_mpa
(
    id   int generated by default as identity primary key,
    name varchar unique not null
);

create table if not exists films
(
    id            int generated by default as identity primary key,
    name          varchar,
    description   text,
    release_date  date,
    duration      int4,
    rating_mpa_id int8,
    foreign key (rating_mpa_id) references rating_mpa (id)
);


create table if not exists genres
(
    id   int generated by default as identity primary key,
    name varchar unique not null
);


create table if not exists films_genres
(
    film_id  int8 not null,
    genre_id int8 not null,
    primary key (film_id, genre_id),
    foreign key (film_id) references films (id),
    foreign key (genre_id) references genres (id)
);


create table if not exists users
(
    id       int generated by default as identity primary key,
    name     varchar,
    email    varchar unique,
    login    varchar unique,
    birthday date
);


create table if not exists likes
(
    user_id int8 not null,
    film_id int8 not null,
    primary key (user_id, film_id),
    foreign key (user_id) references users (id),
    foreign key (film_id) references films (id)
);


create table if not exists user_friends
(
    user_id     int8 not null,
    friend_id   int8 not null,
    is_accepted bool default false,
    primary key (user_id, friend_id),
    foreign key (user_id) references users (id),
    foreign key (friend_id) references users (id)
)