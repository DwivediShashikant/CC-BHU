package com.example.shashikant.bhuconnect;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import static com.example.shashikant.bhuconnect.R.id.uniqueId;
public class EmployeeActivity extends AppCompatActivity {
    private String LOG_TAG = EmployeeActivity.class.getSimpleName();
    String EMP_REQUEST_URL;
    String employeeID;
    public ProgressDialog progressDialog;
    String password;
    String REQUEST_URL;
    Activity activity;
    public boolean firstTimeAsyncTask = true;
    public ProgressBar empProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       progressDialog = new ProgressDialog(EmployeeActivity.this);
        activity= this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        Log.w("","employee");
        ImageView personImage = (ImageView)findViewById(R.id.personType);
        final EditText emailId = (EditText)findViewById(R.id.uniqueIdOrEmail);
        final EditText uniqueID = (EditText)findViewById(uniqueId);
        personImage.setImageResource(R.drawable.employee);
        emailId.setHint(R.string.panId);
        uniqueID.setHint(R.string.paswword);
        //button onClickListner will check if the email is vaild and direct to complain page
        Button loginButtonStudent = (Button)findViewById(R.id.login_button);
        loginButtonStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String empID = emailId.getText().toString();
                 password = uniqueID.getText().toString();
                employeeID = empID;
                REQUEST_URL = "http://14.139.41.140/android/getInfo_employee.php";
                EMP_REQUEST_URL = "http://14.139.41.140/android/getInfoAll_emp.php";
                StudentRecordAsyncTask1 asyncTask = new StudentRecordAsyncTask1(EmployeeActivity.this);
                asyncTask.execute(REQUEST_URL,empID,password);
            }
        });
    }
    public  class StudentRecordAsyncTask1 extends AsyncTask<String, Void, String> {

        Context context;
        AlertDialog alertDialog;
        StudentRecordAsyncTask1(Context ctx){
            context = ctx;
        }
        @Override
        protected String doInBackground(String... url) {
            return Utils.fetchLoginDetails(url[0],url[1],url[2]);

        }

        @Override
        protected void onPreExecute() {
            alertDialog = new AlertDialog.Builder(context).create();
            progressDialog.setTitle("Login Status");
            progressDialog.setMessage("Authenticating...");
            progressDialog.show();
            progressDialog.setCancelable(false);
        }
        @Override
        protected void onPostExecute(String result){
            Log.e(LOG_TAG,"------------------------------>"+result);
            if(result == null){
                alertDialog.setMessage("");

            }
            if(result!=null){
                fetchEmpProfile fetchEmpProfile = new fetchEmpProfile();
                fetchEmpProfile.execute(EMP_REQUEST_URL,employeeID);
                StudentRecordAsyncTask2 studentRecordAsyncTask = new StudentRecordAsyncTask2(EmployeeActivity.this);
                studentRecordAsyncTask.execute(REQUEST_URL,employeeID,password);
            }
            else {
                alertDialog.setMessage("Login Unsuccessful, Check Employee Id Or Password");
                progressDialog.hide();
                alertDialog.show();
            }
            Log.e(LOG_TAG,"$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+employeeID);
        }
    }

    public class fetchEmpProfile extends AsyncTask<String,Void,ArrayList<String>>{

        @Override
        protected ArrayList<String> doInBackground(String... params) {
            return profileUtils.fetchProfileInfo(params[0],params[1]);
        }
        @Override
        protected void onPostExecute(ArrayList<String> result){
            for(int i=0;i<8;i++){
                EmpProfileRecord.EmpProfileList.add(i,result.get(i));
                Log.e(LOG_TAG,"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+EmpProfileRecord.EmpProfileList.get(i));
            }
        }
    }
    public  class StudentRecordAsyncTask2 extends AsyncTask<String, Void, String> {

        Context context;
        StudentRecordAsyncTask2(Context ctx){
            context = ctx;
        }
        @Override
        protected String doInBackground(String... url) {
            return Utils.fetchLoginDetails(url[0],url[1],url[2]);

        }

        @Override
        protected void onPreExecute() {
        }
        @Override
        protected void onPostExecute(String result){
            progressDialog.hide();
            Log.e(LOG_TAG,"------------------------------>"+result);
            if(result == null){

            }
            if(Integer.parseInt(result)==1){
                Intent intent = new Intent(EmployeeActivity.this,EmployeeInfo.class);
                startActivity(intent);
            }
            else {
            }
            Log.e(LOG_TAG,"$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+employeeID);
        }

   }
}
