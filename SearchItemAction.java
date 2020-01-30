package com.internousdev.espresso.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.espresso.dao.MCategoryDAO;
import com.internousdev.espresso.dao.ProductInfoDAO;
import com.internousdev.espresso.dto.MCategoryDTO;
import com.internousdev.espresso.dto.ProductInfoDTO;
import com.internousdev.espresso.util.InputChecker;
import com.opensymphony.xwork2.ActionSupport;

public class SearchItemAction extends ActionSupport implements SessionAware {
	private String searchItemName;
	private int categoryId;
	private List<String> errorList;
	private List<ProductInfoDTO> productInfoDTOList;
	private Map<String, Object> session;

	public String execute() {
		if (StringUtils.isBlank(searchItemName)) {
			searchItemName = "";
		} else {
			InputChecker check = new InputChecker();
			errorList = check.doCheck("検索ワード", searchItemName, 0, 50, true, true, true, true, true, true);
			if (errorList.size() > 0) {
				// 入力が不正ならエラーメッセージを商品一覧画面に遷移
				return SUCCESS;
			}
			//キーワードの"　"を" "に変換する。
			//"２個以上"を" "に変換する。
			//先頭と末尾のスペースを削除する。
			searchItemName = searchItemName.replaceAll("　", " ").replaceAll("\\s{2,}", " ").trim();
		}

		//カテゴリーが表示されていない場合は全てカテゴリーを選択しているものとする。
		if (categoryId == 0) {
			categoryId = 1;
		}
		//カテゴリーが全て選択されている場合と一つのカテゴリーを選択している場合での条件分岐
		ProductInfoDAO productInfoDAO = new ProductInfoDAO();
		String[] searchItemNameList = searchItemName.split(" ");//←検索ワードを分割してsearchItemNameListに格納している。
		switch (categoryId) {
		case 1:
			productInfoDTOList = productInfoDAO.getProductInfoListByKeyword(searchItemNameList);
			break;
		default:
			productInfoDTOList = productInfoDAO.getProductInfoListByCategoryIdAndKeyword(categoryId,
					searchItemNameList);
			break;
		}
		//カテゴリーのリストが表示されていないのはよくないので、作成する。
		if (!session.containsKey("mCategoryDTOList")) {
			List<MCategoryDTO> mCategoryDTOList = new ArrayList<MCategoryDTO>();
			MCategoryDAO mCategoryDAO = new MCategoryDAO();
			mCategoryDTOList = mCategoryDAO.getMCategoryList();
			session.put("mCategoryDTOList", mCategoryDTOList);
		}

		return SUCCESS;
	}

	public String getSearchItemName() {
		return searchItemName;
	}

	public void setSearchItemName(String searchItemName) {
		this.searchItemName = searchItemName;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public List<String> getErrorList() {
		return errorList;
	}

	public void setErrorList(List<String> errorList) {
		this.errorList = errorList;
	}

	public List<ProductInfoDTO> getProductInfoDTOList() {
		return productInfoDTOList;
	}

	public void setProductInfoDTOList(List<ProductInfoDTO> productInfoDTOList) {
		this.productInfoDTOList = productInfoDTOList;
	}
}