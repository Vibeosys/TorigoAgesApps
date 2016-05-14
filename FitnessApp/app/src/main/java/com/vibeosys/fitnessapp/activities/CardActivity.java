package com.vibeosys.fitnessapp.activities;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.vibeosys.fitnessapp.R;
import com.vibeosys.fitnessapp.adapters.MonthYearPickerDialog;

import java.util.ArrayList;
import java.util.List;

public class CardActivity extends AppCompatActivity {

    private String productName;
    private String productDescription;
    private double productPrice;
    TextView txtProductName, txtProductPrice, txtProdctDesc;
    TextView txtDate;
    Button btnPurchase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        //CreditCardView creditCardView = (CreditCardView) findViewById(R.id.card1);
        //Spinner spinner = (Spinner) findViewById(R.id.spiner);
        //ArrayAdapter<String> adapter;
        //String[] arr = {"Master Card", "Visa", "Debit Card", "Credit Card"};
        txtProductName = (TextView) findViewById(R.id.txtProductName);
        txtProdctDesc = (TextView) findViewById(R.id.txtProductDescription);
        txtProductPrice = (TextView) findViewById(R.id.txtPrice);

        txtDate = (TextView) findViewById(R.id.txtDate);
        btnPurchase = (Button) findViewById(R.id.purchaseBtn);
        /*adapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.dropdown_list_item, arr);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);*/
        productName = getIntent().getExtras().getString("productName");
        productDescription = getIntent().getExtras().getString("productDesc");
        productPrice = getIntent().getExtras().getDouble("productPrice");

        txtProductName.setText(productName);
        txtProdctDesc.setText(productDescription);
        txtProductPrice.setText(getResources().getString(R.string.rupess_symbol) + " " + String.format("%.2f", productPrice));

        btnPurchase.setText(getResources().getString(R.string.rupess_symbol) + " " + String.format("%.2f", productPrice) + " Purchase");
    }

    public void showDateDialog(View v) {
        MonthYearPickerDialog pd = new MonthYearPickerDialog();
        pd.setListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                txtDate.setText(monthOfYear + " / " + year);
                // txtDate.setEnabled(false);
            }
        });
        pd.show(getSupportFragmentManager(), "MonthYearPickerDialog");
    }
}
