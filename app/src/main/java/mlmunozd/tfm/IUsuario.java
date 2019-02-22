package mlmunozd.tfm;

import java.util.Date;

public interface IUsuario {

    public String getLogin();

    public void setLogin(String login);

    public String getPassword();

    public void setPassword(String password);

    public String getNombre();

    public void setNombre(String nombre);

    public String getEmail();

    public void setEmail(String email);

    public String getToken();

    public void setToken(String token);

    public Date getTokenExpiracion();

    public void setTokenExpiracion(Date tokenExpiracion);

    public int getPk_usuario();

    public void setPk_usuario(int pk_usuario);

    /*public int getFk_propietario();

    public void setFk_propietario(int fk_propietario);

    public String getPropietario();

    public void setPropietario(String propietario);*/
}
