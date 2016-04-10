package androidmapsdefinitivo.android.bajaintec.com.androidmapsdefinitivo.Entidad;

/**
 * Created by angel on 3/31/2016.
 */
public class Usuario {
    private int mIdUsuario;
    private String mNombreUsuario;
    private String mContrasena;
    private String mCorreo;
    private String mFecha;
    private String mSexo;
    private String mOcupacion;

    public int getIdUsuario() {
        return mIdUsuario;
    }

    public void setIdUsuario(int mIdUsuario) {
        this.mIdUsuario = mIdUsuario;
    }

    public String getNombreUsuario() {
        return mNombreUsuario;
    }

    public void setNombreUsuario(String mNombreUsuario) {
        this.mNombreUsuario = mNombreUsuario;
    }

    public String getContrasena() {
        return mContrasena;
    }

    public void setContrasena(String mContrasena) {
        this.mContrasena = mContrasena;
    }

    public String getCorreo() {
        return mCorreo;
    }

    public void setCorreo(String mCorreo) {
        this.mCorreo = mCorreo;
    }

    public String getFecha() {
        return mFecha;
    }

    public void setFecha(String mFecha) {
        this.mFecha = mFecha;
    }

    public String getSexo() {
        return mSexo;
    }

    public void setSexo(String mSexo) {
        this.mSexo = mSexo;
    }

    public String getOcupacion() {
        return mOcupacion;
    }

    public void setOcupacion(String mOcupacion) {
        this.mOcupacion = mOcupacion;
    }
}
