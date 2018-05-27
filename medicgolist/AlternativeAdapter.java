package com.example.shubhanshu.medicgolist;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AlternativeAdapter extends ArrayAdapter<MedicineDetail> {
    public AlternativeAdapter(Activity context, ArrayList<MedicineDetail> medicine) {
        super(context, 0, medicine);
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.alternative_list, parent, false);
        }
        MedicineDetail currMedicineDetail = getItem(position);
        TextView medicineName = (TextView)listItemView.findViewById(R.id.alt_medicine_name);
        TextView medicinePrice = (TextView)listItemView.findViewById(R.id.alt_price);
        TextView medicineDesc = (TextView)listItemView.findViewById(R.id.alt_desc);
        medicineName.setText(currMedicineDetail.getMedicineName());
        medicineDesc.setText(currMedicineDetail.getDescription());
        medicinePrice.setText(currMedicineDetail.getPrice());

        return listItemView;
    }
}
