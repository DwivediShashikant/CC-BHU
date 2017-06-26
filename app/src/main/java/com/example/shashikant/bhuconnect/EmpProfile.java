package com.example.shashikant.bhuconnect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class EmpProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_profile);
        TextView empName = (TextView)findViewById(R.id.employee_name);
        empName.setText(EmpProfileRecord.EmpProfileList.get(0));

        TextView empID = (TextView)findViewById(R.id.employee_id);
        empID.setText(EmpProfileRecord.EmpProfileList.get(1));

        TextView empDesg = (TextView)findViewById(R.id.employee_designation);
        empDesg.setText(EmpProfileRecord.EmpProfileList.get(2));

        TextView empInstitute = (TextView)findViewById(R.id.employee_faculty);
        empInstitute.setText(EmpProfileRecord.EmpProfileList.get(3));

        TextView empEmail = (TextView)findViewById(R.id.employee_eMail);
        empEmail.setText(EmpProfileRecord.EmpProfileList.get(4));

        TextView empMob = (TextView)findViewById(R.id.employee_mobile_number);
        empMob.setText(EmpProfileRecord.EmpProfileList.get(5));

        TextView PAN = (TextView)findViewById(R.id.employee_pan);
        PAN.setText(EmpProfileRecord.EmpProfileList.get(6));

        TextView retDate = (TextView)findViewById(R.id.employee_retirement_date);
        retDate.setText(EmpProfileRecord.EmpProfileList.get(7));

    }
}
