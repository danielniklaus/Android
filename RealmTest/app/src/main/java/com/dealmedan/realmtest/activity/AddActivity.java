package com.dealmedan.realmtest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dealmedan.realmtest.R;
import com.dealmedan.realmtest.realm.RealmHelper;

/**
 * Created by daniel on 1/25/2017.
 */
public class AddActivity extends AppCompatActivity {
    private RealmHelper realmHelper;
    private EditText inputDescription, inputTitle;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambahan);

        realmHelper = new RealmHelper(AddActivity.this);

        inputTitle = (EditText)findViewById(R.id.et_input_title);
        inputDescription = (EditText) findViewById(R.id.et_input_description);
        save = (Button) findViewById(R.id.btn_save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title, description;

                //mengambil text dari edittext
                title = inputTitle.getText().toString();
                description = inputDescription.getText().toString();

                //menambahkan data pada database
                realmHelper.addArticle(title,description);

                //menutup Add Activity
                finish();

                //kembali ke mainactivit
                Intent i = new Intent(AddActivity.this, MainActivity.class);
                startActivity(i);
            }
        });


    }
}
