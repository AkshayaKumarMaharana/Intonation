package com.a16gmail.maharana.akshaya.last;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.icu.util.Calendar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class BookNow extends AppCompatActivity {

    RecyclerView bookBikes;
    DatabaseReference mDatabase;
    Button fromdate;
    Button todate;
    int year_x,month_x,date_x,hour_x,min_x;
    static  final  int DIALOG_ID=0,DIALOG_ID2=1;
    Button btnBook;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_now);

        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        date_x = cal.get(Calendar.DAY_OF_MONTH);


        fromdate=(Button)findViewById(R.id.btnStartDate);
        todate = (Button)findViewById(R.id.btnEndDate);

        mDatabase= FirebaseDatabase.getInstance().getReference().child("bike list").child("basic");

        bookBikes = (RecyclerView)findViewById(R.id.bookBikes);
        bookBikes.setHasFixedSize(true);
        bookBikes.setLayoutManager(new LinearLayoutManager(this));
        showdialogOnButtonClick();
    }

    public void showdialogOnButtonClick(){
        fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID);
            }
        });

    }
public void showdialog1OnButtonClick(){
    todate.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showDialog(DIALOG_ID);
        }
    });

}

    @Override
    protected Dialog onCreateDialog(int id) {
        if(id==DIALOG_ID)
        return new DatePickerDialog(this, dpicklistner, year_x,month_x,date_x);
        if(id==DIALOG_ID2)
            return new TimePickerDialog(this, tpicklistner , hour_x,min_x,false);
        return null;
    }
    private TimePickerDialog.OnTimeSetListener tpicklistner = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hour_x = hourOfDay;
            min_x = minute;
            fromdate.setText(date_x+"/"+month_x+"/"+year_x+" - "+hour_x+":"+min_x);
        }
    };
    private TimePickerDialog.OnTimeSetListener tpicklistner1 = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hour_x = hourOfDay;
            min_x = minute;
            todate.setText(date_x+"/"+month_x+"/"+year_x+" - "+hour_x+":"+min_x);
        }
    };

    private  DatePickerDialog.OnDateSetListener dpicklistner= new  DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            year_x= year+1;
            month_x=month;
            date_x=dayOfMonth;
            showDialog(DIALOG_ID2);
        }


    };
    private  DatePickerDialog.OnDateSetListener dpicklistner1= new  DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            year_x= year+1;
            month_x=month;
            date_x=dayOfMonth;

            showDialog(DIALOG_ID2);
        }


    };

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Bikes,BikesViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Bikes, BikesViewHolder>(

                Bikes.class,
                R.layout.row,
                BikesViewHolder.class,
                mDatabase

        ) {
            @Override
            protected void populateViewHolder(BikesViewHolder viewHolder, final Bikes model, int position) {
                viewHolder.setName(model.getName());
                viewHolder.setImage(getApplicationContext(),model.getImage());
            }
        };
        bookBikes.setAdapter(firebaseRecyclerAdapter);
        fromdate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }



    public static class BikesViewHolder extends RecyclerView.ViewHolder {

        View mView;
        public BikesViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setName(String name){
            TextView title_name = (TextView)mView.findViewById(R.id.bikeName);
            title_name.setText(name);
        }

        public void setImage(Context ctx, String image){
            ImageView imageView = (ImageView)mView.findViewById(R.id.imageBike);
            Picasso.with(ctx).load(image).into(imageView);
        }
    }
}
