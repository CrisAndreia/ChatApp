package com.crispereira.projectchatapp.Connection;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.crispereira.projectchatapp.R;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter {

    //private static final int INDISPONIVEL = 0;
    private static final int DISPONIVEL = 1;

    private LayoutInflater inflater;
    private List<JSONObject> users = new ArrayList<>();

    public ListAdapter (LayoutInflater inflater) {
        this.inflater = inflater;
    }

    private class ListHolder extends RecyclerView.ViewHolder {

        TextView nameUser;

        public ListHolder(@NonNull View itemView) {
            super(itemView);

            nameUser = itemView.findViewById(R.id.nameUser);
            //statusTxt = itemView.findViewById(R.id.statusTxt);
        }
    }

    @Override
    public int getItemViewType(int position) {

        JSONObject user = users.get(position);

        if (user.has("name"))
            return DISPONIVEL;

        return -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = inflater.inflate(R.layout.item_list_users, parent, false);

        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        JSONObject user = users.get(position);
        //int sizeUsers = users.size();

        try {
            if (user.has("name")) {
                ListAdapter.ListHolder listHolder = (ListAdapter.ListHolder) holder;
                listHolder.nameUser.setText(user.getString("name"));
            }

        } catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public JSONObject getUser(int position){
        return users.get(position);
    }

    public void addItem (JSONObject jsonObject) {
        users.add(jsonObject);
        notifyDataSetChanged();
    }
}
