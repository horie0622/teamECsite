package com.internousdev.espresso.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.internousdev.espresso.dto.ProductInfoDTO;
import com.internousdev.espresso.util.DBConnector;

public class ProductInfoDAO {

	/**
	 * 商品IDを条件として商品情報を取り出す
	 * @param productId 商品ID
	 * @return 商品情報
	 */

	public ProductInfoDTO getProductInfoByProductId(int productId) {

		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		ProductInfoDTO productInfoDTO = new ProductInfoDTO();
		String sql = "select * from product_info where product_id=?";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, productId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				productInfoDTO.setId(rs.getInt("id"));
				productInfoDTO.setProductId(rs.getInt("product_id"));
				productInfoDTO.setProductName(rs.getString("product_name"));
				productInfoDTO.setProductNameKana(rs.getString("product_name_kana"));
				productInfoDTO.setProductDescription(rs.getString("product_description"));
				productInfoDTO.setCategoryId(rs.getInt("category_id"));
				productInfoDTO.setPrice(rs.getInt("price"));
				productInfoDTO.setImgFilePath(rs.getString("image_file_path"));
				productInfoDTO.setImgFileName(rs.getString("image_file_name"));
				productInfoDTO.setReleaseDate(rs.getDate("release_date"));
				productInfoDTO.setReleaseCompany(rs.getString("release_company"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return productInfoDTO;
	}

	/**
	 * 関連商品を取得する。
	 * productDetailsActionで使用
	 * @param categoryId カテゴリーID
	 * @param productId 商品ID
	 *
	 * @param limitOffset データを取得する開始位置
	 * @param limitRowCount データ取得件数
	 * @return 関連商品情報
	 */

	public List<ProductInfoDTO> getRelatedProductList(int categoryId, int productId, int limitOffset,
			int limitRowCount) {

		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		List<ProductInfoDTO> productInfoDTOList = new ArrayList<ProductInfoDTO>();
		String sql = "select * from product_info where category_id=? and product_id not in(?) order by rand() limit ?,?";

		/**
		 * round():表示順をランダムにする。
		 * limit 0,3:0番目から3件データを取得する
		 */

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, categoryId);
			ps.setInt(2, productId);
			ps.setInt(3, limitOffset);
			ps.setInt(4, limitRowCount);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ProductInfoDTO productInfoDTO = new ProductInfoDTO();
				productInfoDTO.setId(rs.getInt("id"));
				productInfoDTO.setProductId(rs.getInt("product_id"));
				productInfoDTO.setProductName(rs.getString("product_name"));
				productInfoDTO.setProductNameKana(rs.getString("product_name_kana"));
				productInfoDTO.setProductDescription(rs.getString("product_description"));
				productInfoDTO.setCategoryId(rs.getInt("category_id"));
				productInfoDTO.setPrice(rs.getInt("price"));
				productInfoDTO.setImgFilePath(rs.getString("image_file_path"));
				productInfoDTO.setImgFileName(rs.getString("image_file_name"));
				productInfoDTO.setReleaseDate(rs.getDate("release_date"));
				productInfoDTO.setReleaseCompany(rs.getString("release_company"));
				productInfoDTOList.add(productInfoDTO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return productInfoDTOList;
	}

	/**
	 * 検索ワードを条件に商品情報を取得する。
	 * searchItemNameList 検索ワードの配列
	 * return 商品情報のList
	 */

	public List<ProductInfoDTO> getProductInfoListByKeyword(String[] searchItemNameList) {
		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		List<ProductInfoDTO> productInfoDTOList = new ArrayList<ProductInfoDTO>();
		String sql = "SELECT * FROM product_info";
		//要素として" "が存在する場合は、検索ワードを指定していない時のみなので０番目が" "の場合のみWhere句を追加しないようにする

		if (!"".equals(searchItemNameList[0])) {//検索時のワードの数だけまわす

			for (int i = 0; i < searchItemNameList.length; i++) {//(ここでfor拡張文を使わない理由→indexを使用した方がわかりやすいため)
				if (i == 0) {//検索ワードの1個目
					sql += " where (product_name like '%" + searchItemNameList[i] + "%' or product_name_kana like '%"
							+ searchItemNameList[i] + "%')";
					//漢字か平仮名での曖昧検索

				} else {//検索ワードの2個目以降
					sql += " or (product_name like '%" + searchItemNameList[i] + "%' or product_name_kana like '%"
							+ searchItemNameList[i] + "%')";
				}
			}
		}
		sql += " order by product_id asc";//昇順で並べる。

		try {
			PreparedStatement ps = con.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ProductInfoDTO productInfoDTO = new ProductInfoDTO();
				productInfoDTO.setId(rs.getInt("id"));
				productInfoDTO.setProductId(rs.getInt("product_id"));
				productInfoDTO.setProductName(rs.getString("product_name"));
				productInfoDTO.setProductNameKana(rs.getString("product_name_kana"));
				productInfoDTO.setProductDescription(rs.getString("product_description"));
				productInfoDTO.setPrice(rs.getInt("price"));
				productInfoDTO.setCategoryId(rs.getInt("category_id"));
				productInfoDTO.setImgFilePath(rs.getString("image_file_path"));
				productInfoDTO.setImgFileName(rs.getString("image_file_name"));
				productInfoDTO.setReleaseDate(rs.getDate("release_date"));
				productInfoDTO.setReleaseCompany(rs.getString("release_company"));
				productInfoDTOList.add(productInfoDTO);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return productInfoDTOList;
	}

	/**
	 * 商品検索((検索ワードとカテゴリーIdを元に商品情報を取得する)
	 * 検索ワード searchItemName
	 * カテゴリーID categoryID
	 * return 商品情報のList
	 */
	public List<ProductInfoDTO> getProductInfoListByCategoryIdAndKeyword(int categoryId, String[] searchItemNameList) {
		DBConnector db = new DBConnector();
		Connection con = db.getConnection();
		List<ProductInfoDTO> productInfoDTOList = new ArrayList<ProductInfoDTO>();
		String sql = "SELECT * FROM product_info where category_id =?";

		if (!"".equals(searchItemNameList[0])) {// 要素として" "が存在する場合は、検索ワードを指定していない時のみなので０番目が" "の場合のみWhere句を追加しないようにする
			//ここでfor拡張文を使わない理由→indexを使用した方がわかりやすいため。
			for (int i = 0; i < searchItemNameList.length; i++) {
				if (i == 0) {
					sql += " and ((product_name like '%" + searchItemNameList[i] + "%' or product_name_kana like '%"
							+ searchItemNameList[i] + "%')";
				} else {
					sql += " or (product_name like '%" + searchItemNameList[i] + "%' or product_name_kana like '%"
							+ searchItemNameList[i] + "%')";
				}
			}
			sql += ")";
		}
		sql += " order by product_id asc";
		try {
			PreparedStatement ps = con.prepareStatement(sql);

			ps.setInt(1, categoryId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ProductInfoDTO productInfoDTO = new ProductInfoDTO();
				productInfoDTO.setId(rs.getInt("id"));
				productInfoDTO.setProductId(rs.getInt("product_id"));
				productInfoDTO.setProductName(rs.getString("product_name"));
				productInfoDTO.setProductNameKana(rs.getString("product_name_kana"));
				productInfoDTO.setProductDescription(rs.getString("product_description"));
				productInfoDTO.setPrice(rs.getInt("price"));
				productInfoDTO.setCategoryId(rs.getInt("category_id"));
				productInfoDTO.setImgFilePath(rs.getString("image_file_path"));
				productInfoDTO.setImgFileName(rs.getString("image_file_name"));
				productInfoDTO.setReleaseDate(rs.getDate("release_date"));
				productInfoDTO.setReleaseCompany(rs.getString("release_company"));
				productInfoDTOList.add(productInfoDTO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return productInfoDTOList;
	}
}