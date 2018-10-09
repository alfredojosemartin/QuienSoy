package android.quiensoy;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by alfredojose.martin on 08/10/2018.
 */
class WordsFileReader {
    Context myContext;
    WordsFileReader(Context context) {
        myContext = context;

    }

    /***
     * Read a file to get all the lines in it
     *
     * @param id the id of raw resource (file)
     * @return String array
     */
    String[] getWords(int id){
        String line;
        ArrayList<String> collection = new ArrayList<>();
        try
        {
            InputStream fraw = myContext.getResources().openRawResource(id);
            BufferedReader brin = new BufferedReader(new InputStreamReader(fraw));

            do {
                line = brin.readLine();
                Log.d("File Reader", "Line = " + line);
                collection.add(line);
            }while(line != null);

            fraw.close();
            return collection.toArray(new String[0]);
        }
        catch (Exception ex)
        {
            Log.e("File Reader", "Error reading raw file");
            return null;
        }
    }


}
