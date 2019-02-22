package mlmunozd.tfm;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Utils implements Serializable{

    //private static String url="http://gcs.proemia.com/apimovil/Api"; //SERVIDOR GCS
    //private static String url="http://segurosdev.proemia.es/apimovil/Api"; //SERVIDOR DEV
    //private static String url="http://192.168.47.30/apimovil/Api"; //LOCAL IP DE LA UPO PERI
    private static String url="http://192.168.47.178/apimovil/Api"; //LOCAL IP DE LA UPO MANUEL
    //private static String url="http://192.168.43.239/apimovil/Api"; //LOCAL IP DEL MOVIL (SEGURAMENTE CAMBIE)

    private static Bitmap scaledBitmap = null;

    public static boolean isConnected(Context appContext) {
        ConnectivityManager connMgr = (ConnectivityManager) appContext.getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    public static JSONObject creaJson(Object objeto, String servicio, String tipo, Context context) throws Exception {
        JSONObject jsonObjectCompleto = new JSONObject();

        if (tipo.equalsIgnoreCase("Usuario")) {
            Usuario us = (Usuario) objeto;
            //creamos el objeto usuario en el json
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("login", us.getLogin());
            jsonObject.put("password", us.getPassword());
            jsonObject.put("nombre", us.getNombre());
            jsonObject.put("email", us.getEmail());
            //jsonObject.put("propietario", us.getPropietario());
            jsonObject.put("token", us.getToken());
            jsonObject.put("tokenExpiracion", us.getTokenExpiracion());
            jsonObject.put("pk_usuario", us.getPk_usuario());
            //jsonObject.put("fk_propietario", us.getFk_propietario());

            //establecemos la cabecera del objeto, a la que llamamos data
            jsonObjectCompleto.accumulate("service", servicio);
            jsonObjectCompleto.accumulate("data", jsonObject);
        }
        return jsonObjectCompleto;
    }

    public static IUsuario usuarioSesion(Context context) {
        IUsuario usuario;
        SharedPreferences loginPreferences;
        loginPreferences = context.getSharedPreferences("loginPreferences", 0x0000);
        int pk_usuario = loginPreferences.getInt("pk_usuario", 0);
        String token = loginPreferences.getString("token", "");
        String pass = loginPreferences.getString("password_enc", "");
        usuario = new Usuario();
        usuario.setPk_usuario(pk_usuario);
        usuario.setToken(token);
        usuario.setPassword(pass);
        return usuario;
    }
}

