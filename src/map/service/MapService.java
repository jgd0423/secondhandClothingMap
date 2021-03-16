package map.service;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import map.model.dao.ShopInfoDAO;
import map.model.dto.ShopInfoDTO;

public class MapService {
	public JSONArray getShopData() {
		ShopInfoDAO dao = new ShopInfoDAO();
		List<ShopInfoDTO> allShopInfos = dao.getShopInfos();
		
		JSONArray shopInfos = new JSONArray();
		
		for (ShopInfoDTO dto : allShopInfos) {
			JSONObject shopInfo = new JSONObject();
			shopInfo.put("title", dto.getShopName());
			shopInfo.put("lat", dto.getLatitude());
			shopInfo.put("lng", dto.getLongitude());
			shopInfos.add(shopInfo);
		}
		
		return shopInfos;
	}
}
