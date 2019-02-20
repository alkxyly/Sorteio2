package alkxyly.com.br.sorteio.activity

import alkxyly.com.br.sorteio.R
import alkxyly.com.br.sorteio.model.Itens
import alkxyly.com.br.sorteio.model.Tabela
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_sorteio.*

class ListagemVendasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listagem_vendas)

        toolbar.setTitle("Domingos Silva")
        toolbar.setSubtitle("Listagem de vendas")
        // toolbar.setTitleTextColor(getColor(R.color.branco))
        setSupportActionBar(toolbar)


        val database : FirebaseDatabase
        database = FirebaseDatabase.getInstance()
        val myRef: DatabaseReference = database.getReference("tabela").child("-LFXNq8VFCw-X9H01svT").child("itens")
        // Read from the database
        var table  : Tabela? = null

        val messageListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.i("onData", "Entrou")
                val list = ArrayList<Itens>()
                if (dataSnapshot.exists()) {
                    var identificador: String? = null
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
                    for (i in 0..99) {
                        identificador = dataSnapshot.child(i.toString()).child("identificador").value.toString()
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
                        vendedor = dataSnapshot.child(i.toString()).child("vendedor").value as String

                        var item: Itens = Itens(identificador = identificador, nome = nome, vendedor = vendedor, rg = rg,
                                cpf = cpf, valor = valor, celular = celular, rua = rua,
                                numero = numero, cidade = cidade, bairro = bairro, estado = estado,
                                complemento = complemento, dataVenda = dataVenda,
                                dataRecebimento = dataRecebimento, pago = pago)

                        Log.i("Lista", item.toString())
                        if(item.vendedor.isNotBlank())
                            list.add(item)
                    }

                }

                Log.i("Lista","Tamanho "+list.size.toString())
            }
        }
        myRef!!.addValueEventListener(messageListener)

    }
}
