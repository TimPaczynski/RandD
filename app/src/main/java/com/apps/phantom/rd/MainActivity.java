package com.apps.phantom.rd;
import ai.api.AIConfiguration;
import ai.api.AIListener;
import ai.api.AIService;
import ai.api.model.AIError;
import ai.api.model.AIResponse;
import ai.api.model.Result;

import com.apps.phantom.rd.database.RandDDbSchema;
import com.apps.phantom.rd.database.RandDBaseHelper;

import com.google.gson.JsonElement;
import java.util.Map;

import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.apps.phantom.rd.database.RandDDbSchema.RandDTable;


public class MainActivity extends ActionBarActivity implements AIListener {

    private final static int REQUEST_AUDIO_PERMISSIONS_ID = 33;
    private AIService aiService;
    private Button ListenButton;
    private TextView ResultTextView;

    private SQLiteDatabase mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final AIConfiguration config = new AIConfiguration("18e1480485364dc6a365983439f23275",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);

        aiService = AIService.getService(this, config);
        aiService.setListener(this);

        //this line might not work
        mDatabase = new RandDBaseHelper(this).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(RandDTable.Cols.ID, "111");
        values.put(RandDTable.Cols.PREMIUM, "$16800");
        values.put(RandDTable.Cols.DEDUCTIBLE, "$1123");
        values.put(RandDTable.Cols.REMAINING, "$723.22");
        mDatabase.insert(RandDTable.NAME, null, values);



        setContentView(R.layout.activity_main);
        ListenButton = (Button) findViewById(R.id.listenbutton);
        ResultTextView = (TextView) findViewById(R.id.result_text);
    }


    public void listenButtonOnClick(final View view) {

        aiService.startListening();
    }

    @Override
    public void onResult(final AIResponse response) {
        Result result = response.getResult();

        // Get parameters
        String parameterString = "";
        if (result.getParameters() != null && !result.getParameters().isEmpty()) {
            for (final Map.Entry<String, JsonElement> entry : result.getParameters().entrySet()) {
                parameterString += "(" + entry.getKey() + ", " + entry.getValue() + ") ";
            }
        }

        if (result.getAction().contains("nearme")) {
            // go to near me and filter

        } else {
            Cursor cursor = mDatabase.query(
                    RandDTable.NAME,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            // Show results in TextView
            String x = result.getAction();

            cursor.moveToFirst();

            if (x.contains("premium")) {
                String data = cursor.getString(1);
                ResultTextView.setText(data);
                cursor.close();
                //ResultTextView.setText(Integer.toString(x));

            }
            else if (x.contains("total")){
                String data = cursor.getString(2);
                ResultTextView.setText(data);
                cursor.close();
            }
            else if (x.contains("remaining")){
                String data = cursor.getString(3);
                ResultTextView.setText(data);
                cursor.close();
            }
            else {
                ResultTextView.setText(result.getAction());
            }
   /*     // To make this work just run result.getActions() and send it through Mark Logic Black box bfore putting it here
        ResultTextView.setText("Query:" + result.getResolvedQuery() +
                "\nAction: " + result.getAction() +
                "\nParameters: " + parameterString);*/
        }
    }

    @Override
    public void onError(final AIError error) {
        ResultTextView.setText(error.toString());
    }

    @Override
    public void onAudioLevel(float level) {

    }

    @Override
    public void onListeningStarted() {

    }

    @Override
    public void onListeningCanceled() {

    }

    @Override
    public void onListeningFinished() {

    }

    protected void onStart() {
        super.onStart();

        checkAudioRecordPermission();
    }

    protected void checkAudioRecordPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {


            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.RECORD_AUDIO)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        REQUEST_AUDIO_PERMISSIONS_ID);


            }
        }
    }
}
/*    @Override
     public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
                switch (requestCode) {
                        case REQUEST_AUDIO_PERMISSIONS_ID: {
                                // If request is cancelled, the result arrays are empty.
                                if (grantResults.length > 0
                                         && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                                        // permission was granted, yay! Do the
                                        // contacts-related task you need to do.

                                    } else {


                                        // permission denied, boo! Disable the
                                         // functionality that depends on this permission.
                                    }
                                 return;
                            }
                     }
            }

}*/

