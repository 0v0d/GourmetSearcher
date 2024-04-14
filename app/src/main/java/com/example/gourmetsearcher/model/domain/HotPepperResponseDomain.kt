package com.example.gourmetsearcher.model.domain

import android.os.Parcelable
import com.example.gourmetsearcher.model.api.BudgetData
import com.example.gourmetsearcher.model.api.GenreData
import com.example.gourmetsearcher.model.api.HotPepperResponse
import com.example.gourmetsearcher.model.api.LargeAreaData
import com.example.gourmetsearcher.model.api.PCData
import com.example.gourmetsearcher.model.api.PhotoData
import com.example.gourmetsearcher.model.api.Results
import com.example.gourmetsearcher.model.api.Shops
import com.example.gourmetsearcher.model.api.SmallAreaData
import com.example.gourmetsearcher.model.api.Urls
import kotlinx.parcelize.Parcelize

/**
 * ホットペッパーグルメAPIのレスポンスデータクラス
 * @param results レストラン情報
 */
@Parcelize
data class HotPepperResponseDomain(
    val results: ResultsDomain
) : Parcelable

@Parcelize
data class ResultsDomain(
    val shops: List<ShopDomain>
) : Parcelable

@Parcelize
data class ShopDomain(
    val id: String,
    val name: String,
    val address: String,
    val station: String,
    val largeArea: LargeAreaDomain,
    val smallArea: SmallAreaDomain,
    val genre: GenreDomain,
    val budget: BudgetDomain,
    val access: String,
    val url: UrlDomain,
    val photo: PhotoDomain,
    val open: String,
    val close: String
) : Parcelable

@Parcelize
data class LargeAreaDomain(
    val name: String
) : Parcelable

@Parcelize
data class SmallAreaDomain(
    val name: String
) : Parcelable

@Parcelize
data class GenreDomain(
    val name: String
) : Parcelable

@Parcelize
data class BudgetDomain(
    val name: String
) : Parcelable

@Parcelize
data class UrlDomain(
    val pc: String
) : Parcelable

@Parcelize
data class PhotoDomain(
    val pc: PCDomain
) : Parcelable

@Parcelize
data class PCDomain(
    val l: String
) : Parcelable

/** APIレスポンスデータからドメインモデルへの変換関数
 * @return ドメインモデル
 */
fun HotPepperResponse.toDomain(): HotPepperResponseDomain {
    return HotPepperResponseDomain(
        results = this.results.toDomain()
    )
}

fun Results.toDomain(): ResultsDomain {
    return ResultsDomain(
        shops = this.shops.map { it.toDomain() }
    )
}

fun Shops.toDomain(): ShopDomain {
    return ShopDomain(
        id = this.id,
        name = this.name,
        address = this.address,
        station = this.station,
        largeArea = this.largeArea.toDomain(),
        smallArea = this.smallArea.toDomain(),
        genre = this.genre.toDomain(),
        budget = this.budget.toDomain(),
        access = this.access,
        url = this.url.toDomain(),
        photo = this.photo.toDomain(),
        open = this.open,
        close = this.close
    )
}

fun LargeAreaData.toDomain(): LargeAreaDomain {
    return LargeAreaDomain(
        name = this.name
    )
}

fun SmallAreaData.toDomain(): SmallAreaDomain {
    return SmallAreaDomain(
        name = this.name
    )
}

fun GenreData.toDomain(): GenreDomain {
    return GenreDomain(
        name = this.name
    )
}

fun BudgetData.toDomain(): BudgetDomain {
    return BudgetDomain(
        name = this.name
    )
}

fun Urls.toDomain(): UrlDomain {
    return UrlDomain(
        pc = this.pc
    )
}

fun PhotoData.toDomain(): PhotoDomain {
    return PhotoDomain(
        pc = this.pc.toDomain()
    )
}

fun PCData.toDomain(): PCDomain {
    return PCDomain(
        l = this.l
    )
}