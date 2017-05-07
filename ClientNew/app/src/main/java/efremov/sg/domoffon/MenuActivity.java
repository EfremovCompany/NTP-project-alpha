package efremov.sg.domoffon;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener {
    protected static final String LOG_TAG = "my_tag";
    TabHost.TabSpec tabSpec;
    public String secret;
    public int user_id = 0;
    public String name;
    public String surname;
    public String patronymic;
    public String cdek;
    public String addr;
    public String phone;

    private ListView lvP;
    private ListView lvA;
    private ProductListAdapter adapterP;
    private ProductListAdapter adapterA;
    private List<Product> mProductListA;
    private List<Product> mProductList;
    private SwipeRefreshLayout mSwipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        intent.getIntExtra("response", user_id);
        //secret = intent.getStringExtra("secret");
        name = intent.getStringExtra("name");
        surname = intent.getStringExtra("surname");
        patronymic = intent.getStringExtra("patronymic");
        cdek = intent.getStringExtra("cdek");
        addr = intent.getStringExtra("addr");
        phone = intent.getStringExtra("phone");

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView  = (View) navigationView.inflateHeaderView(R.layout.nav_header_menu);
        TextView nameTxt = (TextView) headerView.findViewById(R.id.textViewNameUser);
        TextView surTxt = (TextView) headerView.findViewById(R.id.textViewSurUser);
        TextView patTxt = (TextView) headerView.findViewById(R.id.textViewPatUser);
        nameTxt.setText(name);
        surTxt.setText(surname);
        patTxt.setText(patronymic);
        TabHost tabHost = (TabHost) findViewById(R.id.tabhost);
        // инициализация
        tabHost.setup();

        tabSpec = tabHost.newTabSpec("tag1");
        tabSpec.setIndicator("Спец предложение");
        tabSpec.setContent(R.id.tab1);
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setIndicator("Товары");
        tabSpec.setContent(R.id.tab2);
        tabHost.addTab(tabSpec);

        // логгируем переключение вкладок
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String tabId) {
                Log.d(LOG_TAG, "tabId = " + tabId);

            }
        });
        //Товары
        lvP = (ListView)findViewById(R.id.listview_product);
        mProductList = new ArrayList<>();

        mProductList.add(new Product(0,"text", "des", 12, 12));
        mProductList.add(new Product(1,"text1", "des12", 10, 10));
        mProductList.add(new Product(2,"text2", "ds", 1, 12));

        adapterP = new ProductListAdapter(getApplicationContext(), mProductList);
        lvP.setAdapter(adapterP);
        //Предложения
        lvA = (ListView)findViewById(R.id.listview_a);
        mProductListA = new ArrayList<>();

        mProductListA.add(new Product(0,"text", "des", 12, 1));
        mProductListA.add(new Product(1,"text1", "des12", 10, 1));
        mProductListA.add(new Product(2,"text2", "ds", 1, 1));

        adapterA = new ProductListAdapter(getApplicationContext(), mProductListA);
        lvA.setAdapter(adapterA);

        //mSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_container);

        //Настраиваем выполнение OnRefreshListener для данной activity:
        mSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefresh.setOnRefreshListener(this);


        mSwipeRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        //mSwipeRefresh.setOnRefreshListener(this);
        //Настраиваем цветовую тему значка обновления, используя наши цвета:
        //mSwipeRefresh.setColorSchemeResources
        //        (R.color.primary_material_light_1, R.color.colorAccent,R.color.colorPrimaryDark);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Отменяем анимацию обновления
                mSwipeRefresh.setRefreshing(false);
                Random random = new Random();
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Пора покормить кота!", Toast.LENGTH_SHORT);
                toast.show();
            }
        }, 4000);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        // Handle navigation view item clicks here.
        int id = item.getItemId();

        findViewById(R.id.include_edit).setVisibility(View.INVISIBLE);
        findViewById(R.id.include_main).setVisibility(View.INVISIBLE);
        findViewById(R.id.include_my).setVisibility(View.INVISIBLE);
        findViewById(R.id.include_send).setVisibility(View.INVISIBLE);

        if (id == R.id.nav_spec) {
            findViewById(R.id.include_main).setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_my) {
            findViewById(R.id.include_my).setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_edit) {
            findViewById(R.id.include_edit).setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_send) {
            findViewById(R.id.include_send).setVisibility(View.VISIBLE);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
