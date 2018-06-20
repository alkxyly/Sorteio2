package alkxyly.com.br.sorteio.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import alkxyly.com.br.sorteio.R

import kotlinx.android.synthetic.main.activity_dados_cliente.*
import kotlinx.android.synthetic.main.content_dados_cliente.*

class DadosClienteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dados_cliente)
        toolbar.title = "Dados do Cliente"
        setSupportActionBar(toolbar)



        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val args = intent.extras
        val nome = args.getString("nome")
        val rg = args.getString("rg")
        nomeDetalhe.text =  nome.toString()
        rgDetalhe.text = rg
        val cpf = args.getString("cpf")
        cpfDetalhe.text = cpf
        celularDetalhe.text = args.getString("celular")
        dataVencimentoDetalhe.text = args.getString("dataRecebimento")
        dataVendaDetalhe.text = args.getString("dataVenda")
        enderecoDetalhe.text = args.getString("endereco")
        valorDetalhe.text = args.getString("valor")
    }

}
