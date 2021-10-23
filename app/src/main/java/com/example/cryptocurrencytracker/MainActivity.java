package com.example.cryptocurrencytracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Binder;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cryptocurrencytracker.adapter.CurrencyAdapter;
import com.example.cryptocurrencytracker.databinding.ActivityMainBinding;
import com.example.cryptocurrencytracker.databinding.CurrencyRvItemBinding;
import com.example.cryptocurrencytracker.model.CurrencyModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    ArrayList<CurrencyModel> currencyModelArrayList;
    CurrencyAdapter currencyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        currencyModelArrayList=new ArrayList<>();
        currencyAdapter=new CurrencyAdapter(currencyModelArrayList);
        binding.currencyRecycleView.setLayoutManager(new LinearLayoutManager(this));
        binding.currencyRecycleView.setAdapter(currencyAdapter);
        getCurrenyData();
        binding.searchCryptoEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filterCurrencyList(s.toString());

            }
        });

    }
    private void filterCurrencyList(String currency){
        ArrayList<CurrencyModel> filteredList=new ArrayList<>();
        for (CurrencyModel item:currencyModelArrayList){
            if (item.getCurrencyName().toLowerCase().contains(currency.toLowerCase())){
                filteredList.add(item);
            }
        }
        if (filteredList.isEmpty()){
            Toast.makeText(this, "No Currency Found For searched query", Toast.LENGTH_SHORT).show();

        }else {
        currencyAdapter.filterList(filteredList);
        }
    }

    private void getCurrenyData() {
        binding.currencyProgressBar.setVisibility(View.VISIBLE);
        String url="https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
            binding.currencyProgressBar.setVisibility(View.GONE);
                try {
                    JSONArray dataArray=response.getJSONArray("data");
                    for (int i=0;i<dataArray.length();i++){
                        JSONObject dataObj=dataArray.getJSONObject(i);
                        String name=dataObj.getString("name");
                        String symbol=dataObj.getString("symbol");
                        JSONObject quote=dataObj.getJSONObject("quote");
                        JSONObject USD=quote.getJSONObject("USD");
                        double price=USD.getDouble("price");
                        currencyModelArrayList.add(new CurrencyModel(name,symbol,price));

                    }
                    currencyAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Fail to Extract json Data...", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                binding.currencyProgressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Fail to get Data...", Toast.LENGTH_SHORT).show();


            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> headers=new HashMap<>();
                headers.put("X-CMC_PRO_API_KEY","603f5368-c8dd-4b85-8dcc-f69695a596b6");
                return headers;

            }
        };
        requestQueue.add(jsonObjectRequest);
    }
}