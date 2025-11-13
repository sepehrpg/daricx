package com.example.designsystem.component.text

import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat


@Composable
fun HtmlText(
    html: String,
    modifier: Modifier = Modifier
) {
    val annotated = AnnotatedString.fromHtml(
        htmlString = html,
        linkStyles = TextLinkStyles(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        ),
    )
    Text(annotated,modifier = modifier)
}


val testHtml = """
    <h2>Welcome to <b>HtmlText</b> Demo!</h2>
    <p>This is a <i>Compose-based</i> <u>HTML renderer</u>.</p>
    <p style="color:green;">You can also use <span style="color:#FF5722;">inline colors</span> and emojis 😎</p>
    <p>Supported elements include:</p>
    <ul>
        <li><b>Bold</b> text</li>
        <li><i>Italic</i> text</li>
        <li><u>Underline</u></li>
        <li><a href="https://developer.android.com">Clickable Links</a></li>
    </ul>
    <blockquote>“Simplicity is the soul of efficiency.” – Austin Freeman</blockquote>
"""
@Preview
@Composable
fun HtmlTextPreview(){
    HtmlText(testHtml, modifier = Modifier.padding(20.dp))
}


@Composable
fun HtmlTextAndroidView(
    html: String,
    modifier: Modifier = Modifier
) {
    AndroidView(
        factory = { context ->
            TextView(context).apply {
                text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT)
                movementMethod = LinkMovementMethod.getInstance() // for links
                setTextColor(android.graphics.Color.WHITE) // for color
            }
        },
        modifier = modifier
    )
}


