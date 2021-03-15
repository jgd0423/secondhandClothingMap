create table shopInfo (
    no number not null,
    id varchar2(100) not null,
    latitude number not null,
    longitude number not null,
    name varchar2(100) not null,
    instagram varchar2(100) null,
    address varchar2(500) not null,
    shopUrl varchar2(500) null,
    regiDate date not null,
    PRIMARY KEY(id),
    unique(no)
);

CREATE SEQUENCE seq_shopInfo START WITH 1 INCREMENT BY 1 MINVALUE 1;

desc shopInfo;

select * from shopInfo;

update shopinfo set latitude = '35.86111948176465' where no = 1;
commit;