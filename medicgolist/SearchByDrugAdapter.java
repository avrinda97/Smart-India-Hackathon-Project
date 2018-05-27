package com.example.shubhanshu.medicgolist;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Utkarsh on 31-03-2018.
 */

public class SearchByDrugAdapter extends ArrayAdapter<MedicineDetail> {


    public SearchByDrugAdapter(Activity context, ArrayList<MedicineDetail> medicine) {
        super(context, 0, medicine);
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.searchbydruglist, parent, false);
        }
        MedicineDetail currMedicineDetail = getItem(position);
        TextView medicineName = (TextView)listItemView.findViewById(R.id.alt_drug_name);
        TextView medicinePrice = (TextView)listItemView.findViewById(R.id.alt_drug_price);
        TextView medicineDesc = (TextView)listItemView.findViewById(R.id.alt_drug_desc);
        medicineName.setText(currMedicineDetail.getMedicineName());
        medicineDesc.setText(currMedicineDetail.getDescription());
        medicinePrice.setText(currMedicineDetail.getPrice());

        return listItemView;
    }
}
