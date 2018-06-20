package alkxyly.com.br.sorteio.activity
import aktecnologia.br.com.sorteio.adapter.TabelaAdapter
import aktecnologia.br.com.sorteio.domain.TabelaService
import alkxyly.com.br.sorteio.R
import alkxyly.com.br.sorteio.model.Itens
import alkxyly.com.br.sorteio.model.Tabela
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
class SorteioActivity : AppCompatActivity() {
    private var tabela : Tabela? = null


    companion object {
        val RC_SIGN_IN = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sorteio)

        this.tabela = TabelaService.getTabela(context = this)
        toolbar.setTitle("Sorteio")
        toolbar.setTitleTextColor(getColor(R.color.branco))
        setSupportActionBar(toolbar)

        //Inserir tabela no banco de dados
       /**val database : FirebaseDatabase
       database = FirebaseDatabase.getInstance()
       val myRef: DatabaseReference = database.getReference()
       this.carregarTabela(myRef)*/

      val database : FirebaseDatabase
        database = FirebaseDatabase.getInstance()
        val myRef: DatabaseReference = database.getReference("tabela").child("-LFS8WWxCISmdogFlDgX").child("itens")
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
                    var endereco: String? = null
                    var dataVenda: String? = null
                    var dataRecebimento: String? = null
                    var pago: Boolean

                    for(i in 0 .. 99) {
                        identificador  = dataSnapshot.child(i.toString()).child("identificador").value.toString()
                        nome = dataSnapshot.child(i.toString()).child("nome").value.toString()
                        rg = dataSnapshot.child(i.toString()).child("rg").value.toString()
                        cpf = dataSnapshot.child(i.toString()).child("cpf").value.toString()
                        valor = dataSnapshot.child(i.toString()).child("valor").value.toString()
                        celular = dataSnapshot.child(i.toString()).child("celular").value.toString()
                        endereco = dataSnapshot.child(i.toString()).child("endereco").value.toString()
                        dataVenda = dataSnapshot.child(i.toString()).child("dataVenda").value.toString()
                        dataRecebimento = dataSnapshot.child(i.toString()).child("dataRecebimento").value.toString()
                        pago = dataSnapshot.child(i.toString()).child("pago").value as Boolean

                        var item : Itens = Itens(identificador =identificador,nome = nome,rg = rg,cpf = cpf,valor = valor,celular = celular,endereco = endereco,dataVenda = dataVenda,dataRecebimento = dataRecebimento,pago = pago  )
                        Log.i("DADOS",item.toString())
                        list.add(item)
                    }


                }
                recycleView.adapter = TabelaAdapter(this@SorteioActivity, list){
                    if(it == null)
                         showCreateCategoryDialog()
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
                        params.putString("endereco",it.endereco)
                        params.putString("valor",it.valor)
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
    fun showCreateCategoryDialog( ) {
        val context = this
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Dados do Cliente")

        // https://stackoverflow.com/questions/10695103/creating-custom-alertdialog-what-is-the-root-view
        // Seems ok to inflate view with null rootView
        val view = layoutInflater.inflate(R.layout.dialog_customizado, null)

        var nomeEditText = view.findViewById(R.id.nome) as EditText

       // cpf.addTextChangedListener(MaskUtil.insert(cpf,1))

        builder.setView(view);

        // set up the ok button
        builder.setPositiveButton(android.R.string.ok) { dialog, p1 ->
            val newCategory = nomeEditText.text
           // nomeEditText.text = item.nome
            var isValid = true
            if (newCategory.isBlank()) {
                nomeEditText.error = "Erro"
                isValid = false
            }

            if (isValid) {
             Toast.makeText(this,"Cadastro Realizado com sucesso",Toast.LENGTH_SHORT).show()
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
        val key  = firebaseData.child("tabela").push().key
        tabela?.uid = key.toString()
        firebaseData.child("tabela").child(key).setValue(tabela)
    }

}
