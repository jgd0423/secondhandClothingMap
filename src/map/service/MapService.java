package map.service;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import map.model.dao.ShopInfoDAO;
import map.model.dto.ShopInfoDTO;

public class MapService {
	public JSONArray getShopData(Double currentLat, Double currentLng, Double distance) {
		ShopInfoDAO dao = new ShopInfoDAO();
		List<ShopInfoDTO> shopInfosByDistance = dao.getShopInfos(currentLat, currentLng, distance);
		
		JSONArray shopInfos = new JSONArray();
		
		for (ShopInfoDTO dto : shopInfosByDistance) {
			JSONObject shopInfo = new JSONObject();
			shopInfo.put("title", dto.getShopName());
			shopInfo.put("lat", dto.getLatitude());
			shopInfo.put("lng", dto.getLongitude());
			shopInfos.add(shopInfo);
		}
		
		return shopInfos;
	}
}
