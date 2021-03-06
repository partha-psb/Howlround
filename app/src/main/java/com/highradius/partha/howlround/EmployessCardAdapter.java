package com.highradius.partha.howlround;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EmployessCardAdapter extends ArrayAdapter<EmployessCards>
{
    Context context;
    List<EmployessCards> data;
    private ArrayList<EmployessCards> arrayList;

    public EmployessCardAdapter(@NonNull Context context, int resource, @NonNull List<EmployessCards> objects) {
        super(context, resource, objects);
        this.context=context;
        this.data=objects;
        arrayList=new ArrayList<>();
        this.arrayList.addAll(data);
    }
    public View getView(final int positions, View convertView, ViewGroup parent)
    {
        EmployessCards cards=getItem(positions);
        if(convertView==null)
        {
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.give_feedback,parent,false);

        }
        TextView employeedesignation=convertView.findViewById(R.id.card_desination);
        ImageView employeeImage=convertView.findViewById(R.id.review_card_image);
        TextView  employeecardname=convertView.findViewById(R.id.review_card_name);
        TextView employeecarddepartment=convertView.findViewById(R.id.review_card_department);
        employeecarddepartment.setText(cards.getDepartment());
        employeedesignation.setText(cards.getDesignation());
        Picasso.with(context)
                .load(data.get(positions).image_url)
                .fit()
                .centerCrop()
                .into(employeeImage);
        employeecardname.setText(cards.getName());


        return convertView;
    }
    public void filter(String characterText)
    {
        characterText=characterText.toLowerCase(Locale.getDefault());
        data.clear();
        if(characterText.length()==0) {
            data.addAll(arrayList);
        }else
        {
            data.clear();
            for(EmployessCards employessCards:arrayList)
            {
                if(employessCards.getName().toLowerCase(Locale.getDefault()).contains(characterText))
                    data.add(employessCards);
            }
        }
        notifyDataSetChanged();
    }


}
//