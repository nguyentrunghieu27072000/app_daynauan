package com.example.nguyentung.NauAnVungMien.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.nguyentung.NauAnVungMien.MonAnAdapter.MonAnMienBac_Adapter;
import com.example.nguyentung.NauAnVungMien.R;
import com.example.nguyentung.NauAnVungMien.ThongTinMonAn;
import com.example.nguyentung.NauAnVungMien.db.MonAnController;

import java.util.ArrayList;

import it.gmariotti.recyclerview.adapter.AlphaAnimatorAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MienBac_Fragment extends Fragment {


    private RecyclerView recyclerView;
    private MonAnMienBac_Adapter adapter;
    private GridLayoutManager grdManager;
    private ArrayList<ThongTinMonAn> dataSearch;
    public static ArrayList<ThongTinMonAn> lsMonAnMienBac = new ArrayList<>();
    private int ok=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(ok==0){
            getData();
            ok++;
        }
        View v = inflater.inflate(R.layout.fragment_mien_bac_, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.rcView);
        adapter = new MonAnMienBac_Adapter(lsMonAnMienBac, getContext());
        AlphaAnimatorAdapter animatorAdapter = new AlphaAnimatorAdapter(adapter, recyclerView);
        recyclerView.setAdapter(animatorAdapter);
        grdManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(grdManager);
        setHasOptionsMenu(true);
        return v;
    }

    public ArrayList<ThongTinMonAn> getData(){
        ArrayList<ThongTinMonAn> ls = new MonAnController(getContext()).getMonAnMienBac();
        for(int i=0; i<ls.size(); i++){
            lsMonAnMienBac.add(ls.get(i));
        }
        return lsMonAnMienBac;
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem item = menu.findItem(R.id.mnSearch);

        SearchView sv = (SearchView) MenuItemCompat.getActionView(item);
        sv.setOnQueryTextListener(onTextChanged);

        super.onCreateOptionsMenu(menu, inflater);
    }
    private SearchView.OnQueryTextListener onTextChanged = new SearchView.OnQueryTextListener() {

        @Override
        public boolean onQueryTextSubmit(String query) {

            return false;
        }

        @Override
        public boolean onQueryTextChange(String value) {
            dataSearch = new ArrayList<>();

            for (int i = 0; i < lsMonAnMienBac.size(); i++){
                if(lsMonAnMienBac.get(i).getName().toLowerCase().contains(value.toLowerCase())){
                    dataSearch.add(new ThongTinMonAn(lsMonAnMienBac.get(i).getName(), lsMonAnMienBac.get(i).getBm(), lsMonAnMienBac.get(i).getMaterial(), lsMonAnMienBac.get(i).getProcess()));
                }
            }
            adapter = new MonAnMienBac_Adapter(dataSearch, getActivity());
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            return false;
        }
    };

}
