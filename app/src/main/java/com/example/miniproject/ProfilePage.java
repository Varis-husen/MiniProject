package com.example.miniproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfilePage extends Fragment {

    FirebaseAuth firebaseAuth;
    FirebaseFirestore fireStore;
    String currentUserUID;
    TextView tv_email,tv_name,tv_location, tv_dob, tv_gender;

    public ProfilePage() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fireStore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        currentUserUID = firebaseAuth.getUid();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv_dob = view.findViewById(R.id.tv_dob);
        tv_email = view.findViewById(R.id.tv_email);
        tv_gender = view.findViewById(R.id.tv_gender);
        tv_location = view.findViewById(R.id.tv_location);
        tv_name = view.findViewById(R.id.tv_name);

        readFireStore();
    }

    public void readFireStore()
    {
        DocumentReference docRef = fireStore.collection("User").document(currentUserUID);

        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful())
            {
                DocumentSnapshot doc = task.getResult();

                if (doc.exists())
                {
                    Log.d("profileFragment",doc.getData().toString());
                    tv_name.setText("Name : "+doc.get("Name"));
                    tv_location.setText("City : "+doc.get("Location"));
                    tv_gender.setText("Gender : "+doc.get("gender"));
                    tv_email.setText("Email : "+doc.get("Email"));
                    tv_dob.setText("D.O.B : "+doc.get("dob"));
                }
            }
        });
    }
}
