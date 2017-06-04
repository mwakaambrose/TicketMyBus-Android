package biz.kanamo.ambrose.ticketmybus;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.digits.sdk.android.DigitsSession;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import biz.kanamo.ambrose.ticketmybus.Adapters.SeatAdapter;
import biz.kanamo.ambrose.ticketmybus.Models.SeatModel;
import ng.max.slideview.SlideView;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.digits.sdk.android.Digits.getActiveSession;

public class BookNow extends AppCompatActivity {

    Intent intent;
    TextView price, seatNo, from_to, phone, username;
    DigitsSession digitSession;
    String user_name;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_now);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        digitSession = getActiveSession();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Requesting seat, wait.");

        intent = getIntent();
        seatNo = (TextView) findViewById(R.id.seatNo);
        price = (TextView) findViewById(R.id.price);
        from_to = (TextView) findViewById(R.id.from_to);
        phone = (TextView) findViewById(R.id.phone);
        username = (TextView) findViewById(R.id.name);

        SharedPreferences sharedPreferences = getSharedPreferences("ticketmybus", MODE_PRIVATE);
        user_name = sharedPreferences.getString("username", "no name");
        username.setText(user_name);

        seatNo.setText("Seat No: "+intent.getIntExtra("seat_no", 0));
        price.setText(intent.getStringExtra("price"));
        from_to.setText(intent.getStringExtra("to_from") +"at "+ intent.getStringExtra("departure_time"));
        phone.setText(digitSession.getPhoneNumber());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ((SlideView) findViewById(R.id.slideToConfirm)).setOnSlideCompleteListener(new SlideView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(SlideView slideView) {
                // vibrate the device
                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(100);
                // TODO: 3/23/17  send the user info to the server and redirect to the home page
                OkHttpHandler okHttpHandler= new OkHttpHandler();
                okHttpHandler.execute();
            }
        });
    }

    public class OkHttpHandler extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        OkHttpClient client = new OkHttpClient();
        @Override
        protected String doInBackground(String... params) {
            RequestBody requestBody = new FormBody.Builder()
                    .add("phone_number", digitSession.getPhoneNumber())
                    .add("to_from", intent.getStringExtra("to_from"))
                    .add("price", intent.getStringExtra("price"))
                    .add("departure_time", intent.getStringExtra("departure_time"))
                    .add("is_approved", String.valueOf(1))
                    .add("name", user_name)
                    .add("seat_number", String.valueOf(intent.getIntExtra("seat_no", 0)))
                    .build();
            Request request = new Request.Builder()
                    .url("http://ticketmybus.herokuapp.com/api/v1/booking")
                    .post(requestBody)
                    .build();
            Response response ;
            try {
                response = client.newCall(request).execute();
                return response.body().string();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            super.onPostExecute(s);


            if (s == null){
                Toast.makeText(BookNow.this, "No Internet. Please turn on Data", Toast.LENGTH_LONG).show();
                Log.d("XYT", "No response received");
                return;
            }

            Log.d("XYT", s);

            Toast.makeText(BookNow.this, "Booking Request sent successfully.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), BookingHistory.class));

        }
    }
}
