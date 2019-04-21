package com.practice.loginwithgoogle;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

public class UserProfileActivity extends AppCompatActivity {

    private TextView tvName, tvId, tvEmail;
    private ImageView imgViewProfile;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        tvId = findViewById(R.id.tv_id);
        tvEmail = findViewById(R.id.tv_email);
        tvName = findViewById(R.id.tv_name);
        imgViewProfile = findViewById(R.id.iv_profile);



        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            tvName.setText("Display Name : " + account.getDisplayName() + "\nGiven Name : " + account.getGivenName() + "\nFamily Name: " + account.getFamilyName());
            tvId.setText("ID: " + account.getId() + "\nToken Id: " + account.getIdToken());
            tvEmail.setText("Email: " + account.getEmail());
            Picasso.with(this).load(account.getPhotoUrl()).
                    placeholder(R.drawable.google).
                    fit().
                    centerCrop().
                    into(imgViewProfile);
        }
    }

    public void signOutOnClick(View view) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    finish();
                    Toast.makeText(UserProfileActivity.this, "Signed Out Successfully...", Toast.LENGTH_LONG).show();
                }else
                {
                    Toast.makeText(UserProfileActivity.this, "Failed to Sign Out ...", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
