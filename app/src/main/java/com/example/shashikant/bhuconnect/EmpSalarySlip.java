package com.example.shashikant.bhuconnect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class EmpSalarySlip extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_salary_slip);
        TextView accountNumber = (TextView)findViewById(R.id.account_number);
        accountNumber.setText(EmpSalarySlipList.salarySlipList.get(0));

        TextView basicPay = (TextView)findViewById(R.id.basic_pay);
        basicPay.setText(EmpSalarySlipList.salarySlipList.get(1));

        TextView DA = (TextView)findViewById(R.id.da);
        DA.setText(EmpSalarySlipList.salarySlipList.get(2));

        TextView HRA = (TextView)findViewById(R.id.hra);
        HRA.setText(EmpSalarySlipList.salarySlipList.get(3));

        TextView TA = (TextView)findViewById(R.id.ta);
        TA.setText(EmpSalarySlipList.salarySlipList.get(4));

        TextView grossPay = (TextView)findViewById(R.id.gross_pay);
        grossPay.setText(EmpSalarySlipList.salarySlipList.get(5));

        TextView bandPay = (TextView)findViewById(R.id.pay_band);
        bandPay.setText(EmpSalarySlipList.salarySlipList.get(6));

        TextView totalDeduction = (TextView)findViewById(R.id.total_deduction);
        totalDeduction.setText(EmpSalarySlipList.salarySlipList.get(7));

        TextView netPay = (TextView)findViewById(R.id.net_pay);
        netPay.setText(EmpSalarySlipList.salarySlipList.get(8));
    }
}
