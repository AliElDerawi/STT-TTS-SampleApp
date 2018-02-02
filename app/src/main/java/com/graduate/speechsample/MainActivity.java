package com.graduate.speechsample;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends Activity implements TextToSpeech.OnInitListener , View.OnClickListener {

	private TextView txtSpeechInput;
	private ImageButton btnSpeechToText;
	private final int REQ_CODE_SPEECH_INPUT = 100;
	private RadioGroup mRadioGroup;
	private String lang;
	private Button btnTextToSpeech;
    private TextToSpeech tts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Initialize();


		mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup radioGroup, int id) {
				switch (id) {
					case R.id.rb_english:
						lang  = "en-US";
						break;
					case R.id.rb_arabic :
						lang = "ar-SA";
						break;
				}

			}
		});

	}

	private void Initialize(){

		txtSpeechInput = findViewById(R.id.txtSpeechInput);
		btnSpeechToText = findViewById(R.id.btn_speech_to_text);
		mRadioGroup = findViewById(R.id.rb_group);
		mRadioGroup.check(R.id.rb_english);
		btnTextToSpeech = findViewById(R.id.btn_text_to_speech);
		lang = "en-US";
		btnSpeechToText.setOnClickListener(this);
		btnTextToSpeech.setOnClickListener(this);
		btnTextToSpeech.setEnabled(false);

        tts = new TextToSpeech(this, this);
		// hide the action bar
		getActionBar().hide();
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
            if (!lang.equals("ar-SA")){
                if (!tts.isSpeaking()){
                    tts.setSpeechRate(1);
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
}




