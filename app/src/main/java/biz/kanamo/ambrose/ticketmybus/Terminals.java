package biz.kanamo.ambrose.ticketmybus;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.infowindow.InfoWindow;

public class Terminals extends AppCompatActivity {


    IMapController mapController;
    LocationManager locationManager;
    GeoPoint currentLocation;
    Context ctx;
    GeoPoint me;
    MapView map;
    ImageView showme;
    Boolean gps_enabled, network_enabled;
    ListView terminalLists;
    String[] districts;
    TextView d_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //important! set your user agent to prevent getting banned from the osm servers
        ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        setContentView(R.layout.activity_terminals);
        getSupportActionBar().hide();

        districts = new String[]{
                "Gulu", "Kampala", "Kitgum", "Pader"
        };
        d_name = (TextView) findViewById(R.id.d_name);
        terminalLists = (ListView) findViewById(R.id.district_terminals);
        terminalLists.setAdapter(new ArrayAdapter<String>(ctx, android.R.layout.simple_list_item_1, districts));
        terminalLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        addMarker(new GeoPoint(2.773853,32.2993758));
                        d_name.setText("Gulu Bus Terminal");
                        break;
                    case 1:
                        addMarker(new GeoPoint(0.319548, 32.571028));
                        d_name.setText("Kampala Bus Terminal");
                        break;
                    case 2:
                        addMarker(new GeoPoint(3.298596, 32.880901));
                        d_name.setText("Kitgum Bus Terminal");
                        break;
                    case 3:
                        addMarker(new GeoPoint(2.882567, 33.088923));
                        d_name.setText("Pader Bus Terminal");
                        break;
                }
            }
        });
        showme = (ImageView) findViewById(R.id.showme);
        initmap();
    }

    private void initmap() {
        map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMultiTouchControls(true);
        mapController = map.getController();
        mapController.setZoom(17);
        //        2.773523,32.2993548 gulu, another one
        //        0.319548, 32.571028 kampala
        //        3.298596, 32.880901 kitgum bus park
        //        2.882567, 33.088923 pader bus park
        addMarker(new GeoPoint(2.773853,32.2993758));

        try {
            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }
        showme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                currentLocation = new GeoPoint(location.getLatitude(), location.getLongitude());
                addMarker(currentLocation);
            }
        });
    }

    public void addMarker(GeoPoint point){
        Marker startMarker = new Marker(map);
        startMarker.setPosition(point);
//        startMarker.setIcon(R.drawable.ic_launcher);
        startMarker.setAnchor(Marker.ANCHOR_CENTER, 1.0f);
        map.getOverlays().add(startMarker);
        startMarker.setTitle("Title of the marker");
        startMarker.setAnchor(Marker.ANCHOR_BOTTOM, Marker.ANCHOR_CENTER);
        mapController.animateTo(point);
    }
}
