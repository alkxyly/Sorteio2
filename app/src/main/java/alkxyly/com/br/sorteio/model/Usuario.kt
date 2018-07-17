package alkxyly.com.br.sorteio.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Usuario(var uuid: String,var tabela: Tabela): Parcelable