package com.example.designsystem.component.text

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp

@Composable
fun AppTextReadMore(
    text: String,
    modifier: Modifier = Modifier,
    collapsedMaxLines: Int = 3,
    readMoreText: String = "Read more",
    readLessText: String = "Read less",
    readMoreColor: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.primary,
    textStyle: androidx.compose.ui.text.TextStyle = MaterialTheme.typography.bodyMedium
) {
    var expanded by remember { mutableStateOf(false) }
    var isTextOverflow by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .animateContentSize() // smooth expand/collapse animation
    ) {
        AppText(
            text = text,
            style = textStyle,
            maxLines = if (expanded) Int.MAX_VALUE else collapsedMaxLines,
            overflow = TextOverflow.Ellipsis,
            onTextLayout = { result ->
                isTextOverflow = result.hasVisualOverflow
            }
        )

        if (isTextOverflow || expanded) {
            Text(
                text = buildAnnotatedString {
                    append(if (expanded) readLessText else readMoreText)
                    addStyle(
                        style = SpanStyle(
                            color = readMoreColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        ),
                        start = 0,
                        end = this.length
                    )
                },
                modifier = Modifier.clickable { expanded = !expanded },
                style = textStyle
            )
        }
    }
}


@Composable
fun AppTextReadMoreHtmlFormat(
    text: String,
    modifier: Modifier = Modifier,
    collapsedMaxLines: Int = 3,
    readMoreText: String = "Read more",
    readLessText: String = "Read less",
    readMoreColor: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.primary,
    textStyle: androidx.compose.ui.text.TextStyle = MaterialTheme.typography.bodyMedium
) {
    var expanded by remember { mutableStateOf(false) }
    var isTextOverflow by remember { mutableStateOf(false) }
    val annotated = AnnotatedString.fromHtml(
        htmlString = text,
        linkStyles = TextLinkStyles(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        ),
    )
    Column(
        modifier = modifier
            .animateContentSize() // smooth expand/collapse animation
    ) {
        Text(
            text = annotated,
            style = textStyle,
            maxLines = if (expanded) Int.MAX_VALUE else collapsedMaxLines,
            overflow = TextOverflow.Ellipsis,
            onTextLayout = { result ->
                isTextOverflow = result.hasVisualOverflow
            }
        )

        if (isTextOverflow || expanded) {
            Text(
                text = buildAnnotatedString {
                    append(if (expanded) readLessText else readMoreText)
                    addStyle(
                        style = SpanStyle(
                            color = readMoreColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        ),
                        start = 0,
                        end = this.length
                    )
                },
                modifier = Modifier.clickable { expanded = !expanded },
                style = textStyle
            )
        }
    }
}
