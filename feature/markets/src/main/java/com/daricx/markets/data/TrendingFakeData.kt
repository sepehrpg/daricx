package com.daricx.markets.data

import com.example.model.Trending

object TrendingFakeData {

    fun generateFakeTrending(): Trending {
        return Trending(
            categories = categories,
            coins = coins,
            nfts = nfts
        )
    }

    val categories: List<Trending.Category> = List(12) { i ->
        Trending.Category(
            coinsCount = 100 + i,
            data = Trending.Category.Data(
                marketCap = 1_000_000_000.0 + (i * 10_000_000),
                marketCapBtc = 30_000.0 + i * 100,
                marketCapChangePercentage24h = mapOf("usd" to (i - 5) * 0.5, "btc" to (i - 6) * 0.3),
                //sparkline = "https://dummyimage.com/600x200/${i}11/fff&text=Category+$i",
                sparkline = null,
                totalVolume = 200_000_000.0 + (i * 5_000_000),
                totalVolumeBtc = 5000.0 + i * 100
            ),
            id = 100 + i,
            marketCap1hChange = -1.0 + (i * 0.2),
            name = "Category $i",
            slug = "category-$i"
        )
    }

    val coins: List<Trending.Coin> = List(12) { i ->
        Trending.Coin(
            item = Trending.Coin.Item(
                coinId = 1000 + i,
                id = "coin-$i",
                name = "Coin $i",
                symbol = "C$i",
                slug = "coin-$i",
                marketCapRank = i + 1,
                score = i,
                priceBtc = 0.01 * (i + 1),
//                thumb = "https://dummyimage.com/64x64/0${i}0/fff&text=C$i",
//                small = "https://dummyimage.com/32x32/0${i}0/fff&text=S$i",
//                large = "https://dummyimage.com/128x128/0${i}0/fff&text=L$i",
                thumb = null,
                small = null,
                large = null,
                data = Trending.Coin.Item.Data(
                    price = 1000.0 + (i * 50),
                    priceBtc = (0.01 * (i + 1)).toString(),
                    marketCap = (1_000_000_000 + i * 100_000_000).toString(),
                    marketCapBtc = (100_000 + i * 5000).toString(),
                    totalVolume = (50_000_000 + i * 2_000_000).toString(),
                    totalVolumeBtc = (10_000 + i * 500).toString(),
                    priceChangePercentage24h = mapOf("usd" to (i - 5) * 1.2, "btc" to (i - 5) * 0.4),
                    //sparkline = "https://dummyimage.com/600x200/${i}22/fff&text=Coin+$i",
                    sparkline = null,
                    content = Trending.Coin.Item.Data.Content(
                        title = "Coin $i Title",
                        description = "Description for Coin $i"
                    )
                )
            )
        )
    }

    val nfts: List<Trending.Nft> = List(12) { i ->
        Trending.Nft(
            id = "nft-$i",
            name = "NFT $i",
            symbol = "NFT$i",
            nftContractId = 5000 + i,
            //thumb = "https://dummyimage.com/100x100/${i}33/fff&text=NFT$i",
            thumb = null,
            nativeCurrencySymbol = "ETH",
            floorPriceInNativeCurrency = 10.0 + i,
            floorPrice24hPercentageChange = -5.0 + i,
            data = Trending.Nft.Data(
                floorPrice = "${10 + i} ETH",
                floorPriceInUsd24hPercentageChange = "${-5 + i}",
                h24AverageSalePrice = "${15 + i} ETH",
                h24Volume = "${100 + (i * 10)} ETH",
                //sparkline = "https://dummyimage.com/600x200/${i}44/fff&text=NFT+$i",
                sparkline = null,
                content = Trending.Nft.Data.Content(
                    title = "NFT $i Collection",
                    description = "Description for NFT $i collection."
                )
            )
        )
    }
}
