package com.testotest.x230.loancalculator;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
/*import android.widget.Button;*/
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.String;
import java.text.DecimalFormat;

public class CalculatorActivity extends AppCompatActivity {

    private EditText etLoanAmount, etDownPayment, etTerm, etAnnualInterestRate;
    private TextView tvMonthlyPayment, tvTotalRepayment, tvTotalInterest, tvAverageMonthlyInterest;
    /*mButtonCalculate, mButtonReset;;*/
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "onCreate() calls", Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_calculator);

        etLoanAmount = findViewById(R.id.loan_amount);
        etDownPayment = findViewById(R.id.down_payment);
        etTerm = findViewById(R.id.term);
        etAnnualInterestRate = findViewById(R.id.annual_interest_rate);

        tvMonthlyPayment = findViewById(R.id.monthly_repayment);
        tvTotalRepayment = findViewById(R.id.total_repayment);
        tvTotalInterest = findViewById(R.id.total_interest);
        tvAverageMonthlyInterest = findViewById(R.id.average_monthly_interest);

        /* SharedPreferences
        * MyData.xml created in shared_prefs folder in the com.testotest.x230.loancalculator package */
        sp = getSharedPreferences("MyData", Context.MODE_PRIVATE);

        String monthly_repayment = sp.getString("monthly_repayment",null);
        tvMonthlyPayment.setText(monthly_repayment == null? getString(R.string.default_result): monthly_repayment);

        String total_repayment = sp.getString("total_repayment",null);
        tvTotalRepayment.setText(total_repayment == null? getString(R.string.default_result): total_repayment);

        String total_interest = sp.getString("total_interest",null);
        tvTotalInterest.setText(total_interest == null? getString(R.string.default_result): total_interest);

        String average_monthly_interest = sp.getString("average_monthly_interest",null);
        tvAverageMonthlyInterest.setText(average_monthly_interest == null? getString(R.string.default_result): average_monthly_interest);
    }

    @Override
    public void onStart() {
        super.onStart();
        Toast.makeText(this, "onStart() calls", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "onPause() calls", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "onResume() calls", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "onStop() calls", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "onDestroy() calls", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, "onRestart() calls", Toast.LENGTH_SHORT).show();
    }

    private void calculate() {
        String amount = etLoanAmount.getText().toString();
        String downPayment = etDownPayment.getText().toString();
        String interestRate = etAnnualInterestRate.getText().toString();
        String term = etTerm.getText().toString();

        DecimalFormat df = new DecimalFormat(("#.00"));

        double loanAmount = Double.parseDouble(amount) - Double.parseDouble(downPayment);
        double interest = Double.parseDouble(interestRate) / 12 / 100;
        double noOfMonth = (Integer.parseInt(term) * 12);

        if (noOfMonth > 0) {
            double monthlyRepayment = loanAmount *
                    (interest + (interest / (java.lang.Math.pow((1 + interest),
                            noOfMonth) - 1)));
            double totalRepayment = monthlyRepayment * noOfMonth;
            double totalInterest = totalRepayment - loanAmount;
            double monthlyInterest = totalInterest / noOfMonth;

            tvMonthlyPayment.setText(String.valueOf(df.format(monthlyRepayment)));
            tvTotalRepayment.setText(String.valueOf(df.format(totalRepayment)));
            tvTotalInterest.setText(String.valueOf(df.format(totalInterest)));
            tvAverageMonthlyInterest.setText(String.valueOf(df.format(monthlyInterest)));

            /* retain the last answer after you close the application */
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("monthly_repayment",String.valueOf(df.format(monthlyRepayment)));
            editor.putString("total_repayment",String.valueOf(df.format(totalRepayment)));
            editor.putString("total_interest",String.valueOf(df.format(totalInterest)));
            editor.putString("average_monthly_interest",String.valueOf(df.format(monthlyInterest)));
            editor.commit();
        }
    }

    private void reset() {
        etLoanAmount.setText("");
        etDownPayment.setText("");
        etTerm.setText("");
        etAnnualInterestRate.setText("");

        tvMonthlyPayment.setText(R.string.default_result);
        tvTotalRepayment.setText(R.string.default_result);
        tvTotalInterest.setText(R.string.default_result);
        tvAverageMonthlyInterest.setText(R.string.default_result);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_calculate:
                Log.d("Check", "Button Calculate Clicked!");
                Toast.makeText(this, "Button Calculate Clicked!", Toast.LENGTH_SHORT).show();
                calculate();
                break;
            case R.id.button_reset:
                Log.d("Check", "Button Reset Clicked!");
                Toast.makeText(this, "Button Reset Clicked!", Toast.LENGTH_SHORT).show();
                reset();
                break;
        }
    }
}






