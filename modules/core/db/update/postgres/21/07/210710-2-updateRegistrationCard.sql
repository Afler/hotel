update HOTEL_REGISTRATION_CARD set IS_PAYMENT = false where IS_PAYMENT is null ;
alter table HOTEL_REGISTRATION_CARD alter column IS_PAYMENT set not null ;
update HOTEL_REGISTRATION_CARD set IS_PREPAYMENT = false where IS_PREPAYMENT is null ;
alter table HOTEL_REGISTRATION_CARD alter column IS_PREPAYMENT set not null ;
update HOTEL_REGISTRATION_CARD set PREPAYMENT_DATE = current_date where PREPAYMENT_DATE is null ;
alter table HOTEL_REGISTRATION_CARD alter column PREPAYMENT_DATE set not null ;
