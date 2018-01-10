package com.example.kennykanp.bmmbusiness;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.kennykanp.bmmbusiness.Model.Driver;
import com.example.kennykanp.bmmbusiness.Model.FirebaseReference;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class CreateAccountDriverActivity extends BaseActivity implements View.OnClickListener{

    EditText tvName, tvLastName, tvPhoneNumber, tvEmail, tvUser, tvPassword, tvConfirmPassword;
    private Button btRegister;
    private ImageButton btSelectImage;
    private static final String TAG = "EmailPassword";

    private FirebaseAuth mAuth;

    private StorageReference mStorage;
    private  static final int GALLERY_INTENT = 2;

    FirebaseDatabase database =FirebaseDatabase.getInstance();
    final DatabaseReference Ref=database.getReference(FirebaseReference.BMM_REFERENCES);
    Driver driver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_account_driver);

        showToolBar(getResources().getString(R.string.toolbar_tittle_createAccount), true);

        mStorage = FirebaseStorage.getInstance().getReference();

        tvName = (EditText)findViewById(R.id.name_createAccount);
        tvLastName = (EditText) findViewById(R.id.last_name_createAccount);
        tvPhoneNumber = (EditText) findViewById(R.id.number_phone_createAccount);
        tvEmail = (EditText) findViewById(R.id.email_createAccount);
        tvUser = (EditText) findViewById(R.id.user_createAccount);
        tvPassword = (EditText) findViewById(R.id.password_createAccount);
        //tvPhoto = (EditText) findViewById(R.id.photo_Uri);

        btRegister = (Button) findViewById(R.id.register_button_createAccount);
        btSelectImage = (ImageButton) findViewById(R.id.btn_Insert_Image);

        btRegister.setOnClickListener(this);
        btSelectImage.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();

    }

    public void showToolBar(String tittle, boolean upButton){
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

    @Override
    public void onClick(View view) {

        int i = view.getId();
        if (i == R.id.btn_Insert_Image) {
            UploadImage();
        } else if (i == R.id.register_button_createAccount) {
            createDrive();
        }

    }

    private  void UploadImage(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,GALLERY_INTENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK){
            Uri uri = data.getData();
            StorageReference filePath = mStorage.child("DrivePhotos").child(uri.getLastPathSegment());

            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(CreateAccountDriverActivity.this, "Upload Done.",Toast.LENGTH_LONG).show();

                }
            });
        }
    }

    private void createDrive() {
        if (!validateForm()) {
            return;
        }
        showProgressDialog();

        String Name = tvName.getText().toString();
        String LastName = tvLastName.getText().toString();
        String NumberPhone = tvPhoneNumber.getText().toString();
        String Email = tvEmail.getText().toString();
        String User = tvUser.getText().toString();
        String Password = tvPassword.getText().toString();
        //String Photo = tvPhoto.getText().toString();

        driver =new Driver(Name,LastName,NumberPhone, Email, User, Password,"this photo");

        //Create Account
        createAccount(Email, Password);

        hideProgressDialog();
    }

    private void createAccount(String email, String password) {

        Log.d(TAG, "createAccount:" + email);

        showProgressDialog();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Save Data
                            Ref.child(FirebaseReference.DRIVE_REFERENCES).push().setValue(driver);
                            Toast.makeText(CreateAccountDriverActivity.this,"Usuario Agregado", Toast.LENGTH_SHORT).show();

                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(CreateAccountDriverActivity.this, "Authentication sucess.",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(CreateAccountDriverActivity.this, MainActivity.class);
                            finish();
                            CleanField();
                            startActivity(intent);
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(CreateAccountDriverActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // [START_EXCLUDE]
                            hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = tvEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            tvEmail.setError("Required.");
            valid = false;
        } else {
            tvEmail.setError(null);
        }

        String password = tvPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            tvPassword.setError("Required.");
            valid = false;
        } else {
            tvPassword.setError(null);
        }

        return valid;
    }

    void CleanField(){
        tvName.setText("");
        tvLastName.setText("");
        tvPhoneNumber.setText("");
        tvEmail.setText("");
        tvUser.setText("");
        tvPassword.setText("");
        tvConfirmPassword.setText("");
        tvName.requestFocus();
    }

}
