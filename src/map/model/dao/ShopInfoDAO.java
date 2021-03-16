package map.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import db.DbExample;
import map.model.dto.ShopInfoDTO;
import sqlmap.MybatisManager;

public class ShopInfoDAO {
	// Field
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	final String SHOP_INFO = "shopInfo";
	
	// Method
	public Connection getConn() {
		conn = DbExample.getConn();
		return conn;
	}
	
	public void getConnClose(ResultSet rs, PreparedStatement pstmt, Connection conn) {
		DbExample.getConnClose(rs, pstmt, conn);
	}

	public int setInsert(ShopInfoDTO dto) {
//		Map<String, Object> map = new HashMap<>();
//		map.put("dto", dto);
//		map.put("SHOP_INFO", SHOP_INFO);
		
//		SqlSession session = MybatisManager.getInstance().openSession();
//		int result = session.insert("shopInfo.setInsert", dto);
//		session.close();
//		System.out.println(result);
//		return result;
		
		conn = getConn();
		int result = 0;
		try {
			String sql = "INSERT INTO " + SHOP_INFO + " VALUES (seq_shopInfo.NEXTVAL, "
					+ "?, ?, ?, ?, ?, ?, ?, SYSDATE)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getId());
			pstmt.setDouble(2, dto.getLatitude());
			pstmt.setDouble(3, dto.getLongitude());
			pstmt.setString(4, dto.getShopName());
			pstmt.setString(5, dto.getInstagram());
			pstmt.setString(6, dto.getAddress());
			pstmt.setString(7, dto.getShopUrl());
			result = pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			getConnClose(rs, pstmt, conn);
		}
		return result;
	}

	public int getAllRowsCount(String search_option, String search_data) {
		conn = getConn();
		int allRowsCount = 0;
		try {
			String sql = "SELECT COUNT(*) FROM " + SHOP_INFO + " WHERE no > 0 ";
			
			if (search_option.length() > 0 && search_data.length() > 0) {
				if (search_option.equals("name") || search_option.equals("instagram") || search_option.equals("shopUrl")) {
					sql += " AND " + search_option + " LIKE ? ";
				} else if (search_option.equals("name_instagram_shopUrl")) {
					sql += " and (name LIKE ? OR instagram LIKE ? OR shopUrl LIKE ?) ";
				}
			}
			
			int pstmtNum = 0;
			pstmt = conn.prepareStatement(sql);
			
			if (search_option.length() > 0 && search_data.length() > 0) {
				if (search_option.equals("name") || search_option.equals("instagram") || search_option.equals("shopUrl")) {
					pstmt.setString(++pstmtNum, '%' + search_data + '%');
				} else if (search_option.equals("name_instagram_shopUrl")) {
					pstmt.setString(++pstmtNum, '%' + search_data + '%');
					pstmt.setString(++pstmtNum, '%' + search_data + '%');
					pstmt.setString(++pstmtNum, '%' + search_data + '%');
				}
			}
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				allRowsCount = rs.getInt(1);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			getConnClose(rs, pstmt, conn);
		}
		
		return allRowsCount;
	}

	public ArrayList<ShopInfoDTO> getPagingList(int startNum, int endNum, String search_option, String search_data) {
		conn = getConn();
		ArrayList<ShopInfoDTO> list = new ArrayList<>();
		try {
			String basic_sql = "SELECT * FROM " + SHOP_INFO + " WHERE no > 0 ";
			
			if (search_option.length() > 0 && search_data.length() > 0) {
				if (search_option.equals("name") || search_option.equals("instagram") || search_option.equals("shopUrl")) {
					basic_sql += " AND " + search_option + " LIKE ? ";
				} else if (search_option.equals("name_instagram_shopUrl")) {
					basic_sql += " and (name LIKE ? OR instagram LIKE ? OR shopUrl LIKE ?) ";
				}
			}
			
			basic_sql += "ORDER BY no DESC";
			
			String sql = "";
			sql += "SELECT * FROM (SELECT A.*, Rownum Rnum FROM (" + basic_sql + ") A) ";
			sql += "WHERE Rnum >= ? AND Rnum <= ?";
			
			int pstmtNum = 0;
			pstmt = conn.prepareStatement(sql);
			
			if (search_option.length() > 0 && search_data.length() > 0) {
				if (search_option.equals("name") || search_option.equals("instagram") || search_option.equals("shopUrl")) {
					pstmt.setString(++pstmtNum, '%' + search_data + '%');
				} else if (search_option.equals("name_instagram_shopUrl")) {
					pstmt.setString(++pstmtNum, '%' + search_data + '%');
					pstmt.setString(++pstmtNum, '%' + search_data + '%');
					pstmt.setString(++pstmtNum, '%' + search_data + '%');
				}
			}
			
			pstmt.setInt(++pstmtNum, startNum);
			pstmt.setInt(++pstmtNum, endNum);
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ShopInfoDTO dto = new ShopInfoDTO();
				dto.setNo(rs.getInt("no"));
				dto.setId(rs.getString("id"));
				dto.setLatitude(rs.getDouble("latitude"));
				dto.setLongitude(rs.getDouble("longitude"));
				dto.setShopName(rs.getString("shopName"));
				dto.setInstagram(rs.getString("instagram"));
				dto.setAddress(rs.getString("address"));
				dto.setShopUrl(rs.getString("shopUrl"));
				dto.setRegiDate(rs.getDate("regiDate"));
				list.add(dto);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			getConnClose(rs, pstmt, conn);
		}
		
		return list;
	}

