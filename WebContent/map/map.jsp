<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ include file="../include/inc_header.jsp" %>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Map</title>
  </head>

  <body>
    <div id="container" style="display: flex">
      <div id="map" style="width: 1000px; height: 900px"></div>
      <div style="padding-left: 20px">
        현재 위도 : <span id="latitude"></span>
        <br />
        현재 경도 : <span id="longitude"></span>
        <br />
        <div id="buttonContainer">
          <button class="distance">200</button>
          <button class="distance">400</button>
        </div>
        <br />
        <button id="btnWrite" name="btnWrite" onclick="goWrite()">
          자료입력
        </button>
      </div>
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
      let distance = '${defaultDistance}';

      const dbShopInfos = JSON.parse('${shopInfos}');

      const shopInfos = dbShopInfos.map((shopInfo) => {
        const data = new Object();
        data.title = shopInfo.title;
        data.latlng = new kakao.maps.LatLng(shopInfo.lat, shopInfo.lng);
        return data;
      });

      function goWrite() {
        location.href = '${path}/shop_servlet/write.do';
      }

      const distanceBtn = document.querySelector('#buttonContainer');
      distanceBtn.addEventListener('click', (e) => {
        distance = e.target.innerText;
        init();
      });

      function drawCircle(currentLocation) {
        const center = new kakao.maps.LatLng(
          currentLocation.getLat(),
          currentLocation.getLng()
        );
        console.log(center);
        const radius = distance;
        var circle = new kakao.maps.Circle({
          center: center, // 원의 중심좌표 입니다
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

      function setMarkers(lat, lng) {
        const currentLatLng = setCurrentLocationMarker(lat, lng);
        for (let i = 0; i < shopInpos.length; i++) {
          const polyline = getPolylineBetweenMarkers(
            currentLatLng,
            shopInpos[i].latlng
          );
          const distanceBetweenMeAndShop = polyline.getLength();
          if (distanceBetweenMeAndShop <= distance) {
            polyline.setMap(map);
            setShopMarkers(shopInpos[i]);
          }
        }
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

        // 현재 위치 마커 표시
        setCurrentLocationMarker(currentLocation);

        // 현재 위치 기준 원 표시
        drawCircle(currentLocation);

        // 가게 마커 표시
        for (let i = 0; i < shopInfos.length; i++) {
          setShopMarkers(shopInfos[i]);
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
