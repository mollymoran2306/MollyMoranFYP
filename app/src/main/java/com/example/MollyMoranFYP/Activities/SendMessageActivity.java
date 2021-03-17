package com.example.MollyMoranFYP.Activities;



import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.MollyMoranFYP.Models.Message;
import com.example.MollyMoranFYP.Models.Reminder;
import com.example.MollyMoranFYP.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.UUID;

import static com.example.MollyMoranFYP.Activities.AdminHomeActivity2.ProfilePic;

public class SendMessageActivity extends AppCompatActivity {
    private Button btnSend;
    private EditText txtMessage, txtSubject;
    private ImageView imageView;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String Name = "nameKey";
    public static final String ID = "userID";

    private Uri filePath;
    private Uri imageUri;
    private String imageUrl;

    Random rand = new Random();
    int rNum = 100 + rand.nextInt((999 - 100) + 1);
    String fin =  Integer.toString(rNum);
    String id;

    private DatabaseReference db;
    private static int now, repeat;
    private static final String TAG = "*SendMessageActivity*";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendmessage);
        getSupportActionBar().hide();
        setTitle("Send Message"); //might change to add to message board
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            now = extras.getInt("now");
        }
        btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // postMessage();
               // postImage();
                upload();
            }
        });

        imageView = findViewById(R.id.my_avatar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage(SendMessageActivity.this);
            }
        });
    }


//    public void postMessage() {
//        boolean flag = true;
//
//        // Log.d("chk", String.valueOf(now));
//        txtSubject = findViewById(R.id.txtSubject);
//        String subject = txtSubject.getText().toString();
//        txtMessage = findViewById(R.id.txtMessage);
//        String message = txtMessage.getText().toString();
//        String sender = sharedpreferences.getString(Name, "");
//        String image = filePath.toString();
//
//        if (subject.isEmpty()) {
//            Toast.makeText(getApplicationContext(), "Message Subject is empty", Toast.LENGTH_LONG).show();
//            flag = false;
//        }
//      /*	Code	below	is	based	on	MyDay - master opensource reminders application
//                by Edge555 url:https://github.com/edge555/MyDay
//                     */
//        if (flag) {
//            FirebaseUser cursor = FirebaseAuth.getInstance().getCurrentUser();
//            if (cursor != null) {
//                String uid = cursor.getUid();
//
//                db = FirebaseDatabase
//                        .getInstance()
//                        .getReference()
//                        .child("Users")
//                        .child(uid)
//                        .child("Message");
////                }
//                Map<String, Object> val = new TreeMap<>();
//                Message message1;
//
//                    message1 = new Message(subject, message, image, sender, fin);
//
//                val.put(fin, message1);
//                id = fin;
//                db.updateChildren(val);
//
//                Log.d(TAG, "message saved to db" + message);
//            }
//            Toast.makeText(getApplicationContext(), "Message Sent!", Toast.LENGTH_LONG).show();
//            Intent intent = new Intent(SendMessageActivity.this, AdminHomeActivity2.class);
//            startActivity(intent);
//        }
//    }
    //END
