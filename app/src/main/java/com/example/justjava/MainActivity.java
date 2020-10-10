package com.example.justjava;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int n = 0;
    boolean flag = false;
    public void submitOrder(View view) {
        displayPrice(calculatePrice());
    }
    public void incOrder(View view) {
        n++;
        display();
    }
    public void decOrder(View view) {
        n--;
        if(n <= 0){
            n = 0;
            flag = false;
            TextView quantityTextView = (TextView) findViewById(R.id.cupp);
            quantityTextView.setText("NO");
        }
        display();
    }
    public void cup(View view) {
        if(n < 1) return;
        if(flag) {
            flag = false;
            TextView quantityTextView = (TextView) findViewById(R.id.cupp);
            quantityTextView.setText("NO");
        }
        else {
            flag = true;
            TextView quantityTextView = (TextView) findViewById(R.id.cupp);
            quantityTextView.setText("YES");
        }
    }
    private int calculatePrice () {
        int price = 5;
        if(flag) price += 2;
        CheckBox choc = (CheckBox) findViewById(R.id.chocolate_checkbox);
        CheckBox whipp = (CheckBox) findViewById(R.id.whipp);
        if (choc.isChecked()) price += 1;
        if(whipp.isChecked()) price += 1;
        price *= n;
        displayPrice(price);
        return price;
    }
    private void display() {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + n);
    }

    private void displayPrice(int number){
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        if(number == 0) {
            priceTextView.setText("Free!");
        }
        else {
            Locale indiaLocale = new Locale("en", "IN");
            CheckBox whipp = (CheckBox) findViewById(R.id.whipp);
            CheckBox choc = (CheckBox) findViewById(R.id.chocolate_checkbox);
            EditText nom = (EditText) findViewById(R.id.name);
            String s = "Name: " + nom.getText().toString() + "\nAdd Whipped Cream to coffee: " + whipp.isChecked() + "\nAdd chocolate to coffee: "+ choc.isChecked() + "\nQuantity: "+ n + "\nTotal = " + NumberFormat.getCurrencyInstance(indiaLocale).format(number);
            if(!flag) s += "\nThank You for saving the Environment!";
            priceTextView.setText(s + "\nThank You, Visit Again!");
        }
    }
}