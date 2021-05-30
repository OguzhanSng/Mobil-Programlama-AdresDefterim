package com.ozi.adresdefterim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class MainActivity extends AppCompatActivity implements
        KisiListesiFragment.KisiListesiFragmentDinleyicisi,
        KisiEkleDuzenleFragment.KisiEkleDuzenleFragmentDinleyicisi,
        KisiDetayFragment.KisiDetayFragmentDinleyicisi {

    private KisiListesiFragment kisiListesiFragment;
    public static final String ID="_id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String s=sharedPreferences.getString("tema","0");
        int tema=Integer.parseInt(s);
        switch (tema){
            case 0:setTheme(R.style.AppTheme); break;
            case 1:setTheme(R.style.AppThemeMavi); break;
            case 2:setTheme(R.style.AppThemeYesil); break;
            case 3:setTheme(R.style.AppThemePink); break;
            case 4:setTheme(R.style.AppThemeKahverengi); break;
            case 5:setTheme(R.style.AppThemeGri); break;

        }

        setContentView(R.layout.activity_main);

        if(savedInstanceState!=null) //Değişiklik olduysa alttaki kodlar çalışmaz tekrar fragment oluşmaması için(ekran döndürme vs. gibi)
            return;
        if(findViewById(R.id.container)!=null) {
            //Telefondur containeri var
            kisiListesiFragment=new KisiListesiFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.container,kisiListesiFragment);
            transaction.commit();

        }
    }

    @Override
    public void elemanSecildi(long id) {
        if(findViewById(R.id.container)!=null) {
            //Telefondur containeri var
            detayFragmentiniAc(id,R.id.container);
        }
        else{ //Tabletse
            getSupportFragmentManager().popBackStack();
            detayFragmentiniAc(id,R.id.sagPanelContainer);
        }
    }

    private void detayFragmentiniAc(long id, int containerId) {
        Bundle arguments = new Bundle();
        arguments.putLong(ID,id);

        KisiDetayFragment kisiDetayFragment=new KisiDetayFragment();
        kisiDetayFragment.setArguments(arguments);

        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(containerId,kisiDetayFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN); //Geçiş animasyonu
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void kisiEkle() {
        if (findViewById(R.id.container) != null) {
            //Telefon ise
            kisiEkleDuzenleFragmentiniAc(R.id.container,null);
        } else {
            //Tablet ise
            kisiEkleDuzenleFragmentiniAc(R.id.sagPanelContainer,null);
        }

    }

    @Override
    public void kisiDuzenle(Bundle arguments) {

        if (findViewById(R.id.container) != null) {
            //Telefon
            kisiEkleDuzenleFragmentiniAc(R.id.container,arguments);
        } else {
            //Tablet
            kisiEkleDuzenleFragmentiniAc(R.id.sagPanelContainer,arguments);
        }
    }

    private void kisiEkleDuzenleFragmentiniAc(int view_id, Bundle arguments) {

        KisiEkleDuzenleFragment kisiEkleDuzenle=new KisiEkleDuzenleFragment();

        if (arguments!=null)
            kisiEkleDuzenle.setArguments(arguments);

        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(view_id,kisiEkleDuzenle);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
        transaction.commit();

    }


    @Override
    public void kisiSilindi() {
        getSupportFragmentManager().popBackStack(); //Fragment silmemize yarıyor

        if(findViewById(R.id.container)==null) //Tablet
            kisiListesiFragment.guncelle();

    }



    @Override
    public void kisiEkleDuzenleIslemiYapildi(long id) {

        getSupportFragmentManager().popBackStack();  //Ekle veya düzenle fragmentini sil
        if(findViewById(R.id.container)==null){
            //Tablet ise

            getSupportFragmentManager().popBackStack(); //varsa detay fragmenti onu da sil
            detayFragmentiniAc(id,R.id.sagPanelContainer);
            kisiListesiFragment.guncelle();


        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(kisiListesiFragment==null){
            //Tablet ise
            kisiListesiFragment=(KisiListesiFragment) getSupportFragmentManager().
                    findFragmentById(R.id.kisiListesiFragment);
        }
    }
}