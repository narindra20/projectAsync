create table if not exists image_submissions
(
    id uuid
        constraint image_submissions_pk primary key,
    file_name varchar,
    email varchar
);
