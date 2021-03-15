package map.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import db.DbExample;
import map.model.dto.ShopInfoDTO;

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
		conn = getConn();
		int result = 0;
		try {
			String sql = "INSERT INTO " + SHOP_INFO + " VALUES (seq_shopInfo.NEXTVAL, "
					+ "?, ?, ?, ?, ?, ?, ?, SYSDATE)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getId());
			pstmt.setDouble(2, dto.getLatitude());
			pstmt.setDouble(3, dto.getLongitude());
			pstmt.setString(4, dto.getName());
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
				dto.setName(rs.getString("name"));
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
		// TODO Auto-generated method stub
		return null;
	}

}
