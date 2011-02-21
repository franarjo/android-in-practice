package com.manning.aip.brewmap;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;
import com.manning.aip.brewmap.model.Pub;

public class PubOverlay extends ItemizedOverlay<OverlayItem> {

   private List<Pub> pubs;
   private Context context;

   public PubOverlay(Context context, List<Pub> pubs, Drawable marker) {
      super(boundCenterBottom(marker));
      this.context = context;
      this.pubs = pubs;
      populate();
   }

   @Override
   protected OverlayItem createItem(int i) {
      Pub pub = pubs.get(i);
      // GeoPoint uses lat/long in microdegrees format (1e6)
      GeoPoint point = new GeoPoint((int) (pub.getLatitude() * 1e6), (int) (pub.getLongitude() * 1e6));
      return new OverlayItem(point, pub.getName(), "TODO snippet");
   }  

   @Override
   public boolean onTap(final int index) {
      Pub pub = pubs.get(index);
      AlertDialog.Builder builder = new AlertDialog.Builder(context);
      builder.setTitle("Pub")
               .setMessage(
                        pub.getName() + "\nLatitude:" + pub.getLatitude() + "\nLongitude:" + +pub.getLongitude()
                                 + "\nVisit the pub detail page for more info?").setCancelable(true)
               .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                  public void onClick(DialogInterface dialog, int id) {
                     Intent i = new Intent(context, PubDetails.class);
                     i.putExtra("PUB_INDEX", index);
                     context.startActivity(i);
                  }
               }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                  public void onClick(DialogInterface dialog, int id) {
                     dialog.cancel();
                  }
               });
      AlertDialog alert = builder.create();
      alert.show();

      return true; // we'll handle the event here (true) not pass to another overlay (false)
   }

   @Override
   public int size() {
      return pubs.size();
   }

}