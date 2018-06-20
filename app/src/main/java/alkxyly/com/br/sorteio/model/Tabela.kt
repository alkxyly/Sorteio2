package alkxyly.com.br.sorteio.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tabela(var uid: String,var itens: List<Itens>):Parcelable