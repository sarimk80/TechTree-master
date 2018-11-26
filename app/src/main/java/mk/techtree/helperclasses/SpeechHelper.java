package mk.techtree.helperclasses;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import mk.techtree.R;
import mk.techtree.helperclasses.ui.helper.UIHelper;


public class SpeechHelper implements RecognitionListener {

    private static final int REQUEST_RECORD_PERMISSION = 100;


    private SpeechRecognizer speech;
    private Intent recognizerIntent;
    private Activity activity;
    private String LOG_TAG = "VoiceRecognitionActivity";
    private ToggleButton btnView;
    private ProgressBar progressBar;
    private EditText edSpeech;
    OnSpeechRecognitionResult onSpeechRecognitionResult;

    //  Destroy speech in onStop() in fragment
    //     if (speech != null) {
    //        speech.destroy();
    //        Log.i(LOG_TAG, "destroy");
    //    }

    public SpeechHelper(Activity activity, ToggleButton btnView, ProgressBar progressBar, EditText edSpeech, OnSpeechRecognitionResult onSpeechRecognitionResult) {


        this.btnView = btnView;
        this.progressBar = progressBar;
        this.activity = activity;
        this.edSpeech = edSpeech;
        this.onSpeechRecognitionResult = onSpeechRecognitionResult;


        speech = SpeechRecognizer.createSpeechRecognizer(activity);
        speech.setRecognitionListener(this);
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
                "en");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);
//        recognizerIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 1000000);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, 5000);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 5000);


        progressBar.setVisibility(View.GONE);
        edSpeech.setSelection(edSpeech.getText().length());

        setListener();

    }


    public SpeechRecognizer getSpeech() {
        return speech;
    }


    private void setListener() {
        btnView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    UIHelper.hideSoftKeyboard(activity, edSpeech);
//                    btnView.setBackgroundDrawable(activity.getDrawable(R.drawable.micorange));
                    // Place your icon here
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setIndeterminate(true);
                    if (ActivityCompat.checkSelfPermission(activity,
                            Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(activity,
                                new String[]{Manifest.permission.RECORD_AUDIO},
                                REQUEST_RECORD_PERMISSION);
                    } else {
                        Log.e("DB", "PERMISSION GRANTED");
                        speech.startListening(recognizerIntent);
                    }

                } else {
//                    btnView.setBackgroundDrawable(activity.getDrawable((R.drawable.micgreen)));
                    // Place your icon here

                    progressBar.setIndeterminate(false);
                    progressBar.setVisibility(View.GONE);
                    speech.stopListening();
                }
            }
        });
    }



    @Override
    public void onReadyForSpeech(Bundle bundle) {

    }

    @Override
    public void onBeginningOfSpeech() {
        Log.i(LOG_TAG, "onBeginningOfSpeech");
        progressBar.setIndeterminate(false);
        progressBar.setMax(50);
    }

    @Override
    public void onRmsChanged(float rmsdB) {
//        Log.i(LOG_TAG, "onRmsChanged: " + rmsdB);
        progressBar.setProgress((int) rmsdB);
    }


    @Override
    public void onBufferReceived(byte[] bytes) {

    }

    @Override
    public void onEndOfSpeech() {
//        Log.i(LOG_TAG, "onEndOfSpeech");
        progressBar.setIndeterminate(true);
        btnView.setChecked(false);
    }

    @Override
    public void onError(int errorCode) {
        String errorMessage = getErrorText(errorCode);
//        UIHelper.showToast(activity, errorMessage);
        btnView.setChecked(false);

        onSpeechRecognitionResult.onError(errorMessage);
    }

    @Override
    public void onResults(Bundle results) {
        ArrayList<String> result = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String currentValue = "";
        if (result != null) {
            if (result.get(0).equalsIgnoreCase("\n") || result.get(0).equalsIgnoreCase("\n\n")) {
                currentValue = result.get(0);
            } else {
                currentValue = result.get(0) + " ";
            }
        }


        setDictionaryText(currentValue);

        onSpeechRecognitionResult.onResults(currentValue);

    }

    @Override
    public void onPartialResults(Bundle bundle) {

    }

    @Override
    public void onEvent(int i, Bundle bundle) {

    }


    public interface OnSpeechRecognitionResult {
        void onResults(String result);
        void onError(String error);
    }


    public String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }


    public static TreeMap<String, String> getMapCommands() {


        TreeMap<String, String> mapCommands = new TreeMap<String, String>();

//        mapCommands.put( "ampersand"                ,  "&"    );
        mapCommands.put( "dot"                      ,  "."    );
//        mapCommands.put( "colon"                    ,  ":"    );
//        mapCommands.put( "question mark"            ,  "?"    );
//        mapCommands.put( "exclamation point"        ,  "!"    );
        mapCommands.put( "comma"                    ,  ","    );
//        mapCommands.put( "equal sign"               ,  "="    );
//        mapCommands.put( "at sign"                  ,  "@"    );
        mapCommands.put( "slash"                    ,  "/"    );
//        mapCommands.put( "hyphen"                   ,  "-"    );
//        mapCommands.put( "open single quote"        ,  "'"    );
//        mapCommands.put( "close single quote"       ,  "'"    );
//        mapCommands.put( "open double quote"        ,  "\""   );
//        mapCommands.put( "close double quote"       ,  "\""   );
//        mapCommands.put( "open parenthesis"         ,  "("    );
//        mapCommands.put( "close parenthesis"        ,  ")"    );
//        mapCommands.put( "percent sign"             ,  "%"    );
        mapCommands.put( "new line"                 ,  "\n"   );
        mapCommands.put( "next line"                ,  "\n"   );
        mapCommands.put( "new paragraph"            ,  "\n\n" );
        mapCommands.put( "new para"                 ,  "\n\n" );
        mapCommands.put( "new heading"              ,  "\n\n" );    // TWICE ONCE BEFORE AND ENTER TWICE AFTER TEXT

        return mapCommands;
    }


    public void setDictionaryText(String currentValue) {

        int start = Math.max(edSpeech.getSelectionStart(), 0);
        int end = Math.max(edSpeech.getSelectionEnd(), 0);
        edSpeech.getText().replace(Math.min(start, end), Math.max(start, end),
                currentValue, 0, currentValue.length() );


        TreeMap<String, String> mapCommands = SpeechHelper.getMapCommands();
        String text = edSpeech.getText().toString().toLowerCase();


        for (String s : mapCommands.keySet()) {
            s = s.toLowerCase();
            text = text.replaceAll(" " + s + " ", mapCommands.get(s) + " ");
            text = text.replaceAll(s + " ", mapCommands.get(s)+ " ") ;
        }

        if (edSpeech.getText().toString().contains("new heading")) {
            text = text.concat("\n\n");
        }
        edSpeech.setText(StringHelper.sentenceCaseForText(text));
        edSpeech.setSelection(edSpeech.getText().length());
    }


    public static String getKeyFromValue(HashMap hm, Object value) {
        for (Object o : hm.keySet()) {
            if (hm.get(o).equals(value)) {
                return (String) o;
            }
        }
        return "";
    }
}
