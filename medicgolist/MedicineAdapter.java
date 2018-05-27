package com.example.shubhanshu.medicgolist;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by subham on 29/3/18.
 */

public class MedicineAdapter extends ArrayAdapter<MedicineDetail> {

    public MedicineAdapter(Activity context, ArrayList<MedicineDetail> medicine) {
        super(context, 0, medicine);
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_layout, parent, false);
        }

        MedicineDetail currMedicineDetail = getItem(position);
        TextView medicineName = (TextView)listItemView.findViewById(R.id.medicine_name);
        TextView medicinePrice = (TextView)listItemView.findViewById(R.id.medicine_price);
        TextView medicineDesc = (TextView)listItemView.findViewById(R.id.short_desc);
        medicineName.setText(currMedicineDetail.getMedicineName());
        medicineDesc.setText(currMedicineDetail.getDescription());
        medicinePrice.setText(currMedicineDetail.getPrice());

        return listItemView;
    }
}
