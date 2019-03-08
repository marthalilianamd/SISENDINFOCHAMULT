package mlmunozd.tfm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static java.security.MessageDigest.getInstance;

public class Login extends AppCompatActivity implements View.OnClickListener {

    EditText loginText, passText;
    Button loginBtn;
    Usuario usuario;
    Switch recuerdameSwitch;

    Utils utils;
    private SharedPreferences loginPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inicializar();
    }

    private void inicializar(){
        loginText = findViewById(R.id.loginText);
        passText = findViewById(R.id.passText);
        loginBtn = findViewById(R.id.loginBtn);
        utils = new Utils();
        loginBtn.setOnClickListener(this);
        recuerdameSwitch = findViewById(R.id.recuerdameSwitch);

        loginPreferences = this.getSharedPreferences("loginPreferences", MODE_PRIVATE);
        boolean recuerdame = loginPreferences.getBoolean("recuerdame",false);
        if(recuerdame){
            loginText.setText(loginPreferences.getString("login",""));
            passText.setText(loginPreferences.getString("password",""));
        }else{
            loginText.setText(""); passText.setText("");
        }
    }

    @Override
    public void onClick(View v) {
        ConnectionDetector connectionDetector = new ConnectionDetector(getApplicationContext());
        if(connectionDetector.isConnectingToInternet()){
            loginText.setEnabled(false);
            usuario = new Usuario(loginText.getText().toString(), getCadenaEncriptada(String.valueOf(passText.getText())));
            if(!usuario.getLogin().isEmpty() && !usuario.getPassword().isEmpty()){
                loginText.setEnabled(true);
                if(Utils.isConnected(getApplicationContext())){
                  Intent intent = new Intent(getApplicationContext(), EnviarMensaje.class);
                  startActivity(intent);
                  finish();

                }else{
                    Toast.makeText(getApplicationContext(),"Sin conexión a internet",Toast.LENGTH_SHORT).show();
                }
            }else{
                loginBtn.setEnabled(true);
                Toast.makeText(getBaseContext(),"Campos vacios",Toast.LENGTH_SHORT).show();
            }
        }else{
            loginText.setEnabled(true);
            Toast.makeText(getBaseContext(),"No dispone de conexión a internet, revise e intentelo de nuevo",Toast.LENGTH_SHORT).show();
        }
    }

    //encriptar password
    public String getCadenaEncriptada(String cadena){
        String cadenaScriptada;
        MessageDigest md = null;
        try {
            md = getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(cadena.getBytes());
        byte byteData []  = md.digest();
       //convert the byte to hex format method 1
        StringBuffer sha256pass = new StringBuffer();
        for (int i=0; i<byteData.length;i++){
            sha256pass.append(Integer.toString((byteData[i]& 0xff)+ 0x100,16).substring(1));
        }
        cadenaScriptada= sha256pass.toString();

        return cadenaScriptada;
        //end encriptar password
    }
}
