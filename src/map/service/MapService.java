package map.service;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import map.model.dao.ShopInfoDAO;
import map.model.dto.ShopInfoDTO;
import oracle.net.aso.l;

public class MapService {
	public JSONArray getShopData(Double currentLat, Double currentLng, Double distance) {
		ShopInfoDAO dao = new ShopInfoDAO();
		List<ShopInfoDTO> shopInfosByDistance = dao.getShopInfos(currentLat, currentLng, distance);
		
		JSONArray shopInfos = new JSONArray();
		
		for (ShopInfoDTO dto : shopInfosByDistance) {
			JSONObject shopInfo = new JSONObject();
			shopInfo.put("no", dto.getNo());
			shopInfo.put("id", dto.getId());
			shopInfo.put("lat", dto.getLatitude());
			shopInfo.put("lng", dto.getLongitude());
			shopInfo.put("title", dto.getShopName());
			shopInfo.put("instagram", dto.getInstagram());
			shopInfo.put("address", dto.getAddress());
			shopInfo.put("shopUrl", dto.getShopUrl());
			
			
//			String parseInstaId = dto.getInstagram();
//			
//			InstaParser parser = new InstaParser(parseInstaId);
//			ArrayList<String> imgTagSrcs = parser.crawl();
//			
//			JSONArray imgTagSrcsJson = new JSONArray();
//			for (int i = 0; i < imgTagSrcs.size(); i++) {
//				imgTagSrcsJson.add(imgTagSrcs.get(i));
//			}
//			shopInfo.put("imgTagSrcs", imgTagSrcsJson);
			
			shopInfos.add(shopInfo);
		}
		
		String[] backgroundColors = { "#AACFD0", "#A0D097", "#FFFCCB", "#FFA54E" };
		
		for (int i = 0; i < shopInfos.size(); i++) {
			JSONObject shopInfo = (JSONObject) shopInfos.get(i);
			int j = i;
			if (j > 3) {
				j = j % 4;
			}
			shopInfo.put("backgroundColor", backgroundColors[j]);
		}
		

		
		return shopInfos;
	}
}
