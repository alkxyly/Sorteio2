package alkxyly.com.br.sorteio.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class  Itens(var identificador: Int, var nome: String, var flag: Boolean): Parcelable