package mlmunozd.tfm;

import android.Manifest;
import android.content.pm.PackageManager;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EnvioMensaje extends AppCompatActivity {

    Button sendSMSBtn, retrySendSmsBtn;
    TextView smsText, movilText;
    String phoneNo,message;
    static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envio_mensaje);
        checkForSmsPermission();
        sendSMSBtn = findViewById(R.id.sendSMSBtn);
        smsText = findViewById(R.id.smsText);
        movilText = findViewById(R.id.movilText);

        sendSMSBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSMS();
            }
        });
    }

    /*private void disableSmsButton() {
        Toast.makeText(this, "Uso de SMS desactivado", Toast.LENGTH_LONG).show();
        sendSMSBtn = findViewById(R.id.sendSMSBtn);
        sendSMSBtn.setVisibility(View.INVISIBLE);
        retrySendSmsBtn = findViewById(R.id.retrySendSmsBtn);
        retrySendSmsBtn.setVisibility(View.VISIBLE);
    }*/

    private void enableSmsButton() {
        sendSMSBtn = findViewById(R.id.sendSMSBtn);
        sendSMSBtn.setVisibility(View.VISIBLE);
    }

    private void checkForSmsPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS) !=
                PackageManager.PERMISSION_GRANTED) {
            Log.d("INFO PERMISSION", getString(R.string.permission_not_granted));
            // Permission not yet granted. Use requestPermissions().
            // MY_PERMISSIONS_REQUEST_SEND_SMS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);
        } else {
            // Permission already granted. Enable the SMS button.
            enableSmsButton();
        }
    }

    //---sends an SMS message to another device---
    public void sendSMS() {
        phoneNo = String.format("smsto: %s",movilText.getText().toString());
        message = smsText.getText().toString();

        if(phoneNo.length()>0 && message.length()>0){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                Log.d("INFO PERMISSION: ", "Permiso no otorgado");
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS))
                {
                    //Configurar la dirección del centro de servicio si se necesita, en caso contrario scAddress =null

                    //Configurar intents pending para broadcast
                    //cuando el mensaje se envía y cuando se entrega, o se establece en nulo
                    PendingIntent sentIntent = PendingIntent.getActivity(this, 0,
                            new Intent(this, EnvioMensaje.class), 0);

                    PendingIntent deliveryIntent = null;
                    SmsManager sms = SmsManager.getDefault();
                    sms.sendTextMessage(phoneNo, null, message, sentIntent, deliveryIntent);

                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},MY_PERMISSIONS_REQUEST_SEND_SMS);
                }
            }
        }else{
            Toast.makeText(getBaseContext(),"Por favor, ingrese el número del móvil y el mensaje",
                    Toast.LENGTH_SHORT).show();
        }
    }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (permissions[0].equalsIgnoreCase(Manifest.permission.SEND_SMS) &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    enableSmsButton();
                } else {
                    //Permission denied
                    Log.d("INFO PERMISSION:",getString(R.string.failure_permission));
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.failure_permission), Toast.LENGTH_LONG).show();
                    //Deshabilitar el botón SMS
                }
            }
        }
    }
}
