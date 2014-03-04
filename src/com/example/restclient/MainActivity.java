package com.example.restclient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.example.restclient.RestData.DataModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;

public class MainActivity extends Activity {
	private static final String URL_STR = "https://alpha-api.app.net/stream/0/posts/stream/global";
	
	private ListView listView;
	private ListItemAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView) this.findViewById(R.id.listView);
		adapter = new ListItemAdapter(this);
		listView.setAdapter(adapter);
		GetRestDataTask task = new GetRestDataTask();
		task.execute(URL_STR);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private  class GetRestDataTask extends AsyncTask<String, String, List<RestData.DataModel>> {

		private Gson gson;
		
		public GetRestDataTask() {

			gson = new Gson();		
		}
		@Override
		protected List<DataModel> doInBackground(String... urls) {

			 try {
		            HttpClient httpclient = new DefaultHttpClient();
		            HttpGet httpget = new HttpGet(urls[0]);

		            // Execute HTTP get Request
		            HttpResponse response = httpclient.execute(httpget);

		            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "iso-8859-1"), 8);

		            // parsing data
		            Type type = new TypeToken<RestData>(){}.getType();
		            RestData restData  = gson.fromJson(reader, type);
		            reader.close();
		            
		            return restData.dataModelList;
		        } catch (Exception e) {
		            e.printStackTrace();
		            return null;
		        }
		}
	    @Override
	    protected void onPostExecute( List<RestData.DataModel> resultList) {
	        if (resultList != null) {
	        	adapter.setDataList(resultList);
	        } else {
	            // error occured
	        }
	    }
	}
}
