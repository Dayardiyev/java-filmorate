create table films
(
    id            serial8 primary key,
    name          varchar,
    description   text,
    release_date  date,
    duration      int4,
    rating_mpa_id int8,
    foreign key (rating_mpa_id) references rating_mpa (id)
);

create table rating_mpa
(
    id   serial8 primary key,
    name varchar not null
);


create table genres
(
    id   serial8 primary key,
    name varchar not null
);


create table films_genres
(
    film_id  int8 not null,
    genre_id int8 not null,
    primary key (film_id, genre_id),
    foreign key (film_id) references films (id),
    foreign key (genre_id) references genres (id)
);


create table users
(
    id       serial8 primary key,
    email    varchar,
    login    varchar,
    birthday date
);


create table likes
(
    user_id int8 not null,
    film_id int8 not null,
    primary key (user_id, film_id),
    foreign key (user_id) references users (id),
    foreign key (film_id) references films (id)
);


create table user_friends
(
    user_id     int8 not null,
    friend_id   int8 not null,
    is_accepted bool default false,
    primary key (user_id, friend_id),
    foreign key (user_id) references users (id),
    foreign key (friend_id) references users (id)
)