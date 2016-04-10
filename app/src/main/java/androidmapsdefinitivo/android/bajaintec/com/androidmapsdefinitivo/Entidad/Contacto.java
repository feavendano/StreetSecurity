package androidmapsdefinitivo.android.bajaintec.com.androidmapsdefinitivo.Entidad;

/**
 * Created by angel on 4/1/2016.
 */
public class Contacto {

    private String mNombre;
    private String mNumero;
    private boolean mSelected;

    public Contacto(String mNombre, String mNumero, boolean mSelected) {
        this.mNombre = mNombre;
        this.mNumero = mNumero;
        this.mSelected = mSelected;
    }

    public String getNombre() {
        return mNombre;
    }

    public void setNombre(String mNombre) {
        this.mNombre = mNombre;
    }

    public String getNumero() {
        return mNumero;
    }

    public void setNumero(String mNumero) {
        this.mNumero = mNumero;
    }

    public boolean isSelected() {
        return mSelected;
    }

    public void setSelected(boolean mSelected) {
        this.mSelected = mSelected;
    }
}
