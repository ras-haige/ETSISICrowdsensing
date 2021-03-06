package com.etsisi.dev.etsisicrowdsensing.bottom.navigation.bar.fragment.campus.incidences;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.etsisi.dev.etsisicrowdsensing.R;
import com.etsisi.dev.etsisicrowdsensing.model.Incidence;

import java.util.ArrayList;

public class IncidentsAdapter extends RecyclerView.Adapter<IncidentsViewHolder> {

    private static final String TAG = "IncidentsAdapter";

    private static Context context;
    private final IncidenceItemClickListener incidenceItemClickListener;
    private ArrayList<Incidence> mDataset;

    private final int MATERIAL_ICON = R.drawable.material_icon_hd;
    private final int ATMOSPHERE_ICON = R.drawable.atmosphere_icon_hd;
    private final int FACILITIES_ICON = R.drawable.facilities_icon_hd;
    private final int NEW_ICON = R.drawable.add_incidence;

    private static String MATERIAL_STRING ;
    private static String ATMOSPHERE_STRING;
    private static String FACILITIES_STRING;
    private static String ADD_STRING;



    // Provide a suitable constructor (depends on the kind of dataset)
    public IncidentsAdapter(Context context, IncidenceItemClickListener incidenceItemClickListener, ArrayList<Incidence> myDataset) {
        this.context = context;
        this.incidenceItemClickListener = incidenceItemClickListener;
        this.mDataset = myDataset;

        loadCategoryStrings();
    }

    private static void loadCategoryStrings(){
        MATERIAL_STRING  = context.getString(R.string.incidence_material_category);
        ATMOSPHERE_STRING  = context.getString(R.string.incidence_atmosphere_category);
        FACILITIES_STRING = context.getString(R.string.incidence_facilities_category);
        ADD_STRING  = context.getString(R.string.add);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public IncidentsViewHolder onCreateViewHolder(ViewGroup parent,
                                                  int viewType) {
        // create a new view
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.incidence_row, parent, false);

        IncidentsViewHolder vh = new IncidentsViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(IncidentsViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Incidence incidenceItem = mDataset.get(position);


        String bubbleTitle = incidenceItem.getInternalId() == null ? "" : incidenceItem.getInternalId();
        if(bubbleTitle.length() > 10)
            bubbleTitle = bubbleTitle.substring(3,10);

        bubbleTitle = "#" + bubbleTitle;

        String category = incidenceItem.getCategory();

        // TODO Check if incidence state is 1
        // TODO If so change holder bubble title style
        if (incidenceItem.getState() == 1) {
            holder.bubbleTitle.setTypeface(Typeface.DEFAULT_BOLD);
            if(category.equals(MATERIAL_STRING)){
                holder.bubbleTitle.setTextColor(context.getResources().getColor(R.color.darkOrange));
            }
            else if (category.equals(ATMOSPHERE_STRING)){
                holder.bubbleTitle.setTextColor(context.getResources().getColor(R.color.green));
            }
            else if (category.equals(FACILITIES_STRING)){
                holder.bubbleTitle.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            }
        }
        else{
            holder.bubbleTitle.setTextColor(context.getResources().getColor(R.color.black));
            holder.bubbleTitle.setTypeface(Typeface.DEFAULT);
        }


        if(category.equals(ADD_STRING)) {
            holder.bubbleTitle.setText(R.string.add);
        }
        else
            holder.bubbleTitle.setText(bubbleTitle);

        if(category.equals(MATERIAL_STRING)){
            loadImageUsingGlide(MATERIAL_ICON, holder.bubbleImageView);
            //holder.bubbleImageView.setImageResource(MATERIAL_ICON);
        }
        else if (category.equals(ATMOSPHERE_STRING)){
            loadImageUsingGlide(ATMOSPHERE_ICON, holder.bubbleImageView);
            //holder.bubbleImageView.setImageResource(ATMOSPHERE_ICON);
        }
        else if (category.equals(FACILITIES_STRING)){
            loadImageUsingGlide(FACILITIES_ICON, holder.bubbleImageView);
            //holder.bubbleImageView.setImageResource(FACILITIES_ICON);
        }
        else if (category.equals(ADD_STRING)){
            loadImageUsingGlide(NEW_ICON, holder.bubbleImageView);
            //holder.bubbleImageView.setImageResource(NEW_ICON);
        }
        else
            Log.e(TAG, "Found category doesn't match any type.");


        //ViewCompat.setTransitionName(holder.bubbleImageView, );


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incidenceItemClickListener.onIncidenceItemClick(holder.getAdapterPosition(), incidenceItem, holder.bubbleImageView);
            }
        });

            /*
            holder.bubbleLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mIntent = new Intent(mContext, IncidenceDetailActivity.class);
                    mIntent.putExtra("title", holder.bubbleTitle.getText().toString());
                    mContext.startActivity(mIntent);
                }
            });
            */
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    // Load Logo ImageView using Glide Library
    public void loadImageUsingGlide(int drawableId, ImageView imageView) {
        Glide
                .with(context)
                .load(drawableId)
                .into(imageView);
    }

}

// Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
class IncidentsViewHolder extends RecyclerView.ViewHolder {
    TextView bubbleTitle;
    ImageView bubbleImageView;
    RelativeLayout bubbleLayout;


    public IncidentsViewHolder(View v) {
        super(v);
        bubbleTitle = (TextView) v.findViewById(R.id.reason_label);
        bubbleImageView = (ImageView) v.findViewById(R.id.imageView);
        bubbleLayout = (RelativeLayout) v.findViewById(R.id.layout);
    }

}


