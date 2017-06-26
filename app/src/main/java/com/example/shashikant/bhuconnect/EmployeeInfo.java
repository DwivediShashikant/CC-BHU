package com.example.shashikant.bhuconnect;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class EmployeeInfo extends AppCompatActivity {

    private String LOG_TAG = EmployeeInfo.class.getSimpleName();
    private String SALARY_SLIP_URL = "http://14.139.41.140/android/fetch_salaryslip.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_info);
        TextView empNameTextView = (TextView)findViewById(R.id.emp_name);
        empNameTextView.setText("Welcome, "+EmpProfileRecord.EmpProfileList.get(0));
        salarySlipAsynTask task = new salarySlipAsynTask();
        task.execute(SALARY_SLIP_URL,EmpProfileRecord.EmpProfileList.get(1));
        TextView info = (TextView)findViewById(R.id.profile_info);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployeeInfo.this,EmpProfile.class);
                startActivity(intent);
            }
        });
        TextView salarySlip = (TextView)findViewById(R.id.salary_slip_info);
        salarySlip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(EmployeeInfo.this,EmpSalarySlip.class));
            }
        });
    }
    public class salarySlipAsynTask extends AsyncTask<String,Void,ArrayList<String>>{

        @Override
        protected ArrayList<String> doInBackground(String... params) {
            return salarySlipUtils.fetchSalarySlipInfo(params[0],params[1]);
        }
        @Override
        protected void onPostExecute(ArrayList<String> result){
            for(int i=0;i<9;i++){
                EmpSalarySlipList.salarySlipList.add(i,result.get(i));
            }
        }
    }
}
