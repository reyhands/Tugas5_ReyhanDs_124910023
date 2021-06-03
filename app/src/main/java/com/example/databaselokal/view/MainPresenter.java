package com.example.databaselokal.view;

import android.os.AsyncTask;
import android.util.Log;

import com.example.databaselokal.entitas.AppDatabase;
import com.example.databaselokal.entitas.DataLamaran;

import java.util.List;

public class MainPresenter implements MainContact.presenter {
    private MainContact.view view;

    public MainPresenter(MainContact.view view) {
        this.view = view;
    }

    class InsertData extends AsyncTask<Void,Void,Long>{
        private AppDatabase appDatabase;
        private DataLamaran dataLamaran;

        public InsertData(AppDatabase appDatabase, DataLamaran dataLamaran) {
            this.appDatabase = appDatabase;
            this.dataLamaran = dataLamaran;
        }

        @Override
        protected Long doInBackground(Void... voids) {
            return appDatabase.dao().insertData(dataLamaran);
        }

        protected void onPostExecute(Long along){
            super.onPostExecute(along);
            view.successAdd();
        }
    }
    @Override
    public void insertData(String nama, String lahir,String hp, AppDatabase database) {
        final DataLamaran dataLamaran = new DataLamaran();
        dataLamaran.setNama(nama);
        dataLamaran.setLahir(lahir);
        dataLamaran.setHp(hp);
        new InsertData(database,dataLamaran).execute();
    }

    @Override
    public void readData(AppDatabase database) {
        List<DataLamaran> list;
        list = database.dao().getData();
        view.getData(list);
    }

    class EditData extends AsyncTask<Void,Void,Integer>{
        private AppDatabase appDatabase;
        private DataLamaran dataLamaran;

        public EditData(AppDatabase appDatabase, DataLamaran dataLamaran) {
            this.appDatabase = appDatabase;
            this.dataLamaran = dataLamaran;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            return appDatabase.dao().updateData(dataLamaran);
        }

        protected void onPostExecute(Integer integer){
            super.onPostExecute(integer);
            Log.d("integer db","onPostExecute : "+integer);
            view.successAdd();
        }
    }
    @Override
    public void editData(String nama, String lahir,  String hp, int id, AppDatabase database) {
        final DataLamaran dataLamaran = new DataLamaran();
        dataLamaran.setNama(nama);
        dataLamaran.setLahir(lahir);
        dataLamaran.setHp(hp);

        dataLamaran.setId(id);
        new EditData(database,dataLamaran).execute();
    }

    class DeleteData extends AsyncTask<Void,Void,Long>{
        private AppDatabase appDatabase;
        private DataLamaran dataLamaran;

        public DeleteData(AppDatabase appDatabase, DataLamaran dataLamaran) {
            this.appDatabase = appDatabase;
            this.dataLamaran = dataLamaran;
        }

        @Override
        protected Long doInBackground(Void... voids) {
            appDatabase.dao().deleteData(dataLamaran);
            return null;
        }

        protected void onPostExecute(Long along){
            super.onPostExecute(along);
            view.successDelete();
        }
    }
    @Override
    public void deleteData(DataLamaran dataLamaran, AppDatabase database) {
        new DeleteData(database, dataLamaran).execute();
    }
}
