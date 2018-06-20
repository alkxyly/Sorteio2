package aktecnologia.br.com.sorteio.domain


import alkxyly.com.br.sorteio.model.Itens
import alkxyly.com.br.sorteio.model.Tabela
import android.content.Context
import android.util.Log
import com.google.firebase.database.*

class TabelaService{

    companion object {
        val tabela: Tabela?=null
        fun getTabela(context: Context):Tabela{
            val database : FirebaseDatabase
            database = FirebaseDatabase.getInstance()
            val myRef: DatabaseReference = database.getReference("tabela").child("-LE9kcLZ4moA1Z2ngquv")
            // Read from the database
            var table  : Tabela? = null
            val messageListener = object : ValueEventListener {

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.i("onData","Entrou")
                    if (dataSnapshot.exists()) {
                      //  val itens : List<Itens> = dataSnapshot.value as List<Itens>

                      // Log.i("Lista",itens.get(0).toString())
                }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Failed to read value
                }
            }

         //   myRef!!.addValueEventListener(messageListener)

            val itens = mutableListOf<Itens>()


            for(i in 1..100){
                val item = Itens(i.toString(),"Nome "+i,"78789789",
                        "789789798","78,00","7979797979",
                        "Rua exemplo X","12/03/2015","12/03/2012",true)
                itens.add(item)
            }
            val tabela = Tabela("",itens)
            return tabela
        }
    }

}