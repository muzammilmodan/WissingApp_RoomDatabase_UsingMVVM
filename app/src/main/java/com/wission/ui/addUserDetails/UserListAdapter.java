package com.wission.ui.addUserDetails;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.wission.Model.UserDetails_Room;
import com.wission.R;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.TasksViewHolder> {

    private Context mCtx;
    private List<UserDetails_Room> taskList;

    public UserListAdapter(Context mCtx, List<UserDetails_Room> taskList) {
        this.mCtx = mCtx;
        this.taskList = taskList;
    }

    @Override
    public TasksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.row_user_details_room, parent, false);
        return new TasksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TasksViewHolder holder, int position) {
        UserDetails_Room t = taskList.get(position);
        holder.textViewTask.setText(t.getUser_name());
        holder.textViewDesc.setText(t.getUser_phone());
        holder.textViewFinishBy.setText(t.getUser_email());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    class TasksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewStatus, textViewTask, textViewDesc, textViewFinishBy;

        public TasksViewHolder(View itemView) {
            super(itemView);

            textViewStatus = itemView.findViewById(R.id.textViewStatus);
            textViewTask = itemView.findViewById(R.id.textViewTask);
            textViewDesc = itemView.findViewById(R.id.textViewDesc);
            textViewFinishBy = itemView.findViewById(R.id.textViewFinishBy);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            UserDetails_Room task = taskList.get(getAdapterPosition());

            Intent intent = new Intent(mCtx, UpdateTaskActivity.class);
            intent.putExtra("userDetails", task);
            mCtx.startActivity(intent);
        }
    }
}
