package info.abhi.IntelimentTech.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import info.abhi.IntelimentTech.ItemClickListener;
import info.abhi.IntelimentTech.MainActivity;
import info.abhi.IntelimentTech.R;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private final ArrayList<String> alName;
    private final Context context;

    public ItemAdapter(Context context, ArrayList<String> alName) {
        super();
        this.context = context;
        this.alName = alName;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.grid_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.tvSpecies.setText(alName.get(i));

        viewHolder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(int position, boolean isLongClick) {
                if (isLongClick) {
                    context.startActivity(new Intent(context, MainActivity.class));
                } else {
                    Intent intent = new Intent("custom-message");
                    intent.putExtra("quantity",alName.get(position));
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return alName.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public final TextView tvSpecies;
        private ItemClickListener clickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            tvSpecies = itemView.findViewById(R.id.tv_species);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            //noinspection deprecation
            clickListener.onClick(getPosition(), false);
        }

        @Override
        public boolean onLongClick(View view) {
            //noinspection deprecation
            clickListener.onClick(getPosition(), true);
            return true;
        }
    }

}

