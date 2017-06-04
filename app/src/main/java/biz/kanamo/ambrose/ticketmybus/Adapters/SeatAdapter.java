package biz.kanamo.ambrose.ticketmybus.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.ArrayList;

import biz.kanamo.ambrose.ticketmybus.Models.SeatModel;
import biz.kanamo.ambrose.ticketmybus.R;

/**
 * Created by ambrose on 3/20/17.
 */

public class SeatAdapter extends BaseAdapter {

    private ArrayList<SeatModel> seatModels;
    private Context ctx;
    private LayoutInflater inflater;

    public SeatAdapter(ArrayList<SeatModel> seatModels, Context ctx) {
        this.seatModels = seatModels;
        this.ctx = ctx;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return seatModels.size();
    }

    @Override
    public SeatModel getItem(int position) {
        return seatModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return seatModels.get(position).isSeat() ? 1 : 0;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null)
        {
            viewHolder = new ViewHolder();


            if(getItemViewType(position) == 1)
            {
                convertView = inflater.inflate(R.layout.seat, null);
                viewHolder.seatNum = (TextView)convertView.findViewById(R.id.seatx);
            }
            else if(getItemViewType(position) == 0)
            {
                convertView = inflater.inflate(R.layout.aisle, null);
                viewHolder.aisle = (TextView)convertView.findViewById(R.id.aisle);
            }
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        if(getItemViewType(position) == 1)
        {
            viewHolder.seatNum.setText(seatModels.get(position).getSeatNumber()+"");
            if (seatModels.get(position).getIsBooked()){
                viewHolder.seatNum.setBackgroundResource(R.drawable.seat_booked);
            }
        } else if(getItemViewType(position) == 0)
        {
            viewHolder.aisle.setText("");
        }

        return convertView;
    }

    public class ViewHolder
    {
        TextView seatNum;
        TextView aisle;
    }
}
