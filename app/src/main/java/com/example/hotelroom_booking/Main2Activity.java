package com.example.hotelroom_booking;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hotelroom_booking.R;
public class Main2Activity extends AppCompatActivity {
    GridView gridView;

    String[] facilities = {"Pool","Gym","Spa","Restaurant","WiFi","Chauffeur"};
    String[] text={"-The swimming pool has a perfect ventilation system, strict water quality control, and natural lighting that ensures a relaxing atmosphere for all the guests.\n" +
                   "-The Pool area is open daily from 8:00 Am to 5:00 Pm\n" +
                   "-Proper swimming costume is a must for using the swimming pool facilities.","On almost all of our hotels you have free access to gym facilities where you can train for free when you stay with us."+"\nIf you prefer outdoor activities we’d love to give you tips on jogging or walking routes in the hotel surroundings.",
                   "Our loyal and valued guests are the heart of our business. This is why we have put together a number of spa services to create a heightened experience all in the privacy and comfort of your room.\n" + "\n" + "We promise to make your stay here exceptional.","The hotel’s cuisine is our pride and joy. You may appreciate this first thing in the morning with the abundant buffet that awaits you, which offers homemade cakes, yoghurt, fruit juices, cereals, marmalade and jam.\n" +
                   "\n" +
                   "The menu for lunch and dinner, which is accompanied with a rich vegetable buffet, are prepared with attention and can be personalised without difficulty.","Room service is great, but nothing beats Skyping with your family.Room service is great, but nothing beats staying connected with your loved ones."+"The goal is to give the people what they want, and the people want WiFi. We provide high quality wifi service to satisfy our customers","The  chauffeur is available for door to door travel  and for day visits during one's stay.\n" +
                   "The  chauffeur will arrive at your house to drive you smoothly to the hotel and country side  allowing you to sit back and enjoy the views, and will return you safely at the end of your stay."};
    int[] facimages = {R.drawable.pool,R.drawable.gym,R.drawable.spa,R.drawable.restaurant,R.drawable.wifi,R.drawable.car};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //finding listview
        gridView = findViewById(R.id.gridview);

        CustomAdapter customAdapter = new CustomAdapter();
        gridView.setAdapter(customAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getApplicationContext(),fruitNames[i],Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), GriDItemActivity.class);
                intent.putExtra("name",facilities[i]);
                intent.putExtra("image",facimages[i]);
                intent.putExtra("msg",text[i]);
                startActivity(intent);

            }
        });


    }

    private class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return facimages.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = getLayoutInflater().inflate(R.layout.row_data,null);
            //getting view in row_data
            TextView name = view1.findViewById(R.id.facilities);
            ImageView image = view1.findViewById(R.id.images);

            name.setText(facilities[i]);
            image.setImageResource(facimages[i]);
            return view1;



        }
    }
}