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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UserAuthorization extends Activity {
	Button login;
	EditText etusername,etpass;
	TextView tv;
	HttpPost httppost;
    StringBuffer buffer;
    HttpResponse response;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;
    ProgressDialog dialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_authorization);
		login=(Button)findViewById(R.id.btnlogin);
		etusername=(EditText)findViewById(R.id.username);
		etpass=(EditText)findViewById(R.id.password);
		tv=(TextView)findViewById(R.id.tv);
		
		login.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(haveNetworkConnection()){
					dialog = ProgressDialog.show(UserAuthorization.this, "", "Validating User...", true);
					new Thread(new Runnable(){

						@Override
						public void run() {
							// TODO Auto-generated method stub
							Runtime.getRuntime().maxMemory();
							userValidation();
							Log.e("Memory",""+Runtime.getRuntime().totalMemory());
							
						}
						
					}).start();
					
				}//end of if
				else{
					Toast.makeText(UserAuthorization.this, "No Network Connection!!!", Toast.LENGTH_LONG).show();
				}//end of else
			}//end of OnClick method
			
		});//end of OnClick event
	}
	void userValidation(){
		try{
		httpclient=new DefaultHttpClient();
		httppost=new HttpPost("http://10.0.2.2/connectphp/login.php");//type url here make sure it is correct
		nameValuePairs=new ArrayList<NameValuePair>(2);//add your data
		// Always use the same variable name for posting i.e the android side variable name and php side variable name should be similar,
		nameValuePairs.add(new BasicNameValuePair("username",etusername.getText().toString().trim()));
		nameValuePairs.add(new BasicNameValuePair("password",etpass.getText().toString().trim()));
		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		//execute http  POST request
		response=httpclient.execute(httppost);
		//edited
		ResponseHandler<String> responseHandler=new BasicResponseHandler();
		final String response=httpclient.execute(httppost,responseHandler);
		System.out.println("Response : " + response); 
		runOnUiThread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				//tv.setText("Response from PHP : " + response);
                dialog.dismiss();	
			}
			
		});
		 if(response.equalsIgnoreCase("User Found")){
           runOnUiThread(new Runnable() {
           public void run() {
               Toast.makeText(UserAuthorization.this,"Login Success", Toast.LENGTH_SHORT).show();
           }
       });
        
       startActivity(new Intent(UserAuthorization.this, BookSearching.class));
       System.out.println("intent is calling properly");
		 }
		 else{
			 showAlert();
		 }
	}catch(Exception e){
		 dialog.dismiss();
         System.out.println("Exception : " + e.getMessage());
		
	}
}
public void showAlert(){
	UserAuthorization.this.runOnUiThread(new Runnable() {
        public void run() {
            AlertDialog.Builder builder = new AlertDialog.Builder(UserAuthorization.this);
            builder.setTitle("Login Error.");
            builder.setMessage("User not Found.")  
                   .setCancelable(false)
                   .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int id) {
                       }
                   });                     
            AlertDialog alert = builder.create();
            alert.show();               
        }
    });
	
}
private boolean haveNetworkConnection(){
	boolean haveConnectedWifi = false;
    boolean haveConnectedMobile = false;

    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo[] netInfo = cm.getAllNetworkInfo();
    for (NetworkInfo ni : netInfo) {
        if (ni.getTypeName().equalsIgnoreCase("WIFI"))
            if (ni.isConnected())
                haveConnectedWifi = true;
        if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
            if (ni.isConnected())
                haveConnectedMobile = true;
    }
    return haveConnectedWifi || haveConnectedMobile;
}//end of haveNetworkConnection() method


}