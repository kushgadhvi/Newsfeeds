package com.gadhvi.newsfeeds;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    private RecyclerView newslist;
    private DatabaseReference getdatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getdatabase = FirebaseDatabase.getInstance().getReference().child("News-feeds");
        newslist =(RecyclerView)findViewById(R.id.news);
        newslist.setHasFixedSize(true);
        newslist.setLayoutManager(new LinearLayoutManager(this));



    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Newsfeeds,NewsViewHolder> firebaseRecyclerAdapter =new FirebaseRecyclerAdapter<Newsfeeds, NewsViewHolder>(
                Newsfeeds.class,
                R.layout.news_card,
                NewsViewHolder.class,
                getdatabase
        ) {

            @Override
            protected void populateViewHolder(NewsViewHolder viewHolder, Newsfeeds model, int position)
            {


                viewHolder.SetTitle(model.getTitle());
                viewHolder.SetDescription(model.getDescription());
                viewHolder.SetImage(getApplicationContext(),model.getImage());

            }
        };
        newslist.setAdapter(firebaseRecyclerAdapter);
    }
public  static class  NewsViewHolder extends RecyclerView.ViewHolder{
    View newsview;
    public NewsViewHolder(View itemView) {
        super(itemView);
        newsview = itemView;
    }
    public void SetTitle(String Title)
    {
        TextView post_title =(TextView)newsview.findViewById(R.id.news_title);
        post_title.setText(Title);
    }
public void SetDescription(String Description)
    {
    TextView post_desc =(TextView)newsview.findViewById(R.id.news_description);
    post_desc.setText(Description);
     }
public void SetImage(Context ctx,String image)
      {
          ImageView post_image = (ImageView)newsview.findViewById(R.id.news_image);
          Picasso.with(ctx).load(image).into(post_image);
      }

}





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_add) {
            Intent i1 =new Intent(MainActivity.this,login.class);
            startActivity(i1);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
