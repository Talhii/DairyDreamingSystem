package com.example.dairyservice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class Upload_animal_buffalo extends AppCompatActivity {

    private EditText animal_buffalo_age_et,animal_buffalo_price_et;
    private ImageButton animal_image;
    private Button upload_animal_buffalo_data_btn;
    public Uri image_uri;

    private SharedPreferences prefs;

    String Storage_Path = "ImageData/";
    String Database_Path = "ImageData";

    StorageReference storageReference;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_animal_buffalo);

        upload_animal_buffalo_data_btn = findViewById(R.id.upload_animal_buffalo_data_btn);
        animal_image = findViewById(R.id.animal_buffalo_image);
        animal_buffalo_age_et = findViewById(R.id.animal_buffalo_age_et);
        animal_buffalo_price_et = findViewById(R.id.animal_buffalo_price_et);


        storageReference = FirebaseStorage.getInstance().getReference();
        // Assign FirebaseDatabase instance with root database name.
        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);

        prefs = getSharedPreferences("EmailPassing", MODE_PRIVATE);

        animal_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });


        upload_animal_buffalo_data_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadData();
            }
        });
    }

    private void selectImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(Upload_animal_buffalo.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,5);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 6);
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 5 && resultCode == RESULT_OK){

            image_uri = data.getData();
            Bitmap photo = (Bitmap)data.getExtras().get("data");

            image_uri = getImageUri(getApplicationContext(), photo);
            animal_image.setImageURI(image_uri);

        }

        else if(requestCode == 6 && resultCode == RESULT_OK){

            image_uri = data.getData();
            animal_image.setImageURI(image_uri);

        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Buffalo", null);
        return Uri.parse(path);
    }

    // Creating Method to get the selected image file Extension from File Path URI.
    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }

    private void uploadData() {


        final String farm_owner_email = prefs.getString("farm_owner_email", "No Mail defined");
        final String animal_buffalo_age = animal_buffalo_age_et.getText().toString();
        final String animal_buffalo_price = animal_buffalo_price_et.getText().toString();


        if(image_uri != null) {
            StorageReference storageReference2nd = storageReference.child(Storage_Path + System.currentTimeMillis() + "." + GetFileExtension(image_uri));

            if(TextUtils.isEmpty(animal_buffalo_age)){
            animal_buffalo_age_et.setError("Age cannot be empty");
            }

            else if(TextUtils.isEmpty(animal_buffalo_price)){
            animal_buffalo_price_et.setError("Price cannot be empty");

            }
            else{
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Uploading Image...");
                progressDialog.show();
                storageReference2nd.putFile(image_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();

                        Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                        task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String photoLink = uri.toString();
                                // Getting image upload ID.

                                UploadImageInfo imageUploadInfo = new UploadImageInfo(animal_buffalo_price,animal_buffalo_age,farm_owner_email,"buffalo", photoLink);
                                String ImageUploadId = databaseReference.push().getKey();

                                // Adding image upload id s child element into databaseReference.

                                databaseReference.child(ImageUploadId).setValue(imageUploadInfo);
                                Toast.makeText(Upload_animal_buffalo.this, "Buffalo Uploaded Successfully", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }

                }) .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(Upload_animal_buffalo.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progress_percentage = (100.00 *  snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        progressDialog.setMessage("Uploaded Percentage: "+ (int)progress_percentage );
                    }

                });
            }
        }
        else{
            Toast.makeText(this, "Image of Buffalo must be uploaded", Toast.LENGTH_SHORT).show();
        }
    }
}