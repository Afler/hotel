update HOTEL_REGISTRATION_CARD set IS_COVID = false where IS_COVID is null ;
alter table HOTEL_REGISTRATION_CARD alter column IS_COVID set not null ;
