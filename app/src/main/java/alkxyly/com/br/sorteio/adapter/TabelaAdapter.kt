package aktecnologia.br.com.sorteio.adapter


import alkxyly.com.br.sorteio.R
import alkxyly.com.br.sorteio.model.Itens
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.tabela_adapter.view.*

class TabelaAdapter(private var context: Context,
                    val itens: List<Itens>,
                    val onClick: (Itens) -> Unit) : RecyclerView.Adapter<TabelaAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabelaAdapter.ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.tabela_adapter, parent, false)

        return ViewHolder(v);
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val context = holder.itemView.context
       val item = itens[position]
        Log.i("OnBind",item.pago.toString())

        if(item.identificador.toInt() in 0..9)
            holder.tIdentificador.text = "0"+item.identificador.toString()
        else  holder.tIdentificador.text = item.identificador.toString()

        if(!item.nome.equals("")){
            holder.tIdentificador.setTextColor((Color.parseColor("#ff0000")))
        }else   holder.tIdentificador.setTextColor((Color.parseColor("#008000")))

        if(item.pago){
            holder.tIdentificador.setTextColor((Color.parseColor("#0000FF")))
        }

        return holder.itemView.setOnClickListener{onClick(item)}
    }
    override fun getItemCount() = itens.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tIdentificador : TextView

        init {
            tIdentificador = itemView.findViewById<TextView>(R.id.identificador)
        }
    }
}