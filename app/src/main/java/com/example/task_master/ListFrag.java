package com.example.task_master;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListFrag extends ListFragment {

    NameClicked parentActivity;

    public interface NameClicked
    {
        public void onNameClick(int index);
    }


    public ListFrag() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        parentActivity=(NameClicked) context;
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        parentActivity.onNameClick(position);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<String> title = new ArrayList<>();

        if (MyApplication.tasks.isEmpty())
        {
            showNoTaskDialog();
        }
        else
        {
            for (Task t : MyApplication.tasks)
            {
                title.add(t.getTitle());
                ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, title);
                setListAdapter(adapter);
            }
        }
    }

    public void updateTaskList() {
        ArrayList<String> title = new ArrayList<>();
        for (Task t : MyApplication.tasks) {
            title.add(t.getTitle());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, title);
        setListAdapter(adapter);
    }

    private void showNoTaskDialog()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(requireContext());
        builder.setTitle("No Task Created");
        builder.setMessage("You haven't created any task yet");
        builder.setPositiveButton("Ok",(dialog, which) ->
        {
           startActivity(new Intent(requireContext(),Home.class));
        });

        builder.show();

    }

}