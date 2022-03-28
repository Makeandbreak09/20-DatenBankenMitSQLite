package control;

import model.Activity;

public class MainController {

    //Referenzen
    private DatabaseConnector dbConnector;

    /**
     * Beim Erstellen eines MainController-Objekts wird ein neues DatabeseConnector-Objekt erstellt.
     */
    public MainController(){
        dbConnector = new DatabaseConnector("127.0.0.0",1337,"\\model\\Jugendtreff.sqlite","admin","12346");
    }


    public void doSomething(){
        dbConnector.executeStatement("SELECT DISTINCT Stadt FROM Veranstaltungsort");

        int anzahl = dbConnector.getCurrentQueryResult().getRowCount();
        String[][] erstesErgebnis = dbConnector.getCurrentQueryResult().getData();

        dbConnector.executeStatement("SELECT Stadt, Dauer FROM Aktivität, Veranstaltungsort WHERE Aktivität.OrtID = Veranstaltungsort.OrtID");
        String[][] zweitesErgebnis = dbConnector.getCurrentQueryResult().getData();

        for(int i = 0; i < anzahl; i++){
            int gesamt = 0;
            for(int j = 0; j < zweitesErgebnis.length; j++){
                if(erstesErgebnis[i][0].equals(zweitesErgebnis[j][0])){
                    gesamt = gesamt + Integer.parseInt(zweitesErgebnis[j][1]);
                }
            }
            System.out.println(erstesErgebnis[i][0] + ": " +gesamt);
        }
    }

    public void testAQuery(){
        dbConnector.executeStatement("SELECT * FROM Aktivität");
        printLastQuery();
    }

    /**
     * Falls die letzte Abfrage (!) an die Datenbank keinen Fehler produziert hat, so wird das Ergebnis der Abfrage mit Zeilenkopf der Tabelle "schön" in der Systemkonsole ausgegeben.
     * "schön" bedeutet dabei, dass als erste Zeile durch z.B. ein | getrennt die Attributnamen stehen, dann eine Zeile mit ---- zur Trennung der Kopfzeile von den Daten, dann die Daten pro Zeile getrennt durch |.
     * Falls die letzte Abfrage (!) an die Datenbank einen Fehler produziert hat, so wird die Fehlernachricht in der Systemkonsole ausgegeben.
     *
     * Für richtig schöne Ausgaben (falls vorhanden) sollten die Methoden MaxWidth und freeSpaces umgesetzt und klug verwendet werden.
     */
    public void printLastQuery(){
        //TODO 01: siehe Kommentar
        if(dbConnector.getCurrentQueryResult() != null){
            String result = "";

            String[][] data = dbConnector.getCurrentQueryResult().getData();
            String[] columnNames = dbConnector.getCurrentQueryResult().getColumnNames();
            String[] columnTypes = dbConnector.getCurrentQueryResult().getColumnTypes();
            int columnCount = dbConnector.getCurrentQueryResult().getColumnCount();

            for (int j = 0; j < columnCount; j++) {

            }

            for(int i = 0; i<data.length; i++) {
                for (int j = 0; j < columnCount; j++) {

                }
            }
        }
    }


    /**
     * Die Verbindung zur Datenbank wird geschlossen.
     */
    public void close(){
        dbConnector.close();
    }

    /**
     * Falls ein vollständig initialisierter String und ein vollständig initialisiertes, zweidimensionales Zeichenketten-Feld sowie ein Index übergeben wird,
     * wird nach der größten Zeichenkettenlänge gesucht (der String selbst oder ein String im Feld zum Index [][index]) und diese zurückgegeben.
     * Ansonsten wird 0 zurückgegeben.
     * @param s Eine Zeichenkette
     * @param array Ein zweidimensionales Feld aus Zeichenketten
     * @param index Der vorgegebene Index der zweiten Dimension
     * @return
     */
    private int maxWidth(String s, String[][] array, int index){
        int result = 0;
        if(s != null){
            result = s.length();
            if(array != null){
                for(int i = 0; i < array.length; i++) {
                    if(array[i] != null){
                        if(array[i][index].length() > result){
                            result = array[i][index].length();
                        }
                    }
                }
            }
        }

        return result;
    }

    /**
     * Bei Aufruf wird ein String erstellt, der count oft Leerzeichen beinhaltet.
     *
     * Beispiel: freeSpaces(5) liefert das Ergebnis "     " zurück.
     * @param count Anzahl der gewünschten Leerzeichen.
     * @return Fertiger String mit Leerzeichen.
     */
    private String freeSpaces(int count){
        String output = "";
        for(int i = 0; i < count; i++){
            output = output + " ";
        }
        return output;
    }

    /**
     * Diese Methode dient dazu, Informationen aus der Datenbank in Objekten zu speichern und Sortieralgorithmen für Arrays zu wiederholen.
     *
     * Bei Aufruf findet eine Datenbankabfrage aller Aktivitäten statt.
     * Daraufhin werden alle Aktivitäten in einem Feld gespeichert. Dieses Feld ist vom Datentyp Activity (Klasse muss vorher noch vervollständigt werden).
     * Das Feld wird vollständig in der Systemkonsole ausgegeben (möglichst kompakt).
     * Anschließend wird das Feld nach einem Attribut sortiert - hierzu ein Attribut auswählen sowie einen Sortieralgorithmus.
     * Zu guter letzt wird die sortierte Variante nochmal in der Systemkonsole ausgegeben.
     *
     * Es ist im Sinne der Übung nicht erlaubt, bereits mit der SQL-Abfrage zu sortieren.
     */
    public void printSortedActivities(){
        //TODO 02: siehe Kommentar
    }
}
