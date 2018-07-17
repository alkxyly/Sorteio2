package alkxyly.com.br.sorteio.activity


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import alkxyly.com.br.sorteio.R
import alkxyly.com.br.sorteio.extensions.random
import android.graphics.Color
import android.support.annotation.NonNull
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_dados_cliente.*
import kotlinx.android.synthetic.main.content_dados_cliente.*

class DadosClienteActivity : AppCompatActivity() {
    private var identificadorExcluir: String? = null
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

        this.identificadorExcluir = identificador as String

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detalhes, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.deletar -> {
                Log.i("Identificador",identificadorExcluir)
                val database1 : FirebaseDatabase
                database1 = FirebaseDatabase.getInstance()
                val myRef1: DatabaseReference = database1.getReference()
                myRef1.child("tabela").child("-LFXNq8VFCw-X9H01svT").child("itens").child(identificadorExcluir).child("nome").setValue("")
                myRef1.child("tabela").child("-LFXNq8VFCw-X9H01svT").child("itens").child(identificadorExcluir).child("bairro").setValue("")
                myRef1.child("tabela").child("-LFXNq8VFCw-X9H01svT").child("itens").child(identificadorExcluir).child("celular").setValue("")
                myRef1.child("tabela").child("-LFXNq8VFCw-X9H01svT").child("itens").child(identificadorExcluir).child("cidade").setValue("")
                myRef1.child("tabela").child("-LFXNq8VFCw-X9H01svT").child("itens").child(identificadorExcluir).child("complemento").setValue("")
                myRef1.child("tabela").child("-LFXNq8VFCw-X9H01svT").child("itens").child(identificadorExcluir).child("cpf").setValue("")
                myRef1.child("tabela").child("-LFXNq8VFCw-X9H01svT").child("itens").child(identificadorExcluir).child("dataRecebimento").setValue("")
                myRef1.child("tabela").child("-LFXNq8VFCw-X9H01svT").child("itens").child(identificadorExcluir).child("dataVenda").setValue("")
                myRef1.child("tabela").child("-LFXNq8VFCw-X9H01svT").child("itens").child(identificadorExcluir).child("estado").setValue("")
                myRef1.child("tabela").child("-LFXNq8VFCw-X9H01svT").child("itens").child(identificadorExcluir).child("numero").setValue("")
                myRef1.child("tabela").child("-LFXNq8VFCw-X9H01svT").child("itens").child(identificadorExcluir).child("pago").setValue(false)
                myRef1.child("tabela").child("-LFXNq8VFCw-X9H01svT").child("itens").child(identificadorExcluir).child("rg").setValue("")
                myRef1.child("tabela").child("-LFXNq8VFCw-X9H01svT").child("itens").child(identificadorExcluir).child("rua").setValue("")
                myRef1.child("tabela").child("-LFXNq8VFCw-X9H01svT").child("itens").child(identificadorExcluir).child("valor").setValue("")
                myRef1.child("tabela").child("-LFXNq8VFCw-X9H01svT").child("itens").child(identificadorExcluir).child("vendedor").setValue("")
                this.finish()
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }

    }

}
