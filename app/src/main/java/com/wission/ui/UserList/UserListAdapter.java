package com.wission.ui.UserList;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wission.Model.UserData;
import com.wission.R;
import com.wission.interfaces.RecyclerViewItemClicked;
import com.wission.ui.UserDetails.UserDetailsActivity;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.MyViewHolder> implements Handler.Callback {
    private final List<UserData> userHistory;
    Context context;
    private RecyclerViewItemClicked mListener;

    public UserListAdapter(List<UserData> userHistory, Context applicationContext, RecyclerViewItemClicked mListener) {
        this.userHistory = userHistory;
        this.context = applicationContext;
        this.mListener = mListener;
        if (getItemCount() == 1) {
            lastSelectedPosition = 0;
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
      //  return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_data, parent, false), mItemClickListener);

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_data, parent, false);
        return new MyViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        try {

            myViewHolder.tvName.setText("Name: "+ userHistory.get(i).getStrUserName());
            myViewHolder.tvEmail.setText("Email: '"+ userHistory.get(i).getStrUserEmail());
            myViewHolder.tvPhone.setText("Phone: "+ userHistory.get(i).getStrUserPhone());

            /*myViewHolder.llUserDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, UserDetailsActivity.class);
                    intent.putExtra("name", userHistory.get(i).getStrUserName());
                    intent.putExtra("email", userHistory.get(i).getStrUserEmail());
                    intent.putExtra("phone", userHistory.get(i).getStrUserPhone());
                    context.startActivity(intent);
                }
            });*/
        } catch (Exception e) {
            Log.e("onBindViewHolder", "====>" + e.getMessage().toString());
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return userHistory.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        return false;
    }
    public static int lastSelectedPosition = -1;
    public class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        TextView tvName, tvEmail,tvPhone;
        LinearLayout llUserDetails;
        RecyclerViewItemClicked mListener;
        ImageView imgEdit;

        public MyViewHolder(@NonNull View itemView, RecyclerViewItemClicked mListener) {
            super(itemView);
            try {
                this.mListener = mListener;

                llUserDetails = itemView.findViewById(R.id.llUserDetails);
                tvName = itemView.findViewById(R.id.tvName);
                tvEmail = itemView.findViewById(R.id.tvEmail);
                tvPhone = itemView.findViewById(R.id.tvPhone);
                imgEdit = itemView.findViewById(R.id.imgEdit);

                llUserDetails.setOnClickListener(this);
                imgEdit.setOnClickListener(this);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onClick(View v) {


            switch (v.getId()) {

                case R.id.imgEdit:
                   /* mListener.onItemClickListener(v, getAdapterPosition());
                    lastSelectedPosition = getAdapterPosition();
                    notifyItemRangeChanged(0, userHistory.size());*/
                    break;

                case R.id.llUserDetails:
                    Intent intent = new Intent(context, UserDetailsActivity.class);
                    intent.putExtra("name", userHistory.get(getAdapterPosition()).getStrUserName());
                    intent.putExtra("email", userHistory.get(getAdapterPosition()).getStrUserEmail());
                    intent.putExtra("phone", userHistory.get(getAdapterPosition()).getStrUserPhone());
                    context.startActivity(intent);
                    break;
            }
        }
    }
}
