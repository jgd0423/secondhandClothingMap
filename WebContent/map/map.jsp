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
        위도 : <span id="latitude"></span>
        <br />
        경도 : <span id="longitude"></span>
      </div>
    </div>
    <script
      type="text/javascript"
      src="//dapi.kakao.com/v2/maps/sdk.js?appkey=371135d2edf4452e33d26fcd16dc4384"
    ></script>
    <script>
      const COORDS = 'coords';
      let latitude = '';
      let longitude = '';

      function setCurrentLocationMap(lat, lng) {
        var container = document.getElementById('map');
        var options = {
          center: new kakao.maps.LatLng(35.86120949251559, 128.59938944544942),
          level: 3,
        };
        var map = new kakao.maps.Map(container, options);
        var markerPosition = new kakao.maps.LatLng(
          35.86120949251559,
          128.59938944544942
        );
        var marker = new kakao.maps.Marker({
          position: markerPosition,
        });

        marker.setMap(map);

        latitude = lat;
        longitude = lng;

        const spanLat = document.querySelector('#latitude');
        const spanLng = document.querySelector('#longitude');
        spanLat.textContent = latitude;
        spanLng.textContent = longitude;

        var positions = [
          {
            title: "Kick's",
            latlng: new kakao.maps.LatLng(35.86099, 128.59818),
          },
          {
            title: 'Oltremare',
            latlng: new kakao.maps.LatLng(35.86209, 128.59572),
          },
        ];

        // 마커 이미지의 이미지 주소입니다
        var imageSrc =
          'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png';

        for (var i = 0; i < positions.length; i++) {
          // 마커 이미지의 이미지 크기 입니다
          var imageSize = new kakao.maps.Size(24, 35);

          // 마커 이미지를 생성합니다
          var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

          // 마커를 생성합니다
          var marker = new kakao.maps.Marker({
            map: map, // 마커를 표시할 지도
            position: positions[i].latlng, // 마커를 표시할 위치
            title: positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
            image: markerImage, // 마커 이미지
          });
        }
      }

      function handleGeoSucces(position) {
        const latitude = position.coords.latitude;
        const longitude = position.coords.longitude;
        setCurrentLocationMap(latitude, longitude);
      }

      function handleGeoError() {
        console.log("Can't access geo location");
      }

      function askForCoords() {
        navigator.geolocation.getCurrentPosition(
          handleGeoSucces,
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
