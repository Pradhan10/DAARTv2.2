package com.testworkshop.prototype_2.utilities;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.testworkshop.prototype_2.R;

import java.util.ArrayList;

/**
 * Created by yash on 10/4/18.
 */

public class SolrAdapter extends RecyclerView.Adapter<SolrAdapter.SolrAdapterViewHolder> {

    private SolrData[] data;
    private ArrayList<Integer> images = new ArrayList<>();

    public SolrAdapter(SolrData[] data) {

        this.data = data;
        images.add(R.drawable.zero_1);
        images.add(R.drawable.one_1);
        images.add(R.drawable.two_1);
        images.add(R.drawable.three_1);
        images.add(R.drawable.four_1);
        images.add(R.drawable.five_1);
        images.add(R.drawable.six_1);
        images.add(R.drawable.seven_1);
        images.add(R.drawable.eight_1);
        images.add(R.drawable.nine_1);

    }

    @Override
    public SolrAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_layout, parent, false);
        return new SolrAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SolrAdapterViewHolder holder, int position) {

        String title = data[position].getAddress();
        holder.textView_title.setText(title);
        String area = data[position].getArea();
        holder.tv_area.setText(area);
        String city = data[position].getCity();
        holder.tv_city.setText(city);
        Integer imageID = images.get(position);
        holder.imgIcon.setImageResource(imageID);
//        String rating = data[position].getHotel_star_rating_str();
//        holder.tv_rating.setText(rating);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class SolrAdapterViewHolder extends RecyclerView.ViewHolder {

        ImageView imgIcon;
        TextView textView_title, tv_area, tv_city;

        public SolrAdapterViewHolder(View itemView) {
            super(itemView);
            imgIcon = itemView.findViewById(R.id.imgIcon);
            textView_title = itemView.findViewById(R.id.tv_title);
            tv_area = itemView.findViewById(R.id.tv_area);
            tv_city = itemView.findViewById(R.id.tv_city);
            // tv_rating = (TextView)itemView.findViewById(R.id.tv_hotel_star_rating);
        }
    }

}
