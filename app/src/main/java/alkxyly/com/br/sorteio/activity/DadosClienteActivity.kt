package alkxyly.com.br.sorteio.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import alkxyly.com.br.sorteio.R
import android.graphics.Color
import android.view.View
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

import kotlinx.android.synthetic.main.activity_dados_cliente.*
import kotlinx.android.synthetic.main.content_dados_cliente.*
import kotlinx.android.synthetic.main.dialog_customizado.*

class DadosClienteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dados_cliente)
        toolbar.title = "Dados do Cliente"
        setSupportActionBar(toolbar)




        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val args = intent.extras
        val nome = args.getString("nome")
        val rg = args.getString("rg")
        val cpf = args.getString("cpf")
        val identificador = args.getString("identificador")

        nomeDetalhe.text =  nome.toString()
        rgDetalhe.text = rg
        cpfDetalhe.text = cpf
        celularDetalhe.text = args.getString("celular")
        dataVencimentoDetalhe.text = args.getString("dataRecebimento")
        dataVendaDetalhe.text = args.getString("dataVenda")
        ruaDetalhe.text = args.getString("rua")
        numeroDetalhe.text = args.getString("numero")
        bairroDetalhe.text = args.getString("bairro")
        cidadeDetalhe.text = args.getString("cidade")
        estadoDetalhe.text = args.getString("estado")
        complementoDetalhe.text = args.getString("complemento")
        valorDetalhe.text = "R$ "+args.getString("valor")
        vendedorDetalhe.text = args.getString("vendedor")

        if(!args.getBoolean("pago")){
            valorDetalhe.setTextColor((Color.parseColor("#ff0000")))
        }else {
            btPagamento.text = "Pagamento Realizado com sucesso!"
            btPagamento.isClickable = false
        }

        btPagamento.setOnClickListener{

            val database1 : FirebaseDatabase
            database1 = FirebaseDatabase.getInstance()
            val myRef1: DatabaseReference = database1.getReference()
            myRef1.child("tabela").child("-LFXNq8VFCw-X9H01svT").child("itens").child(identificador).child("pago").setValue(true)

            valorDetalhe.setTextColor((Color.parseColor("#ff669900")))
            btPagamento.text = "Pagamento Realizado com sucesso!"
            btPagamento.isClickable = false
            Toast.makeText(this,"Pagamento Realizado com Sucesso",Toast.LENGTH_LONG).show()
        }
    }

}
