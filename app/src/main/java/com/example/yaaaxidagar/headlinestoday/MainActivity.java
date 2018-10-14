package com.example.yaaaxidagar.headlinestoday;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner;
    private Button button;
    private int selection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpSpinner();

        button=(Button)findViewById(R.id.proceed);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selection==1){
                    Intent intent1=new Intent(MainActivity.this,SportsFeeds.class);
                    startActivity(intent1);
                    spinner.setSelection(0);
                }

                else if(selection==2){
                    Intent intent2=new Intent(MainActivity.this,BusinessFeeds.class);
                    startActivity(intent2);
                    spinner.setSelection(0);
                }

                else if(selection==3){
                    Intent intent3=new Intent(MainActivity.this,GamingFeeds.class);
                    startActivity(intent3);
                    spinner.setSelection(0);
                }

                else if(selection==4){
                    Intent intent3=new Intent(MainActivity.this,GeneralFeeds.class);
                    startActivity(intent3);
                    spinner.setSelection(0);
                }

            }
        });


    }

    private void setUpSpinner() {
        spinner=(Spinner)findViewById(R.id.spinner);
        ArrayAdapter arrayAdapter=ArrayAdapter.createFromResource(this,R.array.spinner,R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String value=(String) adapterView.getItemAtPosition(i);

                switch (value){
                    case "Sports":
                        selection=1;
                        break;

                    case "Business":
                        selection=2;
                        break;

                    case "Gaming":
                        selection=3;
                        break;

                    case "General":
                        selection=4;
                        break;

                    default:
                        selection=0;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selection=0;
            }
        });
    }
}
