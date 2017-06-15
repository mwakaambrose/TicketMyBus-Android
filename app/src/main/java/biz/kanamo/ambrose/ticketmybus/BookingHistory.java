package biz.kanamo.ambrose.ticketmybus;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.digits.sdk.android.DigitsSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import biz.kanamo.ambrose.ticketmybus.Adapters.HistoryAdapter;
import biz.kanamo.ambrose.ticketmybus.Models.HistoryModel;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.digits.sdk.android.Digits.getActiveSession;

public class BookingHistory extends AppCompatActivity {

    ListView listView;
    HistoryAdapter historyAdapter;
    String username;
    DigitsSession digitSession;
    ArrayList<HistoryModel> historyModels = new ArrayList<>();
    private ProgressDialog progressDialog;
    private ProgressDialog getProgressDialog;
    private String bookingId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);
        getProgressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cancelling Bookings ...");
        getProgressDialog.setMessage("Getting Bookings History...");

        digitSession = getActiveSession();

        setContentView(R.layout.activity_booking_history);
        SharedPreferences sharedPreferences = getSharedPreferences("ticketmybus", MODE_PRIVATE);
        username = sharedPreferences.getString("username", "no name");
        listView = (ListView) findViewById(R.id.book_history_list);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bookingId = historyModels.get(position).getId();
                new CancelBooking().execute();
            }
        });

        new GetBooking().execute();
    }

    public void updateHistoryModels(String rawJson) {
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(rawJson);
            if (jsonArray.length() == 0) {
                Toast.makeText(BookingHistory.this, "No Booking history for you.", Toast.LENGTH_SHORT).show();
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                historyModels.add(new HistoryModel(jsonObject.getString("id"),
                        jsonObject.getString("to_from"),
                        jsonObject.getString("departure_time"),
                        jsonObject.getString("name"))
                );
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        historyAdapter = new HistoryAdapter(historyModels, this);
        listView.setAdapter(historyAdapter);
        Log.d("XYT", historyModels.toString());
    }

    public class CancelBooking extends AsyncTask<String, String, String> {

        OkHttpClient client = new OkHttpClient();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            RequestBody requestBody = new FormBody.Builder()
                    .add("id", bookingId)
                    .add("phone_number", digitSession.getPhoneNumber())
                    .build();
            Request request = new Request.Builder()
                    .url("http://ticketmybus.herokuapp.com/api/v1/cancel/" + bookingId)
                    .method("DELETE", requestBody)
                    .build();
            Response response;
            try {
                response = client.newCall(request).execute();
                return response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            Toast.makeText(BookingHistory.this, s, Toast.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(), Home.class));
            Log.d("XYT", s);

        }
    }

    public class GetBooking extends AsyncTask<String, String, String> {

        OkHttpClient client = new OkHttpClient();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            getProgressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            Request request = new Request.Builder()
                    .url("http://ticketmybus.herokuapp.com/api/v1/cancel?phone_number=" + digitSession.getPhoneNumber())
                    .build();
            Response response;
            try {
                response = client.newCall(request).execute();
                return response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            getProgressDialog.dismiss();
            updateHistoryModels(s);
            Log.d("XYT", s);

        }
    }
}
