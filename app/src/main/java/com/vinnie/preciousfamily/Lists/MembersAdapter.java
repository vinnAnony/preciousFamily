package com.vinnie.preciousfamily.Lists;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vinnie.preciousfamily.R;

import java.util.List;


public class MembersAdapter extends RecyclerView.Adapter<MembersAdapter.ViewHolder> {

    private List<Members_list> membList;


    public MembersAdapter(List<Members_list> membList) {
        this.membList = membList;
    }

    @Override
    public MembersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.members_card,parent,false);
        return new MembersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Members_list listData=membList.get(position);
        String names = listData.getFirstName()+" "+listData.getMiddleName()+" "+listData.getSurname();
        String fletter = String.valueOf(listData.getFirstName().charAt(0)).toUpperCase();
        holder.txtmname.setText(names);
        holder.txtphone.setText(listData.getPhone());
        holder.txtdrawable.setText(fletter);
    }

    @Override
    public int getItemCount() {
        return membList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtmname,txtphone,txtdrawable;
        public ViewHolder(final View itemView) {
            super(itemView);
            txtmname=itemView.findViewById(R.id.mName);
            txtphone=itemView.findViewById(R.id.mPhone);
            txtdrawable=itemView.findViewById(R.id.mImage);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    savedInformation.familyName = txtfamname.getText().toString();
//                    Intent intent = new Intent(v.getContext(),FamMembers.class);
//                    v.getContext().startActivity(intent);
//                    //Toast.makeText(itemView.getContext(), savedInformation.familyName, Toast.LENGTH_SHORT).show();
//                }
//            });
        }
    }

}
