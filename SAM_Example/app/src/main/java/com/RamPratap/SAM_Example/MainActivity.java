package com.RamPratap.SAM_Example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.RamPratap.SAM_Lib.uC_Device;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    uC_Device con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        con = new uC_Device(this);

        final Switch s = findViewById(R.id.switch_onoff);
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TextView myAwesomeTextView = findViewById(R.id.textView);
                if (isChecked) {
                    int a= con.Connect();
                    myAwesomeTextView.setText(Integer.toString(a));
                    if(a>0){
                        s.setText(getString(R.string.button4_text));
                    }else{
                        s.setChecked(false);
                    }
                } else {
                    int a= con.Disconnect();
                    myAwesomeTextView.setText(Integer.toString(a));
                    if(a>0){
                        s.setText(getString(R.string.button3_text));
                    }else{
                        s.setChecked(true);
                    }
                }
            }
        });

        con.Initialize();
    }

    public void config(View view) {
        con.CP13= uC_Device.Output;
        con.CP12= uC_Device.Input;
        con.CP8= uC_Device.Input;
        con.CP19=uC_Device.Input;
        con.CP20=uC_Device.Input;
        con.CP21=uC_Device.Input;
        con.CP22=uC_Device.Input;
        con.CP23=uC_Device.Input;
        con.CP24=uC_Device.Input;
        con.CP26=uC_Device.Input;
        con.CP25=uC_Device.Input;
        con.SP13=uC_Device.LOW;
        int i = con.Configure();
        TextView myAwesomeTextView = findViewById(R.id.textView);
        myAwesomeTextView.setText(Integer.toString(i));
    }

    public void send1(View view) {
        if(con.SP13==uC_Device.LOW){
            con.SP13 = uC_Device.HIGH;
        }else if(con.SP13==uC_Device.HIGH){
            con.SP13 = uC_Device.LOW;
        }
        int in = con.Send_Data();
        TextView myAwesomeTextView = findViewById(R.id.textView);
        myAwesomeTextView.setText(Integer.toString(in));
    }

    public void send0(View view) {
        int j=con.Read_Data();
        TextView myAwesomeTextView = findViewById(R.id.textView);
        int[] i={con.RP0,con.RP1,con.RP2,con.RP3,con.RP4,con.RP5,con.RP6,con.RP7,con.RP8,con.RP9,con.RP10,con.RP11,con.RP12,con.RP13,con.RP14,con.RP15,con.RP16,con.RP17,con.RP18,con.RP19,con.RP20,con.RP21};
        myAwesomeTextView.setText(j+Arrays.toString(i));
    }
}