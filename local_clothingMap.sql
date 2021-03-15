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

SELECT name FROM (SELECT s.*, LAG(no) OVER (ORDER BY no DESC) preNo, LAG(name) OVER (ORDER BY no DESC) preName, LEAD(no) OVER (ORDER BY no DESC) nxtNo, LEAD(name) OVER (ORDER BY no DESC) nxtName FROM shopInfo s ORDER BY no DESC) WHERE no = 1;

SELECT 
    no, 
    id, 
    latitude, 
    longitude, 
    name, 
    instagram, 
    address, 
    shopUrl, 
    regiDate FROM (SELECT s.*, LAG(no) OVER (ORDER BY no DESC) preNo, LAG(name) OVER (ORDER BY no DESC) preName, LEAD(no) OVER (ORDER BY no DESC) nxtNo, LEAD(name) OVER (ORDER BY no DESC) nxtName FROM shopInfo s ORDER BY no DESC) WHERE no = 1;

delete shopinfo where no = 21;
commit;