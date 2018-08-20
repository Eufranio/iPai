package io.github.eufranio.ipai;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import io.github.eufranio.ipai.db.ProdDB;
import io.github.eufranio.ipai.model.Produto;

public class iPai extends AppCompatActivity {

    ListView lista;
    ProdDB db;
    List<Produto> produtos;
    Produto produto;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_pai);

        this.lista = this.findViewById(R.id.listview_produtos);

        this.lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Produto p = (Produto) parent.getItemAtPosition(position);
                Intent i = new Intent(iPai.this, Formulario.class);
                i.putExtra("produtoEscolhido", p);
                startActivity(i);
            }
        });

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
                produto = (Produto) adapter.getItemAtPosition(position);
                return false;
            }
        });

        lista.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                MenuItem menuDelete = menu.add("Deletar Este Produto");
                menuDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        ProdDB db = new ProdDB(iPai.this);
                        db.delete(produto);
                        db.close();
                        loadProd();
                        return true;
                    }
                });
            }
        });

        Button cadastrar = this.findViewById(R.id.btn_cadastrar);
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(iPai.this, Formulario.class);
                startActivity(i);
            }
        });
    }

    private void loadProd() {
        this.db = new ProdDB(this);
        this.produtos = this.db.getAll();
        this.db.close();

        if (this.produtos != null) {
            adapter = new ArrayAdapter<Produto>(this, android.R.layout.simple_list_item_1, this.produtos);
            this.lista.setAdapter(adapter);
        }
        //finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.loadProd();
    }
}
