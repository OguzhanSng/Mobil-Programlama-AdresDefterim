package com.ozi.adresdefterim;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import java.util.List;

public class CustomAdapter extends BaseAdapter {
    private Context context;
    private List<KisiModel> modelList;

    public CustomAdapter(Context context, List<KisiModel> modelList) {
        this.context = context;
        this.modelList = modelList;

    }

    @Override
    public int getCount() {
        if (modelList!=null) //Kayıt varsa
            return modelList.size();

        return 0; //Kayıt yoksa
    }

    @Override
    public Object getItem(int position) {
        if (modelList!=null)
            return modelList.get(position);

        return null;
    }

    @Override
    public long getItemId(int position) {
        if (modelList!=null)
            return modelList.get(position).getId();

        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (modelList==null) //Kayıt yoksa
            return null;

        LinearLayout container= (LinearLayout) ((Activity)context).getLayoutInflater().inflate(R.layout.custom_listview,null);

        ImageView profil= (ImageView) container.findViewById(R.id.profil_custom_list); //Bilgileri getirme
        TextView adSoyad= (TextView) container.findViewById(R.id.adCustomList); //Bilgileri getirme
        TextView mail= (TextView) container.findViewById(R.id.mailCustomList); //Bilgileri getirme
        TextView telefon= (TextView) container.findViewById(R.id.telefonCustomList); //Bilgileri getirme

        KisiModel model = modelList.get(position); //O anki elemanı almamızı sağlıyor

        adSoyad.setText(model.getAd());
        mail.setText(model.getMail());
        telefon.setText(model.getTelefon());

        if (model.getProfilFoto()!=null){ //Profil fotoğrafı varsa

            byte [] bytes = model.getProfilFoto();
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            profil.setImageBitmap(bitmap);
        }

        return container;
    }
}
