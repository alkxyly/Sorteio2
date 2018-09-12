package alkxyly.com.br.sorteio.activity
import aktecnologia.br.com.sorteio.adapter.TabelaAdapter
import aktecnologia.br.com.sorteio.domain.TabelaService
import alkxyly.com.br.sorteio.MainActivity
import alkxyly.com.br.sorteio.R
import alkxyly.com.br.sorteio.extensions.random
import alkxyly.com.br.sorteio.model.Itens
import alkxyly.com.br.sorteio.model.Tabela
import alkxyly.com.br.sorteio.model.Usuario
import alkxyly.com.br.sorteio.util.MaskUtil

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_sorteio.*
import kotlinx.android.synthetic.main.dialog_customizado.*
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import android.widget.CheckBox
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog
import com.google.android.gms.auth.api.Auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SorteioActivity : AppCompatActivity() {
    private var tabela : Tabela? = null
    private var mDatabase: DatabaseReference? = null
    private var mMessageReference: DatabaseReference? = null
// ...

    var user: FirebaseUser? = null
    var firebaseAuth : FirebaseAuth? = null

    companion object {
        val RC_SIGN_IN = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sorteio)

        this.tabela = TabelaService.getTabela(context = this)
        toolbar.setTitle("Domingos Silva")
        toolbar.setSubtitle("Sorteio")
       // toolbar.setTitleTextColor(getColor(R.color.branco))
        setSupportActionBar(toolbar)

        this.firebaseAuth = FirebaseAuth.getInstance()
        user = firebaseAuth?.currentUser

        //Inserir tabela no banco de dados
       /**val database1 : FirebaseDatabase
       database1 = FirebaseDatabase.getInstance()
       val myRef1: DatabaseReference = database1.getReference()
       this.carregarTabela(myRef1)*/

      val database : FirebaseDatabase
        database = FirebaseDatabase.getInstance()
        val myRef: DatabaseReference = database.getReference("tabela").child("-LFXNq8VFCw-X9H01svT").child("itens")
        // Read from the database
        var table  : Tabela? = null

        val messageListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.i("onData","Entrou")
                val list = ArrayList<Itens>()
                if (dataSnapshot.exists()) {
                    var identificador : String?= null
                    var nome: String? = null
                    var rg: String? = null
                    var cpf: String? = null
                    var valor: String? = null
                    var celular: String? = null
                    var rua: String? = null
                    var numero: String? = null
                    var cidade: String? = null
                    var bairro: String? = null
                    var estado: String? = null
                    var complemento: String? = null
                    var dataVenda: String? = null
                    var dataRecebimento: String? = null
                    var pago: Boolean

                    var vendedor: String? = null
                    for(i in 0 .. 99) {
                        identificador  = dataSnapshot.child(i.toString()).child("identificador").value.toString()
                        nome = dataSnapshot.child(i.toString()).child("nome").value.toString()
                        rg = dataSnapshot.child(i.toString()).child("rg").value.toString()
                        cpf = dataSnapshot.child(i.toString()).child("cpf").value.toString()
                        valor = dataSnapshot.child(i.toString()).child("valor").value.toString()
                        celular = dataSnapshot.child(i.toString()).child("celular").value.toString()

                        rua = dataSnapshot.child(i.toString()).child("rua").value.toString()
                        numero = dataSnapshot.child(i.toString()).child("numero").value.toString()
                        cidade = dataSnapshot.child(i.toString()).child("cidade").value.toString()
                        bairro = dataSnapshot.child(i.toString()).child("bairro").value.toString()
                        estado = dataSnapshot.child(i.toString()).child("estado").value.toString()
                        complemento = dataSnapshot.child(i.toString()).child("complemento").value.toString()

                        dataVenda = dataSnapshot.child(i.toString()).child("dataVenda").value.toString()
                        dataRecebimento = dataSnapshot.child(i.toString()).child("dataRecebimento").value.toString()
                        pago = dataSnapshot.child(i.toString()).child("pago").value as Boolean
                        vendedor =  dataSnapshot.child(i.toString()).child("vendedor").value as String

                        var item : Itens = Itens(identificador =identificador,nome = nome,vendedor = vendedor,rg = rg,
                                                cpf = cpf,valor = valor,celular = celular,rua = rua,
                                                numero = numero,cidade = cidade,bairro = bairro,estado = estado,
                                                complemento = complemento,dataVenda = dataVenda,
                                                dataRecebimento = dataRecebimento,pago = pago  )

                        Log.i("DADOS",item.toString())
                        list.add(item)
                    }


                }
                recycleView.adapter = TabelaAdapter(this@SorteioActivity, list){
                    if(it.nome.equals(""))
                         showCreateCategoryDialog(it)
                    else{
                        val intent = Intent(baseContext, DadosClienteActivity::class.java)
                        val params = Bundle()
                        Log.i("Dados",it.toString())
                        params.putString("nome",it.nome)
                        params.putString("rg",it.rg)
                        params.putString("cpf",it.cpf)
                        params.putString("celular",it.celular)
                        params.putString("dataVenda",it.dataVenda)
                        params.putString("dataRecebimento",it.dataRecebimento)
                        params.putString("valor",it.valor)
                        params.putBoolean("pago",it.pago)
                        params.putString("identificador",it.identificador)
                        params.putString("rua",it.rua)
                        params.putString("numero",it.numero)
                        params.putString("estado",it.estado)
                        params.putString("cidade",it.cidade)
                        params.putString("bairro",it.bairro)
                        params.putString("complemento",it.complemento)
                        params.putString("vendedor",it.vendedor)
                       // params.putString("nome",it.nome)
                        intent.putExtras(params)
                        startActivity(intent)

                    }

                }
            }


            override fun onCancelled(databaseError: DatabaseError) {
                // Failed to read value
            }
        }
           myRef!!.addValueEventListener(messageListener)

        recycleView.layoutManager = GridLayoutManager(this, 5) as RecyclerView.LayoutManager?
        recycleView.setHasFixedSize(true)
       // Log.i("TABELA",tabela?.itens?.get(0)?.nome)

    }
    fun showCreateCategoryDialog(item: Itens ) {
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
        var cidadeEditText = view.findViewById<EditText>(R.id.cidadeDialog)  as EditText
        var bairroEditText = view.findViewById<EditText>(R.id.bairroDialog) as EditText
        var estadoEditText = view.findViewById<EditText>(R.id.estadoDialog) as EditText
        var complementoEditText = view.findViewById<EditText>(R.id.complementoDialog) as EditText

        var dataRecebimentoEditText = view.findViewById<EditText>(R.id.dataRecebimentoDialog) as EditText
        var dataVendaEditText = view.findViewById<EditText>(R.id.dataVendaDialog) as EditText
        var pagoDialogCheckBox = view.findViewById<CheckBox>(R.id.pagoDialog) as CheckBox
        cpfEditText.addTextChangedListener(MaskUtil.insert(cpfEditText,1))

        builder.setView(view);

        // set up the ok button
        builder.setPositiveButton(android.R.string.ok) { dialog, p1 ->
            val newCategory = nomeEditText.text
           // nomeEditText.text = item.nome
            var isValid = true
            if (newCategory.isBlank() || dataVendaEditText.text.isBlank() || valorEditText.text.isBlank()) {
                nomeEditText.error = "Não é possivel cadastrar"
                Toast.makeText(this,"Preencha nome, valor e data de venda",Toast.LENGTH_LONG).show()
                isValid = false
            }

            if (isValid) {
                mDatabase = FirebaseDatabase.getInstance().reference
                val vendedor  = this.user?.displayName
                mDatabase!!.child("tabela").child("-LFXNq8VFCw-X9H01svT").child("itens").child(item.identificador).child("nome").setValue(nomeEditText.text.toString())
                mDatabase!!.child("tabela").child("-LFXNq8VFCw-X9H01svT").child("itens").child(item.identificador).child("vendedor").setValue(vendedor.toString())
                mDatabase!!.child("tabela").child("-LFXNq8VFCw-X9H01svT").child("itens").child(item.identificador).child("rg").setValue(rgEdiText.text.toString())
                mDatabase!!.child("tabela").child("-LFXNq8VFCw-X9H01svT").child("itens").child(item.identificador).child("cpf").setValue(cpfEditText.text.toString())
                mDatabase!!.child("tabela").child("-LFXNq8VFCw-X9H01svT").child("itens").child(item.identificador).child("valor").setValue(valorEditText.text.toString())
                mDatabase!!.child("tabela").child("-LFXNq8VFCw-X9H01svT").child("itens").child(item.identificador).child("celular").setValue(celularEditText.text.toString())

                mDatabase!!.child("tabela").child("-LFXNq8VFCw-X9H01svT").child("itens").child(item.identificador).child("rua").setValue(ruaEditText.text.toString())
                mDatabase!!.child("tabela").child("-LFXNq8VFCw-X9H01svT").child("itens").child(item.identificador).child("numero").setValue(numeroEditText.text.toString())
                mDatabase!!.child("tabela").child("-LFXNq8VFCw-X9H01svT").child("itens").child(item.identificador).child("cidade").setValue(cidadeEditText.text.toString())
                mDatabase!!.child("tabela").child("-LFXNq8VFCw-X9H01svT").child("itens").child(item.identificador).child("bairro").setValue(bairroEditText.text.toString())
                mDatabase!!.child("tabela").child("-LFXNq8VFCw-X9H01svT").child("itens").child(item.identificador).child("estado").setValue(estadoEditText.text.toString())
                mDatabase!!.child("tabela").child("-LFXNq8VFCw-X9H01svT").child("itens").child(item.identificador).child("complemento").setValue(complementoEditText.text.toString())

                mDatabase!!.child("tabela").child("-LFXNq8VFCw-X9H01svT").child("itens").child(item.identificador).child("dataRecebimento").setValue(dataRecebimentoEditText.text.toString())
                mDatabase!!.child("tabela").child("-LFXNq8VFCw-X9H01svT").child("itens").child(item.identificador).child("dataVenda").setValue(dataVendaEditText.text.toString())
                mDatabase!!.child("tabela").child("-LFXNq8VFCw-X9H01svT").child("itens").child(item.identificador).child("pago").setValue(pagoDialogCheckBox.isChecked)

             Toast.makeText(this,pagoDialogCheckBox.isChecked.toString(),Toast.LENGTH_SHORT).show()
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

    fun carregarTabela(firebaseData: DatabaseReference){
        this.tabela = TabelaService.getTabela(context = this)
        val key  = this.user?.uid
        val usuario : Usuario = Usuario(key.toString(), tabela!!)
        firebaseData.child("tabela").child("-LFXNq8VFCw-X9H01svT").child("itens").setValue(tabela?.itens)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sorteio -> {
                showSortear()
               /** val vencedor: Int = (0..99).random()

                /*MaterialStyledDialog.Builder(this)
                        .setTitle("Parabéns!")
                        .setDescription("Número do Sorteio Gerado : ${vencedor.toString()}")
                        .setIcon(R.drawable.ic_refresh_black_24dp)
                        .show();*/
                MaterialStyledDialog.Builder(this)
                        .setTitle("Parabéns! Gerado : ${vencedor.toString()}")
                        .setDescription("**")
                        .setHeaderDrawable(R.drawable.logo)
                        .withDialogAnimation(true)
                        //.setHeaderDrawable(ContextCompat.getDrawable(this, R.drawable.heaer))
                        .show();*/
                return true
            }
            R.id.reiniciar ->{
             showCreateCategoryDialog()
               /** val database1 : FirebaseDatabase
                database1 = FirebaseDatabase.getInstance()
                val myRef1: DatabaseReference = database1.getReference()
                this.carregarTabela(myRef1)*/
                return true
            }
           R.id.vendas -> {
              //  FirebaseAuth.getInstance().signOut()
                var it: Intent
                it = Intent(this,ListagemVendasActivity::class.java)
                startActivity(it)



                //Toast.makeText(this,"${user?.email}", Toast.LENGTH_SHORT).show()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }

    }

    fun showCreateCategoryDialog() {
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
                val valido : Boolean = categoryEditText.text.toString().equals("dedinho310318")

                if(valido){
                    val database1 : FirebaseDatabase
                    database1 = FirebaseDatabase.getInstance()
                    val myRef1: DatabaseReference = database1.getReference()
                    this.carregarTabela(myRef1)
                }else
                    Toast.makeText(context,"Senha Inválida",Toast.LENGTH_LONG).show()
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


    fun showSortear() {
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
                val valido : Boolean = categoryEditText.text.toString().equals("dedinho310318")

                if(valido){
                    val vencedor: Int = (0..99).random()

                    /*MaterialStyledDialog.Builder(this)
                            .setTitle("Parabéns!")
                            .setDescription("Número do Sorteio Gerado : ${vencedor.toString()}")
                            .setIcon(R.drawable.ic_refresh_black_24dp)
                            .show();*/
                    MaterialStyledDialog.Builder(this)
                            .setTitle("Parabéns! Gerado : ${vencedor.toString()}")
                            .setDescription("**")
                            .setHeaderDrawable(R.drawable.logo)
                            .withDialogAnimation(true)
                            //.setHeaderDrawable(ContextCompat.getDrawable(this, R.drawable.heaer))
                            .show();
                }else
                    Toast.makeText(context,"Senha Inválida",Toast.LENGTH_LONG).show()
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

}
