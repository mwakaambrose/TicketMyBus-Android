package biz.kanamo.ambrose.ticketmybus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsSession;

import java.util.ArrayList;

import biz.kanamo.ambrose.ticketmybus.Adapters.ScheduleAdapter;
import biz.kanamo.ambrose.ticketmybus.Models.ScheduleModel;

import static com.digits.sdk.android.Digits.getActiveSession;

public class Home extends AppCompatActivity {


    // Firebase instance variables
//    private FirebaseAuth mFirebaseAuth;
//    private FirebaseUser mFirebaseUser;
    public static final String ANONYMOUS = "anonymous";
    DigitsSession digitSession;
    ListView schedulesView;
    ScheduleAdapter scheduleAdapter;
    ArrayList<ScheduleModel> scheduleModels = new ArrayList<>();
    private String mUsername;
    private String mPhotoUrl;
    private TextView mTextMessage;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    schedulesView.setAdapter(scheduleAdapter);
                    return true;
                case R.id.navigation_dashboard:
                    startActivity(new Intent(getApplicationContext(), Terminals.class));
                    return true;
                case R.id.navigation_notifications:
                    startActivity(new Intent(getApplicationContext(), BookingHistory.class));
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        digitSession = getActiveSession();
        // + digitSession.getPhoneNumber()

        SharedPreferences sharedPreferences = getSharedPreferences("ticketmybus", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "no name");

        getSupportActionBar().setSubtitle("Welcome "+username );
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        schedulesView = (ListView) findViewById(R.id.schedules_view);

        ScheduleModel one = new ScheduleModel(
                "UAE 890D",
                "8.00am",
                "Kitgum to Kampala",
                "64 passengers",
                "",
                "20, 000 Ugx"
        );

        ScheduleModel five = new ScheduleModel(
                "UGT 899D",
                "12.00pm",
                "Kitgum to Kampala",
                "64 passengers",
                "",
                "20, 000 Ugx"
        );

        ScheduleModel two = new ScheduleModel(
                "UEE 909D",
                "4.00pm",
                "Kitgum to Kampala",
                "64 passengers",
                "",
                "20, 000 Ugx"
        );

        ScheduleModel three = new ScheduleModel(
                "UDE 898G",
                "6.00pm",
                "Gulu to Kampala",
                "64 passengers",
                "",
                "20, 000 Ugx"
        );

        ScheduleModel four = new ScheduleModel(
                "UAB 460D",
                "8.00am",
                "Kampala to Kitgum",
                "60 passengers",
                "",
                "25, 000 Ugx"
        );

        ScheduleModel six = new ScheduleModel(
                "UAB 460D",
                "6.00am",
                "Kampala - Gulu - Kitgum",
                "60 passengers",
                "",
                "25, 000 Ugx"
        );

        ScheduleModel seven = new ScheduleModel(
                "UAB 460D",
                "11.00pm",
                "Kampala - Gulu - Kitgum",
                "60 passengers",
                "",
                "25, 000 Ugx"
        );

        scheduleModels.add(one);
        scheduleModels.add(two);
        scheduleModels.add(three);
        scheduleModels.add(four);
        scheduleModels.add(one);
        scheduleModels.add(five);
        scheduleModels.add(six);
        scheduleModels.add(seven);


        scheduleAdapter = new ScheduleAdapter( scheduleModels, getApplicationContext());
        schedulesView.setAdapter(scheduleAdapter);
        schedulesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ScheduleModel item = scheduleModels.get(position);
                Intent startDetailActivity = new Intent(getApplicationContext(), ChooseSeat.class);
                startDetailActivity.putExtra("number_plate", item.getNumPlate());
                startDetailActivity.putExtra("departure_time", item.getDepartureTime());
                startDetailActivity.putExtra("price", item.getPrice());
                startDetailActivity.putExtra("to_from", item.getFromTo());
                startDetailActivity.putExtra("remaining_seat", item.getRemainingSeats());
                startActivity(startDetailActivity);
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if ( id == R.id.help ) {
            startActivity(new Intent(this, Help.class));
        } else if ( id == R.id.about ) {
            startActivity(new Intent(this, Help.class));
        }else if (id == R.id.logout){
            Digits.logout();
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
