package com.gadhvi.newsfeeds;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URL;

public class AddNews extends AppCompatActivity {
    private Button add;
    private ImageView i1;
    private   EditText e1,e2;
    private Uri imageUri = null;
    private static int GALLERY_REQUEST =1;
    private StorageReference storeimage;
    private DatabaseReference database;
    private ProgressDialog loading;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);

        storeimage = FirebaseStorage.getInstance().getReference();
        database = FirebaseDatabase.getInstance().getReference().child("News-feeds");

        add =(Button)findViewById(R.id.addcard);
        i1=(ImageView)findViewById(R.id.addimage);
        e1=(EditText)findViewById(R.id.title);
        e2 =(EditText)findViewById(R.id.description);
        loading = new ProgressDialog(this);


        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getimage = new Intent(Intent.ACTION_GET_CONTENT);
                getimage.setType("image/*");
                startActivityForResult(getimage,GALLERY_REQUEST);
            }
        });



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String title =e1.getText().toString().trim();
                final String discription =e2.getText().toString().trim();
                if(!TextUtils.isEmpty(title)  && !TextUtils.isEmpty(discription) && imageUri!=null)
                {
                    loading.setMessage("Loading Please Wait....");
                    loading.show();
                    StorageReference imagepath = storeimage.child("Newsfeed Images").child(imageUri.getLastPathSegment());
                    imagepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Uri dowmloadUrl = taskSnapshot.getDownloadUrl();
                            DatabaseReference upload =database.push();
                            upload.child("Title").setValue(title);
                            upload.child("Description").setValue(discription);
                            upload.child("Image").setValue(dowmloadUrl.toString());

                            loading.dismiss();
                            Intent gohome = new Intent(AddNews.this, MainActivity.class);
                            startActivity(gohome);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                            loading.dismiss();
                            Toast.makeText(AddNews.this,"Please Try Again Later Something Went Wrong",Toast.LENGTH_LONG).show();
                        }
                    });
                }

                else
                {
                    Toast.makeText(AddNews.this,"Please Fill All the field",Toast.LENGTH_LONG).show();
                }

            }
        });


    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GALLERY_REQUEST &&resultCode==RESULT_OK){

           imageUri =data.getData();
            i1.setImageURI(imageUri);

        }
    }
}
