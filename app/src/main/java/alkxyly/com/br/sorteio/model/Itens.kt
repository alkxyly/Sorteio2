package alkxyly.com.br.sorteio.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class  Itens(var identificador: String, var nome: String,
                  var rg: String,
                  var cpf: String,
                  var valor: String,
                  var celular: String,
                  var endereco: String,
                  var dataVenda: String,
                  var dataRecebimento: String,
                  var pago: Boolean): Parcelable