package Objects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Inserat
{
    public int id;
    public String beschreibung;
    public String marke;
    public int PS;
    public float verbrauch;
    public int groesse;
    public int kilometerstand;
    public String verbrauchsstoff;
    public String bezeichnung;
    public String ausstattung;

    public Inserat(int id, String beschreibung, String marke, int PS, float verbrauch, int groesse, int kilometerstand, String verbrauchsstoff, String bezeichnung, String ausstattung)
    {
        this.id = id;
        this.beschreibung = beschreibung;
        this.marke = marke;
        this.PS = PS;
        this.verbrauch = verbrauch;
        this.groesse = groesse;
        this.kilometerstand = kilometerstand;
        this.verbrauchsstoff = verbrauchsstoff;
        this.bezeichnung = bezeichnung;
        this.ausstattung = ausstattung;
    }

    public Inserat(ResultSet result) throws SQLException
    {
        this.id = result.getInt("id");
        this.beschreibung = result.getString("Beschreibung");
        this.marke = result.getString("marke");
        this.PS = result.getInt("PS");
        this.verbrauch = result.getFloat("verbrauch");
        this.groesse = result.getInt("groesse");
        this.kilometerstand = result.getInt("kilometerstand");
        this.verbrauchsstoff = result.getString("verbrauchsstoff");
        this.bezeichnung = result.getString("Bezeichnung");
        this.ausstattung = result.getString("Ausstattung");

    }

}
