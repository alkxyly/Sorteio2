package alkxyly.com.br.sorteio.activity


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import alkxyly.com.br.sorteio.R
import alkxyly.com.br.sorteio.extensions.random
import alkxyly.com.br.sorteio.model.Itens
import alkxyly.com.br.sorteio.util.MaskUtil
import android.graphics.Color
import android.support.annotation.NonNull
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_dados_cliente.*
import kotlinx.android.synthetic.main.content_dados_cliente.*

class DadosClienteActivity : AppCompatActivity() {
    private var identificadorExcluir: String? = null
    private  var database1: FirebaseDatabase? = null
    private var myRef1: DatabaseReference? = null
    private var estaPago: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dados_cliente)
        toolbar.title = "Dados do Cliente"
        setSupportActionBar(toolbar)

        database1 = FirebaseDatabase.getInstance()
        myRef1 = database1?.getReference()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val args = intent.extras
        val nome = args.getString("nome")
        val rg = args.getString("rg")
        val cpf = args.getString("cpf")
        val identificador = args.getString("identificador")

        this.identificadorExcluir = identificador as String
        nomeDetalhe.text = nome.toString()
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
        valorDetalhe.text = args.getString("valor")
        vendedorDetalhe.text = args.getString("vendedor")

        estaPago = args.getBoolean("pago")
        if (!args.getBoolean("pago")) {
            valorDetalhe.setTextColor((Color.parseColor("#ff0000")))
        } else {
            btPagamento.text = "Pagamento Realizado com sucesso!"
            btPagamento.isClickable = false
        }

        btPagamento.setOnClickListener {

            val database1: FirebaseDatabase
            database1 = FirebaseDatabase.getInstance()
            val myRef1: DatabaseReference = database1.getReference()
            myRef1.child("tabela").child("-LFXNq8VFCw-X9H01svT").child("itens").child(identificador).child("pago").setValue(true)

            valorDetalhe.setTextColor((Color.parseColor("#ff669900")))
            btPagamento.text = "Pagamento Realizado com sucesso!"
            btPagamento.isClickable = false
            Toast.makeText(this, "Pagamento Realizado com Sucesso", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detalhes, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.deletar -> {
                showDeletar()
                return true
            }
            R.id.editar -> {
                Log.i("Editar","Clicou")
                val context = this
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Informa a Senha")

                // https://stackoverflow.com/questions/10695103/creating-custom-alertdialog-what-is-the-root-view
                // Seems ok to inflate view with null rootView
                val view = layoutInflater.inflate(R.layout.confirmacao, null)

                var categoryEditText = view.findViewById(R.id.senha) as EditText

                builder.setView(view);

                // set up the ok button
                builder.setPositiveButton(android.R.string.ok) { dialog, p1 ->
                    val newCategory = categoryEditText.text
                    var isValid = true
                    if (newCategory.isBlank()) {
                        categoryEditText.error = "Campo Vazio"
                        isValid = false
                    }

                    if (isValid) {
                        val valido: Boolean = categoryEditText.text.toString().equals("dedinho310318")
                        if (valido) {
                            shwEditar()
                        }else Toast.makeText(this,"Senha Inválida",Toast.LENGTH_LONG).show()
                    }else  Toast.makeText(this,"Senha Inválida",Toast.LENGTH_LONG).show()

                }

                builder.show()
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }

    }

    fun showDeletar() {
        val context = this
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Informa a Senha")

        // https://stackoverflow.com/questions/10695103/creating-custom-alertdialog-what-is-the-root-view
        // Seems ok to inflate view with null rootView
        val view = layoutInflater.inflate(R.layout.confirmacao, null)

        var categoryEditText = view.findViewById(R.id.senha) as EditText

        builder.setView(view);

        // set up the ok button
        builder.setPositiveButton(android.R.string.ok) { dialog, p1 ->
            val newCategory = categoryEditText.text
            var isValid = true
            if (newCategory.isBlank()) {
                categoryEditText.error = "Campo Vazio"
                isValid = false
            }

            if (isValid) {
                val valido: Boolean = categoryEditText.text.toString().equals("dedinho310318")

                if (valido) {
                    Log.i("Identificador", identificadorExcluir)

                    myRef1?.child("tabela")?.child("-LFXNq8VFCw-X9H01svT")?.child("itens")?.child(identificadorExcluir)?.child("nome")?.setValue("")
                    myRef1?.child("tabela")?.child("-LFXNq8VFCw-X9H01svT")?.child("itens")?.child(identificadorExcluir)?.child("bairro")?.setValue("")
                    myRef1?.child("tabela")?.child("-LFXNq8VFCw-X9H01svT")?.child("itens")?.child(identificadorExcluir)?.child("celular")?.setValue("")
                    myRef1?.child("tabela")?.child("-LFXNq8VFCw-X9H01svT")?.child("itens")?.child(identificadorExcluir)?.child("cidade")?.setValue("")
                    myRef1?.child("tabela")?.child("-LFXNq8VFCw-X9H01svT")?.child("itens")?.child(identificadorExcluir)?.child("complemento")?.setValue("")
                    myRef1?.child("tabela")?.child("-LFXNq8VFCw-X9H01svT")?.child("itens")?.child(identificadorExcluir)?.child("cpf")?.setValue("")
                    myRef1?.child("tabela")?.child("-LFXNq8VFCw-X9H01svT")?.child("itens")?.child(identificadorExcluir)?.child("dataRecebimento")?.setValue("")
                    myRef1?.child("tabela")?.child("-LFXNq8VFCw-X9H01svT")?.child("itens")?.child(identificadorExcluir)?.child("dataVenda")?.setValue("")
                    myRef1?.child("tabela")?.child("-LFXNq8VFCw-X9H01svT")?.child("itens")?.child(identificadorExcluir)?.child("estado")?.setValue("")
                    myRef1?.child("tabela")?.child("-LFXNq8VFCw-X9H01svT")?.child("itens")?.child(identificadorExcluir)?.child("numero")?.setValue("")
                    myRef1?.child("tabela")?.child("-LFXNq8VFCw-X9H01svT")?.child("itens")?.child(identificadorExcluir)?.child("pago")?.setValue(false)
                    myRef1?.child("tabela")?.child("-LFXNq8VFCw-X9H01svT")?.child("itens")?.child(identificadorExcluir)?.child("rg")?.setValue("")
                    myRef1?.child("tabela")?.child("-LFXNq8VFCw-X9H01svT")?.child("itens")?.child(identificadorExcluir)?.child("rua")?.setValue("")
                    myRef1?.child("tabela")?.child("-LFXNq8VFCw-X9H01svT")?.child("itens")?.child(identificadorExcluir)?.child("valor")?.setValue("")
                    myRef1?.child("tabela")?.child("-LFXNq8VFCw-X9H01svT")?.child("itens")?.child(identificadorExcluir)?.child("vendedor")?.setValue("")
                    this.finish()

                } else
                    Toast.makeText(context, "Senha Inválida", Toast.LENGTH_LONG).show()
                /*  val database1 : FirebaseDatabase
                 database1 = FirebaseDatabase.getInstance()
                 val myRef1: DatabaseReference = database1.getReference()
                 this.carregarTabela(myRef1)*/
            }

            if (isValid) {
                dialog.dismiss()
            }
        }

        builder.setNegativeButton(android.R.string.cancel) { dialog, p1 ->
            dialog.cancel()
        }

        builder.show();
    }

    fun shwEditar() {
        val context = this
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Dados do Cliente")

        // https://stackoverflow.com/questions/10695103/creating-custom-alertdialog-what-is-the-root-view
        // Seems ok to inflate view with null rootView
        val view = layoutInflater.inflate(R.layout.dialog_customizado, null)

        var nomeEditText = view.findViewById(R.id.nome) as EditText

        var rgEdiText = view.findViewById<EditText>(R.id.rgDialog) as EditText
        var cpfEditText = view.findViewById<EditText>(R.id.cpfDialog) as EditText
        var valorEditText = view.findViewById<EditText>(R.id.valorDialog) as EditText
        var celularEditText = view.findViewById<EditText>(R.id.telefoneDialog) as EditText
        var ruaEditText = view.findViewById<EditText>(R.id.ruaDialog) as EditText

        var numeroEditText = view.findViewById<EditText>(R.id.numeroDialog) as EditText
        var cidadeEditText = view.findViewById<EditText>(R.id.cidadeDialog) as EditText
        var bairroEditText = view.findViewById<EditText>(R.id.bairroDialog) as EditText
        var estadoEditText = view.findViewById<EditText>(R.id.estadoDialog) as EditText
        var complementoEditText = view.findViewById<EditText>(R.id.complementoDialog) as EditText

        var dataRecebimentoEditText = view.findViewById<EditText>(R.id.dataRecebimentoDialog) as EditText
        var dataVendaEditText = view.findViewById<EditText>(R.id.dataVendaDialog) as EditText
        var pagoDialogCheckBox = view.findViewById<CheckBox>(R.id.pagoDialog) as CheckBox

        nomeEditText.text = Editable.Factory.getInstance().newEditable(nomeDetalhe.text.toString())
        rgEdiText.text =  Editable.Factory.getInstance().newEditable(rgDetalhe.text.toString())
        cpfEditText.text = Editable.Factory.getInstance().newEditable(cpfDetalhe.text.toString())
        valorEditText.text = Editable.Factory.getInstance().newEditable(valorDetalhe.text.toString())
        celularEditText.text = Editable.Factory.getInstance().newEditable(celularDetalhe.text.toString())
        ruaEditText.text = Editable.Factory.getInstance().newEditable(ruaDetalhe.text.toString())
        numeroEditText.text = Editable.Factory.getInstance().newEditable(numeroDetalhe.text.toString())
        cidadeEditText.text = Editable.Factory.getInstance().newEditable(cidadeDetalhe.text.toString())
        bairroEditText.text = Editable.Factory.getInstance().newEditable(bairroDetalhe.text.toString())
        estadoEditText.text = Editable.Factory.getInstance().newEditable(estadoDetalhe.text.toString())
        complementoEditText.text = Editable.Factory.getInstance().newEditable(complementoDetalhe.text.toString())
        dataRecebimentoEditText.text = Editable.Factory.getInstance().newEditable(dataVencimentoDetalhe.text.toString())
        dataVendaEditText.text = Editable.Factory.getInstance().newEditable(dataVendaDetalhe.text.toString())

        pagoDialogCheckBox.visibility = View.INVISIBLE

        cpfEditText.addTextChangedListener(MaskUtil.insert(cpfEditText, 1))


        builder.setView(view)

        // set up the ok button
        builder.setPositiveButton(android.R.string.ok) { dialog, p1 ->
            val newCategory = nomeEditText.text
            // nomeEditText.text = item.nome
            var isValid = true
            if (newCategory.isBlank() || dataVendaEditText.text.isBlank() || valorEditText.text.isBlank()) {
                nomeEditText.error = "Não é possivel cadastrar"
                Toast.makeText(this, "Preencha nome, valor e data de venda", Toast.LENGTH_LONG).show()
                isValid = false
            }

            if (isValid) {


                myRef1!!.child("tabela").child("-LFXNq8VFCw-X9H01svT").child("itens").child(identificadorExcluir).child("nome").setValue(nomeEditText.text.toString())
                myRef1!!.child("tabela").child("-LFXNq8VFCw-X9H01svT").child("itens").child(identificadorExcluir).child("rg").setValue(rgEdiText.text.toString())
                myRef1!!.child("tabela").child("-LFXNq8VFCw-X9H01svT").child("itens").child(identificadorExcluir).child("cpf").setValue(cpfEditText.text.toString())
                myRef1!!.child("tabela").child("-LFXNq8VFCw-X9H01svT").child("itens").child(identificadorExcluir).child("valor").setValue(valorEditText.text.toString())
                myRef1!!.child("tabela").child("-LFXNq8VFCw-X9H01svT").child("itens").child(identificadorExcluir).child("celular").setValue(celularEditText.text.toString())

                myRef1!!.child("tabela").child("-LFXNq8VFCw-X9H01svT").child("itens").child(identificadorExcluir).child("rua").setValue(ruaEditText.text.toString())
                myRef1!!.child("tabela").child("-LFXNq8VFCw-X9H01svT").child("itens").child(identificadorExcluir).child("numero").setValue(numeroEditText.text.toString())
                myRef1!!.child("tabela").child("-LFXNq8VFCw-X9H01svT").child("itens").child(identificadorExcluir).child("cidade").setValue(cidadeEditText.text.toString())
                myRef1!!.child("tabela").child("-LFXNq8VFCw-X9H01svT").child("itens").child(identificadorExcluir).child("bairro").setValue(bairroEditText.text.toString())
                myRef1!!.child("tabela").child("-LFXNq8VFCw-X9H01svT").child("itens").child(identificadorExcluir).child("estado").setValue(estadoEditText.text.toString())
                myRef1!!.child("tabela").child("-LFXNq8VFCw-X9H01svT").child("itens").child(identificadorExcluir).child("complemento").setValue(complementoEditText.text.toString())

                myRef1!!.child("tabela").child("-LFXNq8VFCw-X9H01svT").child("itens").child(identificadorExcluir).child("dataRecebimento").setValue(dataRecebimentoEditText.text.toString())
                myRef1!!.child("tabela").child("-LFXNq8VFCw-X9H01svT").child("itens").child(identificadorExcluir).child("dataVenda").setValue(dataVendaEditText.text.toString())
                //myRef1!!.child("tabela").child("-LFXNq8VFCw-X9H01svT").child("itens").child(identificadorExcluir).child("pago").setValue(pagoDialogCheckBox.isChecked)

                                            nomeDetalhe.text =  nomeEditText.text.toString()
                rgDetalhe.text =  rgEdiText.text.toString()
                cpfDetalhe.text =  cpfEditText.text.toString()
                celularDetalhe.text =   celularEditText.text.toString()
                dataVencimentoDetalhe.text = dataRecebimentoEditText.text.toString()
                dataVendaDetalhe.text =  dataVendaEditText.text.toString()
                ruaDetalhe.text = ruaEditText.text.toString()
                numeroDetalhe.text =  numeroEditText.text.toString()
                bairroDetalhe.text = bairroEditText.text.toString()
                cidadeDetalhe.text =  cidadeEditText.text.toString()
                estadoDetalhe.text =  estadoEditText.text.toString()
                complementoDetalhe.text = complementoEditText.text.toString()
                valorDetalhe.text = valorEditText.text.toString()

              /**  if (!pagoDialogCheckBox.isChecked) {
                    valorDetalhe.setTextColor((Color.parseColor("#ff0000")))
                    btPagamento.text = "REALIZAR PAGAMENTO"
                    btPagamento.isClickable = true
                } else {
                    btPagamento.text = "Pagamento Realizado com sucesso!"
                    btPagamento.isClickable = false
                }*/
            }

            if (isValid) {
                dialog.dismiss()
            }

        }

        builder.show()

    }
}