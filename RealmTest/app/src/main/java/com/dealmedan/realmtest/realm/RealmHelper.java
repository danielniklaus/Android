package com.dealmedan.realmtest.realm;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.dealmedan.realmtest.model.ArticleModel;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by daniel on 1/25/2017.
 */

public class RealmHelper {
    private static final String TAG = "RealmHelper";

    private Realm realm;
    private RealmResults<Article>realmResults;
    public Context context;

    public RealmHelper(Context context){
        realm = Realm.getInstance(context);
        this.context = context;
    }

//    menambah data
    public void addArticle(String title, String description){
        Article article = new Article();
        article.setId((int)(System.currentTimeMillis()/1000));
        article.setTitle(title);
        article.setDescription(description);

        realm.beginTransaction();
        realm.copyToRealm(article);
        realm.commitTransaction();

        showLog("Added ; " + title);
        showToast(title + "berhasil disimpan.");
    }
    //mencari semua artikel

    public ArrayList<ArticleModel> findAllArticle(){
        ArrayList<ArticleModel>data = new ArrayList<>();

        realmResults = realm.where(Article.class).findAll();
        realmResults.sort("id", Sort.DESCENDING);
        if (realmResults.size()>0){
            showLog("Size :" + realmResults.size());
            for (int i = 0; i < realmResults.size(); i++){
                String title, description;
                int id = realmResults.get(i).getId();
                title = realmResults.get(i).getTitle();
                description = realmResults.get(i).getDescription();
                data.add(new ArticleModel(id,title,description));
            }
        }else {
            showLog("Size : 0");
            showToast("Database Kosong!");
        }
        return data;
    }

    //update data
    public void updateArticle(int id, String title, String description){
        realm.beginTransaction();
        Article article = realm.where(Article.class).equalTo("id", id).findFirst();
        article.setTitle(title);
        article.setDescription(description);
        realm.commitTransaction();

        showLog("Updated : "+ title);

        showToast(title + "berhasil diupdate.");
    }

    //menghapus data berdasarkan id

    public void deleteData(int id){
        RealmResults<Article> dataResults = realm.where(Article.class).equalTo("id",id).findAll();
        realm.beginTransaction();
        dataResults.remove(0);
        dataResults.removeLast();
        dataResults.clear();
        realm.commitTransaction();

        showToast("Hapus data berhasil.");
    }

    private void showToast(String s) {
        Toast.makeText(context, s, Toast.LENGTH_LONG).show();
    }

    private void showLog(String s) {
        Log.d(TAG, s);
    }
}
