package com.sportsprediction.bettingtipssportsprediction1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.AcknowledgePurchaseResponseListener;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Vip_Tips extends AppCompatActivity implements PurchasesUpdatedListener, BuySKU{

    private BillingClient billingClient;
    @BindView(R.id.my_recycler_view)
    RecyclerView my_recycler_view;
    private SkuDetails skuDetails;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip__tips);

        ButterKnife.bind(this);

        my_recycler_view.setHasFixedSize(true);
        my_recycler_view.setLayoutManager(new LinearLayoutManager(this));

        billingInit();

    }

    private void billingInit() {
        billingClient = BillingClient.newBuilder(Vip_Tips.this).setListener(this).enablePendingPurchases().build();

        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    // The BillingClient is ready. You can query purchases here.
                    Toast.makeText(Vip_Tips.this, "Billing connected", Toast.LENGTH_SHORT).show();
                    skufetch();
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
                Toast.makeText(Vip_Tips.this, "Billing disconnected", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void skufetch() {
        List<String> skuList = new ArrayList<>();
        skuList.add("one_month_pay");
        skuList.add("second_packages");
        skuList.add("third_packages");

        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
        params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);
        billingClient.querySkuDetailsAsync(params.build(),
                new SkuDetailsResponseListener() {
                    @Override
                    public void onSkuDetailsResponse(BillingResult billingResult,
                                                     List<SkuDetails> skuDetailsList) {
                        // Process the result.
                        Log.d("process", skuDetailsList.toString());

                        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && skuDetailsList != null) {


                            SKUAdapter skuAdapter = new SKUAdapter(skuDetailsList, Vip_Tips.this, (BuySKU) Vip_Tips.this);
                            my_recycler_view.setAdapter(skuAdapter);
                        }
                    }
                });
    }
    public void showAlertdialog(String message) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(message).setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();
    }
    public void onPurchasesUpdated(BillingResult billingResult, @Nullable List<Purchase> purchases) {

        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK
                && purchases != null) {
            for (Purchase purchase : purchases) {
                handlePurchase(purchase);
                //   showAlertdialog("Success : " + purchase.getOriginalJson());
            }
        } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
            // Handle an error caused by a user cancelling the purchase flow.
            Toast.makeText(this, "User cancelled purchase", Toast.LENGTH_SHORT).show();
            showAlertdialog("Cancelled by user : ");

        } else {
            // Handle any other error codes.
            // showAlertdialog("Error by billing : code " + billingResult.getResponseCode()+" "+ billingResult.getDebugMessage());
            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED) {


                Purchase.PurchasesResult purchasesResult = billingClient.queryPurchases(BillingClient.SkuType.INAPP);
                for (Purchase purchase : purchasesResult.getPurchasesList()
                ) {
                    if (purchase.getSku().equalsIgnoreCase(currentSKU) && purchase.isAcknowledged()) {
                        openRespectiveActivity(currentSKU);
                        return;

                    }

                  /*  else if(purchase.getSku().equalsIgnoreCase(currentSKU) && !purchase.isAcknowledged()){
                        List<String> skuList = new ArrayList<>();
                        skuList.add(currentSKU);
                        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
                        params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);
                        billingClient.querySkuDetailsAsync(params.build(),
                                new SkuDetailsResponseListener() {
                                    @Override
                                    public void onSkuDetailsResponse(BillingResult billingResult,
                                                                     List<SkuDetails> skuDetailsList) {
                                        // Process the result.
                                        Log.d("process", skuDetailsList.toString());


                                        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && skuDetailsList != null) {

                                            for (SkuDetails skudetails : skuDetailsList
                                            ) {
                                                if (skudetails.getSku().equalsIgnoreCase(currentSKU)) {
                                                    skudetailssss = skudetails;
                                                    return;
                                                }

                                            }

                                        }
                                    }
                                });

                        if (skudetailssss != null){
                            BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                                    .setSkuDetails(skudetailssss)
                                    .build();
                            currentSKU = skudetailssss.getSku();
                            BillingResult responseCode = billingClient.launchBillingFlow(this, flowParams);
                        }
                        return;
                    }*/
                }


                showAlertdialog("You have not purchased your product your payment either refund or cancelled");

            } else {
                showAlertdialog("Error by user : " + billingResult.getResponseCode() + " " + billingResult.getDebugMessage());

            }

        }

    }

    private void handlePurchase(Purchase purchase) {
        if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
            // Acknowledge purchase and grant the item to the user

            // Acknowledge the purchase if it hasn't already been acknowledged.
            if (!purchase.isAcknowledged()) {
                AcknowledgePurchaseParams acknowledgePurchaseParams =
                        AcknowledgePurchaseParams.newBuilder()
                                .setPurchaseToken(purchase.getPurchaseToken())
                                .setDeveloperPayload(purchase.getOrderId())
                                .build();
                billingClient.acknowledgePurchase(acknowledgePurchaseParams, acknowledgePurchaseResponseListener);
            }

        } else if (purchase.getPurchaseState() == Purchase.PurchaseState.PENDING) {
            // Here you can confirm to the user that they've started the pending
            // purchase, and to complete it, they should follow instructions that
            // are given to them. You can also choose to remind the user in the
            // future to complete the purchase if you detect that it is still
            // pending.
        }
    }

    public void openRespectiveActivity(String productType) {
        switch (productType) {
            case "one_month_pay":
                FirstActivity.open(this, "Welcome 120TK all premium page");

                break;
            case "second_packages":
                //SecondActivity.open(this, "Welcome 100TK single game page");
                break;
            case "third_packages":
                //FirstActivity.open(this, "Welcome 120TK all premium page");
                //SecondActivity.open(this, "Welcome 100TK single game page");
                break;
            default:
                //SecondActivity.open(this, "Welcome " + currentSKU + " single game page");
                break;
        }
    }
    AcknowledgePurchaseResponseListener acknowledgePurchaseResponseListener = new AcknowledgePurchaseResponseListener() {
        @Override
        public void onAcknowledgePurchaseResponse(BillingResult billingResult) {


            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                if (currentSKU != null)
                    openRespectiveActivity(currentSKU);

            }

        }
    };
    String currentSKU = null;

    public void onBuySKU(boolean isBuy, SkuDetails skuDetails) {
        // Retrieve a value for "skuDetails" by calling querySkuDetailsAsync().
        BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                .setSkuDetails(skuDetails)
                .build();
        currentSKU = skuDetails.getSku();
        BillingResult responseCode = billingClient.launchBillingFlow(this, flowParams);

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }

}
