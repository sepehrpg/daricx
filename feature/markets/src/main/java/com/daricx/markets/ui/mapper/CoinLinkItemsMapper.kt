package com.daricx.markets.ui.mapper

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.daricx.markets.ui.model.LinkItem
import com.example.common.extractor.DomainExtractor
import com.example.designsystem.extension.nonBlankOrNull
import com.example.designsystem.icon.AppIcons
import com.example.model.coins.CoinDetails


object CoinLinkItemsMapper {
    @Composable
    fun buildCoinOfficialLinkItems(coinDetails: CoinDetails?): List<LinkItem> {
        fun buildList(
            links: List<String?>?,
            label: String,
            icon: ImageVector
        ): List<LinkItem> =
            links
                ?.filterNot { it.isNullOrBlank() }
                ?.map { link ->
                    LinkItem(
                        label = label,
                        icon = icon,
                        url = link ?: ""
                    )
                } ?: emptyList()

        val homepageLinks = buildList(
            coinDetails?.links?.homepage,
            "Website",
            AppIcons.Website
        )
        val githubLinks = buildList(
            coinDetails?.links?.reposUrl?.github,
            "Github",
            AppIcons.Github
        )
        val whitepaperLinks = listOfNotNull(
            coinDetails?.links?.whitepaper?.takeIf { it.isNotBlank() }?.let {
                LinkItem("WhitePaper", it, AppIcons.WhitePaper)
            }
        )
        return homepageLinks + githubLinks + whitepaperLinks
    }


    @Composable
    fun buildCoinExplorersLinkItems(coinDetails: CoinDetails?): List<LinkItem> {
        return   coinDetails?.links?.blockchainSite
            ?.filterNot { it.isNullOrBlank() }
            ?.map { link ->
                LinkItem(
                    label = DomainExtractor.domain(link?:""),
                    icon = null,
                    url = link ?: ""
                )
            } ?: emptyList()
    }


    @Composable
    fun buildCoinCommunityLinks(coinDetails: CoinDetails?): List<LinkItem> {
        val links = coinDetails?.links ?: return emptyList()

        fun ensureHttp(url: String): String =
            if (url.startsWith("http://") || url.startsWith("https://")) url else "https://$url"

        return buildList {
            // Twitter: screen name → https://twitter.com/{screenName}
            links.twitterScreenName.nonBlankOrNull()?.let { handle ->
                add(LinkItem("Twitter", "https://twitter.com/$handle", AppIcons.XTwitter))
            }

            // Facebook: username → https://facebook.com/{username}
            links.facebookUsername.nonBlankOrNull()?.let { username ->
                add(LinkItem("Facebook", "https://facebook.com/$username", AppIcons.FaceBook))
            }

            // Telegram
            links.telegramChannelIdentifier.nonBlankOrNull()?.let { tg ->
                val url = if (tg.contains(".")) ensureHttp(tg) else "https://t.me/$tg"
                add(LinkItem("Telegram", url, AppIcons.Telegram))
            }

            // Reddit:
            links.subredditUrl.nonBlankOrNull()?.let { url ->
                add(LinkItem("Reddit", ensureHttp(url), AppIcons.Reddit))
            }
        }
    }
}

