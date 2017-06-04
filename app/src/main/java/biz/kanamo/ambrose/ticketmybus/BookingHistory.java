package biz.kanamo.ambrose.ticketmybus;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BookingHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);
        SharedPreferences sharedPreferences = getSharedPreferences("ticketmybus", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "no name");
    }
}
