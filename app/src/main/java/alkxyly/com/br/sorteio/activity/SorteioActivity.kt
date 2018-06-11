package alkxyly.com.br.sorteio.activity

import aktecnologia.br.com.sorteio.adapter.TabelaAdapter
import aktecnologia.br.com.sorteio.domain.TabelaService
import alkxyly.com.br.sorteio.R
import alkxyly.com.br.sorteio.model.Tabela
import alkxyly.com.br.sorteio.util.MaskUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.EditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sorteio.*
import kotlinx.android.synthetic.main.dialog_customizado.*

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
       //val database : FirebaseDatabase
       // database = FirebaseDatabase.getInstance()
       //val myRef: DatabaseReference = database.getReference()
       // this.carregarTabela(myRef)

        recycleView.layoutManager = GridLayoutManager(this, 5) as RecyclerView.LayoutManager?
        recycleView.setHasFixedSize(true)
        recycleView.adapter = TabelaAdapter(this, tabela!!.itens){
            showCreateCategoryDialog(it.nome)

        }
    }
    fun showCreateCategoryDialog( nome: String ) {
        val context = this
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Dados do Cliente")

        // https://stackoverflow.com/questions/10695103/creating-custom-alertdialog-what-is-the-root-view
        // Seems ok to inflate view with null rootView
        val view = layoutInflater.inflate(R.layout.dialog_customizado, null)

        val nomeEditText = view.findViewById(R.id.nome) as EditText
       // cpf.addTextChangedListener(MaskUtil.insert(cpf,1))

        builder.setView(view);

        // set up the ok button
        builder.setPositiveButton(android.R.string.ok) { dialog, p1 ->
            val newCategory = nomeEditText.text
            var isValid = true
            if (newCategory.isBlank()) {
                nomeEditText.error = "Erro"
                isValid = false
            }

            if (isValid) {
             Log.d("TAG",nome)
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
        firebaseData.child("tabela").child(key).setValue(tabela)
    }

}
