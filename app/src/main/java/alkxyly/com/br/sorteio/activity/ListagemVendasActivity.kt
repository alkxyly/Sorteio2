package alkxyly.com.br.sorteio.activity

import alkxyly.com.br.sorteio.R
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_sorteio.*

class ListagemVendasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listagem_vendas)

        toolbar.setTitle("Domingos Silva")
        toolbar.setSubtitle("Listagem de vendas")
        // toolbar.setTitleTextColor(getColor(R.color.branco))
        setSupportActionBar(toolbar)
    }
}
