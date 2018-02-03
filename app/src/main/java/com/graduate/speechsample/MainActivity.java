package com.graduate.speechsample;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends Activity implements TextToSpeech.OnInitListener , View.OnClickListener , AdapterView.OnItemSelectedListener  {

	private TextView txtSpeechInput;
	private ImageButton btnSpeechToText;
	private final int REQ_CODE_SPEECH_INPUT = 100;
	private String lang;
	private Button btnTextToSpeech;
    private TextToSpeech tts;
	private Spinner mLanguageSp;
	private  ArrayAdapter<CharSequence> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Initialize();



	}

	private void Initialize(){

		txtSpeechInput = findViewById(R.id.txtSpeechInput);
		btnSpeechToText = findViewById(R.id.btn_speech_to_text);
		btnTextToSpeech = findViewById(R.id.btn_text_to_speech);
		btnSpeechToText.setOnClickListener(this);
		btnTextToSpeech.setOnClickListener(this);
		mLanguageSp = (Spinner) findViewById(R.id.sp_language);
		adapter = ArrayAdapter.createFromResource(this, R.array.spinner_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mLanguageSp.setAdapter(adapter);
		mLanguageSp.setOnItemSelectedListener(this);
		lang = "en-GB";

        tts = new TextToSpeech(this, this);
		// hide the action bar
	}

	/**
	 * Showing google speech input dialog
	 * */
	private void promptSpeechInput() {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, lang);
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
				getString(R.string.speech_prompt));
		try {
			startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
		} catch (ActivityNotFoundException a) {
			Toast.makeText(getApplicationContext(),
					getString(R.string.speech_not_supported),
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Receiving speech input
	 * */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case REQ_CODE_SPEECH_INPUT: {
			if (resultCode == RESULT_OK && null != data) {

				ArrayList<String> result = data
						.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
				txtSpeechInput.setText(result.get(0));
			}
			break;
		}

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
//           You might use the lang of the device using this
//           Locale.getDefault().getLanguage();

            int result = tts.setLanguage(Locale.UK);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "This Language is not supported", Toast.LENGTH_SHORT).show();
            } else {
                btnTextToSpeech.setEnabled(true);
            }

        } else {
            Toast.makeText(this, "Initialization Failed!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View view) {
        if (view == btnSpeechToText) {
            promptSpeechInput();
        }
        if (view == btnTextToSpeech) {
            promptTextToSpeech();

        }
    }

    private void promptTextToSpeech(){
        String readText = txtSpeechInput.getText().toString().trim();
        if (!readText.isEmpty()){
            if (!lang.equals(getResources().getString(R.string.ar_locale))){
                if (!tts.isSpeaking()){
                    tts.setSpeechRate(1);
                    Locale locale ;
                    switch (lang){
                        case "en-GB" :
                            locale = Locale.UK;
                            break;
                        case "fr-FR":
                            locale = Locale.FRANCE;
                            break;
                        case "de-DE":
                            locale = Locale.GERMANY;
                            break;
                        case "es-ES":
                            locale = new Locale("es", "ES");
                            break;
                        case "ru-RU":
                            locale = new Locale("ru","RU");
                            break;
                        default:
                            locale = Locale.UK;
                    }
                    tts.setLanguage(locale);
                    tts.speak(readText, TextToSpeech.QUEUE_FLUSH, null);
                }else {
                    tts.stop();
                }
            }else {
                Toast.makeText(getApplicationContext(),"Ooop! Arabic Lang is not supported!",Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(),"Please Speak First!", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        tts.stop();
        if (tts != null) {
            tts.shutdown();
        }
    }

	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    	String mSelectedLang = mLanguageSp.getSelectedItem().toString();

		switch (mSelectedLang){
			case "English" :
				lang = getResources().getString(R.string.gb_code);
				break;
			case "Arabic" :
				lang = getResources().getString(R.string.ar_code);
				break;
			case "French":
				lang = getResources().getString(R.string.fr_code);
				break;
			case "German":
				lang = getResources().getString(R.string.de_code);
				break;
			case "Spanish":
				lang = getResources().getString(R.string.es_code);
				break;
			case "Russian":
				lang = getResources().getString(R.string.ru_code);
				break;
			default:
				lang = getResources().getString(R.string.gb_code);
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {

	}
}




