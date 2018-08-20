package io.github.eufranio.ipai;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import io.github.eufranio.ipai.db.ProdDB;
import io.github.eufranio.ipai.model.Produto;

public class Formulario extends AppCompatActivity {

    private EditText etName, etDesc, etQtd;
    private Button btn_poli;
    private Produto editProd, prod;
    private ProdDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        prod = new Produto();
        this.db = new ProdDB(this);

        Intent intent = this.getIntent();
        this.editProd = (Produto) intent.getSerializableExtra("produtoEscolhido");

        this.etName = this.findViewById(R.id.etName);
        this.etDesc = this.findViewById(R.id.etDesc);
        this.etQtd = this.findViewById(R.id.etQtd);

        this.btn_poli = this.findViewById(R.id.btn_poli);

        if (editProd != null) {
            btn_poli.setText("Modificar");
            etName.setText(editProd.getName());
            etDesc.setText(editProd.getDesc());
            etQtd.setText(editProd.getQtd()+"");
            prod.setId(editProd.getId());
        } else {
            btn_poli.setText("Cadastrar");
        }

        btn_poli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prod.setName(etName.getText().toString());
                prod.setDesc(etDesc.getText().toString());
                prod.setQtd(Integer.parseInt(etQtd.getText().toString()));

                if (btn_poli.getText().toString().equals("Cadastrar")) {
                    db.save(prod);
                    db.close();
                } else {
                    db.alter(prod);
                    db.close();
                }
            }
        });
    }
}
