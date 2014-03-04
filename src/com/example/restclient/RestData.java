package com.example.restclient;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class RestData {
	
	public static class MetaDataModel {
		@SerializedName("code")
		public String code;
	}
	
	public static class UserModel {
		@SerializedName("username")
		public String userName;
	}
	

	public static class DataModel {
		public String text;
		public UserModel user;
	}

	@SerializedName("meta")
	MetaDataModel meta;
	
	@SerializedName("data")
	List<DataModel> dataModelList;
	
}
