package com.vinnie.preciousfamily.Lists;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vinnie.preciousfamily.FamMembers;
import com.vinnie.preciousfamily.R;
import com.vinnie.preciousfamily.connection.savedInformation;

import java.util.List;

public class FamilyAdapter extends RecyclerView.Adapter<FamilyAdapter.ViewHolder>{

    private List<Family_list> list_data;

    public FamilyAdapter(List<Family_list> list_data) {
        this.list_data = list_data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.family_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Family_list listData=list_data.get(position);
        holder.txtfamname.setText(listData.getFamname());
        holder.txtmemberno.setText(Integer.toString(listData.getMembersno())+" Members");
    }

    @Override
    public int getItemCount() {
        return list_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtfamname,txtmemberno;
        public ViewHolder(final View itemView) {
            super(itemView);
            txtfamname=itemView.findViewById(R.id.txtfamname);
            txtmemberno=itemView.findViewById(R.id.txt_mnumber);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    savedInformation.familyName = txtfamname.getText().toString();
                    Intent intent = new Intent(v.getContext(),FamMembers.class);
                    v.getContext().startActivity(intent);
                    //Toast.makeText(itemView.getContext(), savedInformation.familyName, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
