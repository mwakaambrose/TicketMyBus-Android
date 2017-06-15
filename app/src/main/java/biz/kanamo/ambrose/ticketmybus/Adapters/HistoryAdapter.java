package biz.kanamo.ambrose.ticketmybus.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import biz.kanamo.ambrose.ticketmybus.Models.HistoryModel;
import biz.kanamo.ambrose.ticketmybus.R;

/**
 * Created by ambrose on 15/06/17.
 */

public class HistoryAdapter extends BaseAdapter {

    private ArrayList<HistoryModel> historyModels;
    private Context ctx;

    public HistoryAdapter(ArrayList<HistoryModel> historyModels, Context ctx) {
        this.historyModels = historyModels;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return historyModels.size();
    }

    @Override
    public HistoryModel getItem(int position) {
        return historyModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rootView = LayoutInflater.from(ctx).inflate(R.layout.history, parent, false);

        TextView name = (TextView) rootView.findViewById(R.id.name);
        TextView departure_time = (TextView) rootView.findViewById(R.id.departure_time);
        TextView from_to = (TextView) rootView.findViewById(R.id.from_to);

        HistoryModel historyModel = getItem(position);

        name.setText(historyModel.getName());
        departure_time.setText(historyModel.getTime());
        from_to.setText(historyModel.getTo_from());

        return rootView;
    }
}
