package com.sportsprediction.bettingtipssportsprediction1;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class TodayAdapter extends ArrayAdapter<Today_Class> {

    private Activity context;
    private List<Today_Class> today_list;


    public TodayAdapter(@NonNull Activity context, List<Today_Class> today_list) {
        super(context, R.layout.today_sample,today_list);
        this.context=context;
        this.today_list=today_list;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //LayoutInflater layoutInflater= context.getLayoutInflater();
        //View view=layoutInflater.inflate(R.layout.today_sample,parent,false);

        LayoutInflater layoutInflater=LayoutInflater.from(context);



        if (convertView==null){
            //layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.today_sample,parent,false);

        }


        Today_Class today_class=today_list.get(position);
        TextView league=convertView.findViewById(R.id.league_name);
        TextView team=convertView.findViewById(R.id.team_name);
        TextView time=convertView.findViewById(R.id.score1);
        TextView time1=convertView.findViewById(R.id.score2);


        league.setText(today_class.getLegue());
        team.setText(today_class.getTeam());
        time.setText(today_class.getScore1());
        time1.setText(today_class.getScore2());



        return convertView;
    }



}
