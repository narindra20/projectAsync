create table if not exists users
(
    id varchar
        constraint users_pk primary key,
    first_name varchar,
    last_name varchar,
    user_name varchar,
    email varchar
);

create table if not exists courses
(
    id varchar
        constraint courses_pk primary key,
    title varchar,
    start timestamp,
    "end" timestamp
);

create table if not exists user_courses
(
    user_id varchar
        constraint user_courses_user_fk references users (id),
    course_id varchar
        constraint user_courses_course_fk references courses (id),
    primary key (user_id, course_id)
);