private void upload() {

            /*	Code	below	is	based	on
     url:
                     */
    if (filePath != null) {
        final StorageReference filePath2 = FirebaseStorage.getInstance().getReference("Images").child(System.currentTimeMillis() + "." + getFileExtension(filePath));
        StorageTask uploadtask = filePath2.putFile(filePath);
        uploadtask.continueWithTask(new Continuation() {
            @Override
            public Object then(@NonNull Task task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return filePath2.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                Uri downloadUri = task.getResult();
                imageUrl = downloadUri.toString();

                FirebaseUser cursor = FirebaseAuth.getInstance().getCurrentUser();
                if (cursor != null) {
                    String uid = cursor.getUid();

                    txtSubject = findViewById(R.id.txtSubject);
                    String subject = txtSubject.getText().toString();
                    txtMessage = findViewById(R.id.txtMessage);
                    String message = txtMessage.getText().toString();
                    String sender = sharedpreferences.getString(Name, "");
                    //String userID = sharedpreferences.getString(ID, "");
                    String profilePic = sharedpreferences.getString(ProfilePic, "");
                    Log.d(TAG, "Shared Pref Profil Pic is" + profilePic);
                    String image;
                    if (imageUrl != null) {
                        image = imageUrl;
                    } else {
                        image = "";
                    }


                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                            .child("Users")
                            .child(uid)
                            .child("Message");
                    String postId = ref.push().getKey();

                    Map<String, Object> val = new TreeMap<>();
                    Message message1;

                    message1 = new Message(subject, message, image, sender, profilePic, fin);

                    val.put(fin, message1);
                    id = fin;
                    ref.updateChildren(val);

                    Log.d(TAG, "message saved to db" + message);

                }

                Toast.makeText(getApplicationContext(), "Message Sent!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SendMessageActivity.this, AdminHomeActivity2.class);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SendMessageActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show(); //e.get messages tells us exactly what went wrong
            }
        });
    } else {
        // Toast.makeText(this, "No image was selected!", Toast.LENGTH_SHORT).show();
        FirebaseUser cursor = FirebaseAuth.getInstance().getCurrentUser();
        if (cursor != null) {
            String uid = cursor.getUid();

            txtSubject = findViewById(R.id.txtSubject);
            String subject = txtSubject.getText().toString();
            txtMessage = findViewById(R.id.txtMessage);
            String message = txtMessage.getText().toString();
            String sender = sharedpreferences.getString(Name, "");
            //String userID = sharedpreferences.getString(ID, "");
            String profilePic = sharedpreferences.getString(ProfilePic, "");
            Log.d(TAG, "Shared Pref Profil Pic is" + profilePic);

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                    .child("Users")
                    .child(uid)
                    .child("Message");
            String postId = ref.push().getKey();

            Map<String, Object> val = new TreeMap<>();
            Message message1;

            message1 = new Message(subject, message, sender, profilePic, fin);

            val.put(fin, message1);
            id = fin;
            ref.updateChildren(val);

            Log.d(TAG, "message saved to db" + message);

            Toast.makeText(getApplicationContext(), "Message Sent!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(SendMessageActivity.this, AdminHomeActivity2.class);
            startActivity(intent);
        }
    }
}


    private String getFileExtension(Uri uri) {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(this.getContentResolver().getType(uri));

    }

    //END


    /*	Code	below	is	based	on	ImageCaptureExample by Cassendra4
     url: https://github.com/Cassendra4/ImageCaptureExample/blob/master/app/src/main/java/example/hasnagi/com/imagecaptureexample/ImageCaptureActivity.java
                     */
    private void selectImage(Context context) {

        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Attach Photo");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {
                    if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(SendMessageActivity.this,new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE
                        },1);

                    }

                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 1);//one can be replaced with any action code

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        imageView.setImageBitmap(selectedImage);
                        Log.d(TAG, "case 0 entered" + selectedImage);
                    }
                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        Log.d(TAG, "Uri selected image is " + selectedImage);
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();
                                //take this out if something breaks
                                filePath = data.getData();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                Log.d(TAG, "picture path is " + picturePath);

                                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

                                Log.d(TAG, "case 1 entered, image bitmap is " + BitmapFactory.decodeFile(picturePath));
                                cursor.close();
                            }
                        }


                    }
                    break;
            }
        }
    }
    //END

    //this method adapted from https://stackoverflow.com/questions/42571558/bitmapfactory-unable-to-decode-stream-java-io-filenotfoundexception
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){

            }
            else{
                Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_LONG).show();
            }
        }
    }

     /*	Code	below	is	based	on	ImageCaptureExample by Cassendra4
     url: https://github.com/Cassendra4/ImageCaptureExample/blob/master/app/src/main/java/example/hasnagi/com/imagecaptureexample/ImageCaptureActivity.java
                     */

    public void postImage() {
        boolean flag = true;

        Random rand = new Random();
        int rNum = 100 + rand.nextInt((999 - 100) + 1);
        String fin =  Integer.toString(rNum);

        FirebaseUser cursor = FirebaseAuth.getInstance().getCurrentUser();
        if (cursor != null) {
            String uid = cursor.getUid();

            db = FirebaseDatabase
                    .getInstance()
                    .getReference()
                    .child("Users")
                    .child(uid)
                    .child("Message")
                    .child(id)
                    .child("image");

            if (filePath != null) {

            db.setValue(filePath.toString());
            Log.d(TAG, "Adding image to firebase " + filePath);


            }
        Toast.makeText(getApplicationContext(), "Message Sent!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(SendMessageActivity.this, AdminHomeActivity2.class);
        startActivity(intent);
    }
    //END
}
        }
