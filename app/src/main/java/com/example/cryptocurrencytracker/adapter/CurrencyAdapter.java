package com.example.cryptocurrencytracker.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cryptocurrencytracker.R;
import com.example.cryptocurrencytracker.databinding.CurrencyRvItemBinding;
import com.example.cryptocurrencytracker.model.CurrencyModel;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.viewHolder> {
    LayoutInflater inflater;
    private ArrayList<CurrencyModel> currencyModelArrayList;
    private static DecimalFormat df2=new DecimalFormat("#.##");

    public CurrencyAdapter(ArrayList<CurrencyModel> currencyModelArrayList) {
        this.currencyModelArrayList = currencyModelArrayList;
    }

    public void filterList(ArrayList<CurrencyModel> filteredList){
        currencyModelArrayList=filteredList;
        notifyDataSetChanged();
    }

    @Override
    public CurrencyAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater==null){
            inflater=LayoutInflater.from(parent.getContext());
        }
        return new viewHolder(DataBindingUtil.inflate(inflater, R.layout.currency_rv_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyAdapter.viewHolder holder, int position) {
    CurrencyModel currencyModel=currencyModelArrayList.get(position);
    holder.binding.currencyNameTv.setText(currencyModel.getCurrencyName());
    holder.binding.symbolTextView.setText(currencyModel.getSymbol());
    holder.binding.currencyRateTv.setText("$ "+df2.format(currencyModel.getPrice()));

    }

    @Override
    public int getItemCount() {
        return currencyModelArrayList.size()    ;
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        CurrencyRvItemBinding binding;
        public viewHolder(@NonNull CurrencyRvItemBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
