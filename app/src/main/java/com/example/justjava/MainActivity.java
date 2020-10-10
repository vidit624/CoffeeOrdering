package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
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
        String s = displayPrice(calculatePrice());
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        EditText nom = (EditText) findViewById(R.id.name);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java - coffee order summary for " + nom.getText().toString().trim());
        intent.putExtra(Intent.EXTRA_TEXT, s);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
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
        Locale indiaLocale = new Locale("en", "IN");
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance(indiaLocale).format(calculatePrice()));
    }
    private int calculatePrice () {
        int price = 5;
        if(flag) price += 2;
        CheckBox choc = (CheckBox) findViewById(R.id.chocolate_checkbox);
        CheckBox whipp = (CheckBox) findViewById(R.id.whipp);
        if (choc.isChecked()) price += 1;
        if(whipp.isChecked()) price += 1;
        return price*n;
    }
    private void display() {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + n);
        Locale indiaLocale = new Locale("en", "IN");
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance(indiaLocale).format(calculatePrice()));
    }

    private String displayPrice(int number){
        if(number == 0)
            return "Free!";
        else {
            Locale indiaLocale = new Locale("en", "IN");
            CheckBox whipp = (CheckBox) findViewById(R.id.whipp);
            CheckBox choc = (CheckBox) findViewById(R.id.chocolate_checkbox);
            EditText nom = (EditText) findViewById(R.id.name);
            String s = "Name: " + nom.getText().toString();
            if(whipp.isChecked()) s += "\nAdded Whipped Cream";
            if(choc.isChecked()) s += "\nAdded Chocolate";
            s += "\nQuantity: "+ n + "\nTotal = " + NumberFormat.getCurrencyInstance(indiaLocale).format(number);
            if(!flag) s += "\nThank You for saving the Environment!";
            s += "\nThank You, Visit Again!";
            return s;
        }
    }
}