package com.example.dawid.visitwroclove.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.dawid.visitwroclove.R;
import com.example.dawid.visitwroclove.model.ShopData;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.mobiltek.paymentsmobile.dotpay.Configuration;
import pl.mobiltek.paymentsmobile.dotpay.events.PaymentEndedEventArgs;
import pl.mobiltek.paymentsmobile.dotpay.events.PaymentManagerCallback;
import pl.mobiltek.paymentsmobile.dotpay.managers.PaymentManager;
import pl.mobiltek.paymentsmobile.dotpay.model.PaymentInformation;


public class MainPanelActivity extends BaseActivity {

    private final String NAME = "firstname";
    private final String LAST_NAME = "lastname";
    private final String EMAIL = "email";

    private PaymentManagerCallback paymentManagerCallback = new PaymentManagerCallback() {
        @Override
        public void onPaymentSuccess(PaymentEndedEventArgs paymentEndedEventArgs) {
            if (paymentEndedEventArgs.mPaymentResult.getStatus().equals("processing")){
                Toast.makeText(MainPanelActivity.this, "Payment failed", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(MainPanelActivity.this, "Payment successful", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onPaymentFailure(PaymentEndedEventArgs paymentEndedEventArgs) {
            Toast.makeText(MainPanelActivity.this, "Payment failed", Toast.LENGTH_LONG).show();
        }
    };

    @OnClick(R.id.dotpay)
    public void samplePayment() {
        PaymentManager.getInstance().initialize(this, getPaymentInformation());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);
        getComponent().inject(this);
        ButterKnife.bind(this);
        setupSDK();
    }

    @NonNull
    private PaymentInformation getPaymentInformation() {
        PaymentInformation paymentInformation = new PaymentInformation(ShopData.getMerchantId(), ShopData.getProductPrice(), ShopData.getDescription(), ShopData.getCurrency());

        Map<String, String> sender = new HashMap<String, String>();
        sender.put(NAME, ShopData.getName());
        sender.put(LAST_NAME, ShopData.getLastName());
        sender.put(EMAIL, ShopData.getEmail());

        paymentInformation.setSenderInformation(sender);
        return paymentInformation;
    }

    private void setupSDK() {
        PaymentManager.getInstance().setPaymentManagerCallback(paymentManagerCallback);
        PaymentManager.getInstance().setApplicationVersion(Configuration.TEST_VERSION);
    }
}


