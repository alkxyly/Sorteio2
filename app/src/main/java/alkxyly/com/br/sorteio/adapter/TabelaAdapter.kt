package aktecnologia.br.com.sorteio.adapter


import alkxyly.com.br.sorteio.R
import alkxyly.com.br.sorteio.model.Itens
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.tabela_adapter.view.*

class TabelaAdapter(private var context: Context,
                    val itens: List<Itens>,
                    val onClick: (Itens) -> Unit) : RecyclerView.Adapter<TabelaAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabelaAdapter.ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.tabela_adapter, parent, false)

        return ViewHolder(v);
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i("onBindViewHolder",itens.get(position).nome)
        holder.itemView.setOnClickListener{onClick(itens.get(position))}
        return holder.bind(itens.get(position))
    }
    override fun getItemCount() = itens.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(itens: Itens) = with(itemView){
            identificador.text = itens.identificador.toString() +"º"
            //nome.text = itens.nome

        }
    }
}