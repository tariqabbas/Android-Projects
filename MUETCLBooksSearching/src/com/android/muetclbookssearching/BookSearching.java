package com.android.muetclbookssearching;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BookSearching extends Activity {
	EditText ettitlebook;
	EditText etauthorname;
	EditText etkeyword;
	Button titleSearchButton;
	Button authorSearchButton;
	Button keywordSearchButton;
	TextView tv;
    HttpPost httppost;
    HttpResponse response;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;
    ProgressDialog dialog = null;
    static String responses;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_searching);
		ettitlebook=(EditText)findViewById(R.id.etbookname);
		etauthorname=(EditText)findViewById(R.id.etauthorname);
		etkeyword=(EditText)findViewById(R.id.etkeyword);
		titleSearchButton=(Button)findViewById(R.id.btnBooksearch);
		authorSearchButton=(Button)findViewById(R.id.btnauthor);
		keywordSearchButton=(Button)findViewById(R.id.btnkeytword);
		
		tv=(TextView)findViewById(R.id.tv);
		
		titleSearchButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog = ProgressDialog.show(BookSearching.this, "", "Collecting Data...", true);
				new Thread(new Runnable() {
					public void run() {
						Runtime.getRuntime().maxMemory();
						searchByTitle();		
					    Log.e("Memory",""+Runtime.getRuntime().totalMemory());
				    }
				  }).start();
			}//end of onClick method
			
		});//end of OnClick Event
		authorSearchButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog = ProgressDialog.show(BookSearching.this, "", "Collecting Data...", true);
				new Thread(new Runnable() {
					public void run() {
						Runtime.getRuntime().maxMemory();
						searchByAuthor();		
					    Log.e("Memory",""+Runtime.getRuntime().totalMemory());
				    }
				  }).start();
			}//end of onclick Method
			
		});//end of OnClic Event
		
		keywordSearchButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog = ProgressDialog.show(BookSearching.this, "", "Collecting Data...", true);
				new Thread(new Runnable() {
					public void run() {
						Runtime.getRuntime().maxMemory();
						searchByKeyWord();		
					    Log.e("Memory",""+Runtime.getRuntime().totalMemory());
				    }
				  }).start();
			}//end of onClick method
			
		});//end of OnClick Event
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.book_searching, menu);
		return true;
	}
	public void searchByTitle(){
	 
		httpclient=new DefaultHttpClient();
		httppost= new HttpPost("http://10.0.2.2/connectphp/TitleSearch.php"); // make sure the url is correct.
		//add your data

		try{
			nameValuePairs = new ArrayList<NameValuePair>(2);
			// Always use the same variable name for posting i.e the android side variable name and php side variable name should be similar, 
			nameValuePairs.add(new BasicNameValuePair("ettitlebook",ettitlebook.getText().toString()));  // $Edittext_value = $_POST['Edittext_value']; 
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			//Execute HTTP Post Request
			response=httpclient.execute(httppost);
			//Response Handler
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			Runtime.getRuntime().maxMemory();
			responses = httpclient.execute(httppost, responseHandler).toString();
			System.out.println("Response : " + responses);
			
			runOnUiThread(new Runnable() {
				public void run() {
   	//tv.setText("Response from PHP : " + responseOfTheAuthor);
		dialog.dismiss();
				}
			});
	
			startActivity(new Intent(BookSearching.this,ResponseByTitle.class));
	
		}catch(Exception e){			
			e.printStackTrace();
			Log.e(e.getMessage(), "");
			dialog.dismiss();

		}
	}//end of searchByTitle
	public void searchByAuthor(){
		
		httpclient=new DefaultHttpClient();
        httppost= new HttpPost("http://10.0.2.2/connectphp/AuthorSearch.php"); // make sure the url is correct.
		//add your data
        
        try{
		nameValuePairs = new ArrayList<NameValuePair>(2);
		// Always use the same variable name for posting i.e the android side variable name and php side variable name should be similar, 
		nameValuePairs.add(new BasicNameValuePair("etauthorname",etauthorname.getText().toString()));  // $Edittext_value = $_POST['Edittext_value']; 
		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		//Execute HTTP Post Request
		response=httpclient.execute(httppost);
        //Response Handler
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		Runtime.getRuntime().maxMemory();
		responses = httpclient.execute(httppost, responseHandler).toString();
		System.out.println("Response : " + responses);
					
		runOnUiThread(new Runnable() {
		    public void run() {
		    	//tv.setText("Response from PHP : " + responseOfTheAuthor);
				dialog.dismiss();
		    }
		});
			
		startActivity(new Intent(BookSearching.this,ResponseByAuthor.class));
			
	}catch(Exception e){			
		e.printStackTrace();
		Log.e(e.getMessage(), "");
		dialog.dismiss();
		
		}
	}//end of search by author name
	public void searchByKeyWord(){
		
		
		httpclient=new DefaultHttpClient();
        httppost= new HttpPost("http://10.0.2.2/connectphp/KeywordSearch.php"); // make sure the url is correct.
		//add your data
        
        try{
		nameValuePairs = new ArrayList<NameValuePair>(2);
		// Always use the same variable name for posting i.e the android side variable name and php side variable name should be similar, 
		nameValuePairs.add(new BasicNameValuePair("etkeyword",etkeyword.getText().toString()));  // $Edittext_value = $_POST['Edittext_value']; 
		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		//Execute HTTP Post Request
		response=httpclient.execute(httppost);
        //Response Handler
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		Runtime.getRuntime().maxMemory();
		responses = httpclient.execute(httppost, responseHandler).toString();
		System.out.println("Response : " + responses);
					
		runOnUiThread(new Runnable() {
		    public void run() {
		    	//tv.setText("Response from PHP : " + responseOfTheAuthor);
				dialog.dismiss();
		    }
		});
			
		startActivity(new Intent(BookSearching.this,ResponseByKeyWord.class));
			
	}catch(Exception e){			
		e.printStackTrace();
		Log.e(e.getMessage(), "");
		dialog.dismiss();
		
		}

	}//end of search by keyword

}

