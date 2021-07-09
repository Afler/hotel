create table HOTEL_CLIENT (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    CONTACTS_EMAIL varchar(255),
    CONTACTS_PHONE_NUMBER varchar(255),
    --
    FIRST_NAME varchar(255) not null,
    LAST_NAME varchar(255) not null,
    PATRONYMIC varchar(255) not null,
    --
    primary key (ID)
);