package com.example.shashikant.bhuconnect;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import static android.R.id.edit;
import static android.R.layout.simple_dropdown_item_1line;
import static android.R.layout.simple_spinner_dropdown_item;
import static android.os.Build.VERSION_CODES.M;
import static com.example.shashikant.bhuconnect.R.id.editComplain;
import static com.example.shashikant.bhuconnect.sendMailUtils.LOG_TAG;
import static com.example.shashikant.bhuconnect.sendMailUtils.fetchMail;

public class WriteComplain extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    AlertDialog.Builder alertDialog;
    Context context;
    boolean flagPlace = false;
    // function for mail validation
    boolean validateEmail(String email){
        if(email.contains("@")){
            return true;
        }
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_write_complain);
        final EditText  placeEditText = (EditText)findViewById(R.id.complain_place);
        final EditText nameEditText = (EditText)findViewById(R.id.student_name);
        final EditText mobEditText = (EditText)findViewById(R.id.student_mobNo);
        final EditText emailEditText = (EditText)findViewById(R.id.student_email);
        final EditText editText = (EditText)findViewById(R.id.editComplain);

        MySpinner();

        Button submitButton = (Button)findViewById(R.id.submit_complain);
        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void  onClick(View v){
                if(flagPlace){
                    placeEditText.setText("Not Applicable");
                }
                sendMailUtils sendMailUtils = new sendMailUtils();
                String place = placeEditText.getText().toString();
                String name = nameEditText.getText().toString();
                String mobile = mobEditText.getText().toString();
                String email = emailEditText.getText().toString();

                String complain = editText.getText().toString();
               // String name  = nameEditText.getText().toString();
                ConnectivityManager cm = (ConnectivityManager)WriteComplain.this.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwok = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwok!=null && activeNetwok.isConnectedOrConnecting();
                final String mailUrl = "http://14.139.41.140/android/mail/swiftmailer-5.x/index.php";
                if(place.length()==0){
                    Toast.makeText(context,"Enter valid place!!",Toast.LENGTH_SHORT).show();
                }
                else if(name.length()==0){
                    Toast.makeText(context,"Enter valid name!!",Toast.LENGTH_SHORT).show();
                }
                else if(mobile.length()==0 || mobile.length()<10){
                    if(mobile.length()<10){
                        Toast.makeText(context,"Mobile number must be of 10 digits",Toast.LENGTH_SHORT).show();
                    }
                    else
                    Toast.makeText(context,"Enter valid mobile number!!",Toast.LENGTH_SHORT).show();
                }
                else if (email.length()==0 || !validateEmail(email)){
                    Toast.makeText(context,"Enter valid email!!",Toast.LENGTH_SHORT).show();
                }
                else if(complain.length()==0){
                    Toast.makeText(context,"Enter valid complain!!",Toast.LENGTH_SHORT).show();
                }
                else if(complain.length()<=20){
                    Toast.makeText(context,"Write complain properly",Toast.LENGTH_SHORT).show();
                }
                else {
                    if(!isConnected){
                        Toast.makeText(context,R.string.no_internet,Toast.LENGTH_LONG).show();
                    }
                    else {
                        sendMailAsyncTask sendMailAsyncTask = new sendMailAsyncTask(WriteComplain.this);
                        sendMailAsyncTask.execute(mailUrl, complain, place, name, mobile, email);
                    }
                }

            }
        });

    }

    public void MySpinner() {
      final Spinner spinner1 = (Spinner) findViewById(R.id.spinnerComplaintType);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.complaint_type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parentView,
                                       View selectedItemView, int position, long id) {
                LinearLayout place = (LinearLayout)findViewById(R.id.place_of_complaint);
                if(spinner1.getSelectedItemPosition()==1){
                    flagPlace = true;
                    place.setVisibility(View.GONE);
                }
                else if(spinner1.getSelectedItemPosition()==0){
                    place.setVisibility(View.VISIBLE);
                }

            }

            public void onNothingSelected(AdapterView<?> arg0) {// do nothing
                Toast.makeText(getApplicationContext(),"select a value",Toast.LENGTH_SHORT).show();
            }

        });

    }
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
        if(parent.getSelectedItemPosition()==1){
            LinearLayout place = (LinearLayout)findViewById(R.id.place_of_complaint);
            place.setVisibility(View.VISIBLE);

        }
    }
    public void onNothingSelected(AdapterView<?>parent){

    }
    public class sendMailAsyncTask extends AsyncTask<String,Void,String>{
        ProgressDialog progressDialog;
        Context mcontext;
        sendMailAsyncTask(Context ctx){
            mcontext = ctx;
        }
        @Override
        protected String doInBackground(String... params) {
            Log.w(LOG_TAG,"!!!!!!!!!!!!!!!!!!!!->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
           String result = sendMailUtils.fetchMail(params[0],params[1],params[2],params[3],params[4],params[5]);
            Log.w(LOG_TAG,"!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+ result);
            return  result;
        }
        @Override
        protected  void onPreExecute(){
            progressDialog = new ProgressDialog(mcontext);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            progressDialog.setCancelable(false);
            alertDialog = new AlertDialog.Builder(new ContextThemeWrapper(mcontext,R.style.AppTheme));
            alertDialog.setTitle("Complain status:");
        }

        @Override
        protected  void onPostExecute(String result){
            progressDialog.dismiss();
            alertDialog.setMessage(result);
            alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                   Intent intent = new Intent(((Dialog)dialog).getContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            alertDialog.show();
        }
    }

}
