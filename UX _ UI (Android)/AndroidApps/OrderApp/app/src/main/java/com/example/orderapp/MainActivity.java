package com.example.orderapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText priceTextEdit;
    EditText nameTextEdit;
    TextView quantityTextView;
    TextView totalOrderTextView;
    CheckBox whippedCreamCheckBox, chocolateCheckBox;
    int quantity = 0;
    int totalPrice;
    String orderMessage;
    String name = "name";
    CardView orderSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        priceTextEdit = findViewById(R.id.price_edit_view);
        quantityTextView = findViewById(R.id.quantity_text_view);
        totalOrderTextView = findViewById(R.id.total_order_text_view);
        whippedCreamCheckBox = findViewById(R.id.checkbox_whipped_cream);
        nameTextEdit = findViewById(R.id.name_edit_view);


        quantity = Integer.parseInt(quantityTextView.getText().toString());
        chocolateCheckBox = findViewById(R.id.checkbox_chocolate);

        orderSummary = findViewById(R.id.card_view_report);
    }

    public void submitOrder(View view) {
        name = nameTextEdit.getText().toString();
        orderMessage = "Thank you for your order, " + name;
        totalPrice = quantity * Integer.parseInt(priceTextEdit.getText().toString());
        orderMessage = orderMessage + "\nPrice is:$" + totalPrice;


        if(whippedCreamCheckBox.isChecked() || chocolateCheckBox.isChecked()){
            orderMessage += "\nYour Topping:";
            if (whippedCreamCheckBox.isChecked()){
                orderMessage += "\nWhipped Cream";
            if (chocolateCheckBox.isChecked()){
                orderMessage += "\nChocolate";
            }
            }
        }
        totalOrderTextView.setText(orderMessage);
        totalOrderTextView.setVisibility(View.VISIBLE);
        orderSummary.setVisibility(View.VISIBLE);

    }

    public void increaseQuantity(View view) {
        quantity++;
        quantityTextView.setText(Integer.toString(quantity));
    }

    public void reduceQuantity(View view) {
        quantity--;
        if(quantity <= 0){
            quantity = 0;
        }
        quantityTextView.setText(Integer.toString(quantity));
    }
}
