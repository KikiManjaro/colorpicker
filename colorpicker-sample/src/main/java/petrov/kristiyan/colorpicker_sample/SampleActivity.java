package petrov.kristiyan.colorpicker_sample;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import petrov.kristiyan.colorpicker.ColorPicker;
import petrov.kristiyan.colorpicker.DoubleColorPicker;


public class SampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        final FloatingActionButton fab = findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DoubleColorPicker colorPicker = new DoubleColorPicker(SampleActivity.this);
                    ArrayList<String> colors = new ArrayList<>();
                    colors.add("#82B926");
                    colors.add("#a276eb");
                    colors.add("#6a3ab2");
                    colors.add("#666666");
                    colors.add("#FFFF00");
                    colors.add("#3C8D2F");
                    colors.add("#FA9F00");
                    colors.add("#FF0000");

                    ((DoubleColorPicker) (colorPicker)
                            .setTitle("First")
                            .setDefaultColorButton(Color.parseColor("#f84c44"))
                            .setColors(colors)
                            .setColumns(5)
                            .setRoundColorButton(true)
                            .addListenerButton("newButton", new ColorPicker.OnButtonListener() {
                                @Override
                                public void onClick(View v, int position, int color) {
                                    Log.d("position", "" + position);
                                }
                            }))
                            .setDefaultDoubleColorButton(Color.parseColor("#f84c44"))
                            .setSecondTitle("Second")
                            .setOnChooseDoubleColorListener(new DoubleColorPicker.OnChooseDoubleColorListener() {
                                @Override
                                public void onChooseColor(int position, int color, int position2, int color2) {
                                    System.out.printf("Clicked");
                                    Log.d("position", "" + position);// will be fired only when OK button was tapped
                                    Log.d("position2", "" + position2);// will be fired only when OK button was tapped
                                }

                                @Override
                                public void onCancel() {
                                }
                            })
                            .show();
                }
            });
        }

    }
}
