package lk.icbt.fyp.helpYouNeed.FriendLocation;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import lk.icbt.fyp.helpYouNeed.R;

public class listOnlineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtEmail;
    ItemClickListener itemClickListenener;
    public listOnlineViewHolder(View itemView)
    {
        super(itemView);
        txtEmail = (TextView)itemView.findViewById(R.id.txt_email);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListenener(ItemClickListener itemClickListenener) {
        this.itemClickListenener = itemClickListenener;
    }

    @Override
    public void onClick(View view)
    {
        itemClickListenener.onClick(view,getAdapterPosition());
    }
}