	public ShopInfoDTO getView(int no) {
		ShopInfoDTO dto = new ShopInfoDTO();
		conn = getConn();
		try {
			String sql = "";
			sql += "SELECT no, id, latitude, longitude, shopName, instagram, ";
			sql += "address, shopUrl, regiDate, preNo, preShop, nxtNo, nxtShop FROM ";
			sql += "(";
			sql += "SELECT s.*, ";
			sql += "LAG(no) OVER (ORDER BY no DESC) preNo, ";
			sql += "LAG(shopName) OVER (ORDER BY no DESC) preShop, ";
			sql += "LEAD(no) OVER (ORDER BY no DESC) nxtNo, ";
			sql += "LEAD(shopName) OVER (ORDER BY no DESC) nxtShop ";
			sql += "FROM " + SHOP_INFO + " s ORDER BY no DESC";
			sql += ") WHERE no = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				dto.setNo(rs.getInt("no"));
				dto.setId(rs.getString("id"));
				dto.setLatitude(rs.getDouble("latitude"));
				dto.setLongitude(rs.getDouble("longitude"));
				dto.setShopName(rs.getString("shopName"));
				dto.setInstagram(rs.getString("instagram"));
				dto.setAddress(rs.getString("address"));
				dto.setShopUrl(rs.getString("shopUrl"));
				dto.setRegiDate(rs.getDate("regiDate"));

				dto.setPreNo(rs.getInt("preNo"));
				dto.setPreShop(rs.getString("preShop"));
				dto.setNxtNo(rs.getInt("nxtNo"));
				dto.setNxtShop(rs.getString("nxtShop"));
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			getConnClose(rs, pstmt, conn);
		}
		
		return dto;
	}

	public int setUpdate(ShopInfoDTO dto) {
		conn = getConn();
		int result = 0;
		try {
			String sql = "UPDATE " + SHOP_INFO + " SET "
					+ "latitude = ?, "
					+ "longitude = ?, "
					+ "shopName = ?, "
					+ "instagram = ?, "
					+ "address = ?, "
					+ "shopUrl = ? "
					+ "WHERE no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setDouble(1, dto.getLatitude());
			pstmt.setDouble(2, dto.getLongitude());
			pstmt.setString(3, dto.getShopName());
			pstmt.setString(4, dto.getInstagram());
			pstmt.setString(5, dto.getAddress());
			pstmt.setString(6, dto.getShopUrl());
			pstmt.setInt(7, dto.getNo());

			result = pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			getConnClose(rs, pstmt, conn);
		}
		
		return result;
	}

	public int setDelete(ShopInfoDTO dto) {
		conn = getConn();
		int result = 0;
		try {
			String sql = "DELETE FROM " + SHOP_INFO + " WHERE no = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getNo());
			result = pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			getConnClose(rs, pstmt, conn);
		}
		return result;
	}

	public ArrayList<ShopInfoDTO> getAllShopInfos() {
		conn = getConn();
		ArrayList<ShopInfoDTO> list = new ArrayList<>();
		try {
			String sql = "SELECT * FROM " + SHOP_INFO + " ORDER BY no DESC";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ShopInfoDTO dto = new ShopInfoDTO();
				dto.setNo(rs.getInt("no"));
				dto.setId(rs.getString("id"));
				dto.setLatitude(rs.getDouble("latitude"));
				dto.setLongitude(rs.getDouble("longitude"));
				dto.setShopName(rs.getString("shopName"));
				dto.setInstagram(rs.getString("instagram"));
				dto.setAddress(rs.getString("address"));
				dto.setShopUrl(rs.getString("shopUrl"));
				dto.setRegiDate(rs.getDate("regiDate"));
				list.add(dto);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			getConnClose(rs, pstmt, conn);
		}
		
		return list;
	}

}
