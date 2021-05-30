package com.ozi.adresdefterim;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import de.hdodenhof.circleimageview.CircleImageView;

public class KisiDetayFragment extends Fragment {

    private KisiDetayFragmentDinleyicisi dinleyici;
    private long id = -1; //0 vermedik çünkü 0.indexdeki elemanı görmesin diye
    private TextView adSoyad,mail,telefon,adres;
    private CircleImageView profil;
    private Bitmap bitmap;

    public  interface KisiDetayFragmentDinleyicisi{

        //Bir kişi silindiğinde çağrılır.
        public void kisiSilindi();

        //Kişi düzenlenleme isteği yapıldığında çağrılır
        public  void kisiDuzenle(Bundle argument);

    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (savedInstanceState!=null) {
            id = savedInstanceState.getLong(MainActivity.ID);
        }else{
            if (getArguments()!=null){
                id = getArguments().getLong(MainActivity.ID);
            }
        }

        return inflater.inflate(R.layout.detay_fragment,container,false); //Detay fragmentini döner
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setHasOptionsMenu(true);

        adSoyad= (TextView) getActivity().findViewById(R.id.ad);
        mail= (TextView) getActivity().findViewById(R.id.mail);
        telefon= (TextView) getActivity().findViewById(R.id.telefon);
        adres= (TextView) getActivity().findViewById(R.id.adres);
        profil= (CircleImageView) getActivity().findViewById(R.id.profil_detay);
    }

    @Override
    public void onResume() {
        super.onResume();
        kisiDetayiniGoster(id); //kişi detayını gösteren metot
    }

    private void kisiDetayiniGoster(long id) {
        KisiModel model = new KisiModel();
        Veritabani veritabani=new Veritabani(getActivity());
        model=veritabani.getKisi(id);

        adSoyad.setText(model.getAd());
        adres.setText(model.getAdres());
        mail.setText(model.getMail());
        telefon.setText(model.getTelefon());

        if (model.getProfilFoto()!=null){
            byte[] bytes=model.getProfilFoto();
            bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            profil.setImageBitmap(bitmap);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(MainActivity.ID,id);

    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dinleyici= (KisiDetayFragment.KisiDetayFragmentDinleyicisi) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        dinleyici=null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.detay_menu,menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.sil:
                new AlertDialog.Builder(getActivity(),AlertDialog.THEME_HOLO_DARK)
                        .setTitle("Sil")
                        .setIcon(android.R.drawable.ic_delete)
                        .setMessage("Kişiyi silmek istediğinizden emin misiniz?")
                        .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Veritabani veritabani=new Veritabani(getActivity());
                                veritabani.sil(id);
                                dinleyici.kisiSilindi();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Hayır",null).show()

                ;

                return true;
            case R.id.duzenle:
                Bundle arguments=new Bundle();
                arguments.putLong(MainActivity.ID,id);
                arguments.putString("ad",adSoyad.getText().toString());
                arguments.putString("adres",adres.getText().toString());
                arguments.putString("telefon",telefon.getText().toString());
                arguments.putString("mail",mail.getText().toString());

                if (bitmap!=null)
                    arguments.putParcelable("resim",bitmap);

                dinleyici.kisiDuzenle(arguments);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

}
