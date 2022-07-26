package com.example.onsalestore.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import com.example.onsale.R;
import com.example.onsalestore.adapters.ClosetItemAdapter;

import com.example.onsalestore.objects.ClosetItem;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ClosetFragment extends Fragment implements ClosetItemMultiSelectListener {

    private RecyclerView rvClosetItems;
    protected ClosetItemAdapter closetItemAdapter;
    protected List<ClosetItem> allClosetItems, selectedList;
    private ActionMode actionMode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_closet, container, false);
        rvClosetItems = view.findViewById(R.id.rvClosetItems);
        allClosetItems = new ArrayList<>();
        selectedList = new ArrayList<>();
        closetItemAdapter = new ClosetItemAdapter(getContext(), allClosetItems);
        closetItemAdapter.setItemClickListener(this);
        rvClosetItems.setAdapter(closetItemAdapter);
        rvClosetItems.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvClosetItems.addItemDecoration(new DividerItemDecoration(getContext(), GridLayout.HORIZONTAL));
        populateClosetItemsList();

        return view;
    }


    private void populateClosetItemsList() {
        ParseUser currentUser = ParseUser.getCurrentUser();
        JSONArray itemsInCloset = currentUser.getJSONArray("closet");
        if (itemsInCloset == null) {
            return;
        }
        for (int i = 0; i < itemsInCloset.length(); ++i) {
            try {
                JSONObject closetItem = (JSONObject) itemsInCloset.get(i);
                String itemId = (String) closetItem.get("objectId");
                queryClosetItems(itemId);
            } catch (Exception e) {
                //TODO: Decide how to handle this exception later
            }
        }
    }

    private void queryClosetItems(String itemId) {
        ParseQuery<ClosetItem> query = ParseQuery.getQuery(ClosetItem.class);
        query.addDescendingOrder(ClosetItem.KEY_CREATED_KEY);
        query.getInBackground(itemId, new GetCallback<ClosetItem>() {
            @Override
            public void done(ClosetItem object, ParseException e) {
                if (e == null) {
                    allClosetItems.add(object);
                    closetItemAdapter.notifyDataSetChanged();
                } else {
                    //TODO: Decide how to handle this exception later
                }
            }
        });
    }

    @Override
    public void onMultiSelectUpdated(int count) {
        if (actionMode == null && count > 0) {
            actionMode = getActivity().startActionMode(actionModeCallback);
        }
        if (actionMode != null && count != 0) {
            actionMode.setTitle("You selected " + count);
        } else {
            actionMode.finish();
        }
    }

    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {

            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_post_or_delete, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            //TODO: check for post
            return false;
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
        }
    };

}