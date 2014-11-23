package com.android.muetclbookssearching;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;


public class ResponseByKeyWord extends Activity {

	String data = "";
	TableLayout tl;
	//int j;
	TableRow tr;
	TextView label;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_response_by_key_word);		
		tl = (TableLayout) findViewById(R.id.keywordtable);
		
		new Thread(new Runnable() {
			public void run() {
				data = BookSearching.responses;
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						ArrayList<TitleBean> users = parseJSON(data);
						addData(users);						
					}
				});
				
			}
		}).start();
	}//end of onCreate()
	
//=================================================================================================
	
	public ArrayList<TitleBean> parseJSON(String result) {
		ArrayList<TitleBean> users = new ArrayList<TitleBean>();
		try {
			JSONArray jArray = new JSONArray(result);
			for (int i = 0; i <= jArray.length(); i++) {
				//j=i;
				JSONObject json_data = jArray.getJSONObject(i);
				TitleBean user = new TitleBean();
				HashSet<TitleBean> h = new HashSet<TitleBean>(users);
				users.clear();
				users.addAll(h);
				user.setAuthor(json_data.getString("author"));
				user.setTitle(json_data.getString("title"));
				user.setIsbnNo(json_data.getString("isbn_no"));
				user.setShelfNo(json_data.getInt("shelf_no"));
//				user.setShelfNo(json_data.getString("shelf_no"));
//				user.setBookCode(json_data.getString("book_code"));
				user.setBookCode(json_data.getInt("book_code"));
				user.setPublisherName(json_data.getString("publisher_name"));
//				user.setCLName(json_data.getString("co_lname"));
				users.add(user);
			}
		} catch (JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());  
		}
		return users;
	}//end of parseJSON()

//=================================================================================================
	
	 void addHeader(){
	        /** Create a TableRow dynamically **/
	        tr = new TableRow(this);
	        /** Creating a TextView to add to the row **/
	        label = new TextView(this);
	        label.setText("Searched Results by Keyword: ");
	        label.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
	        label.setPadding(5, 5, 5, 5);
	        label.setBackgroundColor(Color.rgb(0, 107, 173));
	        LinearLayout Ll = new LinearLayout(this);
	        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
	                LayoutParams.MATCH_PARENT);
	        params.setMargins(5, 5, 5, 5);
	        Ll.addView(label,params);
	        tr.addView((View)Ll); // Adding textView to tablerow.
	 
	         // Add the TableRow to the TableLayout
	        tl.addView(tr, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.MATCH_PARENT));
	 }//end of addHeader()
	 
//=================================================================================================
	 
	public void addData(ArrayList<TitleBean> users) {

			addHeader();
			
			for (Iterator<TitleBean> i = users.iterator(); i.hasNext();) {
	             
				TitleBean p = (TitleBean) i.next();

				/** Create a TableRow dynamically **/
	            tr = new TableRow(this);
	            tr.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
	                    LayoutParams.FILL_PARENT));
	            /** Creating a TextView to add to the row **/
	            label = new TextView(this);
	            label.setText("Author:"+p.getAuthor()+" , Title: "+p.getTitle()+",ISBN_NO:"+p.getIsbnNo()+"\n, Publisher Name:"+p.getPublisherName()+" ,Shelf No:"+p.getShelfNo()+"\n ,Book Code"+p.getBookCode());//+"\nVolume:"+p.getJVolume()+"\nIssue:"+p.getJIssue()+"\n"+p.getCFName()+p.getCMName()+p.getCLName());
	            label.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
	                    LayoutParams.FILL_PARENT));
	            label.setPadding(5, 5, 5, 5);
	            label.setBackgroundColor(Color.LTGRAY);
	            LinearLayout Ll = new LinearLayout(this);
	            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
	                    LayoutParams.MATCH_PARENT);
	            params.setMargins(5, 2, 2, 2);
	            //Ll.setPadding(10, 5, 5, 5);
	            Ll.addView(label,params);
	            tr.addView((View)Ll); // Adding textView to tablerow.
	 
                // Add the TableRow to the TableLayout
	            tl.addView(tr, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.MATCH_PARENT));		}
    }//end of addData()
	
//=================================================================================================

}//end of class
