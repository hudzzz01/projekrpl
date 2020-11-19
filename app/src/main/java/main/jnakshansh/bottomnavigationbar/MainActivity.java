package main.jnakshansh.bottomnavigationbar;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //iniuntuk toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //buttombar
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                selectedFragment = Home_fragment.newInstance();
                                break;
                            case R.id.navigation_alarm:
                                selectedFragment = AlarmFragment.newInstance();
                                break;
                            case R.id.navigation_setting:
                                selectedFragment = FragmentSetting.newInstance();
                                break;
                            case R.id.navigation_exit:
                                selectedFragment = ExitFragment.newInstance();
                                break;
                            case R.id.navigation_mesjid:
                                selectedFragment=MasjidFragment.newInstance();
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });
    }

}
