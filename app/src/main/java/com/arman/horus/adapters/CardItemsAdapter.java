package com.arman.horus.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arman.horus.R;
import com.arman.horus.models.CardItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by arman on 3/27/16.
 */
public class CardItemsAdapter extends RecyclerView.Adapter<CardItemsAdapter.ViewHolder> {

    private final OnItemClickListener onCardClickListener;
    private final List<CardItem> mCards;

    public CardItemsAdapter(List<CardItem> cards, OnItemClickListener onCardClickListener) {
        this.mCards = cards;
        this.onCardClickListener = onCardClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View customCardView = inflater.inflate(R.layout.card_item, parent, false);

        return new ViewHolder(customCardView);
    }

    @Override
    public void onBindViewHolder(CardItemsAdapter.ViewHolder holder, int position) {
        final CardItem card = mCards.get(position);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCardClickListener.onItemClick(card);
            }
        });
        // Set item views based on the data model
        holder.titleView.setText(card.getTitle());
        Picasso.with(holder.imageView.getContext())
                .load(card.getImage())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.oops)
                .into(holder.imageView);
        holder.iconView.setImageResource(card.getIcon());
    }

    @Override
    public int getItemCount() {
        return mCards.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final CardView cardView;
        public final TextView titleView;
        public final ImageView imageView;
        public final ImageView iconView;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            titleView = (TextView) itemView.findViewById(R.id.card_title);
            imageView = (ImageView) itemView.findViewById(R.id.card_image);
            iconView = (ImageView) itemView.findViewById(R.id.card_icon);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(CardItem item);
    }
}
