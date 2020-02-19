package com.tech.cloudnausor.ohmytennis.calenderactivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.tech.cloudnausor.ohmytennis.R;

import java.util.ArrayList;
import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter {

    private List<MyEvent> mEventsList;

    private TimeFormatter mTimeFormatter = new TimeFormatter();

    EventsAdapter() {
        mEventsList = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_event, parent, false);
        return new EventHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyEvent event = mEventsList.get(position);

        ((EventHolder) holder).tvText.setText(event.getTitle());
        ((EventHolder) holder).tvTime.setText(mTimeFormatter.format(event.getTimeInMillis()));
        ((EventHolder) holder).cardView.setBackgroundColor(mEventsList.get(position).getColor());
        ((EventHolder) holder).tvSample.setText(event.getTime());
    }

    @Override
    public int getItemCount() {
        return mEventsList.size();
    }

    class EventHolder extends RecyclerView.ViewHolder {

        TextView tvText, tvTime,tvSample;
        CardView cardView;

        EventHolder(View itemView) {
            super(itemView);

            tvText = itemView.findViewById(R.id.tvText);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvSample = itemView.findViewById(R.id.tvsample);
        }
    }

    void addEvents(List<MyEvent> events) {
        for (int i = 0; i < events.size(); i++) {
            mEventsList.add(events.get(i));
            notifyItemInserted(i);
        }
    }

    void addEvent(MyEvent event) {
        mEventsList.add(event);
        notifyItemInserted(mEventsList.size());
    }

    void clearList() {
        mEventsList.clear();
        notifyDataSetChanged();
    }
}
