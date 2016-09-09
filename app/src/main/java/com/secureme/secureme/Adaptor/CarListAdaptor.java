package com.secureme.secureme.Adaptor;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.secureme.secureme.Entity.Cars;
import com.secureme.secureme.R;

import java.util.ArrayList;

/**
 * Created by mkumail on 8/5/16.
 */

    public class CarListAdaptor extends ArrayAdapter<Cars> {
        Activity context;
        LayoutInflater inflater;

        public CarListAdaptor(Activity _context, ArrayList<Cars> objects) {
            super(_context, R.layout.activity_car_list_row, objects);
            this.context = _context;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        static class ViewHolder {
            TextView carPlateNumber;
            TextView carName;

        }

        public View getView(int position, View convertView, ViewGroup parent) {
            final Cars item = getItem(position);
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.activity_car_list_row, null);
                holder.carPlateNumber = (TextView) convertView.findViewById(R.id.activity_car_list_row_carPlateNumber);
                holder.carName = (TextView) convertView.findViewById(R.id.activity_car_list_row_carName);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.carPlateNumber.setText(item.getCarPlateNumber());
            holder.carName.setText(item.getCarNAme());

            return convertView;
        }
}
