package com.hatchers.ruralcaravane.customer_registration;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.hatchers.ruralcaravane.customer_registration.adapter.CustomerListAdapter;
import com.hatchers.ruralcaravane.customer_registration.database.CustomerTable;
import com.hatchers.ruralcaravane.customer_registration.database.CustomerTableHelper;
import com.hatchers.ruralcaravane.R;

import java.util.ArrayList;

public class CustomerListFragment extends Fragment {

    CustomerListAdapter customerListAdapter;
    private RecyclerView customerRecyclerView;
    private TextView no_cust_txt;
    public static final String OPEN_FROM = "open_from";
    public static final String FROM_CONSTRUCTION = "from_construction";
    public static final String FROM_PAYMENT = "from_payment";
    private String openFrom;

    ArrayList<CustomerTable> customerTables;

    public CustomerListFragment()
    {
        // Required empty public constructor
    }

    public static CustomerListFragment getInstance(String openFrom)
    {
        CustomerListFragment fragment = new CustomerListFragment();
        Bundle args = new Bundle();
        args.putString(OPEN_FROM, openFrom);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            openFrom = getArguments().getString(OPEN_FROM);

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_customer__list, container, false);

        initializations(view);

        setData();

    return view;
    }

    private void initializations(View view)
    {
        no_cust_txt=(TextView)view.findViewById(R.id.no_cust_txt);
        customerRecyclerView = (RecyclerView) view.findViewById(R.id.customerRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        customerRecyclerView.setLayoutManager(layoutManager);

        customerRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        customerRecyclerView.setItemAnimator(new DefaultItemAnimator());
        setData();


        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }

    }

    public  void setData()
    {
        try
        {
            customerTables = CustomerTableHelper.getCustomerdataList(getContext());
            customerListAdapter = new CustomerListAdapter(getContext(), customerTables);
            customerRecyclerView.setAdapter(customerListAdapter);
            if(!(customerTables.size() >0))
            {
                customerRecyclerView.setVisibility(View.GONE);
                no_cust_txt.setVisibility(View.VISIBLE);
            }
            else
            {
                customerRecyclerView.setVisibility(View.VISIBLE);
                no_cust_txt.setVisibility(View.GONE);
            }

        }
        catch (Exception e)
        {
                e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        setData();
        super.onResume();
    }
}
