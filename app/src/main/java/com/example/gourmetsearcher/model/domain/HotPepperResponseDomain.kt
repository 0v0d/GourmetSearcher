package com.example.gourmetsearcher.model.domain

import android.os.Parcelable
import com.example.gourmetsearcher.model.api.BudgetData
import com.example.gourmetsearcher.model.api.GenreData
import com.example.gourmetsearcher.model.api.LargeAreaData
import com.example.gourmetsearcher.model.api.PCData
import com.example.gourmetsearcher.model.api.PhotoData
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
    val shops: List<ShopsDomain>
) : Parcelable

@Parcelize
data class ShopsDomain(
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
fun Shops.toDomain() = ShopsDomain(
    id,
    name,
    address,
    station,
    largeArea.toDomain(),
    smallArea.toDomain(),
    genre.toDomain(),
    budget.toDomain(),
    access,
    url.toDomain(),
    photo.toDomain(),
    open,
    close
)

fun LargeAreaData.toDomain() = LargeAreaDomain(name)

fun SmallAreaData.toDomain() = SmallAreaDomain(name)

fun GenreData.toDomain() = GenreDomain(name)

fun BudgetData.toDomain() = BudgetDomain(name)

fun Urls.toDomain() = UrlDomain(pc)

fun PhotoData.toDomain() = PhotoDomain(pc.toDomain())

fun PCData.toDomain() = PCDomain(l)