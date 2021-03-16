package map.model.dto;

import java.util.Date;

public class ShopInfoDTO {
	// Field
	private String id;
	private int no;
	private String shopName;
	private double latitude;
	private double longitude;
	private String address;
	private String shopUrl;
	private String instagram;
	private Date regiDate;
	private String preShop;
	private String nxtShop;
	private int preNo;
	private int nxtNo;
	
	// Constructor
	public ShopInfoDTO() {}

	// Getters and Setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getShopUrl() {
		return shopUrl;
	}

	public void setShopUrl(String shopUrl) {
		this.shopUrl = shopUrl;
	}

	public String getInstagram() {
		return instagram;
	}

	public void setInstagram(String instagram) {
		this.instagram = instagram;
	}

	public Date getRegiDate() {
		return regiDate;
	}

	public void setRegiDate(Date regiDate) {
		this.regiDate = regiDate;
	}

	public String getPreShop() {
		return preShop;
	}

	public void setPreShop(String preShop) {
		this.preShop = preShop;
	}

	public String getNxtShop() {
		return nxtShop;
	}

	public void setNxtShop(String nxtShop) {
		this.nxtShop = nxtShop;
	}

	public int getPreNo() {
		return preNo;
	}

	public void setPreNo(int preNo) {
		this.preNo = preNo;
	}

	public int getNxtNo() {
		return nxtNo;
	}

	public void setNxtNo(int nxtNo) {
		this.nxtNo = nxtNo;
	}
}
