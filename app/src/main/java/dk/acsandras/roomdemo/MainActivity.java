package dk.acsandras.roomdemo;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private static final String DATABASE_NAME = "movies_db";
    private MovieDatabase movieDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Saved movie", Snackbar.LENGTH_LONG)
                        .setAction("Save movie", null).show();
                //saveMovie();
                loadMovie();
            }
        });

        movieDatabase = Room.databaseBuilder(getApplicationContext(),
                MovieDatabase.class, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();
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

        return super.onOptionsItemSelected(item);
    }

    private void saveMovie() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Movies movie =new Movies();
                movie.setMovieId( "2");
                movie.setMovieName("The Prestige");
                movieDatabase.daoAccess().insertOnlySingleMovie(movie);
            }
        }) .start();
    }

    private void loadMovie() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Movies movie = movieDatabase.daoAccess().fetchOneMoviesbyMovieId(2);
                Log.d("MovieLoaded", movie.getMovieId() +  " " + movie.getMovieName() );
            }
        }) .start();
    }


}
