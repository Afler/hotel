-- begin HOTEL_REGISTRATION_CARD
create table HOTEL_REGISTRATION_CARD (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    CLIENT_ID uuid not null,
    APARTMENTS_ID uuid not null,
    ARRIVAL_DATE date not null,
    DEPARTURE_DATE date not null,
    IS_PAYMENT boolean,
    PAYMENT_DATE date,
    IS_PREPAYMENT boolean,
    PREPAYMENT_DATE date,
    IS_COVID boolean,
    --
    primary key (ID)
)^
-- end HOTEL_REGISTRATION_CARD
-- begin HOTEL_APARTMENTS
create table HOTEL_APARTMENTS (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NUMBER_ integer not null,
    IS_BOOKED boolean,
    IS_FREE boolean,
    --
    primary key (ID)
)^
-- end HOTEL_APARTMENTS
-- begin HOTEL_CLIENT
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
)^
-- end HOTEL_CLIENT
