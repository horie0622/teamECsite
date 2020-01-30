package com.internousdev.espresso.dto;

import java.util.Date;

public class ProductInfoDTO {
	private int Id;
	private int productId;
	private String productName;
	private String productNameKana;
	private String imgFilePath;
	private String imgFileName;
	private int price;
	private int categoryId;
	private Date releaseDate;
	private String releaseCompany;
	private String productDescription;

	//ID
	public int getId() {
		return Id;
	}
	public void setId(int Id) {
		this.Id = Id;
	}

	//商品ID
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}

	//商品名
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	//商品名ふりがな
	public String getProductNameKana() {
		return productNameKana;
	}
	public void setProductNameKana(String productNameKana) {
		this.productNameKana = productNameKana;
	}

	//商品画像
	public String getImgFilePath() {
		return imgFilePath;
	}
	public void setImgFilePath(String imgFilePath) {
		this.imgFilePath = imgFilePath;
	}

	//商品画像名
	public String getImgFileName() {
		return imgFileName;
	}
	public void setImgFileName(String imgFileName) {
		this.imgFileName = imgFileName;
	}

	//値段
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}

	//カテゴリーID
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	//発売年月日
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	//発売会社名
	public String getReleaseCompany() {
		return releaseCompany;
	}
	public void setReleaseCompany(String releaseCompany) {
		this.releaseCompany = releaseCompany;
	}

	//商品詳細情報
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
}
