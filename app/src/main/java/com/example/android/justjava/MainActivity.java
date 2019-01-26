/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match the package name found
 * in the project's AndroidManifest.xml file.
 */

package com.example.android.justjava;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;
import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */

    public void submitOrderEmail(View view) {

        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        Log.v("MainActivity", "Has whipped cream: " + hasWhippedCream);

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        CheckBox cinnamonCheckBox = (CheckBox) findViewById(R.id.cinnamon_checkbox);
        boolean hasCinnamon = cinnamonCheckBox.isChecked();

        CheckBox sprinklesCheckBox = (CheckBox) findViewById(R.id.sprinkles_checkbox);
        boolean hasSprinkles = sprinklesCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate, hasCinnamon, hasSprinkles);
        String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate, hasCinnamon, hasSprinkles);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.Custom_Cocoa_order_for) + " " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    /**
     * Create summary of the order
     *
     * @param price of order
     * @param name adds customer name
     * @param addWhippedCream for whether customer wants cream or not
     * @param addChocolate
     * @param addCinnamon
     * @param addSprinkles
     * @return text summary
     */
    private String createOrderSummary (String name, int price, boolean addWhippedCream, boolean addChocolate, boolean addCinnamon, boolean addSprinkles) {
        String priceMessage = getString(R.string.order_summary);
        priceMessage += "\n\n" + getString(R.string.order_summary_name, name);
        priceMessage += "\n" + getString(R.string.quant)+ " " + quantity;
        priceMessage += "\n" + getString(R.string.wants_whipped_cream) + " " + addWhippedCream;
        priceMessage += "\n" + getString(R.string.wants_chocolate) + " " + addChocolate;
        priceMessage += "\n" + getString(R.string.wants_cinnamon) + " " + addCinnamon;
        priceMessage += "\n" + getString(R.string.wants_sprinkles) + " " + addSprinkles;
        priceMessage += "\n" + getString(R.string.total) + " " + price;
        priceMessage += "\n" + getString(R.string.thank_you);
        return (priceMessage);
    }

    /**
     * Calculates the price of the order.
     *
     * @return total price
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate, boolean hasCinnamon, boolean hasSprinkles) {
        int pricePerCup = 5;
        if (hasWhippedCream == true) {
            pricePerCup += 1;
        }
        if (hasChocolate == true) {
            pricePerCup += 2;
        }
        if (hasCinnamon == true) {
            pricePerCup += 1;
        }
        if (hasSprinkles == true) {
            pricePerCup += 1;
        }

        return quantity * pricePerCup;
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity <= 99) {
            quantity += 1;
            displayQuantity(quantity);
        }
        else {
            Toast.makeText(this,
                    getString(R.string.more_than_100),
                    Toast.LENGTH_SHORT).show();
            return;
        }
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity >= 2) {
            quantity = quantity - 1;
            displayQuantity(quantity);
        }
        else {
            Toast.makeText(this,
                    getString(R.string.less_than_1),
                    Toast.LENGTH_SHORT).show();
            return;
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCocoas) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCocoas);
    }

}