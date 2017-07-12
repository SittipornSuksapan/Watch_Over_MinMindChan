package mean.chan.mind.sendgps;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

public class AddChildActivity extends AppCompatActivity {
    private EditText codeEditText, nameEditText;
    private ImageView pictureImageView;
    private RadioGroup radioGroup;
    private String codeString,nameString,genderString;
    private String[] loginStrings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);

        //Get Value from Intent
        getValueFromIntent();


        //Initial view
        initialView();

        //RadioGroup Controller
        radioGroupController();

        //Back
        backController();

        //Camera Controller
        cameraController();

        //Save controller
        saveController();


    }  //Main Method

    private void radioGroupController() {

    }

    private void getValueFromIntent() {
        loginStrings = getIntent().getStringArrayExtra("Login");
    }

    private void initialView() {
        pictureImageView = (ImageView) findViewById(R.id.imPicture);
        codeEditText = (EditText) findViewById(R.id.edtCode);
        nameEditText = (EditText) findViewById(R.id.edtName);
        radioGroup = (RadioGroup) findViewById(R.id.ragGender);

    }

    private void saveController() {
        ImageView imageView = (ImageView) findViewById(R.id.imvSave);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get value from edit text
                codeString = codeEditText.getText().toString().trim();
                nameString = nameEditText.getText().toString().trim();

                //check space
                if (codeString.equals("") || nameString.equals("")) {
                    //have space
                    MyAlert myAlert = new MyAlert(AddChildActivity.this);
                    myAlert.myDialog("Have space","Please fill all");

                } else {
                    //no space
                    uploadValueToServer();

                }

            }
        });
    }

    private void uploadValueToServer() {



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            Uri uri = data.getData();
            try {

                Bitmap bitmap = BitmapFactory
                        .decodeStream(getContentResolver().openInputStream(uri));
                pictureImageView.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    } //onActivity

    private void cameraController() {

        ImageView cameraImageView = (ImageView) findViewById(R.id.imvCamera);

        cameraImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);

            }
        });
    }

    private void backController() {
        ImageView imageView = (ImageView) findViewById(R.id.imvBack);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
} //Main Class
