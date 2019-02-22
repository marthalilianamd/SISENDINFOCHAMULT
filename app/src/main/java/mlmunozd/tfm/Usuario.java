package mlmunozd.tfm;

import java.io.Serializable;
import java.util.Date;

public class Usuario implements IUsuario, Serializable {

    private String login, password, nombre, email, token, propietario;
    private Date tokenExpiracion;
    private int pk_usuario, fk_propietario;

    public Usuario(String login, String password, String nombre,
                   String email, String propietario, String token,
                   Date tokenExpiracion, int pk_usuario,
                   int fk_propietario) {
        this.login = login;
        this.password = password;
        this.nombre = nombre;
        this.email = email;
        this.token = token;
        this.tokenExpiracion = tokenExpiracion;
        this.pk_usuario = pk_usuario;
        this.propietario = propietario;
        this.fk_propietario = fk_propietario;
    }

    public Usuario(String login, String password ){ //, String propietario) {
        this.login = login;
        this.password = password;
        //this.propietario = propietario;
    }

    public Usuario(){}

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /*public String getPropietario () { return propietario; }

    public void setPropietario(String propietario){
        this.propietario = propietario;
    }*/

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getTokenExpiracion() {
        return tokenExpiracion;
    }

    public void setTokenExpiracion(Date tokenExpiracion) {
        this.tokenExpiracion = tokenExpiracion;
    }

    public int getPk_usuario() {
        return pk_usuario;
    }

    public void setPk_usuario(int pk_usuario) {
        this.pk_usuario = pk_usuario;
    }

    /*public int getFk_propietario() {
        return fk_propietario;
    }

    public void setFk_propietario(int fk_propietario) {
        this.fk_propietario = fk_propietario;
    }*/
}
