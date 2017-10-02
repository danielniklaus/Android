package com.dealmedan.realmtest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dealmedan.realmtest.R;
import com.dealmedan.realmtest.realm.RealmHelper;
import com.dealmedan.realmtest.model.ArticleModel;

import java.util.ArrayList;

/**
 * Created by daniel on 1/25/2017.
 */
public class EditActivity extends AppCompatActivity{
    private int position;
    private Button delete, save;
    private RealmHelper helper;
    private EditText inputTitle, inputDescription;
    private String title, description;
    private String intentTitle, intentDescription;
    private ArrayList<ArticleModel> data;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambahan);

        helper = new RealmHelper(EditActivity.this);
        data = new ArrayList<>();
        position = getIntent().getIntExtra("id",0);
        intentTitle = getIntent().getStringExtra("title");
        intentDescription = getIntent().getStringExtra("description");

        delete = (Button) findViewById(R.id.btn_delete);
        save = (Button) findViewById(R.id.btn_save);

        inputTitle = (EditText) findViewById(R.id.et_input_title);
        inputDescription = (EditText) findViewById(R.id.et_input_description);

        inputTitle.setText(intentTitle);
        inputDescription.setText(intentDescription);

        delete.setVisibility(View.VISIBLE);

        //untk menghapus
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //menghapus dari database
                helper.deleteData(position);
                //berpindah ke MainActivity
                startActivity(new Intent(EditActivity.this,MainActivity.class));
                finish();
            }
        });

        //untuk meng update data
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mengambil text dari edit text
                title = inputTitle.getText().toString();
                description = inputDescription.getText().toString();

                //melakukan update data
                helper.updateArticle(position, title, description);

                //berpidah ke MainActivity
                startActivity(new Intent(EditActivity.this, MainActivity.class));
                finish();
            }
        });
    }


}
