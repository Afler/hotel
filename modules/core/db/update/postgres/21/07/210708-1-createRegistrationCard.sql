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
);