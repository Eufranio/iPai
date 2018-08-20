package io.github.eufranio.ipai.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import io.github.eufranio.ipai.model.Produto;

public class ProdDB extends SQLiteOpenHelper {

    private static final String DB = "produtosDb";
    private static final int VER = 1;

    public ProdDB(Context ctx) {
        super(ctx, DB, null, VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE produtos(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "nome TEXT," +
                "descr TEXT," +
                "qtd INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS produtos";
        db.execSQL(query);
    }

    public void save(Produto p) {
        ContentValues c = new ContentValues();
        c.put("nome", p.getName());
        c.put("descr", p.getDesc());
        c.put("qtd", p.getQtd());

        this.getWritableDatabase().insert("produtos", null, c);
    }

    public void alter(Produto p) {
        ContentValues c = new ContentValues();
        c.put("nome", p.getName());
        c.put("descr", p.getDesc());
        c.put("qtd", p.getQtd());

        String[] args = {p.getId().toString()};

        this.getWritableDatabase().update("produtos", c, "id=?", args);
    }

    public void delete(Produto p) {
        String[] args = {p.getId().toString()};
        this.getWritableDatabase().delete("produtos", "id=?", args);
    }

    public List<Produto> getAll() {
        String[] columns = {"id", "nome", "descr", "qtd"};
        Cursor c = this.getWritableDatabase().query("produtos", columns, null, null, null, null, null, null);
        List<Produto> p = new ArrayList<>();

        while (c.moveToNext()) {
            Produto prod = new Produto();
            prod.setId(c.getLong(0));
            prod.setName(c.getString(1));
            prod.setDesc(c.getString(2));
            prod.setQtd(c.getInt(3));

            p.add(prod);
        }

        return p;
    }

}
