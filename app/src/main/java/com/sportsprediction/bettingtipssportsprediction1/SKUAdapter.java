package com.sportsprediction.bettingtipssportsprediction1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.billingclient.api.SkuDetails;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SKUAdapter extends RecyclerView.Adapter<SKUAdapter.SKUVIEWHOLDER> {

    private List<SkuDetails> arrayList;
    private Context context;
    private BuySKU buySKU;

    public SKUAdapter(List<SkuDetails> arrayList, Context context, BuySKU buySKU){
        this.arrayList=arrayList;
        this.context=context;
        this.buySKU=buySKU;
    }


    @NonNull
    @Override
    public SKUAdapter.SKUVIEWHOLDER onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_bill_confirm_generic,parent,false);


        return new SKUVIEWHOLDER(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SKUAdapter.SKUVIEWHOLDER holder, final int position) {

        holder.tv_title.setText(arrayList.get(position).getTitle().trim());
        holder.tv_details.setText(arrayList.get(position).getPrice());
        holder.bt_buty_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SKUAdapter.this.buySKU.onBuySKU(true,arrayList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class SKUVIEWHOLDER extends RecyclerView.ViewHolder{


        @BindView(R.id.tv_title)
        TextView tv_title;

        @BindView(R.id.tv_details)
        TextView tv_details;

        @BindView(R.id.bt_buty_now)
        Button bt_buty_now;


        private SKUVIEWHOLDER(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
