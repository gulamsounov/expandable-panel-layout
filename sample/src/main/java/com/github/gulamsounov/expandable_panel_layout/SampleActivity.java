package com.github.gulamsounov.expandable_panel_layout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.gulamsounov.expandablepanellayout.ExpandablePanelLayout;
import com.github.gulamsounov.expandablepanellayout.OnChangeState;
import com.github.gulamsounov.expandablepanellayout.State;

public class SampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_activity);

        final ExpandablePanelLayout expandablePanelLayout = findViewById(R.id.content);
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandablePanelLayout.toggle();
            }
        });
        expandablePanelLayout.setOnChangeState(new OnChangeState() {
            @Override
            public void onChange(State state) {
                state.toString();
            }
        });
    }
}
