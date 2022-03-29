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
        if(dbConnector.getCurrentQueryResult() != null){
            String result = "";

            //Get Data
            String[][] data = dbConnector.getCurrentQueryResult().getData();
            String[] columnNames = dbConnector.getCurrentQueryResult().getColumnNames();
            String[] columnTypes = dbConnector.getCurrentQueryResult().getColumnTypes();
            int columnCount = dbConnector.getCurrentQueryResult().getColumnCount();

            int[] columnLength = new int[columnCount];
            for(int i = 0; i<columnLength.length; i++){
                columnLength[i] = maxWidth(columnNames[i], data, i);
            }

            //Make top border
            for (int i = 0; i < columnLength.length; i++) {
                for(int j = 0; j<columnLength[i]; j++) {
                    result += "_";
                }
                result += "___";
            }
            result += "\n";

            //Make row of titles
            for (int i = 0; i < columnCount; i++) {
                result += columnNames[i]+freeSpaces(columnLength[i]-columnNames[i].length())+" | ";
            }
            result += "\n";

            //Make center border
            for (int i = 0; i < columnLength.length; i++) {
                for(int j = 0; j<columnLength[i]; j++) {
                    result += "-";
                }
                result += "---";
            }
            result += "\n";

            //Fill Table
            for(int i = 0; i<data.length; i++) {
                for (int j = 0; j < columnCount; j++) {
                    result += data[i][j]+freeSpaces(columnLength[j]-data[i][j].length())+" | ";
                }
                result += "\n";
            }

            //Make bottom border
            for (int i = 0; i < columnLength.length; i++) {
                for(int j = 0; j<columnLength[i]; j++) {
                    result += "_";
                }
                result += "___";
            }
            result += "\n";

            //Print result
            System.out.println(result);
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
        dbConnector.executeStatement("SELECT * FROM Aktivität");

        if(dbConnector.getCurrentQueryResult()!=null) {
            //Get Data
            String[][] data = dbConnector.getCurrentQueryResult().getData();
            String[] columnNames = dbConnector.getCurrentQueryResult().getColumnNames();
            String[] columnTypes = dbConnector.getCurrentQueryResult().getColumnTypes();
            int columnCount = dbConnector.getCurrentQueryResult().getColumnCount();
            int rowCount = dbConnector.getCurrentQueryResult().getRowCount();

            int[] columnLength = new int[columnCount];
            for (int i = 0; i < columnLength.length; i++) {
                columnLength[i] = maxWidth(columnNames[i], data, i);
            }

            //Create allAktivities
            Activity[] allActivities = new Activity[rowCount];
            for (int i = 0; i < data.length; i++) {
                allActivities[i] = new Activity(data[i]);
            }

            printAllActivities(allActivities, Activity.attributeNames);


            Activity[] sortedActivities = new Activity[allActivities.length];
            for(int i = 0; i<allActivities.length; i++){
                sortedActivities[i] = allActivities[i];
            }
            sortActivitiesRec(sortedActivities, 1, 0, allActivities.length-1);

            printAllActivities(sortedActivities, Activity.attributeNames);
        }
    }

    private Activity[] sortActivitiesRec(Activity[] allActivities, int attribute, int start, int end){
        if(start<end){

            int left = start;
            int right = end;
            int middle = (start+end)/2;
            Activity pivot = allActivities[middle];

            while (left<right) {
                if(Activity.attributeTypes[attribute].equals("String")) {
                    while (allActivities[left].getAll()[attribute].compareTo(pivot.getAll()[attribute]) < 0 && left<middle) {
                        left++;
                    }
                    while (allActivities[right].getAll()[attribute].compareTo(pivot.getAll()[attribute]) > 0 && right>middle) {
                        right--;
                    }
                }
                if(Activity.attributeTypes[attribute].equals("int")) {
                    while (Integer.parseInt(allActivities[left].getAll()[attribute]) < Integer.parseInt(pivot.getAll()[attribute]) && left<middle) {
                        left++;
                    }
                    while (Integer.parseInt(allActivities[right].getAll()[attribute]) > Integer.parseInt(pivot.getAll()[attribute]) && right>middle) {
                        right--;
                    }
                }

                if(left<=right) {
                    Activity temp = allActivities[left];
                    allActivities[left] = allActivities[right];
                    allActivities[right] = temp;

                    if(middle == left){
                        middle = right;
                        left++;
                    } else if(middle == right){
                        middle = left;
                        right--;
                    }else{
                        left++;
                        right--;
                    }

                    //printAllActivities(allActivities, Activity.attributeNames);
                }
            }

            sortActivitiesRec(allActivities, attribute, start, middle-1);
            sortActivitiesRec(allActivities, attribute, middle+1, end);
        }

        return allActivities;
    }

    private void printAllActivities(Activity[] allActivities, String[] titles){
        int[] columnLength = maxWidthActivity(allActivities, titles);

        String result = "";

        //Make top border
        for (int i = 0; i < columnLength.length; i++) {
            for(int j = 0; j<columnLength[i]; j++) {
                result += "_";
            }
            result += "___";
        }
        result += "\n";

        //Make row of titles
        for(int i = 0; i < titles.length; i++){
            result += titles[i]+freeSpaces(columnLength[i]-titles[i].length())+" | ";
        }
        result += "\n";

        //Make center border
        for (int i = 0; i < columnLength.length; i++) {
            for(int j = 0; j<columnLength[i]; j++) {
                result += "-";
            }
            result += "---";
        }
        result += "\n";

        //Fill Table
        for(int i = 0; i<allActivities.length; i++) {
            String[] activityData = allActivities[i].getAll();

            for (int j = 0; j < activityData.length; j++) {
                result += activityData[j]+freeSpaces(columnLength[j]-activityData[j].length())+" | ";
            }
            result += "\n";
        }

        //Make bottom border
        for (int i = 0; i < columnLength.length; i++) {
            for(int j = 0; j<columnLength[i]; j++) {
                result += "_";
            }
            result += "___";
        }
        result += "\n";

        //Print result
        System.out.println(result);
    }

    private int[] maxWidthActivity(Activity[] array, String[] titles){
        int[] result = new int[6];

        for(int t = 0; t<titles.length; t++) {
            result[t] = titles[t].length();
            if (array != null) {
                for (int i = 0; i < array.length; i++) {
                    if (array[i] != null) {
                        if (array[i].getAll()[t].length() > result[t]) {
                            result[t] = array[i].getAll()[t].length();
                        }
                    }
                }
            }
        }

        return result;
    }
}
