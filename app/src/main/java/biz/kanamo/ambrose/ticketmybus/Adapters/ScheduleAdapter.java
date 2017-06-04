package biz.kanamo.ambrose.ticketmybus.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import biz.kanamo.ambrose.ticketmybus.Models.ScheduleModel;
import biz.kanamo.ambrose.ticketmybus.R;

/**
 * Created by ambrose on 3/17/17.
 */

public class ScheduleAdapter extends BaseAdapter {
    private ArrayList<ScheduleModel> schedules;
    private Context ctx;

    public ScheduleAdapter(ArrayList<ScheduleModel> schedules, Context ctx) {
        this.schedules = schedules;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return schedules.size();
    }

    @Override
    public ScheduleModel getItem(int position) {
        return schedules.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rootView = LayoutInflater.from(ctx).inflate(R.layout.schedules, parent, false);

        TextView num_plate = (TextView) rootView.findViewById(R.id.num_plate);
        TextView departure_time = (TextView) rootView.findViewById(R.id.departure_time);
        TextView from_to = (TextView) rootView.findViewById(R.id.from_to);
        TextView total_passengers = (TextView) rootView.findViewById(R.id.total_passengers);
        TextView remaining_seats = (TextView) rootView.findViewById(R.id.remaining_seats);
        TextView price = (TextView) rootView.findViewById(R.id.price);

        ScheduleModel scheduleModel = getItem(position);

        num_plate.setText(scheduleModel.getNumPlate());
        departure_time.setText(scheduleModel.getDepartureTime());
        from_to.setText(scheduleModel.getFromTo());
        total_passengers.setText(scheduleModel.getTotalPassengers());
        remaining_seats.setText(scheduleModel.getRemainingSeats());
        price.setText(scheduleModel.getPrice());

        return rootView;
    }
}
