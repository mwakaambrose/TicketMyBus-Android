package biz.kanamo.ambrose.ticketmybus;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import biz.kanamo.ambrose.ticketmybus.Adapters.SeatAdapter;
import biz.kanamo.ambrose.ticketmybus.Models.SeatModel;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ChooseSeat extends AppCompatActivity {

    GridView seats;
    ArrayList<SeatModel> seatModels = new ArrayList<>();
    Intent intentFrom;
    SeatModel seat;
    private int _seat;
    int[] bookedSeats;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_seat);
        intentFrom = getIntent();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Seats, wait.");

        seats = (GridView) findViewById(R.id.seats);

        bookedSeats = new int[65];

        OkHttpHandler okHttpHandler= new OkHttpHandler();
        okHttpHandler.execute();

        Intent intent = getIntent();
        getSupportActionBar().setTitle(intent.getStringExtra("number_plate"));
        seats.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                seatModels.get(position).setIsBooked(true);

                seat = seatModels.get(position);
                _seat = seat.getSeatNumber();

                /*View _view = parent.getChildAt(position);
                TextView seat_view = (TextView) _view.findViewById(R.id.seatx);
                if (seat_view != null){
                    seat_view.setBackgroundResource(R.drawable.seat_booked);
                }*/

                Snackbar.make(view, "Seat No: "+seat.getSeatNumber(), Snackbar.LENGTH_INDEFINITE)
                        .setAction("Continue", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (_seat == 0){
                                    Toast.makeText(ChooseSeat.this, "You can't sit on the aisle. Choose an actual seat.", Toast.LENGTH_LONG).show();
                                    return;
                                }else if (_seat == 14 || _seat == 15){

                                    Toast.makeText(ChooseSeat.this, "Seats are reserved for staffs", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                Intent intent = new Intent(getApplicationContext(), BookNow.class);
                                intent.putExtra("seat_no", _seat);
                                intent.putExtra("price", intentFrom.getStringExtra("price"));
                                intent.putExtra("to_from", intentFrom.getStringExtra("to_from"));
                                intent.putExtra("departure_time", intentFrom.getStringExtra("departure_time"));
                                startActivity(intent);
                            }
                        }).show();
            }
        });
    }

    private void loadSeats() {
        SeatModel space = new SeatModel(0 ,false, false);
        for (int i = 1; i < 65; i++ ){
            switch (i){
                case 3:
                    seatModels.add(space);
                    break;
                case 8:
                    seatModels.add(space);
                    break;
                case 11:
                    seatModels.add(space);
                    seatModels.add(space);
                    seatModels.add(space);
                    break;
                case 16:
                    seatModels.add(space);
                    break;
                case 21:
                    seatModels.add(space);
                    break;
                case 26:
                    seatModels.add(space);
                    break;
                case 31:
                    seatModels.add(space);
                    break;
                case 36:
                    seatModels.add(space);
                    break;
                case 41:
                    seatModels.add(space);
                    break;
                case 46:
                    seatModels.add(space);
                    break;
                case 51:
                    seatModels.add(space);
                    break;
                case 56:
                    seatModels.add(space);
                    break;
            }
            seatModels.add(new SeatModel(i, false, true));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.choose_seat_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if ( id == R.id.cancel_seat ) {
            Toast.makeText(this, "Choose another seat, auto cancel in the background even if the seat is still highlighted", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
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
            Request request = new Request.Builder()
                    .url("http://ticketmybus.herokuapp.com/api/v1/booking")
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
            // This is where the magic happens
            super.onPostExecute(s);

            try {

                if (s == null){
                    Toast.makeText(ChooseSeat.this, "No Internet. Please turn on Data", Toast.LENGTH_LONG).show();
                    return;
                }
                JSONArray jsonArray = new JSONArray(s);

                for (int i = 0; i < jsonArray.length(); i++){
                    bookedSeats[i] = jsonArray.getInt(i);
                }

                loadSeats();


                // Possible bug inside here.
                for (int i = 0; i < bookedSeats.length; i++){
                    for (int x = 1; x < 65; x++){
                        if (seatModels.get(x).getSeatNumber() == bookedSeats[i]){
                            seatModels.get(x).setIsBooked(true);
                            Log.d("XYT", bookedSeats[i]+"");
                        }
                    }
                }

                seatModels.get(18).setIsBooked(true);
                seatModels.get(19).setIsBooked(true);

                seats.setAdapter(new SeatAdapter(seatModels, getBaseContext()));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
