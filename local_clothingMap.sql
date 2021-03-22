create table shopInfo (
    no number not null,
    id varchar2(100) not null,
    latitude number not null,
    longitude number not null,
    shopName varchar2(100) not null,
    instagram varchar2(100) null,
    address varchar2(500) not null,
    shopUrl varchar2(500) null,
    regiDate date not null,
    PRIMARY KEY(id),
    unique(no)
);

CREATE SEQUENCE seq_shopInfo START WITH 1 INCREMENT BY 1 MINVALUE 1;

drop table shopInfo;
drop sequence seq_shopInfo;


desc shopInfo;

select * from shopInfo;

update shopinfo set latitude = '35.86111948176465' where no = 1;
commit;

SELECT shopName FROM (SELECT s.*, LAG(no) OVER (ORDER BY no DESC) preNo, LAG(name) OVER (ORDER BY no DESC) preShop, LEAD(no) OVER (ORDER BY no DESC) nxtNo, LEAD(name) OVER (ORDER BY no DESC) nxtShop FROM shopInfo s ORDER BY no DESC) WHERE no = 2;

SELECT 
    no, 
    id, 
    latitude, 
    longitude, 
    shopName, 
    instagram, 
    address, 
    shopUrl, 
    preShop,
    nxtShop,
    regiDate FROM (SELECT s.*, LAG(no) OVER (ORDER BY no DESC) preNo, LAG(shopName) OVER (ORDER BY no DESC) preShop, LEAD(no) OVER (ORDER BY no DESC) nxtNo, LEAD(shopName) OVER (ORDER BY no DESC) nxtShop FROM shopInfo s ORDER BY no DESC);

SELECT seq_shopInfo.NEXTVAL as seq FROM DUAL;

INSERT INTO shopInfo VALUES ('3', '1', '1', '1', '1', '1', '1', '1', SYSDATE);

delete shopinfo where no = 3;
commit;


-- 위도, 경도, 위도, 경도 입력
select DISTNACE_WGS84(35.86120949251559, 128.59938944544942, 35.8609641990886, 128.598161214173) from dual;



select * from (
select shopInfo.*, DISTANCE_WGS84(35.86120949251559, 128.59938944544942, latitude, longitude) as DISTANCE
from shopInfo);

CREATE OR REPLACE FUNCTION RADIANS(nDegrees IN NUMBER) 
RETURN NUMBER DETERMINISTIC 
IS
BEGIN
  /*
  -- radians = degrees / (180 / pi)
  -- RETURN nDegrees / (180 / ACOS(-1)); but 180/pi is a constant, so...
  */
  RETURN nDegrees / 57.29577951308232087679815481410517033235;
END RADIANS;
 
create or replace function DISTANCE_WGS84(H_LAT in number, H_LNG in number, T_LAT in number, T_LNG in number)
return number deterministic
is
begin
  return (6371.0 * acos(  
          cos(radians(H_LAT)) * cos(radians(T_LAT))
          * cos(radians(T_LNG) - radians(H_LNG))
          +
          sin(radians(H_LAT)) * sin(radians(T_LAT))        
         ));
end DISTANCE_WGS84;