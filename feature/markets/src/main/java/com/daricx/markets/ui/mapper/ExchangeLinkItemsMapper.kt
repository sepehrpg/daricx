package com.daricx.markets.ui.mapper

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.daricx.markets.ui.model.LinkItem
import com.example.common.extractor.DomainExtractor
import com.example.designsystem.extension.nonBlankOrNull
import com.example.designsystem.icon.AppIcons
import com.example.model.coins.CoinDetails
import com.example.model.exchanges.ExchangeDetail

object ExchangeLinkItemsMapper {

    @Composable
    fun buildCoinCommunityLinks(exchangeDetail: ExchangeDetail): List<LinkItem> {

        fun ensureHttp(url: String): String =
            if (url.startsWith("http://") || url.startsWith("https://")) url else "https://$url"

        return buildList {

            //Website
            exchangeDetail.url.nonBlankOrNull()?.let { url ->
                add(LinkItem(DomainExtractor.domain(url), url, null))
            }

            // Twitter: screen name → https://twitter.com/{screenName}
            exchangeDetail.twitterHandle.nonBlankOrNull()?.let { handle ->
                add(LinkItem("Twitter", "https://twitter.com/$handle", AppIcons.XTwitter))
            }

            // Facebook: username → https://facebook.com/{username}
            exchangeDetail.facebookUrl.nonBlankOrNull()?.let { url ->
                add(LinkItem("Facebook", url, AppIcons.FaceBook))
            }

            // Reddit:
            exchangeDetail.redditUrl.nonBlankOrNull()?.let { url ->
                add(LinkItem("Reddit", ensureHttp(url), AppIcons.Reddit))
            }

            // Telegram
            exchangeDetail.telegramUrl.nonBlankOrNull()?.let { url ->
                add(LinkItem("Telegram", url, AppIcons.Telegram))
            }

            exchangeDetail.slackUrl.nonBlankOrNull()?.let { url ->
                add(LinkItem("Slack", url, null))
            }
            exchangeDetail.otherUrl1.nonBlankOrNull()?.let { url ->
                add(LinkItem(DomainExtractor.domain(url), url, null))
            }
            exchangeDetail.otherUrl2.nonBlankOrNull()?.let { url ->
                add(LinkItem(DomainExtractor.domain(url), url, null))
            }

        }
    }
}



