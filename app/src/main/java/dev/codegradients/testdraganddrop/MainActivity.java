package dev.codegradients.testdraganddrop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {




    // Declaring Views
    // Track Ball And Others
    private View Track_Bal;
    private float dX, dY;
    private RelativeLayout  parent;
    private View verticalLine, horizontalLine;
    private DisplayMetrics displayMetrics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing Views Attaching Object With views
        Track_Bal = findViewById(R.id.text);
        parent = findViewById(R.id.parent_layout);
        horizontalLine = findViewById(R.id.horizontal_line);
        verticalLine = findViewById(R.id.vertical_line);
        final TextView xCoord =findViewById(R.id.textView1);
        final TextView yCoord =findViewById(R.id.textView2);






        // Setting Display Metrics
        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);




        // Controlling On Touch Of Ball

        Track_Bal.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int action = event.getAction();
                switch (action & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN: {

                        // Getting X And Y On Touch
                        dX = v.getX() - event.getRawX();
                        dY = v.getY() - event.getRawY();


                        break;
                    }

                    case MotionEvent.ACTION_MOVE: {

                        // Moving Ball According to Position

                        Track_Bal.animate()
                                .x(event.getRawX() + dX)
                                .y(event.getRawY() + dY)
                                .setDuration(0)
                                .start();
                        horizontalLine.animate()
                                .y(event.getRawY() + dY + (Track_Bal.getHeight() / 2))
                                .setDuration(0)
                                .start();
                        verticalLine.animate()
                                .x(event.getRawX() + dX + (Track_Bal.getWidth() / 2))
                                .setDuration(0)
                                .start();


                        // Reading Values From The Track Ball
                        float xCord = ((Track_Bal.getX() + (Track_Bal.getWidth() / 2))) - (parent.getWidth() / 2);
                        float yCord = ((Track_Bal.getY() + (Track_Bal.getHeight() / 2))) - (parent.getHeight() / 2);


                        // Reading Values Printing Values On Views
                        if (Track_Bal.getX() > 0 && Track_Bal.getY() > 0) {
                            if (Track_Bal.getX() < parent.getWidth() && Track_Bal.getY() < parent.getHeight())
                                xCoord.setText(String.valueOf(MapX(xCord)));
                            yCoord.setText(String.valueOf(MapY(yCord)));
                        }




                        // Putting Restriction to not to cross the border
                        if (Track_Bal.getX() < 0) {
                            Track_Bal.animate()
                                    .x(0)
                                    .y(event.getRawY() + dY)
                                    .setDuration(0)
                                    .start();
                            verticalLine.animate()
                                    .x(Track_Bal.getWidth() / 2)
                                    .setDuration(0)
                                    .start();
                        }
                        if (Track_Bal.getY() < 0) {
                            Track_Bal.animate()
                                    .x(event.getRawX() + dX)
                                    .y(0)
                                    .setDuration(0)
                                    .start();
                            horizontalLine.animate()
                                    .y(Track_Bal.getHeight() / 2)
                                    .setDuration(0)
                                    .start();
                        }

                        if (Track_Bal.getX() + Track_Bal.getWidth() > parent.getWidth()) {
                            Track_Bal.animate()
                                    .x(parent.getWidth() - Track_Bal.getWidth())
                                    .y(event.getRawY() + dY)
                                    .setDuration(0)
                                    .start();

                            verticalLine.animate()
                                    .x(parent.getWidth() - (Track_Bal.getWidth() / 2))
                                    .setDuration(0)
                                    .start();
                        }
                        if (Track_Bal.getY() + Track_Bal.getHeight() > parent.getHeight()) {
                            Track_Bal.animate()
                                    .x(event.getRawX() + dX)
                                    .y(parent.getHeight() - Track_Bal.getHeight())
                                    .setDuration(0)
                                    .start();

                            horizontalLine.animate()
                                    .y(parent.getHeight() - (Track_Bal.getHeight() / 2))
                                    .setDuration(0)
                                    .start();
                        }
                        break;
                    }
                }
                return true;
            }

        });

    }



    // Mapping Value Between 0 - 1
    private double MapX(float x) {
        double top_head = (1 - 0);
        double bottm_head = ((380.99805) - (-320.86313));
        double division = top_head / bottm_head;
        double right_head = (x - (-320.86313));
        return right_head * division + 0;
    }


    private double MapY(float y) {
        double a = 356.1836;
        double b = -324.00098;
        double c = 0;
        double d = 1;
        double top_head = (d - c);
        double bottm_head = ((b) - (a));
        double division = top_head / bottm_head;
        double right_head = (y - (a));
        return right_head * division + c;
    }


}
