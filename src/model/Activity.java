package model;

public class Activity {

    public final static String[] attributeNames = {"Kurzbeschreibung", "Ort ID", "Dauer", "Langbeschreibung", "Partner Bezeichnung", "Partner Tel.-Nr."};
    public final static String[] attributeTypes = {"String", "int", "int", "String", "String", "String"};

    private String kurzbeschreibung;
    private int ortID;
    private int dauer;
    private String langbeschreibung;
    private String partnerBezeichnung;
    private String partnerTelNr;

    public Activity(String[] data) {
        this.setAll(data);
    }

    public void setAll(String[] data){
        kurzbeschreibung = data[0];
        ortID = Integer.parseInt(data[1]);
        dauer = Integer.parseInt(data[2]);
        langbeschreibung = data[3];
        partnerBezeichnung = data[4];
        partnerTelNr = data[5];
    }

    public String[] getAll(){
        String[] output = new String[6];
        output[0] = kurzbeschreibung;
        output[1] = ortID +"";
        output[2] = dauer +"";
        output[3] = langbeschreibung;
        output[4] = partnerBezeichnung;
        output[5] = partnerTelNr;
        return output;
    }

    public String getKurzbeschreibung() {
        return kurzbeschreibung;
    }

    public int getOrtID() {
        return ortID;
    }

    public int getDauer() {
        return dauer;
    }

    public String getLangbeschreibung() {
        return langbeschreibung;
    }

    public String getPartnerBezeichnung() {
        return partnerBezeichnung;
    }

    public String getPartnerTelNr() {
        return partnerTelNr;
    }
}
