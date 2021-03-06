package com.highradius.partha.howlround;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Register extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseuserinfo;


    int indexoffirst;
    String key;
    Long level;
    String url="https://firebasestorage.googleapis.com/v0/b/howlround-7092.appspot.com/o/newUser.png?alt=media&token=c8c3ef5b-6a9f-4433-87cb-930be593dfe7";
    EditText etName, etEmail, etPassword, etConfirmPassword; Spinner spinner,spinnerDepartment;
    ImageView ivCamera;
    Button Register;
    private static final int REQUEST_CODE = 1;
    ProgressBar progressBar;
    String name,email,password,confirmpassword,designation,department;
    DatabaseReference userdata;
    Long Userlevel;

    HashMap<String,Integer> hashMap=new HashMap<String, Integer>(){{

        put("Senior Manager",1);
        put("Manager",2);
        put("Employee",3);
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth=FirebaseAuth.getInstance();
        Register=findViewById(R.id.btn_register);
        etName = findViewById(R.id.et_user_name);
        etEmail = findViewById(R.id.et_user_email);
        etPassword = findViewById(R.id.et_user_password);
        etConfirmPassword = findViewById(R.id.et_user_confirm_password);

        spinner=findViewById(R.id.et_user_designation);
        spinner.setSelection(0);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.designation, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        spinnerDepartment=findViewById(R.id.et_user_department);
        spinnerDepartment.setSelection(0);
        ArrayAdapter<CharSequence> adapterDepartment=ArrayAdapter.createFromResource(this,R.array.department,android.R.layout.simple_spinner_item);
        spinnerDepartment.setAdapter(adapterDepartment);
        spinnerDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                department=parent.getItemAtPosition(position).toString();
                dispay(department);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        initProgressBar();
        createNewUser();

    }
    public  void createNewUser()
    {
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                name=etName.getText().toString();
                email=etEmail.getText().toString();
                indexoffirst=email.indexOf('@');
                key=email.substring(0,indexoffirst);
                sendMessage.message=name;
                password=etPassword.getText().toString();
                confirmpassword=etConfirmPassword.getText().toString();

                showProgressBar();
                if (isStringNull(name)||isStringNull(email)||isStringNull(password)||isStringNull(confirmpassword))
                {
                    Toast.makeText(Register.this,"Fill in all the fields",Toast.LENGTH_LONG).show();
                    hideProgressBar();
                }
                else
                if (!isStringEqual(password,confirmpassword))
                {
                    Toast.makeText(Register.this,"Passwords do not match",Toast.LENGTH_LONG).show();
                    hideProgressBar();
                    etConfirmPassword.setText("");
                    etPassword.setText("");
                }
                else
                {
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            showProgressBar();
                            if (task.isSuccessful())
                            {
                                registerUser();
                                hideProgressBar();
                                sendVerificationEmail();
                                FirebaseAuth.getInstance().signOut();
                                redirectLoginScreen();
                                Toast.makeText(Register.this,"Verification link has been sent to registered Email",Toast.LENGTH_LONG).show();
                                hideProgressBar();
                            }
                            else
                            {
                                Toast.makeText(Register.this,"Email already registered",Toast.LENGTH_LONG).show();
                                hideProgressBar();
                            }
                        }
                        private void registerUser() {

                            Userlevel=findLevel(designation);
                            mDatabase = FirebaseDatabase.getInstance().getReference("registeredemployees");
                            RegisteredEmployeesData registeredEmployeesData=new RegisteredEmployeesData(department,designation,email,url,Userlevel,name);
                            mDatabase.child(""+key).setValue(registeredEmployeesData);

                            mDatabaseuserinfo=FirebaseDatabase.getInstance().getReference("userinfo").child(department);
                            EmployessCards newUser=new EmployessCards(department,designation,email,url,Userlevel,name,0L,0L);
                            mDatabaseuserinfo.child(key).setValue(newUser);
                        }

                        private long findLevel(String designation) {
                            if(designation.equals("Senior Manager")){
                                level=1L;
                            }
                            if(designation.equals("Manager")){
                                level=2L;

                            }
                            if(designation.equals("Employee")){
                                level=3L;
                            }
                            return level;
                        }
                    });
                }
            }
        });

    }
    public void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {

                            }
                            else{
                                Toast.makeText(Register.this, "couldn't send email", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }

    }
    public boolean isStringEqual(String password,String Confirmpassword)
    {
        if(password.equals(Confirmpassword))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public boolean isStringNull(String string)
    {
        if(string.equals(""))
            return true;
        else
            return false;
    }
    private void initProgressBar(){
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
    }
    private void showProgressBar(){
        progressBar.setVisibility(View.VISIBLE);

    }

    private void hideProgressBar(){
        if(progressBar.getVisibility() == View.VISIBLE){
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
    private void redirectLoginScreen(){

        Intent intent = new Intent(Register.this, Login.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        designation=parent.getItemAtPosition(position).toString();
        dispay(designation);
    }

    private void dispay(String designation) {
        Toast.makeText(getApplicationContext(),designation,Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}