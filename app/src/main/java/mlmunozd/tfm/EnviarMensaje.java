package mlmunozd.tfm;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EnviarMensaje extends AppCompatActivity {

    Button sendSMSBtn;
    TextView smsText, movilText;
    String phoneNo,message;
    static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envio_mensaje);
        sendSMSBtn = findViewById(R.id.sendSMSBtn);
        smsText = findViewById(R.id.smsText);
        movilText = findViewById(R.id.movilText);
        sendSMSBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                smsSendMessage(view);
            }
        });
    }


    //---sends an SMS message to another device---
    public void smsSendMessage(View view) {
        //phoneNo = String.format("smsto:%s",movilText.getText().toString());
        phoneNo = movilText.getText().toString();
        message = smsText.getText().toString();

        if( !phoneNo.isEmpty() && !message.isEmpty()){
            // Check for permission first.
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                SmsManager smsManager = SmsManager.getDefault();

                try {// Set the service center address if needed, otherwise null.
                    smsManager.sendTextMessage(phoneNo, null, message, null, null);
                    Toast.makeText(getApplicationContext(), "Mensaje enviado", Toast.LENGTH_LONG).show();
                }catch (Exception e) {
                    Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
            else{
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 10);
                }
            }
        }else{
            Toast.makeText(getBaseContext(),"Ingresar los datos", Toast.LENGTH_SHORT).show();
        }
    }
}
