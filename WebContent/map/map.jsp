<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ include file="../include/inc_header.jsp" %>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>secondhand map</title>
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css"
    />
    <link rel="stylesheet" href="${path}/map/map.css" type="text/css" />
  </head>
  <body>
    <div class="map-container">
      <div id="map">
        <button
          class="btnInfo"
          id="btnCurrentLocation"
          name="btnCurrentLocation"
          type="button"
        >
          <img
            style="width: 30px"
            src="${path}/attach/image/currentLocation.svg"
          />
        </button>
        <button class="btnInfo" id="btnAll">ALL</button>
      </div>
    </div>
    <div class="shop-container">
      <div id="infoWrap">
        현재 위도 : <span id="latitude"></span>
        <br />
        현재 경도 : <span id="longitude"></span>
        <br />
        <div id="buttonContainer">
          <button class="distance">500</button>
          <button class="distance">1000</button>
        </div>
        <br />
        <button id="btnWrite" name="btnWrite">자료입력</button>
      </div>
      <c:forEach var="shopObj" items="${shopInfos }">
        <section
          id="obj${shopObj.no}"
          class="shop"
          style="background-color: ${shopObj.backgroundColor}"
        >
          <div class="shop-wrapper">
            <div
              class="shop__header"
              onclick="goShopLoc(${shopObj.lat}, ${shopObj.lng})"
            >
              <h4>${shopObj.title }</h4>
            </div>
            <div class="img-container"><p>photo</p></div>
          </div>
        </section>
      </c:forEach>
    </div>

    <script
      type="text/javascript"
      src="//dapi.kakao.com/v2/maps/sdk.js?appkey=371135d2edf4452e33d26fcd16dc4384"
    ></script>
    <script>
      const COORDS = 'coords';
      const container = document.getElementById('map');
      let options = {};
      let map = null;
      let distance = Number('${distance}') * 1000;

      const dbShopInfos = JSON.parse('${shopInfos}');

      const shopInfosForMap = dbShopInfos.map((shopInfo) => {
        const data = new Object();
        data.title = shopInfo.title;
        data.latlng = new kakao.maps.LatLng(shopInfo.lat, shopInfo.lng);
        data.no = shopInfo.no;
        data.id = shopInfo.id;
        data.address = shopInfo.address;
        data.instagram = shopInfo.instagram;
        data.shopUrl = shopInfo.shopUrl;
        return data;
      });

      let overlayObjs = new Object();

      const divMap = document.querySelector('#map');
      divMap.addEventListener('click', (e) => {
        if (e.target.classList.contains('close')) {
          const btnCloseId = e.target.id;
          overlayObjs[btnCloseId].setMap(null);
        }
      });

      const btnWrite = document.querySelector('#btnWrite');
      btnWrite.addEventListener('click', goWrite);

      const btnDistance = document.querySelector('#buttonContainer');
      btnDistance.addEventListener('click', (e) => {
        if (e.target.classList.contains('distance')) {
          distance = e.target.innerText;
          const currentLat = document.querySelector('#latitude').innerText;
          const currentLng = document.querySelector('#longitude').innerText;
          let url = '';
          url += '${path}/map_servlet/map.do?';
          url += 'distance=' + distance;
          url += '&currentLat=' + currentLat;
          url += '&currentLng=' + currentLng;

          location.href = url;
        }
      });

      const btnCurrentLocation = document.querySelector('#btnCurrentLocation');
      btnCurrentLocation.addEventListener('click', init);

      const btnAll = document.querySelector('#btnAll');
      btnAll.addEventListener('click', () => {
        distance = 0;
        const currentLat = 0;
        const currentLng = 0;
        let url = '';
        url += '${path}/map_servlet/map.do?';
        url += 'distance=' + distance;
        url += '&currentLat=' + currentLat;
        url += '&currentLng=' + currentLng;

        location.href = url;
      });

      function goShopLoc(lat, lng) {
        const currentLocation = new kakao.maps.LatLng(lat, lng);
        initMap(currentLocation);
      }

      function goShopSection(objNo) {
        const element = document.getElementById(objNo);
        const bodyRect = document.body.getBoundingClientRect();
        const elemRect = element.getBoundingClientRect();
        const offset = elemRect.top - bodyRect.top;
        window.scrollTo({ top: offset, behavior: 'smooth' });
      }

      function goWrite() {
        location.href = '${path}/shop_servlet/write.do';
      }

      function setShopOverlays(marker, latlngObj) {
        let content = '';
        content += '<div class="wrap">';
        content += '<div class="info">';
        content += '<div class="title">';
        content += latlngObj.title;
        content += '<div class="close" id="';
        content += latlngObj.id;
        content += '" title="닫기"></div>';
        content += '</div>';
        content += '<div class="body">';
        content += '<div class="desc">';
        content += '<div class="ellipsis">';
        content += latlngObj.address;
        content += '</div>';
        content += '<div><a href="';
        content += latlngObj.instagram;
        content += '" target="_blank" class="link">인스타그램</a></div>';
        content += '<div><span>온라인: </span>';

        if (latlngObj.shopUrl !== null) {
          content += '<a href="';
          content += latlngObj.shopUrl;
          content += '" target="_blank" class="link">shop</a></div>';
        } else {
          content += '<span>없음</span></div>';
        }

        content += '</div>';
        content += '</div>';
        content += '</div>';
        content += '</div>';
        content += '</div>';

        var overlay = new kakao.maps.CustomOverlay({
          content: content,
          map: map,
          clickable: true,
          position: latlngObj.latlng,
        });

        overlay.setMap(null);

        kakao.maps.event.addListener(marker, 'click', () => {
          overlay.setMap(map);
          const objId = latlngObj.id;
          const objNo = 'obj' + latlngObj.no;
          overlayObjs[objId] = overlay;
          goShopSection(objNo);
        });
      }

      function drawCircle(currentLocation) {
        const radius = distance;
        var circle = new kakao.maps.Circle({
          center: currentLocation, // 원의 중심좌표 입니다
          radius: radius, // 미터 단위의 원의 반지름입니다
          strokeWeight: 3, // 선의 두께입니다
          strokeColor: '#75B8FA', // 선의 색깔입니다
          strokeOpacity: 1, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
          strokeStyle: 'dashed', // 선의 스타일입니다
          fillColor: '#CFE7FF', // 채우기 색깔입니다
          fillOpacity: 0.5, // 채우기 불투명도입니다
        });

        // 지도에 원을 표시합니다
        circle.setMap(map);
      }

      function setShopMarkers(latlngObj) {
        // 마커 이미지의 이미지 주소입니다
        const imageSrc =
          'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png';

        // 마커 이미지의 이미지 크기 입니다
        const imageSize = new kakao.maps.Size(24, 35);

        // 마커 이미지를 생성합니다
        const markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

        // 마커를 생성합니다
        const marker = new kakao.maps.Marker({
          map: map, // 마커를 표시할 지도
          position: latlngObj.latlng, // 마커를 표시할 위치
          title: latlngObj.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
          image: markerImage, // 마커 이미지
        });

        marker.setMap(map);

        setShopOverlays(marker, latlngObj);
      }

      function setCurrentLocationMarker(currentLocation) {
        const markerPosition = currentLocation;
        const marker = new kakao.maps.Marker({
          position: currentLocation,
        });

        marker.setMap(map);

        const spanLat = document.querySelector('#latitude');
        const spanLng = document.querySelector('#longitude');
        spanLat.textContent = currentLocation.getLat();
        spanLng.textContent = currentLocation.getLng();
      }

      function initMap(currentLocation) {
        options = {
          center: currentLocation,
          level: 3,
        };
        map = new kakao.maps.Map(container, options);

        // 가게 마커 표시
        for (let i = 0; i < shopInfosForMap.length; i++) {
          setShopMarkers(shopInfosForMap[i]);
        }

        // 원 표시
        if (distance > 0) {
          drawCircle(currentLocation);
        }
      }

      function handleGeoSuccess(position) {
        const latitude = position.coords.latitude;
        const longitude = position.coords.longitude;
        const currentLocation = new kakao.maps.LatLng(
          35.86120949251559,
          128.59938944544942
        );
        initMap(currentLocation);
        // 현재 위치 마커 표시
        setCurrentLocationMarker(currentLocation);
      }

      function handleGeoError() {
        console.log("Can't access geo location");
      }

      function askForCoords() {
        navigator.geolocation.getCurrentPosition(
          handleGeoSuccess,
          handleGeoError
        );
      }

      function init() {
        askForCoords();
      }

      init();
    </script>
  </body>
</html>
